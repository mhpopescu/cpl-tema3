lexer grammar CoolLexer;

tokens { ERROR } 

//@header{
//    package cool.lexer;
//}

@members{    
    private void raiseError(String msg) {
        setText(msg);
        setType(ERROR);
    }
}



WS : [ \t\r\n]+ -> skip;
SIMPLE_COMMENT : '--' .*? ('\r\n' | '\n') -> skip;


ASSIGN : '<-';
IF : 'if';
THEN : 'then';
ELSE : 'else';
FI : 'fi';
NOT_BITWISE : '~';
NOT_BOOLEAN : 'not';
NEW : 'new';
IS_VOID : 'isvoid';
WHILE : 'while';
LOOP : 'loop';
POOL : 'pool';
LET : 'let';
CASE : 'case';
ESAC : 'esac';
OF : 'of';
IN : 'in';
IS : '=>';

CLASS_SYM : 'class';
INHERITS : 'inherits';
BOOL : 'true' | 'false' ;

fragment DIGIT : [0-9];
INT : ('+'?)DIGIT+;

fragment LETTER: [a-zA-Z];
fragment ALFA_NUM : (LETTER | '_' | DIGIT);
TYPE : [A-Z] ALFA_NUM*; // has to start with upper case letter
ID : [a-z] ALFA_NUM*;

AT : '@';
DOT : '.';
LESS : '<';
LESS_EQ : '<=';
MINUS : '-';
PLUS : '+';
MULTIPLY : '*';
DIVIDE : '/';
EQUAL : '=';
DELIM : ';';
INIT : ':';
COMMA : ',';
OPEN_P : '(';
CLOSED_P : ')';
OPEN_B : '{';
CLOSED_B : '}';


STRING: '"' ( '\\"' | '\\\r\n' | '\\\n' | ~[\n])*? '"' // string
{
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
}
    | '"' ( '\\"' | .)*? ('\r\n' | '\n')
{
    raiseError("Unterminated string constant");
}
    |'"' .*? EOF
{
    raiseError("EOF in string constant");
}
    ;




BLOCK_COMMENT
    : '(*'
      (BLOCK_COMMENT|.)*?
      ('*)'  {skip();} | EOF {raiseError("EOF in comment");})
    ;

UNMATCH_COMM
    : '*)' {raiseError("Unmatched *)");}
    ;


INVALID
    : .
    {
        raiseError("Invalid character: " + getText());
    }
    ;
