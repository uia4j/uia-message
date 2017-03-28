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
public class DoubleCodecTest {

    @Test
    public void testZero() {
        DoubleCodec codec = new DoubleCodec();
        Assert.assertEquals(0.0d, codec.zeroValue(), 0);
    }

    @Test
    public void testDecode() throws Exception {
        byte[] data = new byte[] {
                (byte) 0x40,
                (byte) 0x28,
                (byte) 0x98,
                (byte) 0x92,
                (byte) 0x25,
                (byte) 0x31,
                (byte) 0x11,
                (byte) 0xf1
        };
        DoubleCodec codec = new DoubleCodec();
        Assert.assertEquals(12.29799, codec.decode(data, 8), 5);
    }

    @Test
    public void testEncode() throws Exception {
        DoubleCodec codec = new DoubleCodec();
        Assert.assertArrayEquals(
                new byte[] {
                        (byte) 0x40,
                        (byte) 0x28,
                        (byte) 0x98,
                        (byte) 0x92,
                        (byte) 0x25,
                        (byte) 0x31,
                        (byte) 0x11,
                        (byte) 0xf1
                },
                codec.encode(new Double(12.29799d), 8));
    }
}
