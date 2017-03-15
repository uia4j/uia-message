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
public class FloatCodecTest {

    public FloatCodecTest() {
    }

    @Test
    public void testZero() {
        FloatCodec codec = new FloatCodec();
        Assert.assertEquals(0.0f, codec.zeroValue(), 0);
    }

    @Test
    public void testDecode() throws Exception {
        byte[] data = new byte[] {
                (byte) 0x41,
                (byte) 0x44,
                (byte) 0xc4,
                (byte) 0x9c
        };
        FloatCodec codec = new FloatCodec();
        Assert.assertEquals(12.298, codec.decode(data, 8), 3);
    }

    @Test
    public void testEncode() throws Exception {
        FloatCodec codec = new FloatCodec();
        Assert.assertArrayEquals(
                new byte[] {
                        (byte) 0x41,
                        (byte) 0x44,
                        (byte) 0xc4,
                        (byte) 0x9c
                },
                codec.encode(new Float(12.298f), 8));
    }
}
