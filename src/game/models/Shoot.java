package game.models;

import javax.swing.*;
import java.awt.*;

public class Shoot {
  private Image image;
  private int x, y;
  private int width, height;
  private boolean isVisible;
  private static final int WIDTH = 938;
  private static int SPEED = 12;

  public Shoot(int playerX, int playerY) {
    this.x = playerX + 5;
    this.y = playerY + 15;
    isVisible = true;
  }

  public void load() {
    ImageIcon reference = new ImageIcon("src//res//yellowshot.png");
    image = reference.getImage();

    this.width = image.getWidth(null);
    this.height = image.getHeight(null);
  }

  public static void setSPEED(int SPEED) {
    Shoot.SPEED = SPEED;
  }

  public void update() {
    this.x += SPEED;
    if (this.x > WIDTH) {
      isVisible = false;
    }
  }

  public Rectangle getBounds() {
    return new Rectangle(x, y, width, height);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public void setVisible(boolean visible) {
    isVisible = visible;
  }

  public static int getSPEED() {
    return SPEED;
  }

  public Image getImage() {
    return image;
  }
}
