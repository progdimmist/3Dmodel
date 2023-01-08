package com.cgvsu.math;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix3F;
import com.cgvsu.math.matrix.Matrix4F;
import com.cgvsu.math.vector.Vector;
import com.cgvsu.math.vector.Vector3F;
import com.cgvsu.math.vector.Vector4F;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AffineTransformTest {

    AffineTransform at = new AffineTransform();

    @Test
    void testSetTransformMatrix() {

        Matrix rotateMatrix = new Matrix4F();

        Matrix actual = at.setTransformMatrix(rotateMatrix, (float) (Math.PI / 6), -1);

        Matrix expected = new Matrix4F(new float[][]{
                {1, 0, 0, 0},
                {0, (float) (Math.sqrt(3) / 2), -0.5f, 0},
                {0, 0.5f, (float) (Math.sqrt(3) / 2), 0},
                {0, 0, 0, 1}
        });

        for (int i = 0; i < actual.getSize(); i++) {
            assertArrayEquals(expected.getValues()[i], actual.getValues()[i]);
        }
    }

    //todo переделать тесты
    @Test
    void testRotationAroundX() {

        Vector v = new Vector3F(new float[]{1, 2, 3});

        v = at.rotationAroundX(30, v);

        Vector expected = new Vector4F(new float[]{1, 0.232f, 3.598f, 1});

        assertArrayEquals(expected.getValues(), v.getValues(), 0.001f);


    }

    @Test
    void scale() {
        Vector v = new Vector3F(new float[]{1, 2, 3});

        v = at.scale(v, -1,-1,-1);
        Vector expected = new Vector4F(new float[]{-1, -2, -3, 1});
        assertArrayEquals(expected.getValues(), v.getValues(), 0.001f);

    }

    @Test
    void rotationAroundY() {

        Vector v = new Vector3F(new float[]{1, 2, 3});

        v = at.rotationAroundY(30, v);

        Vector expected = new Vector4F(new float[]{-0.634f, 2, 3.098f, 1});

        assertArrayEquals(expected.getValues(), v.getValues(), 0.001f);

    }

    @Test
    void rotationAroundZ() {

        Vector v = new Vector3F(new float[]{1, 2, 3});

        v = at.rotationAroundZ(30, v);

        Vector expected = new Vector4F(new float[]{-0.134f, 2.232f, 3, 1});

        assertArrayEquals(expected.getValues(), v.getValues(), 0.001f);

    }

    @Test
    void testScale(){
        Matrix4F modelMatrix = new Matrix4F(new float[][] {
                {1,0,0,0},
                {0,1,0,0},
                {0,0,1,0},
                {0,0,0,1}
        });

        Matrix4F actual = at.scale(2,2,2,modelMatrix);
        Matrix4F expected = new Matrix4F(new float[][] {
                {2,0,0,0},
                {0,2,0,0},
                {0,0,2,0},
                {0,0,0,1}
        });


        for (int i = 0; i < actual.getSize(); i++) {
            assertArrayEquals(expected.getValues()[i], actual.getValues()[i]);
        }

    }

    @Test
    void translation() {
        Vector v = new Vector3F(new float[]{1, 2, 3});
        Vector vTransl = new Vector3F(new float[]{1, 2, 3});

        v = at.translation(v,1,2,3);

        Vector expected = new Vector3F(new float[]{2,4,6});

        assertArrayEquals(expected.getValues(), v.getValues(), 0.001f);

    }
}