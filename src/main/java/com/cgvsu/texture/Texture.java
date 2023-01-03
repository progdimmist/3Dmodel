package com.cgvsu.texture;


import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.rasterization.MyColor;

import javax.imageio.ImageIO;
import java.awt.Paint;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Texture {

    public static MyColor getColor(double x0, double y0, BufferedImage image) throws IOException {


        int width = image.getWidth()-1;
        int height = image.getHeight()-1;
        int x = (int) (x0 * width);
        int y = (int) (y0 * height);

        // Getting pixel color by position x and y
        int clr = image.getRGB(x, y);
        double red = ((clr & 0x00ff0000) >> 16) / 255.0f;
        double green = ((clr & 0x0000ff00) >> 8) / 255.0f;
        double blue = (clr & 0x000000ff) / 255.0f;
        return new MyColor(red, green, blue);
    }
}
