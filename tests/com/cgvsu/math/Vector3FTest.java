package com.cgvsu.math;

import com.cgvsu.math.exception.MathExceptions;
import com.cgvsu.math.vector.Vector;
import com.cgvsu.math.vector.Vector2F;
import com.cgvsu.math.vector.Vector3F;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Vector3FTest {

    @Test
    void testVectorProduct() {

        float[] vectorFirst = new float[]{-3.333f, 0.898f};
        float[] vectorSecond = new float[]{-4.21f, 2.34f, 1};
        Vector v1 = new Vector2F(vectorFirst);
        Vector v2 = new Vector3F(vectorSecond);
        Vector vRes = v1;
        v1.vectorCrossProduct(v2);
        assertArrayEquals(v1.getValues(), vRes.getValues());


        vectorFirst = new float[]{-3.333f, 0.898f};
        vectorSecond = new float[]{-4.21f, 2.34f};
        v1 = new Vector2F(vectorFirst);
        v2 = new Vector2F(vectorSecond);
        vRes = v1;
        v1.vectorCrossProduct(v2);
        assertArrayEquals(v1.getValues(), vRes.getValues());

        vectorFirst = new float[]{-3.333f, 0.898f, 1.87f};
        vectorSecond = new float[]{-4.21f, 2.34f, 0.22f};
        v1 = new Vector3F(vectorFirst);
        v2 = new Vector3F(vectorSecond);

        float[] expected = new float[]{-4.17824f, -7.13944f, -4.01864f};

        v1.vectorCrossProduct(v2);
        assertArrayEquals(expected, v1.getValues(), 0.000001f);
    }
}