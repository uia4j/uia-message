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

    MessageSerializer(DataExFactory factory, MessageType mt) {
        this.factory = factory;
        this.mt = mt;
        this.blockValues = new HashMap<String, Object>();
        this.resultBytes = new byte[0];
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
    public synchronized byte[] write(Object obj) throws BlockCodecException {
        return write(obj, null);
    }

    /**
     * Serialize the object to byte array.
     *
     * @param obj The object need to be serialized.
     * @return Byte array.
     * @throws BlockCodecException raise when the object can't be serialized.
     */
    public synchronized byte[] write(Object obj, Map<String, Object> initialValues) throws BlockCodecException {
        this.indexByte = 0;
        this.indexBit = 0;
        this.blockValues.clear();
        if (initialValues != null) {
            this.blockValues.putAll(initialValues);
        }
        this.resultBytes = new byte[0];

        BitBlockSeqType bodyType = this.mt.getBody();
        encode(bodyType.getName(), bodyType, obj);

        this.blockValues.clear();

        return this.resultBytes;
    }

    @SuppressWarnings("unchecked")
    private void encode(String seqName, BitBlockSeqType seqType, Object obj) throws BlockCodecException {
        this.factory.seqTouched(seqName, true, this.indexByte * 8 + this.indexBit);
        try {
            if (obj == null) {
                throw new BlockCodecException(seqName + "> encode failed. value is null");
            }

            for (BlockBaseType blockType : seqType.getBlockOrBlockListOrBlockSeq()) {
                String name = blockType.getName();

                // exists check
                if (!exists(name, blockType, obj)) {
                    continue;
                }

                if (blockType instanceof BitBlockRefType) {
                    String referenceName = ((BitBlockRefType) blockType).getReference();
                    blockType = this.factory.getReferenceBlock(referenceName);
                    if (blockType == null) {
                        throw new BlockCodecException(seqName + "> blockRef failed. \'" +
                                referenceName + "\' " + seqType.getClassName() + "." + name + " references is not defined.");
                    }
                }

                try {
                    if (blockType instanceof BitBlockSeqListType) {
                        Object value = PropertyUtils.read(obj, name);
                        encode(name, (BitBlockSeqListType) blockType, (List<Object>) value);
                    }
                    else if (blockType instanceof BitBlockListType) {
                        Object value = PropertyUtils.read(obj, name);
                        encode(name, (BitBlockListType) blockType, (List<Object>) value);
                    }
                    else if (blockType instanceof BitBlockSeqType) {
                        String cn = ((BitBlockSeqType) blockType).getClassName();
                        Object value = (cn != null && cn.length() > 0) ?
                                PropertyUtils.read(obj, name) :
                                obj;
                        if (value == null) {
                            throw new BlockCodecException(seqName + "> property failed. " +
                                    seqType.getName() + "." + name + " is null");
                        }
                        encode(name, (BitBlockSeqType) blockType, value);
                    }
                    else {
                        Object value = PropertyUtils.read(obj, name);
                        if (value == null) {
                            throw new BlockCodecException(seqName + "> property failed. " +
                                    seqType.getName() + "." + name + " is null");
                        }
                        encode(name, (BitBlockType) blockType, value);
                    }
                }
                catch (BlockCodecException ex1) {
                    throw ex1;
                }
                catch (Exception ex2) {
                    throw new BlockCodecException(seqName + "> encode failed. " +
                            seqType.getName() + "." + name + " ex:" + ex2.getMessage(),
                            ex2);
                }
            }
        }
        finally {
            this.factory.seqTouched(seqName, false, this.indexByte * 8 + this.indexBit);
        }
    }

    private void encode(String name, BitBlockType blockType, Object obj) throws BlockCodecException {
        this.blockValues.put(name, obj);

        int bitLength = 0;

        if (blockType.getSizeFx() != null && blockType.getSizeFx().trim().length() > 0) {
            ValueFx fx = this.factory.getFx(blockType.getSizeFx().trim());
            bitLength = fx.encode(name, obj, this.blockValues);
        }
        else if (blockType.getSizeBlock() != null && blockType.getSizeBlock().length() > 0) {
            String sizeBlock = blockType.getSizeBlock();
            try {
                bitLength = (int) new ElemArithmetic(sizeBlock).calculate(this.blockValues);
                // bitLength = SizeFx.calculate(sizeBlock, this.blockValues);
            }
            catch (Exception ex) {
                throw new BlockCodecException(name + "> codec sizeBlock parse failed. propType:" + name + " ex:" + ex.getMessage(), ex);
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
                    throw new BlockCodecException(name + "> codec property failed. propType:" + prop.getName() + " ex:" + ex.getMessage(), ex);
                }
            }
        }

        byte[] bytes;
        try {
            @SuppressWarnings("unchecked")
            byte[] temp = bitLength == 0 ? new byte[0] : codec.encode(obj, bitLength);
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
            this.factory.valueHandled(name, new BlockInfo(obj, temp, bitLength));
        }
        catch (Exception ex) {
            throw new BlockCodecException(name + "> encode failed(" + blockType.getName() + "). ex:" + ex.getMessage(),
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

    private void encode(String listName, BitBlockListType listType, List<Object> objs) throws BlockCodecException {
        this.factory.listTouched(listName, true, this.indexByte * 8 + this.indexBit);

        // TODO: fix if objs.size() is different from countBlock

        if (objs != null) {
            // body
            for (int i = 0; i < objs.size(); i++) {
                encode(listName, listType, objs.get(i));
            }
        }
        this.factory.listTouched(listName, false, this.indexByte * 8 + this.indexBit);
    }

    private void encode(String listName, BitBlockSeqListType listType, List<Object> objs) throws BlockCodecException {
        this.factory.listTouched(listName, true, this.indexByte * 8 + this.indexBit);

        if (objs != null) {
            // body
            for (int i = 0; i < objs.size(); i++) {
                encode(listName, listType, objs.get(i));
            }
        }
        this.factory.listTouched(listName, false, this.indexByte * 8 + this.indexBit);
    }

    private void put(byte[] data) {
        // be careful
        byte[] value = new byte[this.indexByte + data.length];
        System.arraycopy(this.resultBytes, 0, value, 0, this.resultBytes.length);
        for (int i = 0; i < data.length; i++) {
            byte one = value[this.indexByte + i];
            value[this.indexByte + i] = (byte) (one + data[i]);
        }
        this.resultBytes = value;

        /** fix: 2015-03-12
        while (this.result.size() < this.indexByte + data.length) {
            this.result.add((byte) 0);
        }

        for (int i = 0; i < data.length; i++) {
            byte value = this.result.get(this.indexByte + i).byteValue();
            this.result.remove(this.indexByte + i);
            this.result.add(this.indexByte + i, (byte) (value + data[i]));
        }
         */
    }

    private boolean exists(String blockName, BlockBaseType block, Object obj) throws BlockCodecException {
        if (block.getOptionBlock() != null && block.getOptionBlock().length() > 0) {
            try {
                Object option = PropertyUtils.read(obj, block.getOptionBlock());
                String v = option.getClass() == byte[].class ? ByteUtils.toHexString((byte[]) option, "") : option.toString();
                return block.isOptionEq() ?
                        block.getOptionValue().equals(v) : !block.getOptionValue().equals(v);
            }
            catch (Exception ex) {
                throw new BlockCodecException(blockName + "> existsProp failed. ex:" + ex.getMessage(), ex);
            }
        }
        else {
            return true;
        }
    }
}
