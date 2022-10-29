package com.cgvsu.objwriter;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReaderException;

import java.util.ArrayList;

public class ObjWriter {
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";
    private static final String NULL_CONST = "0.0000";

    public static ArrayList<String> write(Model mesh) {
        ArrayList<String> listFileContent = new ArrayList<>();

        writeVertices(mesh, listFileContent);
        listFileContent.add("# " + mesh.vertices.size() + " vertices");

        writeTextureVertices(mesh, listFileContent);
        listFileContent.add("# " + mesh.textureVertices.size() + " texture coordinates");

        writeNormals(mesh, listFileContent);
        listFileContent.add("# " + mesh.normals.size() + " vertices");

        writePolygons(mesh, listFileContent);
        listFileContent.add("# " + mesh.polygons.size() + " polygons");
        return listFileContent;
    }

    public static void writeVertices(Model mesh, ArrayList<String> listFileContent) {
        for (int i = 0; i < mesh.vertices.size(); i++) {
            listFileContent.add(OBJ_VERTEX_TOKEN + " " + mesh.vertices.get(i).getX()
                    + " " + mesh.vertices.get(i).getY() + " " + mesh.vertices.get(i).getZ());
        }
    }

    public static void writeTextureVertices(Model mesh, ArrayList<String> listFileContent) {
        for (int i = 0; i < mesh.textureVertices.size(); i++) {
            listFileContent.add(OBJ_TEXTURE_TOKEN + " " + mesh.textureVertices.get(i).getX()
                    + " " + mesh.textureVertices.get(i).getY() + " " + NULL_CONST);
        }
    }

    public static void writeNormals(Model mesh, ArrayList<String> listFileContent) {
        for (int i = 0; i < mesh.normals.size(); i++) {
            listFileContent.add(OBJ_NORMAL_TOKEN + " " + mesh.normals.get(i).getX()
                    + " " + mesh.normals.get(i).getY() + " " + mesh.normals.get(i).getZ());
        }
    }

    public static void writePolygons(Model mesh, ArrayList<String> listFileContent) {
        for (int i = 0; i < mesh.polygons.size(); i++) {

            listFileContent.add(String.valueOf(getOneStringPolygons(mesh, i)));
        }
    }

    public static StringBuilder getOneStringPolygons(Model mesh, int i) {

        StringBuilder strOnePolygons = new StringBuilder();
        strOnePolygons.append(OBJ_FACE_TOKEN + " ");

        try {
            if (mesh.polygons.get(i).getSizePolygonsVertexIndices() <= 2)
                throw new IndexOutOfBoundsException("Less than 3 polygons");
            if (mesh.polygons.get(i).getSizePolygonsTextureVertexIndices() == 0 &&
                    mesh.polygons.get(i).getSizePolygonsNormalIndices() == 0) {
                for (int j = 0; j < mesh.polygons.get(i).getSizePolygonsVertexIndices(); j++) {
                    strOnePolygons.append(mesh.polygons.get(i).getVertexIndices().get(j) + 1).append(" ");
                }
            } else if (mesh.polygons.get(i).getSizePolygonsTextureVertexIndices() != 0 &&
                    mesh.polygons.get(i).getSizePolygonsNormalIndices() == 0) {
                for (int j = 0; j < mesh.polygons.get(i).getSizePolygonsTextureVertexIndices(); j++) {
                    strOnePolygons.append(mesh.polygons.get(i).getVertexIndices().get(j) + 1).append("/").
                            append(mesh.polygons.get(i).getTextureVertexIndices().get(j) + 1).append(" ");
                }
            } else {
                for (int j = 0; j < mesh.polygons.get(i).getSizePolygonsTextureVertexIndices(); j++) {
                    strOnePolygons.append(mesh.polygons.get(i).getVertexIndices().get(j) + 1).append("/").append(
                            mesh.polygons.get(i).getTextureVertexIndices().get(j) + 1).append("/").append(
                            mesh.polygons.get(i).getNormalIndices().get(j) + 1).append(" ");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new ObjWriterException(e.getMessage());
        }
        return strOnePolygons;

    }
}
