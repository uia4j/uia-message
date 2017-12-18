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

import java.util.ArrayList;
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
 * Deserialize byte array to a object.
 *
 * @author Kyle
 */
public class MessageDeserializer {

    private final DataExFactory factory;

    private final MessageType mt;

    private final HashMap<String, Object> blockValues;

    private int byteStart;

    private int bitStart;

    MessageDeserializer(DataExFactory factory, MessageType mt) {
        this.factory = factory;
        this.mt = mt;
        this.blockValues = new HashMap<String, Object>();
    }

    /**
     * Report byte index of deserializing.
     * @return Byte index.
     */
    public int currBytes() {
        return this.byteStart;
    }

    /**
     * Report bit index of deserializing.
     * @return Bit index.
     */
    public int currBits() {
        return this.bitStart;
    }

    /**
     * Deserialize byte array to a object.
     *
     * @param data Byte array need to be deserialized.
     * @param <T> Type returned.
     * @return The object.
     * @throws BlockCodecException raise when byte array can't be deserialized.
     */
    public synchronized <T> T deserialize(byte[] data) throws BlockCodecException {
        return deserialize(data, null);
    }

    /**
     * Deserialize byte array to a object.
     *
     * @param data Byte array need to be deserialized.
     * @param context Useful data to deserialize message.
     * @param <T> Type returned.
     * @return The object.
     * @throws BlockCodecException raise when byte array can't be deserialized.
     */
    public synchronized <T> T deserialize(byte[] data, Map<String, Object> context) throws BlockCodecException {
        this.byteStart = 0;
        this.bitStart = 0;
        this.blockValues.clear();
        if (context != null) {
            this.blockValues.putAll(context);
        }
        BitBlockSeqType body = this.mt.getBody();
        @SuppressWarnings("unchecked")
        T result = (T) decode(body.getName(), body, data, null);
        this.blockValues.clear();
        // System.gc();
        return result;
    }

    private Object decode(String seqName, BitBlockSeqType seqType, byte[] data, Object parent) throws BlockCodecException {
        this.factory.seqTouched(seqName, true, this.byteStart * 8 + this.bitStart);
        try {
            String cn = seqType.getClassName();
            Object seqObj = cn != null && cn.length() > 0 ? Class.forName(seqType.getClassName()).newInstance() : parent;

            List<BlockBaseType> blockTypes = seqType.getBlockOrBlockListOrBlockSeq();
            for (BlockBaseType blockType : blockTypes) {
                String name = blockType.getName(); // name of property

                // exists check
                if (!exists(name, blockType, seqObj)) {
                    continue;
                }

                if (blockType instanceof BitBlockRefType) {
                    String referenceName = ((BitBlockRefType) blockType).getReference();
                    blockType = this.factory.getReferenceBlock(referenceName); // change to reference type.
                    if (blockType == null) {
                        throw new BlockCodecException("blockRef failed. \'" + referenceName + "\' " + seqType.getName() + "." + name + " references is not defined.");
                    }
                }

                boolean readonly = false;
                Object blockValue = null;
                if (blockType instanceof BitBlockSeqListType) {
                    blockValue = decode(name, (BitBlockSeqListType) blockType, data);
                }
                else if (blockType instanceof BitBlockListType) {
                    blockValue = decode(name, (BitBlockListType) blockType, data);
                }
                else if (blockType instanceof BitBlockSeqType) {
                    blockValue = decode(name, (BitBlockSeqType) blockType, data, seqObj);
                    cn = ((BitBlockSeqType) blockType).getClassName();
                    readonly = cn == null || cn.length() == 0;
                }
                else {
                    blockValue = decode(name, (BitBlockType) blockType, data);
                    readonly = ((BitBlockType) blockType).isReadonly();
                }

                try {
                    if (!readonly && !PropertyUtils.write(seqObj, name, blockValue)) {
                        throw new BlockCodecException("property failed. " + seqType.getName() + "." + name + " is null");
                    }
                }
                catch (BlockCodecException ex1) {
                    throw ex1;
                }
                catch (Exception ex2) {
                    throw new BlockCodecException(
                            "decode failed. " + seqType.getName() + "." + name + ". ex:" + ex2.getMessage(),
                            ex2);
                }

                if (blockValue != null) {
                    this.blockValues.put(name, blockValue);
                }
            }

            return seqObj;
        }
        catch (BlockCodecException ex1) {
            throw ex1;
        }
        catch (Exception ex2) {
            throw new BlockCodecException(
                    "decode failed. " + seqName + "(" + seqType.getName() + ") ex:" + ex2.getMessage(),
                    ex2);
        }
        finally {
            this.factory.seqTouched(seqName, false, this.byteStart * 8 + this.bitStart);

        }
    }

