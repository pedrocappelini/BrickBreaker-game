package Game;

import java.awt.*;
import static Game.Main.ball;

public class Bricks {

    public int[][] map;
    public int brickWidth;
    public int brickHeight;

    public Bricks(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = 1130 / col;
        brickHeight = 300 / row;
    }

    public void tick() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    //detect collision
                    int brickX = j * brickWidth + 10 + (j * 3);
                    int brickY = i * brickHeight + (i * 3);
                    //the bounds need a +3*i/j to make the collision more accurate because of the space between the bricks
                    Rectangle boundsBrick = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle boundsBall = new Rectangle((int) ball.x, (int) ball.y, ball.width, ball.height);

                    if (boundsBrick.intersects(boundsBall)) {
                        ball.dy *= -1;
                        setBrickValue(0, i, j);
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 1){
                    if(i == 0 || i == 1){
                        g.drawImage(Main.gBrickSprite, j * (brickWidth + 3) + 10, i * (brickHeight + 3), brickWidth, brickHeight, null);
                    }else if(i == 2 || i == 3){
                        g.drawImage(Main.rBrickSprite, j * (brickWidth + 3) + 10, i * (brickHeight + 3), brickWidth, brickHeight, null);
                    }else{
                        g.drawImage(Main.yBrickSprite, j * (brickWidth + 3) + 10, i * (brickHeight + 3), brickWidth, brickHeight, null);
                    }
                }
            }
        }
    }
    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
