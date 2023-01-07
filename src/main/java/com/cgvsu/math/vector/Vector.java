package com.cgvsu.math.vector;

public interface Vector {

    float getX();

    float getY();

    float getZ();

    float getW();

    void vectorCrossProduct(final Vector v2);

    void setValues(final float[] values);

    void sumVectors(final Vector v2);

    void minusTwoVectors(final Vector v2);

    void productVectorOnScalar(final float scalar);

    void divisionVectorOnScalar(final float scalar);

    float vectorLength(final Vector v1);

    Vector vectorNormalization();

    float vectorDotProduct(final Vector v2);

    float[] getValues();

    int getSize();

}
