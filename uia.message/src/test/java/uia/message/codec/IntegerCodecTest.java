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
public class IntegerCodecTest {

    public IntegerCodecTest() {
    }

    @Test
    public void testDecode() throws Exception {
        System.out.println("decode");
        IntegerCodec codec = new IntegerCodec();
        try {
            System.out.println(codec.decode(new byte[] { (byte) 0xfe, (byte) 0x55 }, 8));
            System.out.println(codec.decode(new byte[] { (byte) 0x57, (byte) 0x44 }, 8)); // 0101 0111
            System.out.println(codec.decode(new byte[] { (byte) 0xae, (byte) 0x55 }, 7)); // 1010 1110
            System.out.println(codec.decode(new byte[] { (byte) 0xff, (byte) 0xfe, (byte) 0xfc, (byte) 0x9f }, 16));
            System.out.println(codec.decode(new byte[] { (byte) 0xff, (byte) 0xfe, (byte) 0xfc, (byte) 0x9f }, 15));
            System.out.println(codec.decode(new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xfc, (byte) 0x8f }, 24));
            System.out.println(codec.decode(new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xfc, (byte) 0x00 }, 32));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    @Test
    public void testDecodeUnsigned() throws Exception {
        System.out.println("decodeUnsigned");
        IntegerCodec codec = new IntegerCodec();
        codec.setUnsigned("Y");
        try {
            System.out.println(codec.decode(new byte[] { (byte) 0x57, (byte) 0x44 }, 8)); // 0101 0111
            System.out.println(codec.decode(new byte[] { (byte) 0xae, (byte) 0x55 }, 7)); // 1010 1110
            System.out.println(codec.decode(new byte[] { (byte) 0x8d, (byte) 0xff }, 16));
            System.out.println(codec.decode(new byte[] { (byte) 0x00, (byte) 0x03, (byte) 0xff, (byte) 0xff }, 24));
            System.out.println(codec.decode(new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x00 }, 32));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    @Test
    public void testEncode() throws Exception {
        System.out.println("encode");
        IntegerCodec codec = new IntegerCodec();
        System.out.println(ByteUtils.toBitString(codec.encode(127, 8)));
        System.out.println(ByteUtils.toBitString(codec.encode(127, 7)));
        System.out.println(ByteUtils.toBitString(codec.encode(128, 8)));
        System.out.println(ByteUtils.toBitString(codec.encode(128, 16)));
        System.out.println(ByteUtils.toBitString(codec.encode(-128, 8)));
        System.out.println(ByteUtils.toBitString(codec.encode(-128, 16)));
        System.out.println(ByteUtils.toBitString(codec.encode(-128, 16)));
        System.out.println();
    }

    @Test
    public void testEncodeUnsigned() throws Exception {
        System.out.println("encodeUnsigned");
        IntegerCodec codec = new IntegerCodec();
        codec.setUnsigned("Y");
        System.out.println(ByteUtils.toBitString(codec.encode(127, 8)));
        System.out.println(ByteUtils.toBitString(codec.encode(127, 7)));
        System.out.println(ByteUtils.toBitString(codec.encode(63, 6)));
        System.out.println(ByteUtils.toBitString(codec.encode(128, 8)));
        System.out.println(ByteUtils.toBitString(codec.encode(128, 16)));
        System.out.println(ByteUtils.toBitString(codec.encode(Integer.MAX_VALUE, 32)));
        System.out.println();
    }

    @Test
    public void test() throws Exception {
        IntegerCodec codec = new IntegerCodec(true);
        byte[] result = codec.encode(1424, 16);
        System.out.println(result[0]);
        System.out.println(result[1]);
    }

    @Test
    public void testEncodeUnsignedEx() {
        System.out.println("encodeUnsignedEx ... encode unsigned -128");
        try {
            IntegerCodec codec = new IntegerCodec();
            codec.setUnsigned("Y");
            codec.encode(-128, 8);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

}
