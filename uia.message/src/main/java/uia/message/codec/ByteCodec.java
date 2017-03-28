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

import uia.utils.ByteUtils;

/**
 * Convert data between byte and byte array.<br>
 *
 * @author Kyle
 */
public class ByteCodec implements BlockCodec<Byte> {

    @Override
    public Byte zeroValue() {
        return 0x00;
    }

    @Override
    public Byte decode(byte[] data, int bitLength) throws BlockCodecException {
        int _bitLength = Math.min(8, bitLength);
        byte result = ByteUtils.valueLeft(data[0], _bitLength);
        result = (byte) (result >> (8 - _bitLength));
        return result;
    }

    @Override
    public byte[] encode(Byte data, int bitLength) throws BlockCodecException {
        int _bitLength = Math.min(8, bitLength);
        byte result = ByteUtils.valueRight(data.byteValue(), 8 - _bitLength);
        result = (byte) (result << (8 - _bitLength));
        return new byte[] { result };
    }

    @Override
    public void reset() {
    }
}
