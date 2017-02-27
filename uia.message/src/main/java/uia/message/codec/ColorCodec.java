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

/**
 * Convert byte[] to boolean.<br>
 * Byte array format: { red, green, blue }<br>
 *
 * @author Kyle
 */
public class ColorCodec implements BlockCodec<Color> {

    @Override
    public Color zeroValue() {
        return Color.black;
    }

    @Override
    public Color decode(byte[] data, int bitLength) throws BlockCodecException {
        return new Color(0x0ff & data[0], 0x0ff & data[1], 0x0ff & data[2]);
    }

    @Override
    public byte[] encode(Color data, int bitLength) throws BlockCodecException {
        return new byte[] { (byte) data.getRed(), (byte) data.getGreen(), (byte) data.getBlue() };
    }

    @Override
    public void reset() {
    }
}
