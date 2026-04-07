package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la clase Calculadora.
 *
 * Convención de nomenclatura para cada método de test:
 *   metodo_escenario_resultadoEsperado()
 *
 * Estructura interna de cada test (patrón AAA):
 *   Arrange  → prepara los datos de entrada
 *   Act      → llama al método bajo prueba
 *   Assert   → verifica que el resultado es el esperado
 */
@DisplayName("Calculadora - Tests unitarios")
class CalculadoraTest {

    // La instancia se recrea antes de cada test para garantizar aislamiento.
    private Calculadora calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new Calculadora();
    }

    // ─────────────────────────────────────────────
    // sumar()
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("sumar dos positivos devuelve su suma correcta")
    void sumar_dosPositivos_retornaSuma() {
        // Arrange
        int a = 3, b = 4;

        // Act
        int resultado = calculadora.sumar(a, b);

        // Assert
        assertEquals(7, resultado, "3 + 4 debe ser 7");
    }

    @Test
    @DisplayName("sumar con un negativo devuelve resultado correcto")
    void sumar_unNegativo_retornaResultadoCorrecto() {
        // Arrange
        int a = 10, b = -3;

        // Act
        int resultado = calculadora.sumar(a, b);

        // Assert
        assertEquals(7, resultado, "10 + (-3) debe ser 7");
    }

    @Test
    @DisplayName("sumar dos negativos devuelve negativo")
    void sumar_dosNegativos_retorraNegativos() {
        // Arrange
        int a = -5, b = -2;

        // Act
        int resultado = calculadora.sumar(a, b);

        // Assert
        assertEquals(-7, resultado, "(-5) + (-2) debe ser -7");
    }

    @Test
    @DisplayName("sumar cero no cambia el valor")
    void sumar_conCero_retornaElMismoValor() {
        // Arrange
        int a = 9, b = 0;

        // Act
        int resultado = calculadora.sumar(a, b);

        // Assert
        assertEquals(9, resultado, "9 + 0 debe ser 9");
    }

    // ─────────────────────────────────────────────
    // generarNumeroAleatorio()
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("dos llamadas consecutivas no deben devolver siempre el mismo número")
    void generarNumeroAleatorio_dosLlamadas_noSonIguales() {
        // Arrange - límite grande para que la probabilidad de colisión sea mínima
        int limite = 1_000_000;

        // Act
        int primera = calculadora.generarNumeroAleatorio(limite);
        int segunda = calculadora.generarNumeroAleatorio(limite);

        // Assert: si ambas llamadas devuelven lo mismo, el generador está roto
        assertNotEquals(primera, segunda, "Un generador aleatorio no debe repetir siempre el mismo valor");
    }

    // ─────────────────────────────────────────────
    // esPar()
    // ─────────────────────────────────────────────

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

    // ─────────────────────────────────────────────
    // buscarOperacion()
    // ─────────────────────────────────────────────

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

    // ─────────────────────────────────────────────
    // dividir()
    // ─────────────────────────────────────────────

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

    // ─────────────────────────────────────────────
    // assertAll — múltiples propiedades de sumar()
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("el resultado de sumar(6, 4) cumple varias propiedades a la vez")
    void sumar_resultado_cumpleVariasPropiedades() {
        // Arrange
        int a = 6, b = 4;

        // Act
        int resultado = calculadora.sumar(a, b);

        // Assert: assertAll ejecuta TODAS las comprobaciones aunque alguna falle,
        // así ves de golpe cuántas y cuáles están rotas.
        assertAll("propiedades del resultado de sumar(6, 4)",
            () -> assertEquals(10, resultado,     "debe ser 10"),
            () -> assertTrue(resultado > 0,       "debe ser positivo"),
            () -> assertTrue(resultado % 2 == 0,  "debe ser par"),
            () -> assertNotEquals(0, resultado,   "no debe ser cero")
        );
    }

    // ─────────────────────────────────────────────
    // sumarConRetraso() — assertTimeout
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("sumarConRetraso termina dentro del tiempo límite")
    void sumarConRetraso_terminaDentroDelTiempoLimite() {
        // Assert: el método debe completarse en menos de 500ms.
        // La lambda envuelve la llamada porque assertTimeout necesita medirla.
        assertTimeout(
            Duration.ofMillis(500),
            () -> calculadora.sumarConRetraso(3, 4),
            "El método debe terminar en menos de 500ms"
        );
    }

    @Test
    @Disabled("Test de ejemplo didáctico: falla a propósito para mostrar cómo se ve un timeout en IntelliJ")
    @DisplayName("[EJEMPLO] sumarConRetraso supera el tiempo límite — falla a propósito")
    void sumarConRetraso_superaElTiempoLimite_fallaAProposito() {
        // El límite (50ms) es menor que el retraso real (100ms) → el test FALLA.
        // Elimina @Disabled y ejecútalo para ver el mensaje de timeout en IntelliJ.
        assertTimeout(
            Duration.ofMillis(50),
            () -> calculadora.sumarConRetraso(3, 4),
            "Este test está diseñado para fallar: 50ms < 100ms de retraso"
        );
    }

}
