package org.project.parsing;

import java.util.HashMap;
import java.util.Map;

/**
 * The Context is responsable for keep track of the user defined VARIABLES and FUNCTIONS
 * in the current execution flow.
 *
 * IMPORTANT: Everytime the interpreter steps into a function, a new context is generated.
 * That same context will be deleted once the function ends. Returning to the previous context.
 *
 * Also, if a variable of function is not defined withing the current context.
 * It will ask the PARENT CONTEXT for it.
 *
 * ┌──────────────┐
 * │  CONTEXT 1     │
 * │                │
 * │   ┌─────────┐ │
 * │   │CONTEXT 2 │ │
 * │   └─────────┘ │
 * └──────────────┘
 *
 * It achieves this by linking names to nodes on maps.
 */

public class Context {
    private Map<String, TreeNode> variables = new HashMap<>();
    private Map<String, userFunction> customFunctions = new HashMap<>();
    private Context parentContext;

    /**
     * Default Constructor
     */
    public Context (){}

    /**
     * Builds a context that has a parent.
     * @param parentContext The context this is child of.
     */
    public Context (Context parentContext){
        this.parentContext = parentContext;
    }

    /**
     * Add a function to the current context.
     * @param customFunctions userFunction object to store.
     */
    public void setCustomFunctions(Map<String, userFunction> customFunctions) {
        this.customFunctions = customFunctions;
    }

    /**
     * Returns the current recorded functions.
     * @return Functions of this context.
     */
    public Map<String, userFunction> getCustomFunctions() {
        return customFunctions;
    }

    /**
     * Check if a VARIABLE exist withing this context or parents.
     * @param variableName Variable's name to check.
     * @return True = Exist, False = Not exist.
     */
    public boolean variableExist (String variableName){
        if (this.variables.containsKey(variableName))
            return true;
        if (this.parentContext != null)
            return parentContext.variableExist(variableName);
        return false;
    }

    /**
     * Check if a FUNCTION exist within this context or parents.
     * @param functionName function's names to check.
     * @return True = Exist, False = Not exist.
     */
    public boolean functionExist (String functionName){
        if (this.customFunctions.containsKey(functionName))
            return true;
        if (this.parentContext != null)
            return parentContext.functionExist(functionName);
        return false;
    }

    /**
     * Stores or updates a variable, that is, link a TreeNode to
     * a specific name.
     * @param variableName Variable's name.
     * @param value Node to link.
     */
    public void setVariable (String variableName, TreeNode value){
        this.variables.put(variableName, value);
    }

    /**
     * Stores or updates a function, that is, link a userFunction object to
     * a specific name.
     * @param functionName
     * @param value
     */
    public void setFunction(String functionName, userFunction value){
        this.customFunctions.put(functionName, value);
    }

    /**
     * Returns the TreeNode that corresponds a name.
     * @param variableName variable's name.
     * @return variable's value (TreeNode).
     */
    public TreeNode getVariable (String variableName){
            TreeNode value = this.variables.get(variableName);
            if (value == null && this.parentContext != null)  // If not found, try in parent context.
                value = this.parentContext.getVariable(variableName);
            if (value == null)
                throw new RuntimeException("ERROR: Variable " + variableName + "is not declared");
            return value;
    }

    /**
     * Returns the TreeNode that corresponds a name.
     * @param functionName function's name.
     * @return userFunction's object.
     */
    public userFunction getFunction (String functionName){
        userFunction value = this.customFunctions.get(functionName);
        if (value == null && this.parentContext != null)  // If not found, try in parent context.
            value = this.parentContext.getFunction(functionName);
        if (value == null)
            throw new RuntimeException("ERROR: Function " + functionName + "is not declared");
        return value;

    }

}
