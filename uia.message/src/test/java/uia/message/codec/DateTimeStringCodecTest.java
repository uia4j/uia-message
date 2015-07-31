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

import java.util.Date;

import org.junit.Test;

import uia.utils.ByteUtils;

/**
 * 
 * @author Kyle
 */
public class DateTimeStringCodecTest {

    public DateTimeStringCodecTest() {
    }

    @Test
    public void testDecode() throws Exception {
        DateTimeStringCodec codec = new DateTimeStringCodec();
        codec.setFormat("MMddyyyy");
        System.out.println(codec.decode(new byte[] { 0x31, 0x30, 0x31, 0x39, 0x32, 0x30, 0x31, 0x33 }, 8));
        codec.setFormat("(HHmmssyyyyMMdd)");
        System.out.println(codec.decode("(10462820080604)".getBytes(), 16));
        System.out.println();
    }

    @Test
    public void testEncode() throws Exception {
        DateTimeStringCodec codec = new DateTimeStringCodec();
        codec.setFormat("yyyyMMdd");
        System.out.println(ByteUtils.toHexString(codec.encode(new Date(), 8)));
        System.out.println();
    }

    @Test
    public void testNullable00() throws Exception {
        DateTimeStringCodec codec = new DateTimeStringCodec();
        codec.setFormat("MMddyyyy");
        codec.setNullByte("00");
        codec.setNullable("y");
        System.out.println(codec.decode(new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }, 8));
        System.out.println(ByteUtils.toHexString(codec.encode(null, 8)));
    }

    @Test
    public void testNullable20() throws Exception {
        DateTimeStringCodec codec = new DateTimeStringCodec();
        codec.setFormat("MMddyyyy");
        codec.setNullByte("20");
        codec.setNullable("y");
        System.out.println(codec.decode(new byte[] { 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20 }, 8));
        System.out.println(ByteUtils.toHexString(codec.encode(null, 8)));
    }
}
