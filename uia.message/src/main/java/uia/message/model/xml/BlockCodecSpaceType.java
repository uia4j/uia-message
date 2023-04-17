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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2016.07.27 at 05:56:42 PM CST
//
package uia.message.model.xml;

import java.util.ArrayList;
import java.util.List;

import uia.xml.TagInfo;
import uia.xml.TagListElem;
import uia.xml.TagListInfo;

@TagInfo(name = "BlockCodecSpace")
public class BlockCodecSpaceType {

    @TagListInfo(
            inline = true,
            elems = { @TagListElem(name = "", type = BlockCodecType.class) })
    protected ArrayList<BlockCodecType> blockCodec;

    public BlockCodecSpaceType() {
        this.blockCodec = new ArrayList<BlockCodecType>();
    }

    public List<BlockCodecType> getBlockCodec() {
        if (this.blockCodec == null) {
            this.blockCodec = new ArrayList<BlockCodecType>();
        }
        return this.blockCodec;
    }

}
