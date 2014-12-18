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

import java.io.File;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

import uia.message.model.BlockList;
import uia.message.model.BlockSeq;
import uia.utils.ByteUtils;

/**
 * 
 * @author Kyle
 */
public class MessageReaderTest {

	public MessageReaderTest() {
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
	public void tesRcv1() throws Exception {
		byte[] data = new byte[] {
		        (byte) 0xa7, // hdaer (48)
		        (byte) 0xda,
		        (byte) 0xac,
		        (byte) 0x4f,
		        (byte) 0xbd,
		        (byte) 0xd6,
		        (byte) 0x32, // time (112)
		        (byte) 0x30,
		        (byte) 0x31,
		        (byte) 0x33,
		        (byte) 0x30,
		        (byte) 0x32,
		        (byte) 0x30,
		        (byte) 0x36,
		        (byte) 0x32,
		        (byte) 0x31,
		        (byte) 0x33,
		        (byte) 0x31,
		        (byte) 0x35,
		        (byte) 0x34,
		        (byte) 0x00, // power1 (32)
		        (byte) 0x00,
		        (byte) 0x1e,
		        (byte) 0x81,
		        (byte) 0x32, // power2 (16)
		        (byte) 0x20,
		        (byte) 0x40, // power3 (64)
		        (byte) 0x28,
		        (byte) 0x98,
		        (byte) 0x93,
		        (byte) 0x74,
		        (byte) 0xbc,
		        (byte) 0x6a,
		        (byte) 0x7f,
		        (byte) 0x02, // len (8)
		        (byte) 0x66, // footer (8)
		        (byte) 0x04, // volt (6 + 6)
		        (byte) 0xff // id (4)
		};

		try {
			MessageReader reader = DataExFactory.getFactory("Test").createReader("Rcv1");
			BlockSeq body = reader.read(data);

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
				System.out.println("  volt: " + item.getSubBlock("volt").getValue());
			}
			System.out.println("id         : " + body.getSubBlock("id").getValue());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
