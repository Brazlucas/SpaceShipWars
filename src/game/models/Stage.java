package game.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.List;

public class Stage extends JPanel implements ActionListener {
  private Image background;
  private Player player;
  private Timer timer;
  private int screenWidth, screenHeight;
  private Image cloud;

  private int cloudX = 0; // Posição X da nuvem

  private int cloudSpeed = 2;

  public Stage() {
    setFocusable(true);
    setDoubleBuffered(true);
    ImageIcon reference = new ImageIcon("src//res//background2.png");
    background = reference.getImage();

    ImageIcon cloudReference = new ImageIcon("src//res//cloudb2.png");
    cloud = cloudReference.getImage();

    player = new Player();
    player.load();

    addKeyListener(new KAdapter());

    timer = new Timer(2, this);
    timer.start();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    screenWidth = screenSize.width;
    screenHeight = screenSize.height;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D graphics = (Graphics2D) g;
    graphics.drawImage(background, 0, 0, screenWidth, screenHeight, this);
    graphics.drawImage(cloud, cloudX, 0, screenWidth, screenHeight, this);
    graphics.drawImage(player.getBackground(), player.getX(), player.getY(), this);

    List<Shoot> shoots = player.getShoots();
    for(int i = 0; i < shoots.size(); i++) {
      Shoot m = shoots.get(i);
      m.load();
      graphics.drawImage(m.getImage(), m.getX(), m.getY(), this);
    }

    g.dispose();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    player.update();
    List<Shoot> shoots = player.getShoots();
    for(int i = 0; i < shoots.size(); i++) {
      Shoot m = shoots.get(i);
      if(m.isVisible()) {
        m.update();
      } else {
        shoots.remove(i);
      }
    }

    cloudX++;
    if (cloudX > (screenWidth / 2)) {
      cloudX = -2000;
      System.out.println(cloudX);
    }

    cloudX += cloudSpeed;

    repaint();
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
