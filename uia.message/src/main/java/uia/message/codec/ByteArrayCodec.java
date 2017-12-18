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

import java.util.Arrays;

/**
 * Convert byte array to new byte array.<br>
 *
 * @author Kyle
 */
public class ByteArrayCodec implements BlockCodec<byte[]> {

    @Override
    public byte[] zeroValue() {
        return new byte[0];
    }

    @Override
    public byte[] decode(byte[] data, int bitLength) throws BlockCodecException {
        if (bitLength < 0) {
            return data;
        }

        byte[] result = Arrays.copyOf(data, (int) Math.ceil((double) bitLength / 8));
        int off = bitLength % 8;
        if (off != 0) {
            byte zero = (byte) (0xff << (8 - off));
            result[result.length - 1] &= zero;
        }
        return result;
    }

    @Override
    public byte[] encode(byte[] data, int bitLength) throws BlockCodecException {
        if (bitLength < 0) {
            return data;
        }

        byte[] result = Arrays.copyOf(data, (int) Math.ceil((double) bitLength / 8));
        int off = bitLength % 8;
        if (off != 0) {
            byte zero = (byte) (0xff << (8 - off));
            result[result.length - 1] &= zero;
        }
        return result;
    }

    @Override
    public void reset() {
    }

    @Override
    public String getValueType() {
        return "byte[]";
    }
}
