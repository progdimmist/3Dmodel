package com.cgvsu.model;

import com.cgvsu.math.vector.Vector2F;
import com.cgvsu.math.vector.Vector3F;
import com.cgvsu.objreader.ReaderExceptions;

import java.lang.reflect.Array;
import java.util.*;

public class Model {

    public ArrayList<Vector3F> vertices = new ArrayList<Vector3F>();
    public ArrayList<Vector2F> textureVertices = new ArrayList<Vector2F>();
    public ArrayList<Vector3F> normals = new ArrayList<Vector3F>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
    public ArrayList<Polygon> trianglePolygons = new ArrayList<Polygon>();

    public Model(final ArrayList<Vector3F> vertices, final ArrayList<Vector2F> textureVertices,
                 final ArrayList<Vector3F> normals, final ArrayList<Polygon> polygons,
                 final ArrayList<Polygon> trianglePolygons) {
        this.vertices = vertices;
        this.textureVertices = textureVertices;
        this.normals = normals;
        this.polygons = polygons;
        this.trianglePolygons=trianglePolygons;
    }

    public Model() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        normals = new ArrayList<>();
        polygons = new ArrayList<>();
        trianglePolygons=new ArrayList<>();
    }

    public void setPolygons(ArrayList<Polygon> polygons){
        this.polygons=polygons;
    }
    public void setTrianglePolygons(ArrayList<Polygon> polygons){
        this.trianglePolygons=polygons;
    }
    public List<Vector3F> getVertices() {
        return vertices;
    }

    public List<Vector2F> getTextureVertices() {
        return textureVertices;
    }

    public List<Vector3F> getNormals() {
        return normals;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setVertices(final ArrayList<Vector3F> vertices) {
        this.vertices = vertices;
    }

    public void setTextureVertices(final ArrayList<Vector2F> vertices) {
        this.textureVertices = vertices;
    }

    public void setNormals(final ArrayList<Vector3F> vertices) {
        this.normals = vertices;
    }


    public boolean checkConsistency() {
        for (int i = 0; i < polygons.size(); i++) {
            List<Integer> vertexIndices = polygons.get(i).getVertexIndices();
            List<Integer> textureVertexIndices = polygons.get(i).getTextureVertexIndices();
            List<Integer> normalIndices = polygons.get(i).getNormalIndices();
            if (vertexIndices.size() != textureVertexIndices.size()
                    && vertexIndices.size() != 0 && textureVertexIndices.size() != 0) {
                throw new ReaderExceptions.NotDefinedUniformFormatException(
                        "The unified format for specifying polygon descriptions is not defined.");
            }
            if (vertexIndices.size() != normalIndices.size()
                    && vertexIndices.size() != 0 &&  normalIndices.size() != 0) {
                throw new ReaderExceptions.NotDefinedUniformFormatException(
                        "The unified format for specifying polygon descriptions is not defined.");
            }
            if (normalIndices.size() != textureVertexIndices.size()
                    && normalIndices.size() != 0 && textureVertexIndices.size() != 0) {
                throw new ReaderExceptions.NotDefinedUniformFormatException(
                        "The unified format for specifying polygon descriptions is not defined.");
            }
            for (int j = 0; j < vertexIndices.size(); j++) {
                if (vertexIndices.get(j) >= vertices.size()) {
                    throw new ReaderExceptions.FaceException(
                            "Polygon description is wrong.", i + 1);
                }
            }
            for (int j = 0; j < textureVertexIndices.size(); j++) {
                if (textureVertexIndices.get(j) >= textureVertices.size()) {
                    throw new ReaderExceptions.FaceException(
                            "Polygon description is wrong.", i + 1);
                }
            }
            for (int j = 0; j < normalIndices.size(); j++) {
                if (normalIndices.get(j) >= normals.size()) {
                    throw new ReaderExceptions.FaceException(
                            "Polygon description is wrong.", i + 1);
                }
            }
        }
        return true;
    }
}
