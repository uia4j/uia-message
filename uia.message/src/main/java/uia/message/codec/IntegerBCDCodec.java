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
import uia.utils.IntegerUtils;

/**
 * Convert data between BCD(Binary-coded decimal) integer and byte array. <br>
 *
 * @author Kyle
 */
public class IntegerBCDCodec implements BlockCodec<Integer> {

    @Override
    public Integer zeroValue() {
        return 0;
    }

    @Override
    public Integer decode(byte[] data, int bitLength) throws BlockCodecException {
        if (data.length * 8 != bitLength) {
            throw new BlockCodecException("integer(BCD) decode failure. bit count of " + data + " bytes is not " + bitLength + ".");
        }
        try {
            return new Integer(ByteUtils.bcdValue(data));
        }
        catch (Exception ex) {
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
