package API.Components;

import API.Utility.Vector;
import API.Utility.Rotator;

import javax.swing.*;
import java.awt.*;

public class Sprite {
    private Vector location;
    private Rotator rotator;
    private int width;
    private int height;
    private Image image;

    public Sprite(String imageFile, Vector location) {
        this.location = location;

        loadImage(imageFile);
    }

    public Sprite(String imageFile) {
        this(imageFile, new Vector());
    }

    private void loadImage(String file) {

        ImageIcon ii = new ImageIcon("Game/Assets/" + file);
        image = ii.getImage();

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public Vector getLocation() {
        return location;
    }

    public int getWidth() {

        return width;
    }

    public int getHeight() {

        return height;
    }

    public Image getImage() {

        return image;
    }
}
