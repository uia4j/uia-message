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
package uia.message;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Kyle
 */
public class Rcv1 {

    private byte[] header;

    private Date time;

    private String footer;

    private int voltCount;

    private Rcv1.PowerStatus powerStatus;

    private ArrayList<Rcv1.Volt> volts;

    private Integer id;

    public Rcv1() {
        this.powerStatus = new Rcv1.PowerStatus();
        this.volts = new ArrayList<Rcv1.Volt>();
    }

    public byte[] getHeader() {
        return this.header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Rcv1.PowerStatus getPowerStatus() {
        return this.powerStatus;
    }

    public void setPowerStatus(Rcv1.PowerStatus status) {
        this.powerStatus = status;
    }

    public String getFooter() {
        return this.footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public int getVoltCount() {
        return this.voltCount;
    }

    public void setVoltCount(int voltCount) {
        this.voltCount = voltCount;
    }

    public ArrayList<Rcv1.Volt> getVolts() {
        return this.volts;
    }

    public void setVolts(ArrayList<Volt> volts) {
        this.volts = volts;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static class PowerStatus {

        private Integer power1;

        private Integer power2;

        private Double power3;

        public Integer getPower1() {
            return this.power1;
        }

        public void setPower1(Integer power1) {
            this.power1 = power1;
        }

        public Integer getPower2() {
            return this.power2;
        }

        public void setPower2(Integer power2) {
            this.power2 = power2;
        }

        public Double getPower3() {
            return this.power3;
        }

        public void setPower3(Double power3) {
            this.power3 = power3;
        }
    }

    public static class Volt {

        private Integer volt;

        public Volt() {
        }

        public Volt(Integer volt) {
            this.volt = volt;
        }

        public Integer getVolt() {
            return this.volt;
        }

        public void setVolt(Integer volt) {
            this.volt = volt;
        }
    }
}
