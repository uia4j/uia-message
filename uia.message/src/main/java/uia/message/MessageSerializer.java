/*******************************************************************************
 * Copyright 2017 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uia.message.codec.BlockCodec;
import uia.message.codec.BlockCodecException;
import uia.message.fx.ValueFx;
import uia.message.model.xml.BitBlockListType;
import uia.message.model.xml.BitBlockRefType;
import uia.message.model.xml.BitBlockSeqListType;
import uia.message.model.xml.BitBlockSeqType;
import uia.message.model.xml.BitBlockType;
import uia.message.model.xml.BlockBaseType;
import uia.message.model.xml.MessageType;
import uia.message.model.xml.PropType;
import uia.utils.ByteUtils;
import uia.utils.ElemArithmetic;
import uia.utils.PropertyUtils;

/**
 * Serialize a object to byte array.
 *
 * @author Kyle
 */
public class MessageSerializer {

    private final DataExFactory factory;

    private final MessageType mt;

    private byte[] resultBytes;

    private int indexByte;

    private int indexBit;

    private final HashMap<String, Object> blockValues;

    private final HashMap<String, BlockSerializer> blockSerializers;

    MessageSerializer(DataExFactory factory, MessageType mt) {
        this.factory = factory;
        this.mt = mt;
        this.blockValues = new HashMap<String, Object>();
        this.blockSerializers = new HashMap<String, BlockSerializer>();
        this.resultBytes = new byte[0];

        this.blockSerializers.put(BitBlockType.class.getSimpleName(), new BlockSerializer() {

            @Override
            public void accept(String name, BlockBaseType blockType, Object value, boolean parentValue) throws BlockCodecException {
                acceptBitBlock(name, (BitBlockType) blockType, value, parentValue);
            }

        });

        this.blockSerializers.put(BitBlockListType.class.getSimpleName(), new BlockSerializer() {

            @Override
            public void accept(String name, BlockBaseType blockType, Object value, boolean parentValue) throws BlockCodecException {
                acceptBitBlockList(name, (BitBlockListType) blockType, value, parentValue);
            }

        });

        this.blockSerializers.put(BitBlockSeqType.class.getSimpleName(), new BlockSerializer() {

            @Override
            public void accept(String name, BlockBaseType blockType, Object value, boolean parentValue) throws BlockCodecException {
                acceptBitBlockSeq(name, (BitBlockSeqType) blockType, value, parentValue);
            }

        });

        this.blockSerializers.put(BitBlockSeqListType.class.getSimpleName(), new BlockSerializer() {

            @Override
            public void accept(String name, BlockBaseType blockType, Object value, boolean parentValue) throws BlockCodecException {
                acceptBitBlockSeqList(name, (BitBlockSeqListType) blockType, value, parentValue);
            }

        });

        this.blockSerializers.put(BitBlockRefType.class.getSimpleName(), new BlockSerializer() {

            @Override
            public void accept(String name, BlockBaseType blockType, Object value, boolean parentValue) throws BlockCodecException {
                acceptBitBlockRef(name, (BitBlockRefType) blockType, value, parentValue);
            }

        });
    }

    /**
     * Get message structure definition.
     *
     * @return Message structure definition.
     */
    public MessageType getMessageType() {
        return this.mt;
    }

    /**
     * Serialize the object to byte array.
     *
     * @param obj The object need to be serialized.
     * @return Byte array.
     * @throws BlockCodecException raise when the object can't be serialized.
     */
    public synchronized byte[] serialize(Object obj) throws BlockCodecException {
        return serialize(obj, null);
    }

    /**
     * Serialize the object to byte array.
     *
     * @param obj The object need to be serialized.
     * @param context Useful data to serialize message.
     * @return Byte array.
     * @throws BlockCodecException raise when the object can't be serialized.
     */
    public synchronized byte[] serialize(Object obj, Map<String, Object> context) throws BlockCodecException {
        this.indexByte = 0;
        this.indexBit = 0;
        this.blockValues.clear();
        if (context != null) {
            this.blockValues.putAll(context);
        }
        this.resultBytes = new byte[0];

        BitBlockSeqType bodyType = this.mt.getBody();
        this.blockSerializers.get(bodyType.getClass().getSimpleName()).accept(bodyType.getName(), bodyType, obj, false);
        this.blockValues.clear();

        return this.resultBytes;
    }

