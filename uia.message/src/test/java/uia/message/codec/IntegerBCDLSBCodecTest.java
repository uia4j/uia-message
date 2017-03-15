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
public class IntegerBCDLSBCodecTest {

    public IntegerBCDLSBCodecTest() {
    }

    @Test
    public void testZero() {
        IntegerBCDLSBCodec codec = new IntegerBCDLSBCodec();
        Assert.assertEquals(0, codec.zeroValue(), 0);
    }

    @Test
    public void testDecode() throws Exception {
        IntegerBCDLSBCodec codec = new IntegerBCDLSBCodec();
        Assert.assertEquals(182, codec.decode(new byte[] { (byte) 0x82, (byte) 0x01 }, 16), 0);
        Assert.assertEquals(9182, codec.decode(new byte[] { (byte) 0x82, (byte) 0x91 }, 16), 0);
        Assert.assertEquals(82, codec.decode(new byte[] { (byte) 0x82, (byte) 0x00 }, 16), 0);
        Assert.assertEquals(9100, codec.decode(new byte[] { (byte) 0x00, (byte) 0x91 }, 16), 0);
    }

    @Test
    public void testEncode() throws Exception {
        IntegerBCDLSBCodec codec = new IntegerBCDLSBCodec();
        Assert.assertArrayEquals(new byte[] { (byte) 0x82, (byte) 0x01 }, codec.encode(182, 16));
        Assert.assertArrayEquals(new byte[] { (byte) 0x82, (byte) 0x91 }, codec.encode(9182, 16));
        Assert.assertArrayEquals(new byte[] { (byte) 0x82, (byte) 0x00 }, codec.encode(82, 16));
        Assert.assertArrayEquals(new byte[] { (byte) 0x00, (byte) 0x91 }, codec.encode(9100, 16));
    }
}
