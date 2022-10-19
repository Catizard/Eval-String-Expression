package com.catizard.eval;

public class EvalResult {
    private Object value;
    private int type;

    public EvalResult(Object value, int type) {
        this.value = value;
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