    private boolean acceptBlock(String blockName, BlockBaseType block, Object value, boolean parentValue) throws BlockCodecException {
        if (block.getOptionBlock() != null && block.getOptionBlock().length() > 0) {
            try {
                Object option = value;
                if (parentValue) {
                    option = PropertyUtils.read(value, block.getOptionBlock());
                    if (option == null) {
                        option = this.blockValues.get(block.getOptionBlock());
                    }
                }
                if (option == null) {
                    return false;
                }

                String v = option.getClass() == byte[].class ? ByteUtils.toHexString((byte[]) option, "") : option.toString();
                return block.isOptionEq() ?
                        block.getOptionValue().equals(v) : !block.getOptionValue().equals(v);
            }
            catch (Exception ex) {
                throw new BlockCodecException(this.mt.getName() + "> " + blockName + "> option failed. ex:" + ex.getMessage(), ex);
            }
        }
        else {
            return true;
        }
    }

    private void acceptBitBlock(String name, BitBlockType blockType, Object value, boolean parentValue) throws BlockCodecException {
        Object blockValue = value;
        try {
            if (parentValue) {
                blockValue = PropertyUtils.read(value, name);
            }
            this.blockValues.put(name, blockValue);
        }
        catch (Exception ex) {
            throw new BlockCodecException(this.mt.getName() + "> " + name + "> encode failed(" + blockType.getName() + "). ex:" + ex.getMessage(),
                    ex);
        }

        int bitLength = 0;

        if (blockType.getSizeFx() != null && blockType.getSizeFx().trim().length() > 0) {
            ValueFx fx = this.factory.getFx(blockType.getSizeFx().trim());
            bitLength = fx.encode(name, blockValue, this.blockValues);
        }
        else if (blockType.getSizeBlock() != null && blockType.getSizeBlock().length() > 0) {
            String sizeBlock = blockType.getSizeBlock();
            try {
                bitLength = (int) new ElemArithmetic(sizeBlock).calculate(this.blockValues);
            }
            catch (Exception ex) {
                throw new BlockCodecException(
                        this.mt.getName() + "> " + name + "> codec sizeBlock parse failed. propType:" + name + " ex:" + ex.getMessage(),
                        ex);
            }
        }
        else {
            bitLength = blockType.getSize();
        }

        bitLength = "bit".equalsIgnoreCase(blockType.getSizeUnit()) ? bitLength : bitLength * 8;

        @SuppressWarnings("rawtypes")
        BlockCodec codec = this.factory.getCodec(blockType.getDataType());
        codec.reset();
        if (blockType.getCodecPropSet() != null) {
            for (PropType prop : blockType.getCodecPropSet().getProp()) {
                try {
                    PropertyUtils.write(codec, prop.getName(), prop.getValue());
                }
                catch (Exception ex) {
                    throw new BlockCodecException(
                            this.mt.getName() + "> " + name + "> codec property failed. propType:" + prop.getName() + " ex:" + ex.getMessage(),
                            ex);
                }
            }
        }

        byte[] bytes;
        try {
            @SuppressWarnings("unchecked")
            byte[] temp = bitLength == 0 ? new byte[0] : codec.encode(blockValue, bitLength);
            if (bitLength >= 0) {
                bytes = ByteUtils.offsetBits(
                        temp,
                        this.indexBit,
                        bitLength);
            }
            else {
                bytes = temp;
            }
            put(bytes);
            this.factory.valueHandled(name, new BlockInfo(blockValue, temp, bitLength));
        }
        catch (Exception ex) {
            throw new BlockCodecException(
                    this.mt.getName() + "> " + name + "> encode failed(" + blockType.getName() + "). ex:" + ex.getMessage(),
                    ex);
        }

        // note: last block with dynamic size.
        if (bitLength < 0) {
            bitLength = bytes.length * 8 - this.indexBit;
        }

        int byteCount = bitLength / 8;
        int bitCount = bitLength % 8;

        this.indexByte += byteCount;
        this.indexBit += bitCount;
        if (this.indexBit >= 8) {
            this.indexByte++;
            this.indexBit -= 8;
        }
    }

