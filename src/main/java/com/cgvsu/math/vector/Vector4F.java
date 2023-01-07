package com.cgvsu.math.vector;

import com.cgvsu.math.exception.MathExceptions;

public class Vector4F extends AbstractVector implements Vector {

    public Vector4F() {
    }

    public Vector4F(float[] values) {
        if (checkLengthInputValues(values)) {
            super.values = values;
            super.size = values.length;
        } else throw new MathExceptions();
    }

    public Vector4F(float v1, float v2, float v3, float v4) {
        super.values = new float[4];

        super.size = 4;

        super.values[0] = v1;
        super.values[1] = v2;
        super.values[2] = v3;
        super.values[3] = v4;
    }

    @Override
    public void vectorCrossProduct(Vector v2) {
    }

    @Override
    protected boolean checkLengthInputValues(float[] values) {
        return values.length == 4;
    }

}
