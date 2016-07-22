/*******************************************************************************
 * * Copyright (c) 2015, UIA
 * * All rights reserved.
 * * Redistribution and use in source and binary forms, with or without
 * * modification, are permitted provided that the following conditions are met:
 * *
 * * * Redistributions of source code must retain the above copyright
 * * notice, this list of conditions and the following disclaimer.
 * * * Redistributions in binary form must reproduce the above copyright
 * * notice, this list of conditions and the following disclaimer in the
 * * documentation and/or other materials provided with the distribution.
 * * * Neither the name of the {company name} nor the
 * * names of its contributors may be used to endorse or promote products
 * * derived from this software without specific prior written permission.
 * *
 * * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS" AND ANY
 * * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package uia.message.codec;

/**
 * Core interface used to encode and decode data between actual value and byte array.
 *
 * @author Kyle
 *
 * @param <T> Data type byte array to be converted to.
 */
public interface BlockCodec<T> {

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
