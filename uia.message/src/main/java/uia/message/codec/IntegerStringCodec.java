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
 * Convert data between string of integer and byte array. <br>
 *
 * @author Kyle
 */
public class IntegerStringCodec implements BlockCodec<Integer> {

    @Override
    public Integer decode(byte[] data, int bitLength) throws BlockCodecException {
        try {
            return Integer.parseInt(new String(data).trim());
        }
        catch (Exception ex) {
            throw new BlockCodecException("integerString decode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public Integer zeroValue() {
        return 0;
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
        }
        catch (Exception ex) {
            throw new BlockCodecException("integerString encode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reset() {
    }
}
