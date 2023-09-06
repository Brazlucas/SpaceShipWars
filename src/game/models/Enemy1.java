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
  private boolean enemyHasDamaged = false;

  public Enemy1(int playerX, int playerY) {
    this.x = playerX;
    this.y = playerY;
    isVisible = true;
  }

  public void load() {
    ImageIcon reference = new ImageIcon("src//res//enemy1.png");
    image = reference.getImage();

    enemyDamaged();

    this.width = image.getWidth(null);
    this.height = image.getHeight(null);
  }

  public void enemyDamaged() {
    if (enemyHasDamaged) {
      ImageIcon reference = new ImageIcon("src//res//enemy1damage.png");
      image = reference.getImage();
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

  public boolean isEnemyHasDamaged() {
    return enemyHasDamaged;
  }

  public void setEnemyHasDamaged(boolean enemyHasDamaged) {
    this.enemyHasDamaged = enemyHasDamaged;
  }

  public static int getSPEED() {
    return SPEED;
  }

  public Image getImage() {
    return image;
  }
}
