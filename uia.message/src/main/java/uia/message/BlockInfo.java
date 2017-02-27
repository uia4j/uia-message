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

/**
 *
 * @author Kyle
 *
 */
public final class BlockInfo {

    private final Object value;

    private final byte[] data;

    private final int bitLength;

    /**
     * Constructor.
     * @param value Value.
     * @param data Byte array.
     * @param bitLength Bit length.
     */
    public BlockInfo(Object value, byte[] data, int bitLength) {
        this.value = value;
        this.data = data;
        this.bitLength = bitLength;
    }

    /**
     * Get Value.
     * @return Value.
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Get byte array.
     * @return byte array.
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Get bit length.
     * @return Bit length.
     */
    public int getBitLength() {
        return this.bitLength;
    }
}
