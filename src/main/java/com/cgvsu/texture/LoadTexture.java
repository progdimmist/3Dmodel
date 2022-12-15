package com.cgvsu.texture;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class LoadTexture {
    public static ByteBuffer loadTexture(String path) {
        BufferedImage image = null;
        try {
            InputStream in = new FileInputStream(path);
            image = ImageIO.read(in);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load a texture file!"
                    + System.lineSeparator() + ex.getMessage());
        }
        if (image != null) {
            AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
            transform.translate(0, -image.getHeight());
            AffineTransformOp operation = new AffineTransformOp(transform,
                    AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            image = operation.filter(image, null);
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
            ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = pixels[y * width + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) (pixel & 0xFF));
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
            buffer.flip();
            return buffer;
        } else {
            throw new RuntimeException("File extension not supported!"
                    + System.lineSeparator() + "The following file extensions "
                    + "are supported: "
                    + Arrays.toString(ImageIO.getReaderFileSuffixes()));
        }
    }
}
