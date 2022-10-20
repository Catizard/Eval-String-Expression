package com.catizard.evalexpr.token;

public class TokenRule {
    private String regex;
    private Token.TOKEN_TYPES token_type;
    private int pri;
    private Token.OP_TYPES op_type;
    public TokenRule(String regex, Token.TOKEN_TYPES token_type, int pri, Token.OP_TYPES op_type) {
        this.regex = regex;
        this.token_type = token_type;
        this.pri = pri;
        this.op_type = op_type;
    }

    public String getRegex() {
        return regex;
    }
    public Token.TOKEN_TYPES getToken_type() {
        return token_type;
    }
    public int getPri() {
        return pri;
    }
    public Token.OP_TYPES getOp_type() {
        return op_type;
    }
}
