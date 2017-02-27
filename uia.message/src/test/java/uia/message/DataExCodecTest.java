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

import java.io.File;

import org.junit.Test;

import uia.message.model.xml.BitBlockRefType;
import uia.message.model.xml.BitBlockSeqListType;
import uia.message.model.xml.BitBlockSeqType;
import uia.message.model.xml.BitBlockType;
import uia.message.model.xml.BlockBaseType;
import uia.message.model.xml.BlockCodecSpaceType;
import uia.message.model.xml.BlockCodecType;
import uia.message.model.xml.BlockSpaceType;
import uia.message.model.xml.CodecPropSetType;
import uia.message.model.xml.DataExType;
import uia.message.model.xml.FxSpaceType;
import uia.message.model.xml.FxType;
import uia.message.model.xml.MessageSpaceType;
import uia.message.model.xml.MessageType;
import uia.message.model.xml.PropType;

/**
 *
 * @author Kyle
 */
public class DataExCodecTest {

    public DataExCodecTest() {
    }

    @Test
    public void testDecode() throws Exception {
        File f = new File(DataExCodecTest.class.getResource("Rcv.xml").toURI());

        DataExType dxType = DataExCodec.decode(f);
        print(dxType.getBlockSpace());
        System.out.println();
        print(dxType.getMessageSpace());
        System.out.println();
        print(dxType.getBlockCodecSpace());
        System.out.println();
        print(dxType.getFxSpace());
        System.out.println();
    }

    private void print(BlockSpaceType bsType) {
        if (bsType == null) {
            return;
        }
        System.out.println("BlockSpace> " + bsType.getBlockOrBlockListOrBlockSeq().size());
        for (BlockBaseType block : bsType.getBlockOrBlockListOrBlockSeq()) {
            if (block instanceof BitBlockRefType) {
                print((BitBlockRefType) block);
            }
            else if (block instanceof BitBlockSeqListType) {
                print((BitBlockSeqListType) block);
            }
            else if (block instanceof BitBlockSeqType) {
                print((BitBlockSeqType) block);
            }
            else {
                print((BitBlockType) block);
            }
        }
    }

    private void print(MessageSpaceType msType) {
        System.out.println("MessageSpace> " + msType.getMessage().size());
        for (MessageType mt : msType.getMessage()) {
            System.out.println(mt.getName());
            print(mt.getBody());
        }
    }

    private void print(BlockCodecSpaceType dsType) {
        if (dsType == null) {
            return;
        }
        System.out.println("CodecSpace> " + dsType.getBlockCodec().size());
        for (BlockCodecType decoder : dsType.getBlockCodec()) {
            System.out.println(String.format("  %1$-10s> %2$s",
                    decoder.getDataType(),
                    decoder.getDriver()));
        }
    }

    private void print(FxSpaceType fxType) {
        if (fxType == null) {
            return;
        }
        System.out.println("FxSpace> " + fxType.getFx().size());
        for (FxType decoder : fxType.getFx()) {
            System.out.println(String.format("  %1$-10s> %2$s",
                    decoder.getName(),
                    decoder.getDriver()));
        }
    }

    private void print(BitBlockSeqType seq) {
        System.out.println(String.format("  seq> %1$-15s> %2$s - %3$s",
                seq.getName(),
                seq.getClassName(),
                seq.getDesc()));
        for (BlockBaseType block : seq.getBlockOrBlockListOrBlockSeq()) {
            if (block instanceof BitBlockRefType) {
                print((BitBlockRefType) block);
            }
            else if (block instanceof BitBlockSeqListType) {
                print((BitBlockSeqListType) block);
            }
            else if (block instanceof BitBlockSeqType) {
                print((BitBlockSeqType) block);
            }
            else {
                print((BitBlockType) block);
            }
        }
    }

    private void print(BitBlockRefType block) {
        System.out.println(String.format("  ref> %1$-15s> (ref)",
                block.getName()));
    }

    private void print(BitBlockType block) {
        System.out.println(String.format("  blk> %1$-15s> %2$s(%3$s) - %4$s",
                block.getName(),
                block.getDataType(),
                block.getSize(),
                block.getDesc()));
        CodecPropSetType propSet = block.getCodecPropSet();
        if (propSet != null) {
            for (PropType prop : propSet.getProp()) {
                System.out.println(String.format("   .%1$-8s= %2$s",
                        prop.getName(),
                        prop.getValue()));
            }
        }
    }
}
