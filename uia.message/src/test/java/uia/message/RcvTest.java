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
package uia.message;

import java.util.Date;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class RcvTest {

    @BeforeClass
    public static void startup() throws Exception {
        DataExFactory.register("Test", RcvTest.class.getResourceAsStream("Rcv.xml"));
    }

    @Test
    public void testFactory() {
        DataExFactory factory = DataExFactory.getFactory("Test");
        Assert.assertEquals(11, factory.getMessageList().size(), 0);
        Assert.assertEquals(3, factory.getFxList().size(), 0);
        Assert.assertEquals(19, factory.getCodecList().size(), 0);
    }

    @Test
    public void testRcv1() throws Exception {
        Rcv1 rcv = new Rcv1();
        rcv.setHeader(new byte[] { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48 });
        rcv.setTime(new Date());
        rcv.getPowerStatus().setPower1(254);
        rcv.getPowerStatus().setPower2(2);
        rcv.getPowerStatus().setPower3(-12.298d);
        rcv.setFooter("f");
        rcv.setVoltCount(3);
        rcv.getVolts().add(new Rcv1.Volt(1));
        rcv.getVolts().add(new Rcv1.Volt(31));
        rcv.getVolts().add(new Rcv1.Volt(-32));
        rcv.setId(-1);

        MessageSerializerEx writer = DataExFactory.getFactory("Test").createSerializer("Rcv1");
        byte[] data = writer.write(rcv, null);
        Assert.assertEquals(42, data.length, 0);
        Assert.assertArrayEquals(
                new byte[] { data[23], data[24], data[25], data[26] },
                new byte[] { (byte) 0xfe, 0x00, 0x00, 0x00 });

        MessageDeserializer reader = DataExFactory.getFactory("Test").createDeserializer("Rcv1");
        Rcv1 _rcv = reader.read(data, null);
        Assert.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46 }, _rcv.getHeader());
        Assert.assertEquals(rcv.getTime(), _rcv.getTime());
        Assert.assertEquals(rcv.getPowerStatus().getPower1(), _rcv.getPowerStatus().getPower1());
        Assert.assertEquals(rcv.getPowerStatus().getPower2(), _rcv.getPowerStatus().getPower2());
        Assert.assertEquals(rcv.getPowerStatus().getPower3(), _rcv.getPowerStatus().getPower3());
        Assert.assertEquals(rcv.getFooter(), _rcv.getFooter());
        Assert.assertEquals(rcv.getVolts().get(0).getVolt(), _rcv.getVolts().get(0).getVolt());
        Assert.assertEquals(rcv.getVolts().get(1).getVolt(), _rcv.getVolts().get(1).getVolt());
        Assert.assertEquals(rcv.getVolts().get(2).getVolt(), _rcv.getVolts().get(2).getVolt());
        Assert.assertEquals(rcv.getId(), _rcv.getId());
    }

    @Test
    public void testRcv2() throws Exception {
        Rcv2 rcv = new Rcv2();
        rcv.setHeader(new byte[] { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49 });
        rcv.setData(new byte[] { (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x34, (byte) 0x35, (byte) 0x36, (byte) 0x37 });
        rcv.setFooter("##");

        MessageSerializerEx writer = DataExFactory.getFactory("Test").createSerializer("Rcv2");
        byte[] data = writer.write(rcv);
        Assert.assertEquals(15, data.length, 0);

        MessageDeserializer reader = DataExFactory.getFactory("Test").createDeserializer("Rcv2");
        Rcv2 _rcv = reader.read(data);
        Assert.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46 }, _rcv.getHeader());
        Assert.assertArrayEquals(rcv.getData(), _rcv.getData());
        Assert.assertEquals(rcv.getDataLength(), _rcv.getDataLength(), 0);
        Assert.assertEquals("#", _rcv.getFooter());
    }

    @Test
    public void testRcv3() throws Exception {
        Rcv3 rcv = new Rcv3();
        rcv.setCount(2);
        rcv.getValue1().add(10);  //
        rcv.getValue1().add(20);
        rcv.getValue2().add("Good");
        rcv.getValue2().add("Yes");
        rcv.getValue2().add("Right");
        rcv.getValue3().add(new Rcv3.Value3("Jack", 2));
        rcv.getValue3().add(new Rcv3.Value3("Tom", 3));
        rcv.getValue3().add(new Rcv3.Value3("Mary", 4));

        MessageSerializerEx writer = DataExFactory.getFactory("Test").createSerializer("Rcv3");
        byte[] data = writer.write(rcv);
        Assert.assertEquals(2 + 4 * 2 + 8 * 3 + 12 * 3, data.length, 0);

        MessageDeserializer reader = DataExFactory.getFactory("Test").createDeserializer("Rcv3");
        Rcv3 _rcv = reader.read(data);
        Assert.assertEquals(rcv.getValue1(), _rcv.getValue1());
        Assert.assertEquals(rcv.getValue2(), _rcv.getValue2());
        Assert.assertEquals(rcv.getValue3().size(), _rcv.getValue3().size());
        Assert.assertEquals(rcv.getValue3().get(0).getName(), _rcv.getValue3().get(0).getName());
        Assert.assertEquals(rcv.getValue3().get(0).getId(), _rcv.getValue3().get(0).getId());
        Assert.assertEquals(rcv.getValue3().get(1).getName(), _rcv.getValue3().get(1).getName());
        Assert.assertEquals(rcv.getValue3().get(1).getId(), _rcv.getValue3().get(1).getId());
        Assert.assertEquals(rcv.getValue3().get(2).getName(), _rcv.getValue3().get(2).getName());
        Assert.assertEquals(rcv.getValue3().get(2).getId(), _rcv.getValue3().get(2).getId());
    }

    @Test
    public void testRcv31() throws Exception {
        Rcv3 rcv = new Rcv3();
        rcv.setCount(2);
        rcv.getValue1().add(10);  //
        rcv.getValue1().add(20);
        rcv.getValue2().add("Good");
        rcv.getValue2().add("Yes");
        rcv.getValue2().add("Right");
        rcv.getValue3().add(new Rcv3.Value3("Jack", 2));
        rcv.getValue3().add(new Rcv3.Value3("Tom", 3));
        rcv.getValue3().add(new Rcv3.Value3("Mary", 4));

        MessageSerializerEx writer = DataExFactory.getFactory("Test").createSerializer("Rcv3_1");
        byte[] data = writer.write(rcv);
        Assert.assertEquals(2 + 4 * 2 + 8 * 3 + 12 * 3, data.length, 0);

        MessageDeserializer reader = DataExFactory.getFactory("Test").createDeserializer("Rcv3_1");
        Rcv3 _rcv = reader.read(data);
        Assert.assertEquals(rcv.getValue1(), _rcv.getValue1());
        Assert.assertEquals(rcv.getValue2(), _rcv.getValue2());
        Assert.assertEquals(rcv.getValue3().size(), _rcv.getValue3().size());
        Assert.assertEquals(rcv.getValue3().get(0).getName(), _rcv.getValue3().get(0).getName());
        Assert.assertEquals(rcv.getValue3().get(0).getId(), _rcv.getValue3().get(0).getId());
        Assert.assertEquals(rcv.getValue3().get(1).getName(), _rcv.getValue3().get(1).getName());
        Assert.assertEquals(rcv.getValue3().get(1).getId(), _rcv.getValue3().get(1).getId());
        Assert.assertEquals(rcv.getValue3().get(2).getName(), _rcv.getValue3().get(2).getName());
        Assert.assertEquals(rcv.getValue3().get(2).getId(), _rcv.getValue3().get(2).getId());
    }

    @Test
    public void testRcv32() throws Exception {
        Rcv3 rcv = new Rcv3();
        rcv.setCount(2);
        rcv.getValue1().add(10);  //
        rcv.getValue1().add(20);
        rcv.getValue2().add("Good");
        rcv.getValue2().add("Yes");
        rcv.getValue2().add("Right");
        rcv.getValue3().add(new Rcv3.Value3("Jack", 2));
        rcv.getValue3().add(new Rcv3.Value3("Tom", 3));
        rcv.getValue3().add(new Rcv3.Value3("Mary", 4));

        MessageSerializerEx writer = DataExFactory.getFactory("Test").createSerializer("Rcv3_2");
        byte[] data = writer.write(rcv);
        Assert.assertEquals(2 + 4 * 2 + 8 * 3 + 12 * 3, data.length, 0);

        MessageDeserializer reader = DataExFactory.getFactory("Test").createDeserializer("Rcv3_2");
        Rcv3 _rcv = reader.read(data);
        Assert.assertEquals(rcv.getValue1(), _rcv.getValue1());
        Assert.assertEquals(rcv.getValue2(), _rcv.getValue2());
        Assert.assertEquals(rcv.getValue3().size(), _rcv.getValue3().size());
        Assert.assertEquals(rcv.getValue3().get(0).getName(), _rcv.getValue3().get(0).getName());
        Assert.assertEquals(rcv.getValue3().get(0).getId(), _rcv.getValue3().get(0).getId());
        Assert.assertEquals(rcv.getValue3().get(1).getName(), _rcv.getValue3().get(1).getName());
        Assert.assertEquals(rcv.getValue3().get(1).getId(), _rcv.getValue3().get(1).getId());
        Assert.assertEquals(rcv.getValue3().get(2).getName(), _rcv.getValue3().get(2).getName());
        Assert.assertEquals(rcv.getValue3().get(2).getId(), _rcv.getValue3().get(2).getId());
    }

    @Test
    public void testRcv4() throws Exception {
        Rcv4 rcv = new Rcv4();
        rcv.setMask1(new byte[] { (byte) 0x32, (byte) 0x32 });
        rcv.setMask2(2);
        rcv.setSomething1(new byte[] { (byte) 0x61, (byte) 0x62 });
        rcv.setSomething2(new byte[] { (byte) 0x71, (byte) 0x72 });
        rcv.setData(new byte[] { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48 });

        byte[] data = DataExFactory.serialize("Test", "Rcv4", rcv);
        Assert.assertEquals(16, data.length, 0);

        Rcv4 _rcv = DataExFactory.deserialize("Test", "Rcv4", data);
        Assert.assertArrayEquals(rcv.getMask1(), _rcv.getMask1());
        Assert.assertEquals(rcv.getMask2(), _rcv.getMask2());
        Assert.assertArrayEquals(rcv.getSomething1(), _rcv.getSomething1());
        Assert.assertArrayEquals(rcv.getSomething2(), _rcv.getSomething2());
        Assert.assertArrayEquals(rcv.getData(), _rcv.getData());

        rcv.setMask1(new byte[] { (byte) 0x31, (byte) 0x32 });
        data = DataExFactory.serialize("Test", "Rcv4", rcv);
        Assert.assertEquals(14, data.length, 0);

        _rcv = DataExFactory.deserialize("Test", "Rcv4", data);
        Assert.assertArrayEquals(rcv.getMask1(), _rcv.getMask1());
        Assert.assertEquals(rcv.getMask2(), _rcv.getMask2());
        Assert.assertArrayEquals(null, _rcv.getSomething1());
        Assert.assertArrayEquals(rcv.getSomething2(), _rcv.getSomething2());
        Assert.assertArrayEquals(rcv.getData(), _rcv.getData());
    }

    @Test
    public void testRcv5() throws Exception {
        HashMap<String, Object> initial = new HashMap<String, Object>();
        initial.put("REF_VALUE", 2);

        Rcv5 rcv = new Rcv5();
        rcv.setValue1(1);
        rcv.setValue2(2);
        rcv.setValue3(3212);
        rcv.setValue4(3476);

        byte[] data = DataExFactory.serialize("Test", "Rcv5", rcv, initial);
        Assert.assertArrayEquals(
                new byte[] { 0x00, 0x00, 0x00, 0x01, 0x02, 0x00, 0x32, 0x12, 0x76, 0x34 },
                data);

        Rcv5 _rcv = DataExFactory.deserialize("Test", "Rcv5", data, initial);
        Assert.assertEquals(rcv.getValue1(), _rcv.getValue1(), 0);
        Assert.assertEquals(rcv.getValue2(), _rcv.getValue2(), 0);
        Assert.assertEquals(rcv.getValue3(), _rcv.getValue3(), 0);
        Assert.assertEquals(rcv.getValue4(), _rcv.getValue4(), 0);
    }

    @Test
    public void testRcv50() throws Exception {
        HashMap<String, Object> initial = new HashMap<String, Object>();
        initial.put("REF_VALUE", 2);

        Rcv5 rcv = new Rcv5();
        rcv.setValue1(1);
        rcv.setValue2(2);
        rcv.setValue3(3212);
        rcv.setValue4(3476);

        byte[] data = DataExFactory.serialize("Test", "Rcv5_0", rcv, initial);
        Assert.assertArrayEquals(
                new byte[] { 0x00, 0x00, 0x00, 0x01 },
                data);

        Rcv5 _rcv = DataExFactory.deserialize("Test", "Rcv5_0", data, initial);
        Assert.assertEquals(rcv.getValue1(), _rcv.getValue1(), 0);
        Assert.assertEquals(0, _rcv.getValue2(), 0);
        Assert.assertEquals(0, _rcv.getValue3(), 0);
        Assert.assertEquals(0, _rcv.getValue4(), 0);
    }

    @Test
    public void testRcv6() throws Exception {
        Rcv6 rcv = new Rcv6();
        rcv.setContent1("1234");
        rcv.setContent2("123456");

        byte[] data = DataExFactory.serialize("Test", "Rcv6", rcv);
        Assert.assertEquals(20, data.length, 0);

        Assert.assertArrayEquals(
                new byte[] { 0x31, 0x32, 0x33, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
                new byte[] { data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9] });
        Assert.assertArrayEquals(
                new byte[] { 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x20, 0x20, 0x20, 0x20 },
                new byte[] { data[10], data[11], data[12], data[13], data[14], data[15], data[16], data[17], data[18], data[19] });

        Rcv6 _rcv = DataExFactory.deserialize("Test", "Rcv6", data);
        Assert.assertEquals(rcv.getContent1(), _rcv.getContent1());
        Assert.assertEquals(rcv.getContent2(), _rcv.getContent2());
    }

    @Test
    public void testRcv7() throws Exception {
        Rcv7 rcv = new Rcv7();
        rcv.setContent1("1234");
        rcv.setContent2("123456789abcdefg");

        byte[] data = DataExFactory.serialize("Test", "Rcv7", rcv);
        Assert.assertEquals(18, data.length, 0);

        Assert.assertArrayEquals(
                new byte[] { 0x31, 0x32, 0x33, 0x34, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20 },
                new byte[] { data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9] });
        Assert.assertArrayEquals(
                new byte[] { 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38 },
                new byte[] { data[10], data[11], data[12], data[13], data[14], data[15], data[16], data[17] });

        Rcv7 _rcv = DataExFactory.deserialize("Test", "Rcv7", data);
        Assert.assertEquals(rcv.getContent1(), _rcv.getContent1());
        Assert.assertEquals("12345678", _rcv.getContent2());
    }

    @Test
    public void testRcv70() throws Exception {
        Rcv7 rcv = new Rcv7();
        rcv.setContent1("1234");
        rcv.setContent2("123456789abcdefg");

        byte[] data = DataExFactory.serialize("Test", "Rcv7_0", rcv);
        Assert.assertEquals(10, data.length, 0);

        Assert.assertArrayEquals(
                new byte[] { 0x31, 0x32, 0x33, 0x34, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20 },
                new byte[] { data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9] });

        Rcv7 _rcv = DataExFactory.deserialize("Test", "Rcv7_0", data);
        Assert.assertEquals(rcv.getContent1(), _rcv.getContent1());
        Assert.assertEquals("", _rcv.getContent2());
    }
}
