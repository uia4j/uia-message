/*******************************************************************************
 * Copyright (c) 2013, BooksTech Co.,Ltd.
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the BooksTech nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 *  THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
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
import uia.message.model.xml.DataExType;
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
    }

    private void print(BlockSpaceType bsType) {
        System.out.println("BlockSpace>");
        for (BlockBaseType block : bsType.getBlockOrBlockListOrBlockSeq()) {
            if (block instanceof BitBlockRefType) {
                print((BitBlockRefType) block);
            } else if (block instanceof BitBlockSeqListType) {
                print((BitBlockSeqListType) block);
            } else if (block instanceof BitBlockSeqType) {
                print((BitBlockSeqType) block);
            } else {
                print((BitBlockType) block);
            }
        }
    }

    private void print(MessageSpaceType msType) {
        System.out.println("MessageSpace>");
        for (MessageType mt : msType.getMessage()) {
            System.out.println(mt.getName());
            print(mt.getBody());
        }
    }

    private void print(BlockCodecSpaceType dsType) {
        System.out.println("CodecSpace>");
        for (BlockCodecType decoder : dsType.getBlockCodec()) {
            System.out.println(String.format("  %1$-10s> %2$s",
                    decoder.getDataType(),
                    decoder.getDriver()));
        }
    }

    private void print(BitBlockSeqType seq) {
        for (BlockBaseType block : seq.getBlockOrBlockListOrBlockSeq()) {
            if (block instanceof BitBlockRefType) {
                print((BitBlockRefType) block);
            } else if (block instanceof BitBlockSeqListType) {
                print((BitBlockSeqListType) block);
            } else if (block instanceof BitBlockSeqType) {
                print((BitBlockSeqType) block);
            } else {
                print((BitBlockType) block);
            }
        }
    }

    private void print(BitBlockRefType block) {
        System.out.println(String.format("  %1$-10s> (ref)",
                block.getName()));
    }

    private void print(BitBlockType block) {
        System.out.println(String.format("  %1$-10s> %2$s(%3$s)",
                block.getName(),
                block.getDataType(),
                block.getSize()));
        if (block.getCodecPropSet() != null) {
            for (PropType prop : block.getCodecPropSet().getProp()) {
                System.out.println(String.format("   .%1$-8s= %2$s",
                        prop.getName(),
                        prop.getValue()));
            }
        }
    }
}
