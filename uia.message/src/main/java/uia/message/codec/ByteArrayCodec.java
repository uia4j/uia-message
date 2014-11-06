/*******************************************************************************
 * Copyright (c) 2013, BooksTech Co.,Ltd.
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the BooksTech nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 *  THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package uia.message.codec;

import java.util.Arrays;

/**
 * Convert byte array to new byte array.
 * 
 * @author Kyle
 */
public class ByteArrayCodec implements BlockCodec<byte[]> {

	@Override
	public byte[] decode(byte[] data, int bitLength) throws BlockCodecException {
		if (bitLength < 0) {
			return data;
		}

		byte[] result = Arrays.copyOf(data, (int) Math.ceil((double) bitLength / 8));
		int off = bitLength % 8;
		if (off != 0) {
			byte zero = (byte) (0xff << (8 - off));
			result[result.length - 1] &= zero;
		}
		return result;
	}

	@Override
	public byte[] encode(byte[] data, int bitLength) throws BlockCodecException {
		if (bitLength < 0) {
			return data;
		}

		byte[] result = Arrays.copyOf(data, (int) Math.ceil((double) bitLength / 8));
		int off = bitLength % 8;
		if (off != 0) {
			byte zero = (byte) (0xff << (8 - off));
			result[result.length - 1] &= zero;
		}
		return result;
	}

	@Override
	public void reset() {
	}
}
