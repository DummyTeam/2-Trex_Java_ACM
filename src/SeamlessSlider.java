import java.util.ArrayList;

public class SeamlessSlider {

    private ArrayList<GameObject> images;
    private int sliderSpeed = 1;
    private Game game;

    SeamlessSlider(Game game, GameObject temp) {
        int numberOfCont = (int) (DataProvider.getWindowWidth() / temp.getWidth());
        numberOfCont += 2;

        this.game = game;

        images = new ArrayList<>();

        initSlides(numberOfCont, temp);
    }

    public void setSliderSpeed(int sliderSpeed) {
        this.sliderSpeed = sliderSpeed;
    }

    private void initSlides(int numOfItems, GameObject temp) {
        try {
            for (int i = 0; i < numOfItems; i++) {
                if (temp != null) {
                    GameObject newElem = (GameObject) temp.clone();
                    images.add(newElem);
                    game.add(newElem);
                }
            }
        } catch (Exception e) {
            System.out.println("Error happened " + e.getMessage());
        }
    }

    public void removeSlides() {
        for (GameObject image : images) {
            game.remove(image);
        }
        images.clear();
    }

    public void buildSliderAnim() {
        if (images.size() > 0)
            images.get(0).setLocation(images.get(0).getX() - sliderSpeed, images.get(0).getY());

        for (int i = 0; i < images.size(); i++) {
            if (i == 0 && images.get(0).getX() + images.get(0).getWidth() < 0) {
                images.get(0).setLocation(0, images.get(0).getY());
            } else if (i != 0) {
                double xLoc = images.get(i - 1).getX() + images.get(i - 1).getWidth();
                double yLoc = images.get(i).getY();
                images.get(i).setLocation(xLoc, yLoc);
            }
        }
    }
}
