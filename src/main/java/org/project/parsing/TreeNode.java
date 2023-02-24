package org.project.parsing;

import java.util.ArrayList;
import java.util.List;

abstract class TreeNode {

    protected List<String> tokens = new ArrayList<>();
    protected List<TreeNode> childNodes = new ArrayList<>();

    // Recibes a valid expression (no missing parenthesis)
    public TreeNode getInstance(ArrayList<String> tokens){
        if (tokens.size() == 1)
            return new Atom(tokens.get(0));
        else if (tokens.size() > 1)
            return new SExpression(tokens);
        else
            throw new RuntimeException("ERROR: Attempted to create a node without data.");
    }

    public TreeNode getInstance(String value){
        if (value.length() > 1)
            return new SExpression(value);
        else
            return new Atom(value);
    }

    public TreeNode getInstance(boolean boolValue){
        return new Atom(boolValue);
    }

    public TreeNode getInstance(int numeric){
        return new Atom(numeric);
    }

    abstract public boolean isSExpression();

    abstract public TreeNode evaluate();

    @Override
    public String toString() {
        return String.join(" ", this.childNodes.toString());
    }
}
