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
public class LongCodecTest {

    public LongCodecTest() {
    }

    @Test
    public void testZero() {
        LongCodec codec = new LongCodec();
        Assert.assertEquals(0, codec.zeroValue(), 0);
    }

    @Test
    public void testDecode() throws Exception {
        LongCodec codec = new LongCodec();
        // 11111110
        Assert.assertEquals(-2, codec.decode(new byte[] { (byte) 0xfe }, 8), 0);
        // 1111111_ => 11111111
        Assert.assertEquals(-1, codec.decode(new byte[] { (byte) 0xfe }, 7), 0);
        // 11111111 11111110
        Assert.assertEquals(-2, codec.decode(new byte[] { (byte) 0xff, (byte) 0xfe }, 16), 0);
        // 11111111 1111110_ >> 11111111 11111110
        Assert.assertEquals(-2, codec.decode(new byte[] { (byte) 0xff, (byte) 0xfd }, 15), 0);

        Assert.assertEquals(-4, codec.decode(new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xfc }, 24), 0);
        Assert.assertEquals(-1024, codec.decode(new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xfc, (byte) 0x00 }, 32), 0);
    }

    @Test
    public void testEncode() throws Exception {
        LongCodec codec = new LongCodec();
        // 01111111
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f }, codec.encode(127L, 8));
        // _1111111 >> 11111110
        Assert.assertArrayEquals(new byte[] { (byte) 0xfe }, codec.encode(127L, 7));
        Assert.assertArrayEquals(new byte[] { 0x00, (byte) 0x7f }, codec.encode(127L, 16));
        // 10000000
        Assert.assertArrayEquals(new byte[] { (byte) 0x80 }, codec.encode(-128L, 8));
        // _1111111 10000000 >> 11111111 00000000
        Assert.assertArrayEquals(new byte[] { (byte) 0xff, (byte) 0x00 }, codec.encode(-128L, 15));
        // 11111111 10000000
        Assert.assertArrayEquals(new byte[] { (byte) 0xff, (byte) 0x80 }, codec.encode(-128L, 16));
    }
}
