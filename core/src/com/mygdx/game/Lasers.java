package com.mygdx.game;
import static com.mygdx.game.Constants.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Lasers {
    private int no;
    //private Sprite pic;
    private int x;
    private int y;
    private int x2;
    private int y2;
    //private boolean edge;//where to draw laserpoint
    private int bulletX;
    private int bulletY;
    private int direction;
    //private boolean drawn = false;
    public Lasers(int no, int x, int y, int x2, int y2, int direction) {
        //pic = new Sprite(new Texture(Gdx.files.internal("boards\\laser.png")));
        this.no = no;
        this.x = x;
        this.x*=60;
        this.y = y;
        this.y*=60;
        this.x2 = x2;
        this.x2*=60;
        this.y2 = y2;
        this.y2*=60;
        this.y = SCREEN_H - this.y;
        this.y2 = SCREEN_H - this.y2;
        this.direction = direction;
        //this.edge = edge;

        if (this.direction == LEFT1) {
            if(this.no==1) {//1 is middle beam
                this.x += 50;
                this.x2 -= 9;
                this.y -= 10;
                this.y2 -= 10;
            }
            if(this.no==2) {//2 is top beam
                this.x += 50;
                this.x2-=9;
                this.y += 5;
                this.y2 += 5;
            }
            if(this.no==3) {//3 is bottom beam
                this.x += 50;
                this.x2-=9;
                this.y -= 25;
                this.y2 -= 25;
            }
        }//if (this.direction == LEFT1)

        if (this.direction == RIGHT1) {
            if (this.no == 1) {//1 is middle beam
                this.x -= 10;
                this.x2 += 48;
                this.y -= 10;
                this.y2 -= 10;
            }
            if (this.no == 2) {//2 is top beam
                this.x -= 10;
                this.x2 += 48;
                this.y += 5;
                this.y2 += 5;
            }
            if (this.no == 3) {//3 is bottom beam
                this.x -= 10;
                this.x2 += 48;
                this.y -= 25;
                this.y2 -= 25;
            }
        }


        if (this.direction == DOWN1) {
            if (this.no == 1) {//1 is middle beam
                this.x += 20;
                this.x2 += 20;
                this.y += 20;
                this.y2 -= 40;
            }
            if (this.no == 2) {//2 is left beam
                this.x += 5;
                this.x2 += 5;
                this.y += 20;
                this.y2 -= 40;
            }
            if (this.no == 3) {//3 is right beam
                this.x += 35;
                this.x2 += 35;
                this.y += 20;
                this.y2 -= 40;
            }
        }

        if (this.direction == UP1) {
            if (this.no == 1) {//1 is middle beam
                this.x += 20;
                this.x2 += 20;
                this.y -= 40;
                this.y2 += 20;
            }
            if (this.no == 2) {//2 is left beam
                this.x += 5;
                this.x2 += 5;
                this.y -= 40;
                this.y2 += 20;
            }
            if (this.no == 3) {//3 is right beam
                this.x += 35;
                this.x2 += 35;
                this.y -= 40;
                this.y2 += 20;
            }
        }

        bulletX = this.x;
        bulletY = this.y;
    }//Lasers Constructor

    private boolean active = false;
    //getters and setters
    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX2() {
        return x2;
    }
    public void setX2(int x2) {
        this.x2 = x2;
    }
    public int getY2() {
        return y2;
    }
    public void setY2(int y2) {
        this.y2 = y2;
    }
    public int getBulletX() {
        return bulletX;
    }
    public void setBulletX(int bulletX) {
        this.bulletX = bulletX;
    }
    public int getBulletY() {
        return bulletY;
    }
    public void setBulletY(int bulletY) {
        this.bulletY = bulletY;
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}//class Lasers
