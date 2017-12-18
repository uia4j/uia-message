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
public class IntegerLSBCodec implements BlockCodec<Integer> {

    private final boolean orig;

    private boolean unsigned;

    public IntegerLSBCodec() {
        this(false);
    }

    public IntegerLSBCodec(boolean unsigned) {
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
            byte[] value = ByteUtils.copyBits(data, 0, bitLength);
            value = ByteUtils.reverse(value);
            int bitStart = bitLength % 8;
            if (bitStart != 0) {
                bitStart = 8 - bitStart;
                value[0] = (byte) (value[0] >>> bitStart);
                value = ByteUtils.copyBits(value, 0, bitStart, bitLength);
            }
            return new Integer(ByteUtils.intValue(value, bitLength, this.unsigned));
        }
        catch (Exception ex) {
            throw new BlockCodecException("integer(LSB) decode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] encode(Integer data, int bitLength) throws BlockCodecException {
        int value = data.intValue();
        if (this.unsigned && value < 0) {
            throw new BlockCodecException("integer(LSB,unsigned) encode failure. value(" + value + ") is less than zero.");
        }
        try {
            byte[] temp = this.unsigned ?
                    new byte[] {
                            (byte) value,
                            (byte) (value >>> 8),
                            (byte) (value >>> 16),
                            (byte) (value >>> 24) } :
                    new byte[] {
                            (byte) value,
                            (byte) (value >> 8),
                            (byte) (value >> 16),
                            (byte) (value >> 24) };

            int byteStart = bitLength / 8;
            int bitStart = bitLength % 8;
            if (bitStart != 0) {
                bitStart = 8 - bitStart;
            }
            if (byteStart < 4) {
                temp[byteStart] = (byte) (temp[byteStart] << bitStart);
            }
            return ByteUtils.copyBits(temp, 0, bitLength);
        }
        catch (Exception ex) {
            throw new BlockCodecException("integer(LSB) encode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reset() {
        this.unsigned = this.orig;
    }

    @Override
    public String getValueType() {
        return "int";
    }
}
