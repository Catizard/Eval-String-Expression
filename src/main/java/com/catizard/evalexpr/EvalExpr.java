package com.catizard.evalexpr;

import com.catizard.eval.Eval;
import com.catizard.eval.EvalResult;
import com.catizard.evalexpr.token.Token;
import com.catizard.evalexpr.token.TokenRule;

import java.util.ArrayList;
import java.util.List;

public class EvalExpr implements Eval {
    private static List<TokenRule> rules = new ArrayList<TokenRule>();
    static {
        //set rules
        rules.add(new TokenRule(" +",      Token.TOKEN_TYPES.TK_NOTYPE,              -1, Token.OP_TYPES.NO_OP));
        rules.add(new TokenRule("\\+",     Token.TOKEN_TYPES.TK_PLUS,                4,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("\\-",     Token.TOKEN_TYPES.TK_MINUS,               4,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("\\*",     Token.TOKEN_TYPES.TK_MULTI,               3,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("\\/",     Token.TOKEN_TYPES.TK_DIV,                 3,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("\\(",     Token.TOKEN_TYPES.TK_LEFT_PARENTHESIS,    -1, Token.OP_TYPES.NO_OP));
        rules.add(new TokenRule("\\)",     Token.TOKEN_TYPES.TK_RIGHT_PARENTHESIS,   -1, Token.OP_TYPES.NO_OP));
        rules.add(new TokenRule("[0-9]+",  Token.TOKEN_TYPES.TK_DEC,                 -1, Token.OP_TYPES.NO_OP));
        rules.add(new TokenRule("==",      Token.TOKEN_TYPES.TK_BINARY_EQ,           7,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("!=",      Token.TOKEN_TYPES.TK_NEQ,                 7,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("&&",      Token.TOKEN_TYPES.TK_BINARY_AND,          11, Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("||",      Token.TOKEN_TYPES.TK_BINARY_OR,           11, Token.OP_TYPES.BINARY_OP));
        //set tokens
        
    }
    public EvalResult eval(String expr) throws Exception {
        //make tokens
        List<Token> tokens = make_tokens(expr);
        //do eval 
        return do_eval(tokens);
    }
    
    private List<Token> make_tokens(String expr) {
        return null;
    }
    
    private EvalResult do_eval(List<Token> tokens) {
        return null;
    }
}

