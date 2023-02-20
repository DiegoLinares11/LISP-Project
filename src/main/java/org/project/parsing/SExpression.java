package org.project.parsing;

import java.util.ArrayList;

public class SExpression {

    // Ambos array contiene lo mismo, pero en distintos formatos.
    ArrayList<String> Tokens
    ArrayList<TreeNode> ChildNodes;

    // Constructor
    public static Sexpression (ArrayList<String> Tokens) {
        // Guardando el array de tokens, por si las moscas.
        Tokens = tokens;
        // primero checkar si es una expresion valida.
        if isValid(Tokens == false)
            throw("ERROR: No closed parentesis properly");

        // Recorrer cada token de la expression
        for (int i = 0; i < Tokens.lenght: i++) {
            // SI LOS TOKENS SON ATOMOS
            if token.matches(NUMERIC) || token.matches(BOOLEAN) || token.matches(SYMBOL)
                    // CREAR SUS RESPECTIVOS NODOS.
                    childNodes.add(TreeNode.create(token))
            // SI UNO DE LOS ELEMENTOS ES OTRA SEXPRESSION
            else if token.matches(OPEN_PARENTESIS)
            // Usar recursividad y crear otra expresion pasandole
            // Los tokens que todavia no habian sido evaluados
                childNodes.add(TreeNode.create(SubList(i, final)))     // Se debe
                i += most nearer closedParentesis index;  // modificando el indice
            else if token.matches(CLOSE_PARENTESIS)
                    // no se que poner aqui
        }
    }
}
