package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.sql.Array;

import static com.mygdx.game.Constants.*;
//import org.w3c.dom.Text;
//import java.awt.*;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.Color.YELLOW;

public class Robots {
    //private final int SCREEN_H = 800;
    private final int OFF = 0;//robot player type
    private final int ON = 1;
    private final int CPU = 2;

    private String name;
    private boolean active;
    private boolean virtual;
    private int lives;
    private int health;
    private int x;//robots tile coordinates
    private int y;//robots tile coordinates
    private int AIX;//robots AI tile coordinates
    private int AIY;//robots AI tile coordinates
    private int currentTarget;
    public boolean[] target;
    private int targetX;
    private int targetY;
    private float drawX;//pixel coordinates
    private float drawY;
    private String  tileType = "";
    private int facingDir;
    private int guiFacingDir;
    private Color color;
    private Sprite pic;
    private Sprite picV;
    private int type;
    private float scale;
    private boolean dirSelected;
    private boolean cardsSelected;
    private int cardsHeld;
    private int moveType = -1;
    private int bounceDistance;
    private boolean falling = false;
    private float moveDistance;
    private int turnAmount;
    private int moveDirection;
    private boolean blockedUp = false;
    private boolean blockedDown = false;
    private boolean blockedLeft = false;
    private boolean blockedRight = false;
    private boolean drawZap = false;
    private boolean dead;
    private int respawnX;
    private int respawnY;

    public Robots (int no) {
        switch (no) {
            case 1:
                this.name = "Twonky";
                this.x = 70;
                this.y = 200;
                this.pic = new Sprite(new Texture(Gdx.files.internal("robots\\twonky.png")));
                this.picV = new Sprite(new Texture(Gdx.files.internal("robots\\twonky_v.png")));
                this.color =  WHITE;
                break;
            case 2:
                this.name = "SquashBot";
                this.x = 70;
                this.y = 350;
                this.color =  PURPLE;
                this.pic = new Sprite(new Texture(Gdx.files.internal("robots\\squash.png")));
                this.picV = new Sprite(new Texture(Gdx.files.internal("robots\\squash_v.png")));
                break;
            case 3:
                this.name = "Twitch";
                this.x = 70;
                this.y = 500;
                this.color =  ORANGE;
                this.pic = new Sprite(new Texture(Gdx.files.internal("robots\\twitch.png")));
                this.picV = new Sprite(new Texture(Gdx.files.internal("robots\\twitch_v.png")));
                break;
            case 4:
                this.name = "ZoomBot";
                this.x = 70;
                this.y = 650;
                this.color =  MAGENTA;
                this.pic = new Sprite(new Texture(Gdx.files.internal("robots\\zoom.png")));
                this.picV = new Sprite(new Texture(Gdx.files.internal("robots\\zoom_v.png")));
                break;
            case 5:
                this.name = "HammerBot";
                this.x = 690;
                this.y = 200;
                this.color =  GREEN;
                this.pic = new Sprite(new Texture(Gdx.files.internal("robots\\hammer.png")));
                this.picV = new Sprite(new Texture(Gdx.files.internal("robots\\hammer_v.png")));
                break;
            case 6:
                this.name = "SpinBot";
                this.x = 690;
                this.y = 350;
                this.color =  RED;
                this.pic = new Sprite(new Texture(Gdx.files.internal("robots\\spin.png")));
                this.picV = new Sprite(new Texture(Gdx.files.internal("robots\\spin_v.png")));
                break;
            case 7:
                this.name = "Hulk X90";
                this.x = 690;
                this.y = 500;
                this.color =  BLUE;
                this.pic = new Sprite(new Texture(Gdx.files.internal("robots\\hulk.png")));
                this.picV = new Sprite(new Texture(Gdx.files.internal("robots\\hulk_v.png")));
                break;
            case 8:
                this.name = "TrundleBot";
                this.x = 690;
                this.y = 650;
                this.color =  YELLOW;
                this.pic = new Sprite(new Texture(Gdx.files.internal("robots\\trundle.png")));
                this.picV = new Sprite(new Texture(Gdx.files.internal("robots\\trundle_v.png")));
                break;
        }//switch(no)
        y = SCREEN_H - y;
        this.type = OFF;
        lives = 3;
        virtual = true;
        active = false;
        health = 10;
        moveType = NONE;
        moveDirection = NONE;
        bounceDistance = 10;
        scale = 1.0f;
        cardsHeld = 0;

        target = new boolean[6];
        for(int i=0; i<6; i++){
            target[i] = false;
        }
        target[0] = true;
        currentTarget=1;

        /*
        target[1] = true;
        target[2] = false;
        target[3] = false;
        target[4] = false;
        target[5] = false;
        target[6] = false;
        */

        /*

        */
        currentTarget=2;
    }//Robots(int no) constructor

