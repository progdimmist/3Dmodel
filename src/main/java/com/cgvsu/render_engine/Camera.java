package com.cgvsu.render_engine;
import com.cgvsu.math.AffineTransform;
import com.cgvsu.math.vector.Vector3F;

import javax.vecmath.Vector3f;
import javax.vecmath.Matrix4f;

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

    Matrix4f getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    Matrix4f getProjectionMatrix() {
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