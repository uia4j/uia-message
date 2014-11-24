/*******************************************************************************
 * Copyright (c) 2013, BooksTech Co.,Ltd.
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the BooksTech nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 *  THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package uia.message;

import java.io.File;
import java.net.URL;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import uia.utils.ByteUtils;

/**
 * 
 * @author Kyle
 */
public class MessageSerializerTest {

    public MessageSerializerTest() {
    }

    @BeforeClass
    public static void startup() throws Exception {
        URL url = MessageDeserializerTest.class.getResource("Rcv.xml");
        System.out.println("xml:" + url);
        DataExFactory.register("Test", new File(url.toURI()));
    }

    @Test
    public void testRcv1() throws Exception {
        // message
        Rcv1 rcv1 = new Rcv1();
        rcv1.setHeader(new byte[] { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48 });
        rcv1.setTime(new Date());
        rcv1.getPowerStatus().setPower1(7809);
        rcv1.getPowerStatus().setPower2(2);
        rcv1.getPowerStatus().setPower3(-12.298d);
        rcv1.setFooter("f");
        rcv1.setVoltCount(3);
        rcv1.getVolts().add(new Rcv1.Volt(1));
        rcv1.getVolts().add(new Rcv1.Volt(32));
        rcv1.getVolts().add(new Rcv1.Volt(-32));
        rcv1.setId(15);

        try {
            // encode
            MessageSerializer writer = DataExFactory.getFactory("Test").createSerializer("Rcv1");
            byte[] data = writer.write(rcv1);
            System.out.println(ByteUtils.toHexString(data));

            // decode
            MessageDeserializer reader = DataExFactory.getFactory("Test").createDeserializer("Rcv1");
            rcv1 = (Rcv1) reader.read(data);
            System.out.println("header   : " + ByteUtils.toHexString(rcv1.getHeader()));
            System.out.println("time     : " + rcv1.getTime());
            System.out.println("power1   : " + rcv1.getPowerStatus().getPower1());
            System.out.println("power2   : " + rcv1.getPowerStatus().getPower2());
            System.out.println("power3   : " + rcv1.getPowerStatus().getPower3());
            System.out.println("footer   : " + rcv1.getFooter());
            System.out.println("voltCount: " + rcv1.getVoltCount());
            for (int i = 0; i < rcv1.getVolts().size(); i++) {
                System.out.println("  Volt    : " + rcv1.getVolts().get(i).getVolt());
            }
            System.out.println("Id        : " + rcv1.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testRcv2() throws Exception {
        // message
        Rcv2 rcv2 = new Rcv2();
        rcv2.setHeader(new byte[] { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48 });
        rcv2.setTime(new Date());
        rcv2.setFooter("##");

        try {
            // encode
            MessageSerializer writer = DataExFactory.getFactory("Test").createSerializer("Rcv2");
            byte[] data = writer.write(rcv2);
            System.out.println(ByteUtils.toHexString(data));

            // decode
            MessageDeserializer reader = DataExFactory.getFactory("Test").createDeserializer("Rcv2");
            rcv2 = (Rcv2) reader.read(data);
            System.out.println("header: " + ByteUtils.toHexString(rcv2.getHeader()));
            System.out.println("time  : " + rcv2.getTime());
            System.out.println("footer: " + rcv2.getFooter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testRcv3() throws Exception {
        // message
        Rcv3 rcv3 = new Rcv3();
        rcv3.setHeader(new byte[] { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48 });
        rcv3.setData(new byte[] { (byte) 0x41, (byte) 0x42 });
        rcv3.setFooter("##");

        try {
            // encode
            MessageSerializer writer = DataExFactory.getFactory("Test").createSerializer("Rcv3");
            byte[] data = writer.write(rcv3);
            System.out.println(ByteUtils.toHexString(data));

            // decode
            MessageDeserializer reader = DataExFactory.getFactory("Test").createDeserializer("Rcv3");
            rcv3 = (Rcv3) reader.read(data);
            System.out.println("header: " + ByteUtils.toHexString(rcv3.getHeader()));
            System.out.println("data  : " + ByteUtils.toHexString(rcv3.getData()));
            System.out.println("footer: " + rcv3.getFooter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testRcv4() throws Exception {
        // message
        Rcv4 rcv4 = new Rcv4();
        rcv4.setMask(new byte[] { (byte) 0x41, (byte) 0x42 });
        rcv4.setData(new byte[] { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48 });
        rcv4.setSomething1(new byte[] { (byte) 0x61, (byte) 0x62 });
        rcv4.setSomething2(new byte[] { (byte) 0x71, (byte) 0x72 });

        try {
            // encode
            MessageSerializer writer = DataExFactory.getFactory("Test").createSerializer("Rcv4");
            byte[] data = writer.write(rcv4);
            System.out.println(ByteUtils.toHexString(data));

            // decode
            MessageDeserializer reader = DataExFactory.getFactory("Test").createDeserializer("Rcv4");
            rcv4 = (Rcv4) reader.read(data);
            System.out.println("mask: " + ByteUtils.toHexString(rcv4.getMask()));
            System.out.println("something1: " + ByteUtils.toHexString(rcv4.getSomething1()));
            System.out.println("something2: " + ByteUtils.toHexString(rcv4.getSomething2()));
            System.out.println("data: " + ByteUtils.toHexString(rcv4.getData()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testRcv5() throws Exception {
        // message
        Rcv5 rcv1 = new Rcv5();
        rcv1.setFooter(255);
        rcv1.getVolts().add(1);
        rcv1.getVolts().add(32);
        rcv1.getVolts().add(-32);
        rcv1.setId(15);

        try {
            // encode
            MessageSerializer writer = DataExFactory.getFactory("Test").createSerializer("Rcv5");
            byte[] data = writer.write(rcv1);
            System.out.println(ByteUtils.toHexString(data));

            // decode
            MessageDeserializer reader = DataExFactory.getFactory("Test").createDeserializer("Rcv5");
            rcv1 = (Rcv5) reader.read(data);
            System.out.println("footer   : " + rcv1.getFooter());
            System.out.println("voltCount: " + rcv1.getVoltCount());
            for (int i = 0; i < rcv1.getVolts().size(); i++) {
                System.out.println("  Volt    : " + rcv1.getVolts().get(i));
            }
            System.out.println("Id        : " + rcv1.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
