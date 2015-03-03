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

import java.util.Arrays;

/**
 * Convert data between string of integer and byte array. <br />
 * 
 * @author Kyle
 */
public class IntegerStringCodec implements BlockCodec<Integer> {

    @Override
    public Integer decode(byte[] data, int bitLength) throws BlockCodecException {
        try {
            return Integer.parseInt(new String(data).trim());
        } catch (Exception ex) {
            throw new BlockCodecException("integerString decode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] encode(Integer data, int bitLength) throws BlockCodecException {
        try {
            if (data == null) {
                data = 0;
            }
            byte[] str = data.toString().getBytes();
            byte[] result = new byte[bitLength / 8];
            int len = Math.min(str.length, result.length);

            Arrays.fill(result, (byte) '0');
            System.arraycopy(str, 0, result, result.length - len, len);
            return result;
        } catch (Exception ex) {
            throw new BlockCodecException("integerString encode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reset() {
    }
}
