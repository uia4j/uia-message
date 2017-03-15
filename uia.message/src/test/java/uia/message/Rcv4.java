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

/**
 *
 * @author Kyle
 */
public class Rcv4 {

    public byte[] mask1;

    public int mask2;

    private byte[] something1;

    private byte[] something2;

    private byte[] data;

    public byte[] getMask1() {
        return this.mask1;
    }

    public void setMask1(byte[] mask1) {
        this.mask1 = mask1;
    }

    public int getMask2() {
        return this.mask2;
    }

    public void setMask2(int mask2) {
        this.mask2 = mask2;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getSomething1() {
        return this.something1;
    }

    public void setSomething1(byte[] something1) {
        this.something1 = something1;
    }

    public byte[] getSomething2() {
        return this.something2;
    }

    public void setSomething2(byte[] something2) {
        this.something2 = something2;
    }

}
