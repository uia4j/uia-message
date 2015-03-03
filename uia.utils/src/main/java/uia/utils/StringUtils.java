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
package uia.utils;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 
 * @author Kyle
 */
public class StringUtils {

    public static boolean bool(String yn) {
        return "Y".equalsIgnoreCase(yn) || "TRUE".equalsIgnoreCase(yn);
    }

    public static byte[] toBytes(String value, int length, byte empty) {
        return toBytes(value, length, empty, Charset.forName("UTF8"));
    }

    public static byte[] toBytes(String value, int length, byte empty, Charset charset) {
        byte[] temp = value.getBytes(charset);
        if (temp.length == length) {
            return temp;
        }

        byte[] result = new byte[length];
        Arrays.fill(result, empty);
        for (int i = 0, cnt = Math.min(length, temp.length); i < cnt; i++) {
            result[i] = temp[i];
        }

        return result;
    }

}
