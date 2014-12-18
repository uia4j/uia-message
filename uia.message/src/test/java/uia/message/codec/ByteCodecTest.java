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
package uia.message.codec;

import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class ByteCodecTest {

    public ByteCodecTest() {
    }

    @Test
    public void testDecode() throws Exception {
    	System.out.println("decode");
        ByteCodec codec = new ByteCodec();
        System.out.println(codec.decode(new byte[]{(byte) 0x7f, (byte)0xff}, 8));
        System.out.println(codec.decode(new byte[]{(byte) 0x7f, (byte)0xff}, 7));
        System.out.println(codec.decode(new byte[]{(byte) 0x7f, (byte)0xff}, 6));
        System.out.println(codec.decode(new byte[]{(byte) 0x7f, (byte)0xff}, 5));
        System.out.println(codec.decode(new byte[]{(byte) 0x7f, (byte)0xff}, 4));
        System.out.println(codec.decode(new byte[]{(byte) 0x7f, (byte)0xff}, 3));
        System.out.println(codec.decode(new byte[]{(byte) 0x7f, (byte)0xff}, 2));
        System.out.println(codec.decode(new byte[]{(byte) 0x7f, (byte)0xff}, 1));
        
        System.out.println(codec.decode(new byte[]{(byte) 0x80, (byte)0xff}, 8));
        System.out.println(codec.decode(new byte[]{(byte) 0x80, (byte)0xff}, 7));
        System.out.println(codec.decode(new byte[]{(byte) 0x80, (byte)0xff}, 6));
        System.out.println(codec.decode(new byte[]{(byte) 0x80, (byte)0xff}, 5));
        System.out.println(codec.decode(new byte[]{(byte) 0x80, (byte)0xff}, 4));
        System.out.println(codec.decode(new byte[]{(byte) 0x80, (byte)0xff}, 3));
        System.out.println(codec.decode(new byte[]{(byte) 0x80, (byte)0xff}, 2));
        System.out.println(codec.decode(new byte[]{(byte) 0x80, (byte)0xff}, 1));
        System.out.println();
    }

    @Test
    public void testEncode() throws Exception {
    	System.out.println("eecode");
        ByteCodec codec = new ByteCodec();
        System.out.println(codec.encode((byte) 0xff, 8)[0]);
        System.out.println(codec.encode((byte) 0x7f, 7)[0]);
        System.out.println(codec.encode((byte) 0x3f, 6)[0]);
        System.out.println(codec.encode((byte) 0x1f, 5)[0]);
        System.out.println(codec.encode((byte) 0x0f, 4)[0]);
        System.out.println(codec.encode((byte) 0x07, 3)[0]);
        System.out.println(codec.encode((byte) 0x03, 2)[0]);
        System.out.println(codec.encode((byte) 0x01, 1)[0]);
        
        System.out.println(codec.encode((byte) 0x7f, 8)[0]);
        System.out.println(codec.encode((byte) 0x3f, 7)[0]);
        System.out.println(codec.encode((byte) 0x1f, 6)[0]);
        System.out.println(codec.encode((byte) 0x0f, 5)[0]);
        System.out.println(codec.encode((byte) 0xf7, 4)[0]);
        System.out.println(codec.encode((byte) 0xf3, 3)[0]);
        System.out.println(codec.encode((byte) 0xf1, 2)[0]);
        System.out.println(codec.encode((byte) 0xf0, 1)[0]);
        System.out.println();
    }
}
