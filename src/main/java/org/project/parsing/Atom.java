package org.project.parsing;

/** Type of TreeNode which DO NOT HAVE CHILD NODES. But can STORE ONE VALUE.
 * Tokens like:
 * - 3  (Numbers)
 * - hello (Text)
 * - NIL (Booleans)
 * Will be translated to atoms during parsing. */
public class Atom extends TreeNode implements Cloneable{

    /**Stored value, could be a number, text of boolean.*/
    private String value;

    /**
     * Creates an atom.
     * @param value Value to store in atom.
     */
    public Atom(String value){
        this.value = value;
    }

    /**
     * Creates a boolean atom (value = "T" or "NIL")
     * @param boolValue Valut to store in atom
     */
    public Atom (boolean boolValue){
        this.value = boolValue ? "T" : "NIL";}

    /**
     * Creates a numeric atom
     * @param numeric Value to store in atom
     */
    public Atom (int numeric){
        this.value = Integer.toString(numeric);
    }

    /**
     * Creates a numeric atom.
     * @param numeric Value to store in atom.
     */
    public Atom (double numeric){
        this.value = Double.toString(numeric);
    }

    /**
     * Report this is not an SExpression.
     * @return False, because an Atom IS NOT an SExpression.
     */
    @Override
    public boolean isAtom() {
        return true;
    }

    /**
     * Evaluates an atom. Since an atom cannot be broken in smaller SExpressions.
     * It just returns itself.
     * @param context Context of user defined variables and functions.
     * @return
     */
    @Override
    public TreeNode evaluate(Context context) {
        if(context.variableExist(this.value))
            return context.getVariable(this.value);
        else
            return this;
    }

    /**
     * Return stored value within atom.
     * @return stored value withing atom.
     */
    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public Atom clone() {
        Atom clone = (Atom) super.clone();
        // TODO: copy mutable state here, so the clone can't change the internals of the original
        return clone;
    }
}
