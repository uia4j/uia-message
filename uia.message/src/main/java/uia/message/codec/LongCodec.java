/*******************************************************************************
 * * Copyright (c) 2015, UIA
 * * All rights reserved.
 * * Redistribution and use in source and binary forms, with or without
 * * modification, are permitted provided that the following conditions are met:
 * *
 * *     * Redistributions of source code must retain the above copyright
 * *       notice, this list of conditions and the following disclaimer.
 * *     * Redistributions in binary form must reproduce the above copyright
 * *       notice, this list of conditions and the following disclaimer in the
 * *       documentation and/or other materials provided with the distribution.
 * *     * Neither the name of the {company name} nor the
 * *       names of its contributors may be used to endorse or promote products
 * *       derived from this software without specific prior written permission.
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

import uia.utils.BooleanUtils;
import uia.utils.ByteUtils;
import uia.utils.StringUtils;

/**
 * Convert data between long and byte array. <br />
 * 
 * @author Kyle
 */
public class LongCodec implements BlockCodec<Long> {

	private final boolean orig;

	private boolean unsigned;

	public LongCodec() {
		this(false);
	}

	public LongCodec(boolean unsigned) {
		this.unsigned = unsigned;
		this.orig = this.unsigned;
	}

	public void setUnsigned(String yn) {
		this.unsigned = StringUtils.bool(yn);
	}

	public String getUnsigned() {
		return BooleanUtils.toYN(this.unsigned);
	}

	@Override
	public Long decode(byte[] data, int bitLength) throws BlockCodecException {
		try {
			return new Long(ByteUtils.longValue(data, bitLength));
		} catch (Exception ex) {
			throw new BlockCodecException("long decode failure. " + ex.getMessage(), ex);
		}
	}

	@Override
	public byte[] encode(Long data, int bitLength) throws BlockCodecException {
		long value = data.longValue();
		try {
			byte[] temp = new byte[] {
			        (byte) (value >> 56),
			        (byte) (value >> 48),
			        (byte) (value >> 40),
			        (byte) (value >> 32),
			        (byte) (value >> 24),
			        (byte) (value >> 16),
			        (byte) (value >> 8),
			        (byte) value
			};

			int byteStart = 8 - (int) Math.ceil((double) bitLength / 8);
			int bitStart = bitLength % 8;
			if (bitStart != 0) {
				bitStart = 8 - bitStart;
			}
			return ByteUtils.copyBits(temp, byteStart, bitStart, bitLength);
		} catch (Exception ex) {
			throw new BlockCodecException("long encode failure. " + ex.getMessage(), ex);
		}
	}

	@Override
	public void reset() {
		this.unsigned = this.orig;
	}
}
