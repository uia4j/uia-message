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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uia.message.codec.BlockCodec;
import uia.message.codec.BlockCodecException;
import uia.message.model.xml.BitBlockListType;
import uia.message.model.xml.BitBlockRefType;
import uia.message.model.xml.BitBlockSeqListType;
import uia.message.model.xml.BitBlockSeqType;
import uia.message.model.xml.BitBlockType;
import uia.message.model.xml.BlockBaseType;
import uia.message.model.xml.MessageType;

/**
 * Serialize a object to byte array.
 *
 * @author Kyle
 */
public class MessageClassGenerator {

    private final DataExFactory factory;

    private final MessageType mt;

    private final HashMap<String, BlockWalker> blockWalker;

    private ArrayList<StringBuilder> builders;

    MessageClassGenerator(DataExFactory factory, MessageType mt) {
        this.factory = factory;
        this.mt = mt;
        this.blockWalker = new HashMap<String, BlockWalker>();
        this.builders = new ArrayList<StringBuilder>();

        this.blockWalker.put(BitBlockType.class.getSimpleName(), new BlockWalker() {

            @Override
            public void accept(String name, BlockBaseType blockType, StringBuilder builder, int indent) throws BlockCodecException {
                acceptBitBlock(name, (BitBlockType) blockType, builder, indent);
            }

            @Override
            public String toString() {
                return "BitBlockWalker";
            }

        });

        this.blockWalker.put(BitBlockListType.class.getSimpleName(), new BlockWalker() {

            @Override
            public void accept(String name, BlockBaseType blockType, StringBuilder builder, int indent) throws BlockCodecException {
                acceptBitBlockList(name, (BitBlockListType) blockType, builder, indent);
            }

            @Override
            public String toString() {
                return "BitBlockListWalker";
            }

        });

        this.blockWalker.put(BitBlockSeqType.class.getSimpleName(), new BlockWalker() {

            @Override
            public void accept(String name, BlockBaseType blockType, StringBuilder builder, int indent) throws BlockCodecException {
                acceptBitBlockSeq(name, (BitBlockSeqType) blockType, builder, indent);
            }

            @Override
            public String toString() {
                return "BitBlockSeqWalker";
            }

        });

        this.blockWalker.put(BitBlockSeqListType.class.getSimpleName(), new BlockWalker() {

            @Override
            public void accept(String name, BlockBaseType blockType, StringBuilder builder, int indent) throws BlockCodecException {
                acceptBitBlockSeqList(name, (BitBlockSeqListType) blockType, builder, indent);
            }

            @Override
            public String toString() {
                return "BitBlockSeqListWalker";
            }

        });

        this.blockWalker.put(BitBlockRefType.class.getSimpleName(), new BlockWalker() {

            @Override
            public void accept(String name, BlockBaseType blockType, StringBuilder builder, int indent) throws BlockCodecException {
                acceptBitBlockRef(name, (BitBlockRefType) blockType, builder, indent);
            }

            @Override
            public String toString() {
                return "BitBlockRefWalker";
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

    public synchronized void generate(PrintStream out) throws BlockCodecException {
        StringBuilder builder0 = new StringBuilder();

        BitBlockSeqType bodyType = this.mt.getBody();
        List<BlockBaseType> blocks = bodyType.getBlockOrBlockListOrBlockSeq();
        for (BlockBaseType block : blocks) {
            this.blockWalker.get(block.getClass().getSimpleName()).accept(block.getName(), block, builder0, 4);
        }

        int idx = bodyType.getClassName().lastIndexOf(".");
        out.println("package " + bodyType.getClassName().substring(0, idx) + ";\n");
        out.print("public class " + bodyType.getClassName().substring(idx + 1) + " {");
        out.println(builder0.toString());
        for (StringBuilder builder : this.builders) {
            out.println(builder.toString());
        }
        out.println("}");
    }

    private void acceptBitBlock(String name, BitBlockType blockType, StringBuilder builder, int indent) throws BlockCodecException {
        BlockCodec<?> codec = this.factory.getCodec(blockType.getDataType());
        indenting(builder, indent).append("private ").append(codec.getValueType()).append(" ").append(name).append(";");
    }

    private void acceptBitBlockList(String listName, BitBlockListType listType, StringBuilder builder, int indent) throws BlockCodecException {
        BlockCodec<?> codec = this.factory.getCodec(listType.getDataType());
        indenting(builder, indent).append("private ArrayList<").append(codec.getValueType()).append("> ").append(listName).append(";");
    }

    private void acceptBitBlockSeq(String seqName, BitBlockSeqType seqType, StringBuilder builder, int indent) throws BlockCodecException {
        if (seqType.getClassName() == null) {
            for (BlockBaseType block : seqType.getBlockOrBlockListOrBlockSeq()) {
                this.blockWalker.get(block.getClass().getSimpleName()).accept(block.getName(), block, builder, 4);
            }
        }
        else {
            String classType = seqType.getClassName().substring(seqType.getClassName().indexOf("$") + 1);
            indenting(builder, indent).append("private ").append(classType).append(" ").append(seqName).append(";");

            StringBuilder builderNext = new StringBuilder();
            this.builders.add(builderNext);

            indenting(builderNext, 4).append("public static class ").append(classType).append(" {");
            for (BlockBaseType block : seqType.getBlockOrBlockListOrBlockSeq()) {
                this.blockWalker.get(block.getClass().getSimpleName()).accept(block.getName(), block, builderNext, 8);
            }
            indenting(builderNext, 4).append("}");
        }
    }

    private void acceptBitBlockSeqList(String listName, BitBlockSeqListType listType, StringBuilder builder, int indent) throws BlockCodecException {
        String classType = listType.getClassName().substring(listType.getClassName().indexOf("$") + 1);
        indenting(builder, indent).append("private ArrayList<").append(classType).append("> ").append(listName).append(";");

        StringBuilder builderNext = new StringBuilder();
        this.builders.add(builderNext);

        indenting(builderNext, 4).append("public static class ").append(classType).append(" {");
        for (BlockBaseType block : listType.getBlockOrBlockListOrBlockSeq()) {
            this.blockWalker.get(block.getClass().getSimpleName()).accept(block.getName(), block, builderNext, 8);
        }
        indenting(builderNext, 4).append("}");
    }

    private void acceptBitBlockRef(String blockName, BitBlockRefType referenceType, StringBuilder builder, int indent) throws BlockCodecException {
        BlockBaseType block = this.factory.getReferenceBlock(referenceType.getReference());
        this.blockWalker.get(block.getClass().getSimpleName()).accept(blockName, block, builder, 4);
    }

    private static StringBuilder indenting(StringBuilder builder, int indent) {
        builder.append("\n\n");
        for (int i = 0; i < indent; i++) {
            builder.append(" ");
        }
        return builder;
    }

    public static interface BlockWalker {

        public void accept(String name, BlockBaseType blockType, StringBuilder builder, int indent) throws BlockCodecException;
    }

}
