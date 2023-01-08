package com.cgvsu.render_engine;
import com.cgvsu.math.AffineTransform;
import com.cgvsu.math.matrix.Matrix3F;
import com.cgvsu.math.matrix.Matrix4F;
import com.cgvsu.math.vector.Vector3F;

import javax.vecmath.Matrix4f;
import java.util.Arrays;

public class Camera {

    public Camera(
            final Vector3F position,
            final Vector3F target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public void setPosition(final Vector3F position) {
        this.position = position;
    }

    public void setTarget(final Vector3F target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3F getPosition() {
        return position;
    }

    public Vector3F getTarget() {
        return target;
    }

    public void movePosition(final Vector3F translation) {
        this.position.sumVectors(translation);
    }

    public void scalePosition(final Vector3F scale){
        this.position = affineTransform.scale(this.position, scale.getX(), scale.getY(), scale.getZ());
    }

    public void rotationPositionAroundX(final int angle){
        this.position = affineTransform.rotationAroundX(angle,this.position);
    }
    public void rotationPositionAroundY(final int angle){
        this.position = affineTransform.rotationAroundY(angle,this.position);
    }
    public void rotationPositionAroundZ(final int angle){
        this.position = affineTransform.rotationAroundZ(angle,this.position);
    }

    public void moveTarget(final Vector3F translation) {
        this.target.sumVectors(translation);;
    }

    public void rotationAroundProizvolAxes(double angle){
        Vector3F v = new Vector3F();
        v = this.target;
        v.minusTwoVectors(this.position);
        //v.setValues(new float[]{0.5f,0.5f,0.5f});
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        v.vectorNormalization();
        float x = v.getX();
        float y = v.getY();
        float z = v.getZ();
        System.out.println(x + " " + y + " " + z);
        Matrix3F mRotationAroundAxes = new Matrix3F(
                new float[][]{
                        {cos + (1-cos) * x*x, (1-cos)*x*y - sin*z, (1-cos)*x*z + sin *y},
                        {(1-cos)*y*x + sin*z, cos+(1-cos) * y*y, (1-cos) * y*z - sin*x},
                        {(1-cos)*z*x - sin*y, (1-cos)*z*y + sin*x, cos+(1-cos) * z * z}
                }
        );
        System.out.println(Arrays.deepToString(mRotationAroundAxes.getValues()));

        this.position = (Vector3F) mRotationAroundAxes.productMatrixOnVector(mRotationAroundAxes, this.position);
    }

    public void rotationAroundAxes(double angleX, double angleY, double angleZ){
        float sinX = (float) Math.sin(angleX);
        float sinY = (float) Math.sin(angleY);
        float sinZ = (float) Math.sin(angleZ);
        float cosX = (float) Math.cos(angleX);
        float cosY = (float) Math.cos(angleY);
        float cosZ = (float) Math.cos(angleZ);
        Matrix3F mRotationAroundAxes = new Matrix3F(
                new float[][]{
                        {cosY * cosZ, sinY * sinX - cosY * sinZ * cosX, cosY * sinZ * sinX + sinY * cosX},
                        {sinZ, cosY * cosX, -cosZ * sinX},
                        {-sinY * cosZ, sinX * sinZ * cosX + cosY * sinX, cosY * cosX - sinY * sinZ * sinX}
                }
        );
        this.position = (Vector3F) mRotationAroundAxes.productMatrixOnVector(mRotationAroundAxes, this.position);

    }

    Matrix4F getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    Matrix4F getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    private Vector3F position;
    private Vector3F target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;
    private final AffineTransform affineTransform = new AffineTransform();
}