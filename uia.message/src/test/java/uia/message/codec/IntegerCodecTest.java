/*******************************************************************************
 * Copyright 2017 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
public class IntegerCodecTest {

    public IntegerCodecTest() {
    }

    @Test
    public void testDecode() throws Exception {
        IntegerCodec codec = new IntegerCodec();
        // 11111110
        Assert.assertEquals(-2, codec.decode(new byte[] { (byte) 0xfe }, 8), 0);
        // 1111111_ => 11111111
        Assert.assertEquals(-1, codec.decode(new byte[] { (byte) 0xfe }, 7), 0);
        // 11111111 11111110
        Assert.assertEquals(-2, codec.decode(new byte[] { (byte) 0xff, (byte) 0xfe }, 16), 0);
        // 11111111 1111110_ >> 11111111 11111110
        Assert.assertEquals(-2, codec.decode(new byte[] { (byte) 0xff, (byte) 0xfd }, 15), 0);

        Assert.assertEquals(-4, codec.decode(new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xfc, (byte) 0x8f }, 24), 0);
        Assert.assertEquals(-1024, codec.decode(new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xfc, (byte) 0x00 }, 32), 0);
    }

    @Test
    public void testDecodeUnsigned() throws Exception {
        IntegerCodec codec = new IntegerCodec();
        codec.setUnsigned("Y");
        Assert.assertEquals(254, codec.decode(new byte[] { (byte) 0xfe, (byte) 0x55 }, 8), 0);
        Assert.assertEquals(127, codec.decode(new byte[] { (byte) 0xfe, (byte) 0x55 }, 7), 0);
        Assert.assertEquals(508, codec.decode(new byte[] { (byte) 0xfe, (byte) 0x00 }, 9), 0);
    }

    @Test
    public void testEncode() throws Exception {
        IntegerCodec codec = new IntegerCodec();
        // 01111111
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f }, codec.encode(127, 8));
        // _1111111 >> 11111110
        Assert.assertArrayEquals(new byte[] { (byte) 0xfe }, codec.encode(127, 7));
        Assert.assertArrayEquals(new byte[] { 0x00, (byte) 0x7f }, codec.encode(127, 16));
        // 10000000
        Assert.assertArrayEquals(new byte[] { (byte) 0x80 }, codec.encode(-128, 8));
        // _1111111 10000000 >> 11111111 00000000
        Assert.assertArrayEquals(new byte[] { (byte) 0xff, (byte) 0x00 }, codec.encode(-128, 15));
        // 11111111 10000000
        Assert.assertArrayEquals(new byte[] { (byte) 0xff, (byte) 0x80 }, codec.encode(-128, 16));
    }

    @Test
    public void testEncodeUnsigned() throws Exception {
        IntegerCodec codec = new IntegerCodec();
        codec.setUnsigned("Y");
        // 11111110
        Assert.assertArrayEquals(new byte[] { (byte) 0xfe }, codec.encode(254, 8));
        // _1111111 >> 11111110
        Assert.assertArrayEquals(new byte[] { (byte) 0xfe }, codec.encode(127, 7));
        // _______1 11111100 >> 11111110 00000000
        Assert.assertArrayEquals(new byte[] { (byte) 0xfe, (byte) 0x00 }, codec.encode(508, 9));
    }
}
