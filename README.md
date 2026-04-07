# Pruebas Unitarias en Java — Demo didáctica

> **Dirigido a:** alumnos de 1.º DAM · Formación Profesional · Madrid  
> **Objetivo:** entender qué son las pruebas unitarias, para qué sirven y cómo escribirlas en Java con IntelliJ IDEA y JUnit 5.

---

## Índice

1. [¿Qué es una prueba unitaria?](#1-qué-es-una-prueba-unitaria)
2. [¿Por qué son importantes?](#2-por-qué-son-importantes)
3. [Tecnologías usadas](#3-tecnologías-usadas)
4. [Estructura del proyecto](#4-estructura-del-proyecto)
5. [Descripción de cada archivo](#5-descripción-de-cada-archivo)
6. [Análisis detallado del test](#6-análisis-detallado-del-test)
7. [Cómo ejecutar los tests en IntelliJ](#7-cómo-ejecutar-los-tests-en-intellij)
8. [Ejercicios propuestos](#8-ejercicios-propuestos)

> **Tests implementados:** `assertEquals` (sumar) · `assertNotEquals` (generarNumeroAleatorio)

---

## 1. ¿Qué es una prueba unitaria?

Imagina que acabas de escribir un método que suma dos números. ¿Cómo sabes que funciona bien? La forma más inmediata es ejecutar el programa y mirarlo con los ojos. Eso funciona... hasta que el programa crece y tiene 200 métodos. Revisarlo todo a mano cada vez que tocas algo es imposible.

Una **prueba unitaria** es un pequeño programa automático que:

1. Llama a **un solo método** de tu código.
2. Le pasa unos datos concretos.
3. Comprueba que el resultado es el que tú esperabas.
4. Te dice si ha **pasado** (verde ✅) o **fallado** (rojo ❌).

> **"Unitaria"** significa que prueba la unidad más pequeña posible de código: normalmente un método.

### Analogía del mundo real

Piensa en la ITV de un coche. No prueban el coche entero de golpe: comprueban **cada pieza por separado** — frenos, luces, escape... Si algo falla, saben exactamente qué pieza tiene el problema. Las pruebas unitarias hacen lo mismo con tu software.

---

## 2. ¿Por qué son importantes?

| Sin pruebas unitarias | Con pruebas unitarias |
|---|---|
| Descubres los errores cuando el cliente ya los ha visto | Descubres los errores antes de entregar |
| Cambiar código da miedo por si "rompe algo" | Cambias con confianza: los tests te avisan al instante |
| Tienes que probar todo a mano cada vez | La comprobación es automática y dura milisegundos |
| Documentación que queda obsoleta | Los tests son documentación viva: describen qué debe hacer el código |

En el mundo profesional, escribir tests es una práctica estándar. Empresas como Google, Amazon o cualquier empresa de software seria lo exigen en sus proyectos.

---

## 3. Tecnologías usadas

| Herramienta | Versión | Para qué sirve |
|---|---|---|
| **Java** | 21 | Lenguaje de programación |
| **IntelliJ IDEA** | Cualquiera | Entorno de desarrollo (IDE) |
| **Maven** | 3.x | Gestiona las dependencias y compila el proyecto |
| **JUnit 5** (Jupiter) | 5.10.2 | Framework para escribir y ejecutar tests en Java |

---

## 4. Estructura del proyecto

```
java-unit-testing-demo/
│
├── pom.xml                          ← Configuración del proyecto Maven
│
└── src/
    ├── main/
    │   └── java/
    │       └── org/example/
    │           ├── Main.java        ← Punto de entrada de la aplicación
    │           └── Calculadora.java ← Clase que vamos a probar
    │
    └── test/
        └── java/
            └── org/example/
                └── CalculadoraTest.java ← Pruebas unitarias de Calculadora
```

### La regla de oro de la estructura

Maven (y por tanto IntelliJ) aplica una convención muy clara:

- **`src/main/`** → aquí va tu código real, el que se entrega al cliente.
- **`src/test/`** → aquí van los tests. Este código **nunca** llega a producción; solo se usa durante el desarrollo.

Fíjate en que los tests están en el **mismo paquete** (`org.example`) que la clase que prueban. Esto es intencionado: permite que el test acceda a los miembros de la clase aunque no sean `public`.

---

## 5. Descripción de cada archivo

### `pom.xml` — El cerebro de Maven

```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>   <!-- Solo existe en tiempo de test, no en producción -->
    </dependency>
</dependencies>
```

El `pom.xml` es como el `package.json` de Node o el `requirements.txt` de Python: le dice a Maven qué librerías externas necesita el proyecto y cómo compilarlo.

El atributo `<scope>test</scope>` es muy importante: le indica a Maven que JUnit **solo** se necesita para ejecutar los tests, no para el programa final. Si alguien descarga tu app, JUnit no vendrá incluido.

---

### `Main.java` — Punto de entrada

```java
public class Main {
    public static void main(String[] args) {
        System.out.printf("Unit testing demo by JC");
    }
}
```

Es el arranque de la aplicación. En este proyecto su función es meramente demostrativa: lo importante aquí no es el `main`, sino los tests.

---

### `Calculadora.java` — La clase bajo prueba

```java
public class Calculadora {
    public int sumar(int a, int b) {
        return a + b;
    }

    public int generarNumeroAleatorio(int limite) {
        return (int) (Math.random() * limite);
    }

    public boolean esPar(int numero) {
        return numero % 2 == 0;
    }

    public String buscarOperacion(String nombre) {
        if (nombre.equals("sumar") || nombre.equals("restar") ||
            nombre.equals("multiplicar") || nombre.equals("dividir")) {
            return nombre;
        }
        return null;
    }

    public int dividir(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("No se puede dividir por cero");
        }
        return a / b;
    }
}
```

Esta es la clase que queremos verificar. Cada método introduce una aserción distinta:

| Método | Qué hace | Aserción usada |
|---|---|---|
| `sumar` | Suma dos enteros. Resultado predecible | `assertEquals` |
| `generarNumeroAleatorio` | Devuelve un entero aleatorio. Resultado impredecible | `assertNotEquals` |
| `esPar` | Devuelve `true` o `false`. Resultado booleano | `assertTrue` / `assertFalse` |
| `buscarOperacion` | Devuelve el nombre si existe, `null` si no | `assertNotNull` / `assertNull` |
| `dividir` | Divide dos enteros o lanza excepción si `b == 0` | `assertEquals` + `assertThrows` |

> **Nota pedagógica:** Cada método introduce una aserción distinta con una justificación real.

---

### `CalculadoraTest.java` — Las pruebas unitarias

Este es el archivo más importante del proyecto para el aprendizaje. Se explica en detalle en la siguiente sección.

---

## 6. Análisis detallado del test

Vamos a diseccionar `CalculadoraTest.java` línea a línea.

### 6.1 Las anotaciones de JUnit 5

Las **anotaciones** son instrucciones especiales que empiezan por `@` y le dicen a JUnit cómo tratar cada parte de la clase.

```java
@DisplayName("Calculadora - Tests unitarios")
class CalculadoraTest {
```

| Anotación | Significado |
|---|---|
| `@DisplayName("...")` | Nombre legible que aparece en el informe de resultados |
| `@Test` | Marca un método como caso de prueba. Sin esto, JUnit lo ignora |
| `@BeforeEach` | Se ejecuta **antes de cada test**. Ideal para preparar el estado inicial |

---

### 6.2 El método `setUp()` — Preparación limpia

```java
private Calculadora calculadora;

@BeforeEach
void setUp() {
    calculadora = new Calculadora();
}
```

Antes de que empiece **cada** test, JUnit llama a `setUp()`. Esto garantiza que cada prueba comienza con una instancia fresca de `Calculadora`, sin "residuos" del test anterior.

> **¿Por qué no declarar `calculadora` directamente como atributo inicializado?**  
> Porque si un test modifica el estado del objeto, podría afectar al siguiente. Con `@BeforeEach` cada test es completamente independiente.

---

### 6.3 El patrón AAA — La estructura de todo buen test

Todos los tests de este proyecto siguen el patrón **AAA** (Arrange, Act, Assert):

```
Arrange  →  prepara los datos de entrada
Act      →  llama al método que quieres probar
Assert   →  comprueba que el resultado es el esperado
```

Veámoslo aplicado al primer test:

```java
@Test
@DisplayName("sumar dos positivos devuelve su suma correcta")
void sumar_dosPositivos_retornaSuma() {

    // Arrange: preparamos los valores de entrada
    int a = 3, b = 4;

    // Act: llamamos al método que estamos probando
    int resultado = calculadora.sumar(a, b);

    // Assert: comprobamos que el resultado es el que esperamos
    assertEquals(7, resultado, "3 + 4 debe ser 7");
}
```

**Separar siempre estas tres fases** hace que los tests sean fáciles de leer y de depurar. Si el test falla, sabes exactamente en qué fase falló.

---

### 6.4 El método `assertEquals` — El corazón del test

```java
assertEquals(valorEsperado, valorObtenido, "mensaje si falla");
```

| Parámetro | Qué es |
|---|---|
| `valorEsperado` | Lo que tú, el programador, sabes que debería salir |
| `valorObtenido` | Lo que ha devuelto el método que estás probando |
| `"mensaje si falla"` | Texto explicativo que aparece en rojo si el test no pasa |

Si `valorEsperado != valorObtenido`, JUnit marca el test como **FALLIDO** y muestra el mensaje de error. Si son iguales, el test **PASA**.

---

### 6.5 Nomenclatura de los métodos de test

Los métodos de test siguen la convención:

```
nombreDelMetodo_escenario_resultadoEsperado()
```

| Parte | Ejemplo | Explica... |
|---|---|---|
| `nombreDelMetodo` | `sumar` | qué método de producción se está probando |
| `escenario` | `dosPositivos` | con qué datos de entrada |
| `resultadoEsperado` | `retornaSuma` | qué debería ocurrir |

Esta convención hace que el nombre del test sea casi una frase en inglés técnico que cualquier desarrollador entiende sin leer el cuerpo.

---

### 6.6 Los casos de prueba — assertEquals vs assertNotEquals

Un buen suite de tests no prueba solo "el caso feliz". También prueba los **casos límite**, los **casos adversos** y los casos donde el resultado **no es predecible** de antemano.

#### Con `assertEquals` — cuando conoces el resultado exacto

```
sumar(3, 4)    → caso normal: dos positivos          ✅ esperamos exactamente 7
sumar(10, -3)  → caso adverso: un número negativo    ✅ esperamos exactamente 7
sumar(-5, -2)  → caso adverso: ambos negativos       ✅ esperamos exactamente -7
sumar(9, 0)    → caso límite: el elemento neutro     ✅ esperamos exactamente 9
```

#### Con `assertNotEquals` — cuando el resultado es impredecible

```java
@Test
@DisplayName("dos llamadas consecutivas no deben devolver siempre el mismo número")
void generarNumeroAleatorio_dosLlamadas_noSonIguales() {
    // Arrange
    int limite = 1_000_000;

    // Act
    int primera = calculadora.generarNumeroAleatorio(limite);
    int segunda  = calculadora.generarNumeroAleatorio(limite);

    // Assert: no podemos saber qué número saldrá, pero sí que NO deben ser siempre iguales
    assertNotEquals(primera, segunda, "Un generador aleatorio no debe repetir siempre el mismo valor");
}
```

No sabemos qué número devolverá `generarNumeroAleatorio`, así que `assertEquals` no aplica. Lo que sí podemos afirmar es que dos llamadas consecutivas **no deben coincidir**, porque eso indicaría que el generador está roto (por ejemplo, si alguien usara siempre la misma semilla).

#### Con `assertTrue` y `assertFalse` — cuando el resultado es booleano

Cuando el método devuelve `true` o `false`, usar `assertEquals(true, resultado)` funciona pero es redundante. La forma limpia y expresiva es `assertTrue` y `assertFalse`:

```java
@Test
@DisplayName("esPar con número par devuelve true")
void esPar_numeroPar_retornaTrue() {
    // Arrange
    int numero = 4;

    // Act
    boolean resultado = calculadora.esPar(numero);

    // Assert
    assertTrue(resultado, "4 es par, debe devolver true");
}

@Test
@DisplayName("esPar con número impar devuelve false")
void esPar_numeroImpar_retornaFalse() {
    // Arrange
    int numero = 3;

    // Act
    boolean resultado = calculadora.esPar(numero);

    // Assert
    assertFalse(resultado, "3 es impar, debe devolver false");
}

@Test
@DisplayName("esPar con cero devuelve true (caso límite)")
void esPar_cero_retornaTrue() {
    // Arrange
    int numero = 0;

    // Act
    boolean resultado = calculadora.esPar(numero);

    // Assert: el 0 es par porque 0 % 2 == 0
    assertTrue(resultado, "0 es par, debe devolver true");
}
```

Fíjate en el tercer test: el `0` es un **caso límite** clásico. Muchos alumnos dudan si el cero es par o impar — el test lo responde de forma objetiva e inapelable.

#### Con `assertNotNull` y `assertNull` — cuando el resultado puede no existir

En Java es habitual que un método devuelva `null` para indicar "no encontrado". `assertNull` y `assertNotNull` comprueban exactamente eso:

```java
@Test
@DisplayName("buscarOperacion con nombre conocido devuelve el nombre")
void buscarOperacion_operacionExistente_noEsNull() {
    // Arrange
    String nombre = "sumar";

    // Act
    String resultado = calculadora.buscarOperacion(nombre);

    // Assert: una operación conocida debe devolver un valor, nunca null
    assertNotNull(resultado, "Una operación conocida no debe devolver null");
}

@Test
@DisplayName("buscarOperacion con nombre desconocido devuelve null")
void buscarOperacion_operacionInexistente_esNull() {
    // Arrange
    String nombre = "dividir_por_cero";

    // Act
    String resultado = calculadora.buscarOperacion(nombre);

    // Assert: una operación desconocida debe devolver null (no encontrada)
    assertNull(resultado, "Una operación desconocida debe devolver null");
}
```

Este patrón "devuelve `null` si no existe" es muy frecuente en Java. Lo verás continuamente al buscar elementos en listas, mapas o bases de datos. Y es también la causa del error más famoso de Java: el `NullPointerException`, que ocurre cuando intentas usar un objeto que es `null` sin comprobarlo antes.

#### Con `assertThrows` — cuando el método debe lanzar una excepción

Hasta ahora todos los tests verificaban que el método devuelve algo. `assertThrows` es diferente: verifica que el método **falla de forma controlada**, lanzando la excepción correcta.

```java
@Test
@DisplayName("dividir dos positivos devuelve el cociente correcto")
void dividir_dosPositivos_retornaCociente() {
    // Arrange
    int a = 8, b = 2;

    // Act
    int resultado = calculadora.dividir(a, b);

    // Assert
    assertEquals(4, resultado, "8 / 2 debe ser 4");
}

@Test
@DisplayName("dividir entre cero lanza ArithmeticException")
void dividir_entreCero_lanzaArithmeticException() {
    // Arrange
    int a = 10, b = 0;

    // Act + Assert: la lambda () -> es el código que debe lanzar la excepción.
    // assertThrows verifica que dicha excepción se produce. Si NO se lanza, el test FALLA.
    assertThrows(
        ArithmeticException.class,
        () -> calculadora.dividir(a, b),
        "Dividir por cero debe lanzar ArithmeticException"
    );
}
```

**La parte más nueva aquí es la lambda `() ->`**. No es más que una forma de envolver el código que queremos ejecutar, para que `assertThrows` pueda interceptar la excepción si se lanza. Puedes leerlo como: *"ejecuta esto, y comprueba que lanza una `ArithmeticException`"*.

> **Concepto clave:** probar que un método falla correctamente es tan importante como probar que funciona bien. Un método que lanza una excepción genérica o no lanza ninguna ante una entrada inválida es un método con un bug.

#### ¿Cuándo usar cada aserción?

| Aserción | Úsala cuando... | Ejemplo real |
|---|---|---|
| `assertEquals` | conoces el valor exacto esperado | operaciones matemáticas, parseo de fechas |
| `assertNotEquals` | no conoces el valor exacto, pero sabes lo que **no** debe ocurrir | números aleatorios, tokens de sesión, hashes |
| `assertTrue` | el método debe devolver `true` | validaciones, comprobaciones de estado |
| `assertFalse` | el método debe devolver `false` | validaciones negativas, comprobaciones de estado |
| `assertNotNull` | el método debe devolver un objeto real, no vacío | buscar un elemento que existe |
| `assertNull` | el método debe indicar "no encontrado" con `null` | buscar un elemento que no existe |
| `assertThrows` | el método debe lanzar una excepción ante una entrada inválida | división por cero, índice fuera de rango |

> **Reflexión para el aula:** ¿Qué pasaría si `dividir` no tuviera el `if (b == 0)`? ¿Lanzaría igualmente una excepción? ¿Cuál? ¿Sería la misma que estamos comprobando?

---

## 7. Cómo ejecutar los tests en IntelliJ

### Opción A — Ejecutar un test individual

1. Abre `CalculadoraTest.java`.
2. Haz clic en el **triángulo verde** que aparece a la izquierda del número de línea del método de test.
3. Selecciona **"Run"**.

### Opción B — Ejecutar todos los tests de la clase

1. Haz clic en el **triángulo verde** junto al nombre de la clase (`class CalculadoraTest`).
2. Selecciona **"Run 'CalculadoraTest'"**.

### Opción C — Ejecutar todos los tests del proyecto

1. Haz clic derecho sobre la carpeta `test` en el panel de proyecto.
2. Selecciona **"Run 'All Tests'"**.

### Interpretando los resultados

Tras ejecutar, IntelliJ muestra el panel **"Run"** en la parte inferior:

- **Verde ✅** → el test ha pasado. El método se comporta como esperabas.
- **Rojo ❌** → el test ha fallado. IntelliJ te muestra qué valor esperabas y qué valor obtuvo realmente. Ese es tu punto de partida para depurar.

---

## 8. Ejercicios propuestos

Estos ejercicios están diseñados para practicar lo aprendido de menor a mayor dificultad.

### Nivel 1 — Ampliar la calculadora

Añade los siguientes métodos a `Calculadora.java` y escribe al menos **3 tests** por cada uno:

- `restar(int a, int b)`
- `multiplicar(int a, int b)`
- `dividir(int a, int b)` ← Pista: ¿qué pasa si `b` es `0`?

### Nivel 2 — Probar un método con condiciones

Añade este método a `Calculadora`:

```java
public boolean esPositivo(int numero) {
    return numero > 0;
}
```

Escribe tests para: número positivo, número negativo y el caso límite del `0`.

### Nivel 3 — Test que falla a propósito

Modifica temporalmente `Calculadora.java` para que `sumar` reste en lugar de sumar:

```java
public int sumar(int a, int b) {
    return a - b;  // bug introducido a propósito
}
```

Ejecuta los tests. ¿Cuántos fallan? ¿Qué mensaje muestra IntelliJ? Luego corrígelo y comprueba que vuelven a pasar. Este ejercicio muestra **el valor real** de tener tests: detectan el bug automáticamente.

---

> **Recuerda:** un test que nunca falla no te dice nada. Un test que falla cuando introduces un bug es un test que está haciendo su trabajo.
