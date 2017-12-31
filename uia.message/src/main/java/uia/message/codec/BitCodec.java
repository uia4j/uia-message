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

/**
 * Convert data between boolean and byte array. <br>
 * Just check the highest bit in first byte. bitLength will be ignored.<br>
 *
 * @author Kyle
 */
public class BitCodec implements BlockCodec<Boolean> {

    @Override
    public Boolean zeroValue() {
        return false;
    }

    @Override
    public Boolean decode(byte[] data, int bitLength) throws BlockCodecException {
        return (data[0] & 0x80) > 0;
    }

    @Override
    public byte[] encode(Boolean data, int bitLength) throws BlockCodecException {
        return data ? new byte[] { (byte) 0x80 } : new byte[] { 0x00 };
    }

    @Override
    public void reset() {
    }

    @Override
    public String getValueType() {
        return "boolean";
    }
}
