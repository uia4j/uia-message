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
public class StringCodecTest {

    @Test
    public void testZero() {
        StringCodec codec = new StringCodec();
        Assert.assertEquals("", codec.zeroValue());
    }

    @Test
    public void tesEncode() throws Exception {
        StringCodec codec = new StringCodec();
        // UTF8
        Assert.assertEquals("誰", codec.decode(new byte[] { (byte) 0xe8, (byte) 0xaa, (byte) 0xb0 }, 24));
        // GBK
        codec.setEncoding("GBK");
        Assert.assertEquals("陈A", codec.decode(new byte[] { (byte) 0xb3, (byte) 0xc2, (byte) 0x41, (byte) 0x42 }, 24));
        // SPACE char
        codec.setEmptyByte("00");
        Assert.assertEquals("陈", codec.decode(new byte[] { (byte) 0xb3, (byte) 0xc2, (byte) 0x00, (byte) 0x00 }, 32));
    }

    @Test
    public void testDecode() throws Exception {
        StringCodec codec = new StringCodec();
        // UTF8
        Assert.assertArrayEquals(new byte[] { (byte) 0xe8, (byte) 0xaa, (byte) 0xb0, 0x20 }, codec.encode("誰", 32));
        // GBK
        codec.setEncoding("GBK");
        Assert.assertArrayEquals(new byte[] { (byte) 0xb3, (byte) 0xc2, (byte) 0x20, 0x20 }, codec.encode("陈", 32));
        // SPACE char => 0x00
        codec.setEmptyByte("00");
        Assert.assertArrayEquals(new byte[] { (byte) 0xb3, (byte) 0xc2, (byte) 0x00, 0x00 }, codec.encode("陈", 32));
    }
}
