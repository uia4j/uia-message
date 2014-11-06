package cases;

import java.io.File;

import org.junit.Test;

import uia.message.DataExFactory;
import uia.message.MessageDeserializer;

public class HelloTest {

	@Test
	public void testRead() throws Exception {
		String domainName = "Cases";
		File schemaFile = new File(HelloTest.class.getResource("hello.xml").toURI());
		DataExFactory.register(domainName, schemaFile);

		String messageName = "Hello";
		DataExFactory f = DataExFactory.getFactory(domainName);
		MessageDeserializer reader = f.createDeserializer(messageName);

		byte[] data = new byte[] {
		        (byte) 0x41, // header: 8 bytes, value 'ABCDEFGH'
		        (byte) 0x42,
		        (byte) 0x43,
		        (byte) 0x44,
		        (byte) 0x45,
		        (byte) 0x46,
		        (byte) 0x47,
		        (byte) 0x48,
		        (byte) 0x00, // number: 4 bytes, value 1024
		        (byte) 0x00,
		        (byte) 0x04,
		        (byte) 0x00,
		        (byte) 0xff // tailer: 1 bytes, 0xff
		};
		Hello hello = (Hello) reader.read(data);
		System.out.println(new String(hello.getHeader()));
		System.out.println(hello.getNumber());
		System.out.println(hello.getTailer());
	}
}