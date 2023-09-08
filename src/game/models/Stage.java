package game.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
  private SoundPlayer stageMusic;
  private SoundPlayer gameOverSound;
  private SoundPlayer explosion;
  private int totalDeaths;

  public Stage() {
    setFocusable(true);
    setDoubleBuffered(true);
    ImageIcon reference = new ImageIcon("src//res//blackground.png");
    background = reference.getImage();

//    stageMusic = new SoundPlayer("src//res//lisbonacid.wav");
//    stageMusic.play();

    gameOverSound = new SoundPlayer("src//res//gameover.wav");

    explosion = new SoundPlayer("src//res//explosion.wav");

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
    int coords [] = new int [40];
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

  public void debugger(Graphics g) {
    // DEBUG do inimigo
    for (Enemy1 enemy : enemy1) {
      if (enemy.isVisible()) {
        g.setColor(Color.RED);
        Rectangle bounds = enemy.getBounds();
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
      }
    }

    // DEBUG do tiro
    List<Shoot> shootDebug = player.getShoots();
    for (int i = 0; i < shootDebug.size(); i++) {
      Shoot m = shootDebug.get(i);
      if (m.isVisible()) {
        g.setColor(Color.BLUE); // Cor do retângulo dos tiros
        Rectangle bounds = m.getBounds();
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
      }
    }

    // DEBUG do player
    if (player.isVisible()) {
      g.setColor(Color.RED);
      Rectangle bounds = player.getBounds();
      g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics = (Graphics2D) g;

    if (inGame) {
      graphics.drawImage(background, 0, 0, screenWidth, screenHeight, this);

      debugger(g);

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

      graphics.setColor(Color.RED);
      Font font = new Font("Arial", Font.PLAIN, 30);
      graphics.setFont(font);
      graphics.drawString("KILLS:" + totalDeaths, 10, 30);

    } else {
      gameOverSound.getClip().start();
//      stageMusic.stop();
      ImageIcon reference = new ImageIcon("src//res//gameover.png");
      background = reference.getImage();
      graphics.drawImage(reference.getImage(), 100, 100, null);
    }

    g.dispose();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    player.update();

    if (player.isTurbo()) {
      timer.setDelay(2);
    }

    if (player.isTurbo() == false) {
      timer.setDelay(5);
    }

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
        if (player.isTurbo()) {
          shoots.get(shootIndex).setSPEED(1);
        }
        if (!player.isTurbo()) {
          shoots.get(shootIndex).setSPEED(12);
        }
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
        if (player.isTurbo()) {
          tempEnemy1.setVisible(false);
        } else {
          player.setVisible(false);
          tempEnemy1.setVisible(false);
          inGame = false;
        }
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
          tempEnemy1.setenemyLife(tempEnemy1.getenemyLife() - 1);
          if (tempEnemy1.getenemyLife() < 1) {
            totalDeaths += 1;
            explosion.play();
          }
          tempShoot.setVisible(false);
        }
      }
    }
  }

  private class KAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
      player.keyRelease(e);
    }
  }
}
