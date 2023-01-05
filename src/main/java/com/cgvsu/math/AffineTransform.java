package com.cgvsu.math;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix4F;
import com.cgvsu.math.vector.Vector;
import com.cgvsu.math.vector.Vector3F;
import com.cgvsu.math.vector.Vector4F;
import com.cgvsu.model.Model;

import java.util.ArrayList;

public class AffineTransform {

    public ArrayList<Vector3F> transformingVertex;
    public ArrayList<Vector3F> transformedVertex;

    public AffineTransform(ArrayList<Vector3F> vertex) {
        this.transformingVertex = vertex;
    }

    public void setTransformingVertex(ArrayList<Vector3F> transformingModel) {
        this.transformingVertex = transformingModel;
    }

    public void setTransformingModel(Model model) {
        this.transformingVertex = model.vertices;
    }

    public ArrayList<Vector3F> getTransformingModel() {
        return transformingVertex;
    }

    public AffineTransform() {
    }

    public void transforming(float scaleOnX, float scaleOnY, float scaleOnZ,
                             int angleX, int angleY, int angleZ,
                             float translationX, float translationY, float translationZ){
        Vector3F tmpVector;

        for(int vertex = 0; vertex < transformingVertex.size(); vertex++){
            tmpVector = transformingVertex.get(vertex);
            tmpVector = scale(tmpVector, scaleOnX,scaleOnY,scaleOnZ);
            tmpVector = rotation(tmpVector, angleX, angleY, angleZ);
            tmpVector = translation(tmpVector, translationX,translationY,translationZ);
            transformedVertex.add(tmpVector);
        }


    }

    public Vector3F translation(Vector v, float translationX, float translationY, float translationZ){
        v = toVector4F(v);

        Matrix translation = new Matrix4F(new float[][]{
                {1, 0, 0, translationX},
                {0, 1, 0, translationY},
                {0, 0, 1, translationZ},
                {0, 0, 0, 1}
        });

        return toVector3F(translation.productMatrixOnVector(translation,v));
    }

    public Vector3F scale(Vector v, float scaleOnX, float scaleOnY, float scaleOnZ) {
        v = toVector4F(v);

        Matrix scale = new Matrix4F(new float[][]{
                {scaleOnX, 0, 0, 0},
                {0, scaleOnY, 0, 0},
                {0, 0, scaleOnZ, 0},
                {0, 0, 0, 1}
        });

        return toVector3F(scale.productMatrixOnVector(scale, v));
    }

    public Vector3F rotation(Vector v, int angleX, int angleY, int angleZ){

        Vector3F resultVector;

        resultVector = rotationAroundZ(angleZ, rotationAroundY(angleY, rotationAroundX(angleX, v)));

        return resultVector;
    }

    public Vector3F rotationAroundX(int angleX, Vector v) {

        v = toVector4F(v);

        float angleWithPi = (float) (angleX * Math.PI / 180.0);

        Matrix rotateMatrix = new Matrix4F();
        rotateMatrix = setTransformMatrix(rotateMatrix, angleWithPi, -1);

        return toVector3F(rotateMatrix.productMatrixOnVector(rotateMatrix, v));

    }

    public Vector3F rotationAroundY(int angleY, Vector v) {

        v = toVector4F(v);

        float angleWithPi = (float) (angleY * Math.PI / 180.0);

        Matrix rotateMatrix = new Matrix4F();
        rotateMatrix = setTransformMatrix(rotateMatrix, angleWithPi, 0);

        return toVector3F(rotateMatrix.productMatrixOnVector(rotateMatrix, v));

    }

    public Vector3F rotationAroundZ(int angleZ, Vector v) {

        v = toVector4F(v);

        float angleWithPi = (float) (angleZ * Math.PI / 180.0);

        Matrix rotateMatrix = new Matrix4F();
        rotateMatrix = setTransformMatrix(rotateMatrix, angleWithPi, 1);

        return toVector3F(rotateMatrix.productMatrixOnVector(rotateMatrix, v));

    }

    private Vector3F toVector3F(Vector vector){
        Vector3F resultVector = new Vector3F();

        float[] tmp = new float[3];

        for(int i = 0; i < tmp.length; i++){
            tmp[i] = vector.getValues()[i];
        }

        resultVector.setValues(tmp);
        return resultVector;
    }

    private Vector4F toVector4F(Vector vector) {
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
