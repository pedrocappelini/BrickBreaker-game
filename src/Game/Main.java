package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Main extends Canvas implements Runnable, KeyListener {

    public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    public static BufferedImage Spritesheet;
    public static BufferedImage playerSprite;
    public static BufferedImage ballSprite;
    public static BufferedImage gBrickSprite;
    public static BufferedImage yBrickSprite;
    public static BufferedImage rBrickSprite;
    public static BufferedImage Background;


    static {
        try {
            Spritesheet = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/Spritesheet.png")));
            playerSprite = Spritesheet.getSubimage(1, 12, 30, 10);
            ballSprite = Spritesheet.getSubimage(34,2,12,12);
            gBrickSprite = Spritesheet.getSubimage(49,4,14,7);
            yBrickSprite = Spritesheet.getSubimage(33,20,14,7);
            rBrickSprite = Spritesheet.getSubimage(49,20,14,7);
            Background = Spritesheet.getSubimage(64,0,120,75);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int WIDTH = 1200;
    public static int HEIGHT = 750;

    static Player player = new Player(WIDTH / 2 - 70, HEIGHT - 120);
    static Ball ball = new Ball(WIDTH/2 - 10, (HEIGHT/2 - 10)+150);
    static Bricks bricks = new Bricks(6, 12);

    public static void main(String[] args) {
        Main main = new Main();
        main.setupFrame();
        new Thread(main).start();
    }

    public Main() {
        addKeyListener(this);
        setFocusable(true);
    }

    public void setupFrame() {
        JFrame frame = new JFrame("BrickBreaker");
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.pack(); //ensure the frame is getting the preferred size;
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);
    }

    public void tick() {
        player.tick();
        ball.tick();
        bricks.tick();
    }

    public void render() {
        /* /*change the color of the layer
        Graphics g2 = layer.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillOval(0,0,WIDTH,HEIGHT);
        g2.dispose();
        */
        //a buffer strategy is a way to draw things on the screen
        BufferStrategy bs = this.getBufferStrategy();
        if (getBufferStrategy() == null) {
            createBufferStrategy(3);
            return;
        }
        //creates a g object of graphics to draw the layer
        Graphics g;
        //draw the layer
        g = bs.getDrawGraphics();
        g.drawImage(layer, 0, 0, WIDTH, HEIGHT, null);
        if(Background != null) {
            g.drawImage(Background, 0, 0, WIDTH, HEIGHT, null);
        }

        player.render(g);
        ball.render(g);
        bricks.render(g);


        bs.show();
    }

    @Override
    public void run() {
        while (true) {
            tick();
            render();
            try {
                Thread.sleep(16); //60 fps
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.moveLeft = true;
            ball.movingLeft = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            player.moveRight = true;
            ball.movingRight = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.moveLeft = false;
            ball.movingLeft = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            player.moveRight = false;
            ball.movingRight = false;
        }
    }
}

