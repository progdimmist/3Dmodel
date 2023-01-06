package com.cgvsu.math.vector;

import com.cgvsu.math.exception.MathExceptions;

public abstract class AbstractVector implements Vector {
    protected int size;
    protected float[] values;

    @Override
    public float getX(){
        try {
            return values[0];
        } catch (Exception e){
            throw new MathExceptions("Error in Vector values");
        }
    }
    @Override
    public float getY(){
        try {
            return values[1];
        } catch (Exception e){
            throw new MathExceptions("Error in Vector values");
        }
    }
    @Override
    public float getZ(){
        try {
            return values[2];
        } catch (Exception e){
            throw new MathExceptions("Error in Vector values");
        }
    }
    @Override
    public float getW(){
        try {
            return values[3];
        } catch (Exception e){
            throw new MathExceptions("Error in Vector values");
        }
    }

    @Override
    public abstract Vector vectorCrossProduct(final Vector v1, final Vector v2);

    @Override
    public abstract float length();

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
    public Vector sumVectors(final Vector v1, final Vector v2) {

        if (v1.getSize() == v2.getSize()) {
            float[] tmp = new float[v1.getSize()];

            for (int i = 0; i < v1.getSize(); i++) {
                tmp[i] = v1.getValues()[i] + v2.getValues()[i];
            }

            this.values = tmp;
        } else throw new MathExceptions();
        return this;
    }

    @Override
    public Vector minusTwoVectors(final Vector v1, final Vector v2) {

        if (v1.getSize() == v2.getSize()) {
            float[] tmp = new float[v1.getSize()];

            for (int i = 0; i < v1.getSize(); i++) {
                tmp[i] = v1.getValues()[i] - v2.getValues()[i];
            }

            this.values = tmp;
        } else throw new MathExceptions();
        return this;
    }

    @Override
    public Vector productVectorOnScalar(final Vector v1, final float scalar) {

        float[] tmp = new float[v1.getSize()];

        for (int i = 0; i < v1.getSize(); i++) {
            tmp[i] = v1.getValues()[i] * scalar;
        }

        this.values = tmp;

        return this;
    }

    @Override
    public Vector divisionVectorOnScalar(final Vector v1, final float scalar) {

        float[] tmp = new float[v1.getSize()];

        if (scalar != 0) {

            for (int i = 0; i < v1.getSize(); i++) {
                tmp[i] = v1.getValues()[i] / scalar;
            }

            this.values = tmp;
        } else {
            throw new MathExceptions();
        }

        return this;
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
    public Vector vectorNormalization(final Vector v1) {

        float[] tmp = new float[v1.getSize()];

        float length = v1.vectorLength(v1);
        for (int i = 0; i < v1.getSize(); i++) {
            tmp[i] = v1.getValues()[i] / length;
        }

        this.values = (tmp);

        return this;
    }

    @Override
    public float vectorDotProduct(final Vector v1, final Vector v2) {
        float tmp = 0;
        if (v1.getSize() == v2.getSize()) {
            for (int i = 0; i < v1.getSize(); i++) {
                tmp = tmp + v1.getValues()[i] * v2.getValues()[i];
            }
        } else throw new MathExceptions();
        return tmp;
    }
}
