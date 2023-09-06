package game.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player implements ActionListener {
  private List <Shoot> shoots;
  private int x, y;
  private int dx, dy;
  private Image background;
  private int height, width;
  private boolean isVisible, isTurbo;
  private Timer timer;
  private SoundPlayer shootSound;
  private int playerLife;

  public Player() {
    this.x = 100;
    this.y = 100;
    isVisible = true;
    isTurbo = false;
    playerLife = 3;

    shoots = new ArrayList<Shoot>();

    timer = new Timer(5000, this);
    timer.start();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (isTurbo == true) {
      turbo();
      isTurbo = false;
    }
    if (!isTurbo) {
      load();
    }
  }

  public void load() {
    ImageIcon reference = new ImageIcon("src//res//spaceship5small.png");
    playerDamaged();
    shootSound = new SoundPlayer("src//res//blaster.wav");
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

  public void playerDamaged() {
    if (playerLife == 1) {
      ImageIcon reference = new ImageIcon("src//res//enemy1damagesmall.png");
      background = reference.getImage();
    }
    if (playerLife < 1) {
      setVisible(false);
    }
  }

  public void turbo() {
    isTurbo = true;
    ImageIcon reference = new ImageIcon("src//res//spaceship5turbo.png");
    background = reference.getImage();
  }

  public boolean isTurbo() {
    return isTurbo;
  }

  public Rectangle getBounds() {
    return new Rectangle(x, y, width, height);
  }

  public void keyPressed(KeyEvent key) {
    int code = key.getKeyCode();

    if (code == KeyEvent.VK_X) {
      if (!isTurbo) {
        turbo();
      }
    }

    if (code == KeyEvent.VK_SPACE) {
      if (!isTurbo) {
        shootSound.play();
        simpleShoot();
      }
    }

    if (code == KeyEvent.VK_UP) {
      dy=-8;
    }

    if (code == KeyEvent.VK_DOWN) {
      dy=8;
    }

    if (code == KeyEvent.VK_LEFT) {
      dx=-8;
    }

    if (code == KeyEvent.VK_RIGHT) {
      dx=8;
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

  public int getPlayerLife() {
    return playerLife;
  }

  public void setPlayerLife(int playerLife) {
    this.playerLife = playerLife;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public void setVisible(boolean visible) {
    isVisible = visible;
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
