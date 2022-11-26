package com.mygdx.game;

public class GameState {
    private boolean newGame = true;
    private int turn = 0;
    private boolean pause = false;
    private int debugMenu = -1;
    private boolean title = true;
    //private int board = -1;
    private boolean selectBoard = false;
    private int boardSelectPage = 1;
    private boolean selectCards = false;
    private int noOfFlagsPlaced = 0;
    private boolean dealCards = false;
    private int registryPhase = 0;
    private  boolean selectRobot = false;
    private boolean selectStartingDir = false;
    private boolean gameOn = false;
    //private boolean gears = false;

    //getters & setters
    public boolean isNewGame() {
        return newGame;
    }
    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }
    public int getTurn() {
        return turn;
    }
    public void setTurn(int turn) {
        this.turn = turn;
    }
    public boolean isTile() {
        return title;
    }
    public boolean isPause() {
        return pause;
    }
    public void setPause(boolean pause) {
        this.pause = pause;
    }
    public boolean isTitle() {
        return title;
    }
    public void setTitle(boolean title) {
        this.title = title;
    }
    public boolean isSelectRobot() {
        return selectRobot;
    }
    public void setSelectRobot(boolean selectRobot) {
        this.selectRobot = selectRobot;
    }
    public boolean isSelectBoard() {
        return selectBoard;
    }
    public void setSelectBoard(boolean selectBoard) {
        this.selectBoard = selectBoard;
    }
    public int getBoardSelectPage() {
        return boardSelectPage;
    }
    public void setBoardSelectPage(int boardSelectPage) {
        this.boardSelectPage = boardSelectPage;
    }
    public boolean isSelectStartingDir() {
        return selectStartingDir;
    }
    public void setSelectStartingDir(boolean selectStartingDir) {
        this.selectStartingDir = selectStartingDir;
    }
    public boolean isSelectCards() {
        return selectCards;
    }
    public void setSelectCards(boolean selectCards) {
        this.selectCards = selectCards;
    }

    /*
        public boolean isPlaceFlags() {
            return placeFlags;
        }
        public void setPlaceFlags(boolean placeFlags) {
            this.placeFlags = placeFlags;
        }
        */
    public int getNoOfFlagsPlaced() {
        return noOfFlagsPlaced;
    }
    public void setNoOfFlagsPlaced(int noOfFlagsPlaced) {
        this.noOfFlagsPlaced = noOfFlagsPlaced;
    }
    public boolean isGameOn() {
        return gameOn;
    }
    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }
    public boolean isDealCards() {
        return dealCards;
    }
    public void setDealCards(boolean dealCards) {
        this.dealCards = dealCards;
    }
    public int getRegistryPhase() {
        return registryPhase;
    }
    public void setRegistryPhase(int registryPhase) {
        this.registryPhase = registryPhase;
    }
    /*
    public boolean isGears() {
        return gears;
    }
    public void setGears(boolean gears) {
        this.gears = gears;
    }
    */
    public int getDebugMenu() {
        return debugMenu;
    }
    public void setDebugMenu(int debugMenu) {
        this.debugMenu = debugMenu;
    }
}
