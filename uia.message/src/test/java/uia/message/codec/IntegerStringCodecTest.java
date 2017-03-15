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
public class IntegerStringCodecTest {

    @Test
    public void testZero() {
        IntegerStringCodec codec = new IntegerStringCodec();
        Assert.assertEquals(0, codec.zeroValue(), 0);
    }

    @Test
    public void testDecode() throws Exception {
        IntegerStringCodec codec = new IntegerStringCodec();
        Assert.assertEquals(123, codec.decode("123".getBytes(), 24), 0);
        Assert.assertEquals(13, codec.decode("013".getBytes(), 24), 0);
        Assert.assertEquals(3, codec.decode("003".getBytes(), 24), 0);
    }

    @Test
    public void testEncode() throws Exception {
        IntegerStringCodec codec = new IntegerStringCodec();
        Assert.assertArrayEquals(new byte[] { 0x31, 0x32, 0x33 }, codec.encode(123, 24));
        Assert.assertArrayEquals(new byte[] { 0x30, 0x32, 0x33 }, codec.encode(23, 24));
        Assert.assertArrayEquals(new byte[] { 0x30, 0x30, 0x33 }, codec.encode(3, 24));
    }
}
