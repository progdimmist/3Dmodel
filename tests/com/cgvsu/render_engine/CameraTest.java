package com.cgvsu.render_engine;

import com.cgvsu.math.vector.Vector3F;
import org.junit.jupiter.api.Test;

class CameraTest {

    @Test
    void testVector() {
        Vector3F target = new Vector3F(new float[]{0,0,0});
        Vector3F position = new Vector3F(new float[]{0,0,15});
        Camera camera = new Camera(position,target,0,0,0,0);
        camera.vectorY();

        camera.rotationAroundChangedX(Math.PI / 20);
    }
}