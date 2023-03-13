package org.project;

import org.project.parsing.Context;
import org.project.parsing.TreeNode;

public class Interpreter {

    public static void evaluate (TreeNode rootNode){
        Context rootContext = new Context();
        rootNode.evaluate(rootContext);
    }
}