    private Object decode(String name, BitBlockType blockType, byte[] data) throws BlockCodecException {
        int bitLength = 0;

        if (blockType.getSizeFx() != null && blockType.getSizeFx().trim().length() > 0) {
            ValueFx fx = this.factory.getFx(blockType.getSizeFx().trim());
            bitLength = fx.decode(name, data, this.blockValues, this.byteStart, this.bitStart);
        }
        else if (blockType.getSizeBlock() != null && blockType.getSizeBlock().length() > 0) {
            String sizeBlock = blockType.getSizeBlock();
            try {
                // TODO: check
                bitLength = (int) new ElemArithmetic(sizeBlock).calculate(this.blockValues);
                // bitLength = SizeFx.calculate(sizeBlock, this.blockValues);
            }
            catch (Exception ex) {
                throw new BlockCodecException(
                        "sizeBlock failed. block:" + name + " sizeBlock:" + blockType.getSizeBlock(),
                        ex);
            }
        }
        else {
            bitLength = blockType.getSize();
        }

        BlockCodec<?> codec = this.factory.getCodec(blockType.getDataType());
        if (bitLength == 0) {
            return codec.zeroValue();
        }

        if (bitLength > 0) {
            bitLength = "bit".equalsIgnoreCase(blockType.getSizeUnit()) ? bitLength : bitLength * 8;
        }
        else {
            // note: last block with dynamic size.
            bitLength = 8 * (data.length - this.byteStart) - this.bitStart;
        }

        byte[] bytes = ByteUtils.copyBits(data, this.byteStart, this.bitStart, bitLength);
        codec.reset();
        if (blockType.getCodecPropSet() != null) {
            for (PropType prop : blockType.getCodecPropSet().getProp()) {
                try {
                    PropertyUtils.write(codec, prop.getName(), prop.getValue());
                }
                catch (Exception ex) {
                    throw new BlockCodecException(
                            "codec property failed. block:" + name + " propType:" + prop.getName() + " ex:" + ex.getMessage(),
                            ex);
                }
            }
        }

        Object value = null;
        try {
            value = codec.decode(bytes, bitLength);
            this.factory.valueHandled(name, new BlockInfo(value, bytes, bitLength));
        }
        catch (Exception ex) {
            throw new BlockCodecException(
                    "decode failed. block:" + name + "(" + blockType.getName() + ") ex:" + ex.getMessage(),
                    ex);
        }

        int byteCount = bitLength / 8;
        int bitCount = bitLength % 8;

        this.byteStart += byteCount;
        this.bitStart += bitCount;
        if (this.bitStart >= 8) {
            this.byteStart++;
            this.bitStart -= 8;
        }

        return value;
    }

    private List<Object> decode(String listName, BitBlockListType listType, byte[] data) throws BlockCodecException {
        this.factory.listTouched(listName, true, this.byteStart * 8 + this.bitStart);

        Integer count;

        if (listType.getCountFx() != null && listType.getCountFx().trim().length() > 0) {
            ValueFx fx = this.factory.getFx(listType.getCountFx().trim());
            count = fx.decode(listName, data, this.blockValues, this.byteStart, this.bitStart);
        }
        else if (listType.getCountBlock() != null && listType.getCountBlock().length() > 0) {
            String countBlock = listType.getCountBlock();
            try {
                // TODO: check
                count = (int) new ElemArithmetic(countBlock).calculate(this.blockValues);
                // count = SizeFx.calculate(countBlock, this.blockValues);
            }
            catch (Exception ex) {
                throw new BlockCodecException(
                        "countBlock failed. block:" + listName + " sizeBlock:" + listType.getCountBlock(),
                        ex);
            }
        }
        else {
            count = listType.getCount();
        }

        ArrayList<Object> objs = new ArrayList<Object>();
        for (int i = 0; i < count; i++) {
            objs.add(decode(listName, (BitBlockType) listType, data));
        }

        this.factory.listTouched(listName, false, this.byteStart * 8 + this.bitStart);
        return objs;
    }

    private List<Object> decode(String listName, BitBlockSeqListType listType, byte[] data) throws BlockCodecException {
        this.factory.listTouched(listName, true, this.byteStart * 8 + this.bitStart);

        Integer count;
        if (listType.getCountFx() != null && listType.getCountFx().trim().length() > 0) {
            ValueFx fx = this.factory.getFx(listType.getCountFx().trim());
            count = fx.decode(listName, data, this.blockValues, this.byteStart, this.bitStart);
        }
        else if (listType.getCountBlock() != null && listType.getCountBlock().length() > 0) {
            String countBlock = listType.getCountBlock();
            try {
                // TODO: check
                count = (int) new ElemArithmetic(countBlock).calculate(this.blockValues);
                // count = SizeFx.calculate(countBlock, this.blockValues);
            }
            catch (Exception ex) {
                throw new BlockCodecException(
                        "countBlock failed. block:" + listName + " sizeBlock:" + listType.getCountBlock(),
                        ex);
            }
        }
        else {
            count = listType.getCount();
            if (count < 0) {
                count = Integer.MAX_VALUE;
            }
        }

        ArrayList<Object> objs = new ArrayList<Object>();
        for (int i = 0; i < count; i++) {
            objs.add(decode(listName, listType, data, null));
            if ((this.byteStart * 8 + this.bitStart) >= data.length * 8) {
                break;
            }
        }

        this.factory.listTouched(listName, false, this.byteStart * 8 + this.bitStart);
        return objs;
    }

    private boolean exists(String blockName, BlockBaseType block, Object obj) throws BlockCodecException {
        if (block.getOptionBlock() != null && block.getOptionBlock().length() > 0) {
            try {
                Object option = PropertyUtils.read(obj, block.getOptionBlock());
                if (option == null) {
                    option = this.blockValues.get(block.getOptionBlock());
                }
                if (option == null) {
                    return false;
                }
                String v = option.getClass() == byte[].class ? ByteUtils.toHexString((byte[]) option, "") : option.toString();
                return block.isOptionEq() ?
                        block.getOptionValue().equals(v) : !block.getOptionValue().equals(v);
            }
            catch (Exception ex) {
                throw new BlockCodecException(
                        "existsProp failed. " + blockName + " ex:" + ex.getMessage(),
                        ex);
            }
        }
        else {
            return true;
        }
    }
}
