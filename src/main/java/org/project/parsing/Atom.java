package org.project.parsing;

import org.project.lexing.Patterns;

public class Atom extends TreeNode {

    String value;

    public Atom(String value){
        this.value = value;
    }

    public Atom (boolean boolValue){
        this.value = boolValue ? "T" : "NIL";
        tokens.add(value);
    }

    public Atom (int numeric){
        this.value = Integer.toString(numeric);
        tokens.add(value);
    }

    @Override
    public boolean isSExpression() {
        return false;
    }

    @Override
    public TreeNode evaluate() {
        return this;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
