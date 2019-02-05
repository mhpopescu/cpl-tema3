// Generated from /home/mike/Documents/cpl/Tema2/src/cool/lexer/CoolLexer.g4 by ANTLR 4.7.2
package cool.lexer;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CoolLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ERROR=1, WS=2, SIMPLE_COMMENT=3, ASSIGN=4, IF=5, THEN=6, ELSE=7, FI=8, 
		NOT_BITWISE=9, NOT_BOOLEAN=10, NEW=11, IS_VOID=12, WHILE=13, LOOP=14, 
		POOL=15, LET=16, CASE=17, ESAC=18, OF=19, IN=20, IS=21, CLASS_SYM=22, 
		INHERITS=23, BOOL=24, INT=25, TYPE=26, ID=27, AT=28, DOT=29, LESS=30, 
		LESS_EQ=31, MINUS=32, PLUS=33, MULTIPLY=34, DIVIDE=35, EQUAL=36, DELIM=37, 
		INIT=38, COMMA=39, OPEN_P=40, CLOSED_P=41, OPEN_B=42, CLOSED_B=43, STRING=44, 
		BLOCK_COMMENT=45, UNMATCH_COMM=46, INVALID=47;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WS", "SIMPLE_COMMENT", "ASSIGN", "IF", "THEN", "ELSE", "FI", "NOT_BITWISE", 
			"NOT_BOOLEAN", "NEW", "IS_VOID", "WHILE", "LOOP", "POOL", "LET", "CASE", 
			"ESAC", "OF", "IN", "IS", "CLASS_SYM", "INHERITS", "BOOL", "DIGIT", "INT", 
			"LETTER", "ALFA_NUM", "TYPE", "ID", "AT", "DOT", "LESS", "LESS_EQ", "MINUS", 
			"PLUS", "MULTIPLY", "DIVIDE", "EQUAL", "DELIM", "INIT", "COMMA", "OPEN_P", 
			"CLOSED_P", "OPEN_B", "CLOSED_B", "STRING", "BLOCK_COMMENT", "UNMATCH_COMM", 
			"INVALID"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'<-'", "'if'", "'then'", "'else'", "'fi'", "'~'", 
			"'not'", "'new'", "'isvoid'", "'while'", "'loop'", "'pool'", "'let'", 
			"'case'", "'esac'", "'of'", "'in'", "'=>'", "'class'", "'inherits'", 
			null, null, null, null, "'@'", "'.'", "'<'", "'<='", "'-'", "'+'", "'*'", 
			"'/'", "'='", "';'", "':'", "','", "'('", "')'", "'{'", "'}'", null, 
			null, "'*)'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ERROR", "WS", "SIMPLE_COMMENT", "ASSIGN", "IF", "THEN", "ELSE", 
			"FI", "NOT_BITWISE", "NOT_BOOLEAN", "NEW", "IS_VOID", "WHILE", "LOOP", 
			"POOL", "LET", "CASE", "ESAC", "OF", "IN", "IS", "CLASS_SYM", "INHERITS", 
			"BOOL", "INT", "TYPE", "ID", "AT", "DOT", "LESS", "LESS_EQ", "MINUS", 
			"PLUS", "MULTIPLY", "DIVIDE", "EQUAL", "DELIM", "INIT", "COMMA", "OPEN_P", 
			"CLOSED_P", "OPEN_B", "CLOSED_B", "STRING", "BLOCK_COMMENT", "UNMATCH_COMM", 
			"INVALID"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	    
	    private void raiseError(String msg) {
	        setText(msg);
	        setType(ERROR);
	    }


	public CoolLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CoolLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 45:
			STRING_action((RuleContext)_localctx, actionIndex);
			break;
		case 46:
			BLOCK_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 47:
			UNMATCH_COMM_action((RuleContext)_localctx, actionIndex);
			break;
		case 48:
			INVALID_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void STRING_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:

			    String s = getText();

			    if (s.contains("\0")) {
			        raiseError("String contains null character");
			        break;
			    }

			    if (s.length() > 1)
			    s = s.substring(1, s.length() - 1);
			    s = s.replace("\\\\", "\0");

			    s = s.replace("\\t", "\t");
			    s = s.replace("\\n", "\n");
			    s = s.replace("\\b", "\b");
			    s = s.replace("\\f", "\f");
			    s = s.replace("\\", "");
			    s = s.replace("\0", "\\");
			    setText(s);

			    if (s.length() >= 1024) {
			        raiseError("String constant too long");
			        break;
			    }

			break;
		case 1:

			    raiseError("Unterminated string constant");

			break;
		case 2:

			    raiseError("EOF in string constant");

			break;
		}
	}
	private void BLOCK_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			skip();
			break;
		case 4:
			raiseError("EOF in comment");
			break;
		}
	}
	private void UNMATCH_COMM_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 5:
			raiseError("Unmatched *)");
			break;
		}
	}
	private void INVALID_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 6:

			        raiseError("Invalid character: " + getText());
			    
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\61\u0165\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\3\2\6\2g\n\2\r"+
		"\2\16\2h\3\2\3\2\3\3\3\3\3\3\3\3\7\3q\n\3\f\3\16\3t\13\3\3\3\3\3\3\3\5"+
		"\3y\n\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3"+
		"\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3"+
		"\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3"+
		"\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3"+
		"\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u00e0\n\30"+
		"\3\31\3\31\3\32\5\32\u00e5\n\32\3\32\6\32\u00e8\n\32\r\32\16\32\u00e9"+
		"\3\33\3\33\3\34\3\34\3\34\5\34\u00f1\n\34\3\35\3\35\7\35\u00f5\n\35\f"+
		"\35\16\35\u00f8\13\35\3\36\3\36\7\36\u00fc\n\36\f\36\16\36\u00ff\13\36"+
		"\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3("+
		"\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3/\3/\3/\3/\3/\3/\3/\7/"+
		"\u012b\n/\f/\16/\u012e\13/\3/\3/\3/\3/\3/\3/\7/\u0136\n/\f/\16/\u0139"+
		"\13/\3/\3/\3/\5/\u013e\n/\3/\3/\3/\7/\u0143\n/\f/\16/\u0146\13/\3/\3/"+
		"\5/\u014a\n/\3\60\3\60\3\60\3\60\3\60\7\60\u0151\n\60\f\60\16\60\u0154"+
		"\13\60\3\60\3\60\3\60\3\60\3\60\3\60\5\60\u015c\n\60\3\61\3\61\3\61\3"+
		"\61\3\61\3\62\3\62\3\62\7r\u012c\u0137\u0144\u0152\2\63\3\4\5\5\7\6\t"+
		"\7\13\b\r\t\17\n\21\13\23\f\25\r\27\16\31\17\33\20\35\21\37\22!\23#\24"+
		"%\25\'\26)\27+\30-\31/\32\61\2\63\33\65\2\67\29\34;\35=\36?\37A C!E\""+
		"G#I$K%M&O\'Q(S)U*W+Y,[-]._/a\60c\61\3\2\b\5\2\13\f\17\17\"\"\3\2\62;\4"+
		"\2C\\c|\3\2C\\\3\2c|\3\2\f\f\2\u0178\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2"+
		"\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\63\3\2\2\2\29\3\2\2\2\2;\3\2\2"+
		"\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2"+
		"I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3"+
		"\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2"+
		"\2\2c\3\2\2\2\3f\3\2\2\2\5l\3\2\2\2\7|\3\2\2\2\t\177\3\2\2\2\13\u0082"+
		"\3\2\2\2\r\u0087\3\2\2\2\17\u008c\3\2\2\2\21\u008f\3\2\2\2\23\u0091\3"+
		"\2\2\2\25\u0095\3\2\2\2\27\u0099\3\2\2\2\31\u00a0\3\2\2\2\33\u00a6\3\2"+
		"\2\2\35\u00ab\3\2\2\2\37\u00b0\3\2\2\2!\u00b4\3\2\2\2#\u00b9\3\2\2\2%"+
		"\u00be\3\2\2\2\'\u00c1\3\2\2\2)\u00c4\3\2\2\2+\u00c7\3\2\2\2-\u00cd\3"+
		"\2\2\2/\u00df\3\2\2\2\61\u00e1\3\2\2\2\63\u00e4\3\2\2\2\65\u00eb\3\2\2"+
		"\2\67\u00f0\3\2\2\29\u00f2\3\2\2\2;\u00f9\3\2\2\2=\u0100\3\2\2\2?\u0102"+
		"\3\2\2\2A\u0104\3\2\2\2C\u0106\3\2\2\2E\u0109\3\2\2\2G\u010b\3\2\2\2I"+
		"\u010d\3\2\2\2K\u010f\3\2\2\2M\u0111\3\2\2\2O\u0113\3\2\2\2Q\u0115\3\2"+
		"\2\2S\u0117\3\2\2\2U\u0119\3\2\2\2W\u011b\3\2\2\2Y\u011d\3\2\2\2[\u011f"+
		"\3\2\2\2]\u0149\3\2\2\2_\u014b\3\2\2\2a\u015d\3\2\2\2c\u0162\3\2\2\2e"+
		"g\t\2\2\2fe\3\2\2\2gh\3\2\2\2hf\3\2\2\2hi\3\2\2\2ij\3\2\2\2jk\b\2\2\2"+
		"k\4\3\2\2\2lm\7/\2\2mn\7/\2\2nr\3\2\2\2oq\13\2\2\2po\3\2\2\2qt\3\2\2\2"+
		"rs\3\2\2\2rp\3\2\2\2sx\3\2\2\2tr\3\2\2\2uv\7\17\2\2vy\7\f\2\2wy\7\f\2"+
		"\2xu\3\2\2\2xw\3\2\2\2yz\3\2\2\2z{\b\3\2\2{\6\3\2\2\2|}\7>\2\2}~\7/\2"+
		"\2~\b\3\2\2\2\177\u0080\7k\2\2\u0080\u0081\7h\2\2\u0081\n\3\2\2\2\u0082"+
		"\u0083\7v\2\2\u0083\u0084\7j\2\2\u0084\u0085\7g\2\2\u0085\u0086\7p\2\2"+
		"\u0086\f\3\2\2\2\u0087\u0088\7g\2\2\u0088\u0089\7n\2\2\u0089\u008a\7u"+
		"\2\2\u008a\u008b\7g\2\2\u008b\16\3\2\2\2\u008c\u008d\7h\2\2\u008d\u008e"+
		"\7k\2\2\u008e\20\3\2\2\2\u008f\u0090\7\u0080\2\2\u0090\22\3\2\2\2\u0091"+
		"\u0092\7p\2\2\u0092\u0093\7q\2\2\u0093\u0094\7v\2\2\u0094\24\3\2\2\2\u0095"+
		"\u0096\7p\2\2\u0096\u0097\7g\2\2\u0097\u0098\7y\2\2\u0098\26\3\2\2\2\u0099"+
		"\u009a\7k\2\2\u009a\u009b\7u\2\2\u009b\u009c\7x\2\2\u009c\u009d\7q\2\2"+
		"\u009d\u009e\7k\2\2\u009e\u009f\7f\2\2\u009f\30\3\2\2\2\u00a0\u00a1\7"+
		"y\2\2\u00a1\u00a2\7j\2\2\u00a2\u00a3\7k\2\2\u00a3\u00a4\7n\2\2\u00a4\u00a5"+
		"\7g\2\2\u00a5\32\3\2\2\2\u00a6\u00a7\7n\2\2\u00a7\u00a8\7q\2\2\u00a8\u00a9"+
		"\7q\2\2\u00a9\u00aa\7r\2\2\u00aa\34\3\2\2\2\u00ab\u00ac\7r\2\2\u00ac\u00ad"+
		"\7q\2\2\u00ad\u00ae\7q\2\2\u00ae\u00af\7n\2\2\u00af\36\3\2\2\2\u00b0\u00b1"+
		"\7n\2\2\u00b1\u00b2\7g\2\2\u00b2\u00b3\7v\2\2\u00b3 \3\2\2\2\u00b4\u00b5"+
		"\7e\2\2\u00b5\u00b6\7c\2\2\u00b6\u00b7\7u\2\2\u00b7\u00b8\7g\2\2\u00b8"+
		"\"\3\2\2\2\u00b9\u00ba\7g\2\2\u00ba\u00bb\7u\2\2\u00bb\u00bc\7c\2\2\u00bc"+
		"\u00bd\7e\2\2\u00bd$\3\2\2\2\u00be\u00bf\7q\2\2\u00bf\u00c0\7h\2\2\u00c0"+
		"&\3\2\2\2\u00c1\u00c2\7k\2\2\u00c2\u00c3\7p\2\2\u00c3(\3\2\2\2\u00c4\u00c5"+
		"\7?\2\2\u00c5\u00c6\7@\2\2\u00c6*\3\2\2\2\u00c7\u00c8\7e\2\2\u00c8\u00c9"+
		"\7n\2\2\u00c9\u00ca\7c\2\2\u00ca\u00cb\7u\2\2\u00cb\u00cc\7u\2\2\u00cc"+
		",\3\2\2\2\u00cd\u00ce\7k\2\2\u00ce\u00cf\7p\2\2\u00cf\u00d0\7j\2\2\u00d0"+
		"\u00d1\7g\2\2\u00d1\u00d2\7t\2\2\u00d2\u00d3\7k\2\2\u00d3\u00d4\7v\2\2"+
		"\u00d4\u00d5\7u\2\2\u00d5.\3\2\2\2\u00d6\u00d7\7v\2\2\u00d7\u00d8\7t\2"+
		"\2\u00d8\u00d9\7w\2\2\u00d9\u00e0\7g\2\2\u00da\u00db\7h\2\2\u00db\u00dc"+
		"\7c\2\2\u00dc\u00dd\7n\2\2\u00dd\u00de\7u\2\2\u00de\u00e0\7g\2\2\u00df"+
		"\u00d6\3\2\2\2\u00df\u00da\3\2\2\2\u00e0\60\3\2\2\2\u00e1\u00e2\t\3\2"+
		"\2\u00e2\62\3\2\2\2\u00e3\u00e5\7-\2\2\u00e4\u00e3\3\2\2\2\u00e4\u00e5"+
		"\3\2\2\2\u00e5\u00e7\3\2\2\2\u00e6\u00e8\5\61\31\2\u00e7\u00e6\3\2\2\2"+
		"\u00e8\u00e9\3\2\2\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\64"+
		"\3\2\2\2\u00eb\u00ec\t\4\2\2\u00ec\66\3\2\2\2\u00ed\u00f1\5\65\33\2\u00ee"+
		"\u00f1\7a\2\2\u00ef\u00f1\5\61\31\2\u00f0\u00ed\3\2\2\2\u00f0\u00ee\3"+
		"\2\2\2\u00f0\u00ef\3\2\2\2\u00f18\3\2\2\2\u00f2\u00f6\t\5\2\2\u00f3\u00f5"+
		"\5\67\34\2\u00f4\u00f3\3\2\2\2\u00f5\u00f8\3\2\2\2\u00f6\u00f4\3\2\2\2"+
		"\u00f6\u00f7\3\2\2\2\u00f7:\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f9\u00fd\t"+
		"\6\2\2\u00fa\u00fc\5\67\34\2\u00fb\u00fa\3\2\2\2\u00fc\u00ff\3\2\2\2\u00fd"+
		"\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe<\3\2\2\2\u00ff\u00fd\3\2\2\2"+
		"\u0100\u0101\7B\2\2\u0101>\3\2\2\2\u0102\u0103\7\60\2\2\u0103@\3\2\2\2"+
		"\u0104\u0105\7>\2\2\u0105B\3\2\2\2\u0106\u0107\7>\2\2\u0107\u0108\7?\2"+
		"\2\u0108D\3\2\2\2\u0109\u010a\7/\2\2\u010aF\3\2\2\2\u010b\u010c\7-\2\2"+
		"\u010cH\3\2\2\2\u010d\u010e\7,\2\2\u010eJ\3\2\2\2\u010f\u0110\7\61\2\2"+
		"\u0110L\3\2\2\2\u0111\u0112\7?\2\2\u0112N\3\2\2\2\u0113\u0114\7=\2\2\u0114"+
		"P\3\2\2\2\u0115\u0116\7<\2\2\u0116R\3\2\2\2\u0117\u0118\7.\2\2\u0118T"+
		"\3\2\2\2\u0119\u011a\7*\2\2\u011aV\3\2\2\2\u011b\u011c\7+\2\2\u011cX\3"+
		"\2\2\2\u011d\u011e\7}\2\2\u011eZ\3\2\2\2\u011f\u0120\7\177\2\2\u0120\\"+
		"\3\2\2\2\u0121\u012c\7$\2\2\u0122\u0123\7^\2\2\u0123\u012b\7$\2\2\u0124"+
		"\u0125\7^\2\2\u0125\u0126\7\17\2\2\u0126\u012b\7\f\2\2\u0127\u0128\7^"+
		"\2\2\u0128\u012b\7\f\2\2\u0129\u012b\n\7\2\2\u012a\u0122\3\2\2\2\u012a"+
		"\u0124\3\2\2\2\u012a\u0127\3\2\2\2\u012a\u0129\3\2\2\2\u012b\u012e\3\2"+
		"\2\2\u012c\u012d\3\2\2\2\u012c\u012a\3\2\2\2\u012d\u012f\3\2\2\2\u012e"+
		"\u012c\3\2\2\2\u012f\u0130\7$\2\2\u0130\u014a\b/\3\2\u0131\u0137\7$\2"+
		"\2\u0132\u0133\7^\2\2\u0133\u0136\7$\2\2\u0134\u0136\13\2\2\2\u0135\u0132"+
		"\3\2\2\2\u0135\u0134\3\2\2\2\u0136\u0139\3\2\2\2\u0137\u0138\3\2\2\2\u0137"+
		"\u0135\3\2\2\2\u0138\u013d\3\2\2\2\u0139\u0137\3\2\2\2\u013a\u013b\7\17"+
		"\2\2\u013b\u013e\7\f\2\2\u013c\u013e\7\f\2\2\u013d\u013a\3\2\2\2\u013d"+
		"\u013c\3\2\2\2\u013e\u013f\3\2\2\2\u013f\u014a\b/\4\2\u0140\u0144\7$\2"+
		"\2\u0141\u0143\13\2\2\2\u0142\u0141\3\2\2\2\u0143\u0146\3\2\2\2\u0144"+
		"\u0145\3\2\2\2\u0144\u0142\3\2\2\2\u0145\u0147\3\2\2\2\u0146\u0144\3\2"+
		"\2\2\u0147\u0148\7\2\2\3\u0148\u014a\b/\5\2\u0149\u0121\3\2\2\2\u0149"+
		"\u0131\3\2\2\2\u0149\u0140\3\2\2\2\u014a^\3\2\2\2\u014b\u014c\7*\2\2\u014c"+
		"\u014d\7,\2\2\u014d\u0152\3\2\2\2\u014e\u0151\5_\60\2\u014f\u0151\13\2"+
		"\2\2\u0150\u014e\3\2\2\2\u0150\u014f\3\2\2\2\u0151\u0154\3\2\2\2\u0152"+
		"\u0153\3\2\2\2\u0152\u0150\3\2\2\2\u0153\u015b\3\2\2\2\u0154\u0152\3\2"+
		"\2\2\u0155\u0156\7,\2\2\u0156\u0157\7+\2\2\u0157\u0158\3\2\2\2\u0158\u015c"+
		"\b\60\6\2\u0159\u015a\7\2\2\3\u015a\u015c\b\60\7\2\u015b\u0155\3\2\2\2"+
		"\u015b\u0159\3\2\2\2\u015c`\3\2\2\2\u015d\u015e\7,\2\2\u015e\u015f\7+"+
		"\2\2\u015f\u0160\3\2\2\2\u0160\u0161\b\61\b\2\u0161b\3\2\2\2\u0162\u0163"+
		"\13\2\2\2\u0163\u0164\b\62\t\2\u0164d\3\2\2\2\26\2hrx\u00df\u00e4\u00e9"+
		"\u00f0\u00f6\u00fd\u012a\u012c\u0135\u0137\u013d\u0144\u0149\u0150\u0152"+
		"\u015b\n\b\2\2\3/\2\3/\3\3/\4\3\60\5\3\60\6\3\61\7\3\62\b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}