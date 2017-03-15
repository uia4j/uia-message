/*******************************************************************************
 * Copyright 2017 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.message.codec;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class IntegerLSBCodecTest {

    public IntegerLSBCodecTest() {
    }

    @Test
    public void testZero() {
        IntegerLSBCodec codec = new IntegerLSBCodec();
        Assert.assertEquals(0, codec.zeroValue(), 0);
    }

    @Test
    public void testDecode() throws Exception {
        IntegerLSBCodec codec = new IntegerLSBCodec();
        // 1111111_ >> 11111111
        Assert.assertEquals(-1, codec.decode(new byte[] { (byte) 0xfe }, 7), 0);
        // 11111110 11111111 >> 11111111 11111110
        Assert.assertEquals(-2, codec.decode(new byte[] { (byte) 0xfe, (byte) 0xff }, 16), 0);
        // 00000001 11______ >> 00000001 11000000 >> 11000000 00000001 >> ______11 00000001
        Assert.assertEquals(-255, codec.decode(new byte[] { (byte) 0x01, (byte) 0xcf }, 10), 0);

        Assert.assertEquals(-4, codec.decode(new byte[] { (byte) 0xfc, (byte) 0xff, (byte) 0xff }, 24), 0);
    }

    @Test
    public void testDecodeUnsigned() throws Exception {
        IntegerLSBCodec codec = new IntegerLSBCodec();
        codec.setUnsigned("Y");
        Assert.assertEquals("Y", codec.getUnsigned());

        // 1111111_ >> 01111111
        Assert.assertEquals(127, codec.decode(new byte[] { (byte) 0xfe }, 7), 0);
        // 11111110 00000000 >> 00000000 11111110
        Assert.assertEquals(254, codec.decode(new byte[] { (byte) 0xfe, (byte) 0x00 }, 16), 0);
        // 00000001 11______ >> 00000001 11000000 >> 11000000 00000001 >> ______11 00000001
        Assert.assertEquals(512 + 256 + 1, codec.decode(new byte[] { (byte) 0x01, (byte) 0xcf }, 10), 0);
    }

    @Test
    public void testEncode() throws Exception {
        IntegerLSBCodec codec = new IntegerLSBCodec();
        Assert.assertArrayEquals(new byte[] { (byte) 0xfe }, codec.encode(-1, 7));
        Assert.assertArrayEquals(new byte[] { (byte) 0xfe, (byte) 0xff }, codec.encode(-2, 16));
        // 11111111 00000001 >> ______11 00000001 >> 11________ 00000001 >> 00000001 11________
        Assert.assertArrayEquals(new byte[] { (byte) 0x01, (byte) 0xc0 }, codec.encode(-255, 10));

        Assert.assertArrayEquals(new byte[] { (byte) 0xfc, (byte) 0xff, (byte) 0xff }, codec.encode(-4, 24));
    }

    @Test
    public void testEncodeUnsigned() throws Exception {
        IntegerLSBCodec codec = new IntegerLSBCodec(true);
        Assert.assertEquals("Y", codec.getUnsigned());

        Assert.assertArrayEquals(new byte[] { (byte) 0xfe }, codec.encode(127, 7));
        Assert.assertArrayEquals(new byte[] { (byte) 0xfe, (byte) 0x00 }, codec.encode(254, 16));
        // 00000011 00000001 >> ______11 00000001 >> 11________ 00000001 >> 00000001 11________
        Assert.assertArrayEquals(new byte[] { (byte) 0x01, (byte) 0xc0 }, codec.encode(512 + 256 + 1, 10));
    }
}
