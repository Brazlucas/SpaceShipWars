package game.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class Stage extends JPanel implements ActionListener {
  private Image background;
  private Player player;
  private Timer timer;
  private int screenWidth, screenHeight;
  private List<Enemy1> enemy1;
  private List<Stars> stars;
  private boolean inGame;

  public Stage() {
    setFocusable(true);
    setDoubleBuffered(true);
    ImageIcon reference = new ImageIcon("src//res//blackground.png");
    background = reference.getImage();

    player = new Player();
    player.load();

    addKeyListener(new KAdapter());

    timer = new Timer(2, this);
    timer.start();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    screenWidth = screenSize.width;
    screenHeight = screenSize.height;

    addEnemies();
    addStars();

    inGame = true;
  }

  public void addEnemies() {
    int coords [] = new int [0];
    enemy1 = new ArrayList<Enemy1>();

    for (int i = 0; i < coords.length; i++) {
      int x = (int)(Math.random() * 8000+1024);
      int y = (int)(Math.random() * 650+30);
      enemy1.add(new Enemy1(x, y));
    }
  }

  public void addStars() {
    int coords [] = new int [200];
    stars = new ArrayList<Stars>();

    for (int i = 0; i < coords.length; i++) {
      int x = (int)(Math.random() * 1024+0);
      int y = (int)(Math.random() * 768+0);
      stars.add(new Stars(x, y));
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics = (Graphics2D) g;

    if (inGame) {
      graphics.drawImage(background, 0, 0, screenWidth, screenHeight, this);

      for (int i = 0; i < stars.size(); i++) {
        Stars index = stars.get(i);
        index.load();
        graphics.drawImage(index.getImage(), index.getX(), index.getY(), this);
      }

      graphics.drawImage(player.getBackground(), player.getX(), player.getY(), this);

      List<Shoot> shoots = player.getShoots();
      for(int i = 0; i < shoots.size(); i++) {
        Shoot m = shoots.get(i);
        m.load();
        graphics.drawImage(m.getImage(), m.getX(), m.getY(), this);
      }

      for (int enemyIndex = 0; enemyIndex < enemy1.size(); enemyIndex++) {
        Enemy1 in = enemy1.get(enemyIndex);
        in.load();
        graphics.drawImage(in.getImage(), in.getX(), in.getY(), this);
      }
    } else {
      ImageIcon gameOver = new ImageIcon("src//res//gameover.png");
      graphics.drawImage(gameOver.getImage(), 1024, 768, this);
    }

    g.dispose();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    player.update();

    for (int i = 0; i < stars.size(); i++) {
      Stars on = stars.get(i);
      if (on.isVisible()) {
        on.update();
      } else {
        stars.remove(i);
      }
    }

    List<Shoot> shoots = player.getShoots();
    for(int shootIndex = 0; shootIndex < shoots.size(); shootIndex++) {
      Shoot m = shoots.get(shootIndex);
      if(m.isVisible()) {
        m.update();
      } else {
        shoots.remove(shootIndex);
      }
    }

    for (int enemyIndex = 0; enemyIndex < enemy1.size(); enemyIndex++) {
      Enemy1 in = enemy1.get(enemyIndex);
      if (in.isVisible()) {
        in.update();
      } else {
        enemy1.remove(enemyIndex);
      }
    }

    checkCollisions();

    repaint();
  }

  public void checkCollisions() {
    Rectangle shipShape = player.getBounds();
    Rectangle enemyShape1;
    Rectangle shootShape;

    for (int enemyIndex = 0; enemyIndex < enemy1.size(); enemyIndex++) {
      Enemy1 tempEnemy1 = enemy1.get(enemyIndex);

      enemyShape1 = tempEnemy1.getBounds();

      if (shipShape.intersects(enemyShape1)) {
        player.setVisible(false);
        tempEnemy1.setVisible(false);
        inGame = false;
      }
    }

    List<Shoot> shoots = player.getShoots();
    for (int shootIndex = 0; shootIndex < shoots.size(); shootIndex++) {
      Shoot tempShoot = shoots.get(shootIndex);

      shootShape = tempShoot.getBounds();

      for (int i = 0; i < enemy1.size(); i++) {
        Enemy1 tempEnemy1 = enemy1.get(i);
        enemyShape1 = tempEnemy1.getBounds();
        if (shootShape.intersects(enemyShape1)) {
          tempEnemy1.setVisible(false);
          tempShoot.setVisible(false);
        }
      }
    }
  }

  private class KAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        Stars.SPEED = 12;
      }
      player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
      player.keyRelease(e);
      Stars.SPEED = 10;
    }
  }
}
