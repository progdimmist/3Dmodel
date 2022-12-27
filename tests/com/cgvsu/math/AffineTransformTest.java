package com.cgvsu.math;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix3F;
import com.cgvsu.math.vector.Vector;
import com.cgvsu.math.vector.Vector3F;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AffineTransformTest {

    AffineTransform at = new AffineTransform();

    @Test
    void testSetTransformMatrix() {

        Matrix rotateMatrix = new Matrix3F();

        Matrix actual = at.setTransformMatrix(rotateMatrix, (float) (Math.PI / 6), -1);

        Matrix expected = new Matrix3F(new float[][]{
                {1,0,0},
                {0, (float) (Math.sqrt(3) / 2), -0.5f},
                {0, 0.5f, (float) (Math.sqrt(3) / 2)}
        });

        for (int i = 0; i < actual.getSize(); i++) {
            assertArrayEquals(expected.getValues()[i], actual.getValues()[i]);
        }
    }

    @Test
    void testRotationAroundX(){

        Vector v = new Vector3F(new float[]{1,2,3});

        v = at.rotationAroundX(30, v);

        Vector expected = new Vector3F(new float[]{1,0.232f,3.598f});

        assertArrayEquals(expected.getValues(), v.getValues(), 0.001f);


    }

}