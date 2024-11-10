package Game;

import java.awt.*;

public class Player {

    int speed = 6;
    int x,y,width,height;
    public boolean moveRight, moveLeft;


    public Player(int x, int y){
        this.x = x;
        this.y = y;
        this.width = 140;
        this.height = 30;

    }

    public void tick(){
        if (moveRight) {
            x += speed;
        }else if(moveLeft){
            x -= speed;
        }

        if(x < 0){
            x *= 0;
        }else if( x + width > Main.WIDTH - 16){
            x = Main.WIDTH - width - 16;
        }


    }

    public void render(Graphics g){
        g.drawImage(Main.playerSprite, x, y, width, height, null);

        //g.setColor(Color.red);
        //g.drawRect(x+40,y,width-80,height);
    }
}

