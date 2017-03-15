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

import java.io.File;

import uia.message.DataExFactory;

/**
 * NetFlow packet environment.
 *
 * @author Kyle K. Lin
 *
 */
public class NFPacketEnv {

    public static final String DOMAIN_NAME = "NFPacket";

    public static void initial() throws Exception {
        String file = NFPacketEnv.class.getResource("NFPacket.xml").getFile();
        DataExFactory.register(DOMAIN_NAME, new File(file));
    }
}
