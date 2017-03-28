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
public class ByteArrayCodecTest {

    @Test
    public void testZero() {
        ByteArrayCodec codec = new ByteArrayCodec();
        Assert.assertEquals(0, codec.zeroValue().length, 0);
    }

    @Test
    public void testDecode() throws Exception {
        ByteArrayCodec codec = new ByteArrayCodec();
        Assert.assertArrayEquals(new byte[] { 0x60 }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 3));
        Assert.assertArrayEquals(new byte[] { 0x70 }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 4));
        Assert.assertArrayEquals(new byte[] { 0x78 }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 5));
        Assert.assertArrayEquals(new byte[] { 0x7c }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 6));
        Assert.assertArrayEquals(new byte[] { 0x7e }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 7));
        Assert.assertArrayEquals(new byte[] { 0x7f }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 8));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0x80 }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 9));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xc0 }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 10));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xe0 }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 11));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xf0 }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 12));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xf0 }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 13));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xf0 }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 14));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xf2 }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 15));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xf3 }, codec.decode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 16));
    }

    @Test
    public void testEncode() throws Exception {
        ByteArrayCodec codec = new ByteArrayCodec();
        Assert.assertArrayEquals(new byte[] { 0x60 }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 3));
        Assert.assertArrayEquals(new byte[] { 0x70 }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 4));
        Assert.assertArrayEquals(new byte[] { 0x78 }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 5));
        Assert.assertArrayEquals(new byte[] { 0x7c }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 6));
        Assert.assertArrayEquals(new byte[] { 0x7e }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 7));
        Assert.assertArrayEquals(new byte[] { 0x7f }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 8));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0x80 }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 9));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xc0 }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 10));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xe0 }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 11));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xf0 }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 12));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xf0 }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 13));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xf0 }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 14));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xf2 }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 15));
        Assert.assertArrayEquals(new byte[] { (byte) 0x7f, (byte) 0xf3 }, codec.encode(new byte[] { (byte) 0x7f, (byte) 0xf3 }, 16));
    }
}
