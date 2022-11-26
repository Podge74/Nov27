package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Flags {
    //private String name;
    private static final int SCREEN_H = 800;
    private int x =0;
    private int y =0;
    private int XDraw =0;
    private int YDraw =0;
    private int no;
    private Sprite pic;

    public Flags(int x, int y, int no) {
        this.x = x;
        this.y = y;
        this.no = no;
        XDraw = x * 60 -1;
        YDraw = y * 60 + 30;
        YDraw = SCREEN_H - YDraw;

        switch(no) {
            case 1:
                pic = new Sprite(new Texture(Gdx.files.internal("flags\\flag1.png")));
                break;
            case 2:
                pic = new Sprite(new Texture(Gdx.files.internal("flags\\flag2.png")));
                break;
            case 3:
                pic = new Sprite(new Texture(Gdx.files.internal("flags\\flag3.png")));
                break;
            case 4:
                pic = new Sprite(new Texture(Gdx.files.internal("flags\\flag4.png")));
                break;
            case 5:
                pic = new Sprite(new Texture(Gdx.files.internal("flags\\flag5.png")));
                break;
            case 6:
                pic = new Sprite(new Texture(Gdx.files.internal("flags\\flag6.png")));
                break;
        }
    }

    public Sprite getPic() {
        return pic;
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
    public int getXDraw() {
        return XDraw;
    }
    public void setXDraw(int XDraw) {
        this.XDraw = XDraw;
    }
    public int getYDraw() {
        return YDraw;
    }
    public void setYDraw(int YDraw) {
        this.YDraw = YDraw;
    }
    public int getNo() {
        return no;
    }
}//class Flags
