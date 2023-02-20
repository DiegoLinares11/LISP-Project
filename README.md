<h1 align="center">
    <img src="./media/lispLogo.png" width="200px">
    <h1 align="center" style="font-style:italic;">Lisp x Java</h1>
    <h5 align="center">
    <i style="color:grey;"> Una pequeña implementacion de Lisp en Java</i>
    </h5>

</h1>

Elaborado con java 17 y maven.  

**Código fuente:**  [*Aquí*](https://github.com/DanielRasho/HT4-InfixCalculator/tree/main/App_main/src/main/java)  

**Unit Test:** [*Aquí*](https://github.com/DanielRasho/HT4-InfixCalculator/tree/main/App_main/src/test/java/App_main)  

## Guia de Uso

1. Descargar este repositorio.

2. En un archivo escribir el código Lisp a interpretar
   
   ```lisp
   ;./Fibonacci.lisp
   
   (defun fibonacci (n)
       (cond 
           ((< n 2) n)
           (t ( + (fibonacci (- n 1)) (fibonacci (- n 2)) ))
       )
   )
   
   (fibonacci 5)
   ; Prints 5
   ```

3. Ejecutar  `javaLisp.jar` pasando el archivo a interpretar como argumento
   
   ```bash
   java -jar ./javaLisp.jar <codigo.lisp>
   Ejemplo
   java -jar ./javaLisp.jar ./Fibonacci.lisp
   ```

#### Windows

Antes de ejecutarlo, asegurarse que el Java(TM) Platform SE binary, esta activado. 

<img title="" src="./media/jarr.gif" alt="" width="465" data-align="center">

## UML de clases

![](./classesUML.png)

## Colaboradores

- Daniel Rayo 22933

- Diego Linares 221256

- Luis


