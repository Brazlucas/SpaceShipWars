package game.models;

import javax.swing.*;
import java.awt.*;

public class Enemy1 {
  private Image image;
  private int x, y;
  private int width, height;
  private boolean isVisible;
//  private static final int WIDTH = 938;
  private static int SPEED = 8;
  private int enemyLife = 2;

  public Enemy1(int playerX, int playerY) {
    this.x = playerX;
    this.y = playerY;
    isVisible = true;
  }

  public void load() {
    ImageIcon reference = new ImageIcon("src//res//enemy1small.png");
    image = reference.getImage();

    enemyDamaged();

    this.width = image.getWidth(null);
    this.height = image.getHeight(null);
  }

  public void enemyDamaged() {
    if (enemyLife == 1) {
      ImageIcon reference = new ImageIcon("src//res//enemy1damagesmall.png");
      image = reference.getImage();
    }
    if (enemyLife < 1) {
      setVisible(false);
    }
  }

  public void update() {
    this.x -= SPEED;
//    if (this.x > WIDTH) {
//      isVisible = false;
//    }
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

  public void setVisible(boolean visible) {
    isVisible = visible;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public int getenemyLife() {
    return enemyLife;
  }

  public void setenemyLife(int enemyLife) {
    this.enemyLife = enemyLife;
  }

  public static int getSPEED() {
    return SPEED;
  }

  public Image getImage() {
    return image;
  }
}
