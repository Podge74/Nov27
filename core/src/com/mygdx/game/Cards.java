package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import static com.mygdx.game.Constants.*;

//import static com.mygdx.game.Constants.SCREEN_H;

public class Cards {
    //private static final int SCREEN_H = 800;
    private int no;
    private int moveType;
    private String name;
    private Sprite pic;
    private int x, y;
    private int xMin, xMax;
    private int yMin, yMax;
    private int width, height;
    private int priority;
    private String owner;
    private boolean active = false;
    private int order;
    private int pickedOrder;
    private boolean played = false;
    private int score = NONE;
    private int AIX = NONE;
    private int AIY = NONE;
    private boolean locked;

    public Cards(int no) {
        this.no = no;
        if (no >=1 && no <=6) {
            this.name = "U Turn";
            moveType = U_TURN;
            this.pic = new Sprite(new Texture(Gdx.files.internal("cards\\u_turn.png")));
        }
        if (no >=7 && no <=42) {
            if (no%2 == 0) {
                this.name = "Turn Right";
                moveType = TURN_RIGHT;
                this.pic = new Sprite(new Texture(Gdx.files.internal("cards\\turn_right.png")));
            } else {
                this.name = "Turn Left";
                moveType = TURN_LEFT;
                this.pic = new Sprite(new Texture(Gdx.files.internal("cards\\turn_left.png")));
            }
        }
        if (no >= 43 && no <= 48) {
            this.name = "BackUp";
            moveType = BACK_UP;
            this.pic = new Sprite(new Texture(Gdx.files.internal("cards\\back_up.png")));
        }
        if (no >= 49 && no <= 66) {
            this.name = "Forward1";
            moveType = FORWARD_1;
            this.pic = new Sprite(new Texture(Gdx.files.internal("cards\\forward_1.png")));
        }
        if (no >= 67 && no <= 78) {
            this.name = "Forward2";
            moveType = FORWARD_2;
            this.pic = new Sprite(new Texture(Gdx.files.internal("cards\\forward_2.png")));
        }
        if (no >= 79 && no <= 84) {
            this.name = "Forward3";
            moveType = FORWARD_3;
            this.pic = new Sprite(new Texture(Gdx.files.internal("cards\\forward_3.png")));
        }
        this.x = 0;
        this.y = 0;
        /*
        this.xMin = this.x;
        this.xMax = this.x + width;
        this.yMin = SCREEN_H - y - height;
        this.yMax = SCREEN_H - y;
        */
        this.priority = no*10;
        owner = "NONE";
        this.height = 124;
        this.width = 80;
        this.order =-1;
        pickedOrder =-1;
        this.locked = false;
        this.active = false;
    }//Card constructor

    //getters and setters
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public int getMoveType() {
        return moveType;
    }
    public void setPic(Sprite pic) {
        this.pic = pic;
    }
    public Sprite getPic() {
        return pic;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getX() {
        return x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return y;
    }
    public void setxMin() {
        this.xMin = this.x;
        //this.xMin = xMin;
    }
    public int getxMin() {
        return xMin;
    }
    public void setxMax() {
        this.xMax = this.x + this.width;
    }
    public int getxMax() {
        return xMax;
    }
    public void setyMin() {
        this.yMin = SCREEN_H - this.y - this.height;
    }
    public int getyMin() {
        return yMin;
    }
    public void setyMax() {
        this.yMax = SCREEN_H - this.y;
    }
    public int getyMax() {
        return yMax;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getPriority() {
        return priority;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getOwner() {
        return owner;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public int getOrder() {
        return order;
    }
    public void setPickedOrder(int pickedOrder) {
        this.pickedOrder = pickedOrder;
    }
    public int getPickedOrder() {
        return pickedOrder;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean getActive() {
        return active;
    }
    public boolean getPlayed() {
        return played;
    }
    public void setPlayed(boolean played) {
        this.played = played;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getAIX() {
        return AIX;
    }
    public void setAIX(int AIX) {
        this.AIX = AIX;
    }
    public int getAIY() {
        return AIY;
    }
    public void setAIY(int AIY) {
        this.AIY = AIY;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    public boolean getLocked() {
        return locked;
    }
    public int getHeight() {
        return height;
    }

}//class Cards
