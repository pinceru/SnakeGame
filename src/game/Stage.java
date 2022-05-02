package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Stage extends JPanel implements ActionListener {

	private char direction = 'D', rematch;
	private int snakeSize = 6, foodX, foodY, score = 0;
	private Timer timer;
	private Random random;
	private boolean running = false;
	private int[] xAxis = new int[Config.UNITS];
	private int[] yAxis = new int[Config.UNITS];
	
	Stage() {
		setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
		setBackground(Color.DARK_GRAY);
		setFocusable(true);
		addKeyListener(new KeyListenerAdapter());
		random = new Random();
		play();
	}
	
	public void play() {
		generateFood();
		running = true;
		timer = new Timer(200, this);
		timer.start();
	}
	
	public void generateFood() {
		foodX = Food.getFoodX(random);
		foodY = Food.getFoodY(random);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawScreen(g);
    }
	
	public void drawScreen(Graphics g) {
		if(running) {
			g.setColor(Color.RED);
			g.fillRect(foodX, foodY, Config.FOOD_SIZE, Config.FOOD_SIZE);
			for(int i = 0; i < snakeSize; i++) {
				if(i == 0) {
					g.setColor(Color.GREEN);
					g.fillRect(xAxis[0], yAxis[0], Config.SNAKE_WIDTH, Config.SNAKE_WIDTH);
				} else {
					g.fillRect(xAxis[i], yAxis[i], Config.SNAKE_WIDTH, Config.SNAKE_WIDTH);
				}
			}
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Pontos: " + score, 
					(Config.SCREEN_WIDTH - metrics.stringWidth("Pontos: " + score)) / 2, 
					g.getFont().getSize());
		} else {
			gameOver(g);
		}
	}
	
	private void gameOver(Graphics g) {
		Scanner scan = new Scanner(System.in);
		g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics scoreFont = getFontMetrics(g.getFont());
        g.drawString("Pontos: " + score, 
        		(Config.SCREEN_WIDTH - scoreFont.stringWidth("Pontos: " + score)) / 2, 
        		g.getFont().getSize());
        g.setFont(new Font("Arial", Font.BOLD, 45));
        FontMetrics finalFont = getFontMetrics(g.getFont());
        g.drawString("Fim de jogo. Se fudeu.", 
        		(Config.SCREEN_WIDTH - finalFont.stringWidth("Fim de jogo. Se fudeu.")) / 2, 
        		Config.SCREEN_HEIGHT / 2);
//        g.drawString("Jogar novamente? [S / N]", 
//        		(Config.SCREEN_WIDTH - finalFont.stringWidth("Jogar novamente? [S / N]")) / 2, 
//        		Config.SCREEN_HEIGHT);
//        rematch = scan.next().charAt(0);
//        if(rematch == 'S') {
//        	play();
//        }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			getFood();
			limitsValidate();
		}
		repaint();
		
	}
	
	private void limitsValidate() {
		for(int i = snakeSize; i > 0; i--) {
			if(yAxis[0] == yAxis[i] && xAxis[0] == xAxis[i]) {
				running = false;
				break;
			}
		}
		
		if(xAxis[0] < 0 || xAxis[0] > Config.SCREEN_WIDTH || yAxis[0] < 0 || yAxis[0] > Config.SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}

	private void getFood() {
		if(xAxis[0] == foodX && yAxis[0] == foodY) {
			snakeSize++;
			score++;
			generateFood();
		}
		
	}

	private void move() {
		for(int i = snakeSize; i > 0; i--) {
			yAxis[i] = yAxis[i - 1];
			xAxis[i] = xAxis[i - 1];
		}
		switch (direction) {
			case 'W':
				yAxis[0] = yAxis[0] - Config.SNAKE_WIDTH;
                break;
            case 'S':
            	yAxis[0] = yAxis[0] + Config.SNAKE_WIDTH;
                break;
            case 'D':
				xAxis[0] = xAxis[0] + Config.SNAKE_WIDTH;
                break;
            case 'A':
            	xAxis[0] = xAxis[0] - Config.SNAKE_WIDTH;
                break;
		}
	}

	class KeyListenerAdapter extends KeyAdapter {
		@Override
        public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT: {
					if(direction != 'D') {
						direction = 'A';
					}
					break;
				}
				case KeyEvent.VK_RIGHT: {
					if(direction != 'A') {
						direction = 'D';
					}
					break;
				}
				case KeyEvent.VK_UP: {
					if(direction != 'S') {
						direction = 'W';
					}
					break;
				}
				case KeyEvent.VK_DOWN: {
					if(direction != 'W') {
						direction = 'S';
					}
					break;
				}
				default:
                    break;
			}
		}
	}
}
