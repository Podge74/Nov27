package com.mygdx.game;
import static com.mygdx.game.Constants.*;

public class Boards {
    private int name =-1;
    private int board =-1;
    private boolean currentBoard = false;
    //private int blueFrame = 0;//remove???
    private int blueBelts = NONE;
    private boolean blueBeltsDone = true;
    private int blueBeltPhase = 0;
    private int redBelts = NONE;
    private boolean redBeltsDone = true;
    private int pushers = NONE;
    private boolean pushersDone = true;
    private int gears = NONE;
    private boolean gearsDone = true;
    private int lasers = NONE;
    private boolean lasersDone = true;
    private int flamers = NONE;
    private boolean flamersDone = true;
    private int delay = OFF;
    private int[][] map;

    public Boards(int name) {
        if (name == TUTORIAL) {
            this.name = name;
            map = new int[][]{
                {16,11,11,11,11,11,11,11,11,11,11,17},
                {13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                {13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                {13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                {13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                {13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                {13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                {13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                {13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                {13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                {13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                {18,12,12,12,12,12,12,12,12,12,12,19}
            };
        }//TUTORIAL

        /*
        //push test
        if (name == TUTORIAL) {
            this.name = name;
            map = new int[][]{
                    {16,16,11,11,11,11,11,11,11,11,11,17},
                    {13,13, 0, 0, 0, 0, 0, 0, 0, 9,10,14},
                    {13,13, 0, 0, 0, 0, 0, 0, 0, 1, 5,14},
                    {13,13, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                    {13,13, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                    {13,13, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                    {13,13, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                    {13,13, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                    {13,13, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                    {13,13, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                    {18,18,12,12,12,12,12,12,12,12,12,19}
            };
        }//TUTORIAL
        */

        /*
        //ORIGINAL
        if (name == TUTORIAL) {
            this.name = name;
            map = new int[][]{
                    {16,11,11,11,11,11,11,11,11,11,11,17},
                    {13, 0, 0, 0, 4, 4, 4, 0, 0, 0, 0,14},
                    {13, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0,14},
                    {13, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0,14},
                    {13, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0,14},
                    {13, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0,14},
                    {13, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0,14},
                    {13, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0,14},
                    {13, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0,14},
                    {13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,14},
                    {13, 0, 0, 0, 0,99, 0, 0, 0, 0, 0,14},
                    {18,12,12,12,12,12,12,12,12,12,12,19}
            };
        }//TUTORIAL
        */

        if (name == VAULT) {
            this.name = name;
            map = new int[][]{
                {88, 0,11, 5,11, 0, 5,11, 0,11, 1, 0},
                { 9, 8, 8, 5, 0,13, 5, 0, 0, 0, 1, 0},
                { 2, 0, 0,15, 0,12,12, 0,15, 0, 1,14},
                { 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {13, 2, 0, 0,16, 0, 0,17, 0, 0, 0,14},
                { 0, 2,14, 0, 0,99,99, 0, 0,13, 0, 0},
                { 0, 2,14, 0, 0,99,99, 0, 0,13, 4, 4},
                {13, 2, 0, 0,18, 0, 0,19, 0, 0, 1,14},
                {3, 9, 11, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {13, 0, 0,15, 0,11,11, 0,15, 0, 1,14},
                { 4, 2, 0, 0, 0, 0, 4, 4, 4, 4, 1, 0},
                { 0, 2,12, 0,12, 0, 1,12, 0,12, 0,88}
            };
        }//VAULT
        if (name == CHOP_SHOP) {
            this.name = name;
            map = new int[][]{
                {88, 0,11, 5,11, 6, 1,11, 2,11, 0, 0},
                { 0,15,14, 5, 0, 6, 1, 0, 2, 2, 3, 3},
                {13, 0,11, 5, 7, 7, 1, 0, 2, 2, 0,14},
                { 0, 0, 0, 5, 0,18, 1, 0, 0,15, 0, 0},
                {13, 0, 0, 5, 0, 4,10, 0,11,99, 0,14},
                { 3, 3, 9, 5, 0,99, 2, 3, 9, 3, 3, 3},
                { 4, 2,10, 5, 0, 0, 2,15,12, 0,12, 0},
                {13, 2,12, 5, 0, 0, 2, 0, 0, 0, 0,14},
                { 0, 2,14, 0, 9,10,14, 0,10, 3, 3, 3},
                {13, 2,99, 1, 0,15, 0, 0, 2,15, 0,14},
                {14, 0,13, 1, 0, 0, 0,13, 2, 0,13, 0},
                { 0, 2,12, 1,12, 0, 0,12, 2,12, 0,88}
            };
        }//CHOP_SHOP
        if (name == SPIN_ZONE) {//TEST
            this.name = name;
            map = new int[][]{
                    { 0, 0,11, 0,11, 0, 0,11, 0,11, 0, 0},
                    { 0, 8, 8, 8, 6, 0, 0, 8,15, 8, 6, 0},
                    {13, 5, 9,12, 6,10, 0, 5, 9, 0, 6,14},
                    { 0, 5,88, 9, 6,13,14, 5,99, 9, 6, 0},
                    {13, 5, 7,15, 7, 0,10, 5, 7, 7, 7,14},
                    {0, 0, 0, 0, 10, 0, 0, 0,11,10, 0, 0},
                    {0, 0, 10,12, 0, 0, 0,10, 0, 0, 0, 0},
                    {13, 8, 8, 8, 6,10, 0, 8, 8, 8, 6,14},
                    { 0, 5, 9,99, 6,13,14, 5, 9,88,15, 0},
                    {13,15, 0, 9, 6, 0,10, 5,11, 9, 6,14},
                    { 0, 5,15, 7, 7, 0, 0, 5, 7, 7, 7, 0},
                    { 0, 0,12, 0,12, 0, 0,12, 0,12, 0, 0}
            };
        }//SPIN_ZONE
        /*
        if (name == SPIN_ZONE) {//ORIGINAL
            this.name = name;
            map = new int[][]{
                { 0, 0,11, 0,11, 0, 0,11, 0,11, 0, 0},
                { 0, 8, 8, 8, 6, 0, 0, 8, 8, 8, 6, 0},
                {13, 5, 9,12, 6,10, 0, 5, 9, 0, 6,14},
                { 0, 5,88, 9, 6,13,14, 5,99, 9, 6, 0},
                {13, 5, 7, 7, 7, 0,10, 5, 7, 7, 7,14},
                {0, 0, 0, 0, 10, 0, 0, 0,11,10, 0, 0},
                {0, 0, 10,12, 0, 0, 0,10, 0, 0, 0, 0},
                {13, 8, 8, 8, 6,10, 0, 8, 8, 8, 6,14},
                { 0, 5, 9,99, 6,13,14, 5, 9,88, 6, 0},
                {13, 5, 0, 9, 6, 0,10, 5,11, 9, 6,14},
                { 0, 5, 7, 7, 7, 0, 0, 5, 7, 7, 7, 0},
                { 0, 0,12, 0,12, 0, 0,12, 0,12, 0, 0}
            };
        }//SPIN_ZONE
        */

        /*
        if (name == EXCHANGE) {
            this.name = name;
            map = new int[][]{//EXCHANGE TEST
                    { 0, 2,11, 5,11, 2, 5,11, 2,11, 1,88},
                    { 3, 9, 0, 5, 0, 2, 5, 0, 2,13, 9, 3},
                    {13, 0,12, 5, 0, 2,15, 0, 2, 0, 0,14},
                    { 4, 4, 4, 4, 0,15, 5, 0,10, 4, 4, 4},
                    {13, 0, 0, 0,19, 2, 5,18, 0, 0, 0,14},
                    { 7,15, 7, 7, 7, 6, 0, 3, 3, 3,15, 0},
                    { 4, 4,15, 4, 4, 6, 0, 8, 8, 8,15, 8},
                    {13, 0, 0, 0,17, 6,15,16, 0, 0, 0,14},
                    { 3, 3, 3,10, 0, 6, 1, 0,10, 3, 3, 3},
                    {13,15, 0, 1, 0,15, 1, 0, 2, 0,11,14},
                    { 0, 0, 0, 1, 0, 6, 1, 0, 2, 0,14, 0},
                    {88, 2,12, 1,12, 6, 0,12, 2,12,15, 0}
            };
        }//EXCHANGE TEST
        */

        if (name == EXCHANGE) {
            this.name = name;
            map = new int[][]{//EXCHANGE ORIGINAL
                { 0, 2,11, 5,11, 2, 5,11, 2,11, 1,88},
                { 3, 9, 0, 5, 0, 2, 5, 0, 2,13, 9, 3},
                {13, 0,12, 5, 0, 2, 5, 0, 2, 0, 0,14},
                { 4, 4, 4, 4, 0, 2, 5, 0,10, 4, 4, 4},
                {13, 0, 0, 0,19, 2, 5,18, 0, 0, 0,14},
                { 3, 3, 3, 3, 3, 0, 0, 3, 3, 3, 3, 0},
                { 4, 4, 4, 4, 4, 0, 0, 8, 8, 8, 8, 8},
                {13, 0, 0, 0,17, 2, 1,16, 0, 0, 0,14},
                { 3, 3, 3,10, 0, 2, 1, 0,10, 3, 3, 3},
                {13,15, 0, 1, 0, 2, 1, 0, 2, 0,11,14},
                {0, 0, 0, 1, 0, 2, 1, 0, 2, 0, 14, 0},
                {88, 2,12, 1,12, 2, 0,12, 2,12,15, 0}
            };
        }//EXCHANGE

        if (name == ISLAND) {//ORIGINAL
            this.name = name;
            map = new int[][]{
                { 0, 0,11, 0,11, 0, 0,11, 0,11, 0,88},
                { 0,15,15, 0, 0, 0, 0, 0, 0,15,15, 0},
                {13,15, 9, 4, 4, 4, 4, 4, 4, 9,15,14},
                { 0, 0, 1,10, 3, 3, 3, 3,10, 2, 0, 0},
                {13, 0, 1, 2, 0, 0,15,15, 1, 2, 0,14},
                { 0, 0, 1, 2, 3, 3,99,15, 1, 2, 0, 0},
                { 0, 0, 1, 2,15, 0, 4, 4, 1, 2, 0, 0},
                {13, 0, 1, 2,15,15, 0, 0, 1, 2, 0,14},
                { 0, 0, 1,10, 4, 4, 4, 4,10, 2, 0, 0},
                {13,15, 3, 3, 3, 3, 3, 3, 3, 2,15,14},
                { 0,15,15, 0, 0, 0, 0, 0, 0,15,15, 0},
                { 0, 0,12, 0,12, 0, 0,12, 0,12, 0, 0}
            };
        }
        if (name == CHESS) {
            this.name = name;
            map = new int[][]{
                {88, 0,11, 0,11, 0, 0,11, 0,11, 0, 0},
                { 0, 8, 8, 8, 8, 8, 8, 8, 8, 8, 6, 0},
                {13, 5, 0, 2, 0, 2, 0, 2, 0, 2, 6,14},
                { 0, 5, 2, 0, 2, 0, 2, 0,15, 0, 6, 0},
                {13, 5, 0, 2, 0, 2, 0, 2, 0, 2, 6,14},
                { 0, 5, 2, 0,15, 0,99, 0, 2, 0, 6, 0},
                { 0, 5, 0, 1, 0,99, 0,15, 0, 1, 6, 0},
                {13, 5, 1, 0, 1, 0, 1, 0, 1, 0, 6,14},
                { 0, 5, 0, 1, 0,15, 0, 1, 0, 1, 6, 0},
                {13, 5, 1, 0, 1, 0, 1, 0, 1, 0, 6,14},
                { 0, 5, 7, 7, 7, 7, 7, 7, 7, 7, 7, 0},
                { 0, 0,12, 0,12, 0, 0,12, 0,12, 0,88}
            };
        }
        if (name == CROSS) {
            this.name = name;
            map = new int[][]{
                { 0, 2,11, 0,11, 2, 1,11,12,11, 1,88},
                { 0, 4, 4, 4, 4, 2, 1, 0, 0,17, 1, 3},
                {13, 0, 0, 0,12, 2, 1,13, 0,15, 0,14},
                { 0,19, 0,19, 2, 3, 1,18,12, 0,12, 0},
                {13,15,15, 2, 3,15, 1, 3, 0,12, 0,14},
                { 3, 3, 3, 3,15,15,15, 1, 3, 3, 3, 3},
                { 4, 4, 4, 4, 2,15, 4, 4, 4, 4, 4, 4},
                {13,12,11,12, 4, 2, 1,16, 0, 0,12,14},
                { 0, 0,13, 0,16, 2, 1,13,14,15, 0, 0},
                {13, 0,12, 0, 0, 2, 1, 0,13, 0, 0, 4},
                { 4, 2,15, 0, 0, 2, 1, 3, 3, 3, 3, 0},
                {15, 2,12, 0,12, 2, 1,19, 0,12, 1, 0}
            };
        }//CROSS
        if (name == BLAST_FURNACE) {
            this.name = name;
            map = new int[][]{
                { 0, 2, 0,12, 0,14, 1, 0, 2, 0, 0, 0},
                { 0, 4, 4, 4, 4, 4, 1, 0, 2,14, 2, 3},
                { 0,11, 0,12,11, 0,11,12, 2, 0, 2, 0},
                { 4, 4, 4, 4, 4, 4, 4, 4, 2,13, 2,13},
                { 0, 0, 0, 0,11, 0, 0,14, 2,14, 2, 0},
                {12, 4, 4, 4, 4, 2,88, 0, 2, 0, 2,12},
                { 4, 1, 0, 0, 0, 2,15,15, 2, 0, 4, 4},
                { 0, 0,11,99, 0, 4, 4, 4, 2,13, 0,15},
                { 3, 3, 3, 3, 0, 0, 0,11, 2, 3, 3, 3},
                { 0, 0,11, 1,13, 0, 0, 0, 2,11, 0, 0},
                { 4, 4, 4, 1,14, 0,15, 0, 2, 0,88, 4},
                {12, 0, 0, 1,12, 0, 1, 0, 2, 0, 1, 0}
            };
        }//BLAST_FURNACE
        if (name == LASER_MAZE) {
            this.name = name;
            map = new int[][]{
                {14, 2,11,88,11, 2, 1, 3, 3, 3, 3,15},
                { 3, 2,18, 0,14,15, 1,12,13,16, 1, 3},
                {16, 2,13,12, 0, 0, 1, 3,12, 0,13,14},
                { 0, 2, 0,14,12,18, 4, 1,12,12, 0,11},
                {13, 2,13,13,12,99, 1,13, 0,15, 3,14},
                { 3, 3, 0,12,12,14, 1,19, 0, 0, 1, 3},
                { 4, 2,13,12, 0, 0, 1, 0,19,14, 4, 4},
                {13 ,2, 0,12, 0,11, 1,18, 0,14, 1,14},
                { 0,15,13,12,14,14, 1, 3,12,15, 1, 0},
                {13,12, 0,12, 0, 0, 4, 1, 0,13, 1,14},
                { 4, 2,13,13,15, 2, 1,15, 0,14, 1, 4},
                {17, 2,12, 0,15, 2, 1,15,14,12, 1,88}
            };
        }//BLAST_FURNACE

        if (name == PIT_MAZE) {
            this.name = name;
            map = new int[][]{
                {15, 0,11, 0,11, 2, 1, 3, 0,12, 1,15},
                { 0,14, 0,15, 0, 2,15, 1, 0,15, 1, 3},
                {13, 0,88,12, 2, 3, 0, 1,18, 4, 1,14},
                {14,15,14,15, 2,15,11, 1,99, 1,15,13},
                {13, 0, 0, 2, 3, 0, 0, 1,15, 1, 3,14},
                { 3, 3, 3, 3,15,16,12, 1, 3,15, 1, 3},
                { 0, 0,15,12, 0,19,15,15, 1,15, 0, 4},
                {13,16, 0, 0, 0, 0, 0,11, 1, 3, 0,14},
                { 0, 0,15, 0,15,99,15, 0,15, 1,15, 0},
                {13,16, 0, 0, 2, 3, 3, 0, 0, 1, 0,14},
                { 4, 2,15,15, 2,15, 1,15,13, 1, 3, 0},
                {15, 2,12,11, 4, 2, 1,12, 0,12, 1,88}
            };
        }//PIT_MAZE

        if (name == MAELSTROM) {
            this.name = name;
            map = new int[][]{
                {88, 2,11, 0,11, 2, 1,11, 0, 0, 0, 0},
                { 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 3},
                {13, 8, 8, 8, 8, 8, 8, 8, 8, 6, 2,14},
                { 0, 5, 4, 4, 4, 4, 4, 4, 2, 6, 2, 0},
                {13, 5, 1, 8, 8, 8, 8, 6, 2, 6, 2,14},
                { 3, 5, 1, 5, 4,15,15, 6, 2, 6, 2, 3},
                { 8, 5, 1, 5, 1,15,15, 7, 2, 6, 2, 0},
                {13 ,5, 1, 5, 1, 3, 3, 3, 3, 6, 2,14},
                {99, 5, 1, 5, 7, 7, 7, 7, 7, 7, 2, 0},
                {13, 5, 1, 3, 3, 3, 3, 3, 3, 3, 3,14},
                { 8, 5, 7, 7, 7, 7, 7, 7, 7, 7, 7, 0},
                { 0, 0,12, 0,12, 0, 5,12, 0,12, 5,88}
            };
        }

    }//Boards constructor

    //getters and setters
    public int getName() {
        return name;
    }
    public int getBoard() {
        return board;
    }
    public void setBoard(int board) {
        this.board = board;
    }
    public boolean isCurrentBoard() {
        return currentBoard;
    }
    public void setCurrentBoard(boolean currentBoard) {
        this.currentBoard = currentBoard;
    }
    public int getDelay() {
        return delay;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }
    public int[][] getMap() {
        return map;
    }
    public int isGears() {
        return gears;
    }
    public void setGears(int gears) {
        this.gears = gears;
    }
    public int isRedBelts() {
        return redBelts;
    }
    public void setRedBelts(int redBelts) {
        this.redBelts = redBelts;
    }
    public int isBlueBelts() {
        return blueBelts;
    }
    public void setBlueBelts(int blueBelts) {
        this.blueBelts = blueBelts;
    }
    public boolean isBlueBeltsDone() {
        return blueBeltsDone;
    }
    public void setBlueBeltsDone(boolean blueBeltsDone) {
        this.blueBeltsDone = blueBeltsDone;
    }
    public int getBlueBeltPhase() {
        return blueBeltPhase;
    }
    public void setBlueBeltPhase(int blueBeltPhase) {
        this.blueBeltPhase = blueBeltPhase;
    }
    public int isLasers() {
        return lasers;
    }
    public void setLasers(int lasers) {
        this.lasers = lasers;
    }
    public int isPushers() {
        return pushers;
    }
    public void setPushers(int pushers) {
        this.pushers = pushers;
    }
    public int isFlamers() {
        return flamers;
    }
    public void setFlamers(int flamers) {
        this.flamers = flamers;
    }
    public boolean isGearsDone() {
        return gearsDone;
    }
    public void setGearsDone(boolean gearsDone) {
        this.gearsDone = gearsDone;
    }
    public boolean isRedBeltsDone() {
        return redBeltsDone;
    }
    public void setRedBeltsDone(boolean redBeltsDone) {
        this.redBeltsDone = redBeltsDone;
    }
    public boolean isLasersDone() {
        return lasersDone;
    }
    public void setLasersDone(boolean lasersDone) {
        this.lasersDone = lasersDone;
    }
    public boolean isPushersDone() {
        return pushersDone;
    }
    public void setPushersDone(boolean pushersDone) {
        this.pushersDone = pushersDone;
    }
    public boolean isFlamersDone() {
        return flamersDone;
    }
    public void setFlamersDone(boolean flamersDone) {
        this.flamersDone = flamersDone;
    }
}//class boards
