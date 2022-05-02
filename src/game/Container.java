package game;

import javax.swing.JFrame;

public class Container extends JFrame{
	public static void main(String[] args) {
        new Container();
    }

    Container() {
        add(new Stage());
        setTitle("Snake Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
