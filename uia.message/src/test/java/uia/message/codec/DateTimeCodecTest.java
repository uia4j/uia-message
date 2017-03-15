/*******************************************************************************
 * Copyright 2017 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.message.codec;

import java.nio.ByteBuffer;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class DateTimeCodecTest {

    public DateTimeCodecTest() {
    }

    @Test
    public void testDecode() throws Exception {
        long time = System.currentTimeMillis();
        byte[] timeBytes = ByteBuffer.allocate(8).putLong(time).array();

        DateTimeCodec codec = new DateTimeCodec();
        Assert.assertEquals(time, codec.decode(timeBytes, 64).getTime(), 0);
    }

    @Test
    public void testEncode() throws Exception {
        Date now = new Date();
        long time = now.getTime();

        DateTimeCodec codec = new DateTimeCodec();
        Assert.assertArrayEquals(ByteBuffer.allocate(8).putLong(time).array(), codec.encode(now, 64));
    }
}
