package org.project.parsing;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private final Map<String, TreeNode> variables = new HashMap<>();
    private final Map<String, TreeNode> customFunctions = new HashMap<>();

    public boolean variableExist (String variableName){
        if (this.variables.isEmpty())
            return false;
        return variables.containsKey(variableName);
    }

    public boolean functionExist (String variableName){
        if (this.customFunctions.isEmpty())
            return false;
        return customFunctions.containsKey(variableName);
    }

    public TreeNode getVariable (String variableName){
            TreeNode value = this.variables.get(variableName);
            if (value.equals(null))
                throw new RuntimeException("Error: Variable " + variableName + "is not declared");
            else
                return value;
    }

    public TreeNode getFunction (String functionName){
        TreeNode value = this.variables.get(functionName);
        if (value.equals(null))
            throw new RuntimeException("Error: Function " + functionName + "is not declared");
        else
            return value;
    }

    public void setVariable (String variableName, TreeNode value){
        this.variables.put(variableName, value);
    }

    public void setFunction(String functionName, TreeNode value){
        this.variables.put(functionName, value);
    }

}
