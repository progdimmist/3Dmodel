package com.cgvsu.render_engine;
import com.cgvsu.math.AffineTransform;
import com.cgvsu.math.matrix.Matrix3F;
import com.cgvsu.math.matrix.Matrix4F;
import com.cgvsu.math.vector.Vector3F;

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

    public void moveTarget(final Vector3F translation) {
        this.target.sumVectors(translation);;
    }


    Vector3F resultY = new Vector3F(new float[]{0,1.0f,0});
    public Vector3F vectorY(){
        Vector3F resultX = new Vector3F();
        Vector3F resultZ = new Vector3F();

        resultZ.minusTwoVectors(target, position);
        resultX.vectorCrossProduct(resultY, resultZ);
        resultY.vectorCrossProduct(resultZ, resultX);

        resultX.vectorNormalization();
        resultY.vectorNormalization();
        resultZ.vectorNormalization();

        return resultY;
    }
    public Vector3F vectorZ(){
        Vector3F resultX = new Vector3F();
        Vector3F resultZ = new Vector3F();

        resultZ.minusTwoVectors(target, position);
        resultX.vectorCrossProduct(resultY, resultZ);
        resultY.vectorCrossProduct(resultZ, resultX);

        return resultZ;
    }
    public Vector3F vectorX(){
        Vector3F resultX = new Vector3F();
        Vector3F resultZ = new Vector3F();

        resultZ.minusTwoVectors(target, position);
        resultX.vectorCrossProduct(resultY, resultZ);
        resultY.vectorCrossProduct(resultZ, resultX);

        resultX.vectorNormalization();
        resultY.vectorNormalization();
        resultZ.vectorNormalization();

        return resultX;
    }

    public void rotationAroundChangedX(double angle){
        Vector3F resultX = vectorX();
        rotationAroundVector(angle, resultX);
    }

    public void rotationAroundChangedY(double angle){

        resultY = vectorY();
        rotationAroundVector(angle, resultY);
    }

    private void rotationAroundVector(double angle, Vector3F result) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        result.vectorNormalization();
        float x = result.getX();
        float y = result.getY();
        float z = result.getZ();
        Matrix3F mRotationAroundAxes = new Matrix3F(
                new float[][]{
                        {cos + (1-cos) * x*x, (1-cos)*x*y - sin*z, (1-cos)*x*z + sin *y},
                        {(1-cos)*y*x + sin*z, cos+(1-cos) * y*y, (1-cos) * y*z - sin*x},
                        {(1-cos)*z*x - sin*y, (1-cos)*z*y + sin*x, cos+(1-cos) * z * z}
                }
        );

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