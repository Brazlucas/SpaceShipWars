package game.models;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Stars {
  private Image image;
  private int x, y;
  private int width, height;
  private boolean isVisible;
  //  private static final int WIDTH = 938;
  protected static int SPEED = 10;

  public Stars (int x, int y) {
    this.x = x;
    this.y = x;
    isVisible = true;
  }

  public void load() {
    ImageIcon reference = new ImageIcon("src//res//star.png");
    image = reference.getImage();

    this.width = image.getWidth(null);
    this.height = image.getHeight(null);
  }

  public void update() {
    if (this.x < 0) {

      this.x = width;
      Random starRandom1 = new Random();
      int starWidth = starRandom1.nextInt(500);
      this.x = starWidth + 1024;

      Random starRandom2 = new Random();
      int starHeight = starRandom2.nextInt(768);
      this.y = starHeight;
    } else {
      this.x -= SPEED;
    }
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setVisible(boolean visible) {
    isVisible = visible;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public static void setSPEED(int SPEED) {
    Stars.SPEED = SPEED;
  }

  public static int getSPEED() {
    return SPEED;
  }

  public Image getImage() {
    return image;
  }
}
