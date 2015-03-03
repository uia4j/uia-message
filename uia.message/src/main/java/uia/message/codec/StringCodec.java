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

import uia.utils.ByteUtils;

/**
 * 
 * @author Kyle
 */
public class StringCodec implements BlockCodec<String> {

    private String encoding;

    private byte empty;

    public StringCodec() {
        this.encoding = "UTF-8";
        this.empty = 0x20;
    }

    public String getEmptyByte() {
        return ByteUtils.toHexString(this.empty);
    }

    public void setEmptyByte(String empty) {
        this.empty = (byte) Integer.parseInt(empty, 16);
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String decode(byte[] data, int bitLength) throws BlockCodecException {
        try {
            byte[] value = new byte[data.length];
            for (int i = 0; i < data.length; i++) {
                value[i] = data[i] != this.empty ? data[i] : 0x20;
            }
            return new String(Arrays.copyOfRange(value, 0, bitLength / 8), this.encoding).trim();
        } catch (Exception ex) {
            throw new BlockCodecException("string decode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] encode(String data, int bitLength) throws BlockCodecException {
        try {
            byte[] str = data == null ? new byte[0] : data.getBytes(this.encoding);
            byte[] result = new byte[bitLength / 8];

            Arrays.fill(result, this.empty);
            System.arraycopy(str, 0, result, 0, Math.min(str.length, result.length));
            return result;
        } catch (Exception ex) {
            throw new BlockCodecException("string encode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reset() {
        this.empty = 0x20;
        this.encoding = "UTF-8";
    }
}
