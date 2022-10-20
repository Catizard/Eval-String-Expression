package com.catizard.evalexpr;

import com.catizard.eval.Eval;
import com.catizard.eval.EvalResult;
import com.catizard.evalexpr.token.Token;
import com.catizard.evalexpr.token.TokenRule;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class EvalExpr implements Eval {
    private static final List<TokenRule> rules = new ArrayList<TokenRule>();
    private static final List<Pattern> patterns = new ArrayList<Pattern>();
    static {
        //set rules
        rules.add(new TokenRule(" +",      Token.TOKEN_TYPES.TK_NOTYPE,              -1, Token.OP_TYPES.NO_OP));
        rules.add(new TokenRule("\\+",     Token.TOKEN_TYPES.TK_PLUS,                4,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("\\-",     Token.TOKEN_TYPES.TK_BINARY_MINUS,               4,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("\\*",     Token.TOKEN_TYPES.TK_MULTI,               3,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("\\/",     Token.TOKEN_TYPES.TK_DIV,                 3,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("\\(",     Token.TOKEN_TYPES.TK_LEFT_PARENTHESIS,    -1, Token.OP_TYPES.NO_OP));
        rules.add(new TokenRule("\\)",     Token.TOKEN_TYPES.TK_RIGHT_PARENTHESIS,   -1, Token.OP_TYPES.NO_OP));
        rules.add(new TokenRule("[0-9]+",  Token.TOKEN_TYPES.TK_DEC,                 -1, Token.OP_TYPES.NO_OP));
        rules.add(new TokenRule("==",      Token.TOKEN_TYPES.TK_BINARY_EQ,           7,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("!=",      Token.TOKEN_TYPES.TK_NEQ,                 7,  Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("&&",      Token.TOKEN_TYPES.TK_BINARY_AND,          11, Token.OP_TYPES.BINARY_OP));
        rules.add(new TokenRule("||",      Token.TOKEN_TYPES.TK_BINARY_OR,           11, Token.OP_TYPES.BINARY_OP));
        //compile patterns
        rules.forEach(r -> patterns.add(Pattern.compile(r.getRegex())));
    }
    public EvalResult eval(String expr) throws Exception {
        //make tokens
        List<Token> tokens = makeTokens(expr);
        //do eval 
        return tokens.isEmpty() ? null : doEval(tokens, 0, tokens.size() - 1);
    }
    
    private List<Token> makeTokens(String expr) throws Exception {
        List<Token> tokens = new ArrayList<Token>();
        while (!expr.isEmpty()) {
            boolean matched = false;
            for (int i = 0;i < EvalExpr.rules.size();++i) {
                TokenRule rule = EvalExpr.rules.get(i);
                Pattern pattern = EvalExpr.patterns.get(i);
                Matcher matcher = pattern.matcher(expr);
                if (matcher.lookingAt()) {
                    addToken(tokens, matcher.group() , rule);
                    expr = expr.substring(matcher.end());
                    matched = true;
                    break;
                }
            }
            
            if (!matched) {
                throw new Exception("no match found!");
            }
        }
        return tokens;
    }
    
    private EvalResult doEval(List<Token> tokens, int p, int q) throws Exception {
        assert(p <= q);
        //check if [p,q] is a whole
        Token token = tokens.get(p);
        if (p == q) {
            if (token.token_type != Token.TOKEN_TYPES.TK_DEC) {
                throw new Exception("illegal token");
            }
            //TODO complete class EvalReulst's architecture
            return new EvalResult(Integer.parseInt(token.str), 0);
        } else if(p + 1 == q && token.op_type == Token.OP_TYPES.UNARY_OP) {
            if (token.token_type == Token.TOKEN_TYPES.TK_UNARY_MINUS) {
                return new EvalResult(Integer.parseInt(tokens.get(q).str) * -1, 0);
            }
        }
        
        if (checkParenthese(tokens, p, q)) {
            return doEval(tokens, p + 1, q - 1);
        }
        
        int mainOp = findMainOperator(tokens, p, q);
        EvalResult retLeft = doEval(tokens, p, mainOp - 1);
        EvalResult retRight = doEval(tokens, mainOp + 1, q);
        //TODO how we calc two EvalResult?
        return null;
    }
    
    private boolean checkParenthese(List<Token> tokens, int p, int q) {
        if (tokens.get(p).token_type != Token.TOKEN_TYPES.TK_LEFT_PARENTHESIS
        ||  tokens.get(q).token_type != Token.TOKEN_TYPES.TK_RIGHT_PARENTHESIS) {
            return false;
        }
        
        int sum = 0;
        for (int i = p + 1;i <= q - 1;++i) {
            if (tokens.get(i).token_type == Token.TOKEN_TYPES.TK_LEFT_PARENTHESIS) {
                ++sum;
            } else if(tokens.get(i).token_type == Token.TOKEN_TYPES.TK_RIGHT_PARENTHESIS) {
                --sum;
            }
            
            if (sum < 0) {
                return false;
            }
        }
        return sum == 0;
    }
    private int findMainOperator(List<Token> tokens, int p, int q) throws Exception {
        int ret = -1, sum = 0, handPri = (int)1e9;
        for (int i = p;i <= q;++i) {
            Token token = tokens.get(i);
            if (token.token_type == Token.TOKEN_TYPES.TK_LEFT_PARENTHESIS) {
                ++sum;
            } else if(token.token_type == Token.TOKEN_TYPES.TK_RIGHT_PARENTHESIS) {
                --sum;
            }
            if (sum != 0) {
                continue;
            }
            if (token.op_type != Token.OP_TYPES.NO_OP && token.pri < handPri) {
                handPri = token.pri;
                ret = i;
            }
        }
        if (ret == -1) {
            throw new Exception("cannot find any operator");
        }
        return ret;
    }
    private void addToken(List<Token> tokens, String part, TokenRule rule) {
        if (rule.getToken_type() == Token.TOKEN_TYPES.TK_NOTYPE) {
            return ;
        }
        Token newToken = new Token(rule.getToken_type(), part, rule.getPri(), rule.getOp_type());
        //check if it is a minus flag operator
        boolean head_op = tokens.size() == 0;
        if (!head_op && newToken.token_type == Token.TOKEN_TYPES.TK_BINARY_MINUS) {
            Token lastToken = tokens.get(tokens.size() - 1);
            if (lastToken.token_type == Token.TOKEN_TYPES.TK_RIGHT_PARENTHESIS
            ||  lastToken.token_type == Token.TOKEN_TYPES.TK_DEC) {
                newToken.op_type = Token.OP_TYPES.UNARY_OP;
                newToken.token_type = Token.TOKEN_TYPES.TK_UNARY_MINUS;
            }
        }
        doAddToken(tokens, newToken);
    }
    private void doAddToken(List<Token> tokens, Token token) {
        tokens.add(token);
    }
}

