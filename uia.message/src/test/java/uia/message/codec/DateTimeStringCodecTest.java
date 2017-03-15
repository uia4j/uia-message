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

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class DateTimeStringCodecTest {

    public DateTimeStringCodecTest() {
    }

    @Test
    public void testNullable() throws Exception {
        DateTimeStringCodec codec = new DateTimeStringCodec();
        codec.setFormat("MMddyyyy");
        codec.setNullable("Y");
        Date dt = codec.decode(new byte[] { 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20 }, 8);
        Assert.assertNull(dt);
    }

    @Test
    public void testDecode() throws Exception {
        DateTimeStringCodec codec = new DateTimeStringCodec();
        codec.setFormat("MMddyyyy");
        Date dt1 = codec.decode(new byte[] { 0x31, 0x30, 0x31, 0x39, 0x32, 0x30, 0x31, 0x33 }, 8);

        codec.setFormat("(HHmmssyyyyMMdd)");
        Date dt2 = codec.decode("(18462820080604)".getBytes(), 16);

        Calendar cal = Calendar.getInstance();
        // 1
        cal.setTime(dt1);
        Assert.assertEquals(2013, cal.get(Calendar.YEAR), 0);
        Assert.assertEquals(10, cal.get(Calendar.MONTH) + 1, 0);
        Assert.assertEquals(19, cal.get(Calendar.DATE), 0);
        // 2
        cal.setTime(dt2);
        Assert.assertEquals(2008, cal.get(Calendar.YEAR), 0);
        Assert.assertEquals(6, cal.get(Calendar.MONTH) + 1, 0);
        Assert.assertEquals(4, cal.get(Calendar.DATE), 0);
        Assert.assertEquals(18, cal.get(Calendar.HOUR_OF_DAY), 0);
        Assert.assertEquals(46, cal.get(Calendar.MINUTE), 0);
        Assert.assertEquals(28, cal.get(Calendar.SECOND), 0);

        // Null Value
        codec.setFormat("MMddyyyy");
        codec.setNullByte("00");
        codec.setNullable("y");
        Assert.assertEquals(null, codec.decode(new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }, 8));
    }

    @Test
    public void testEncode() throws Exception {
        DateTimeStringCodec codec = new DateTimeStringCodec();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1992);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DATE, 18);
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 20);
        cal.set(Calendar.SECOND, 15);
        cal.set(Calendar.MILLISECOND, 27);

        long time = cal.getTimeInMillis();
        codec.setFormat("yyyyMMdd");
        Assert.assertArrayEquals("19920218".getBytes(), codec.encode(new Date(time), 8));
        codec.setFormat("yyyyMMddHHmm");
        Assert.assertArrayEquals("199202181320".getBytes(), codec.encode(new Date(time), 12));

        // Null Value
        codec.setNullByte("00");
        codec.setNullable("y");
        Assert.assertArrayEquals(
                new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
                codec.encode(null, 12));
    }
}
