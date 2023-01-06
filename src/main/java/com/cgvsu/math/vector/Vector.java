package com.cgvsu.math.vector;

public interface Vector {

    float getX();

    float getY();

    float getZ();

    float getW();
    float length();

    Vector vectorCrossProduct(final Vector v1, final Vector v2);

    void setValues(final float[] values);

    Vector sumVectors(final Vector v1, final Vector v2);

    Vector minusTwoVectors(final Vector v1, final Vector v2);

    Vector productVectorOnScalar(final Vector v1, final float scalar);

    Vector divisionVectorOnScalar(final Vector v1, final float scalar);

    float vectorLength(final Vector v1);

    Vector vectorNormalization(final Vector v1);

    float vectorDotProduct(final Vector v1, final Vector v2);

    float[] getValues();

    int getSize();

}
