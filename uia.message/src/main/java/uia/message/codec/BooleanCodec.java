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

import uia.utils.ByteUtils;

/**
 * Convert byte[] to boolean.
 * 
 * @author Kyle
 */
public class BooleanCodec implements BlockCodec<Boolean> {

	private int trueValue;

	private int falseValue;

	public BooleanCodec() {
		this.trueValue = 0x01;
		this.falseValue = 0x00;
	}

	public String getTrueValue() {
		return Integer.toString(this.trueValue);
	}

	public void setTrueValue(String value) {
		this.trueValue = Integer.parseInt(value);
	}

	public String getFalseValue() {
		return Integer.toString(this.falseValue);
	}

	public void setFalseValue(String value) {
		this.falseValue = Integer.parseInt(value);
	}

	@Override
	public Boolean decode(byte[] data, int bitLength) throws BlockCodecException {
		try {
			return ByteUtils.intValue(data, bitLength, true) == this.trueValue;
		} catch (Exception ex) {
			throw new BlockCodecException("boolean codec failure. " + ex.getMessage(), ex);
		}
	}

	@Override
	public byte[] encode(Boolean data, int bitLength) throws BlockCodecException {
		int value = Boolean.TRUE.equals(data) ? this.trueValue : this.falseValue;
		IntegerCodec ic = new IntegerCodec(true);
		return ic.encode(value, bitLength);
	}

	@Override
	public void reset() {
		this.trueValue = 0x00;
		this.falseValue = 0x01;
	}
}
