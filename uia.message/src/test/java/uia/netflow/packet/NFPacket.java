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
package uia.netflow.packet;

import uia.message.DataExFactory;
import uia.message.codec.BlockCodecException;
import uia.netflow.packet.v5.NFPacketV5;

/**
 * NetFlow packet.
 *
 * @author Kyle K. Lin
 *
 */
public class NFPacket {

    public static final String ID = "Packet";

    private int version;

    public NFPacket() {
        this.version = 5;
    }

    public NFPacket(int version) {
        this.version = version;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public static NFPacket decode(byte[] data) throws BlockCodecException {
        NFPacket pk = (NFPacket) DataExFactory.deserialize(NFPacketEnv.DOMAIN_NAME, NFPacket.ID, data);
        if (pk.getVersion() == 5) {
            NFPacketV5 pk5 = (NFPacketV5) DataExFactory.deserialize(NFPacketEnv.DOMAIN_NAME, NFPacketV5.ID, data);
            return pk5;
        }
        else {
            return pk;
        }
    }
}
