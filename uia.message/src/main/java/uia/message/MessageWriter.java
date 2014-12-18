/*******************************************************************************
 * * Copyright (c) 2014, UIA
 * * All rights reserved.
 * * Redistribution and use in source and binary forms, with or without
 * * modification, are permitted provided that the following conditions are met:
 * *
 * *     * Redistributions of source code must retain the above copyright
 * *       notice, this list of conditions and the following disclaimer.
 * *     * Redistributions in binary form must reproduce the above copyright
 * *       notice, this list of conditions and the following disclaimer in the
 * *       documentation and/or other materials provided with the distribution.
 * *     * Neither the name of the {company name} nor the
 * *       names of its contributors may be used to endorse or promote products
 * *       derived from this software without specific prior written permission.
 * *
 * * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS" AND ANY
 * * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package uia.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uia.message.codec.BlockCodec;
import uia.message.codec.BlockCodecException;
import uia.message.model.Block;
import uia.message.model.BlockList;
import uia.message.model.BlockSeq;
import uia.message.model.xml.BitBlockRefType;
import uia.message.model.xml.BitBlockSeqListType;
import uia.message.model.xml.BitBlockSeqType;
import uia.message.model.xml.BitBlockType;
import uia.message.model.xml.BlockBaseType;
import uia.message.model.xml.MessageType;
import uia.message.model.xml.PropType;
import uia.utils.ByteUtils;
import uia.utils.PropertyUtils;

/**
 * @deprecated
 * @author Kyle
 */
@Deprecated
public class MessageWriter {

    private final DataExFactory factory;

    private final MessageType mt;

    private final ArrayList<Byte> result;

    private int indexByte;

    private int indexBit;

    private final HashMap<String, Object> blockValues;

    MessageWriter(DataExFactory factory, MessageType mt) {
        this.factory = factory;
        this.mt = mt;
        this.result = new ArrayList<Byte>();
        this.blockValues = new HashMap<String, Object>();
    }

    public byte[] write(BlockSeq body) throws BlockCodecException {
        this.indexByte = 0;
        this.indexBit = 0;
        this.result.clear();
        this.blockValues.clear();

        BitBlockSeqType bodyType = this.mt.getBody();
        encode(bodyType, body);

        byte[] data = new byte[this.result.size()];
        for (int i = 0; i < this.result.size(); i++) {
            data[i] = this.result.get(i).byteValue();
        }

        this.result.clear();
        this.blockValues.clear();

        return data;
    }

    private void encode(BitBlockSeqType seqType, BlockSeq block) throws BlockCodecException {
        for (BlockBaseType blockType : seqType.getBlockOrBlockListOrBlockSeq()) {
            String name = blockType.getName();
            if (blockType instanceof BitBlockRefType) {
                blockType = this.factory.getReferenceBlock(((BitBlockRefType) blockType).getReference());
            }

            if (blockType instanceof BitBlockSeqListType) {
                BlockList list = (BlockList) block.getSubBlock(blockType.getName());
                encode((BitBlockSeqListType) blockType, list);
            } else if (blockType instanceof BitBlockSeqType) {
                BlockSeq seq = (BlockSeq) block.getSubBlock(blockType.getName());
                encode((BitBlockSeqType) blockType, seq);
            } else {
                Block b = block.getSubBlock(name);
                if (b == null || b.getValue() == null) {
                    throw new BlockCodecException(name + " value missing");
                }
                encode((BitBlockType) blockType, b.getValue(), name);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void encode(BitBlockType blockType, Object data, String name) throws BlockCodecException {
        this.blockValues.put(name, data);

        int bitLength = 0;
        String sizeBlock = blockType.getSizeBlock();
        if (sizeBlock != null && sizeBlock.length() > 0) {
            Integer size = (Integer) this.blockValues.get(blockType.getSizeBlock());
            bitLength = size.intValue();
            if ("+".equals(blockType.getSizeFactorOp())) {
                bitLength += blockType.getSizeFactor();
            } else if ("-".equals(blockType.getSizeFactorOp())) {
                bitLength -= blockType.getSizeFactor();
            } else if ("*".equals(blockType.getSizeFactorOp())) {
                bitLength *= blockType.getSizeFactor();
            } else if ("/".equals(blockType.getSizeFactorOp())) {
                bitLength /= blockType.getSizeFactor();
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
                } catch (Exception ex) {
                    throw new BlockCodecException("encode failure. object:" + name + ", property:" + name + ". " + ex.getMessage());
                }
            }
        }

        try {
            byte[] bytes = ByteUtils.offsetBits(
                    codec.encode(data, bitLength),
                    this.indexBit,
                    bitLength);
            put(bytes);
        } catch (Exception ex) {
            throw new BlockCodecException(name + ": encode ex (" + ex.getMessage() + ")", ex);
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

    private void encode(BitBlockSeqListType listType, BlockList block) throws BlockCodecException {
        block.setCountBlock(listType.getCountBlock());
        List<BlockSeq> items = block.getList();
        // body
        for (int i = 0; i < items.size(); i++) {
            encode(listType, items.get(i));
        }
    }

    private void put(byte[] data) {
        while (this.result.size() < this.indexByte + data.length) {
            this.result.add((byte) 0);
        }

        for (int i = 0; i < data.length; i++) {
            byte value = this.result.get(this.indexByte + i).byteValue();
            this.result.remove(this.indexByte + i);
            this.result.add(this.indexByte + i, (byte) (value + data[i]));
        }
    }
}
