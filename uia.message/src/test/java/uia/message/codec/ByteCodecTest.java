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
public class ByteCodecTest {

    @Test
    public void testZero() {
        ByteCodec codec = new ByteCodec();
        Assert.assertEquals(0, codec.zeroValue(), 0);
    }

    @Test
    public void testDecode() throws Exception {
        ByteCodec codec = new ByteCodec();
        Assert.assertEquals((byte) 0x7f, codec.decode(new byte[] { (byte) 0x7f }, 8), 0);
        Assert.assertEquals((byte) 0x3f, codec.decode(new byte[] { (byte) 0x7f }, 7), 0);
        Assert.assertEquals((byte) 0x1f, codec.decode(new byte[] { (byte) 0x7f }, 6), 0);
        Assert.assertEquals((byte) 0x0f, codec.decode(new byte[] { (byte) 0x7f }, 5), 0);
        Assert.assertEquals((byte) 0x07, codec.decode(new byte[] { (byte) 0x7f }, 4), 0);
        Assert.assertEquals((byte) 0x03, codec.decode(new byte[] { (byte) 0x7f }, 3), 0);
        Assert.assertEquals((byte) 0x01, codec.decode(new byte[] { (byte) 0x7f }, 2), 0);
        Assert.assertEquals((byte) 0x00, codec.decode(new byte[] { (byte) 0x7f }, 1), 0);
    }

    @Test
    public void testEncode() throws Exception {
        ByteCodec codec = new ByteCodec();
        Assert.assertEquals((byte) 0x7f, codec.encode((byte) 0x7f, 8)[0], 0);
        Assert.assertEquals((byte) 0x7e, codec.encode((byte) 0x3f, 7)[0], 0);
        Assert.assertEquals((byte) 0x7c, codec.encode((byte) 0x1f, 6)[0], 0);
        Assert.assertEquals((byte) 0x78, codec.encode((byte) 0x0f, 5)[0], 0);
        Assert.assertEquals((byte) 0x70, codec.encode((byte) 0x07, 4)[0], 0);
        Assert.assertEquals((byte) 0x60, codec.encode((byte) 0x03, 3)[0], 0);
        Assert.assertEquals((byte) 0x40, codec.encode((byte) 0x01, 2)[0], 0);
        Assert.assertEquals((byte) 0x00, codec.encode((byte) 0x00, 1)[0], 0);
    }
}
