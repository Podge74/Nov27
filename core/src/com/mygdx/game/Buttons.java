package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Buttons {
    private final int SCREEN_H = 800;
    public final int BUTTON_BOARD1 = 0;
    public final int BUTTON_BOARD2 = 100;
    public final int BUTTON_BOARD_BUTTON1 = 5;
    public final int BUTTON_BOARD_BUTTON2 = 6;
    public final int BUTTON_ROBOT_TYPE_BOX = 1;
    public final int BUTTON_BACK = 2;
    public final int BUTTON_START = 3;
    public final int BUTTON_DIRECTION_ARROW = 4;

    private String name;
    private int no;
    private int type;
    private Sprite pic;
    private int x,y;
    private int xMin, xMax;
    private int yMin, yMax;
    private int width, height;
    private String text;
    private int textX, textY;
    private Sprite img_typeButton;

    private Sprite arrowUp;
    private Sprite arrowDown;
    private Sprite arrowLeft;
    private Sprite arrowRight;



    public Buttons(int no) {

        arrowUp = new Sprite(new Texture(Gdx.files.internal("buttons\\green_arrow_up.png")));
        arrowDown = new Sprite(new Texture(Gdx.files.internal("buttons\\green_arrow_down.png")));
        arrowLeft = new Sprite(new Texture(Gdx.files.internal("buttons\\green_arrow_left.png")));
        arrowRight = new Sprite(new Texture(Gdx.files.internal("buttons\\green_arrow_right.png")));

        switch(no) {
            case 6://robot type buttons
                this.name = "Twonky";
                this.x = 470;
                this.y = 200;
                //this.textX = this.x+100;
                break;
            case 7:
                this.name = "SquashBot";
                this.x = 470;
                this.y = 350;
                break;
            case 8:
                this.name = "Twitch";
                this.x = 470;
                this.y = 500;
                break;
            case 9:
                this.name = "ZoomBot";
                this.x = 470;
                this.y = 650;
                break;
            case 10:
                this.name = "HammerBot";
                this.x = 1090;
                this.y = 200;
                break;
            case 11:
                this.name = "SpinBot";
                this.x = 1090;
                this.y = 350;
                break;
            case 12:
                this.name = "Hulk X90";
                this.x = 1090;
                this.y = 500;
                break;
            case 13:
                this.name = "TrundleBot";
                this.x = 1090;
                this.y = 650;
                break;
            case 14:
                this.name = "BACK";
                this.text = this.name;
                this.pic = new Sprite(new Texture(Gdx.files.internal("buttons\\type_button2.png")));
                this.type = BUTTON_BACK;
                this.x = 210;
                this.y = 790;//10;
                this.textX = 240;
                this.textY = 720;
                this.width = 180;
                this.height = 100;
                break;
            case 15:
                this.name = "START";
                this.text = this.name;
                this.pic = new Sprite(new Texture(Gdx.files.internal("buttons\\type_button2.png")));
                this.type = BUTTON_START;
                this.x = 835;
                this.y = 790;//10;
                this.textX = 852;
                this.textY = 720;
                this.width = 180;
                this.height = 100;
                break;
            //int aX = 922;
            //int aY = 710;
            case 16:
                this.name = "greenArrowUp";
                this.type = BUTTON_DIRECTION_ARROW;
                this.pic = arrowUp;
                this.x = 922;
                this.y = 620;
                this.width = 80;
                this.height = 96;
                break;
            case 17:
                this.name = "greenArrowDown";
                this.type = BUTTON_DIRECTION_ARROW;
                this.pic = arrowDown;
                this.x = 922;
                this.y = 800;
                this.width = 80;
                this.height = 96;
                break;
            case 18:
                this.name = "greenArrowLeft";
                this.type = BUTTON_DIRECTION_ARROW;
                this.pic = arrowLeft;
                this.width = 96;
                this.height = 80;
                this.x = 832;
                this.y = 710;
                break;
            case 19:
                this.name = "greenArrowRight";
                this.type = BUTTON_DIRECTION_ARROW;
                this.pic = arrowRight;
                this.width = 96;
                this.height = 80;
                this.x = 1012;
                this.y = 710;
                break;
            case 20:
                type = 666;
                this.name = "continue";
                this.pic = new Sprite(new Texture(Gdx.files.internal("buttons\\run.png")));
                this.x = 795;
                this.y = 120;//680;
                this.width = 316;
                this.height = 60;
                break;
        }//switch(no)

        //mini board buttons
        if(no == 100) {
            this.name = "TUTORIAL";
            this.x = 100;
            this.y = 380;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\tutorial.png")));
            this.textX = 130;
            this.textY = 400;
            type = BUTTON_BOARD1;
            width = 284;
            height = 284;
        }
        if(no == 101) {
            this.name = "VAULT";
            this.x = 500;
            this.y = 380;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\vault.png")));
            this.textX = 580;
            this.textY = 400;
            type = BUTTON_BOARD1;
            width = 284;
            height = 284;
        }

        if(no == 102) {
            this.name = "CHOP SHOP";
            this.x = 900;
            this.y = 380;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\chopshop.png")));
            this.textX = 910;
            this.textY = 400;
            type = BUTTON_BOARD1;
            width = 284;
            height = 284;
        }

        if(no == 103) {
            this.name = "SPIN ZONE";
            this.x = 100;
            this.y = 740;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\spinzone.png")));
            this.textX = 130;
            this.textY = 760;
            type = BUTTON_BOARD1;
            width = 284;
            height = 284;
        }

        if(no == 104) {
            this.name = "EXCHANGE";
            this.x = 500;
            this.y = 740;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\exchange.png")));
            this.textX = 530;
            this.textY = 760;
            type = BUTTON_BOARD1;
            width = 284;
            height = 284;
        }

        if(no == 105) {
            this.name = "ISLAND";
            this.x = 900;
            this.y = 740;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\island.png")));
            this.textX = 980;
            this.textY = 760;
            type = BUTTON_BOARD1;
            width = 284;
            height = 284;
        }

        if(no == 106) {
            this.name = "CHESS";
            this.x = 100;
            this.y = 380;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\chess.png")));
            this.textX = 165;
            this.textY = 400;
            type = BUTTON_BOARD2;
            width = 284;
            height = 284;
        }

        if(no == 107) {
            this.name = "CROSS";
            this.x = 500;
            this.y = 380;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\cross.png")));
            this.textX = 570;
            this.textY = 400;
            type = BUTTON_BOARD2;
            width = 284;
            height = 284;
        }

        if(no == 108) {
            this.name = "BLAST_FURNACE";
            this.x = 900;
            this.y = 380;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\blastfurnace.png")));
            this.textX = 870;
            this.textY = 400;
            type = BUTTON_BOARD2;
            width = 284;
            height = 284;
        }

        if(no == 109) {
            this.name = "LASER MAZE";
            this.x = 100;
            this.y = 740;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\lasermaze.png")));
            this.textX = 110;
            this.textY = 760;
            type = BUTTON_BOARD2;
            width = 284;
            height = 284;
        }

        if(no == 110) {
            this.name = "PIT MAZE";
            this.x = 500;
            this.y = 740;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\pitmaze.png")));
            this.textX = 550;
            this.textY = 760;
            type = BUTTON_BOARD2;
            width = 284;
            height = 284;
        }

        if(no == 111) {
            this.name = "MAELSTROM";
            this.x = 900;
            this.y = 740;
            this.pic = new Sprite(new Texture(Gdx.files.internal("boards\\maelstrom.png")));
            this.textX = 910;
            this.textY = 760;
            type = BUTTON_BOARD2;
            width = 284;
            height = 284;
        }


        if(no == 120) {
            this.name = "";
            this.x = 900;
            this.y = 80;
            this.pic = arrowRight;
            this.textX = -1;
            this.textY = -1;
            type = BUTTON_BOARD_BUTTON1;
            width = 96;
            height = 80;
        }
        if(no == 121) {
            this.name = "";
            this.x = 290;
            this.y = 80;
            this.pic = arrowLeft;
            this.textX = -1;
            this.textY = -1;
            type = BUTTON_BOARD_BUTTON2;
            width = 96;
            height = 80;
        }


        if(no >= 6 && no <= 13) {//robot type buttons
            this.type = BUTTON_ROBOT_TYPE_BOX;
            this.pic = new Sprite(new Texture(Gdx.files.internal("buttons\\type_button2.png")));
            this.width = 120;
            this.height = 100;
            this.text = "OFF";
            this.textX = this.x -47;
            this.textY = this.y -65;
        }

        this.no = no;
        this.xMin=x;
        this.xMax=x+width;
        y = SCREEN_H - y;
        this.yMin=SCREEN_H - this.y-this.height;
        this.yMax=SCREEN_H - this.y;
        this.textY =  SCREEN_H - this.textY;
    }//Buttons constructor

    //getters and setters
    public String getName() {
        return name;
    }
    public int getType() {
        return type;
    }
    public int getNo() {
        return no;
    }
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
    public int getxMin() {
        return xMin;
    }
    public int getxMax() {
        return xMax;
    }
    public int getyMin() {
        return yMin;
    }
    public int getyMax() {
        return yMax;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public int getTextX() {
        return textX;
    }
    public void setTextX(int textX) {
        this.textX = textX;
    }
    public int getTextY() {
        return textY;
    }
    public void setTextY(int textY) {
        this.textY = textY;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}//class Buttons
