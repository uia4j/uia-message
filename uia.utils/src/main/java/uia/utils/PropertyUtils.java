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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author Kyle
 */
public class PropertyUtils {

	/**
	 * Apply value to specific property of block converter.
	 * 
	 * @param obj The block converter.
	 * @param propName The property name.
	 * @param value The property value.
	 * @throws IntrospectionException bean info exception.
	 * @throws IllegalAccessException bean info exception.
	 * @throws IllegalArgumentException bean info exception.
	 * @throws InvocationTargetException bean info exception.
	 */
	public static boolean write(Object obj, String propName, Object value)
	        throws IntrospectionException,
	        IllegalAccessException,
	        IllegalArgumentException,
	        InvocationTargetException {

		BeanInfo info = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] pds = info.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			if (pd.getName().equals(propName)) {
				pd.getWriteMethod().invoke(obj, value);
				return true;
			}
		}

		return false;
	}

	/**
	 * Retrive value to specific property of block converter.
	 * 
	 * @param obj The block converter.
	 * @param propName The property name.
	 * @return The value.
	 * @throws IntrospectionException bean info exception.
	 * @throws IllegalAccessException bean info exception.
	 * @throws IllegalArgumentException bean info exception.
	 * @throws InvocationTargetException bean info exception.
	 */
	public static Object read(Object obj, String propName)
	        throws IntrospectionException,
	        IllegalAccessException,
	        IllegalArgumentException,
	        InvocationTargetException {

		BeanInfo info = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] pds = info.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			if (pd.getName().equals(propName)) {
				return pd.getReadMethod().invoke(obj);
			}
		}

		return null;
	}
}
