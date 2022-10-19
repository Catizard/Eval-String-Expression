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
}
