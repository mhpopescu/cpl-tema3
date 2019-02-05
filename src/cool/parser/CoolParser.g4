parser grammar CoolParser;

options {
    tokenVocab = CoolLexer;
}

program
    : (class_def DELIM)+ EOF
    ;

class_def
    : CLASS_SYM className=TYPE (INHERITS baseClass=TYPE)? OPEN_B (feature DELIM)* CLOSED_B
    ;

feature
    : ID OPEN_P (formal (COMMA formal)* )? CLOSED_P INIT TYPE OPEN_B exp=expr CLOSED_B # method_def
    | idName=ID INIT typeName=TYPE (ASSIGN expr)?                                      # var_def
    ;

formal
    : idName=ID INIT typeName=TYPE
    ;

local
    : ID INIT TYPE (ASSIGN expr)?
    ;

case_branch
    : ID INIT TYPE IS expr DELIM
    ;

expr
    : ID                                    # id
    | BOOL                                  # bool
    | INT                                   # int
    | STRING                                # string
    | ID OPEN_P (expr (COMMA expr)* )? CLOSED_P                         # method_call
    | expr (AT TYPE)? DOT ID OPEN_P (expr (COMMA expr)* )? CLOSED_P     # obj_method_call
    | NOT_BITWISE expr                      # not_bitwise
    | IS_VOID expr                          # is_void
    | expr MULTIPLY expr                    # multiply
    | expr DIVIDE expr                      # divide
    | expr PLUS expr                        # plus
    | expr MINUS expr                       # minus
    | expr LESS_EQ expr                     # less_eq
    | expr LESS expr                        # less
    | expr EQUAL expr                       # equal
    | NOT_BOOLEAN expr                      # not_boolean
    | ID ASSIGN expr                        # assign
    | OPEN_P expr CLOSED_P                  # paranthesis
    | NEW TYPE                              # new
    | IF expr THEN expr ELSE expr FI        # if
    | WHILE expr LOOP expr POOL             # while
    | LET local (COMMA local)* IN expr      # let
    | CASE expr OF case_branch+ ESAC        # case
    | OPEN_B (expr DELIM)+ CLOSED_B         # block
    ;


