package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Flamers {
    private Sprite pic;
    private int x = -1;
    private int y = -1;
    private int direction = -1;
    private boolean active = false;

    public Flamers(int x, int y, int direction) {
        pic = new Sprite(new Texture(Gdx.files.internal("tiles\\flamer0.png")));
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    //getters and setters
    public Sprite getPic() {
        return pic;
    }
    public void setPic(Sprite pic) {
        this.pic = pic;
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
}//class flamers
