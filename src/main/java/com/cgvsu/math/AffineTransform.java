package com.cgvsu.math;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix3F;
import com.cgvsu.math.matrix.Matrix4F;
import com.cgvsu.math.vector.Vector;

public class AffineTransform {

    public Vector rotationAroundX (int angle, Vector v){

        float angleWithPi = (float) (angle * Math.PI / 180.0);

        Matrix rotateMatrix = new Matrix3F();
        rotateMatrix = setTransformMatrix(rotateMatrix, angleWithPi, -1);

        return rotateMatrix.productMatrixOnVector(rotateMatrix, v);

    }

    public Matrix setTransformMatrix(Matrix rotateMatrix, float angleWithPi, int axis){

        int matrixSize = rotateMatrix.getSize();

        switch (axis) {
            case -1 -> {
                rotateMatrix.setSingleMatrix();
                float[][] tmp = new float[][]{
                        {1, 0, 0},
                        {0, (float) Math.cos(angleWithPi), (float) -Math.sin(angleWithPi)},
                        {0, (float) Math.sin(angleWithPi), (float) Math.cos(angleWithPi)},
                };
                rotateMatrix.setValue(tmp);
                return rotateMatrix;
            }
            case 0 -> {
                rotateMatrix.setSingleMatrix();
                float[][] tmp = new float[][]{
                        {(float) Math.cos(angleWithPi), 0, (float) -Math.sin(angleWithPi)},
                        {0, 1, 0},
                        {(float) Math.sin(angleWithPi), 0, (float) Math.cos(angleWithPi)}
                };
                rotateMatrix.setValue(tmp);
                return rotateMatrix;
            }
            case 1 -> {
                rotateMatrix.setSingleMatrix();
                float[][] tmp = new float[][]{
                        {(float) Math.cos(angleWithPi), (float) -Math.sin(angleWithPi), 0},
                        {(float) Math.sin(angleWithPi), (float) Math.cos(angleWithPi), 0},
                        {0, 0, 1}
                };
                rotateMatrix.setValue(tmp);
                return rotateMatrix;
            }
        }
        return rotateMatrix;
    }

}
