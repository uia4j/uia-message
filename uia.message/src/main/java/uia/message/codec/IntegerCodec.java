/*******************************************************************************
 * * Copyright (c) 2015, UIA
 * * All rights reserved.
 * * Redistribution and use in source and binary forms, with or without
 * * modification, are permitted provided that the following conditions are met:
 * *
 * *     * Redistributions of source code must retain the above copyright
 * *       notice, this list of conditions and the following disclaimer.
 * *     * Redistributions in binary form must reproduce the above copyright
 * *       notice, this list of conditions and the following disclaimer in the
 * *       documentation and/or other materials provided with the distribution.
 * *     * Neither the name of the {company name} nor the
 * *       names of its contributors may be used to endorse or promote products
 * *       derived from this software without specific prior written permission.
 * *
 * * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS" AND ANY
 * * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package uia.message.codec;

import uia.utils.BooleanUtils;
import uia.utils.ByteUtils;
import uia.utils.StringUtils;

/**
 * Convert data between integer and byte array. <br />
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
    public Integer decode(byte[] data, int bitLength) throws BlockCodecException {
        try {
            return new Integer(ByteUtils.intValue(data, bitLength, this.unsigned));
        } catch (Exception ex) {
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
        } catch (Exception ex) {
            throw new BlockCodecException("integer encode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reset() {
        this.unsigned = this.orig;
    }
}
