<h1 align="center">
    <img src="./media/lispLogo.png" width="200px">
    <h1 align="center" style="font-style:italic;">JLisp</h1>
    <h5 align="center">
    <i style="color:grey;"> Una pequeña implementacion de Lisp en Java</i>
    </h5>

</h1>

Elaborado con Java 17 y [Maven](https://www.campusmvp.es/recursos/post/java-que-es-maven-que-es-el-archivo-pom-xml.aspx).

**Código fuente:**  [***Aquí***](https://github.com/DiegoLinares11/LISP-Project/tree/master/src/main/java/org/project)  

**Unit Test:** [***Aquí***](https://github.com/DiegoLinares11/LISP-Project/tree/master/src/test/java/org/project)  

## Indice

- [Guia de Uso](#guia-de-uso)
  
  - [Problemas en Windows](#problemas-en-windows)

- [¿Como funciona?](#como-funciona)

## Guia de Uso

1. Descargar el ejecutable `JLisp.jar` [aquí](https://github.com/DiegoLinares11/LISP-Project/releases).

2. Ejecutar `JLisp.jar` con el siguiente comando.
   
   ```bash
   java -jar ./JLisp.jar
   ```

#### Opciones de comando

Al ejectuarse como un comando en terminal, JLisp tambien puede recibir argumentos opcionales.

**AYUDAAAA!** 😩

Si no sabes como se ejecuta JLisp, recuerda que siempre puedes pedir ayuda asi:

```bash
java -jar ./JLisp.jar --help
```

**Leer de un archivo**

Jlisp tambien puede leer codigo guardado en un archivo, lo puedes especificar con la opcion `--input` seguido de la ruta al archivo.

```bash
java -jar ./JLisp.jar --input <rutaArchivo>
Ejemplo:
java -jar ./JLisp.jar --input ./Fibonacci.lisp
```

Este podría ser un codigo de prueba:

```lisp
;./Fibonacci.lisp.

(defun fibonacci (n)
    (cond
        ((< n 2) n)
        (T ( + (fibonacci (- n 1)) (fibonacci (- n 2))))
    )
)

(print (fibonacci 5))
; Prints 5
```

**Guardar resultados**

Por ultimo, tambien puedes guardar los resultados de tus operaciones en un archivo con la opcion `--output`.

```bash
java -jar ./JLisp.jar --output <rutaArchivo>
Ejemplo:
java -jar ./JLisp.jar --output ./resultados.txt
```

**Combinar varias opciones**

Recuerda que puedes combinar varias opciones a la vez al ejecutar JLisp

```bash
java -jar ./JLisp.jar --output <rutaArchivo> --input <rutaArchivo>
```

#### Problemas en Windows

Si estas en Windows, asegurate de tener instalado y corriendo el java JDK o JSE (version >= 17).

<img title="" src="./media/jarr.gif" alt="" width="673" data-align="center">

## ¿Como funciona?

Todo esta explicado con detalle en la  [**WIKI**](https://github.com/DiegoLinares11/LISP-Project/wiki) :question:

Pero para empezar el interpreta se compone de 3 partes principales

1. **Lexer**: Rompe una expresion dado en palabras significativas llamadas *"tokens"*

2. **Parser**: Recibe una lista de *tokens* y los analiza segun unas reglas gramáticas, para luego construir *"árboles de nodos"* que representan instrucciones que si pueden ser ejecutadas por java ( o el lenguaje que se desee).

3. **Interpreter**: Recibe arboles de instrucciones y los ejecuta.

A continuacion puedes ver un Diagrama UML de todas las clases y como se relacionan entre ellas:

![](./classesUML.png?)
