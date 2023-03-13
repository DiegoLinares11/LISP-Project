package org.project.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Context {
    private Map<String, TreeNode> variables = new HashMap<>();
    private Map<String, userFunction> customFunctions = new HashMap<>();
    private Context parentContext;

    public Context (){}

    public void setCustomFunctions(Map<String, userFunction> customFunctions) {
        this.customFunctions = customFunctions;
    }

    public Map<String, userFunction> getCustomFunctions() {
        return customFunctions;
    }

    public Context (Context parentContext){
        this.parentContext = parentContext;
    }

    public boolean variableExist (String variableName){
        if (this.variables.containsKey(variableName))
            return true;
        if (this.parentContext != null)
            return parentContext.variableExist(variableName);
        return false;
    }

    public boolean functionExist (String functionName){
        if (this.customFunctions.containsKey(functionName))
            return true;
        if (this.parentContext != null)
            return parentContext.functionExist(functionName);
        return false;
    }

    public TreeNode getVariable (String variableName){
            TreeNode value = this.variables.get(variableName);
            if (value == null && this.parentContext != null)  // If not found, try in parent context.
                value = this.parentContext.getVariable(variableName);
            if (value == null)
                throw new RuntimeException("ERROR: Variable " + variableName + "is not declared");
            return value;
    }

    public userFunction getFunction (String functionName){
        userFunction value = this.customFunctions.get(functionName);
        if (value == null && this.parentContext != null)  // If not found, try in parent context.
            value = this.parentContext.getFunction(functionName);
        if (value == null)
            throw new RuntimeException("ERROR: Function " + functionName + "is not declared");
        return value;

    }

    public void setVariable (String variableName, TreeNode value){
        this.variables.put(variableName, value);
    }

    public void setFunction(String functionName, userFunction value){
        this.customFunctions.put(functionName, value);
    }

}
