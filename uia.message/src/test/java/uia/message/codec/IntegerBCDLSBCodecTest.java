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
package uia.message.codec;

import org.junit.Test;

import uia.utils.ByteUtils;

/**
 * 
 * @author Kyle
 */
public class IntegerBCDLSBCodecTest {

    public IntegerBCDLSBCodecTest() {
    }

    @Test
    public void testDecode() throws Exception {
        System.out.println("decode");
        IntegerBCDLSBCodec codec = new IntegerBCDLSBCodec();
        System.out.println(codec.decode(new byte[] { (byte) 0x14, (byte) 0x20 }, 16));
    }

    @Test
    public void testEncode() throws Exception {
        System.out.println("encode");
        IntegerBCDLSBCodec codec = new IntegerBCDLSBCodec();
        System.out.println(ByteUtils.toHexString(codec.encode(2014, 16)));
        System.out.println(ByteUtils.toHexString(codec.encode(1420, 16)));
    }

    @Test
    public void testZero() throws Exception {
        System.out.println("zero");
        IntegerBCDLSBCodec codec = new IntegerBCDLSBCodec();
        System.out.println(ByteUtils.toHexString(codec.encode(191, 24)));
        System.out.println(codec.decode(new byte[] { (byte) 0x82, (byte) 0x00 }, 16));
    }
}
