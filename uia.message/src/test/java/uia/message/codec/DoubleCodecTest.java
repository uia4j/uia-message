/*******************************************************************************
 * * Copyright (c) 2014, UIA
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

import org.junit.Test;
import uia.utils.ByteUtils;

/**
 *
 * @author Kyle
 */
public class DoubleCodecTest {

    public DoubleCodecTest() {
    }

    @Test
    public void testDecode() throws Exception {
        System.out.println("decode");
        byte[] data = new byte[]{
            (byte) 0x40,
            (byte) 0x28,
            (byte) 0x98,
            (byte) 0x93,
            (byte) 0x74,
            (byte) 0xbc,
            (byte) 0x6a,
            (byte) 0x7e
        };
        DoubleCodec codec = new DoubleCodec();
        System.out.println(codec.decode(data, 8));
        System.out.println();
    }

    @Test
    public void testEncode() throws Exception {
        System.out.println("encode");
        DoubleCodec codec = new DoubleCodec();
        byte[] result = codec.encode(new Double(12.298d), 8);
        System.out.println(ByteUtils.toHexString(result));
        System.out.println();
    }
}
