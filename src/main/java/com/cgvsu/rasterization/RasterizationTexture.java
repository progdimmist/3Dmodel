package com.cgvsu.rasterization;

import com.cgvsu.GuiController;
import com.cgvsu.math.Vector2f;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.texture.Texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RasterizationTexture {
    public static void fillTriangle(
            final GraphicsUtils gr,
            MyPoint3D p1, MyPoint3D p2, MyPoint3D p3,
            MyColor myColor1, MyColor myColor2, MyColor myColor3,
            Double[][] zBuffer, Camera camera, BufferedImage image,
            Vector2f texturePoint1, Vector2f texturePoint2, Vector2f texturePoint3) throws IOException {

        List<MyPoint3D> points = new ArrayList<>(Arrays.asList(p1, p2, p3));

        for (int i = 0; i < 3; i++) {
            if (points.get(0).getY() > points.get(1).getY()) {
                MyPoint3D tmp = points.get(1);
                points.set(1, points.get(0));
                points.set(0, tmp);
                Vector2f tmp1 = texturePoint1;
                texturePoint1 = texturePoint2;
                texturePoint2 = tmp1;
            }
            if (points.get(1).getY() > points.get(2).getY()) {
                MyPoint3D tmp = points.get(2);
                points.set(2, points.get(1));
                points.set(1, tmp);
                Vector2f tmp1 = texturePoint2;
                texturePoint2 = texturePoint3;
                texturePoint3 = tmp1;
                if (points.get(0).getY() > points.get(1).getY()) {
                    MyPoint3D tmp2 = points.get(1);
                    points.set(1, points.get(0));
                    points.set(0, tmp2);
                    Vector2f tmp3 = texturePoint1;
                    texturePoint1 = texturePoint2;
                    texturePoint2 = tmp3;
                }
            }
        }
        double cosLight;
        if (GuiController.isLight) cosLight = MathRasterization.getCosLight(camera, p1, p2, p3);
        else cosLight = 1;
        final double x1 = points.get(0).getX();
        final double x2 = points.get(1).getX();
        final double x3 = points.get(2).getX();
        final double y1 = points.get(0).getY();
        final double y2 = points.get(1).getY();
        final double y3 = points.get(2).getY();
        final double z1 = points.get(0).getZ();
        final double z2 = points.get(1).getZ();
        final double z3 = points.get(2).getZ();

        for (int y = (int) (y1 + 1); y <= y2; y++) {
            double startX = getX(y, x1, x2, y1, y2);
            double endX = getX(y, x1, x3, y1, y3);
            fillLine(gr, y, startX, endX, myColor1, myColor2, myColor3, x1, x2, x3, y1, y2, y3, z1, z2, z3,
                    zBuffer, camera, cosLight, image, texturePoint1, texturePoint2, texturePoint3);
        }

        for (int y = (int) (y2 + 1); y < y3; y++) {
            double startX = getX(y, x1, x3, y1, y3);
            double endX = getX(y, x2, x3, y2, y3);
            fillLine(gr, y, startX, endX, myColor1, myColor2, myColor3, x1, x2, x3, y1, y2, y3, z1, z2, z3,
                    zBuffer, camera, cosLight, image, texturePoint1, texturePoint2, texturePoint3);
        }
    }

    public static void fillTriangle(
            GraphicsUtils gr,
            double x1, double y1, double z1,
            double x2, double y2, double z2,
            double x3, double y3, double z3,
            MyColor myColor1, MyColor myColor2, MyColor myColor3,
            Double[][] zBuffer, Camera camera, BufferedImage image,
            Vector2f texturePoint1, Vector2f texturePoint2, Vector2f texturePoint3) throws IOException {
        fillTriangle(gr, new MyPoint3D(x1, y1, z1), new MyPoint3D(x2, y2, z2), new MyPoint3D(x3, y3, z3),
                myColor1, myColor2, myColor3, zBuffer, camera, image, texturePoint1, texturePoint2, texturePoint3);
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
            Double[][] zBuffer, Camera camera, double cosLight, BufferedImage image,
            Vector2f texturePoint1, Vector2f texturePoint2, Vector2f texturePoint3) throws IOException {

        double startXT = getX(y, texturePoint1.getX(), texturePoint2.getX(), texturePoint1.getY(), texturePoint2.getY());
        double endXT = getX(y, texturePoint1.getX(), texturePoint3.getY(), texturePoint1.getY(), texturePoint3.getY());
        if (Double.compare(startX, endX) > 0) {
            double temp = startX;
            startX = endX;
            endX = temp;
            double temp1 = startXT;
            startXT = endXT;
            endXT = temp1;
        }


        for (int x = (int) startX + 1; x < endX; x++) {
            double z = MathRasterization.getZ(new MyPoint3D(x1, y1, z1), new MyPoint3D(x2, y2, z2), new MyPoint3D(x3, y3, z3), x, y);
            if (x >= 0 && y >= 0) {
                if (zBuffer[x][y] == null || zBuffer[x][y] > Math.abs(z - camera.getPosition().z)) {

                    MyColor color=getColor(myColor1, myColor2, myColor3, x, y, x1, x2, x3, y1, y2, y3, cosLight, image,
                            texturePoint1, texturePoint2, texturePoint3);
                    gr.setPixel(x, y,new MyColor(color.getRed()*cosLight,color.getGreen()*cosLight,color.getBlue()*cosLight) );

                    zBuffer[x][y] = Math.abs(z - camera.getPosition().z);
                }
            }
        }
    }


    private static MyColor getColor(
            MyColor myColor1, MyColor myColor2, MyColor myColor3,
            double x, double y,
            double x1, double x2, double x3,
            double y1, double y2, double y3,
            double cosLight, BufferedImage image, Vector2f texturePoint1, Vector2f texturePoint2, Vector2f texturePoint3) throws IOException {

        /*if (x1 >= x2 && x2 >= x3) {
            double xT=(x-x3)/(x1-x3);
            double yT=(y-y1)/(y3-y1);
            double xI=texturePoint3.getX()+(texturePoint1.getX()-texturePoint3.getX())*xT;
            double yI=texturePoint1.getY()+(texturePoint3.getY()-texturePoint1.getY())*yT;
            return Texture.getColor(xI,yI,image);
        } else if (x1 >= x3 && x3 >= x2) {
            double xT=(x-x2)/(x1-x2);
            double yT=(y-y1)/(y3-y1);
            double xI=texturePoint2.getX()+(texturePoint1.getX()-texturePoint2.getX())*xT;
            double yI=texturePoint1.getY()+(texturePoint3.getY()-texturePoint1.getY())*yT;
            return Texture.getColor(xI,yI,image);
        } else if (x2 >= x1 && x1 >= x3) {
            double xT=(x-x3)/(x2-x3);
            double yT=(y-y1)/(y3-y1);
            double xI=texturePoint3.getX()+(texturePoint2.getX()-texturePoint3.getX())*xT;
            double yI=texturePoint1.getY()+(texturePoint3.getY()-texturePoint1.getY())*yT;
            return  Texture.getColor(xI,yI,image);
        }else if (x2 >= x3 && x3 >= x1) {
            double xT=(x-x1)/(x2-x1);
            double yT=(y-y1)/(y3-y1);
            double xI=texturePoint1.getX()+(texturePoint2.getX()-texturePoint1.getX())*xT;
            double yI=texturePoint1.getY()+(texturePoint3.getY()-texturePoint1.getY())*yT;
            return Texture.getColor(xI,yI,image);
        }else if (x3 >= x2 && x2 >= x1) {
            double xT=(x-x1)/(x3-x1);
            double yT=(y-y1)/(y3-y1);
            double xI=texturePoint1.getX()+(texturePoint3.getX()-texturePoint1.getX())*xT;
            double yI=texturePoint1.getY()+(texturePoint3.getY()-texturePoint1.getY())*yT;
            return Texture.getColor(xI,yI,image);
        }else {
            double xT=(x-x2)/(x3-x2);
            double yT=(y-y1)/(y3-y1);
            double xI=texturePoint2.getX()+(texturePoint3.getX()-texturePoint2.getX())*xT;
            double yI=texturePoint1.getY()+(texturePoint3.getY()-texturePoint1.getY())*yT;
            return Texture.getColor(xI,yI,image);

        }*/
        double t=((x2-x1)*(y-y1)-(x-x1)*(y2-y1))/((x-x1)*(y3-y2)-(x3-x2)*(y-y1));
        double xM=x2+(x3-x2)*t;
        double yM=y2+(y3-y2)*t;
        double a=(Math.sqrt((xM-x2)*(xM-x2)+(yM-y2)*(yM-y2)))/(Math.sqrt((x3-x2)*(x3-x2)+(y3-y2)*(y3-y2)));
        double b=(Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1)))/(Math.sqrt((xM-x1)*(xM-x1)+(yM-y1)*(yM-y1)));
        double uM=texturePoint2.x+(texturePoint3.x-texturePoint2.x)*a;
        double vM=texturePoint2.y+(texturePoint3.y-texturePoint2.y)*b;
        double uD=texturePoint1.x+(uM-texturePoint1.x)*a;
        double vD=texturePoint1.y+(vM-texturePoint1.y)*b;
        return Texture.getColor(uD,vD,image);

    }
}
