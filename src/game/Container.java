package game;

import game.models.Stage;

import javax.swing.JFrame;
public class Container extends JFrame {
  public Container() {
    add(new Stage());
    setTitle("First game");
    setSize(1024, 768);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    this.setResizable(false);
    setVisible(true);
  }

  public static void main(String []args) {
    new Container();
  }
}