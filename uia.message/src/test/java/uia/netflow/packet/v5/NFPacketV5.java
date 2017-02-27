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
package uia.netflow.packet.v5;

import java.util.ArrayList;

import uia.netflow.packet.NFPacket;

public class NFPacketV5 extends NFPacket {

    public static final String ID = "PacketV5";

    private NFHeaderV5 header;

    private ArrayList<NFRecordV5> records;

    public NFPacketV5() {
        super(5);
        this.header = new NFHeaderV5();
        this.records = new ArrayList<NFRecordV5>();
    }

    public NFHeaderV5 getHeader() {
        return this.header;
    }

    public void setHeader(NFHeaderV5 header) {
        this.header = header;
    }

    public ArrayList<NFRecordV5> getRecords() {
        return this.records;
    }

    public void setRecords(ArrayList<NFRecordV5> records) {
        this.records = records;
    }

}
