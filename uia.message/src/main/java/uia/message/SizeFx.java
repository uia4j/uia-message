package uia.message;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

public class SizeFx {

	/**
	 * 
	 * @param func
	 * @param blockValues
	 * @return
	 * @throws Exception
	 */
	public static int calculate(String func, Map<String, Object> blockValues) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(func.getBytes("UTF-8")));
        SizeLexer lexer = new SizeLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SizeParser parser = new SizeParser(tokens);
        parser.blockValues = blockValues;
        return parser.eval();
	}
}
