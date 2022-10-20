package com.catizard.evalexpr.token;

public class Token {
    public TOKEN_TYPES token_type;
    public String str;
    public int pri;
    public OP_TYPES op_type;
    public enum TOKEN_TYPES {
        TK_NOTYPE /*  blank  */,
        TK_PLUS /*  +  */,
        TK_BINARY_MINUS /*  -  */,
        TK_MULTI /*  *  */,
        TK_DIV /*  /  */,
        TK_LEFT_PARENTHESIS /*  (  */,
        TK_RIGHT_PARENTHESIS /*  )  */,
        TK_DEC /*  DECIMAL NUMBER  */,
        TK_BINARY_EQ /*  ==  */,
        TK_NEQ /*  !=  */,
        TK_BINARY_AND /*  &&  */,
        TK_BINARY_OR /* || */,
        TK_UNARY_MINUS /*  -  */,
    }
    public enum OP_TYPES {
        NO_OP,
        UNARY_OP,
        BINARY_OP,
    }

    public Token(TOKEN_TYPES token_type, String str, int pri, OP_TYPES op_type) {
        this.token_type = token_type;
        this.str = str;
        this.pri = pri;
        this.op_type = op_type;
    }
}
