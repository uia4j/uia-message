package uia.message;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import uia.utils.ElemArithmetic;

/**
 * 
 * @author Kyle
 *
 */
public class SizeFx {

	/**
	 * Element arithmetic
	 * @param func Function
	 * @param blockValues Block values used to calculate.
	 * @return Result.
	 * @throws Exception Failed.
	 */
	public static int calculate(String func, Map<String, Object> blockValues) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(func.getBytes("UTF-8")));
        SizeLexer lexer = new SizeLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SizeParser parser = new SizeParser(tokens);
        parser.blockValues = blockValues;
        return parser.eval();
	}

	/**
	 * Element arithmetic.
	 * @param func Function
	 * @param blockValues Block values used to calculate.
	 * @return Result.
	 */
	public static int calculate2(String func, Map<String, Object> blockValues) {
		ElemArithmetic ea = new ElemArithmetic(func);
		return (int)ea.calculate(blockValues);
		
	}
}
