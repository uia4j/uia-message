/*******************************************************************************
 * * Copyright (c) 2014, UIA
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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class ByteUtilsTest {

    public ByteUtilsTest() {
    }

    @Test
    public void testDoubleByte() {
    	byte[] v1 = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putDouble(12.9d).array();
    	byte[] v2 = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(13.9d).array();
    	System.out.println(ByteUtils.toHexString(v1));
    	System.out.println(ByteUtils.toHexString(v2));
    	System.out.println("v1=" + ByteBuffer.wrap(v1).getDouble());
    	System.out.println("v2=" + ByteBuffer.wrap(v2).order(ByteOrder.LITTLE_ENDIAN).getDouble());
    }
    
    @Test
    @Ignore
    public void testMaxNum() throws Exception {
        try {
            byte[] temp = new byte[]{(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            ByteUtils.uintValue(temp, 32);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    @Test
    @Ignore
    public void testIntValue() throws Exception {
        System.out.println(ByteUtils.intValue(new byte[]{(byte) 0xfe}, 8));
        System.out.println(ByteUtils.intValue(new byte[]{(byte) 0xfe}, 7));
        System.out.println();

        byte[] temp = new byte[]{(byte) 0x80, (byte) 0x00, (byte) 0x30, (byte) 0x81};
        System.out.println(ByteUtils.toBitString(temp));
        System.out.println(ByteUtils.intValue(temp, 8));
        System.out.println(ByteUtils.intValue(temp, 16));
        System.out.println(ByteUtils.intValue(temp, 24));
        System.out.println(ByteUtils.intValue(temp, 32));
        System.out.println();
    }

    @Test
    @Ignore
    public void testUintValue() throws Exception {
        System.out.println(ByteUtils.uintValue(new byte[]{(byte) 0xfe}, 8));
        System.out.println(ByteUtils.uintValue(new byte[]{(byte) 0xfe}, 7));
        System.out.println();

        byte[] temp = new byte[]{(byte) 0x80, (byte) 0x00, (byte) 0x30, (byte) 0x81};
        System.out.println(ByteUtils.toBitString(temp));
        System.out.println(ByteUtils.uintValue(temp, 8));
        System.out.println(ByteUtils.uintValue(temp, 16));
        System.out.println(ByteUtils.uintValue(temp, 24));
        // System.out.println(IntegerUtils.uintValue(temp, 32));
        System.out.println();
    }

    @Test
    public void testBcdValue() throws Exception {
        System.out.println(ByteUtils.bcdValue((byte) 0x71));
        System.out.println(ByteUtils.bcdValue((byte) 0x82));
        System.out.println(ByteUtils.bcdValue(new byte[]{(byte) 0x34, (byte) 0x12}));
        System.out.println(ByteUtils.bcdValue(new byte[]{(byte) 0x20, (byte) 0x12, (byte) 0x32}));
        System.out.println();
    }

    @Test
    @Ignore
    public void testOffset() {
        System.out.println("testOffset");
        byte[] data = new byte[]{(byte) 0xf0, (byte) 0x4f, (byte) 0x84, (byte) 0x80};
        System.out.println(ByteUtils.toBitString(data));
        System.out.println(ByteUtils.toBitString(ByteUtils.offsetBits(data, 3)));
        System.out.println(ByteUtils.toBitString(ByteUtils.offsetBits(data, 3, 12)));
        System.out.println(ByteUtils.toBitString(ByteUtils.offsetBits(data, 3, 16)));
    }

    @Test
    @Ignore
    public void testToValueRightLeft() {
        System.out.println("testToValueRightLeft");
        byte value = (byte) 0xa9;
        System.out.println("value   =" + ByteUtils.toBitString(value));
        System.out.println("Right(2)=" + ByteUtils.toBitString(ByteUtils.valueRight(value, 2)));
        System.out.println("Left(2) =" + ByteUtils.toBitString(ByteUtils.valueLeft(value, 2)));

    }

    @Test
    @Ignore
    public void testSub() {
        System.out.println("testSub");
        byte[] data = new byte[]{(byte) 0xf8, (byte) 0x4f, (byte) 0xb4, (byte) 0x6f};
        System.out.println(ByteUtils.toBitString(data));
        System.out.println(ByteUtils.toBitString(ByteUtils.copyBits(data, 0, 4)));
        System.out.println(ByteUtils.toBitString(ByteUtils.copyBits(data, 0, 13)));
        System.out.println(ByteUtils.toBitString(ByteUtils.copyBits(data, 0, 4, 8)));
        System.out.println(ByteUtils.toBitString(ByteUtils.copyBits(data, 0, 4, 10)));
        System.out.println(ByteUtils.toBitString(ByteUtils.copyBits(data, 0, 4, 15)));
        System.out.println(ByteUtils.toBitString(ByteUtils.copyBits(data, 3, 2, 16)));

    }

    @Test
    @Ignore
    public void testToString() {
        System.out.println(ByteUtils.toBitString((byte) 0xf0));
        System.out.println(ByteUtils.toBitString((byte) 0x0f));
        System.out.println(ByteUtils.toBitString((byte) 0x81));
        System.out.println(ByteUtils.toHexString((byte) 0xf0));
        System.out.println(ByteUtils.toHexString((byte) 0x0f));
        System.out.println(ByteUtils.toHexString((byte) 0x81));
    }
}
