package com.cgvsu.rasterization;

import com.cgvsu.math.Vector3f;
import javafx.geometry.Point2D;


import javax.vecmath.Point2d;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Rasterization {

    //todo: сделать @param Point2D общим
    public static void fillTriangle(
            final GraphicsUtils gr,
            Point2D p1, Point2D p2, Point2D p3,
            MyColor myColor1, MyColor myColor2, MyColor myColor3) {


        List<Point2D> points = new ArrayList<>(Arrays.asList(p1, p2, p3));

        points.sort(Comparator.comparingDouble(Point2D::getY));

        final double x1 = points.get(0).getX();
        double x2 = points.get(1).getX();
        double x3 = points.get(2).getX();
        double y1 = points.get(0).getY();
        double y2 = points.get(1).getY();
        double y3 = points.get(2).getY();

        for (int y = (int) (y1 + 1); y <= y2; y++) {
            double startX = getX(y, x1, x2, y1, y2);
            double endX = getX(y, x1, x3, y1, y3);

            fillLine(gr, y, startX, endX, myColor1, myColor2, myColor3, x1, x2, x3, y1, y2, y3);
        }

        for (int y = (int) (y2 + 1); y < y3; y++) {
            double startX = getX(y, x1, x3, y1, y3);
            double endX = getX(y, x2, x3, y2, y3);
            fillLine(gr, y, startX, endX, myColor1, myColor2, myColor3, x1, x2, x3, y1, y2, y3);
        }

    }

    static void fillTriangle(
            GraphicsUtils gr,
            double x1, double y1,
            double x2, double y2,
            double x3, double y3,
            MyColor myColor1, MyColor myColor2, MyColor myColor3) {
        fillTriangle(gr, new Point2D(Math.round(x1), Math.round(y1)), new Point2D(Math.round(x2), Math.round(y2)), new Point2D(Math.round(x3), Math.round(y3)), myColor1, myColor2, myColor3);
    }

    private static double getX(double y, double x1, double x2, double y1, double y2) {
        return Math.round((x2 - x1) * (y - y1) / (y2 - y1)) + x1;
    }

    private static void fillLine(
            final GraphicsUtils gr, int y, double startX, double endX,
            MyColor myColor1, MyColor myColor2, MyColor myColor3,
            double x1, double x2, double x3,
            double y1, double y2, double y3) {

        if (Double.compare(startX, endX) > 0) {
            double temp = startX;
            startX = endX;
            endX = temp;
        }

        for (int x = (int) startX + 1; x < endX; x++) {
            gr.setPixel(x, y, getColor(myColor1, myColor2, myColor3, x, y, x1, x2, x3, y1, y2, y3));
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
