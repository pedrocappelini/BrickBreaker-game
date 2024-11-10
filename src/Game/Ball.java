package Game;

import java.awt.*;
import java.util.Random;

import static Game.Main.ball;

public class Ball {

    public boolean movingLeft,movingRight;
    private boolean failed = false;
    public int width, height;
    public double angleMin = 30;
    public double angleMax = 150;
    public double x;
    public double y;
    double dx;
    double dy;
    public double speed = 3.5;

    public Ball(int x, int y){
        this.x = x;
        this.y = y;
        this.width = 32;
        this.height = 32;

        Random rand = new Random();
        double angle = Math.toRadians(angleMin + rand.nextDouble() * (angleMax - angleMin));
        this.dx =  Math.cos(angle);
        this.dy = Math.sin(angle);
        //normalizes the speed of the ball
        double length = Math.sqrt(dx*dx + dy*dy);
        if (length != 0) {
            this.dx /= length;
            this.dy /= length;
        }
    }

    public void gameOver(){
        if(failed){
            System.out.println("Game Over");
            System.exit(0);
        }
    }

    public void tick(){
        //adjusting the speed
        x += dx * speed;
        y += dy * speed;

        if(x < 0 || x+width-10 > Main.WIDTH - width){
            dx *= -1;
        }
        if(y < 0){
            ball.dy = Math.abs(ball.dy); //ensures the ball goes down
            int angle = new Random().nextInt(120 - 45) + 45;
            ball.dx = Math.cos(Math.toRadians(angle)) * (ball.dx > 0 ? 1 : -1);
        }
        if(y > Main.HEIGHT){
            failed = true;
            gameOver();
        }

        Rectangle boundsBall = new Rectangle ((int)x,(int)y,width,height);
        Rectangle boundsMiddlePlayer = new Rectangle(Main.player.x+40,Main.player.y,Main.player.width-80,Main.player.height);
        Rectangle boundsLeftPlayer = new Rectangle(Main.player.x,Main.player.y,40,Main.player.height);
        Rectangle boundsRightPlayer = new Rectangle(Main.player.x+100,Main.player.y,40,Main.player.height);

        //bola q penso 0
        if(boundsBall.intersects(boundsMiddlePlayer)){
            dy *= -1;
        }else if(boundsBall.intersects(boundsRightPlayer)) {
            int angle = new Random().nextInt(75 - 30) + 30;
            dx = Math.cos(Math.toRadians(angle));
            dy = Math.sin(Math.toRadians(angle));
            dy *= -1;
        }else if(boundsBall.intersects(boundsLeftPlayer)){
            int angle = new Random().nextInt(135 - 105) + 105;
            dx = Math.cos(Math.toRadians(angle));
            dy = Math.sin(Math.toRadians(angle));
            dy *= -1;
        }


        //randomize while the player moves (not working properly)
        /*if(boundsBall.intersects(boundsPlayer)){
            Random rand = new Random();
            angleMin = 45;
            angleMax = 135;
            double angle = angleMin + rand.nextDouble() * (angleMax - angleMin);

            if(movingLeft){
                this.dx =  Math.cos(angle);
                this.dy = Math.sin(angle);
            }else if(movingRight){
                this.dx =  Math.cos(angle);
                this.dy = Math.sin(angle);
            }else{
                dy *= -1;
            }
        }*/

    }

    public void render(Graphics g){
        g.drawImage(Main.ballSprite, (int)x, (int)y, width, height, null);

        //g.setColor(Color.red);
        //g.drawRect((int)x,(int)y,width,height);
    }
}

