package com.cgvsu.math.vector;
import com.cgvsu.math.exception.MathExceptions;

public class Vector2F extends AbstractVector implements Vector {

    public Vector2F(float[] values) {
        if (checkLengthInputValues(values)) {
            super.values = values;
            super.size = values.length;
        } else throw new MathExceptions();
    }

    public Vector2F(float v1, float v2) {
        super.values = new float[2];

        super.size = 2;

        super.values[0] = v1;
        super.values[1] = v2;
    }

    public Vector2F() {
    }

    @Override
    public Vector vectorCrossProduct(Vector v1, Vector v2) {
        return null;
    }

    @Override
    public float length() {
        return (float) Math.sqrt(values[0]*values[0]+values[1]*values[1]);
    }

    @Override
    protected boolean checkLengthInputValues(float[] values) {
        return values.length == 2;
    }
}