    //getters and setters
    public String getName() {
        return name;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
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
    public float getDrawX() {
        return drawX;
    }
    public void setDrawX(float drawX) {
        this.drawX = drawX;
    }
    public float getDrawY() {
        return drawY;
    }
    public void setDrawY(float drawY) {
        this.drawY = drawY;
    }
    public int getCurrentTarget() {
        return currentTarget;
    }
    public void setCurrentTarget(int currentTarget) {
        this.currentTarget = currentTarget;
    }
    public int getTargetX() {
        return targetX;
    }
    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }
    public int getTargetY() {
        return targetY;
    }
    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }
    public String getTileType() {
        return tileType;
    }
    public void setTileType(String tileType) {
        this.tileType = tileType;
    }
    public boolean isVirtual() {
        return virtual;
    }
    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }
    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getFacingDir() {
        return facingDir;
    }
    public void setFacingDir(int facingDir) {
        this.facingDir = facingDir;
    }
    public int getGuiFacingDir() {
        return guiFacingDir;
    }
    public void setGuiFacingDir(int guiFacingDir) {
        this.guiFacingDir = guiFacingDir;
    }
    public int getMoveDirection() {
        return moveDirection;
    }
    public void setMoveDirection(int moveDirection) {
        this.moveDirection = moveDirection;
    }
    public int getBounceDistance() {
        return bounceDistance;
    }
    public void setBounceDistance(int bounceDistance) {
        this.bounceDistance = bounceDistance;
    }
    public Sprite getPic() {
        return pic;
    }
    public Sprite getPicV() {
        return picV;
    }
    public Color getColor() {
        return color;
    }
    public Sprite getTexture() {
        return pic;
    }
    public boolean getActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public int getMoveType() {
        return moveType;
    }
    public void setMoveType(int moveType) {
        this.moveType = moveType;
    }
    public float getMoveDistance() {
        return moveDistance;
    }
    public void setMoveDistance(float moveDistance) {
        this.moveDistance = moveDistance;
    }
    public int getTurnAmount() {
        return turnAmount;
    }
    public void setTurnAmount(int turnAmount) {
        this.turnAmount = turnAmount;
    }
    public boolean isBlockedUp() {
        return blockedUp;
    }
    public void setBlockedUp(boolean blockedUp) {
        this.blockedUp = blockedUp;
    }
    public boolean isBlockedDown() {
        return blockedDown;
    }
    public void setBlockedDown(boolean blockedDown) {
        this.blockedDown = blockedDown;
    }
    public boolean isBlockedLeft() {
        return blockedLeft;
    }
    public void setBlockedLeft(boolean blockedLeft) {
        this.blockedLeft = blockedLeft;
    }
    public boolean isBlockedRight() {
        return blockedRight;
    }
    public void setBlockedRight(boolean blockedRight) {
        this.blockedRight = blockedRight;
    }
    public boolean isDrawZap() {
        return drawZap;
    }
    public void setDrawZap(boolean drawZap) {
        this.drawZap = drawZap;
    }
    public boolean isFalling() {
        return falling;
    }
    public void setFalling(boolean falling) {
        this.falling = falling;
    }
    public float getScale() {
        return scale;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }
    public boolean isDirSelected() {
        return dirSelected;
    }
    public void setDirSelected(boolean dirSelected) {
        this.dirSelected = dirSelected;
    }
    public boolean getCardsSelected() {
        return cardsSelected;
    }
    public void setCardsSelected(boolean cardsSelected) {
        this.cardsSelected = cardsSelected;
    }
    public int getCardsHeld() {
        return cardsHeld;
    }
    public void setCardsHeld(int cardsHeld) {
        this.cardsHeld = cardsHeld;
    }
    public boolean isDead() {
        return dead;
    }
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    public int getRespawnX() {
        return respawnX;
    }
    public void setRespawnX(int respawnX) {
        this.respawnX = respawnX;
    }
    public int getRespawnY() {
        return respawnY;
    }
    public void setRespawnY(int respawnY) {
        this.respawnY = respawnY;
    }
}//class Robots


