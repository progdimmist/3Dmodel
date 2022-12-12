package com.cgvsu.model;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import java.util.*;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
    public ArrayList<Polygon> trianglePolygons = new ArrayList<Polygon>();

    public void setPolygons(ArrayList<Polygon> polygons){
        this.polygons=polygons;
    }
    public void setTrianglePolygons(ArrayList<Polygon> polygons){
        this.trianglePolygons=polygons;
    }
}
