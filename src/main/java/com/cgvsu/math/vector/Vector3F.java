package com.cgvsu.math.vector;

import com.cgvsu.math.exception.MathExceptions;

public class Vector3F extends AbstractVector implements Vector {

    public Vector3F() {
    }

    public Vector3F(float[] values) {
        if (checkLengthInputValues(values)) {
            super.values = values;
            super.size = values.length;
        } else throw new MathExceptions();
    }

    public Vector3F(float v1, float v2, float v3) {
        super.values = new float[3];

        super.size = 3;

        super.values[0] = v1;
        super.values[1] = v2;
        super.values[2] = v3;
    }

    public void vectorCrossProduct(Vector v2) {
        Vector3F vRes = new Vector3F();
        float[] tmp = new float[3];
        if (this.getSize() != v2.getSize() || !checkLengthInputValues(this.getValues())
                || !checkLengthInputValues(v2.getValues())) {
            throw new MathExceptions();
        }

        tmp[0] = this.getValues()[1] * v2.getValues()[2] - this.getValues()[2] * v2.getValues()[1];
        tmp[1] = -(this.getValues()[0] * v2.getValues()[2] - this.getValues()[2] * v2.getValues()[0]);
        tmp[2] = this.getValues()[0] * v2.getValues()[1] - this.getValues()[1] * v2.getValues()[0];
        vRes.setValues(tmp);

        this.values = vRes.values;
        this.size = vRes.size;
    }

    @Override
    public Vector vectorCrossProduct(Vector v1, Vector v2) {
        return null;
    }

    @Override
    protected boolean checkLengthInputValues(float[] values) {
        return values.length == 3;
    }

}
