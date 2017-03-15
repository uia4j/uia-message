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
package uia.netflow.packet.v5;

public class NFRecordV5 {

    private byte[] srcAddr;

    private byte[] dstAddr;

    private byte[] nextHop;

    private byte[] input;

    private byte[] output;

    private int dPkts;

    private int dOctets;

    private int first;

    private int last;

    private int srcPort;

    private int dstPort;

    private byte pad1;

    private byte tcpFlags;	// URG=32, ACK=16, PSH=8, RST=4, SYN=2, FIN=1

    private byte protocol;

    private byte typeOfServ;

    private int srcAs;

    private int dstAs;

    private byte srcMask;

    private byte dstMask;

    private byte[] pad2;

    public NFRecordV5() {
        this.srcAddr = new byte[] { 0x7f, 0x00, 0x00, 0x01 };
        this.dstAddr = new byte[4];
        this.nextHop = new byte[4];
        this.input = new byte[2];
        this.output = new byte[2];
        this.pad2 = new byte[2];
    }

    public byte[] getSrcAddr() {
        return this.srcAddr;
    }

    public void setSrcAddr(byte[] srcAddr) {
        this.srcAddr = srcAddr;
    }

    public byte[] getDstAddr() {
        return this.dstAddr;
    }

    public void setDstAddr(byte[] dstAddr) {
        this.dstAddr = dstAddr;
    }

    public byte[] getNextHop() {
        return this.nextHop;
    }

    public void setNextHop(byte[] nextHop) {
        this.nextHop = nextHop;
    }

    public byte[] getInput() {
        return this.input;
    }

    public void setInput(byte[] input) {
        this.input = input;
    }

    public byte[] getOutput() {
        return this.output;
    }

    public void setOutput(byte[] output) {
        this.output = output;
    }

    public int getdPkts() {
        return this.dPkts;
    }

    public void setdPkts(int dPkts) {
        this.dPkts = dPkts;
    }

    public int getdOctets() {
        return this.dOctets;
    }

    public void setdOctets(int dOctets) {
        this.dOctets = dOctets;
    }

    public int getFirst() {
        return this.first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLast() {
        return this.last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getSrcPort() {
        return this.srcPort;
    }

    public void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    public int getDstPort() {
        return this.dstPort;
    }

    public void setDstPort(int dstPort) {
        this.dstPort = dstPort;
    }

    public byte getPad1() {
        return this.pad1;
    }

    public void setPad1(byte pad1) {
        this.pad1 = pad1;
    }

    public byte getTcpFlags() {
        return this.tcpFlags;
    }

    public void setTcpFlags(byte tcpFlags) {
        this.tcpFlags = tcpFlags;
    }

    public byte getProtocol() {
        return this.protocol;
    }

    public void setProtocol(byte protocol) {
        this.protocol = protocol;
    }

    public byte getTypeOfServ() {
        return this.typeOfServ;
    }

    public void setTypeOfServ(byte typeOfServ) {
        this.typeOfServ = typeOfServ;
    }

    public int getSrcAs() {
        return this.srcAs;
    }

    public void setSrcAs(int srcAs) {
        this.srcAs = srcAs;
    }

    public int getDstAs() {
        return this.dstAs;
    }

    public void setDstAs(int dstAs) {
        this.dstAs = dstAs;
    }

    public byte getSrcMask() {
        return this.srcMask;
    }

    public void setSrcMask(byte srcMask) {
        this.srcMask = srcMask;
    }

    public byte getDstMask() {
        return this.dstMask;
    }

    public void setDstMask(byte dstMask) {
        this.dstMask = dstMask;
    }

    public byte[] getPad2() {
        return this.pad2;
    }

    public void setPad2(byte[] pad2) {
        this.pad2 = pad2;
    }

}
