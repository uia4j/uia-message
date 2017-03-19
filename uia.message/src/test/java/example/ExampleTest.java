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
package example;

import org.junit.Assert;
import org.junit.Test;

import uia.message.DataExFactory;

public class ExampleTest {

    @Test
    public void testCase1() throws Exception {

        // register
        DataExFactory.register("cases", ExampleTest.class.getResourceAsStream("test.xml"));

        // deserialize
        byte[] data = new byte[] {
                0x4a, 0x75, 0x64, 0x79, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, // name
                0x00,                                                       // sex
                0x31, 0x39, 0x39, 0x32, 0x30, 0x32, 0x31, 0x38              // birthday
        };
        One one = (One) DataExFactory.deserialize("cases", "Case1", data);
        Assert.assertEquals("Judy", one.getName());
        Assert.assertEquals(0, one.getSex(), 0);
        Assert.assertEquals("19920218", one.getBirthdayString("yyyyMMdd"));

        // serialize
        one.setName("Jack");
        one.setSex(1);
        byte[] result = DataExFactory.serialize("cases", "Case1", one);
        Assert.assertArrayEquals(
                new byte[] {
                        0x4a, 0x61, 0x63, 0x6b, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, // name: Jack
                        0x01,                                                       // sex: 1
                        0x31, 0x39, 0x39, 0x32, 0x30, 0x32, 0x31, 0x38              // birthday: 19920218
                },
                result);

    }

}
