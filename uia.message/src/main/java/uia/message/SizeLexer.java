// $ANTLR 3.5 D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g 2016-05-02 16:27:19

package uia.message;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class SizeLexer extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public SizeLexer() {} 
	public SizeLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public SizeLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g"; }

	// $ANTLR start "T__8"
	public final void mT__8() throws RecognitionException {
		try {
			int _type = T__8;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:12:6: ( '(' )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:12:8: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__8"

	// $ANTLR start "T__9"
	public final void mT__9() throws RecognitionException {
		try {
			int _type = T__9;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:13:6: ( ')' )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:13:8: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__9"

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:14:7: ( '*' )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:14:9: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__10"

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:15:7: ( '+' )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:15:9: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:16:7: ( '-' )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:16:9: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "T__13"
	public final void mT__13() throws RecognitionException {
		try {
			int _type = T__13;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:17:7: ( '/' )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:17:9: '/'
			{
			match('/'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__13"

	// $ANTLR start "Number"
	public final void mNumber() throws RecognitionException {
		try {
			int _type = Number;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:57:5: ( ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )? )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:57:10: ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )?
			{
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:57:10: ( '0' .. '9' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:57:22: ( '.' ( '0' .. '9' )+ )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0=='.') ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:57:23: '.' ( '0' .. '9' )+
					{
					match('.'); 
					// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:57:27: ( '0' .. '9' )+
					int cnt2=0;
					loop2:
					while (true) {
						int alt2=2;
						int LA2_0 = input.LA(1);
						if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
							alt2=1;
						}

						switch (alt2) {
						case 1 :
							// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							if ( cnt2 >= 1 ) break loop2;
							EarlyExitException eee = new EarlyExitException(2, input);
							throw eee;
						}
						cnt2++;
					}

					}
					break;

			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "Number"

	// $ANTLR start "Param"
	public final void mParam() throws RecognitionException {
		try {
			int _type = Param;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:63:5: ( ( Character )+ )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:63:10: ( Character )+
			{
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:63:10: ( Character )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '0' && LA4_0 <= '9')||(LA4_0 >= 'A' && LA4_0 <= 'Z')||LA4_0=='_'||(LA4_0 >= 'a' && LA4_0 <= 'z')) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt4 >= 1 ) break loop4;
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "Param"

	// $ANTLR start "Character"
	public final void mCharacter() throws RecognitionException {
		try {
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:69:5: ( 'A' .. 'Z' | 'a' .. 'z' | '0' .. '9' | '_' )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "Character"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:76:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
			// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:76:9: ( ' ' | '\\t' | '\\r' | '\\n' )
			{
			if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			_channel=HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:1:8: ( T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | Number | Param | WS )
		int alt5=9;
		alt5 = dfa5.predict(input);
		switch (alt5) {
			case 1 :
				// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:1:10: T__8
				{
				mT__8(); 

				}
				break;
			case 2 :
				// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:1:15: T__9
				{
				mT__9(); 

				}
				break;
			case 3 :
				// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:1:20: T__10
				{
				mT__10(); 

				}
				break;
			case 4 :
				// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:1:26: T__11
				{
				mT__11(); 

				}
				break;
			case 5 :
				// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:1:32: T__12
				{
				mT__12(); 

				}
				break;
			case 6 :
				// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:1:38: T__13
				{
				mT__13(); 

				}
				break;
			case 7 :
				// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:1:44: Number
				{
				mNumber(); 

				}
				break;
			case 8 :
				// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:1:51: Param
				{
				mParam(); 

				}
				break;
			case 9 :
				// D:\\Dev\\UIA\\uia.message\\java\\src\\uia.message\\grammar\\Size.g:1:57: WS
				{
				mWS(); 

				}
				break;

		}
	}


	protected DFA5 dfa5 = new DFA5(this);
	static final String DFA5_eotS =
		"\7\uffff\1\12\3\uffff";
	static final String DFA5_eofS =
		"\13\uffff";
	static final String DFA5_minS =
		"\1\11\6\uffff\1\60\3\uffff";
	static final String DFA5_maxS =
		"\1\172\6\uffff\1\172\3\uffff";
	static final String DFA5_acceptS =
		"\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\uffff\1\10\1\11\1\7";
	static final String DFA5_specialS =
		"\13\uffff}>";
	static final String[] DFA5_transitionS = {
			"\2\11\2\uffff\1\11\22\uffff\1\11\7\uffff\1\1\1\2\1\3\1\4\1\uffff\1\5"+
			"\1\uffff\1\6\12\7\7\uffff\32\10\4\uffff\1\10\1\uffff\32\10",
			"",
			"",
			"",
			"",
			"",
			"",
			"\12\7\7\uffff\32\10\4\uffff\1\10\1\uffff\32\10",
			"",
			"",
			""
	};

	static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
	static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
	static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
	static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
	static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
	static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
	static final short[][] DFA5_transition;

	static {
		int numStates = DFA5_transitionS.length;
		DFA5_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
		}
	}

	protected class DFA5 extends DFA {

		public DFA5(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 5;
			this.eot = DFA5_eot;
			this.eof = DFA5_eof;
			this.min = DFA5_min;
			this.max = DFA5_max;
			this.accept = DFA5_accept;
			this.special = DFA5_special;
			this.transition = DFA5_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | Number | Param | WS );";
		}
	}

}
