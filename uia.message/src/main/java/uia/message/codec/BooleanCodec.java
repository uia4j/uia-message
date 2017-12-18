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

/**
 * Convert byte[] to boolean.<br>
 * Default 1: true, 0: false.<br>
 *
 * @author Kyle
 */
public class BooleanCodec implements BlockCodec<Boolean> {

    private int trueValue;

    private int falseValue;

    public BooleanCodec() {
        this.trueValue = 0x01;
        this.falseValue = 0x00;
    }

    public String getTrueValue() {
        return Integer.toString(this.trueValue);
    }

    public void setTrueValue(String value) {
        this.trueValue = Integer.parseInt(value);
    }

    public String getFalseValue() {
        return Integer.toString(this.falseValue);
    }

    public void setFalseValue(String value) {
        this.falseValue = Integer.parseInt(value);
    }

    @Override
    public Boolean zeroValue() {
        return false;
    }

    @Override
    public Boolean decode(byte[] data, int bitLength) throws BlockCodecException {
        try {
            return ByteUtils.intValue(data, bitLength, true) == this.trueValue;
        }
        catch (Exception ex) {
            throw new BlockCodecException("boolean codec failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] encode(Boolean data, int bitLength) throws BlockCodecException {
        int value = Boolean.TRUE.equals(data) ? this.trueValue : this.falseValue;
        IntegerCodec ic = new IntegerCodec(true);
        return ic.encode(value, bitLength);
    }

    @Override
    public void reset() {
        this.trueValue = 0x00;
        this.falseValue = 0x01;
    }

    @Override
    public String getValueType() {
        return "bool";
    }
}
