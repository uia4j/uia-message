/*******************************************************************************
 * * Copyright (c) 2014, UIA
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

import uia.utils.ByteUtils;
import uia.utils.IntegerUtils;

/**
 * 
 * @author Kyle
 */
public class IntegerBCDCodec implements BlockCodec<Integer> {

    public IntegerBCDCodec() {
    }

    @Override
    public Integer decode(byte[] data, int bitLength) throws BlockCodecException {
        if (data.length * 8 != bitLength) {
            throw new BlockCodecException("integer(BCD) decode failure. bit count of " + data + " bytes is not " + bitLength + ".");
        }
        try {
            return new Integer(ByteUtils.bcdValue(data));
        } catch (Exception ex) {
            throw new BlockCodecException("integer(BCD) decode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] encode(Integer data, int bitLength) throws BlockCodecException {
        byte[] value = IntegerUtils.bcdValue(data);

        int cnt = bitLength / 8;
        if (value.length > cnt) {
            throw new BlockCodecException("integer(BCD) encode failure. bit count of '" + data + "' is not " + bitLength + ".");
        }

        byte[] result = new byte[cnt];
        int offset = cnt - value.length;
        for (int i = 0; i < value.length; i++) {
            result[offset + i] = value[i];
        }
        return result;
    }

    @Override
    public void reset() {
    }
}
