package org.project.parsing;

public class Atom extends TreeNode {

    String value;

    public Atom(String value){
        this.value = value;
    }

    public Atom (boolean boolValue){
        this.value = boolValue ? "T" : "NIL";}

    public Atom (int numeric){
        this.value = Integer.toString(numeric);
    }

    public Atom (double numeric){
        this.value = Double.toString(numeric);
    }

    @Override
    public boolean isSExpression() {
        return false;
    }

    @Override
    public TreeNode evaluate(Context context) {
        if(context.variableExist(this.value))
            return context.getVariable(this.value);
        else
            return this;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
