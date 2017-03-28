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
 * Convert data between long and byte array. <br>
 *
 * @author Kyle
 */
public class LongCodec implements BlockCodec<Long> {

    @Override
    public Long zeroValue() {
        return 0L;
    }

    @Override
    public Long decode(byte[] data, int bitLength) throws BlockCodecException {
        try {
            return new Long(ByteUtils.longValue(data, bitLength));
        }
        catch (Exception ex) {
            throw new BlockCodecException("long decode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] encode(Long data, int bitLength) throws BlockCodecException {
        long value = data.longValue();
        try {
            byte[] temp = new byte[] {
                    (byte) (value >> 56),
                    (byte) (value >> 48),
                    (byte) (value >> 40),
                    (byte) (value >> 32),
                    (byte) (value >> 24),
                    (byte) (value >> 16),
                    (byte) (value >> 8),
                    (byte) value
            };

            int byteStart = 8 - (int) Math.ceil((double) bitLength / 8);
            int bitStart = bitLength % 8;
            if (bitStart != 0) {
                bitStart = 8 - bitStart;
            }
            return ByteUtils.copyBits(temp, byteStart, bitStart, bitLength);
        }
        catch (Exception ex) {
            throw new BlockCodecException("long encode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reset() {

    }
}
