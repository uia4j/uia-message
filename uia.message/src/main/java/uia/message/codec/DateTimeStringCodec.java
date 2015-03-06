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
        } catch (Exception ex) {
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
        } catch (Exception ex) {
            throw new BlockCodecException("date encode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reset() {
        this.format = "yyyyMMddHHmmss";
        this.nullable = false;
        this.nullByte = 0x20;
    }
}
