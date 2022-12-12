package com.cgvsu.rasterization;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Rasterization {

    public static void fillTriangle(
            final GraphicsUtils gr,
            MyPoint3D p1, MyPoint3D p2, MyPoint3D p3,
            MyColor myColor1, MyColor myColor2, MyColor myColor3,
            Double[][] zBuffer) {

        List<MyPoint3D> points = new ArrayList<>(Arrays.asList(p1, p2, p3));

        points.sort(Comparator.comparingDouble(MyPoint3D::getY));

        final double x1 = points.get(0).getX();
        double x2 = points.get(1).getX();
        double x3 = points.get(2).getX();
        double y1 = points.get(0).getY();
        double y2 = points.get(1).getY();
        double y3 = points.get(2).getY();
        double z1 = points.get(0).getZ();
        double z2 = points.get(1).getZ();
        double z3 = points.get(2).getZ();

        for (int y = (int) (y1 + 1); y <= y2; y++) {
            double startX = getX(y, x1, x2, y1, y2);
            double endX = getX(y, x1, x3, y1, y3);
            fillLine(gr, y, startX, endX, myColor1, myColor2, myColor3, x1, x2, x3, y1, y2, y3, z1, z2, z3, zBuffer);
        }

        for (int y = (int) (y2 + 1); y < y3; y++) {
            double startX = getX(y, x1, x3, y1, y3);
            double endX = getX(y, x2, x3, y2, y3);
            fillLine(gr, y, startX, endX, myColor1, myColor2, myColor3, x1, x2, x3, y1, y2, y3, z1, z2, z3, zBuffer);
        }
    }

    public static void fillTriangle(
            GraphicsUtils gr,
            double x1, double y1, double z1,
            double x2, double y2, double z2,
            double x3, double y3, double z3,
            MyColor myColor1, MyColor myColor2, MyColor myColor3,
            Double[][] zBuffer) {
        fillTriangle(gr, new MyPoint3D(x1, y1, z1), new MyPoint3D(x2, y2, z2), new MyPoint3D(x3, y3, z3),
                myColor1, myColor2, myColor3, zBuffer);
    }

    private static double getX(double y, double x1, double x2, double y1, double y2) {
        return (x2 - x1) * (y - y1) / (y2 - y1) + x1;
    }

    private static void fillLine(
            final GraphicsUtils gr, int y, double startX, double endX,
            MyColor myColor1, MyColor myColor2, MyColor myColor3,
            double x1, double x2, double x3,
            double y1, double y2, double y3,
            double z1, double z2, double z3,
            Double[][] zBuffer) {

        if (Double.compare(startX, endX) > 0) {
            double temp = startX;
            startX = endX;
            endX = temp;
        }

        for (int x = (int) startX + 1; x < endX; x++) {
            double z = Normals.getZ(new MyPoint3D(x1, y1, z1), new MyPoint3D(x2, y2, z2), new MyPoint3D(x3, y3, z3), x, y);
            if (x >= 0 && y >= 0) {
                if (zBuffer[x][y] == null || zBuffer[x][y] < z) {
                    gr.setPixel(x, y, getColor(myColor1, myColor2, myColor3, x, y, x1, x2, x3, y1, y2, y3));
                    zBuffer[x][y] = z;
                }
            }
        }
    }


    private static MyColor getColor(
            MyColor myColor1, MyColor myColor2, MyColor myColor3,
            double x, double y,
            double x1, double x2, double x3,
            double y1, double y2, double y3) {

        double detT = (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);

        double alpha = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / detT;

        double betta = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / detT;

        double gamma = 1 - alpha - betta;

        double r = (alpha * myColor1.getRed() + betta * myColor2.getRed() + gamma * myColor3.getRed());
        double g = (alpha * myColor1.getGreen() + betta * myColor2.getGreen() + gamma * myColor3.getGreen());
        double b = (alpha * myColor1.getBlue() + betta * myColor2.getBlue() + gamma * myColor3.getBlue());

        return new MyColor(r, g, b);
    }

}
