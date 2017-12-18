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

import uia.utils.ByteUtils;

/**
 * Convert data between String and byte array. <br>
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
    public String zeroValue() {
        return "";
    }

    @Override
    public String decode(byte[] data, int bitLength) throws BlockCodecException {
        try {
            byte[] value = new byte[data.length];
            for (int i = 0; i < data.length; i++) {
                value[i] = data[i] != this.empty ? data[i] : 0x20;
            }
            return new String(Arrays.copyOfRange(value, 0, bitLength / 8), this.encoding).trim();
        }
        catch (Exception ex) {
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
        }
        catch (Exception ex) {
            throw new BlockCodecException("string encode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reset() {
        this.empty = 0x20;
        this.encoding = "UTF-8";
    }

    @Override
    public String getValueType() {
        return "String";
    }
}
