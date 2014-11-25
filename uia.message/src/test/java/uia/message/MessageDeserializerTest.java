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

import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

import uia.utils.ByteUtils;

/**
 * 
 * @author Kyle
 */
public class MessageDeserializerTest {

    public MessageDeserializerTest() {
    }

    @BeforeClass
    public static void startup() throws Exception {
        URL url = MessageDeserializerTest.class.getResource("Rcv.xml");
        System.out.println("xml:" + url);

        DataExFactory.register("Test", MessageDeserializerTest.class.getResourceAsStream("Rcv.xml"));
    }

    @Test
    public void testRcv1() throws Exception {
        System.out.println("Rcv1");
        byte[] data = new byte[] {
                (byte) 0xa7, // hdaer (48)
                (byte) 0xda,
                (byte) 0xac,
                (byte) 0x4f,
                (byte) 0xbd,
                (byte) 0xd6,
                (byte) 0x32, // time (112)
                (byte) 0x30,
                (byte) 0x31,
                (byte) 0x33,
                (byte) 0x30,
                (byte) 0x32,
                (byte) 0x30,
                (byte) 0x36,
                (byte) 0x32,
                (byte) 0x31,
                (byte) 0x33,
                (byte) 0x31,
                (byte) 0x35,
                (byte) 0x34,
                (byte) 0x00, // power1 (32)
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x01,
                (byte) 0x32, // power2 (16)
                (byte) 0x20,
                (byte) 0x40, // power3 (64)
                (byte) 0x28,
                (byte) 0x98,
                (byte) 0x93,
                (byte) 0x74,
                (byte) 0xbc,
                (byte) 0x6a,
                (byte) 0x7f,
                (byte) 0x03, // len (8)
                (byte) 0x66, // footer (8)
                (byte) 0x04, // volt (6 + 6 + 6)
                (byte) 0x20,
                (byte) 0xd0 // id (4)
        };

        try {
            Rcv1 rcv1 = (Rcv1) DataExFactory.deserialize("Test", "Rcv1", data);
            System.out.println("header    : " + ByteUtils.toHexString(rcv1.getHeader()));
            System.out.println("time      : " + rcv1.getTime());
            System.out.println("power1    : " + rcv1.getPowerStatus().getPower1());
            System.out.println("power2    : " + rcv1.getPowerStatus().getPower2());
            System.out.println("power3    : " + rcv1.getPowerStatus().getPower3());
            System.out.println("footer    : " + rcv1.getFooter());
            System.out.println("voltCount : " + rcv1.getVoltCount());
            for (int i = 0; i < rcv1.getVolts().size(); i++) {
                System.out.println("Volt      : " + rcv1.getVolts().get(i).getVolt());
            }
            System.out.println("Id        : " + rcv1.getId());

            System.out.println("Rcv5");
            rcv1 = (Rcv1) DataExFactory.deserialize("Test", "Rcv5", data);
            System.out.println("header    : " + ByteUtils.toHexString(rcv1.getHeader()));
            System.out.println("time      : " + rcv1.getTime());
            System.out.println("power1    : " + rcv1.getPowerStatus().getPower1());
            System.out.println("power2    : " + rcv1.getPowerStatus().getPower2());
            System.out.println("power3    : " + rcv1.getPowerStatus().getPower3());
            System.out.println("footer    : " + rcv1.getFooter());
            System.out.println("voltCount : " + rcv1.getVoltCount());
            for (int i = 0; i < rcv1.getVolts().size(); i++) {
                System.out.println("Volt      : " + rcv1.getVolts().get(i).getVolt());
            }
            System.out.println("Id        : " + rcv1.getId());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testRcv2() throws Exception {
        System.out.println("Rcv2");
        byte[] data = new byte[] {
                (byte) 0xa7, // hdaer (48)
                (byte) 0xda,
                (byte) 0xac,
                (byte) 0x4f,
                (byte) 0xbd,
                (byte) 0xd6,
                (byte) 0x32, // time (112)
                (byte) 0x30,
                (byte) 0x31,
                (byte) 0x33,
                (byte) 0x30,
                (byte) 0x32,
                (byte) 0x30,
                (byte) 0x36,
                (byte) 0x32,
                (byte) 0x31,
                (byte) 0x33,
                (byte) 0x31,
                (byte) 0x35,
                (byte) 0x34,
                (byte) 0x41 // footer (8)
        };

        try {
            Rcv2 rcv2 = (Rcv2) DataExFactory.deserialize("Test", "Rcv2", data);
            System.out.println("header: " + ByteUtils.toHexString(rcv2.getHeader()));
            System.out.println("time  : " + rcv2.getTime());
            System.out.println("footer: " + rcv2.getFooter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testRcv3() throws Exception {
        System.out.println("Rcv3");
        byte[] data = new byte[] {
                (byte) 0xa7, // header (48)
                (byte) 0xda,
                (byte) 0xac,
                (byte) 0x4f,
                (byte) 0xbd,
                (byte) 0xd6,
                (byte) 0x06, // dataLength
                (byte) 0x31, // data
                (byte) 0x32,
                (byte) 0x33,
                (byte) 0x34,
                (byte) 0x35,
                (byte) 0x36,
                (byte) 0x41 // footer (8bits)
        };

        try {
            Rcv3 rcv3 = (Rcv3) DataExFactory.deserialize("Test", "Rcv3", data);
            System.out.println("header   : " + ByteUtils.toHexString(rcv3.getHeader()));
            System.out.println("data     : " + ByteUtils.toHexString(rcv3.getData()));
            System.out.println("footer   : " + rcv3.getFooter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testRcv4() throws Exception {
        System.out.println("Rcv4");
        byte[] data = new byte[] {
                (byte) 0x31, // mask1
                (byte) 0x32,
                (byte) 0x00, // mask 2
                (byte) 0x01,
                (byte) 0x92, // ??
                (byte) 0x91,
                (byte) 0x41, // data
                (byte) 0x42,
                (byte) 0x43,
                (byte) 0x44,
                (byte) 0x45,
                (byte) 0x46,
                (byte) 0x47,
                (byte) 0x48
        };

        try {
            Rcv4 rcv4 = (Rcv4) DataExFactory.deserialize("Test", "Rcv4", data);
            System.out.println("mask1: " + ByteUtils.toHexString(rcv4.getMask1()));
            System.out.println("mask2: " + rcv4.getMask2());
            System.out.println("something1: " + ByteUtils.toHexString(rcv4.getSomething1()));
            System.out.println("something2: " + ByteUtils.toHexString(rcv4.getSomething2()));
            System.out.println("data: " + ByteUtils.toHexString(rcv4.getData()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
