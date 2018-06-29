import acm.graphics.GImage;

import java.awt.*;
import java.io.Serializable;

public class GameObject extends GImage implements Serializable {

    public GameObject(Image image) {
        super(image);
    }

    GameObject(String s) {
        super(s);
    }

    public GameObject(int[][] ints) {
        super(ints);
    }

    public GameObject(Image image, double v, double v1) {
        super(image, v, v1);
    }

    public GameObject(String s, double v, double v1) {
        super(s, v, v1);
    }

    public GameObject(int[][] ints, double v, double v1) {
        super(ints, v, v1);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
