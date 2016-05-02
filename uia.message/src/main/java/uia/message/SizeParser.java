// $ANTLR 3.5 D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g 2016-05-02 16:27:19

package uia.message;

import java.util.Map;
import java.util.HashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class SizeParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "Character", "Number", "Param", 
		"WS", "'('", "')'", "'*'", "'+'", "'-'", "'/'"
	};
	public static final int EOF=-1;
	public static final int T__8=8;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int Character=4;
	public static final int Number=5;
	public static final int Param=6;
	public static final int WS=7;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public SizeParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public SizeParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return SizeParser.tokenNames; }
	@Override public String getGrammarFileName() { return "D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g"; }


	public Map<String, Object> blockValues = new HashMap<String, Object>();



	// $ANTLR start "eval"
	// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:26:1: eval returns [int value] : e= additionExp ;
	public final int eval() throws RecognitionException  {
		int value = 0;


		int e =0;

		try {
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:27:5: (e= additionExp )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:27:10: e= additionExp
			{
			pushFollow(FOLLOW_additionExp_in_eval67);
			e=additionExp();
			state._fsp--;

			 value = e; 
			}

		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "eval"



	// $ANTLR start "additionExp"
	// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:31:1: additionExp returns [int value] : e1= multiplyExp ( '+' e2= multiplyExp | '-' e3= multiplyExp )* ;
	public final int additionExp() {
		int value = 0;


		int e1 =0;
		int e2 =0;
		int e3 =0;

		try {
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:32:5: (e1= multiplyExp ( '+' e2= multiplyExp | '-' e3= multiplyExp )* )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:32:10: e1= multiplyExp ( '+' e2= multiplyExp | '-' e3= multiplyExp )*
			{
			pushFollow(FOLLOW_multiplyExp_in_additionExp99);
			e1=multiplyExp();
			state._fsp--;

			 value = e1; 
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:33:10: ( '+' e2= multiplyExp | '-' e3= multiplyExp )*
			loop1:
			while (true) {
				int alt1=3;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==11) ) {
					alt1=1;
				}
				else if ( (LA1_0==12) ) {
					alt1=2;
				}

				switch (alt1) {
				case 1 :
					// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:33:12: '+' e2= multiplyExp
					{
					match(input,11,FOLLOW_11_in_additionExp115); 
					pushFollow(FOLLOW_multiplyExp_in_additionExp119);
					e2=multiplyExp();
					state._fsp--;

					 value += e2; 
					}
					break;
				case 2 :
					// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:34:12: '-' e3= multiplyExp
					{
					match(input,12,FOLLOW_12_in_additionExp136); 
					pushFollow(FOLLOW_multiplyExp_in_additionExp140);
					e3=multiplyExp();
					state._fsp--;

					 value -= e3; 
					}
					break;

				default :
					break loop1;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "additionExp"



	// $ANTLR start "multiplyExp"
	// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:39:1: multiplyExp returns [int value] : e1= atomExp ( '*' e2= atomExp | '/' e3= atomExp )* ;
	public final int multiplyExp() {
		int value = 0;


		int e1 =0;
		int e2 =0;
		int e3 =0;

		try {
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:40:5: (e1= atomExp ( '*' e2= atomExp | '/' e3= atomExp )* )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:40:10: e1= atomExp ( '*' e2= atomExp | '/' e3= atomExp )*
			{
			pushFollow(FOLLOW_atomExp_in_multiplyExp184);
			e1=atomExp();
			state._fsp--;

			 value = e1; 
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:41:10: ( '*' e2= atomExp | '/' e3= atomExp )*
			loop2:
			while (true) {
				int alt2=3;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==10) ) {
					alt2=1;
				}
				else if ( (LA2_0==13) ) {
					alt2=2;
				}

				switch (alt2) {
				case 1 :
					// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:41:12: '*' e2= atomExp
					{
					match(input,10,FOLLOW_10_in_multiplyExp201); 
					pushFollow(FOLLOW_atomExp_in_multiplyExp205);
					e2=atomExp();
					state._fsp--;

					 value *= e2; 
					}
					break;
				case 2 :
					// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:42:12: '/' e3= atomExp
					{
					match(input,13,FOLLOW_13_in_multiplyExp221); 
					pushFollow(FOLLOW_atomExp_in_multiplyExp225);
					e3=atomExp();
					state._fsp--;

					 value /= e3; 
					}
					break;

				default :
					break loop2;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "multiplyExp"



	// $ANTLR start "atomExp"
	// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:49:1: atomExp returns [int value] : (e1= Number |e2= Param | '(' e3= additionExp ')' );
	public final int atomExp() {
		int value = 0;


		Token e1=null;
		Token e2=null;
		int e3 =0;

		try {
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:50:5: (e1= Number |e2= Param | '(' e3= additionExp ')' )
			int alt3=3;
			switch ( input.LA(1) ) {
			case Number:
				{
				alt3=1;
				}
				break;
			case Param:
				{
				alt3=2;
				}
				break;
			case 8:
				{
				alt3=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}
			switch (alt3) {
				case 1 :
					// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:50:10: e1= Number
					{
					e1=(Token)match(input,Number,FOLLOW_Number_in_atomExp269); 
					 value = Integer.parseInt(e1.getText()); 
					}
					break;
				case 2 :
					// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:51:10: e2= Param
					{
					e2=(Token)match(input,Param,FOLLOW_Param_in_atomExp285); 
					 value = Integer.parseInt("" + blockValues.get(e2.getText())); 
					}
					break;
				case 3 :
					// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:52:10: '(' e3= additionExp ')'
					{
					match(input,8,FOLLOW_8_in_atomExp299); 
					pushFollow(FOLLOW_additionExp_in_atomExp303);
					e3=additionExp();
					state._fsp--;

					match(input,9,FOLLOW_9_in_atomExp305); 
					 value = e3; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "atomExp"

	// Delegated rules



	public static final BitSet FOLLOW_additionExp_in_eval67 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_multiplyExp_in_additionExp99 = new BitSet(new long[]{0x0000000000001802L});
	public static final BitSet FOLLOW_11_in_additionExp115 = new BitSet(new long[]{0x0000000000000160L});
	public static final BitSet FOLLOW_multiplyExp_in_additionExp119 = new BitSet(new long[]{0x0000000000001802L});
	public static final BitSet FOLLOW_12_in_additionExp136 = new BitSet(new long[]{0x0000000000000160L});
	public static final BitSet FOLLOW_multiplyExp_in_additionExp140 = new BitSet(new long[]{0x0000000000001802L});
	public static final BitSet FOLLOW_atomExp_in_multiplyExp184 = new BitSet(new long[]{0x0000000000002402L});
	public static final BitSet FOLLOW_10_in_multiplyExp201 = new BitSet(new long[]{0x0000000000000160L});
	public static final BitSet FOLLOW_atomExp_in_multiplyExp205 = new BitSet(new long[]{0x0000000000002402L});
	public static final BitSet FOLLOW_13_in_multiplyExp221 = new BitSet(new long[]{0x0000000000000160L});
	public static final BitSet FOLLOW_atomExp_in_multiplyExp225 = new BitSet(new long[]{0x0000000000002402L});
	public static final BitSet FOLLOW_Number_in_atomExp269 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Param_in_atomExp285 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_8_in_atomExp299 = new BitSet(new long[]{0x0000000000000160L});
	public static final BitSet FOLLOW_additionExp_in_atomExp303 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_atomExp305 = new BitSet(new long[]{0x0000000000000002L});
}
