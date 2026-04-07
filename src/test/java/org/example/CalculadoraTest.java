package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
