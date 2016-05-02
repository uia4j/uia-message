package uia.message;

import java.util.HashMap;

import org.junit.Test;

public class SizeFxTest {

	@Test
	public void test() throws Exception {
		HashMap<String, Object> blockValues = new HashMap<String, Object>();
		blockValues.put("dataLen1", 20);
		blockValues.put("dataLen2", 5);
		
		System.out.println(SizeFx.calculate("dataLen1*10", blockValues));
		
	}
}

