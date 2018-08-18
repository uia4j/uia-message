package wafermap;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

import uia.message.DataExFactory;

public class DataSchemaTest {

    @Test
    public void test() throws Exception {
        // file
        File file = new File("data/WAFERMAP1");
        byte[] fileData = new byte[(int) file.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        dis.readFully(fileData);
        dis.close();

        DataExFactory.register("WaferMap", DataSchemaTest.class.getResourceAsStream("DataSchema.xml"));

        WaferInfo result = DataExFactory.deserialize("WaferMap", "Normal", fileData);
        System.out.println(result);
        result.print();
    }
}