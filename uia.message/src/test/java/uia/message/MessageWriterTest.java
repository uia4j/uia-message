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
import java.net.URL;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import uia.message.model.Block;
import uia.message.model.BlockList;
import uia.message.model.BlockSeq;
import uia.utils.ByteUtils;

/**
 * 
 * @author Kyle
 */
public class MessageWriterTest {

    public MessageWriterTest() {
    }

    @BeforeClass
    public static void startup() throws Exception {
        URL url = MessageDeserializerTest.class.getResource("Rcv.xml");
        System.out.println("xml:" + url);
        DataExFactory.register("Test", new File(url.toURI()));
    }

    /**
     * Test of getBlockValue method, of class Message.
     */
    @Test
    public void testRcv1() throws Exception {
        // message, structure based on definition of sammple.xml.
        BlockSeq body = new BlockSeq("dev");
        body.putSubBlock(new Block("header", new byte[] { 0x41, 0x42, 0x41, 0x43, 0x41, 0x44, 0x41, 0x45 }));
        body.putSubBlock(new Block("time", new Date()));

        BlockSeq powerStatus = new BlockSeq("powerStatus");
        powerStatus.putSubBlock(new Block("power1", -16));
        powerStatus.putSubBlock(new Block("power2", 12));
        powerStatus.putSubBlock(new Block("power3", -12.34));
        body.putSubBlock(powerStatus);

        body.putSubBlock(new Block("voltCount", 3));
        body.putSubBlock(new Block("footer", "A"));

        BlockSeq volt1 = new BlockSeq("body");
        volt1.putSubBlock(new Block("volt", 3));
        BlockSeq volt2 = new BlockSeq("body");
        volt2.putSubBlock(new Block("volt", -32));
        BlockSeq volt3 = new BlockSeq("body");
        volt3.putSubBlock(new Block("volt", 32));

        BlockList list = new BlockList("volts");
        list.getList().add(volt1);
        list.getList().add(volt2);
        list.getList().add(volt3);

        body.putSubBlock(new Block("id", 15));
        body.putSubBlock(list);

        try {
            // encode
            MessageWriter writer = DataExFactory.getFactory("Test").createWriter("Rcv1");
            byte[] data = writer.write(body);
            System.out.println(ByteUtils.toHexString(data));

            MessageReader reader = DataExFactory.getFactory("Test").createReader("Rcv1");
            body = reader.read(data);
            System.out.println("header     : " + ByteUtils.toHexString((byte[]) body.getSubBlock("header").getValue()));
            System.out.println("time       : " + body.getSubBlock("time").getValue());
            System.out.println("powerStatus: " + ByteUtils.toHexString((byte[]) body.getSubBlock("powerStatus").getValue()));
            System.out.println("power1     : " + body.getSubBlock("powerStatus").getSubBlock("power1").getValue());
            System.out.println("power2     : " + body.getSubBlock("powerStatus").getSubBlock("power2").getValue());
            System.out.println("power3     : " + body.getSubBlock("powerStatus").getSubBlock("power3").getValue());
            System.out.println("footer     : " + body.getSubBlock("footer").getValue());
            System.out.println("voltCount  : " + body.getSubBlock("voltCount").getValue());
            BlockList volts = (BlockList) body.getSubBlock("volts");
            for (BlockSeq item : volts.getList()) {
                System.out.println("volts> volt: " + item.getSubBlock("volt").getValue());
            }
            System.out.println("id         : " + body.getSubBlock("id").getValue());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
