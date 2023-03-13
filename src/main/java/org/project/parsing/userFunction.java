package org.project.parsing;

import org.project.parsing.primitiveFunctions.LispPrimitiveFunctions;

import java.util.List;

import static org.project.parsing.primitiveFunctions.functionHelper.areArgumentsValid;

public class userFunction {
    String name;
    List<TreeNode> argsVariables;
    TreeNode body = new SExpression("");

    public userFunction(String name, TreeNode args, TreeNode body){
        this.name = name;
        this.argsVariables = args.getChildNodes();
        this.body.getChildNodes().add(body);
    }

    public TreeNode evaluate(List<TreeNode> argsValues, Context context){
        Context functionContext = getFunctionContext(context, argsValues);
        TreeNode result = this.body.evaluate(functionContext);
        if (result.getChildNodes().size() > 1)
            throw new RuntimeException("Error: Functions body must return ONE VALUE OR NONE");
        return result.getChildNodes().get(0);
    }

    public Context getFunctionContext(Context originalContext, List<TreeNode> argsValues){
        areArgumentsValid(argsValues, name, this.argsVariables.size(), Integer::equals);
        Context functionContext = new Context(originalContext);
        // Bind every actual arg value, to its variable.
        for (int i=0; i < this.argsVariables.size(); i++) {
            LispPrimitiveFunctions.setq(argsVariables.get(i), argsValues.get(i), functionContext);
            //functionContext.setCustomFunctions(originalContext.getCustomFunctions()); // Copy Functions
        }
        return functionContext;
    }
}
