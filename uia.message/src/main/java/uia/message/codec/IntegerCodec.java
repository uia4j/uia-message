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

import uia.utils.BooleanUtils;
import uia.utils.ByteUtils;
import uia.utils.StringUtils;

/**
 * Convert data between integer and byte array. <br>
 *
 * @author Kyle
 */
public class IntegerCodec implements BlockCodec<Integer> {

    private final boolean orig;

    private boolean unsigned;

    public IntegerCodec() {
        this(false);
    }

    public IntegerCodec(boolean unsigned) {
        this.unsigned = unsigned;
        this.orig = this.unsigned;
    }

    public void setUnsigned(String yn) {
        this.unsigned = StringUtils.bool(yn);
    }

    public String getUnsigned() {
        return BooleanUtils.toYN(this.unsigned);
    }

    @Override
    public Integer zeroValue() {
        return 0;
    }

    @Override
    public Integer decode(byte[] data, int bitLength) throws BlockCodecException {
        try {
            return new Integer(ByteUtils.intValue(data, bitLength, this.unsigned));
        }
        catch (Exception ex) {
            throw new BlockCodecException("integer decode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] encode(Integer data, int bitLength) throws BlockCodecException {
        int value = data.intValue();
        if (this.unsigned && value < 0) {
            throw new BlockCodecException("integer(unsigned) encode failure. value(" + value + ") is less than zero.");
        }
        try {
            byte[] temp = new byte[] {
                    (byte) (value >> 24),
                    (byte) (value >> 16),
                    (byte) (value >> 8),
                    (byte) value
            };

            int byteStart = 4 - (int) Math.ceil((double) bitLength / 8);
            int bitStart = bitLength % 8;
            if (bitStart != 0) {
                bitStart = 8 - bitStart;
            }
            return ByteUtils.copyBits(temp, byteStart, bitStart, bitLength);
        }
        catch (Exception ex) {
            throw new BlockCodecException("integer encode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reset() {
        this.unsigned = this.orig;
    }
}
