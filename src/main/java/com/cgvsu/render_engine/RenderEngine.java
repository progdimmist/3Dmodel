package com.cgvsu.render_engine;

import java.awt.Color;
import java.util.ArrayList;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Polygon;
import com.cgvsu.rasterization.DrawUtilsJavaFX;
import com.cgvsu.rasterization.GraphicsUtils;
import com.cgvsu.rasterization.MyColor;
import com.cgvsu.rasterization.Rasterization;
import com.cgvsu.triangle.Triangle;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
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

        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                javax.vecmath.Vector3f vertexVecmath = new javax.vecmath.Vector3f(vertex.x, vertex.y, vertex.z);

                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
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

        ArrayList<Polygon> triangles = Triangle.triangulatePolygon(mesh.polygons);
        mesh.setPolygons(triangles);

        for (int i = 0; i < nPolygons; i++) {
            final int nVerticesInPolygon = mesh.polygons.get(i).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(i).getVertexIndices().get(vertexInPolygonInd));

                javax.vecmath.Vector3f vertexVecmath = new javax.vecmath.Vector3f(vertex.x, vertex.y, vertex.z);

                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }


            Rasterization.fillTriangle(graphicsUtils,
                    new Point2D(resultPoints.get(0).x, resultPoints.get(0).y),
                    new Point2D(resultPoints.get(1).x, resultPoints.get(1).y),
                    new Point2D(resultPoints.get(2).x, resultPoints.get(2).y),
                    MyColor.RED, MyColor.BLUE, MyColor.GREEN);





/*
            if (nVerticesInPolygon > 0)
                Rasterization.fillTriangle(graphicsUtils,
                        new Vector3f(resultPoints.get(nVerticesInPolygon - 2).x,resultPoints.get(nVerticesInPolygon - 2).y,0),
                        new Vector3f(resultPoints.get(nVerticesInPolygon - 1).x,resultPoints.get(nVerticesInPolygon - 1).y,0),
                        new Vector3f(resultPoints.get(nVerticesInPolygon).x,resultPoints.get(nVerticesInPolygon).y,0),
                        MyColor.BLUE,MyColor.RED,MyColor.GREEN);




            Rasterization.fillTriangle(graphicsUtils, mesh.vertices.get(mesh.polygons.get(i).getVertexIndices().get(0)),
                   mesh.vertices.get(mesh.polygons.get(i).getVertexIndices().get(1)),
                    mesh.vertices.get(mesh.polygons.get(i).getVertexIndices().get(2)), MyColor.BLUE,MyColor.RED,MyColor.GREEN);
        */
        }


    }
}