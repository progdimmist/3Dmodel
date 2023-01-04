package com.cgvsu.math;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix3F;
import com.cgvsu.math.matrix.Matrix4F;
import com.cgvsu.math.vector.Vector;
import com.cgvsu.math.vector.Vector4F;

public class AffineTransform {

    public Vector translation(Vector v, Vector vectorTranslation){
        v = toVector4F(v);

        Matrix translation = new Matrix4F(new float[][]{
                {1, 0, 0, vectorTranslation.getValues()[0]},
                {0, 1, 0, vectorTranslation.getValues()[1]},
                {0, 0, 1, vectorTranslation.getValues()[2]},
                {0, 0, 0, 1}
        });

        return translation.productMatrixOnVector(translation,v);
    }

    public Vector scale(Vector v, float onX, float onY, float onZ) {
        v = toVector4F(v);

        Matrix scale = new Matrix4F(new float[][]{
                {onX, 0, 0, 0},
                {0, onY, 0, 0},
                {0, 0, onZ, 0},
                {0, 0, 0, 1}
        });

        return scale.productMatrixOnVector(scale, v);
    }

    public Vector rotationAroundX(int angle, Vector v) {

        v = toVector4F(v);

        float angleWithPi = (float) (angle * Math.PI / 180.0);

        Matrix rotateMatrix = new Matrix4F();
        rotateMatrix = setTransformMatrix(rotateMatrix, angleWithPi, -1);

        return rotateMatrix.productMatrixOnVector(rotateMatrix, v);

    }

    public Vector rotationAroundY(int angle, Vector v) {

        v = toVector4F(v);

        float angleWithPi = (float) (angle * Math.PI / 180.0);

        Matrix rotateMatrix = new Matrix4F();
        rotateMatrix = setTransformMatrix(rotateMatrix, angleWithPi, 0);

        return rotateMatrix.productMatrixOnVector(rotateMatrix, v);

    }

    public Vector rotationAroundZ(int angle, Vector v) {

        v = toVector4F(v);

        float angleWithPi = (float) (angle * Math.PI / 180.0);

        Matrix rotateMatrix = new Matrix4F();
        rotateMatrix = setTransformMatrix(rotateMatrix, angleWithPi, 1);

        return rotateMatrix.productMatrixOnVector(rotateMatrix, v);

    }

    private Vector toVector4F(Vector vector) {
        Vector4F resultVector = new Vector4F();

        float[] tmp = new float[4];
        for (int i = 0; i < vector.getSize(); i++) {
            tmp[i] = vector.getValues()[i];
        }

        for (int i = vector.getSize(); i < tmp.length; i++) {
            tmp[i] = 1;
        }

        resultVector.setValues(tmp);
        return resultVector;
    }

    public Matrix setTransformMatrix(Matrix rotateMatrix, float angleWithPi, int axis) {

        switch (axis) {
            case -1 -> {
                rotateMatrix.setSingleMatrix();
                float[][] tmp = new float[][]{
                        {1, 0, 0, 0},
                        {0, (float) Math.cos(angleWithPi), (float) -Math.sin(angleWithPi), 0},
                        {0, (float) Math.sin(angleWithPi), (float) Math.cos(angleWithPi), 0},
                        {0, 0, 0, 1}
                };
                rotateMatrix.setValue(tmp);
                return rotateMatrix;
            }
            case 0 -> {
                rotateMatrix.setSingleMatrix();
                float[][] tmp = new float[][]{
                        {(float) Math.cos(angleWithPi), 0, (float) -Math.sin(angleWithPi), 0},
                        {0, 1, 0, 0},
                        {(float) Math.sin(angleWithPi), 0, (float) Math.cos(angleWithPi), 0},
                        {0, 0, 0, 1}
                };
                rotateMatrix.setValue(tmp);
                return rotateMatrix;
            }
            case 1 -> {
                rotateMatrix.setSingleMatrix();
                float[][] tmp = new float[][]{
                        {(float) Math.cos(angleWithPi), (float) -Math.sin(angleWithPi), 0, 0},
                        {(float) Math.sin(angleWithPi), (float) Math.cos(angleWithPi), 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}
                };
                rotateMatrix.setValue(tmp);
                return rotateMatrix;
            }
        }
        return rotateMatrix;
    }

}
