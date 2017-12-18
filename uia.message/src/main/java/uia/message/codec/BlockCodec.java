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
package uia.message.codec;

/**
 * Core interface used to encode and decode data between actual value and byte array.<br>
 *
 * @author Kyle
 *
 * @param <T> Data type byte array to be converted to.
 */
public interface BlockCodec<T> {

    public String getValueType();

    /**
     * Decode byte array to specific data.
     *
     * @param data The byte array.
     * @param bitLength Effective bit length of data.
     * @return The result.
     * @throws BlockCodecException Decode fail.
     */
    public T decode(byte[] data, int bitLength) throws BlockCodecException;

    /**
     * Encode specific data to byte array.
     *
     * @param data Data to be converted.
     * @param bitLength Effective bit length of result.
     * @return The result.
     * @throws BlockCodecException Encode fail.
     */
    public byte[] encode(T data, int bitLength) throws BlockCodecException;

    /**
     * Default value if bit length is zero.
     * @return Default value.
     */
    public T zeroValue();

    /**
     * Reset to initial state.
     */
    public void reset();
}
