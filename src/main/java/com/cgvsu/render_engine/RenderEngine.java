package com.cgvsu.render_engine;

import java.util.ArrayList;

import com.cgvsu.math.matrix.Matrix4F;
import com.cgvsu.math.vector.Vector3F;
import com.cgvsu.rasterization.GraphicsUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javax.vecmath.*;

import com.cgvsu.model.Model;
import javafx.scene.layout.AnchorPane;

import static com.cgvsu.render_engine.GraphicConveyor.*;

public class RenderEngine {

    @FXML
    static
    AnchorPane anchorPane;
    @FXML
    private static Canvas canvas;

    public static void render(
            final GraphicsContext graphicsContext,
            final GraphicsUtils graphicsUtils,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height) {

        Matrix4F modelMatrix = rotateScaleTranslate();
        Matrix4F viewMatrix = camera.getViewMatrix();
        Matrix4F projectionMatrix = camera.getProjectionMatrix();

        Matrix4F modelViewProjectionMatrix = new Matrix4F(modelMatrix);
        modelViewProjectionMatrix.productOnMatrix(viewMatrix);
        modelViewProjectionMatrix.productOnMatrix(projectionMatrix);

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point3f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3F vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                javax.vecmath.Vector3f vertexVecmath = new javax.vecmath.Vector3f(vertex.getX(), vertex.getY(), vertex.getZ());

                Point3f resultPoint = vertexToPoint3f(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).x,
                        resultPoints.get(vertexInPolygonInd - 1).y,
                        resultPoints.get(vertexInPolygonInd).x,
                        resultPoints.get(vertexInPolygonInd).y);

            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).x,
                        resultPoints.get(nVerticesInPolygon - 1).y,
                        resultPoints.get(0).x,
                        resultPoints.get(0).y);


        }

    }

}