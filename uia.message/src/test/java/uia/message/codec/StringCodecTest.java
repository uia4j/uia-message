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

import uia.utils.ByteUtils;

/**
 * 
 * @author Kyle
 */
public class StringCodecTest {

    @Test
    public void tesEncode() throws Exception {
        System.out.println("encode");
        StringCodec codec = new StringCodec();
        try {
            System.out.println(codec.decode(new byte[] { (byte) 0xe8, (byte) 0xaa, (byte) 0xb0 }, 24));
            codec.setEncoding("GBK");
            System.out.println(codec.decode(new byte[] { (byte) 0xb3, (byte) 0xc2, (byte) 0x41, (byte) 0x42 }, 24));
            codec.setEmptyByte("00");
            System.out.println(codec.decode(new byte[] { (byte) 0xb3, (byte) 0xc2, (byte) 0x00, (byte) 0x00 }, 32));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    @Test
    public void testDecode() throws Exception {
        System.out.println("decode");
        StringCodec codec = new StringCodec();
        try {
            System.out.println(ByteUtils.toHexString(codec.encode("誰", 32)));
            codec.setEncoding("GBK");
            System.out.println(ByteUtils.toHexString(codec.encode("陈", 32)));
            codec.setEmptyByte("00");
            System.out.println(ByteUtils.toHexString(codec.encode("陈", 32)));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }
}
