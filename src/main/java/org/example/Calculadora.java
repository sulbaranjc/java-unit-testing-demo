package org.example;

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

    public int sumarConRetraso(int a, int b) throws InterruptedException {
        Thread.sleep(100); // simula 100ms de trabajo (p.ej. consulta a base de datos)
        return a + b;
    }
}
