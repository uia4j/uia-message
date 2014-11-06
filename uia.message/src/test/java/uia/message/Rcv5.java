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

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Kyle
 */
public class Rcv5 {

    private byte[] header;
    private Date time;
    private String footer;
    private int voltCount;
    private Rcv5.PowerStatus status;
    private ArrayList<Integer> volts;
    private Integer id;

    public Rcv5() {
        this.status = new Rcv5.PowerStatus();
        this.volts = new ArrayList<Integer>();
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

    public Rcv5.PowerStatus getPowerStatus() {
        return this.status;
    }

    public void setPowerStatus(Rcv5.PowerStatus status) {
        this.status = status;
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

    public ArrayList<Integer> getVolts() {
        return this.volts;
    }

    public void setVolts(ArrayList<Integer> volts) {
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
}
