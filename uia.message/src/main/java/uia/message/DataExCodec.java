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
package uia.message;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import uia.message.model.xml.DataExType;

/**
 * Data exchange codec.
 * 
 * @author kyle
 */
class DataExCodec {

	static Unmarshaller UNMARSHALLER;

	static DataExType decode(File file) throws Exception {
		Scanner freader = new Scanner(file);
		StringBuilder content = new StringBuilder();
		while (freader.hasNextLine()) {
			content.append(freader.nextLine().trim());
		}
		freader.close();
		return decode(content.toString());
	}

	static DataExType decode(InputStream stream) throws Exception {
		if (UNMARSHALLER == null) {
			initial();
		}

		// Create the XMLReader
		SAXParserFactory factory = SAXParserFactory.newInstance();
		XMLReader reader = factory.newSAXParser().getXMLReader();

		// The filter class to set the correct namespace
		XMLFilterImpl xmlFilter = new XMLNamespaceFilter(reader);
		reader.setContentHandler(UNMARSHALLER.getUnmarshallerHandler());

		SAXSource source = new SAXSource(xmlFilter, new InputSource(stream));

		@SuppressWarnings("unchecked")
		JAXBElement<DataExType> elem = (JAXBElement<DataExType>) UNMARSHALLER.unmarshal(source);
		return elem.getValue();
	}

	static DataExType decode(String content) throws Exception {
		if (UNMARSHALLER == null) {
			initial();
		}

		// Create the XMLReader
		SAXParserFactory factory = SAXParserFactory.newInstance();
		XMLReader reader = factory.newSAXParser().getXMLReader();

		// The filter class to set the correct namespace
		XMLFilterImpl xmlFilter = new XMLNamespaceFilter(reader);
		reader.setContentHandler(UNMARSHALLER.getUnmarshallerHandler());

		InputStream inStream = new ByteArrayInputStream(content.getBytes("UTF-8"));
		SAXSource source = new SAXSource(xmlFilter, new InputSource(inStream));

		@SuppressWarnings("unchecked")
		JAXBElement<DataExType> elem = (JAXBElement<DataExType>) UNMARSHALLER.unmarshal(source);
		return elem.getValue();
	}

	static void initial() throws Exception {
		try {
			JAXBContext jc = JAXBContext.newInstance("uia.message.model.xml");
			UNMARSHALLER = jc.createUnmarshaller();
		} catch (JAXBException ex) {
			UNMARSHALLER = null;
			throw ex;
		}
	}

	static class XMLNamespaceFilter extends XMLFilterImpl {

		public XMLNamespaceFilter(XMLReader reader) {
			super(reader);
		}

		@Override
		public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException {
			super.startElement("http://message.uia/model/xml", localName, qName, attributes);
		}
	}
}
