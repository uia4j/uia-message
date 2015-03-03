/*******************************************************************************
 * * Copyright (c) 2015, UIA
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
public class MessageReader {

    private final DataExFactory factory;

    private final MessageType mt;

    private final HashMap<String, Block> blocks;

    private int byteStart;

    private int bitStart;

    MessageReader(DataExFactory factory, MessageType mt) {
        this.factory = factory;
        this.mt = mt;
        this.blocks = new HashMap<String, Block>();
    }

    public BlockSeq read(byte[] data) throws BlockCodecException {
        this.byteStart = 0;
        this.bitStart = 0;
        this.blocks.clear();

        BitBlockSeqType body = this.mt.getBody();
        BlockSeq result = decode(body, data, true, body.getName());
        this.blocks.put(body.getName(), result);

        return result;
    }

    private BlockSeq decode(BitBlockSeqType blockSeqType, byte[] data, boolean addToResult, String name) throws BlockCodecException {
        int bytePt = this.byteStart;
        int bitPt = this.bitStart;

        BlockSeq blockSeq = new BlockSeq(name);

        boolean ignore = false;
        List<BlockBaseType> blockTypes = blockSeqType.getBlockOrBlockListOrBlockSeq();
        for (BlockBaseType blockType : blockTypes) {
            String subName = blockType.getName();

            if (blockType instanceof BitBlockRefType) {
                blockType = this.factory.getReferenceBlock(((BitBlockRefType) blockType).getReference());
            }

            Block block;
            if (blockType instanceof BitBlockSeqListType) {
                block = decode((BitBlockSeqListType) blockType, data, subName);
            } else if (blockType instanceof BitBlockSeqType) {
                block = decode((BitBlockSeqType) blockType, data, false, subName);
            } else {
                block = decode((BitBlockType) blockType, data, subName);
            }
            if (!ignore && addToResult) {
                this.blocks.put(subName, block);
            }

            blockSeq.putSubBlock(block);

        }

        int bitLength = 8 * (this.byteStart - bytePt) + (this.bitStart - bitPt);
        blockSeq.setValue(ByteUtils.copyBits(data, bytePt, bitPt, bitLength));

        return blockSeq;
    }

    private Block decode(BitBlockType blockType, byte[] data, String name) throws BlockCodecException {
        int bitLength = 0;
        String sizeBlock = blockType.getSizeBlock();
        if (sizeBlock != null && sizeBlock.length() > 0) {
            Integer size = (Integer) this.blocks.get(blockType.getSizeBlock()).getValue();
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

        byte[] bytes = ByteUtils.copyBits(data, this.byteStart, this.bitStart, bitLength);
        BlockCodec<?> codec = this.factory.getCodec(blockType.getDataType());
        codec.reset();
        if (blockType.getCodecPropSet() != null) {
            for (PropType prop : blockType.getCodecPropSet().getProp()) {
                try {
                    PropertyUtils.write(codec, prop.getName(), prop.getValue());
                } catch (Exception ex) {
                    throw new BlockCodecException("decode failure. object:" + name + ", property:" + name + ". " + ex.getMessage());
                }
            }
        }

        Object value = null;
        try {
            value = codec.decode(bytes, bitLength);
        } catch (Exception ex) {
            throw new BlockCodecException(name + ": decode ex (" + ex.getMessage() + ")", ex);
        }

        Block block = new Block(name, value);

        int byteCount = bitLength / 8;
        int bitCount = bitLength % 8;

        this.byteStart += byteCount;
        this.bitStart += bitCount;
        if (this.bitStart >= 8) {
            this.byteStart++;
            this.bitStart -= 8;
        }

        return block;
    }

    private BlockList decode(BitBlockSeqListType blockListType, byte[] data, String name) throws BlockCodecException {
        int bytePt = this.byteStart;
        int bitPt = this.bitStart;

        BlockList blockList = new BlockList(name);

        Integer count;
        String countBlock = blockListType.getCountBlock();
        if (countBlock != null && countBlock.length() > 0) {
            count = (Integer) this.blocks.get(blockListType.getCountBlock()).getValue();
            if ("+".equals(blockListType.getCountFactorOp())) {
                count += blockListType.getCountFactor();
            } else if ("-".equals(blockListType.getCountFactorOp())) {
                count -= blockListType.getCountFactor();
            } else if ("*".equals(blockListType.getCountFactorOp())) {
                count *= blockListType.getCountFactor();
            } else if ("/".equals(blockListType.getCountFactorOp())) {
                count /= blockListType.getCountFactor();
            }
        } else {
            count = blockListType.getCount();
        }

        for (int i = 0; i < count; i++) {
            blockList.getList().add(decode(blockListType, data, false, name));
        }

        int bitLength = 8 * (this.byteStart - bytePt) + (this.bitStart - bitPt);
        blockList.setValue(ByteUtils.copyBits(data, bytePt, bitPt, bitLength));

        // this.blocks.put(blockList.getName(), blockList);

        return blockList;
    }
}
