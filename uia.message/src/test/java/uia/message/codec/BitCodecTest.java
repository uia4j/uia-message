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

public class BitCodecTest {

    @Test
    public void testZero() {
        BitCodec codec = new BitCodec();
        Assert.assertFalse(codec.zeroValue());
    }

    @Test
    public void testDecode() throws BlockCodecException {
        BitCodec codec = new BitCodec();
        Assert.assertTrue(codec.decode(new byte[] { (byte) 0x88 }, 1));
        Assert.assertFalse(codec.decode(new byte[] { (byte) 0x78 }, 1));
    }

    @Test
    public void testEncode() throws BlockCodecException {
        BitCodec codec = new BitCodec();
        Assert.assertArrayEquals(new byte[] { (byte) 0x80 }, codec.encode(true, 1));
        Assert.assertArrayEquals(new byte[] { (byte) 0x00 }, codec.encode(false, 1));
    }
}
