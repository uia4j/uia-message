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

public class BooleanCodecTest {

    @Test
    public void testZero() {
        BooleanCodec codec = new BooleanCodec();
        Assert.assertFalse(codec.zeroValue());
    }

    @Test
    public void testDecode() throws BlockCodecException {
        BooleanCodec codec = new BooleanCodec();

        codec.setTrueValue("0");
        Assert.assertEquals("0", codec.getTrueValue());
        codec.setFalseValue("1");
        Assert.assertEquals("1", codec.getFalseValue());
        Assert.assertEquals(false, codec.decode(new byte[] { (byte) 0xbf }, 1));
        Assert.assertEquals(false, codec.decode(new byte[] { (byte) 0x3f }, 3));
        Assert.assertEquals(false, codec.decode(new byte[] { (byte) 0x02 }, 7));
        Assert.assertEquals(true, codec.decode(new byte[] { (byte) 0x01 }, 7));

        codec.reset();

        codec.setTrueValue("1");
        Assert.assertEquals("1", codec.getTrueValue());
        codec.setFalseValue("0");
        Assert.assertEquals("0", codec.getFalseValue());
        // 10111111
        Assert.assertEquals(true, codec.decode(new byte[] { (byte) 0xbf }, 1));
        // 00111111
        Assert.assertEquals(true, codec.decode(new byte[] { (byte) 0x3f }, 3));
        // 00000010
        Assert.assertEquals(true, codec.decode(new byte[] { (byte) 0x02 }, 7));
        // 00000001
        Assert.assertEquals(false, codec.decode(new byte[] { (byte) 0x01 }, 7));
    }

    @Test
    public void testEncode() throws BlockCodecException {
        BooleanCodec codec = new BooleanCodec();
        Assert.assertEquals((byte) 0x80, codec.encode(true, 1)[0]);
        Assert.assertEquals((byte) 0x40, codec.encode(true, 2)[0]);
        Assert.assertEquals((byte) 0x20, codec.encode(true, 3)[0]);
        Assert.assertEquals((byte) 0x10, codec.encode(true, 4)[0]);
        Assert.assertEquals((byte) 0x08, codec.encode(true, 5)[0]);
        Assert.assertEquals((byte) 0x04, codec.encode(true, 6)[0]);
        Assert.assertEquals((byte) 0x02, codec.encode(true, 7)[0]);
        Assert.assertEquals((byte) 0x01, codec.encode(true, 8)[0]);
    }
}
