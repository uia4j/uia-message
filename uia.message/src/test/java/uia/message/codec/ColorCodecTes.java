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

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

public class ColorCodecTes {

    @Test
    public void testZero() {
        ColorCodec codec = new ColorCodec();
        Assert.assertEquals(Color.black, codec.zeroValue());
    }

    @Test
    public void testDecode() throws BlockCodecException {
        ColorCodec codec = new ColorCodec();
        Color color = codec.decode(new byte[] { (byte) 0xff, 0x00, 0x00 }, 24);
        Assert.assertEquals(Color.red, color);
    }

    @Test
    public void testEncode() throws BlockCodecException {
        ColorCodec codec = new ColorCodec();
        byte[] rgb = codec.encode(Color.blue, 24);
        Assert.assertArrayEquals(new byte[] { 0x00, 0x00, (byte) 0xff }, rgb);
    }
}
