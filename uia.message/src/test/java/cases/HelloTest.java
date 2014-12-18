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
package cases;

import java.io.File;

import org.junit.Test;

import uia.message.DataExFactory;
import uia.message.MessageDeserializer;

public class HelloTest {

	@Test
	public void testRead() throws Exception {
		String domainName = "Cases";
		File schemaFile = new File(HelloTest.class.getResource("hello.xml").toURI());
		DataExFactory.register(domainName, schemaFile);

		String messageName = "Hello";
		DataExFactory f = DataExFactory.getFactory(domainName);
		MessageDeserializer reader = f.createDeserializer(messageName);

		byte[] data = new byte[] {
		        (byte) 0x41, // header: 8 bytes, value 'ABCDEFGH'
		        (byte) 0x42,
		        (byte) 0x43,
		        (byte) 0x44,
		        (byte) 0x45,
		        (byte) 0x46,
		        (byte) 0x47,
		        (byte) 0x48,
		        (byte) 0x00, // number: 4 bytes, value 1024
		        (byte) 0x00,
		        (byte) 0x04,
		        (byte) 0x00,
		        (byte) 0xff // tailer: 1 bytes, 0xff
		};
		Hello hello = (Hello) reader.read(data);
		System.out.println(new String(hello.getHeader()));
		System.out.println(hello.getNumber());
		System.out.println(hello.getTailer());
	}
}