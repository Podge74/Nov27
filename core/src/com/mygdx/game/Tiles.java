package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.mygdx.game.Constants.*;

public class Tiles {
    private String name;
    private int type;
    private int no;
    private int x,y;
    private int animationFrame;
    private float rotation = 0;//used only for gear tiles
    private Sprite pic;
    private Sprite pic2;
    private boolean canPlaceFlag = false;
    private boolean flagPlaced = false;
    private boolean upBlocked = false;
    private boolean downBlocked = false;
    private boolean leftBlocked = false;
    private boolean rightBlocked = false;
    private boolean upRed = false;
    private boolean downRed = false;
    private boolean leftRed = false;
    private boolean rightRed = false;
    private boolean upBlue = false;
    private boolean downBlue = false;
    private boolean leftBlue = false;
    private boolean rightBlue = false;
    private int gearRotate = 0;
    //private int leftRotate = 0;
    //private int rightRotate = 0;
    private boolean hole = false;
    private int repair = 0;
    private boolean laser = false;
    private int flag = 0;
    private int AIscore = NONE;


    public Tiles(int y, int x, int type) {
        this.type = type;
        this.x = x+1;//x1, y1 is top left tile
        this.y = y+1;
        animationFrame = 0;
        rotation = 0;
        gearRotate = 0;
        pic2 = null;
        if(type > 12) {
            this.no = -1;
        }

        //add rotation to belt tiles
        switch(type) {
            case 0://floor
                name = "floor";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\floor.png")));
                canPlaceFlag = true;
                break;
            case 1://red belt up
                name = "redBeltUp";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\red0.png")));
                upRed = true;
                break;
            case 2://red belt down
                name = "redBeltDown";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\red0.png")));
                rotation = 180;
                downRed = true;
                break;
            case 3://red belt left
                name = "redBeltLeft";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\red0.png")));
                rotation = 90;
                leftRed = true;
                break;
            case 4://red belt right
                name = "redBeltRight";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\red0.png")));
                rotation = 270;
                rightRed = true;
                break;
            case 5://blue belt up
                name = "blueBeltUp";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\blue0.png")));
                rotation = 0;
                upBlue = true;
                break;
            case 6://blue belt down
                name = "blueBeltDown";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\blue0.png")));
                rotation = 180;
                downBlue = true;
                break;
            case 7://blue belt left
                name = "blueBeltLeft";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\blue0.png")));
                rotation = 90;
                leftBlue = true;
                break;
            case 8://blue belt right
                name = "blueBeltRight";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\blue0.png")));
                rotation = 270;
                rightBlue = true;
                break;
            case 9://gear clockwise
                name = "gearClockwise";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\clock_anim2.png")));
                //pic = new Sprite(new Texture(Gdx.files.internal("tiles\\floor.png")));
                //pic2 = new Sprite(new Texture(Gdx.files.internal("tiles\\clock_anim2.png")));
                gearRotate = 0;
                break;
            case 10://gear anti-clockwise
                name = "gearAntiClockwise";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\anti_clock_anim.png")));
                //pic = new Sprite(new Texture(Gdx.files.internal("tiles\\floor.png")));
                //pic2 = new Sprite(new Texture(Gdx.files.internal("tiles\\anti_clock_anim.png")));
                gearRotate = 0;
                break;
            case 11://wall up
                name = "wallUp";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\wall_up.png")));
                upBlocked = true;
                canPlaceFlag = true;
                break;
            case 12://wall down
                name = "wallDown";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\wall_down.png")));
                downBlocked = true;
                canPlaceFlag = true;
                break;
            case 13://wall left
                name = "wallLeft";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\wall_left.png")));
                leftBlocked = true;
                canPlaceFlag = true;
                break;
            case 14://wall right
                name = "wallRight";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\wall_right.png")));
                rightBlocked = true;
                canPlaceFlag = true;
                break;
            case 15://hole
                name = "hole";
                //pic = new Sprite(new Texture(Gdx.files.internal("tiles\\hole.png")));
                hole = true;
                break;
            case 16://wall top/left
                name = "wallTopLeft";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\wall_tl.png")));
                upBlocked = true;
                leftBlocked = true;
                break;
            case 17://wall top/right
                name = "wallTopRight";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\wall_tr.png")));
                upBlocked = true;
                rightBlocked = true;
                break;
            case 18://wall bottom/left
                name = "wallBottomLeft";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\wall_bl.png")));
                downBlocked = true;
                leftBlocked = true;
                break;
            case 19://wall bottom/right
                name = "wallBottomRight";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\wall_br.png")));
                downBlocked = true;
                rightBlocked = true;
                break;
            case 88://single spanner repair
                name = "singleSpanner";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\one_spanner.png")));
                repair = 1;
                break;
            case 99://double spanner repair
                name = "doubleSpanner";
                pic = new Sprite(new Texture(Gdx.files.internal("tiles\\two_spanner.png")));
                repair = 2;
                break;
        }//switch
    }//Tiles constructor

    //getters and setters
    public String getName() {
        return name;
    }
    public int getNo() {
        return no;
    }
    public int getAnimationFrame() {
        return animationFrame;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getType() {
        return type;
    }
    public float getRotation() {
        return rotation;
    }
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    public Sprite getPic() {
        return pic;
    }
    public Sprite getPic2() {
        return pic2;
    }
    public boolean getCanPlaceFlag() {
        return canPlaceFlag;
    }
    public boolean getFlagPlaced() {
        return flagPlaced;
    }
    public void setFlagPlaced(boolean flagPlaced) {
        this.flagPlaced = flagPlaced;
    }
    public boolean isUpBlocked() {
        return upBlocked;
    }
    public boolean isDownBlocked() {
        return downBlocked;
    }
    public boolean isLeftBlocked() {
        return leftBlocked;
    }
    public boolean isRightBlocked() {
        return rightBlocked;
    }
    public void setUpBlocked(boolean upBlocked) {
        this.upBlocked = upBlocked;
    }
    public void setDownBlocked(boolean downBlocked) {
        this.downBlocked = downBlocked;
    }
    public void setLeftBlocked(boolean leftBlocked) {
        this.leftBlocked = leftBlocked;
    }
    public void setRightBlocked(boolean rightBlocked) {
        this.rightBlocked = rightBlocked;
    }
    public boolean isUpRed() {
        return upRed;
    }
    public boolean isDownRed() {
        return downRed;
    }
    public boolean isLeftRed() {
        return leftRed;
    }
    public boolean isRightRed() {
        return rightRed;
    }
    public boolean isUpBlue() {
        return upBlue;
    }
    public boolean isDownBlue() {
        return downBlue;
    }
    public boolean isLeftBlue() {
        return leftBlue;
    }
    public boolean isRightBlue() {
        return rightBlue;
    }
    public int getGearRotate() {
        return gearRotate;
    }
    public void setGearRotate(int gearRotate) {
        this.gearRotate = gearRotate;
    }
    public boolean isHole() {
        return hole;
    }
    public int getRepair() {
        return repair;
    }
    public int getAIscore() {
        return AIscore;
    }
    public void setAIscore(int AIscore) {
        this.AIscore = AIscore;
    }
    /*
    public int[][] getTUTORIAL() {
        return TUTORIAL;
    }

    public int[][] getVAULT() {
        return VAULT;
    }

    public int[][] getCHOP_SHOP() {
        return CHOP_SHOP;
    }

    public int[][] getSPIN_ZONE() {
        return SPIN_ZONE;
    }

    public int[][] getEXCHANGE() {
        return EXCHANGE;
    }

    public int[][] getISLAND() {
        return ISLAND;
    }
    */
}//class Tiles
