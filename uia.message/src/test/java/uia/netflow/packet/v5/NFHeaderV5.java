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

public class NFHeaderV5 {

    private int version;

    private int count;

    private int sysUptime;

    private int unixSecs;

    private int unixNsecs;

    private int flowSeq;

    private int engineType;

    private int engineId;

    private byte[] samplingInterval;

    public NFHeaderV5() {
        this.version = 5;
        this.samplingInterval = new byte[2];
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSysUptime() {
        return this.sysUptime;
    }

    public void setSysUptime(int sysUptime) {
        this.sysUptime = sysUptime;
    }

    public int getUnixSecs() {
        return this.unixSecs;
    }

    public void setUnixSecs(int unixSecs) {
        this.unixSecs = unixSecs;
    }

    public int getUnixNsecs() {
        return this.unixNsecs;
    }

    public void setUnixNsecs(int unixNsecs) {
        this.unixNsecs = unixNsecs;
    }

    public int getFlowSeq() {
        return this.flowSeq;
    }

    public void setFlowSeq(int flowSeq) {
        this.flowSeq = flowSeq;
    }

    public int getEngineType() {
        return this.engineType;
    }

    public void setEngineType(int engineType) {
        this.engineType = engineType;
    }

    public int getEngineId() {
        return this.engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public byte[] getSamplingInterval() {
        return this.samplingInterval;
    }

    public void setSamplingInterval(byte[] samplingInterval) {
        this.samplingInterval = samplingInterval;
    }

}
