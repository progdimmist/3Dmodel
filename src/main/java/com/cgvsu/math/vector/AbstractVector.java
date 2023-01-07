package com.cgvsu.math.vector;

import com.cgvsu.math.exception.MathExceptions;

public abstract class AbstractVector implements Vector {
    protected int size;
    protected float[] values;

    @Override
    public Vector minusTwoVectors(final Vector v1, final Vector v2) {

        if (v1.getSize() != v2.getSize()) {
            throw new MathExceptions();
        }
        float[] tmp = new float[v1.getSize()];

        for (int i = 0; i < v1.getSize(); i++) {
            tmp[i] = v1.getValues()[i] - v2.getValues()[i];
        }

        this.values = tmp;
        this.size = tmp.length;

        return this;
    }

    @Override
    public Vector productVectorOnScalar(final Vector v1, final float scalar) {

        float[] tmp = new float[v1.getSize()];

        for (int i = 0; i < v1.getSize(); i++) {
            tmp[i] = v1.getValues()[i] * scalar;
        }

        this.values = tmp;
        this.size = tmp.length;

        return this;
    }

    @Override
    public Vector divisionVectorOnScalar(final Vector v1, final float scalar) {

        float[] tmp = new float[v1.getSize()];

        if (scalar == 0) {
            throw new MathExceptions();
        }

        for (int i = 0; i < v1.getSize(); i++) {
            tmp[i] = v1.getValues()[i] / scalar;
        }

        this.values = tmp;
        this.size = tmp.length;

        return this;
    }


    @Override
    public float getX() {
        try {
            return values[0];
        } catch (Exception e) {
            throw new MathExceptions("Error in Vector values");
        }
    }

    @Override
    public float getY() {
        try {
            return values[1];
        } catch (Exception e) {
            throw new MathExceptions("Error in Vector values");
        }
    }

    @Override
    public float getZ() {
        try {
            return values[2];
        } catch (Exception e) {
            throw new MathExceptions("Error in Vector values");
        }
    }

    @Override
    public float getW() {
        try {
            return values[3];
        } catch (Exception e) {
            throw new MathExceptions("Error in Vector values");
        }
    }

    @Override
    public abstract void vectorCrossProduct(final Vector v2);

    protected abstract boolean checkLengthInputValues(final float[] values);

    @Override
    public float[] getValues() {
        return values;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setValues(final float[] values) {
        if (checkLengthInputValues(values)) {
            this.values = values;
            this.size = values.length;
        } else throw new MathExceptions();
    }

    @Override
    public void sumVectors(final Vector v2) {

        if (this.getSize() != v2.getSize()) {
            throw new MathExceptions();
        }
        float[] tmp = new float[this.getSize()];

        for (int i = 0; i < this.getSize(); i++) {
            tmp[i] = this.getValues()[i] + v2.getValues()[i];
        }

        this.values = tmp;
        this.size = tmp.length;
    }

    @Override
    public Vector sumVectors(final Vector v1, final Vector v2) {
        if (v1.getSize() != v2.getSize()) {
            throw new MathExceptions();
        }

        float[] tmp = new float[v1.getSize()];

        for (int i = 0; i < v1.getSize(); i++) {
            tmp[i] = v1.getValues()[i] + v2.getValues()[i];
        }

        this.values = tmp;
        this.size = tmp.length;
        return this;
    }

    @Override
    public void minusTwoVectors(final Vector v2) {

        if (this.getSize() != v2.getSize()) {
            throw new MathExceptions();
        }

        float[] tmp = new float[this.getSize()];

        for (int i = 0; i < this.getSize(); i++) {
            tmp[i] = this.getValues()[i] - v2.getValues()[i];
        }

        this.values = tmp;
        this.size = tmp.length;
    }

    @Override
    public void productVectorOnScalar(final float scalar) {

        float[] tmp = new float[this.getSize()];

        for (int i = 0; i < this.getSize(); i++) {
            tmp[i] = this.getValues()[i] * scalar;
        }

        this.values = tmp;
        this.size = tmp.length;
    }

    @Override
    public void divisionVectorOnScalar(final float scalar) {

        float[] tmp = new float[this.getSize()];

        if (scalar == 0) {
            throw new MathExceptions();
        }

        for (int i = 0; i < this.getSize(); i++) {
            tmp[i] = this.getValues()[i] / scalar;
        }

        this.values = tmp;
        this.size = tmp.length;
    }

    @Override
    public float vectorLength(final Vector v1) {
        float tmp = 0;

        for (int i = 0; i < v1.getSize(); i++) {
            tmp = tmp + v1.getValues()[i] * v1.getValues()[i];
        }

        return (float) Math.sqrt(tmp);
    }

    @Override
    public Vector vectorNormalization() {

        float[] tmp = new float[this.getSize()];

        float length = this.vectorLength(this);
        for (int i = 0; i < this.getSize(); i++) {
            tmp[i] = this.getValues()[i] / length;
        }

        this.values = tmp;
        this.size = tmp.length;
        return this;
    }

    @Override
    public float vectorDotProduct(final Vector v2) {
        float tmp = 0;
        if (this.getSize() == v2.getSize()) {
            for (int i = 0; i < this.getSize(); i++) {
                tmp = tmp + this.getValues()[i] * v2.getValues()[i];
            }
        } else throw new MathExceptions();
        return tmp;
    }
}
