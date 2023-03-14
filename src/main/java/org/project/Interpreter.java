package org.project;

import org.project.parsing.Context;
import org.project.parsing.TreeNode;

/** Representation of a INTERPRETER... which means: given a NODE TREE of lisp expressions. It will evaluate
 * each tree node, until the tree is only conformed by Atoms.
 * Conforms the THIRD AND LAST LAYER of code analysis after the PARSER.
 * Ex input:
 * ROOT NODE/
 * ├─ list
 * ├─ EXP1/
 * │  ├─ +
 * │  ├─ 2
 * │  ├─ 3
 * ├─ EXP2/
 * │  ├─ /
 * │  ├─ EXPR3/
 * │  │  ├─ *
 * │  │  ├─ 2
 * │  │  ├─ 6
 * │  ├─ 4
 *
 * Ex output:
 * ROOT NODE/
 * ├─ 5
 * ├─ 3
 * IMPORTANT: Operators like SETQ or DEFUN will evaluate to null, therefore disappearing of the tree.
 */
public class Interpreter {

    /**
     * Evaluates a lisp Tree node.
     * @param rootNode Node to evaluate.
     * @return Evaluated node.
     */
    public static TreeNode evaluate (TreeNode rootNode){
        Context rootContext = new Context();
        return rootNode.evaluate(rootContext);
    }
}