    @SuppressWarnings("unchecked")
    private void acceptBitBlockList(String listName, BitBlockListType listType, Object value, boolean parentValue) throws BlockCodecException {
        this.factory.listTouched(listName, true, this.indexByte * 8 + this.indexBit);

        List<Object> listValues = null;
        try {
            if (parentValue) {
                listValues = (List<Object>) PropertyUtils.read(value, listName);
            }
            else {
                listValues = (List<Object>) value;
            }
        }
        catch (Exception ex) {
            throw new BlockCodecException(
                    this.mt.getName() + "> " + listName + "> encode failed(" + listType.getName() + "). ex:" + ex.getMessage(),
                    ex);
        }

        // TODO: fix if objs.size() is different from countBlock
        if (listValues != null) {
            BlockSerializer serializer = this.blockSerializers.get(BitBlockType.class.getSimpleName());
            for (int i = 0; i < listValues.size(); i++) {
                serializer.accept(listName, listType, listValues.get(i), false);
            }
        }

        this.factory.listTouched(listName, false, this.indexByte * 8 + this.indexBit);
    }

    private void acceptBitBlockSeq(String seqName, BitBlockSeqType seqType, Object value, boolean parentValue) throws BlockCodecException {
        this.factory.seqTouched(seqName, true, this.indexByte * 8 + this.indexBit);

        Object seqValue = value;
        String cn = seqType.getClassName();
        if (parentValue && cn != null && cn.length() > 0) {
            try {
                seqValue = PropertyUtils.read(value, seqName);
            }
            catch (Exception ex) {
                throw new BlockCodecException(
                        this.mt.getName() + "> " + seqName + "> encode failed(" + seqType.getName() + "). ex:" + ex.getMessage(),
                        ex);
            }
        }

        if (seqValue == null) {
            throw new BlockCodecException(this.mt.getName() + "> " + seqName + "> encode failed. value is null");
        }

        for (BlockBaseType blockType : seqType.getBlockOrBlockListOrBlockSeq()) {
            String name = blockType.getName();
            if (acceptBlock(name, blockType, seqValue, true)) {
                this.blockSerializers.get(blockType.getClass().getSimpleName()).accept(blockType.getName(), blockType, seqValue, true);
            }
        }

        this.factory.seqTouched(seqName, false, this.indexByte * 8 + this.indexBit);
    }

    @SuppressWarnings("unchecked")
    private void acceptBitBlockSeqList(String listName, BitBlockSeqListType listType, Object value, boolean parentValue) throws BlockCodecException {
        this.factory.listTouched(listName, true, this.indexByte * 8 + this.indexBit);

        List<Object> listValues = null;
        try {
            if (parentValue) {
                listValues = (List<Object>) PropertyUtils.read(value, listName);
            }
            else {
                listValues = (List<Object>) value;
            }
        }
        catch (Exception ex) {
            throw new BlockCodecException(
                    this.mt.getName() + "> " + listName + "> encode failed(" + listType.getName() + "). ex:" + ex.getMessage(),
                    ex);
        }

        if (listValues != null) {
            BlockSerializer serializer = this.blockSerializers.get(BitBlockSeqType.class.getSimpleName());
            for (int i = 0; i < listValues.size(); i++) {
                serializer.accept(listName, listType, listValues.get(i), false);
            }
        }

        this.factory.listTouched(listName, false, this.indexByte * 8 + this.indexBit);
    }

    private void acceptBitBlockRef(String blockName, BitBlockRefType referenceType, Object value, boolean parentValue) throws BlockCodecException {
        String referenceName = referenceType.getReference();
        BlockBaseType blockType = this.factory.getReferenceBlock(referenceName);
        if (blockType == null) {
            throw new BlockCodecException(this.mt.getName() + "> " + blockName + "> blockRef failed. \'" + referenceName + "\' is not defined.");
        }

        this.blockSerializers.get(blockType.getClass().getSimpleName()).accept(blockName, blockType, value, parentValue);
    }

    private void put(byte[] data) {
        byte[] value = new byte[this.indexByte + data.length];
        System.arraycopy(this.resultBytes, 0, value, 0, this.resultBytes.length);
        for (int i = 0; i < data.length; i++) {
            byte one = value[this.indexByte + i];
            value[this.indexByte + i] = (byte) (one + data[i]);
        }
        this.resultBytes = value;
    }
}
