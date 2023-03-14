package org.project.parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a Node, used for representing lisp data. A node can have child nodes, thus
 * forming a tree, of data that could be traversed and evaluated.
 */
abstract public class TreeNode implements Cloneable{


    /** List of all Nodes this Node is parent of.*/
    protected List<TreeNode> childNodes = new ArrayList<>();

    /**
     * @deprecated
     * Transforms a list of tokens in a Node.
     * @param tokens A valid list of tokens (Following Lisp sintaxis).
     * @return A Node that represents the given data.
     */
    public TreeNode getInstance(ArrayList<String> tokens){
        if (tokens.size() == 1)
            return new Atom(tokens.get(0));
        else if (tokens.size() > 1)
            return new SExpression(tokens);
        else
            throw new RuntimeException("ERROR: Attempted to create a node without data.");
    }

    /**
     * @deprecated
     * Transforms a string input in a Node.
     * @param value A valid list of tokens (Following Lisp sintaxis).
     * @return A Node that represents the given data.
     */
    public TreeNode getInstance(String value){
        if (value.length() > 1)
            return new SExpression(value);
        else
            return new Atom(value);
    }

    /**
     * @deprecated
     * Returns a Node containing a given bool value.
     * @param boolValue Bool value.
     * @return A Node that represents the given data.
     */
    public TreeNode getInstance(boolean boolValue){
        return new Atom(boolValue);
    }

    /**
     * @deprecated
     * Returns a Node containing a given numeric value.
     * @param numeric numeric value.
     * @return A Node that represents the given data.
     */
    public TreeNode getInstance(int numeric){
        return new Atom(numeric);
    }

    /**
     * @return The list of child nodes this node is parent of.
     */
    public List<TreeNode> getChildNodes() {
        return childNodes;
    }

    /**
     * @return True : is a Atom, False: is an SExpression.
     */
    abstract public boolean isAtom();

    /**
     * Evaluate a given node recursively, and return a new node with the value.
     * Ex inputNode : (+ (+ 3 2) 3)
     * Ex outputNode : (8)
     * @param context Context of user defined variables and functions.
     * @return Evaluated node.
     */
    abstract public TreeNode evaluate(Context context);

    @Override
    public String toString() {
        return String.join(" ", this.childNodes.toString());
    }

    @Override
    public TreeNode clone() {
        try {
            TreeNode clone = (TreeNode) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
