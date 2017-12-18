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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import uia.utils.ByteUtils;
import uia.utils.StringUtils;

/**
 * Convert data between string of time and byte array with specific format. <br>
 *
 * <p>
 * XML sample:
 * </p>
 *
 * <pre style="font-size:10px">
 * {@code
 * <CodecPropSet>
 *     <Prop name="format">yyyyMMddHHmmss</Prop>
 * </CodecPropSet>
 * }
 * </pre>
 *
 * @author Kyle
 */
public class DateTimeStringCodec implements BlockCodec<Date> {

    private String format;

    private boolean nullable;

    private byte nullByte;

    public DateTimeStringCodec() {
        this.format = "yyyyMMddHHmmss";
        this.nullable = false;
        this.nullByte = 0x20;
    }

    public String getNullable() {
        return Boolean.toString(this.nullable);
    }

    public void setNullable(String nullable) {
        this.nullable = StringUtils.bool(nullable);
    }

    public String getNullByte() {
        return ByteUtils.toHexString(this.nullByte);
    }

    public void setNullByte(String nullByte) {
        this.nullByte = (byte) Integer.parseInt(nullByte, 16);
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public Date zeroValue() {
        return new Date();
    }

    @Override
    public Date decode(byte[] data, int bitLength) throws BlockCodecException {
        if (this.nullable) {
            boolean isNull = true;
            for (int i = 0; i < data.length; i++) {
                if (data[i] != this.nullByte) {
                    isNull = false;
                    break;
                }
            }
            if (isNull) {
                return null;
            }
        }

        try {
            String value = new String(data);
            return new SimpleDateFormat(this.format).parse(value);
        }
        catch (Exception ex) {
            if (this.nullable) {
                return null;
            }
            throw new BlockCodecException("date decode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] encode(Date data, int bitLength) throws BlockCodecException {
        try {
            if (this.nullable && data == null) {
                byte[] result = new byte[this.format.length()];
                Arrays.fill(result, this.nullByte);
                return result;
            }
            return new SimpleDateFormat(this.format).format(data).getBytes();
        }
        catch (Exception ex) {
            throw new BlockCodecException("date encode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reset() {
        this.format = "yyyyMMddHHmmss";
        this.nullable = false;
        this.nullByte = 0x20;
    }

    @Override
    public String getValueType() {
        return "String";
    }
}
