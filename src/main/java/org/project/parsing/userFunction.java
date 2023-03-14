package org.project.parsing;

import org.project.parsing.primitiveFunctions.LispPrimitiveFunctions;

import java.util.List;

import static org.project.parsing.primitiveFunctions.functionHelper.areArgumentsValid;

/**
 * Representation of custom Function. Every time it is called, it creates a new context.
 */
public class userFunction {
    String name;
    List<TreeNode> argsVariables;
    TreeNode body = new SExpression("");

    /**
     * Function constructor.
     * @param name Function's name.
     * @param args Function's Sexpression that contains the args.
     * @param body Function's body.
     */
    public userFunction(String name, TreeNode args, TreeNode body){
        this.name = name;
        this.argsVariables = args.getChildNodes();
        this.body.getChildNodes().add(body);
    }

    /**
     * Evaluates a functions body, with the given arguments.
     * @param argsValues List of arguments.
     * @param parentContext function's Parent context.
     * @return A TreeNode with the result.
     */
    public TreeNode evaluate(List<TreeNode> argsValues, Context parentContext){
        Context functionContext = genContext(argsValues, parentContext);
        TreeNode result = this.body.evaluate(functionContext);
        if (result.getChildNodes().size() > 1)
            throw new RuntimeException("Error: Functions body must return ONE VALUE OR NONE");
        else if(result.getChildNodes().isEmpty())
            return null;
        return result.getChildNodes().get(0);
    }

    /**
     * Generates the context, the function, will be execute on.
     * @param parentContext function's Parent context.
     * @param argsValues List of arguments.
     * @return
     */
    private Context genContext(List<TreeNode> argsValues, Context parentContext){
        areArgumentsValid(argsValues, name, this.argsVariables.size(), Integer::equals);
        Context functionContext = new Context(parentContext);
        // Bind every actual arg value, to its variable.
        for (int i=0; i < this.argsVariables.size(); i++) {
            LispPrimitiveFunctions.setq(argsVariables.get(i), argsValues.get(i), functionContext);
        }
        return functionContext;
    }
}
