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
package uia.netflow.packet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import uia.message.DataExFactory;
import uia.message.SimpleBlockListener;
import uia.netflow.packet.v5.NFPacketV5;
import uia.netflow.packet.v5.NFRecordV5;
import uia.utils.ByteUtils;
import uia.utils.HexStringUtils;

public class NFPacketTest {

    private static SimpleBlockListener L = new SimpleBlockListener();

    @BeforeClass
    public static void initial() throws Exception {
        NFPacketEnv.initial();
        DataExFactory.getFactory(NFPacketEnv.DOMAIN_NAME).addListener(L);
    }

    @AfterClass
    public static void shutdown() {
        DataExFactory.getFactory(NFPacketEnv.DOMAIN_NAME).removeListener(L);
        DataExFactory.getFactory(NFPacketEnv.DOMAIN_NAME).clearListeners();
    }

    @Test
    public void testSerialize() throws Exception {
        System.out.println("testSerialize");
        NFPacket pk = new NFPacket();
        DataExFactory factory = DataExFactory.getFactory(NFPacketEnv.DOMAIN_NAME);
        byte[] data = factory.serialize(NFPacket.ID, pk);
        System.out.println(" - " + ByteUtils.toHexString(data, ","));
        System.out.println();
    }

    @Test
    public void testDeserialize() throws Exception {
        System.out.println("testDeserialize");
        DataExFactory factory = DataExFactory.getFactory(NFPacketEnv.DOMAIN_NAME);
        factory.deserialize(NFPacket.ID, new byte[] { 0x00, 0x09, 0x00, 0x00, 0x00, 0x00, 0x00 });
        System.out.println();
    }

    @Test
    public void testSerializeV5() throws Exception {
        System.out.println("testSerializeV5");
        NFPacketV5 pk = new NFPacketV5();
        pk.getRecords().add(new NFRecordV5());
        pk.getRecords().add(new NFRecordV5());
        pk.getHeader().setCount(2);
        DataExFactory factory = DataExFactory.getFactory(NFPacketEnv.DOMAIN_NAME);
        byte[] data = factory.serialize(NFPacketV5.ID, pk, null);
        System.out.println(" - " + ByteUtils.toHexString(data, ","));
        System.out.println();
    }

    @Test
    public void testDeserializeV5() throws Exception {
        System.out.println("testDeserializeV5");
        byte[] data = HexStringUtils
                .toBytes("00-05-00-01-04-d5-1c-ed-3c-ff-1f-08-20-d8-1e-75-ba-30-ba-f5-fe-ff-ff-ff-0a-00-00-01-0a-00-00-02-00-00-00-00-00-00-00-00-00-00-00-00-00-00-03-e8-04-d4-32-8d-04-d5-1c-ed-03-e8-00-50-80-69-06-ff-ff-ff-ff-ff-00-00-00-00", "-");
        DataExFactory factory = DataExFactory.getFactory(NFPacketEnv.DOMAIN_NAME);
        factory.deserialize(NFPacketV5.ID, data, null);
        System.out.println();
    }
}
