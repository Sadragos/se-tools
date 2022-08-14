package eu.fehlemann;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OverlayEditor {
    public enum Position {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        CENTER
    }

    public static class Overlay {
        int height;
        int width;
        Position position;
        File file;

        public Overlay(int height, int width, Position position, File file) {
            this.height = height;
            this.width = width;
            this.position = position;
            this.file = file;
        }
    }

    public static void addOverlays(ArrayList<File> images, ArrayList<File> overlays, Position position, int width, int height) throws IOException {
        ArrayList<Overlay> result = new ArrayList<>();
        for(File over : overlays) {
            result.add(new Overlay(height, width, position, over));
        }
        addOverlays(images, result);
    }

    public static void addOverlays(ArrayList<File> images, ArrayList<Overlay> overlays) throws IOException {
        for(File image : images) {
            for(Overlay over : overlays) {
                addOverlays(image, over);
            }
        }
    }

    public static void addOverlays(File image, ArrayList<Overlay> overlays) throws IOException {
        for(Overlay over : overlays) {
            addOverlays(image, over);
        }
    }

    public static void addOverlays(ArrayList<File> images, Overlay overlay) throws IOException {
        for(File image : images) {
            addOverlays(image, overlay);
        }
    }

    public static void addOverlays(File image, Overlay overlay) throws IOException {
        if(!image.isFile()) {
            System.out.println("Skipping " + image.getName() + " - not an image");
            return;
        }
        File target = new File(image.getParent(), image.getName().substring(0, image.getName().indexOf(".")) + overlay.file.getName().substring(0, overlay.file.getName().indexOf(".")) + ".png");

        BufferedImage overlayImage = resize(ImageIO.read(overlay.file), overlay.width, overlay.height);
        BufferedImage sourceImage = ImageIO.read(image);
        BufferedImage combined = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = combined.getGraphics();
        g.drawImage(sourceImage, 0, 0, null);
        switch (overlay.position) {
            case CENTER:
                g.drawImage(overlayImage, (sourceImage.getWidth() / 2) - (overlay.width / 2), (sourceImage.getHeight() / 2) - (overlay.height / 2), null);
                break;
            case TOP_LEFT:
                g.drawImage(overlayImage, 0, 0, null);
                break;
            case TOP_RIGHT:
                g.drawImage(overlayImage, sourceImage.getWidth() - overlay.width, 0, null);
                break;
            case BOTTOM_LEFT:
                g.drawImage(overlayImage, 0, sourceImage.getHeight() - overlay.height, null);
                break;
            case BOTTOM_RIGHT:
                g.drawImage(overlayImage, sourceImage.getWidth() - overlay.width, sourceImage.getHeight() - overlay.height, null);
                break;
        }

        ImageIO.write(combined, "PNG", target);
        g.dispose();

        System.out.println("Added " + overlay.file.getName() + " to " + image.getName() + " => " + target.getName());
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
