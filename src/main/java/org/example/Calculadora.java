package org.example;

public class Calculadora {
    public int sumar(int a, int b) {
        return a + b;
    }

    public int generarNumeroAleatorio(int limite) {
        return (int) (Math.random() * limite);
    }
}
