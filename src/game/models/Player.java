package game.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player {
  private List <Shoot> shoots;
  private int x, y;
  private int dx, dy;
  private Image background;
  private int height, width;

  public Player() {
    this.x = 100;
    this.y = 100;

    shoots = new ArrayList<Shoot>();
  }

  public void load() {
    ImageIcon reference = new ImageIcon("src//res//spaceship.png");
    background = reference.getImage();
    height = background.getHeight(null);
    width = background.getWidth(null);
  }

  public void update() {
    x += dx;
    y += dy;
  }

  public void simpleShoot() {
    this.shoots.add(new Shoot(x + (width / 2), y + (height / y)));
  }

  public void keyPressed(KeyEvent key) {
    int code = key.getKeyCode();

    if (code == KeyEvent.VK_SPACE) {
      simpleShoot();
    }
    if (code == KeyEvent.VK_UP) {
      dy=-3;
    }
    if (code == KeyEvent.VK_DOWN) {
      dy=3;
    }
    if (code == KeyEvent.VK_LEFT) {
      dx=-3;
    }
    if (code == KeyEvent.VK_RIGHT) {
      dx=3;
    }
  }

  public void keyRelease(KeyEvent key) {
    int code = key.getKeyCode();

    if (code == KeyEvent.VK_UP) {
      dy=0;
    }
    if (code == KeyEvent.VK_DOWN) {
      dy=0;
    }
    if (code == KeyEvent.VK_LEFT) {
      dx=0;
    }
    if (code == KeyEvent.VK_RIGHT) {
      dx=0;
    }
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Image getBackground() {
    return background;
  }

  public List<Shoot> getShoots() {
    return shoots;
  }
}
