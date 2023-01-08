package com.cgvsu.render_engine;

import com.cgvsu.math.matrix.Matrix4F;
import com.cgvsu.math.vector.Vector3F;

import javax.vecmath.*;


public class GraphicConveyor {

    public static Matrix4F rotateScaleTranslate() {
        float[][] matrix = new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4F(matrix);
    }

    public static Matrix4F lookAt(Vector3F eye, Vector3F target) {
        return lookAt(eye, target, new Vector3F(0F, 1.0F, 0F));
    }

    public static Matrix4F lookAt(Vector3F eye, Vector3F target, Vector3F up) {
        Vector3F resultX = new Vector3F();
        Vector3F resultY = new Vector3F();
        Vector3F resultZ = new Vector3F();

        resultZ.minusTwoVectors(target, eye);
        resultX.vectorCrossProduct(up, resultZ);
        resultY.vectorCrossProduct(resultZ, resultX);

        resultX.vectorNormalization();
        resultY.vectorNormalization();
        resultZ.vectorNormalization();

        float[][] matrix = new float[][]{
                {resultX.getX(), resultY.getX(), resultZ.getX(), 0},
                {resultX.getY(), resultY.getY(), resultZ.getY(), 0},
                {resultX.getZ(), resultY.getZ(), resultZ.getZ(), 0},
                {-resultX.vectorDotProduct(eye), -resultY.vectorDotProduct(eye), -resultZ.vectorDotProduct(eye), 1}
        };
        return new Matrix4F(matrix);
    }

    public static Matrix4F perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {

        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        return new Matrix4F(new float[][]{
                {tangentMinusOnDegree / aspectRatio, 0,0,0},
                {0,tangentMinusOnDegree,0,0},
                {0,0, (farPlane + nearPlane) / (farPlane - nearPlane), 1.0F},
                {0,0, 2 * (nearPlane * farPlane) / (nearPlane - farPlane), 0}
        });
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4F matrix, final Vector3f vertex) {
        final float x = (vertex.x * matrix.getValues()[0][0]) + (vertex.y * matrix.getValues()[1][0]) +
                (vertex.z * matrix.getValues()[2][0]) + matrix.getValues()[3][0];
        final float y = (vertex.x * matrix.getValues()[0][1]) + (vertex.y * matrix.getValues()[1][1]) +
                (vertex.z * matrix.getValues()[2][1]) + matrix.getValues()[3][1];
        final float z = (vertex.x * matrix.getValues()[0][2]) + (vertex.y * matrix.getValues()[1][2]) +
                (vertex.z * matrix.getValues()[2][2]) + matrix.getValues()[3][2];
        final float w = (vertex.x * matrix.getValues()[0][3]) + (vertex.y * matrix.getValues()[1][3]) +
                (vertex.z * matrix.getValues()[2][3]) + matrix.getValues()[3][3];
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.x * width + width / 2.0F, -vertex.y * height + height / 2.0F);
    }

    public static Point3f vertexToPoint3f(final Vector3f vertex, final int width, final int height) {
        return new Point3f(vertex.x * width + width / 2.0F, -vertex.y * height + height / 2.0F, vertex.z * width + width / 2.0F);
    }
}
