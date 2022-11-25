package com.cgvsu.math;

// Это заготовка для собственной библиотеки для работы с линейной алгеброй
public class Vector2f {
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x, y;


    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public double vectorMultiplication(Vector2f v) {
        return x * v.y - y * v.x;
    }
}
