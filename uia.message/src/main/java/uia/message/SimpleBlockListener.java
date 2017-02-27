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
package uia.message;

import uia.utils.ByteUtils;

/**
 * Simple block listener.<br>
 * The listener will output serializing and deserializing information to console.
 *
 * @author Kyle
 *
 */
public class SimpleBlockListener implements BlockListener {

    private String fix;

    /**
     * Constructor.
     */
    public SimpleBlockListener() {
        this.fix = "";
    }

    @Override
    public void valueHandled(String name, BlockInfo block) {
        if (block.getValue().getClass() == byte[].class) {
            System.out.println("BLK> " + this.fix + name + ":" +
                    ByteUtils.toHexString(block.getData()) + "(" + block.getBitLength() + "), " +
                    ByteUtils.toHexString((byte[]) block.getValue()));
        }
        else {
            System.out.println("BLK> " + this.fix + name + ":" +
                    ByteUtils.toHexString(block.getData()) + "(" + block.getBitLength() + "), " +
                    block.getValue());
        }
    }

    @Override
    public void seqTouched(String name, boolean start, int offset) {
        if (start) {
            System.out.println("SEQ> " + this.fix + name);
            this.fix += "  ";
        }
        else {
            this.fix = this.fix.substring(0, this.fix.length() - 2);
        }
    }

    @Override
    public void listTouched(String name, boolean start, int offset) {
        if (start) {
            System.out.println("LST> " + this.fix + name);
            this.fix += "  ";
        }
        else {
            this.fix = this.fix.substring(0, this.fix.length() - 2);
        }
    }
}
