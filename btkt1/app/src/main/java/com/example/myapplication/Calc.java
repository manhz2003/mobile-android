package com.example.myapplication;

public class Calc {
    private int a;
    private int b;

    public Calc(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int add() {
        return a + b;
    }

    public int subtract() {
        return a - b;
    }

    public int multiply() {
        return a * b;
    }

    public int divide() {
        if (b != 0) {
            return a / b;
        } else {
            throw new ArithmeticException("Không thể chia cho 0");
        }
    }
}
