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
package uia.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Kyle
 */
public class ByteUtils {

    /**
     * Reverse byte index.
     * @param data Data.
     * @return Result.
     */
    public static byte[] reverse(byte[] data) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[data.length - i - 1];
        }
        return result;
    }

    /**
     * Convert byte to BCD integer.
     * @param value Data
     * @return Result.
     */
    public static int bcdValue(byte value) {
        return 10 * (0x0f & (value >> 4)) + (0x0f & value);
    }

    /**
     * Convert bytes to BCD integer.
     * @param value Data
     * @return Result.
     */
    public static int bcdValue(byte[] value) {
        int result = 0;
        for (int i = 0; i < value.length; i++) {
            result *= 100;
            result += bcdValue(value[i]);
        }
        return result;
    }

    /**
     * Convert bytes to short.
     * @param value Data
     * @return Result.
     */
    public static short shortValue(byte[] data) {
        int len = (0x00ff & data[0]);
        if (data.length > 1) {
            len = len << 8;
            len += (0x00ff & data[1]);
        }
        return (short) len;
    }

    /**
     * Convert bytes to unsigned integer.
     * @param value Data
     * @param bitLength Bit length.
     * @return Result
     */
    public static int uintValue(byte[] value, int bitLength) {
        return intValue(value, bitLength, true);
    }

    /**
     * Convert bytes to integer.
     * @param value Data
     * @param bitLength Bit length.
     * @return Result
     */
    public static int intValue(byte[] value, int bitLength) {
        return intValue(value, bitLength, false);
    }

    /**
     * Convert bytes to integer.
     * @param value Data
     * @param bitLength Bit length.
     * @param unsigned Unsigned or not.
     * @return Result.
     */
    public static int intValue(byte[] value, int bitLength, boolean unsigned) {
        int bc = (int) Math.ceil((double) bitLength / 8);
        long temp = unsigned ? (long) (0x00ff & value[0]) : (long) value[0];
        for (int i = 1; i < bc; i++) {
            temp <<= 8;
            temp |= (0x00ff & value[i]);
        }

        int endBit = (bitLength % 8);
        if (endBit != 0) {
            temp >>= (8 - endBit);
        }

        if (unsigned && temp > Integer.MAX_VALUE) {
            throw new java.lang.IllegalArgumentException("value(" + temp + ") is greater of " + Integer.MAX_VALUE);
        }

        return (int) temp;
    }

    /**
     * Convert bytes to long.
     * @param value Data
     * @param bitLength Bit length.
     * @return Result.
     */
    public static long longValue(byte[] value, int bitLength) {
        int bc = (int) Math.ceil((double) bitLength / 8);
        long temp = value[0];
        for (int i = 1; i < bc; i++) {
            temp <<= 8;
            temp |= (0x00ff & value[i]);
        }

        int endBit = (bitLength % 8);
        if (endBit != 0) {
            temp >>= (8 - endBit);
        }

        return temp;
    }

    /**
     * Copy bits from original bytes source.
     * @param data original bytes source.
     * @param byteStart the start index of bytes.
     * @param bitLength the bit count to be retrieved.
     * @return Result.
     */
    public static byte[] copyBits(byte[] data, int byteStart, int bitLength) {
        return copyBits(data, byteStart, 0, bitLength);
    }

    /**
     * Copy bits from original bytes source.
     * @param data original bytes source.
     * @param byteStart the start index of bytes.
     * @param bitStart the start index of bits.
     * @param bitLength the bit count to be retrieved.
     * @return Result.
     */
    public static byte[] copyBits(byte[] data, int byteStart, int bitStart, int bitLength) {
        int byteCount = (int) Math.ceil((double) bitLength / 8);
        byte[] result = new byte[byteCount];

        for (int i = 0; i < byteCount; i++) {
            if (byteStart + i >= data.length) {
                break;
            }

            if (bitStart == 0) {
                result[i] = data[byteStart + i];
            } else {
                result[i] = (byte) (ByteUtils.valueRight(data[byteStart + i], bitStart) << bitStart);
                if (byteStart + i + 1 < data.length) {
                    int right = ByteUtils.valueLeft(data[byteStart + i + 1], bitStart);
                    right = (byte) ((0x00ff & right) >>> (8 - bitStart));
                    result[i] |= right;
                }
            }
        }

        int bitEnd = bitLength % 8;
        if (bitEnd != 0) {
            result[byteCount - 1] = valueLeft(result[byteCount - 1], bitEnd);
        }

        return result;
    }

    /**
     * ex. data=[0x73] (01110011), offset=3 >> 00001110 01100000
     * 
     * @param data
     * @param bitOffset
     * @return
     */
    public static byte[] offsetBits(byte[] data, int bitOffset) {
        return offsetBits(data, bitOffset, 8 * data.length);
    }

    /**
     * ex. data=[0x73] (01110011), offset=3, length=7 >> 00001110 01000000
     * 
     * @param data
     * @param bitOffset
     * @param bitLength
     * @return
     */
    public static byte[] offsetBits(byte[] data, int bitOffset, int bitLength) {
        int byteCount = (int) Math.ceil((double) (bitOffset + bitLength) / 8);
        byte[] bytes = new byte[byteCount];

        for (int i = 0; i < byteCount; i++) {
            if (i >= data.length) {
                break;
            }

            if (bitOffset == 0) {
                bytes[i] = data[i];
            } else {
                bytes[i] |= (byte) ((0x00ff & data[i]) >>> bitOffset);
                if (i + 1 < byteCount) {
                    bytes[i + 1] = (byte) (data[i] << (8 - bitOffset));
                }
            }
        }

        int bitEnd = (int) Math.ceil((double) (bitOffset + bitLength) % 8);
        if (bitEnd != 0) {
            bytes[byteCount - 1] = valueLeft(bytes[byteCount - 1], bitEnd);
        }

        return bytes;
    }

    /**
     * 
     * @param data
     * @param from
     * @param length
     * @return
     */
    public static String toString(byte[] data, int from, int length) {
        return new String(Arrays.copyOfRange(data, from, from + length));
    }

    public static String toHexString(byte data) {
        return String.format("%02x", data & 0xff);
    }

    /**
     * ex. data=[0xf3,0x82] >> "f3-82"
     * 
     * @param data byte array.
     * @return hex string.
     */
    public static String toHexString(byte[] data) {
        return toHexString(data, "-");
    }

    /**
     * ex. data=[0xf3,0x82] >> "f3-82"
     * 
     * @param data byte array.
     * @param max Max bytes converted to string.
     * @return hex string.
     */
    public static String toHexString(byte[] data, int max) {
        return toHexString(data, "-", max);
    }

    /**
     * ex. data=[0xf3,0x82], split=";" >> "f3;82"
     * 
     * @param data byte array.
     * @param split split string.
     * @return
     */
    public static String toHexString(byte[] data, String split) {
        if (data == null || data.length == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(toHexString(data[0]));
        for (int i = 1; i < data.length; i++) {
            result.append(split).append(toHexString(data[i]));
        }
        return result.toString();
    }

    /**
     * ex. data=[0xf3,0x82], split=";" >> "f3;82"
     * 
     * @param data byte array.
     * @param split split string.
     * @param max Max bytes converted to string.
     * @return hex string.
     */
    public static String toHexString(byte[] data, String split, int max) {
        if (data == null || data.length == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(toHexString(data[0]));
        for (int i = 1; i < Math.min(max, data.length); i++) {
            result.append(split).append(toHexString(data[i]));
        }
        if(data.length > max) {
            result.append(" ... total:" + data.length);
        }
        return result.toString();
    }

    /**
     * ex. data=0x83 >> "10000011"
     * 
     * @param data one byte.
     * @return
     */
    public static String toBitString(byte data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if ((data & (0x80 >>> i)) == 0) {
                result.append("0");
            } else {
                result.append("1");
            }
        }
        return result.toString();
    }

    /**
     * ex. data=[0x82,0xf0] >> "1000001011110000"
     * 
     * @param data byte array.
     * @return
     */
    public static String toBitString(byte[] data) {
        StringBuilder result = new StringBuilder();
        for (byte value : data) {
            result.append(toBitString(value)).append(" ");
        }
        return result.toString().trim();
    }

    /**
     * ex. data=0xf0, pos=3 >> 0x10
     * 
     * @param data
     * @param pos
     * @return
     */
    public static byte valueRight(byte data, int pos) {
        return (byte) (data & (0xff >>> pos));
    }

    /**
     * ex. data=0xf0, pos=3 >> 0xe0
     * 
     * @param data
     * @param pos
     * @return
     */
    public static byte valueLeft(byte data, int pos) {
        return (byte) (data & ~(0xff >>> pos));
    }

    public static byte[] toArray(List<Byte> data) {
        byte[] result = new byte[data.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = data.get(i);
        }
        return result;
    }

    public static void add(List<Byte> result, byte[] data) {
        for (int i = 0; i < data.length; i++) {
            result.add(data[i]);
        }
    }

    public static void add(List<Byte> result, byte b, int count) {
        for (int i = 0; i < count; i++) {
            result.add(b);
        }
    }

    public static byte[] copy(byte[] data, int from) {
        return copy(data, from, data.length - from, (byte) 0x00);
    }

    public static byte[] copy(byte[] data, int from, int length) {
        return copy(data, from, length, (byte) 0x00);
    }

    public static byte[] copy(byte[] data, int from, int length, byte empty) {
        byte[] result = new byte[length];
        Arrays.fill(result, empty);
        int len = Math.min(length, data.length - from);
        for (int i = 0; i < len; i++) {
            result[i] = data[from + i];
        }
        return result;
    }

    public static boolean compare(byte[] source, byte[] target) {
        if (source == null || target == null || source.length != target.length) {
            return false;
        }

        for (int i = 0; i < source.length; i++) {
            if (source[i] != target[i]) {
                return false;
            }
        }
        return true;
    }
}
