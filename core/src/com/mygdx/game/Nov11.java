/*
Build: Current build Nov 21 19:24
Bugs:
board lasers: some lasers not working on CHOP_SHOP board
board lasers: robot getting zapped standing to the right of laserpoint

To Do:
fix pause, animation problems
create new game and menu options
end game method
 */
package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.Input.Keys.*;
import static com.mygdx.game.Constants.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import jdk.vm.ci.amd64.AMD64;

import java.util.Random;

public class Nov11 extends ApplicationAdapter {
	private int mouseX, mouseY;
	//used for belt animation

	//game speeds
	private float robotMoveIncrement = 1f;//slow - 0.5f
	private int robotTurnIncrement = 2;
	private int gearTurnIncrement = 2;
	private final int laserSpeed = 1;
	private float beltSpeed = 0.08f;//1f
	private float blueBeltAnimationSpeed = beltSpeed;
	private float redBeltAnimationSpeed = beltSpeed;
	private float pusherAnimationSpeed = 0.1f;
	private float flamerAnimationSpeed = 0.1f;
	//private float boardLaserAnimationSpeed = 0.2f;
	private float zapAnimationSpeed = 0.1f;
	private int blueFrame = 0;
	private int zapFrame = 0;
	private float blankAnimationSpeed = 2f;

	//public Sprite tmpCard;
	//private boolean waitKey = false;
	private static final int FRAME_ROWS = 1;
	private static final int BLUE_FRAME_COLS = 13;//25
	private static final int RED_FRAME_COLS = 13;
	private static final int PUSHER_FRAME_COLS = 23;
	private static final int FLAMER_FRAME_COLS = 10;
	private static final int ZAP_FRAME_COLS = 12;
	private static final int BLANK_FRAME_COLS = 13;
	Animation<TextureRegion> redBeltAnimation;
	Animation<TextureRegion> blueBeltAnimation;
	Animation<TextureRegion> pusherAnimation;
	Animation<TextureRegion> flamerAnimation;
	Animation<TextureRegion> zapAnimation;
	Animation<TextureRegion> blankAnimation;
	Texture redBeltSheet;
	Texture blueBeltSheet;
	ShapeRenderer laserRend;
	Texture pusherSheet;
	Texture flamerSheet;
	Texture zapSheet;
	Texture blankSheet;
	float stateTime;
	private BitmapFont font;
	private Robots[] robo = new Robots[8];
	private ArrayList<Buttons> buttonList = new ArrayList<>();
	private ArrayList<Boards> boardList = new ArrayList<Boards>(6);
	private ArrayList<Robots> roboList = new ArrayList<>(8);
	private ArrayList<Robots> roboActiveList = new ArrayList<>(8);
	private ArrayList<Flags> flagList = new ArrayList<>(6);
	private ArrayList<Tiles> tileList = new ArrayList<>(144);
	private ArrayList<Cards> cardList = new ArrayList<>(84);
	private ArrayList<Pushers> pusherList = new ArrayList<>(40);
	private ArrayList<Flamers> flamerList = new ArrayList<>(40);
	private ArrayList<Lasers> laserList = new ArrayList<>(40);
	private boolean[] cardSlot = new boolean[5];
	GameState gameState = new GameState();
	//Boards board = new Boards();
	private int tileMap[][];//create boards
	private SpriteBatch batch;

	private Texture img;
	private Texture img_stars;
	private Texture img_cover;
	private Texture img_title;
	private Sprite img_typeButton;
	private Sprite img_laserPointUp;
	private Sprite img_laserPointDown;
	private Sprite img_laserPointLeft;
	private Sprite img_laserPointRight;
	private Sprite img_damage;
	private Sprite img_damage_red;
	private Sprite img_blackBox;
	private Sprite img_options;
	private Sound bounce;
	private Sound zap2;

	@Override
	public void create () {
		batch = new SpriteBatch();

		batch.setBlendFunction(gl.GL_SRC_ALPHA, gl.GL_ONE_MINUS_SRC_ALPHA);//not working
		batch.enableBlending();//not working

		laserRend = new ShapeRenderer();
		blueBeltSheet = new Texture(Gdx.files.internal("tiles\\blue_belt_up_anim.png"));
		redBeltSheet = new Texture(Gdx.files.internal("tiles\\red_belt_up_anim.png"));
		pusherSheet = new Texture(Gdx.files.internal("tiles\\pusher_up_anim.png"));
		flamerSheet = new Texture(Gdx.files.internal("tiles\\flamer_up_anim.png"));
		zapSheet = new Texture(Gdx.files.internal("misc\\zap_anim.png"));
		blankSheet = new Texture(Gdx.files.internal("tiles\\blank_anim.png"));


		TextureRegion[][] blue = TextureRegion.split(blueBeltSheet,
				blueBeltSheet.getWidth() / BLUE_FRAME_COLS,
				blueBeltSheet.getHeight() / FRAME_ROWS);

		TextureRegion[][] red = TextureRegion.split(redBeltSheet,
				redBeltSheet.getWidth() / RED_FRAME_COLS,
				redBeltSheet.getHeight() / FRAME_ROWS);

		TextureRegion[][] pusher = TextureRegion.split(pusherSheet,
				pusherSheet.getWidth() / PUSHER_FRAME_COLS,
				pusherSheet.getHeight() / FRAME_ROWS);

		TextureRegion[][] flamer = TextureRegion.split(flamerSheet,
				flamerSheet.getWidth() / FLAMER_FRAME_COLS,
				flamerSheet.getHeight() / FRAME_ROWS);

		TextureRegion[][] zap = TextureRegion.split(zapSheet,
				zapSheet.getWidth() / ZAP_FRAME_COLS,
				zapSheet.getHeight() / FRAME_ROWS);

		TextureRegion[][] blank = TextureRegion.split(blankSheet,
				blankSheet.getWidth() / BLANK_FRAME_COLS,
				blankSheet.getHeight() / FRAME_ROWS);


		TextureRegion[] blueBeltFrames = new TextureRegion[BLUE_FRAME_COLS * FRAME_ROWS];
		int blueIndex = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < BLUE_FRAME_COLS; j++) {
				blueBeltFrames[blueIndex++] = blue[i][j];
			}
		}

		TextureRegion[] redBeltFrames = new TextureRegion[RED_FRAME_COLS * FRAME_ROWS];
		int redIndex = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < RED_FRAME_COLS; j++) {
				redBeltFrames[redIndex++] = red[i][j];
			}
		}

		TextureRegion[] pusherFrames = new TextureRegion[PUSHER_FRAME_COLS * FRAME_ROWS];
		int pusherIndex = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < PUSHER_FRAME_COLS; j++) {
				pusherFrames[pusherIndex++] = pusher[i][j];
			}
		}

		TextureRegion[] flamerFrames = new TextureRegion[FLAMER_FRAME_COLS * FRAME_ROWS];
		int flamerIndex = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FLAMER_FRAME_COLS; j++) {
				flamerFrames[flamerIndex++] = flamer[i][j];
			}
		}

		TextureRegion[] zapFrames = new TextureRegion[ZAP_FRAME_COLS * FRAME_ROWS];
		int zapIndex = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < ZAP_FRAME_COLS; j++) {
				zapFrames[zapIndex++] = zap[i][j];
			}
		}

		TextureRegion[] blankFrames = new TextureRegion[BLANK_FRAME_COLS * FRAME_ROWS];
		int blankIndex = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < BLANK_FRAME_COLS; j++) {
				blankFrames[blankIndex++] = blank[i][j];
			}
		}

		blueBeltAnimation = new Animation<TextureRegion>(blueBeltAnimationSpeed, blueBeltFrames);
		redBeltAnimation = new Animation<TextureRegion>(redBeltAnimationSpeed, redBeltFrames);
		pusherAnimation = new Animation<TextureRegion>(pusherAnimationSpeed, pusherFrames);
		flamerAnimation = new Animation<TextureRegion>(flamerAnimationSpeed, flamerFrames);
		zapAnimation = new Animation<TextureRegion>(zapAnimationSpeed, zapFrames);
		blankAnimation = new Animation<TextureRegion>(blankAnimationSpeed, blankFrames);

		font = new BitmapFont();
		img_stars = new Texture(Gdx.files.internal("misc\\stars.png"));
		img_cover = new Texture(Gdx.files.internal("misc\\cover.png"));
		img_title = new Texture(Gdx.files.internal("misc\\title.png"));
		img_laserPointUp = new Sprite(new Texture(Gdx.files.internal("tiles\\laserpointup.png")));
		img_laserPointDown = new Sprite(new Texture(Gdx.files.internal("tiles\\laserpointdown.png")));
		img_laserPointLeft = new Sprite(new Texture(Gdx.files.internal("tiles\\laserpointleft.png")));
		img_laserPointRight = new Sprite(new Texture(Gdx.files.internal("tiles\\laserpointright.png")));
		img_damage = new Sprite(new Texture(Gdx.files.internal("damage.png")));
		img_damage_red = new Sprite(new Texture(Gdx.files.internal("damager.png")));
		img_blackBox = new Sprite(new Texture(Gdx.files.internal("misc\\blackbox.png")));
		img_options = new Sprite(new Texture(Gdx.files.internal("misc\\options.png")));

		//sound
		bounce = Gdx.audio.newSound(Gdx.files.internal("sounds\\bounce.wav"));
		zap2 = Gdx.audio.newSound(Gdx.files.internal("sounds\\zap.wav"));

		stateTime = 0f;
	}//create
	@Override
	public void render() {
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (gameState.isNewGame()) {
			newGame();
		}
		if (gameState.isTitle()) {
			title();
		}
		if (gameState.isSelectBoard()) {
			boardSelectMenu();
		}
		if (gameState.isSelectRobot()) {
			robotSelectMenu();
		}
		//drawCards();
		if (gameState.isGameOn()) {//main draw loop
			//main render loop
			drawBoard();
			drawFlags();
			for (Boards bd : boardList) {//draw inactive blue lasers
				if (bd.isLasers()!=NONE) {
					drawLasers();
				}
			}
			drawCards();
			drawGUI();

			if(gameState.isPause()) {
				font.getData().setScale(4.0f,4.0f);
				font.setColor(Color.PURPLE);
				batch.begin();
				font.draw(batch,"PAUSE", 920, 50);
				batch.end();
			}

			//activate board elements
			if((!gameState.isPause()) && (!anyRobotsActive())) {
				//animate blue belts
				for (Boards bd : boardList) {
					if (bd.isBlueBelts() == ON) {
						moveBlueBelts();
					}
				}
				//animate red belts
				for (Boards bd : boardList) {
					if (bd.isRedBelts() == ON) {
						moveRedBelts();
					}
				}
				//animate pushers
				for (Boards bd : boardList) {
					if (bd.isPushers() == ON) {
						movePushers();
					}
				}
				//animate gears
				for (Boards bd : boardList) {
					if (bd.isGears() == ON) {
						moveGears();
					}
				}
				//animate flamers
				for (Boards bd : boardList) {
					if (bd.isFlamers() == ON) {
						moveFlamers();
					}
				}
				//animate lasers
				for (Boards bd : boardList) {
					if (bd.isLasers() == ON) {
						fireLasers();
					}
				}
				//animate robots getting zapped
				for(Robots r : roboList) {
					if(r.isDrawZap()) {
						drawZap();
					}
				}
			}//activate board elements

			drawRobots();

			//deal cards
			if (gameState.isDealCards()) {
				detectRobotTile();
				dealCards();
			}
			//CPU direction and card selection
			for (Robots r : roboList) {
				if (r.getType() == CPU) {
					if (!r.isDirSelected()) {
						selectStartingDirCPU();
					} else {
						getAIscores();
						selectCardsCPU();
					}
				}
			}
			//player controlled robot selects starting direction
			if (gameState.isSelectStartingDir()) {
				selectStartingDir();
			}
			//player controlled robot selects cards
			if (gameState.isSelectCards()) {
				selectCards();
			}
			//activate player robot if direction is not set
			if ((!anyRobotsActive())) {
				for (Robots r : roboList) {
					if ((r.getType() == ON) && (!r.isDirSelected())) {
						r.setActive(true);
						gameState.setSelectStartingDir(true);
						break;
					}
				}
			}
			//activate player robot if direction is selected but is not programmed
			if (!anyRobotsActive()) {
				for (Robots r : roboList) {
					if ((r.getType() == ON) && (r.isDirSelected())) {
						if (!r.getCardsSelected()) {
							r.setActive(true);
							gameState.setSelectCards(true);
							break;
						}
					}
				}
			}
			//bounce robot off wall
			if (!gameState.isPause()) {
				for (Robots r : roboList) {
					if((r.getMoveType()==BOUNCE_FORWARD) || (r.getMoveType()==BOUNCE_BACK)) {
						bounce();
					}
				}
			}
			//move robots forward and back
			if (!gameState.isPause()){
				for (Robots r : roboList) {
					if (!r.isFalling()) {
						if (r.getMoveType() == MOVE) {
							moveRobot(r.getName());
						}
					}
				}
			}
			//turn robots left, right, u turn
			if (!gameState.isPause()) {
				for (Robots r : roboList) {
					if (!r.isFalling()) {
						if ( (r.getTurnAmount() != 0)) {//if ((r.getActive()) && (r.getTurnAmount() != 0)) {
							turnRobot(r.getName());
						}
					}
				}
			}
			//shrink falling robot
			if (!gameState.isPause()) {
				for (Robots r : roboList) {
					if (r.isFalling()) {
						robotFalling();
					}
				}
			}
			//activate blue belts
			if((allCardsPlayed()) && (!anyRobotsActive()) && (allRobotsDoneProgramming())) {
				for(Boards bd : boardList) {
					if ((bd.isBlueBelts() != NONE) && (!bd.isBlueBeltsDone())) {
						bd.setBlueBelts(ON);
					}
				}
			}
			//activate red belts
			if ((allCardsPlayed()) && (!anyRobotsActive()) && (allRobotsDoneProgramming())) {
				for (Boards bd : boardList) {
					if ((bd.isBlueBeltsDone()) && (!bd.isRedBeltsDone())) {
						bd.setRedBelts(ON);
					}
				}
			}
			//activate pushers
			if ((allCardsPlayed()) && (!anyRobotsActive()) && (allRobotsDoneProgramming())) {
				for (Boards bd : boardList) {
					if((bd.isBlueBeltsDone()) && (bd.isRedBeltsDone())) {
						if (!bd.isPushersDone()) {
							bd.setPushers(ON);
						}
					}
				}
			}
			//activate gears
			if ((allCardsPlayed()) && (!anyRobotsActive()) && (allRobotsDoneProgramming())) {
				for (Boards bd : boardList) {
					if((bd.isBlueBeltsDone()) && (bd.isRedBeltsDone()) && ((bd.isPushersDone()))) {
						if (!bd.isGearsDone()) {
							bd.setGears(ON);
						}
					}
				}
			}
			//activate flamers
			if ((allCardsPlayed()) && (!anyRobotsActive()) && (allRobotsDoneProgramming())) {
				for (Boards bd : boardList) {
					if((bd.isBlueBeltsDone()) && (bd.isRedBeltsDone()) && ((bd.isPushersDone())  && (bd.isGearsDone()))) {
						if (!bd.isFlamersDone()) {
							bd.setFlamers(ON);
						}
					}
				}
			}
			//activate board lasers
			if ((allCardsPlayed()) && (!anyRobotsActive()) && (allRobotsDoneProgramming())) {
				for (Boards bd : boardList) {
					if((bd.isBlueBeltsDone()) && (bd.isRedBeltsDone()) && (bd.isPushersDone())) {
						if((bd.isGearsDone()) && (bd.isFlamersDone())) {
							if (!bd.isLasersDone()) {
								bd.setLasers(ON);
							}
						}
					}
				}
			}

			//TEST - XXX
			/*
			for(Robots r : roboList) {
				if(r.getName().equalsIgnoreCase("Twonky")) {
					r.setDrawZap(true);
				}
				drawZap();
			}
			*/
			//TEST - XXX

			//activate next card and robot
			if ((!anyRobotsActive()) && (allRobotsDoneProgramming()) && (!animationPlaying())) {
				activateNextCardAndRobot();
			}

			//increase registry phase
			if ((allCardsPlayed()) && (!anyRobotsActive()) && (allRobotsDoneProgramming()) && (allBoardElementsDone())) {
				if (gameState.getRegistryPhase() < 5) {
					gameState.setRegistryPhase(gameState.getRegistryPhase() + 1);
					for (Boards bd : boardList) {
						if (bd.isBlueBelts() != NONE) {
							bd.setBlueBeltsDone(false);
						}
						if (bd.isRedBelts() != NONE) {
							bd.setRedBeltsDone(false);
						}
						if (bd.isPushers() != NONE) {
							bd.setPushersDone(false);
						}
						if (bd.isGears() != NONE) {
							bd.setGearsDone(false);
						}
						if (bd.isLasers() != NONE) {
							bd.setLasersDone(false);
						}
						if (bd.isFlamers() != NONE) {
							bd.setFlamersDone(false);
						}
					}
				}
			}
			//reset registry phase to 0
			boolean resetRegistryPhase = true;
			if ((gameState.getRegistryPhase() == 5) && (allBoardElementsDone())) {
				for (Cards c : cardList) {//check all cards have been played
					if (c.getPickedOrder() == 4) {
						if (!c.getPlayed()) {
							resetRegistryPhase = false;
						}
					}
				}
			}
			//end turn sequence, reset reg_phase to 0, cards and robots
			if ((gameState.getRegistryPhase() == 5) && (resetRegistryPhase)) {
				endTurn();
				gameState.setRegistryPhase(0);
				cardList.clear();
				for (int c = 84; c >= 1; c--) {//5
					cardList.add(new Cards(c));
				}
				for (Robots r : roboList) {
					r.setCardsHeld(0);
					r.setCardsSelected(false);
				}
				gameState.setDealCards(true);
			}
		}//(gameState.isGameOn())

		if (gameState.getDebugMenu() == CARD_DEBUG) {
			cardDebugMenu();
		}
		if (gameState.getDebugMenu() == GAME_DEBUG) {
			gameDebugMenu();
		}
		if (gameState.getDebugMenu() == FLAG_DEBUG) {
			flagDebugMenu();
		}
		if (gameState.getDebugMenu() == ROBOT_DEBUG) {
			robotDebugMenu();
		}
		if ((gameState.getDebugMenu() >= 4) && (gameState.getDebugMenu() <= 40)) {
			switch (gameState.getDebugMenu()) {
				case TWONKY_DEBUG:
					singleRobotDebug("Twonky");
					break;
				case TWONKY_CARD_DEBUG:
					singleRobotCardDebug("Twonky");
					break;
				case SQUASHBOT_DEBUG:
					singleRobotDebug("SquashBot");
					break;
				case SQUASHBOT_CARD_DEBUG:
					singleRobotCardDebug("SquashBot");
					break;
				case TWITCH_DEBUG:
					singleRobotDebug("Twitch");
					break;
				case TWITCH_CARD_DEBUG:
					singleRobotCardDebug("Twitch");
					break;
				case ZOOMBOT_DEBUG:
					singleRobotDebug("ZoomBot");
					break;
				case ZOOMBOT_CARD_DEBUG:
					singleRobotCardDebug("ZoomBot");
					break;
				case HAMMERBOT_DEBUG:
					singleRobotDebug("HammerBot");
					break;
				case HAMMERBOT_CARD_DEBUG:
					singleRobotCardDebug("HammerBot");
					break;
				case SPINBOT_DEBUG:
					singleRobotDebug("SpinBot");
					break;
				case SPINBOT_CARD_DEBUG:
					singleRobotCardDebug("SpinBot");
					break;
				case HULK_DEBUG:
					singleRobotDebug("Hulk X90");
					break;
				case HULK_CARD_DEBUG:
					singleRobotCardDebug("Hulk X90");
					break;
				case TRUNDLEBOT_DEBUG:
					singleRobotDebug("TrundleBot");
					break;
				case TRUNDLEBOT_CARD_DEBUG:
					singleRobotCardDebug("TrundleBot");
					break;
			}
		}//if (!gameState.isPause()) {

		if (gameState.getDebugMenu() == GRID_DEBUG) {
			showGrid();
		}
		if (gameState.getDebugMenu() == AI_DEBUG) {
			AIdebug();
		}
		getInput();
		showMouseXY();
	}//render
	public void newGame() {
		//create buttons
		for(int b = 6; b <= 20; b++) {//15
			buttonList.add(new Buttons(b));
		}

		for(int b = 100; b <= 111; b++) {//15
			buttonList.add(new Buttons(b));
		}

		buttonList.add(new Buttons(120));
		buttonList.add(new Buttons(121));

		boardList.add(new Boards(TUTORIAL));
		boardList.add(new Boards(VAULT));
		boardList.add(new Boards(CHOP_SHOP));
		boardList.add(new Boards(SPIN_ZONE));
		boardList.add(new Boards(EXCHANGE));
		boardList.add(new Boards(ISLAND));

		boardList.add(new Boards(CHESS));
		boardList.add(new Boards(CROSS));
		boardList.add(new Boards(BLAST_FURNACE));
		boardList.add(new Boards(LASER_MAZE));
		boardList.add(new Boards(PIT_MAZE));
		boardList.add(new Boards(MAELSTROM));

		//create Robots
		for(int r = 0; r <= 7; r++) {
			roboList.add(new Robots(r+1));
		}
		//create cards
		for(int c = 84; c >= 1; c--) {//5
			cardList.add(new Cards(c));
		}
		gameState.setNewGame(false);
	}//newGame
	public void delay() {
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentBlankFrame = blankAnimation.getKeyFrame(stateTime, false);
		batch.begin();
		batch.draw(currentBlankFrame, 1100, 700, 30, 30, 60, 60, 1.0f, 1.0f, 0, true);
		batch.end();

		//end pusher animation
		if(blankAnimation.isAnimationFinished(stateTime)){
			for(Boards b : boardList) {
				b.setDelay(DONE);
			}
			stateTime = 0f;
		}
	}
	public void title() {
		//ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(img_stars, 0, 0);
		batch.draw(img_cover, 179, 0);
		batch.draw(img_title, 290, 640);
		//batch.draw(img_blackBox, 800,400);
		batch.end();
		if (Gdx.input.isButtonJustPressed(0)) {
			mouseX = 0;mouseY = 0;
			gameState.setSelectBoard(true);
			gameState.setTitle(false);
		}

		/*
		ShapeRenderer laserRend = new ShapeRenderer();
		batch.begin();
		laserRend.setColor(Color.PINK);
		laserRend.begin(ShapeRenderer.ShapeType.Filled);
		laserRend.rectLine(0f, 0f, 1280f, 800f, 4f);
		laserRend.end();
		batch.end();
		*/

		/*
		for(Lasers l : laserList) {
			l.setY(SCREEN_H - l.getY());
			l.setY2(SCREEN_H - l.getY2());
		}

		for(Lasers l : laserList) {
			ShapeRenderer laserRend = new ShapeRenderer();
			batch.begin();
			laserRend.setColor(Color.RED);
			laserRend.begin(ShapeRenderer.ShapeType.Filled);
			laserRend.rectLine(l.getX()*60, l.getY()*60+10, l.getX2()*60, l.getY2()*60+10, 4f);
			laserRend.end();
			batch.end();

		}
		*/


	}//title
	public void boardSelectMenu () {
		ScreenUtils.clear(0, 0, 0, 1);
		font.getData().setScale(3.0f,3.0f);

		batch.begin();
		batch.draw(img_stars, 0, 0);
		font.draw(batch,"SELECT BOARD",470,770);
		batch.end();

		//temp variables for different pages
		int boardType = -1;
		int buttonType = -1;
		if(gameState.getBoardSelectPage() == 1) {
			boardType = BUTTON_BOARD1;
			buttonType = BUTTON_BOARD_BUTTON1;
		}
		if(gameState.getBoardSelectPage() == 2) {
			boardType = BUTTON_BOARD2;
			buttonType = BUTTON_BOARD_BUTTON2;
		}
		//draw boards/board names
		font.setColor(com.badlogic.gdx.graphics.Color.BLUE);
		for (Buttons b : buttonList) {
			if ((b.getType() == boardType) || (b.getType() == buttonType)) {
				batch.begin();
				font.draw(batch, b.getName(), b.getTextX(), b.getTextY());
				batch.draw(b.getPic(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
				batch.end();
			}
		}
		//next/previous page buttons
		if (Gdx.input.isButtonJustPressed(0)) {
			for (Buttons b : buttonList) {
				if (b.getType() == buttonType) {
					if ((mouseX > b.getxMin()) && (mouseX < b.getxMax())) {
						if ((mouseY > b.getyMin()) && (mouseY < b.getyMax())) {
							//gameState.setBoardSelectPage(2);
							switch(gameState.getBoardSelectPage()) {
								case 1:
									gameState.setBoardSelectPage(2);
									break;
								case 2:
									gameState.setBoardSelectPage(1);
									break;
							}
						}
					}
				}
			}
		}

		//get player input for board selection
		int boardSelected = -1;
		int page = -1;
		if(gameState.getBoardSelectPage() == 1) {
			page = BUTTON_BOARD1;
		} else if (gameState.getBoardSelectPage() == 2) {
			page = BUTTON_BOARD2;
		}
		if (Gdx.input.isButtonJustPressed(0)) {
			for (Buttons b : buttonList) {
				if(b.getType() == page) {
					if ((mouseX > b.getxMin()) && (mouseX < b.getxMax())) {
						if ((mouseY > b.getyMin()) && (mouseY < b.getyMax())) {
							boardSelected = b.getNo();
							for(Boards bd : boardList) {
								if(bd.getName() == boardSelected) {
									bd.setBoard(bd.getName());
									bd.setCurrentBoard(true);
									tileMap = bd.getMap();
									break;
								}
							}
						}
					}
				}
			}
		}

		//end board selection
		if(boardSelected != -1) {
			mouseX = 0;
			mouseY = 0;
			gameState.setSelectRobot(true);
			gameState.setSelectBoard(false);
		}
	}//boardSelectMenu
	public void robotSelectMenu() {
		ScreenUtils.clear(0, 0, 0, 1);
		font.setColor(Color.PURPLE);
		font.getData().setScale(3.0f,3.0f);

		batch.begin();
		batch.draw(img_stars, 0, 0);
		font.draw(batch,"SELECT PLAYERS",460,770);
		//draw robots and print robot names



		for(Robots r : roboList) {
			batch.draw(r.getPic(), r.getX(), r.getY(), 95.0f, 95.0f);//draw robots
			font.setColor(r.getColor());
			font.draw(batch, r.getName(), r.getX() + 140, r.getY() + 70);//print robot name
		}
		//draw robot type buttons
		for(Buttons b : buttonList) {
			if(b.getType() == b.BUTTON_ROBOT_TYPE_BOX) {
				batch.draw(b.getPic(), b.getX(), b.getY());//draw button
				for(Robots r: roboList) {//change button text color according to robo type
					if(b.getName().equals(r.getName())) {
						switch(r.getType()) {
							case 0:
								font.setColor(Color.RED);
								break;
							case 1:
								font.setColor(Color.GREEN);
								break;
							case 2:
								font.setColor(Color.BLUE);
								break;
						}
						font.draw(batch, b.getText(), b.getTextX() + 60, b.getTextY());//draw button type name
					}
				}//robo loop
			}
		}//button loop
		batch.end();

		//get player input for robot selection
		if (Gdx.input.isButtonJustPressed(0)) {
			for (Buttons b : buttonList) {
				if(b.getType() == b.BUTTON_ROBOT_TYPE_BOX) {
					if ((mouseX > b.getxMin()) && (mouseX < b.getxMax())) {
						if ((mouseY > b.getyMin()) && (mouseY < b.getyMax())) {
							for(Robots r : roboList) {
								if(b.getName().equals(r.getName())) {
									switch(r.getType()) {
										case 0:
											b.setText("ON");
											r.setType(1);
											break;
										case 1:
											b.setText("CPU");
											r.setType(2);
											break;
										case 2:
											b.setText("OFF");
											r.setType(0);
											break;
									}
								}
							}
						}
					}
				}
			}
		}//END - get player input for robot selection

		//draw back and start buttons
		for(Buttons b : buttonList) {
			if((b.getType() == b.BUTTON_START || b.getType() == b.BUTTON_BACK)) {
				batch.begin();
				batch.draw(b.getPic(), b.getX(), b.getY(), 180f, 100f);
				font.setColor(Color.GREEN);
				font.draw(batch, b.getText(), b.getTextX(), b.getTextY());
				batch.end();
			}
		}
		//back and start button input
		if(Gdx.input.isButtonJustPressed(0)) {
			for(Buttons b:buttonList) {
				if(Gdx.input.getX() > b.getxMin() && (Gdx.input.getX() < b.getxMax())) {
					if(Gdx.input.getY() > b.getyMin() && (Gdx.input.getY() < b.getyMax())) {
						if ((b.getType() == b.BUTTON_BACK)) {
							for (Boards bd : boardList) {
								bd.setCurrentBoard(false);
								bd.setBoard(-1);
							}
							//tileList.
							mouseX = 0;
							mouseY = 0;
							gameState.setSelectBoard(true);
							gameState.setSelectRobot(false);
						}
						if ((b.getType() == b.BUTTON_START)) {
							//remove non-playing robots
							for (Robots r : roboList) {//copy roboList into temporary roboActiveList
								if (r.getType() != OFF) {
									roboActiveList.add(r);
								}
							}
							roboList.clear();
							roboList = roboActiveList;
							createBoard();
						}
					}
				}
			}
		}
	}//robotSelectMenu
	public void createBoard() {
		//remove all non used boards
		ArrayList<Boards> boardCurrentList = new ArrayList<>(8);
		for(Boards bd : boardList) {
			if(bd.isCurrentBoard()) {
				boardCurrentList.add(bd);
			}
		}
		boardList.clear();
		boardList = boardCurrentList;

		//read selected board into tileMap array
		for (int y = 0; y < tileMap[0].length; y++) {
			for (int x = 0; x < tileMap.length; x++) {
				tileList.add(new Tiles(x, y, tileMap[x][y]));
			}//y loop
		}//x loop

		//add existing board elements
		for (Boards bd : boardList) {
			for (Tiles t : tileList) {
				if ((t.getType() >= 1) && (t.getType() <= 4)) {
					bd.setRedBelts(OFF);
					bd.setRedBeltsDone(false);
				}
				if ((t.getType() >= 5) && (t.getType() <= 8)) {
					bd.setBlueBelts(OFF);
					bd.setBlueBeltsDone(false);
				}
				if ((t.getType() >= 9) && (t.getType() <= 10)) {
					bd.setGears(OFF);
					bd.setGearsDone(false);
				}
			}
		}
		//add pushers
		for (Boards bd : boardList) {
			if (bd.getName() == VAULT) {
				bd.setPushers(OFF);
				bd.setPushersDone(false);
				pusherList.add(new Pushers(6, 2, LEFT1));
				pusherList.add(new Pushers(6, 3, DOWN1));
				pusherList.add(new Pushers(10, 6, LEFT1));
				pusherList.add(new Pushers(3, 7, RIGHT1));
				pusherList.add(new Pushers(10, 7, LEFT1));
				pusherList.add(new Pushers(7, 10, UP1));
			}
		}
		for (Boards bd : boardList) {
			if (bd.getName() == BLAST_FURNACE) {
				bd.setPushers(OFF);
				bd.setPushersDone(false);
				pusherList.add(new Pushers(4, 2, UP1));
				pusherList.add(new Pushers(10, 2, RIGHT1));
				pusherList.add(new Pushers(2, 3, UP1));
				pusherList.add(new Pushers(5, 4, DOWN1));
				pusherList.add(new Pushers(8, 4, UP1));
				pusherList.add(new Pushers(9, 5, LEFT1));
				pusherList.add(new Pushers(11, 4, RIGHT1));
				pusherList.add(new Pushers(7, 3, UP1));
				pusherList.add(new Pushers(8, 8, DOWN1));
				pusherList.add(new Pushers(3, 9, DOWN1));
				pusherList.add(new Pushers(10, 9, DOWN1));
				pusherList.add(new Pushers(4, 10, RIGHT1));
				pusherList.add(new Pushers(5, 12, DOWN1));
			}
		}
		for (Boards bd : boardList) {
			if (bd.getName() == LASER_MAZE) {
				bd.setPushers(OFF);
				bd.setPushersDone(false);
				pusherList.add(new Pushers(2, 2, RIGHT1));
				pusherList.add(new Pushers(11, 11, LEFT1));
				pusherList.add(new Pushers(9, 12, RIGHT1));
			}
		}
		for (Boards bd : boardList) {
			if (bd.getName() == MAELSTROM) {
				bd.setPushers(OFF);
				bd.setPushersDone(false);
				pusherList.add(new Pushers(3, 1, UP1));
				pusherList.add(new Pushers(5, 1, UP1));
				pusherList.add(new Pushers(8, 1, UP1));
				pusherList.add(new Pushers(10, 1, UP1));
				pusherList.add(new Pushers(1, 3, LEFT1));
				pusherList.add(new Pushers(1, 5, LEFT1));
				pusherList.add(new Pushers(1, 8, LEFT1));
				pusherList.add(new Pushers(1, 10, LEFT1));
				pusherList.add(new Pushers(12, 3, RIGHT1));
				pusherList.add(new Pushers(12, 5, RIGHT1));
				pusherList.add(new Pushers(12, 8, RIGHT1));
				pusherList.add(new Pushers(12, 10, RIGHT1));
				pusherList.add(new Pushers(3, 12, DOWN1));
				pusherList.add(new Pushers(5, 12, DOWN1));
				pusherList.add(new Pushers(8, 12, DOWN1));
				pusherList.add(new Pushers(10, 12, DOWN1));
			}
		}
		//add flamers
		for (Boards bd : boardList) {
			if (bd.getName() == BLAST_FURNACE) {
				bd.setFlamers(OFF);
				bd.setFlamersDone(false);
				flamerList.add(new Flamers(6, 1, RIGHT1));
				flamerList.add(new Flamers(4, 3, DOWN1));
				flamerList.add(new Flamers(5, 3, UP1));
				flamerList.add(new Flamers(10, 4, LEFT1));
				flamerList.add(new Flamers(5, 5, UP1));
				flamerList.add(new Flamers(8, 5, RIGHT1));
				flamerList.add(new Flamers(10, 5, RIGHT1));
				flamerList.add(new Flamers(1, 6, DOWN1));
				flamerList.add(new Flamers(12, 6, DOWN1));
				flamerList.add(new Flamers(3, 8, UP1));
				flamerList.add(new Flamers(10, 8, LEFT1));
				flamerList.add(new Flamers(8, 9, UP1));
				flamerList.add(new Flamers(3, 10, UP1));
				flamerList.add(new Flamers(5, 10, LEFT1));
				flamerList.add(new Flamers(5, 11, RIGHT1));
				flamerList.add(new Flamers(1, 12, DOWN1));
			}
		}
		//add Lasers
		for (Boards bd : boardList) {//VAULT
			if (bd.getName() == VAULT) {
				bd.setLasers(OFF);
				bd.setLasersDone(false);
				laserList.add(new Lasers(1, 4, 5, 1, 5, LEFT1));
				//laserList.add(new Lasers(2,4, 5,1,5, LEFT1));//test
				//laserList.add(new Lasers(3,4, 5,1,5, LEFT1));//test
				laserList.add(new Lasers(1, 4, 8, 1, 8, LEFT1));
				laserList.add(new Lasers(1, 9, 5, 12, 5, RIGHT1));
				//laserList.add(new Lasers(2,9, 5,12,5, RIGHT1));//test
				//laserList.add(new Lasers(3,9, 5,12,5, RIGHT1));//test
				laserList.add(new Lasers(1, 9, 8, 12, 8, RIGHT1));
				laserList.add(new Lasers(1, 3, 9, 3, 12, DOWN1));
				//laserList.add(new Lasers(2,3, 9,3,12, DOWN1));//test
				//laserList.add(new Lasers(3,3, 9,3,12, DOWN1));//test
				//laserList.add(new Lasers(1,8, 12,8,9, UP1));//test
				//laserList.add(new Lasers(2,8, 12,8,9, UP1));//test
				//laserList.add(new Lasers(3,8, 12,8,9, UP1));//test
			}
		}
		for (Boards bd : boardList) {//CHOP_SHOP
			if (bd.getName() == CHOP_SHOP) {
				bd.setLasers(OFF);
				bd.setLasersDone(false);
				//laserList.add(new Lasers(1, 6, 2, 4, 2, LEFT1));//not implemented yet!!!
				laserList.add(new Lasers(1, 3, 8, 3, 3, UP1));
				laserList.add(new Lasers(2, 7, 9, 4, 9, LEFT1));
				laserList.add(new Lasers(3, 7, 9, 4, 9, LEFT1));
				laserList.add(new Lasers(1, 9, 7, 9, 5, UP1));
				laserList.add(new Lasers(1, 10, 11, 8, 11, LEFT1));
				laserList.add(new Lasers(1, 2, 11, 2, 11, RIGHT1));
				laserList.add(new Lasers(2, 2, 11, 2, 11, RIGHT1));
				laserList.add(new Lasers(3, 2, 11, 2, 11, RIGHT1));
			}
		}
		for (Boards bd : boardList) {//EXCHANGE
			if (bd.getName() == EXCHANGE) {
				bd.setLasers(OFF);
				bd.setLasersDone(false);
				laserList.add(new Lasers(1, 3, 1, 3, 3, DOWN1));
			}
		}
		for (Boards bd : boardList) {//CROSS
			if (bd.getName() == CROSS) {
				bd.setLasers(OFF);
				bd.setLasersDone(false);
				laserList.add(new Lasers(1, 5, 3, 5, 1, UP1));
				laserList.add(new Lasers(2, 9, 4, 9, 2, UP1));
				laserList.add(new Lasers(3, 9, 4, 9, 2, UP1));
				laserList.add(new Lasers(1, 3, 9, 4, 9, RIGHT1));
				laserList.add(new Lasers(1, 8, 9, 9, 9, RIGHT1));
			}
		}
		for (Boards bd : boardList) {//LASER_MAZE
			if (bd.getName() == LASER_MAZE) {
				bd.setLasers(OFF);
				bd.setLasersDone(false);
				laserList.add(new Lasers(2, 3, 1, 3, 2, DOWN1));
				laserList.add(new Lasers(3, 3, 1, 3, 2, DOWN1));
				//laserList.add(new Lasers(1, 8, 1, 8, 2, DOWN1));//not implemented yet!!!
				//laserList.add(new Lasers(2, 8, 1, 8, 2, DOWN1));//not implemented yet!!!
				//laserList.add(new Lasers(3, 8, 1, 8, 2, DOWN1));//not implemented yet!!!
				laserList.add(new Lasers(2, 9, 2, 9, 2, RIGHT1));
				laserList.add(new Lasers(3, 9, 2, 9, 2, RIGHT1));
				laserList.add(new Lasers(1, 1, 3, 2, 3, RIGHT1));
				laserList.add(new Lasers(2, 2, 5, 1, 5, LEFT1));
				laserList.add(new Lasers(3, 2, 5, 1, 5, LEFT1));
				laserList.add(new Lasers(2, 5, 4, 5, 1, UP1));
				laserList.add(new Lasers(3, 5, 4, 5, 1, UP1));
				laserList.add(new Lasers(2, 11, 3, 12, 3, RIGHT1));
				laserList.add(new Lasers(3, 11, 3, 12, 3, RIGHT1));
				laserList.add(new Lasers(2, 9, 4, 9, 4, DOWN1));
				laserList.add(new Lasers(3, 9, 4, 9, 4, DOWN1));
				laserList.add(new Lasers(2, 5, 6, 5, 6, UP1));
				laserList.add(new Lasers(3, 5, 6, 5, 6, UP1));
				laserList.add(new Lasers(1, 3, 7, 9, 7, RIGHT1));
				laserList.add(new Lasers(2, 10, 7, 10, 7, RIGHT1));
				laserList.add(new Lasers(3, 10, 7, 10, 7, RIGHT1));
				laserList.add(new Lasers(1, 11, 8, 12, 8, RIGHT1));
				laserList.add(new Lasers(1, 9, 9, 9, 8, UP1));
				laserList.add(new Lasers(1, 10, 10, 12, 10, RIGHT1));
				laserList.add(new Lasers(2, 6, 9, 6, 9, LEFT1));
				laserList.add(new Lasers(3, 6, 9, 6, 9, LEFT1));
				laserList.add(new Lasers(1, 3, 12, 3, 3, UP1));
				laserList.add(new Lasers(1, 4, 10, 4, 10, UP1));
			}
		}
		for (Boards bd : boardList) {//PIT_MAZE
			if (bd.getName() == PIT_MAZE) {
				bd.setLasers(OFF);
				bd.setLasersDone(false);
				laserList.add(new Lasers(1, 7, 4, 7, 6, DOWN1));
			}
		}

		//block tiles that are blocked by neighbouring tiles
		for (Tiles neighbour : tileList) {
			for (Tiles current : tileList) {
				//block tiles up where tile above down is blocked
				if(neighbour.isDownBlocked()) {
					if(current.getX()==neighbour.getX()) {
						if(current.getY()==neighbour.getY()+1) {
							current.setUpBlocked(true);
						}
					}
				}
				//block tiles down where tile below up is blocked
				if(neighbour.isUpBlocked()) {
					if(current.getX()==neighbour.getX()) {
						if(current.getY()==neighbour.getY()-1) {
							current.setDownBlocked(true);
						}
					}
				}
				//block tiles left where tile left is blocked to the right
				if(neighbour.isRightBlocked()) {
					if(current.getX()==neighbour.getX()+1) {
						if(current.getY()==neighbour.getY()) {
							current.setLeftBlocked(true);
						}
					}
				}
				//block tiles right where tile right is blocked to the left
				if(neighbour.isLeftBlocked()) {
					if(current.getX()==neighbour.getX()-1) {
						if(current.getY()==neighbour.getY()) {
							current.setRightBlocked(true);
						}
					}
				}

			}
		}

		placeFlags();
		placeRobots();
		gameState.setDealCards(true);
		gameState.setGameOn(true);
		gameState.setSelectRobot(false);
		mouseX = 0;
		mouseY = 0;
	}//createBoard
	public void placeFlags() {
		int min = 1;
		int max = 12;
		int range = max - min + 1;
		int flagsPlacedCount = 1;
		for (int i =1; i <=6; i++) {
			int flagX = (int) (Math.random() * range) + min;
			int flagY = (int) (Math.random() * range) + min;
			for (Tiles t : tileList) {
				if ((flagX == t.getX()) && (flagY == t.getY())) {
					if ((t.getCanPlaceFlag()) && (!t.getFlagPlaced()) && (gameState.getNoOfFlagsPlaced() < 7)) {
						flagList.add(new Flags(flagX, flagY, i));
						t.setFlagPlaced(true);
						flagsPlacedCount++;
						gameState.setNoOfFlagsPlaced(flagsPlacedCount);
					}
					else if (i>0) {
						i--;
					}
				}
			}
		}
		//for testing flags
		boolean manualFlagPlacement = false;
		if(manualFlagPlacement) {
			for(Flags f : flagList) {
				if(f.getNo()==2) {
					f.setX(12);
					f.setY(1);
					f.setXDraw(f.getX()*60-1);
					f.setYDraw(f.getY()*60+30);
					f.setYDraw(SCREEN_H-f.getYDraw());
				}
			}
		}
		//set next unvisited flag as robots target
		if(gameState.getNoOfFlagsPlaced()==7) {
			updateRobotTarget();
		}
	}//placeFlags
	public void drawFlags() {
		for (Flags f : flagList) {
			batch.begin();
			batch.draw(f.getPic(), f.getXDraw(), f.getYDraw());
			batch.end();
		}
	}//drawFlags
	public void updateRobotTarget() {
		//set current target for robot
		for (Robots r : roboList) {
			for(Flags f : flagList) {
				if(r.getCurrentTarget() == f.getNo()) {
					r.setTargetX(f.getX());
					r.setTargetY(f.getY());
				}
			}
		}
		/*
		for (Robots r : roboList) {
			System.out.println("RX: " + r.getX());
			System.out.println("RY: " + r.getY());
			System.out.println("TX: " + r.getTargetX());
			System.out.println("TY: " + r.getTargetY());
		}
		*/
		//check if target is hit
		for (Robots r : roboList) {
			if((r.getX() == r.getTargetX()) && (r.getY()==r.getTargetY())) {
				r.setCurrentTarget(r.getCurrentTarget()+1);
				//System.out.println("Current target hit!");
			}
		}
		//update current target for robot
		for (Robots r : roboList) {
			for(Flags f : flagList) {
				if(r.getCurrentTarget() == f.getNo()) {
					r.setTargetX(f.getX());
					r.setTargetY(f.getY());
				}
			}
		}
	}//updateRobotTarget()
	public void placeRobots() {
		for (Flags f : flagList) {
			for (Robots r : roboList) {
				if (r.getType() != 0) {
					if (f.getNo() == 1) {
						r.setX(f.getX());
						r.setY(f.getY());
						r.setRespawnX(f.getX());
						r.setRespawnY(f.getY());
					}
					r.setDrawX(r.getX() *60);
					r.setDrawY(r.getY() *60 + 30);
					r.setDrawY(SCREEN_H - r.getDrawY());
				}
			}
		}
		//custom robot starting positions for testing
		boolean testPosition = false;
		if (testPosition) {
			for (Robots r : roboList) {
				if (r.getName().equalsIgnoreCase("Twonky")) {
					r.setX(4);
					r.setY(1);
					r.setDrawX(r.getX() *60);
					r.setDrawY(r.getY() *60 + 30);
					r.setDrawY(SCREEN_H - r.getDrawY());
				}
				if (r.getName().equalsIgnoreCase("SquashBot")) {
					r.setX(10);
					r.setY(6);
					r.setDrawX(r.getX() *60);
					r.setDrawY(r.getY() *60 + 30);
					r.setDrawY(SCREEN_H - r.getDrawY());
				}
				if (r.getName().equalsIgnoreCase("Twitch")) {
					r.setX(1);
					r.setY(3);
					r.setDrawX(r.getX() *60);
					r.setDrawY(r.getY() *60 + 30);
					r.setDrawY(SCREEN_H - r.getDrawY());
				}
				if (r.getName().equalsIgnoreCase("ZoomBot")) {
					r.setX(1);
					r.setY(4);
					r.setDrawX(r.getX() *60);
					r.setDrawY(r.getY() *60 + 30);
					r.setDrawY(SCREEN_H - r.getDrawY());
				}
				if (r.getName().equalsIgnoreCase("HammerBot")) {
					r.setX(1);
					r.setY(5);
					r.setDrawX(r.getX() * 60);
					r.setDrawY(r.getY() * 60 + 30);
					r.setDrawY(SCREEN_H - r.getDrawY());
				}
				if (r.getName().equalsIgnoreCase("SpinBot")) {
					r.setX(1);
					r.setY(6);
					r.setDrawX(r.getX() *60);
					r.setDrawY(r.getY() *60 + 30);
					r.setDrawY(SCREEN_H - r.getDrawY());
				}
				if (r.getName().equalsIgnoreCase("Hulk X90")) {
					r.setX(1);
					r.setY(7);
					r.setDrawX(r.getX() * 60);
					r.setDrawY(r.getY() * 60 + 30);
					r.setDrawY(SCREEN_H - r.getDrawY());
				}
				if (r.getName().equalsIgnoreCase("TrundleBot")) {
					r.setX(1);
					r.setY(8);
					r.setDrawX(r.getX() *60);
					r.setDrawY(r.getY() *60 + 30);
					r.setDrawY(SCREEN_H - r.getDrawY());
				}
			}
		}
	}//placeRobots
	public void drawBoard() {
		//stateTime += Gdx.graphics.getDeltaTime();
		batch.begin();
		batch.draw(img_stars, 0, 0);
		batch.end();

		int yOffset = 40;//35;
		//draw board
		for (Tiles t : tileList) {
			if(t.getType()!=15) {//do not draw holes
				batch.begin();
				//draw floor tile beneath gear sprites
				if((t.getType()==9) || (t.getType()==10)) {
					for (Tiles t2 : tileList) {
						if(t2.getType()==0) {
							batch.draw(t2.getPic(), t.getX() * 60 - 10, SCREEN_H - t.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, 0);
						}
					}
				}
				//draw all board tiles
				batch.draw(t.getPic(), t.getX() * 60 - 10, SCREEN_H - t.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, t.getRotation());
				batch.end();
			}
		}
		//draw pushers
		for(Pushers p : pusherList) {
			batch.begin();
			batch.draw(p.getPic(), p.getX() * 60 - 10, SCREEN_H - p.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, p.getDirection());
			batch.end();
		}

		for(Flamers f : flamerList) {
			batch.begin();
			batch.draw(f.getPic(), f.getX() * 60 - 10, SCREEN_H - f.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, f.getDirection());
			batch.end();
		}
	}//drawBoard
	public void moveRobot(String name) {
		//stateTime += Gdx.graphics.getDeltaTime();
		//detect tile robots are on / check for wall collision
		for (Robots r : roboList) {
			if ((r.getActive())) {//((r.getActive()) || (r.getMoveDistance() != 0f))
				if ((r.getMoveDistance() == 0f) ||(r.getMoveDistance() == 60f) || (r.getMoveDistance() == 120f) || (r.getMoveDistance() == 180f) || (r.getMoveDistance() == -60f)) {
					detectRobotTile();
					checkBlocked();
					checkWallCollision();
					if(r.getMoveDistance()!=0) {
						robotPushingRobot();//PROBLEM HERE!!!???
					}
				}
			}
		}

		for (Robots r : roboList) {
			if ((r.getMoveDistance() == 60f) || (r.getMoveDistance() == 120f)) {
				updateRobotTarget();
			}
		}

		//check for holes and edge of board fall
		for (Robots r : roboList) {
			if ((r.getMoveDistance() == 60f) || (r.getMoveDistance() == 120f)) {
				checkFall();
			}
		}
		//move forward/back
		for (Robots r : roboList) {
			if ((r.getMoveType() == MOVE) && (r.getMoveDistance() > 0f)) {
				switch (r.getMoveDirection()) {
					case UP1:
						if(!r.isBlockedUp()) {
							r.setDrawY(r.getDrawY() + robotMoveIncrement);
							r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
						}
						break;
					case DOWN1:
						if(!r.isBlockedDown()) {
							r.setDrawY(r.getDrawY() - robotMoveIncrement);
							r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
						}
						break;
					case LEFT1:
						r.setDrawX(r.getDrawX() - robotMoveIncrement);
						r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
						break;
					case RIGHT1:
						r.setDrawX(r.getDrawX() + robotMoveIncrement);
						r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
						break;
				}
			}
		}

		//end movement
		for (Robots r : roboList) {
			if((!r.isFalling())) {
				if ((r.getActive()) && (r.getMoveDistance() == 0f)) {
					detectRobotTile();
					robotMoveIncrement = 1f;
					updateRobotTarget();
					System.out.println("goto updateRobotTarget");
					checkFall();
					if(!animationPlaying()) {
						deactivateRobotAndCard();
					}
				}
			}
		}
	}//moveRobot(String name)
	public void moveBlueBelts() {
		stateTime += Gdx.graphics.getDeltaTime();
		int yOffset = 40;

		for (Tiles t : tileList) {
			batch.begin();
			for (Boards bd : boardList) {
				if (bd.isBlueBelts() == ON) {
					TextureRegion currentBlueFrame = blueBeltAnimation.getKeyFrame(stateTime, false);
					switch (t.getType()) {
						case 5:
							batch.draw(currentBlueFrame, t.getX() * 60 - 10, SCREEN_H - t.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, 90, true);//blueUp
							break;
						case 6:
							batch.draw(currentBlueFrame, t.getX() * 60 - 10, SCREEN_H - t.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, 270, true);//blueDown
							break;
						case 7:
							batch.draw(currentBlueFrame, t.getX() * 60 - 10, SCREEN_H - t.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, 180, true);//blueLeft
							break;
						case 8:
							batch.draw(currentBlueFrame, t.getX() * 60 - 10, SCREEN_H - t.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, 0, true);//blueRight
							break;
					}
				}
			}
			batch.end();
		}//for (Tiles t : tileList)

		//draw pushers as they are drawn on top of belts
		for(Pushers p : pusherList) {
			batch.begin();
			batch.draw(p.getPic(), p.getX() * 60 - 10, SCREEN_H - p.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, p.getDirection());
			batch.end();
		}
		//move robots standing on belt
		for(Robots r : roboList) {
			for(Tiles t : tileList) {
				if ((r.getX() == t.getX()) && (r.getY() == t.getY())) {
					if(r.getMoveDirection()==NONE) {
						switch (t.getType()) {
							case 5:
								r.setMoveDirection(UP1);
								r.setMoveDistance(60);
								break;
							case 6:
								r.setMoveDirection(DOWN1);
								r.setMoveDistance(60);
								break;
							case 7:
								r.setMoveDirection(LEFT1);
								r.setMoveDistance(60);
								break;
							case 8:
								r.setMoveDirection(RIGHT1);
								r.setMoveDistance(60);
								break;
						}//switch
					}
				}
			}//tile loop
		}//robot loop

		for(Robots r : roboList) {
			if(r.getMoveDistance()>0) {
				if (r.getMoveDirection() == UP1) {
					r.setDrawY(r.getDrawY() + robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
				if (r.getMoveDirection() == DOWN1) {
					r.setDrawY(r.getDrawY() - robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
				if (r.getMoveDirection() == LEFT1) {
					r.setDrawX(r.getDrawX() - robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
				if (r.getMoveDirection() == RIGHT1) {
					r.setDrawX(r.getDrawX() + robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
			}
		}

		//increase blue belt phase as it moves twice
		if(blueBeltAnimation.isAnimationFinished(stateTime)){
			for(Boards bd: boardList) {
				if(bd.getBlueBeltPhase()==0) {
					bd.setBlueBeltPhase(bd.getBlueBeltPhase()+1);
					//stop robots moving
					for(Robots r : roboList) {
						r.setMoveDirection(NONE);
						r.setMoveDistance(0f);
					}
					stateTime = 0f;
				}
			}
			checkFall();
		}

		//end animation
		if(blueBeltAnimation.isAnimationFinished(stateTime)){
			for(Robots r : roboList) {
				r.setMoveDirection(NONE);
				r.setMoveDistance(0f);
			}
			for(Boards bd: boardList) {
				if(bd.getBlueBeltPhase()==1) {
					bd.setBlueBeltPhase(0);
					bd.setBlueBelts(OFF);
					bd.setBlueBeltsDone(true);
					stateTime = 0f;
				}
			}
			checkFall();
		}
	}//moveBlueBelts
	public void moveRedBelts() {
		stateTime += Gdx.graphics.getDeltaTime();
		int yOffset = 40;
		for (Tiles t : tileList) {
			batch.begin();
			for (Boards bd : boardList) {
				if (bd.isRedBelts() == ON) {
					TextureRegion currentRedFrame = redBeltAnimation.getKeyFrame(stateTime, false);
					switch (t.getType()) {
						case 1:
							batch.draw(currentRedFrame, t.getX() * 60 - 10, SCREEN_H - t.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, 90, true);//blueUp
							break;
						case 2:
							batch.draw(currentRedFrame, t.getX() * 60 - 10, SCREEN_H - t.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, 270, true);//blueUp
							break;
						case 3:
							batch.draw(currentRedFrame, t.getX() * 60 - 10, SCREEN_H - t.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, 180, true);//blueUp
							break;
						case 4:
							batch.draw(currentRedFrame, t.getX() * 60 - 10, SCREEN_H - t.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, 0, true);//blueUp
							break;
					}
				}
			}
			batch.end();
		}//for (Tiles t : tileList) {
		//draw pushers
		for(Pushers p : pusherList) {
			batch.begin();
			batch.draw(p.getPic(), p.getX() * 60 - 10, SCREEN_H - p.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, p.getDirection());
			batch.end();
		}
		//move robots standing on belt
		for(Robots r : roboList) {
			for(Tiles t : tileList) {
				if ((r.getX() == t.getX()) && (r.getY() == t.getY())) {
					if(r.getMoveDirection()==NONE) {
						switch (t.getType()) {
							case 1:
								r.setMoveDirection(UP1);
								r.setMoveDistance(60);
								robotPushingRobot();
								break;
							case 2:
								r.setMoveDirection(DOWN1);
								r.setMoveDistance(60);
								robotPushingRobot();
								break;
							case 3:
								r.setMoveDirection(LEFT1);
								r.setMoveDistance(60);
								robotPushingRobot();
								break;
							case 4:
								r.setMoveDirection(RIGHT1);
								r.setMoveDistance(60);
								robotPushingRobot();
								break;
						}//switch
					}
				}
			}//tile loop
		}//robot loop

		for(Robots r : roboList) {
			if(r.getMoveDistance()>0) {
				if (r.getMoveDirection() == UP1) {
					r.setDrawY(r.getDrawY() + robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
				if (r.getMoveDirection() == DOWN1) {
					r.setDrawY(r.getDrawY() - robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
				if (r.getMoveDirection() == LEFT1) {
					r.setDrawX(r.getDrawX() - robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
				if (r.getMoveDirection() == RIGHT1) {
					r.setDrawX(r.getDrawX() + robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
			}
		}
		//end red belt animation
		if(redBeltAnimation.isAnimationFinished(stateTime)){
			//stop robots moving
			for(Robots r : roboList) {
				r.setMoveDirection(NONE);
				r.setMoveDistance(0f);
			}
			for(Boards bd: boardList) {
				bd.setRedBelts(OFF);
				bd.setRedBeltsDone(true);
				stateTime = 0f;
			}
			checkFall();
		}
	}//moveRedBelts
	public void movePushers() {
		stateTime += Gdx.graphics.getDeltaTime();
		int yOffset = 40;
		for (Pushers p : pusherList) {
			TextureRegion currentPusherFrame = pusherAnimation.getKeyFrame(stateTime, false);
			batch.begin();
			batch.draw(currentPusherFrame, p.getX() * 60 - 10, SCREEN_H - p.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, p.getDirection()+90, true);
			batch.end();
		}

		//end pusher animation
		if(pusherAnimation.isAnimationFinished(stateTime)){
			for(Boards bd: boardList) {
				bd.setPushers(OFF);
				bd.setPushersDone(true);
				checkFall();
				stateTime = 0f;
			}
		}
		//push robots in pusher path
		int tmpMoveDir = -1;
		for(Robots r : roboList) {
			if(r.getMoveDirection()==NONE) {
				for(Pushers p : pusherList) {
					if((r.getX()==p.getX()) && (r.getY()==p.getY())){
						switch(p.getDirection()) {
							case UP1:
								tmpMoveDir=DOWN1;
								break;
							case DOWN1:
								tmpMoveDir=UP1;
								break;
							case LEFT1:
								tmpMoveDir=RIGHT1;
								break;
							case RIGHT1:
								tmpMoveDir=LEFT1;
								break;
						}
						r.setMoveDirection(tmpMoveDir);
						r.setMoveDistance(60);
						robotPushingRobot();
					}
				}
			}
		}
		//pusher moves robot
		for(Robots r : roboList) {
			if(r.getMoveDistance()>0) {
				if (r.getMoveDirection() == UP1) {
					r.setDrawY(r.getDrawY() + robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
				if (r.getMoveDirection() == DOWN1) {
					r.setDrawY(r.getDrawY() - robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
				if (r.getMoveDirection() == LEFT1) {
					r.setDrawX(r.getDrawX() - robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
				if (r.getMoveDirection() == RIGHT1) {
					r.setDrawX(r.getDrawX() + robotMoveIncrement);
					r.setMoveDistance(r.getMoveDistance() - robotMoveIncrement);
				}
			}
		}
	}//movePushers
	public void moveGears() {
		for (Tiles t : tileList) {
			if((t.getType() == 9) && (t.getGearRotate()<90)) {//clockwise//was ifLeftRotate
				t.setRotation(t.getRotation()-gearTurnIncrement);
				t.setGearRotate(t.getGearRotate()+gearTurnIncrement);
			}
			if((t.getType() == 10) && (t.getGearRotate()<90)) {//anti_clockwise
				t.setRotation(t.getRotation()+gearTurnIncrement);
				t.setGearRotate(t.getGearRotate()+gearTurnIncrement);
			}
		}//tile loop

		//ensure gears do not go beyond 360
		for (Tiles t : tileList) {
			if ((t.getType() == 9) || (t.getType() == 10)) {//clockwise
				if((t.getRotation()==360) || (t.getRotation()==-360)) {
					t.setRotation(0);
				}
			}
		}

		int rotation = 0;
		//turn robot on gears clockwise
		for(Robots r : roboList) {
			for (Tiles t : tileList) {
				if ((r.getX() == t.getX()) && (r.getY() == t.getY())) {
					if (t.getType() == 9) {//clockwise
						rotation = r.getFacingDir() - gearTurnIncrement;
						r.setFacingDir(rotation);
					}
				}
			}
		}
		//turn robot on gears anti-clockwise
		for(Robots r : roboList) {
			for (Tiles t : tileList) {
				if ((r.getX() == t.getX()) && (r.getY() == t.getY())) {
					if (t.getType() == 10) {//clockwise
						rotation = r.getFacingDir() + gearTurnIncrement;
						r.setFacingDir(rotation);
					}
				}
			}
		}

		//end gear turn
		for(Tiles t : tileList) {
			if (t.getGearRotate() == 90) {
				t.setGearRotate(0);
				for (Boards bd : boardList) {
					bd.setGears(OFF);
					bd.setGearsDone(true);
				}
				for (Robots r : roboList) {
					if(r.getFacingDir()==90) {
						r.setFacingDir(-270);
					}
					if(r.getFacingDir()==180) {
						r.setFacingDir(-180);
					}
					if((r.getFacingDir()==360)||(r.getFacingDir()==-360)) {
						r.setFacingDir(0);
					}
				}
			}
		}//tile loop
	}//turnGears
	public void moveFlamers() {
		stateTime += Gdx.graphics.getDeltaTime();
		int yOffset = 40;
		for (Flamers f : flamerList) {
			TextureRegion currentFlamerFrame = flamerAnimation.getKeyFrame(stateTime, false);
			batch.begin();
			batch.draw(currentFlamerFrame, f.getX() * 60 - 10, SCREEN_H - f.getY() * 60 - yOffset, 30, 30, 60, 60, 1.0f, 1.0f, f.getDirection()+90, true);
			batch.end();
		}

		//end flamer animation
		if(flamerAnimation.isAnimationFinished(stateTime)){
			for(Boards bd: boardList) {
				bd.setFlamers(OFF);
				bd.setFlamersDone(true);
				stateTime = 0f;
			}
		}
	}//moveRedBelts
	public void drawLasers() {
		for(Boards b : boardList) {
			laserRend.setColor(Color.BLUE);
			laserRend.begin(ShapeRenderer.ShapeType.Filled);
			for (Lasers l : laserList) {
				laserRend.rectLine(l.getX(), l.getY(), l.getX2(), l.getY2(), 6);
			}
			laserRend.end();
		}//drawLasers

		//draw laser gun
		int xOffset = -1;
		int yOffset = -1;
		for (Lasers l : laserList) {


			if(l.getDirection()==UP1) {
				xOffset = -8;
				yOffset = 0;//8
			}
			if(l.getDirection()==DOWN1) {
				xOffset = -8;
				yOffset = -16;
			}
			if(l.getDirection()==LEFT1) {
				xOffset = -16;
				yOffset = -8;
			}
			if(l.getDirection()==RIGHT1) {
				xOffset = 0;
				yOffset = -8;
			}

			batch.begin();
			if(l.getDirection()==DOWN1) {
				batch.draw(img_laserPointDown, l.getX()+xOffset, l.getY()+yOffset);
			}
			if(l.getDirection()==UP1) {
				batch.draw(img_laserPointUp, l.getX()+xOffset, l.getY()+yOffset);
			}

			if(l.getDirection()==LEFT1) {
				batch.draw(img_laserPointLeft, l.getX()+xOffset, l.getY()+yOffset);
			}
			if(l.getDirection()==RIGHT1) {
				batch.draw(img_laserPointRight, l.getX()+xOffset, l.getY()+yOffset);
			}
			batch.end();

		}
	}//class drawLasers
	public void fireLasers() {
		laserRend.setColor(Color.RED);
		for (Lasers l : laserList) {
			if(l.getDirection() == RIGHT1) {
				laserRend.begin(ShapeRenderer.ShapeType.Filled);
				laserRend.rectLine(l.getX(), l.getY(), l.getBulletX(), l.getBulletY(), 6);
				laserRend.end();
				if(l.getBulletX() < l.getX2()) {
					l.setBulletX(l.getBulletX()+laserSpeed);
				}
			}
			if(l.getDirection() == LEFT1) {
				laserRend.begin(ShapeRenderer.ShapeType.Filled);
				laserRend.rectLine(l.getX(), l.getY(), l.getBulletX(), l.getBulletY(), 6);
				laserRend.end();
				if(l.getBulletX() > l.getX2()) {
					l.setBulletX(l.getBulletX()-laserSpeed);
				}
			}
			if(l.getDirection() == DOWN1) {
				laserRend.begin(ShapeRenderer.ShapeType.Filled);
				laserRend.rectLine(l.getX(), l.getY(), l.getBulletX(), l.getBulletY(), 6);
				laserRend.end();
				if(l.getBulletY() > l.getY2()) {
					l.setBulletY(l.getBulletY()-laserSpeed);
				}
			}
			if(l.getDirection() == UP1) {
				laserRend.begin(ShapeRenderer.ShapeType.Filled);
				laserRend.rectLine(l.getX(), l.getY(), l.getBulletX(), l.getBulletY(), 6);
				laserRend.end();
				if(l.getBulletY() < l.getY2()) {
					l.setBulletY(l.getBulletY()+laserSpeed);
				}
			}
		}//for (Lasers l : laserList) {

		for (Lasers l : laserList) {
			switch (l.getDirection()) {
				case UP1:
					if(l.getBulletY() < l.getY2()) {
						l.setBulletY(l.getY2());
					}
					break;
				case DOWN:
					if(l.getBulletY() > l.getY2()) {
						l.setBulletY(l.getY2());
					}
					break;
				case LEFT:
					if(l.getBulletX() < l.getX2()) {
						l.setBulletX(l.getX2());
					}
					break;
				case RIGHT:
					if(l.getBulletX() > l.getX2()) {
						l.setBulletX(l.getX2());
					}
					break;
			}
		}

		//TEST - XXX
		//check if robots are hit by laser
		for (Lasers l : laserList) {
			for(Robots r : roboList) {

				//if((l.getNo()==1) && (l.getDirection()==LEFT1)){
					if((l.getBulletX() > r.getDrawX()-20) && (l.getBulletX()<r.getDrawX()+20)){
						if((l.getBulletY() > r.getDrawY()-25) && (l.getBulletY() < r.getDrawY()+25)) {
							//System.out.println("LASER HIT!");
							//System.out.println("X: " + l.getBulletX());
							//System.out.println("Y: " + l.getBulletY());
							r.setDrawZap(true);
						}
					}
				//}

				/*
				if((l.getX()==10) && (l.getY()==11)) {
					System.out.println("X: " + l.getBulletX());
					System.out.println("Y: " + l.getBulletY());
				}
				*/

				/*
				if((l.getBulletX() > r.getDrawX()-20) && (l.getBulletX() < r.getDrawX()+20)){
					if((l.getBulletY() > r.getDrawY()-20) && (l.getBulletY() < r.getDrawY()+20)){
						System.out.println("LASER HIT!!!");
					}
				}
				*/

				/*
				if((l.getBulletX() == r.getDrawX()) && (l.getBulletY() == r.getDrawY())) {
					System.out.println("LASER HIT!!!");
				}
				*/

			}
		}

		//check if all lasers are done
		boolean lasersDone = true;
		for (Lasers l : laserList) {
			//if((l.getBulletX()<l.getX2()) || (l.getBulletY()<l.getY2())) {
			if((l.getBulletX()!=l.getX2()) || (l.getBulletY()!=l.getY2())) {
				lasersDone = false;
			}
		}
		//end laser animation
		if(lasersDone) {
			for (Lasers l : laserList) {//reset bullets
				l.setBulletX(l.getX());
				l.setBulletY(l.getY());
			}
			for (Boards b : boardList) {
				b.setLasers(OFF);
				b.setLasersDone(true);
			}
		}
	}//fireLasers
	public void drawZap() {
		stateTime += Gdx.graphics.getDeltaTime();
		int yOffset = 40;

		//play zap sound
		if(zapFrame==0){
			zap2.play(1, 0.5f, 0);
		}
		//draw zap animation
		TextureRegion currentZapFrame = zapAnimation.getKeyFrame(stateTime, false);
		batch.begin();
		for(Robots r : roboList) {
			if(r.isDrawZap()) {
				batch.draw(currentZapFrame, r.getDrawX()-10,r.getDrawY()-10, 30, 30, 60, 60, 1.0f, 1.0f, 0, true);
				//System.out.println(zapFrame);
				zapFrame++;
			}
		}
		batch.end();
		//end zap animation
		if(zapAnimation.isAnimationFinished(stateTime)){

			for(Robots r : roboList){
				r.setDrawZap(false);
			}

			zapFrame = 0;
			stateTime = 0f;
		}
	}//drawZap
	public void drawGUI() {

		batch.begin();
		batch.draw(img_options, 0,740);
		batch.end();


		int x = 1040;
		for(Robots r : roboList) {

			if(r.getActive()) {

				batch.begin();
				font.getData().setScale(3.0f, 3.0f);//print robot name
				font.setColor(r.getColor());
				font.draw(batch, r.getName(), x, 790);

				batch.draw(r.getPic(), x + 105, 650, 50, 50, 100, 100, 1.0f, 1.0f, r.getGuiFacingDir());

				int xS = x + 90;//draw remaining lives
				for (int l = 0; l < r.getLives(); l++) {
					batch.draw(r.getPic(), xS, 600, 30f, 30f);
					xS += 50;
				}

				int xL = 1180;//is center to middle small robot
				int yL = 470;//50

				if (r.getHealth() == 10) {
					batch.draw(img_damage, xL, yL, 30f, 30f);//row4
				} else {
					batch.draw(img_damage_red, xL, yL, 30f, 30f);
				}
				if (r.getHealth() >= 9) {
					batch.draw(img_damage, xL + 15, yL + 30, 30f, 30f);
				} else {
					batch.draw(img_damage_red, xL + 15, yL + 30, 30f, 30f);
				}
				if (r.getHealth() >= 8) {
					batch.draw(img_damage, xL - 15, yL + 30, 30f, 30f);//row3
				} else {
					batch.draw(img_damage_red, xL - 15, yL + 30, 30f, 30f);
				}
				if (r.getHealth() >= 7) {
					batch.draw(img_damage, xL + 30, yL + 60, 30f, 30f);
				} else {
					batch.draw(img_damage_red, xL + 30, yL + 60, 30f, 30f);
				}
				if (r.getHealth() >= 6) {
					batch.draw(img_damage, xL, yL + 60, 30f, 30f);
				} else {
					batch.draw(img_damage_red, xL, yL + 60, 30f, 30f);
				}
				if (r.getHealth() >= 5) {
					batch.draw(img_damage, xL - 30, yL + 60, 30f, 30f);//row2
				} else {
					batch.draw(img_damage_red, xL - 30, yL + 60, 30f, 30f);
				}
				if (r.getHealth() >= 4) {
					batch.draw(img_damage, xL + 45, yL + 90, 30f, 30f);
				} else {
					batch.draw(img_damage_red, xL + 45, yL + 90, 30f, 30f);
				}
				if (r.getHealth() >= 3) {
					batch.draw(img_damage, xL + 15, yL + 90, 30f, 30f);
				} else {
					batch.draw(img_damage_red, xL + 15, yL + 90, 30f, 30f);
				}
				if (r.getHealth() >= 2) {
					batch.draw(img_damage, xL - 15, yL + 90, 30f, 30f);
				} else {
					batch.draw(img_damage_red, xL - 15, yL + 90, 30f, 30f);
				}
				if (r.getHealth() >= 1) {
					batch.draw(img_damage, xL - 45, yL + 90, 30f, 30f);//row1
				} else {
					batch.draw(img_damage_red, xL - 45, yL + 90, 30f, 30f);
				}
				batch.end();

			}//robot active
		}//robo loop
	}//drawGui
	public void dealCards() {
		Random random = new Random();
		int number = random.nextInt(84);
		number++;
		number*=10;
		//deal cards
		for(Robots r : roboList) {
			if (r.getType() != 0) {
				for (Cards c : cardList) {
					if ((c.getPriority() == number) && (c.getOwner().equalsIgnoreCase("NONE"))) {
						if(r.getCardsHeld() < r.getHealth()-1) {
							if (c.getOwner().equalsIgnoreCase("NONE")) {
								c.setOwner(r.getName());
								c.setOrder(r.getCardsHeld() + 1);
								r.setCardsHeld(r.getCardsHeld() + 1);
							}
						}
					}
				}//cardList loop
			}
		}//roboList loop

		//check if all players have all cards
		int checkDone = 0;
		for( Robots r : roboList) {
			if((r.getType() != 0) && (r.getCardsHeld() < r.getHealth() -1)) {
				checkDone++;
			}
		}
		//end dealCards
		if (checkDone == 0) {
			gameState.setDealCards(false);
		}
	}//dealCards()
	public void drawCards() {
		font.setColor(Color.GREEN);
		//set X/Y co-ordinates for unpicked cards
		for (Robots r : roboList) {
			if (r.getActive()) {
				for (Cards c : cardList) {
					if (c.getOwner().equalsIgnoreCase(r.getName()) && (c.getPickedOrder() == -1)) {//?
						for (int orderLoop = 1; orderLoop < r.getHealth(); orderLoop++) {//+1????
							if (c.getOrder() == orderLoop) {
								switch(c.getOrder()) {
									case 1:
										c.setX(790);
										c.setY(SCREEN_H - 130 - c.getHeight());
										break;
									case 2:
										c.setX(910);
										c.setY(SCREEN_H - 130 - c.getHeight());
										break;
									case 3:
										c.setX(1030);
										c.setY(SCREEN_H - 130 - c.getHeight());
										break;
									case 4:
										c.setX(790);
										c.setY(SCREEN_H - 280 - c.getHeight());
										break;
									case 5:
										c.setX(910);
										c.setY(SCREEN_H - 280 - c.getHeight());
										break;
									case 6:
										c.setX(1030);
										c.setY(SCREEN_H - 280 - c.getHeight());
										break;
									case 7:
										c.setX(790);
										c.setY(SCREEN_H - 430 - c.getHeight());//460
										break;
									case 8:
										c.setX(910);
										c.setY(SCREEN_H - 430 - c.getHeight());
										break;
									case 9:
										c.setX(1030);
										c.setY(SCREEN_H - 430 - c.getHeight());
										break;
								}
							}
						}
					}
				}
			}
		}
		//draw cards during programming phase
		float fontSize = 1.5f;
		for (Robots r : roboList) {
			if (!r.getCardsSelected()) {
				for (Cards c : cardList) {
					if (r.getActive() && (r.getName().equalsIgnoreCase(c.getOwner()))) {
						batch.begin();
						batch.draw(c.getPic(), c.getX(), c.getY());
						font.getData().setScale(fontSize, fontSize);
						font.draw(batch, Integer.toString(c.getPriority()), c.getX() + 20, c.getY() + 114);
						batch.end();
					}
				}
			}
		}
		//draw programmed cards
		for (Robots r : roboList) {
			if (r.getCardsSelected()) {
				for (Cards c : cardList) {
					if (r.getActive() && (r.getName().equalsIgnoreCase(c.getOwner()))) {
						if(c.getPickedOrder() != -1) {
							batch.begin();
							if (!c.getActive()) {
								batch.draw(c.getPic(), c.getX(), c.getY());
								font.getData().setScale(fontSize, fontSize);
								font.draw(batch, Integer.toString(c.getPriority()), c.getX() + 20, c.getY() + 114);
							} else {
								batch.draw(c.getPic(), c.getX() -10, c.getY(), 100f, 150f);
								font.getData().setScale(2.0f, 2.0f);
								font.draw(batch, Integer.toString(c.getPriority()), c.getX() + 17, c.getY() + 139);
							}
							batch.end();
						}
					}
				}
			}
		}
	}//drawCards
	public void selectCards() {
		detectRobotTile();//XXX
		checkBlocked();//XXX
		getAIscores();
		for (Cards c : cardList) {
			c.setxMin();
			c.setxMax();
			c.setyMin();
			c.setyMax();
		}
		//pick a card
		for (Robots r : roboList) {
			if (r.getActive()) {
				for (Cards c : cardList) {
					if (c.getOwner().equalsIgnoreCase(r.getName()) && (c.getPickedOrder() == -1)) {
						if ((mouseX > c.getxMin() && (mouseX < c.getxMax())) && (mouseY > c.getyMin() && (mouseY < c.getyMax()))) {
							if ((Gdx.input.isTouched()) && (r.isDirSelected())) {
								for (int i = 0; i < 5; i++) {
									if (!cardSlot[i]) {
										c.setY(SCREEN_H - 620 - 124);
										c.setPickedOrder(i);
										c.setX(775 + ((+i)*100));
										cardSlot[i] = true;
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		//set card collision boundaries
		for (Cards c : cardList) {
			c.setxMin();
			c.setxMax();
			c.setyMin();
			c.setyMax();
		}
		//if card is deselected
		for (Robots r : roboList) {
			if (r.getActive()) {
				for (Cards c : cardList) {
					if (c.getOwner().equalsIgnoreCase(r.getName())) {
						if ((mouseX > c.getxMin() && (mouseX < c.getxMax())) && (mouseY > c.getyMin() && (mouseY < c.getyMax()))) {
							if (Gdx.input.isTouched()) {
								if (c.getPickedOrder() > -1) {
									cardSlot[c.getPickedOrder()] = false;
									c.setPickedOrder(-1);
								}
							}
						}
					}
				}
			}
		}
		//check if all cards are selected
		int countPickedCards = 0;
		for (int i =0; i <5; i++) {
			if (cardSlot[i]) {countPickedCards++;}
		}
		//draw continue button
		if (countPickedCards == 5) {
			for (Buttons  b : buttonList) {
				if (b.getName().equalsIgnoreCase("continue")) {
					batch.begin();
					batch.draw(b.getPic(), b.getX(), b.getY(), 316f, 60f);
					batch.end();
				}
			}
		}
		//if continue is clicked
		for (Buttons  b : buttonList) {
			if ((b.getName().equalsIgnoreCase("continue")) && (countPickedCards == 5)) {
				if ((mouseX > b.getxMin()) && (mouseX < b.getxMax()) && ((mouseY > b.getyMin()) && (mouseY < b.getyMax()))) {
					if (Gdx.input.isButtonJustPressed(0)) {
						for (Robots r : roboList) {
							if (r.getActive()) {
								r.setCardsSelected(true);
								r.setActive(false);
								gameState.setSelectCards(false);
							}
						}
						for (int i = 0; i < 5; i++) {
							cardSlot[i] = false;
						}
					}
				}
			}
		}
	}//selectCards()
	public void getAIscores() {
		int tx = NONE;
		int ty = NONE;
		for(Robots r : roboList) {
			tx = r.getTargetX();
			ty = r.getTargetY();
		}
		int xDiff = NONE;
		int yDiff = NONE;
		int totalDiff = NONE;
		//get AI Score for tile - Measure tile distance from target
		for(Tiles t : tileList) {
			//tile left of target
			if(t.getX() < tx) {
				xDiff = tx - t.getX();
			}
			//tile right of target
			if(t.getX() > tx) {
				xDiff = t.getX() - tx;
			}
			//same x axis as target
			if(t.getX() == tx) {
				xDiff = 0;
			}
			//tile above target
			if(t.getY() < ty) {
				yDiff = ty-t.getY();
			}
			//tile below target
			if(t.getY() > ty) {
				yDiff = t.getY() - ty;
			}
			//same y axis as target
			if(t.getY() == ty) {
				yDiff = 0;
			}

			totalDiff=xDiff+yDiff;

			if(t.getType() == 15) {//add penalty for hole tiles
				t.setAIscore(25);
			} else {
				t.setAIscore(totalDiff);
			}
		}
		//adjust AI for red belts
		for(Tiles t : tileList) {
			//red belts up
			if(t.getType()==1) {
				if(t.getY() <= ty) {//if tile is above
					t.setAIscore(t.getAIscore()+1);
				}
				if(t.getY() > ty) {//if tile is below target
					t.setAIscore(t.getAIscore()-1);
				}
			}
			//red belts down
			if(t.getType()==2) {
				if(t.getY() < ty) {//if tile is above
					t.setAIscore(t.getAIscore()-1);
				}
				if(t.getY() >= ty) {//if tile is below target
					t.setAIscore(t.getAIscore()+1);
				}
			}
			//red belts left
			if(t.getType()==3) {
				if(t.getX() <= tx) {//if tile left of target or on target
					t.setAIscore(t.getAIscore()+1);
				}
				if(t.getX() > tx) {//if tile right of target
					t.setAIscore(t.getAIscore()-1);
				}
			}
			//red belts right
			if(t.getType()==4) {
				if(t.getX() < tx) {//if tile left of target
					t.setAIscore(t.getAIscore()-1);
				}
				if(t.getX() >= tx) {//if tile right of target or on target
					t.setAIscore(t.getAIscore()+1);
				}
			}
		}

		//adjust AI for blue belts - THIS ONLY DOES 1st PHASE - ADD 2nd PHASE!!!
		//blue belts up
		for(Tiles t : tileList) {
			if (t.getType() == 5) {//up
				if (t.getY() <= ty) {//if tile is above
					t.setAIscore(t.getAIscore() + 1);
				}
				if (t.getY() > ty) {//if tile is below target
					t.setAIscore(t.getAIscore() - 1);
				}
				t.setAIscore(t.getAIscore() + getAIscoresPhase2(t.getX(), t.getY()-1));
			}
		}
		//blue belts down
		for(Tiles t : tileList) {
			if(t.getType()==6) {//down
				if(t.getY() < ty) {//if tile is above
					t.setAIscore(t.getAIscore()-1);
				}
				if(t.getY() >= ty) {//if tile is below target
					t.setAIscore(t.getAIscore()+1);
				}
				t.setAIscore(t.getAIscore() + getAIscoresPhase2(t.getX(), t.getY()+1));
			}
		}
		//blue belts left
		for(Tiles t : tileList) {
			if (t.getType() == 7) {//left
				if (t.getX() <= tx) {//if tile left of target or on target
					t.setAIscore(t.getAIscore() + 1);
				}
				if (t.getX() > tx) {//if tile right of target
					t.setAIscore(t.getAIscore() - 1);
				}
				t.setAIscore(t.getAIscore() + getAIscoresPhase2(t.getX()-1, t.getY()));
			}
		}
		//blue belts right
		for(Tiles t : tileList) {
			if(t.getType()==8) {//right
				if(t.getX() < tx) {//if tile left of target
					t.setAIscore(t.getAIscore()-1);
				}
				if(t.getX() >= tx) {//if tile right of target or on target
					t.setAIscore(t.getAIscore()+1);
				}
				t.setAIscore(t.getAIscore() + getAIscoresPhase2(t.getX()+1, t.getY()));
			}
		}

		//adjust score if belts are pushing robot off board
		for(Tiles t : tileList) {
			//off board up
			if((t.getType() == 1)||(t.getType() == 5)) {
				if(t.getY()==1) {
					t.setAIscore(25);
				}
				if((t.getType()==5) && (t.getY()==2)) {//2nd phase
					t.setAIscore(25);
				}
				////into hole up
				for(Tiles nextTile : tileList) {
					if ((nextTile.getX() == t.getX()) && (nextTile.getY() == t.getY() - 1)) {
						if(nextTile.getType()==15) {
							t.setAIscore(25);
						}
					}
					if((t.getType()== 5)  && (nextTile.getX() == t.getX()) && (nextTile.getY() == t.getY() - 2)) {//phase 2
						if(nextTile.getType()==15) {
							t.setAIscore(25);
						}
					}
				}
			}
			//off board down
			if((t.getType() == 2)||(t.getType() == 6)) {
				if(t.getY()==12) {
					t.setAIscore(25);
				}
				if((t.getType()==6) && (t.getY()==11)) {//2nd phase
					t.setAIscore(25);
				}
				////into hole down
				for(Tiles nextTile : tileList) {
					if ((nextTile.getX() == t.getX()) && (nextTile.getY() == t.getY() + 1)) {
						if(nextTile.getType()==15) {
							t.setAIscore(25);
						}
					}
					if((t.getType()== 6) && (nextTile.getX() == t.getX())  && (nextTile.getY() == t.getY() + 2)) {//phase 2
						if(nextTile.getType()==15) {
							t.setAIscore(25);
						}
					}
				}
			}
			//off board left
			if((t.getType() == 3)||(t.getType() == 7)) {
				if(t.getX()==1) {
					t.setAIscore(25);
				}
				if((t.getType()==7) && (t.getX()==2)) {//2nd phase
					t.setAIscore(25);
				}
				////into hole left
				for(Tiles nextTile : tileList) {
					if ((nextTile.getX() == t.getX()-1) && (nextTile.getY() == t.getY())) {
						if(nextTile.getType()==15) {
							t.setAIscore(25);
						}
					}
					if((t.getType()== 7) && (nextTile.getX() == t.getX()-2)  && (nextTile.getY() == t.getY())) {//phase 2
						if(nextTile.getType()==15) {
							t.setAIscore(25);
						}
					}
				}
			}
			//off board right
			if((t.getType() == 4)||(t.getType() == 8)) {
				if(t.getX()==12) {
					t.setAIscore(25);
				}
				if((t.getType()==8) && (t.getX()==11)) {//2nd phase
					t.setAIscore(25);
				}
				////into hole right
				for(Tiles nextTile : tileList) {
					if ((nextTile.getX() == t.getX()+1) && (nextTile.getY() == t.getY())) {
						if(nextTile.getType()==15) {
							t.setAIscore(25);
						}
					}
					if((t.getType()== 8) && (nextTile.getX() == t.getX()+2)  && (nextTile.getY() == t.getY())) {//phase 2
						if(nextTile.getType()==15) {
							t.setAIscore(25);
						}
					}
				}
			}
		}
		//adjust score
		for(Tiles t : tileList) {
			t.setAIscore(t.getAIscore()*100);
		}
	}//getAIscores()
	public int getAIscoresPhase2(int x, int y) {
		int adjustment = 0;

		int tx = NONE;
		int ty = NONE;
		for(Robots r : roboList) {
			tx = r.getTargetX();
			ty = r.getTargetY();
		}

		for(Tiles t : tileList) {
			if((x==t.getX()) && (y==t.getY())) {
				if(t.getType()==15) {
					adjustment=25;
					break;
				}
			}
		}

		for(Tiles t : tileList) {
			if((x==t.getX()) && (y==t.getY())) {
				//2nd phase up
				if(t.getType()==5) {
					if (t.getY() <= ty) {//if tile is above
						adjustment++;
						break;
					}
					if (t.getY() > ty) {//if tile is below target
						adjustment--;
						break;
					}
				}
				//2nd phase down
				if(t.getType()==6) {
					if(t.getY() < ty) {//if tile is above
						adjustment--;
						break;
					}
					if(t.getY() >= ty) {//if tile is below target
						adjustment++;
						break;
					}
				}
				//2nd phase left
				if(t.getType()==7) {
					if (t.getX() <= tx) {//if tile left of target or on target
						adjustment++;
						break;
					}
					if (t.getX() > tx) {//if tile right of target
						adjustment--;
						break;
					}
				}
				//2nd phase right
				if(t.getType()==8) {
					if(t.getX() < tx) {//if tile left of target
						adjustment--;
						break;
					}
					if(t.getX() >= tx) {//if tile right of target or on target
						adjustment++;
						break;
					}
				}

			}
		}



		/*
		for(Tiles t : tileList) {
			if ((x == t.getX()) && (y == t.getY())) {
				if (t.getType() == 5) {

				}
			}
		}
		*/

		/*
		for(Tiles t : tileList) {
			if((x==t.getX()) && (y==t.getY())) {
				if(t.getType()== 5) {
					adjustment =-1;
				}
			}
		}
		*/
		return adjustment;
	}

	public void robotAIscore() {
		/*
		//robot AI score
		int rx = NONE;
		int ry = NONE;
		int tx = NONE;
		int ty = NONE;
		//get target xy position
		for(Robots r : roboList) {
			if(r.getActive()) {
				rx=r.getX();
				ry=r.getY();
				tx=r.getTargetX();
				ty=r.getTargetY();
			}
		}

		int xDiff = NONE;
		int yDiff = NONE;
		int totalDiff = NONE;
		for(Tiles t : tileList) {
			if((t.getX()==tx) && (t.getY()==ty)) {
				//robot left of target
				if(rx<tx) {
					xDiff=tx-rx;
					System.out.println("xDiff Left: " + xDiff);
				}
				//robot right of target
				if(rx>tx) {
					xDiff=rx-tx;
					System.out.println("xDiff right: " + xDiff);
				}
				if(rx==tx) {
					xDiff=0;
				}
				//robot above of target
				if(ry<ty) {
					yDiff=ty-ry;
					System.out.println("yDiff above: " + yDiff);
				}
				//robot below of target
				if(ry>ty) {
					yDiff=ry-ty;
					System.out.println("yDiff below: " + yDiff);
				}
				if(ry==ty) {
					yDiff=0;
				}
				totalDiff = xDiff+yDiff;
				t.setAIscore(totalDiff);
				System.out.println("Total Diff: " + totalDiff);
			}
		}
		*/
	}
	public void selectCardsCPU() {
		//count amount of cards already programmed
		int pickedCards = 0;
		for (Robots r : roboList) {
			if ((r.getType() == CPU)) {
				for (Cards c : cardList) {
					if(c.getPickedOrder()!=-1) {
						pickedCards++;
					}
				}
			}
		}

		//System.out.println(pickedCards);

		//place current robot XY position int AI XY position
		if(pickedCards==0) {
			for (Robots r : roboList) {
				if ((r.getType() == CPU)) {// && (!r.getCardsSelected())
					r.setAIX(r.getX());
					r.setAIY(r.getY());
				}
			}
		}

		//assign score to all turn cards
		for (Robots r : roboList) {
			if ((r.getType() == CPU) && (!r.getCardsSelected())) {
				for (Cards c : cardList) {
					if ((c.getOwner().equalsIgnoreCase(r.getName()))) {
						if((c.getNo() >0) && (c.getNo() <=42)) {//all turn cards
							c.setAIX(r.getAIX());
							c.setAIY(r.getAIY());
						}
					}
				}
			}
		}
		//assign score to all FORWARD1 cards
		for (Robots r : roboList) {
			if ((r.getType() == CPU) && (!r.getCardsSelected())) {
				for (Cards c : cardList) {
					if ((c.getOwner().equalsIgnoreCase(r.getName()))) {
						if ((c.getNo() >= 49) && (c.getNo() <= 66)) {//forward1 cards
							switch(r.getFacingDir()) {
								case UP1:
									c.setAIX(r.getAIX());
									c.setAIY(r.getAIY()-1);
									break;
								case DOWN1:
									c.setAIX(r.getAIX());
									c.setAIY(r.getAIY()+1);
									break;
								case LEFT1:
									c.setAIX(r.getAIX()-1);
									c.setAIY(r.getAIY());
									break;
								case RIGHT1:
									c.setAIX(r.getAIX()+1);
									c.setAIY(r.getAIY());
									break;
							}
						}
					}
				}
			}
		}
		//assign score to all FORWARD2 cards
		for (Robots r : roboList) {
			if ((r.getType() == CPU) && (!r.getCardsSelected())) {
				for (Cards c : cardList) {
					if ((c.getOwner().equalsIgnoreCase(r.getName()))) {
						if ((c.getNo() >= 67) && (c.getNo() <= 78)) {//forward1 cards
							switch(r.getFacingDir()) {
								case UP1:
									c.setAIX(r.getAIX());
									c.setAIY(r.getAIY()-2);
									break;
								case DOWN1:
									c.setAIX(r.getAIX());
									c.setAIY(r.getAIY()+2);
									break;
								case LEFT1:
									c.setAIX(r.getAIX()-2);
									c.setAIY(r.getAIY());
									break;
								case RIGHT1:
									c.setAIX(r.getAIX()+2);
									c.setAIY(r.getAIY());
									break;
							}
						}
					}
				}
			}
		}
		//assign score to all FORWARD3 cards
		for (Robots r : roboList) {
			if ((r.getType() == CPU) && (!r.getCardsSelected())) {
				for (Cards c : cardList) {
					if ((c.getOwner().equalsIgnoreCase(r.getName()))) {
						if ((c.getNo() >= 79) && (c.getNo() <= 84)) {//forward1 cards
							switch(r.getFacingDir()) {
								case UP1:
									c.setAIX(r.getAIX());
									c.setAIY(r.getAIY()-3);
									break;
								case DOWN1:
									c.setAIX(r.getAIX());
									c.setAIY(r.getAIY()+3);
									break;
								case LEFT1:
									c.setAIX(r.getAIX()-3);
									c.setAIY(r.getAIY());
									break;
								case RIGHT1:
									c.setAIX(r.getAIX()+3);
									c.setAIY(r.getAIY());
									break;
							}
						}
					}
				}
			}
		}
		//assign score to all BACKUP cards
		for (Robots r : roboList) {
			if ((r.getType() == CPU) && (!r.getCardsSelected())) {
				for (Cards c : cardList) {
					if ((c.getOwner().equalsIgnoreCase(r.getName()))) {
						if ((c.getNo() >= 43) && (c.getNo() <= 48)) {//forward1 cards
							switch(r.getFacingDir()) {
								case UP1:
									c.setAIX(r.getAIX());
									c.setAIY(r.getAIY()+1);
									break;
								case DOWN1:
									c.setAIX(r.getAIX());
									c.setAIY(r.getAIY()-1);
									break;
								case LEFT1:
									c.setAIX(r.getAIX()+1);
									c.setAIY(r.getAIY());
									break;
								case RIGHT1:
									c.setAIX(r.getAIX()-1);
									c.setAIY(r.getAIY());
									break;
							}
						}
					}
				}
			}
		}

		//assign score to cards
		for (Cards c : cardList) {
			if(c.getPickedOrder()==-1) {
				for (Tiles t : tileList) {
					if((c.getAIX()==t.getX()) && (c.getAIY()==t.getY())) {
						c.setScore(t.getAIscore());
					}
				}
			}
		}

		if(pickedCards==0) {
			for (Robots r : roboList) {
				if ((r.getType() == CPU) && (!r.getCardsSelected())) {
					for (Cards c : cardList) {
						if ((c.getOwner().equalsIgnoreCase(r.getName()))) {
							for(int scoreCount = 0; scoreCount<=2500; scoreCount+=25) {
								if((c.getScore()==scoreCount) && (c.getPickedOrder() == -1)) {
									System.out.println(c.getName());
									c.setPickedOrder(0);
									break;
								}
							}
						}
					}
				}
			}
		}



		/*
		//randomly program CPU robot with 5 cards
		for (Robots r : roboList) {
			if ((r.getType() == CPU) && (!r.getCardsSelected())) {
				for (Cards c : cardList) {
					if ((c.getOwner().equalsIgnoreCase(r.getName())) && (c.getPickedOrder() == -1)) {
						c.setY(SCREEN_H - 620 - 124);
						switch (c.getOrder()) {
							case 1:
								c.setX(775);
								c.setPickedOrder(0);
								break;
							case 2:
								c.setX(875);
								c.setPickedOrder(1);
								break;
							case 3:
								c.setX(975);
								c.setPickedOrder(2);
								break;
							case 4:
								c.setX(1075);
								c.setPickedOrder(3);
								break;
							case 5:
								c.setX(1175);
								c.setPickedOrder(4);
								r.setCardsSelected(true);
								break;
						}
					}
				}
			}
		}
		*/

	}//selectCardsCPU
	public void drawRobots() {
		/*
		r.setDrawX(r.getX() *60);
		r.setDrawY(r.getY() *60 + 30);
		r.setDrawY(SCREEN_H - r.getDrawY());
		*/

		//update robots tile coords based on draw x/y coords;
		float tmpY=0;
		for(Robots r : roboList) {
			tmpY = SCREEN_H - r.getDrawY() -30;
			r.setX((int)r.getDrawX()/60);
			r.setY((int)tmpY/60);
		}

		//draw all robots
		for (Robots r : roboList) {
			if (r.getType() != 0) {
				batch.begin();
				if(!r.isVirtual()) {
					batch.draw(r.getPic(), r.getDrawX(), r.getDrawY(), 20, 20, 40, 40, r.getScale(), r.getScale(), r.getFacingDir());
				} else {
					batch.draw(r.getPicV(), r.getDrawX(), r.getDrawY(), 20, 20, 40, 40, r.getScale(), r.getScale(), r.getFacingDir());
				}
				batch.end();
			}
		}
		//draw active robot on top of pile
		for (Robots r : roboList) {
			if (r.getActive()) {//draw active robot on top of pile
				batch.begin();
				if(!r.isVirtual()) {
					batch.draw(r.getPic(), r.getDrawX(), r.getDrawY(), 20, 20, 40, 40, r.getScale(), r.getScale(), r.getFacingDir());
				} else {
					batch.draw(r.getPicV(), r.getDrawX(), r.getDrawY(), 20, 20, 40, 40, r.getScale(), r.getScale(), r.getFacingDir());
				}
				batch.end();
			}
		}
	}//drawRobots
	public void selectStartingDirCPU() {
		int min = 0;
		int max = 3;
		int range = max - min + 1;
		for (Robots r : roboList) {
			if ((r.getType() == CPU) && (!r.isDirSelected())) {
				int dir = (int) (Math.random() * range) + min;
				switch(dir) {
					case 0:
						r.setFacingDir(UP1);
						r.setDirSelected(true);
						break;
					case 1:
						r.setFacingDir(RIGHT1);
						r.setDirSelected(true);
						break;
					case 3:
						r.setFacingDir(DOWN1);
						r.setDirSelected(true);
						break;
					case 4:
						r.setFacingDir(LEFT1);
						r.setDirSelected(true);
						break;
				}
			}
			r.setGuiFacingDir(r.getFacingDir());
		}
	}//selectStartingDirCPU
	public void selectStartingDir() {
		//draw direction selection arrows
		for(Buttons b : buttonList) {
			if(( b.getNo() >= 16) && (b.getNo() <= 19)) {
				batch.begin();
				batch.draw(b.getPic(), b.getX(), b.getY(), 60f, 60f);
				batch.end();
			}
		}
		//if any direction is selected
		for (Buttons b : buttonList) {
			if(b.getType() == BUTTON_DIRECTION_ARROW) {
				if ((mouseX > b.getxMin()) && (mouseX < b.getxMax())) {//check user input
					if ((mouseY > b.getyMin()) && (mouseY < b.getyMax())) {
						if (Gdx.input.isButtonJustPressed(0)) {
							for(Robots r : roboList) {
								if(r.getActive()) {
									if(b.getName().equalsIgnoreCase("greenArrowUp")) {
										r.setFacingDir(UP1);
										r.setDirSelected(true);
										gameState.setSelectCards(true);
										gameState.setSelectStartingDir(false);
									}
									if(b.getName().equalsIgnoreCase("greenArrowDown")) {
										r.setFacingDir(DOWN1);
										r.setDirSelected(true);
										gameState.setSelectCards(true);
										gameState.setSelectStartingDir(false);
									}
									if(b.getName().equalsIgnoreCase("greenArrowLeft")) {
										r.setFacingDir(LEFT1);
										r.setDirSelected(true);
										gameState.setSelectCards(true);
										gameState.setSelectStartingDir(false);
									}
									if(b.getName().equalsIgnoreCase("greenArrowRight")) {
										r.setFacingDir(RIGHT1);
										r.setDirSelected(true);
										gameState.setSelectCards(true);
										gameState.setSelectStartingDir(false);
									}
									r.setGuiFacingDir(r.getFacingDir());
								}
							}
						}
					}
				}
			}
		}
	}//selectStartingDir
	public void activateNextCardAndRobot() {
		detectRobotTile();
		checkBlocked();

		//get card with highest priority
		boolean cardAlreadyActive = false;
		for (Cards c : cardList) {
			for (Robots r : roboList) {
				if ((c.getOwner().equalsIgnoreCase(r.getName())) && (c.getPickedOrder() == gameState.getRegistryPhase())) {
					if ((!cardAlreadyActive) && (!c.getPlayed())) {
						c.setActive(true);
						//tmpCard = c.getPic();//TEST
						r.setActive(true);
						r.setMoveType(c.getMoveType());//set robot move type to card type
						cardAlreadyActive = true;
					}
				}
			}
		}
		//set robot move distance
		for (Robots r : roboList) {
			if (r.getActive()) {
				if (r.getMoveType() == FORWARD_1) {
					r.setMoveDistance(60f);
					r.setMoveDirection(r.getFacingDir());
					r.setMoveType(MOVE);
				}
				if (r.getMoveType() == FORWARD_2) {
					r.setMoveDistance(120f);
					r.setMoveDirection(r.getFacingDir());
					r.setMoveType(MOVE);
				}
				if (r.getMoveType() == FORWARD_3) {
					r.setMoveDistance(180f);
					r.setMoveDirection(r.getFacingDir());
					r.setMoveType(MOVE);
				}
				if (r.getMoveType() == BACK_UP) {
					r.setMoveDistance(60f);
					r.setMoveType(MOVE);
					if (r.getFacingDir() == UP1) {
						r.setMoveDirection(DOWN1);
					}
					if (r.getFacingDir() == DOWN1) {
						r.setMoveDirection(UP1);
					}
					if (r.getFacingDir() == LEFT1) {
						r.setMoveDirection(RIGHT1);
					}
					if (r.getFacingDir() == RIGHT1) {
						r.setMoveDirection(LEFT1);
					}
				}

				if (r.getMoveType() == TURN_RIGHT) {
					r.setTurnAmount(90);
					switch(r.getFacingDir()) {
						case UP1:
							r.setMoveDirection(RIGHT1);
							break;
						case RIGHT1:
							r.setMoveDirection(DOWN1);
							break;
						case DOWN1:
							r.setMoveDirection(LEFT1);
							break;
						case LEFT1:
							r.setMoveDirection(UP1);
							break;
					}
				}
				if (r.getMoveType() == TURN_LEFT) {
					r.setTurnAmount(-90);
					switch(r.getFacingDir()) {
						case UP1:
							r.setMoveDirection(LEFT1);
							break;
						case LEFT1:
							r.setMoveDirection(DOWN1);
							break;
						case DOWN1:
							r.setMoveDirection(RIGHT1);
							break;
						case RIGHT1:
							r.setMoveDirection(UP1);
							break;
					}
				}
				if (r.getMoveType() == U_TURN) {
					r.setTurnAmount(180);
					switch(r.getFacingDir()) {
						case UP1:
							r.setMoveDirection(DOWN1);
							break;
						case LEFT1:
							r.setMoveDirection(RIGHT1);
							break;
						case DOWN1:
							r.setMoveDirection(UP1);
							break;
						case RIGHT1:
							r.setMoveDirection(LEFT1);
							break;
					}
				}
			}
		}
	}//activateNextCardAndRobot
	public void deactivateRobotAndCard() {
		for (Robots r : roboList) {
			if (r.getActive()) {
				//mark current card as played
				for (Cards c : cardList) {
					if ((c.getOwner().equalsIgnoreCase(r.getName())) && (c.getActive())) {
						c.setPlayed(true);
						c.setActive(false);
					}
				}
			}
			//deactivate all robots and update tile and blocked status
			r.setGuiFacingDir(r.getFacingDir());
			r.setActive(false);
			r.setMoveType(NONE);
			r.setMoveDirection(NONE);
			r.setMoveDistance(0f);
			r.setTurnAmount(0);
			detectRobotTile();
			checkBlocked();
		}//Robot loop
	}//deactivateRobotAndCard()
	public void bounce() {
		//forward bounce finished
		for(Robots r : roboList) {
			if ((r.getMoveType()==BOUNCE_FORWARD)&&(r.getBounceDistance() == 0f)) {
				r.setMoveType(BOUNCE_BACK);
			}
		}
		//bounce robot forwards
		for(Robots r : roboList) {
			if(r.getMoveType()==BOUNCE_FORWARD){
				switch(r.getMoveDirection()) {
					case UP1:
						r.setDrawY(r.getDrawY()+1f);
						r.setBounceDistance(r.getBounceDistance()-1);
						break;
					case DOWN1:
						r.setDrawY(r.getDrawY()-1f);
						r.setBounceDistance(r.getBounceDistance()-1);
						break;
					case LEFT1:
						r.setDrawX(r.getDrawX()-1f);
						r.setBounceDistance(r.getBounceDistance()-1);
						break;
					case RIGHT1:
						r.setDrawX(r.getDrawX()+1f);
						r.setBounceDistance(r.getBounceDistance()-1);
						break;
				}//switch
			}
		}//robo loop
		//bounce back finished
		for(Robots r : roboList) {
			if ((r.getMoveType()==BOUNCE_BACK)&&(r.getBounceDistance() == 10)) {
				r.setMoveType(NONE);
				r.setMoveDistance(0f);
				if(!animationPlaying()) {
					deactivateRobotAndCard();
				}
			}
		}
		//bounce robot back
		for(Robots r : roboList) {
			if(r.getMoveType()==BOUNCE_BACK){
				switch(r.getMoveDirection()) {
					case UP1:
						r.setDrawY(r.getDrawY()-1f);
						r.setBounceDistance(r.getBounceDistance()+1);
						break;
					case DOWN1:
						r.setDrawY(r.getDrawY()+1f);
						r.setBounceDistance(r.getBounceDistance()+1);
						break;
					case LEFT1:
						r.setDrawX(r.getDrawX()+1f);
						r.setBounceDistance(r.getBounceDistance()+1);
						break;
					case RIGHT1:
						r.setDrawX(r.getDrawX()-1f);
						r.setBounceDistance(r.getBounceDistance()+1);
						break;
				}//switch
			}
		}//robo loop
	}//bounce
	public void turnRobot(String name) {
		detectRobotTile();
		checkBlocked();
		int rotation = 0;
		//turn right/U turn
		for (Robots r : roboList) {
			if ((r.getMoveType() == TURN_RIGHT) || (r.getMoveType() == U_TURN)) {
				if (r.getTurnAmount() > 0) {
					rotation = r.getFacingDir() - robotTurnIncrement;
					r.setFacingDir(rotation);
					r.setTurnAmount(r.getTurnAmount() - robotTurnIncrement);
				}
			}
		}
		//turn left
		for (Robots r : roboList) {
			if (r.getMoveType() == TURN_LEFT) {
				if (r.getTurnAmount() < 0) {
					rotation = r.getFacingDir() + robotTurnIncrement;
					r.setFacingDir(rotation);
					r.setTurnAmount(r.getTurnAmount() + robotTurnIncrement);
				}
			}
		}
		//deactivate robot and card, mark card as played
		for (Robots r : roboList) {
			if ((r.getActive()) && (r.getTurnAmount() == 0)) {
				r.setFacingDir(r.getMoveDirection());
				if (r.getFacingDir() == -360){//reset -360 to 0
					r.setFacingDir(0);
				}
				if(!animationPlaying()) {
					deactivateRobotAndCard();
				}
			}
		}
	}//turnRobot
	public void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}//waitOnKeyPress
	public void detectRobotTile() {
		//detect tile robot is standing on
		for (Robots r : roboList) {
			if ((r.getMoveDistance() ==  0f) || (r.getMoveDistance() == 60f) || (r.getMoveDistance() == 120f) || (r.getMoveDistance() == 180f)) {
				r.setX((int)r.getDrawX() / 60);
				r.setY((SCREEN_H - (int)r.getDrawY()));
				r.setY(r.getY() / 60);
				for (Tiles t : tileList) {
					if ((r.getX() == t.getX()) && (r.getY() == t.getY())) {
						r.setTileType(t.getName());
					}
				}
			}
		}
	}//detectRobotTile
	public void checkBlocked() {
		//remove all blocked status
		for (Robots r : roboList) {
			r.setBlockedUp(false);
			r.setBlockedDown(false);
			r.setBlockedLeft(false);
			r.setBlockedRight(false);
		}
		//check if tile robot is standing on is blocking robot
		for (Robots r : roboList) {
			for (Tiles t : tileList) {
				if (r.getTileType() == t.getName()) {
					if (t.isUpBlocked()) {
						r.setBlockedUp(true);
					}
					if (t.isDownBlocked()) {
						r.setBlockedDown(true);
					}
					if (t.isLeftBlocked()) {
						r.setBlockedLeft(true);
					}
					if (t.isRightBlocked()) {
						r.setBlockedRight(true);
					}
				}
			}//Tile loop
		}//Robots loop

		/*
		//check if tile beside robot is blocking robot
		for (Robots r : roboList) {
			for (Tiles t : tileList) {
				if ((t.getX() == r.getX()) && (t.getY() == r.getY() -1)) {
					if (t.isDownBlocked()) {
						r.setBlockedUp(true);
					}
				}
				if ((t.getX() == r.getX()) && (t.getY() == r.getY() +1)) {
					if (t.isUpBlocked()) {
						r.setBlockedDown(true);
					}
				}
				if ((t.getX() == r.getX() -1) && (t.getY() == r.getY())) {
					if (t.isRightBlocked()) {
						r.setBlockedLeft(true);
					}
				}
				if ((t.getX() == r.getX() +1) && (t.getY() == r.getY())) {
					if (t.isLeftBlocked()) {
						r.setBlockedRight(true);
					}
				}
			}//Tiles loop
		}//Robots loop
		*/

		//check if robots are blocking robots
		for (Robots r0 : roboList) {
			for (Robots movingRobot : roboList) {
				for (Robots blockingRobot : roboList) {
					if(blockingRobot.isBlockedUp()) {
						if ((movingRobot.getX() == blockingRobot.getX()) && (movingRobot.getY() == blockingRobot.getY()+1)) {
							movingRobot.setBlockedUp(true);
						}
					}
					if(blockingRobot.isBlockedDown()) {
						if ((movingRobot.getX() == blockingRobot.getX()) && (movingRobot.getY() == blockingRobot.getY()-1)) {
							movingRobot.setBlockedDown(true);
						}
					}
					if(blockingRobot.isBlockedLeft()) {
						if ((movingRobot.getX() == blockingRobot.getX()+1) && (movingRobot.getY() == blockingRobot.getY())) {
							movingRobot.setBlockedLeft(true);
						}
					}
					if(blockingRobot.isBlockedRight()) {
						if ((movingRobot.getX() == blockingRobot.getX()-1) && (movingRobot.getY() == blockingRobot.getY())) {
							movingRobot.setBlockedRight(true);
						}
					}
				}
			}
		}
	}//checkBlocked
	public void checkWallCollision() {
		for (Robots r : roboList) {
			if (r.getActive()) {
				if ((r.getMoveDirection() == UP1) && (r.isBlockedUp())) {
					r.setMoveDistance(10f);
					r.setMoveType(BOUNCE_FORWARD);
					bounce.play(1, 0.5f, 0);
				}
				if ((r.getMoveDirection() == DOWN1) && (r.isBlockedDown())) {
					r.setMoveDistance(10f);
					r.setMoveType(BOUNCE_FORWARD);
					bounce.play(1, 0.5f, 0);
				}
				if ((r.getMoveDirection() == LEFT1) && (r.isBlockedLeft())) {
					r.setMoveDistance(10f);
					r.setMoveType(BOUNCE_FORWARD);
					bounce.play(1, 0.5f, 0);
				}
				if ((r.getMoveDirection() == RIGHT1) && (r.isBlockedRight())) {
					r.setMoveDistance(10f);
					r.setMoveType(BOUNCE_FORWARD);
					bounce.play(1, 0.5f, 0);
				}
			}
		}
	}//checkWallCollision
	public void robotPushingRobot() {
		//System.out.println("Check Push");
		detectRobotTile();
		checkBlocked();
		for (Robots r0 : roboList) {
			for (Robots pushing : roboList) {
				for (Robots pushed : roboList) {
					if (pushing.getMoveDirection() == UP1) {
						if ((!pushing.isBlockedUp()) && (!pushed.isBlockedUp())) {
							if ((pushed.getX() == pushing.getX()) && (pushed.getY() == pushing.getY() - 1)) {
								if (pushed.getMoveType() == NONE) {
									pushed.setMoveDirection(UP1);
									pushed.setMoveType(pushing.getMoveType());
									pushed.setMoveDistance(pushing.getMoveDistance());
								}
							}
						}
					}
					if (pushing.getMoveDirection() == DOWN1) {
						if ((!pushing.isBlockedDown()) && (!pushed.isBlockedDown())) {
							if ((pushed.getX() == pushing.getX()) && (pushed.getY() == pushing.getY() + 1)) {
								if (pushed.getMoveType() == NONE) {
									pushed.setMoveDirection(DOWN1);
									pushed.setMoveType(pushing.getMoveType());
									pushed.setMoveDistance(pushing.getMoveDistance());
								}
							}
						}
					}
					if (pushing.getMoveDirection() == LEFT1) {
						if ((!pushing.isBlockedLeft()) && (!pushed.isBlockedLeft())) {
							if ((pushed.getX() == pushing.getX() - 1) && (pushed.getY() == pushing.getY())) {
								if (pushed.getMoveType() == NONE) {
									pushed.setMoveDirection(LEFT1);
									pushed.setMoveType(pushing.getMoveType());
									pushed.setMoveDistance(pushing.getMoveDistance());
								}
							}
						}
					}
					if (pushing.getMoveDirection() == RIGHT1) {
						if ((!pushing.isBlockedRight()) && (!pushed.isBlockedRight())) {
							if ((pushed.getX() == pushing.getX() + 1) && (pushed.getY() == pushing.getY())) {
								if (pushed.getMoveType() == NONE) {
									System.out.println("Push");
									pushed.setMoveDirection(RIGHT1);
									pushed.setMoveType(pushing.getMoveType());
									pushed.setMoveDistance(pushing.getMoveDistance());
								}
							}
						}
					}
				}//pushed loop
			}//pushing loop
		}//r0 loop
	}//robotPushingRobot
	public void checkFall() {
		//check if robot is falling down hole(tile 15)
		detectRobotTile();
		for (Robots r : roboList) {
			for (Tiles t : tileList) {
				if((t.getType()==15)){//(!areBoardElementsMoving())
					if((r.getMoveDistance()==0f) || (r.getMoveDistance()==60f) || (r.getMoveDistance()==120f)) {
						if ((r.getX() == t.getX()) && (r.getY() == t.getY())) {
							r.setMoveDirection(-1);
							r.setMoveDistance(0f);
							r.setMoveType(NONE);
							r.setFalling(true);
						}
					}
				}
			}
		}
		//check if robot has moved off board edge
		for (Robots r : roboList) {
			if ((r.getX()>12) || (r.getX()<1) || (r.getY()>12) || (r.getY()<1)) {
				if((r.getMoveDistance()==0f) || (r.getMoveDistance()==60f) || (r.getMoveDistance()==120f)) {
				//if(!areBoardElementsMoving()) {
					//System.out.println(areBoardElementsMoving());
					r.setMoveDirection(-1);
					r.setMoveDistance(0f);
					r.setMoveType(NONE);
					r.setFalling(true);
				}
			}
		}

	}//checkFall
	public void robotFalling() {
		for(Robots r : roboList) {
			if(r.isFalling()) {

				while(r.getScale()>0) {
					r.setScale(r.getScale()-0.04f);
					break;
				}

				if(r.getScale()<=0f){
					robotDead(r.getName());
					//deactivateRobotAndCard();
				}
			}
		}
	}//robotFalling
	public String robotDead(String name) {
		for(Robots r : roboList) {
			if(name.equalsIgnoreCase(r.getName())) {

				for(Cards c : cardList) {
					if(c.getOwner().equalsIgnoreCase(r.getName())) {
						c.setPlayed(true);
					}
				}
				r.setFalling(false);
				r.setDead(true);
				deactivateRobotAndCard();
			}
		}
		return "";
	}
	public void getInput() {
		if (Gdx.input.isKeyJustPressed(SPACE)) {
			if (!gameState.isPause()) {
				gameState.setPause(true);
			} else {
				gameState.setPause(false);
			}
		}
		if (Gdx.input.isKeyJustPressed(G)) {//'r' robot debug menu//46
			if (gameState.getDebugMenu() != GAME_DEBUG) {
				gameState.setDebugMenu(GAME_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(R)) {//'r' robot debug menu//46
			if (gameState.getDebugMenu() != ROBOT_DEBUG) {
				gameState.setDebugMenu(ROBOT_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(C)) {//'r' robot debug menu//46
			if (gameState.getDebugMenu() != CARD_DEBUG) {
				gameState.setDebugMenu(CARD_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(F)) {//'r' robot debug menu//46
			if (gameState.getDebugMenu() != FLAG_DEBUG) {
				gameState.setDebugMenu(FLAG_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(F1)) {
			//singleRobotDebug("Twonky");
			if (gameState.getDebugMenu() != TWONKY_DEBUG) {
				gameState.setDebugMenu(TWONKY_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(NUM_1)) {
			if (gameState.getDebugMenu() != TWONKY_CARD_DEBUG) {
				gameState.setDebugMenu(TWONKY_CARD_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(F2)) {
			if (gameState.getDebugMenu() != SQUASHBOT_DEBUG) {
				gameState.setDebugMenu(SQUASHBOT_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(NUM_2)) {
			if (gameState.getDebugMenu() != SQUASHBOT_CARD_DEBUG) {
				gameState.setDebugMenu(SQUASHBOT_CARD_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(F3)) {
			if (gameState.getDebugMenu() != TWITCH_DEBUG) {
				gameState.setDebugMenu(TWITCH_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(NUM_3)) {
			if (gameState.getDebugMenu() != TWITCH_CARD_DEBUG) {
				gameState.setDebugMenu(TWITCH_CARD_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(F4)) {
			if (gameState.getDebugMenu() != ZOOMBOT_DEBUG) {
				gameState.setDebugMenu(ZOOMBOT_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(NUM_4)) {
			if (gameState.getDebugMenu() != ZOOMBOT_CARD_DEBUG) {
				gameState.setDebugMenu(ZOOMBOT_CARD_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(F5)) {
			if (gameState.getDebugMenu() != HAMMERBOT_DEBUG) {
				gameState.setDebugMenu(HAMMERBOT_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(NUM_5)) {
			if (gameState.getDebugMenu() != HAMMERBOT_CARD_DEBUG) {
				gameState.setDebugMenu(HAMMERBOT_CARD_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(F6)) {
			if (gameState.getDebugMenu() != SPINBOT_DEBUG) {
				gameState.setDebugMenu(SPINBOT_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(NUM_6)) {
			if (gameState.getDebugMenu() != SPINBOT_CARD_DEBUG) {
				gameState.setDebugMenu(SPINBOT_CARD_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(F7)) {
			if (gameState.getDebugMenu() != HULK_DEBUG) {
				gameState.setDebugMenu(HULK_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(NUM_7)) {
			if (gameState.getDebugMenu() != HULK_CARD_DEBUG) {
				gameState.setDebugMenu(HULK_CARD_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(F8)) {
			if (gameState.getDebugMenu() != TRUNDLEBOT_CARD_DEBUG) {
				gameState.setDebugMenu(TRUNDLEBOT_CARD_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(NUM_8)) {
			if (gameState.getDebugMenu() != TRUNDLEBOT_CARD_DEBUG) {
				gameState.setDebugMenu(TRUNDLEBOT_CARD_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(F12)) {
			if (gameState.getDebugMenu() != GRID_DEBUG) {
				gameState.setDebugMenu(GRID_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
		if (Gdx.input.isKeyJustPressed(F11)) {
			if (gameState.getDebugMenu() != AI_DEBUG) {
				gameState.setDebugMenu(AI_DEBUG);
			} else {
				gameState.setDebugMenu(-1);
			}
		}
	}//getInput
	public void endTurn() {
		gameState.setTurn(gameState.getTurn()+1);
		//reset dead robots with lives remaining
		for (Robots r : roboList) {
			if (r.isDead()) {
				r.setLives(r.getLives()-1);
				if (r.getLives() >0) {
					r.setScale(1.0f);
					r.setDead(false);
					r.setFalling(false);
					r.setX(r.getRespawnX());
					r.setY(r.getRespawnY());
					r.setDrawX(r.getX() *60);
					r.setDrawY(r.getY() *60 + 30);
					r.setDrawY(SCREEN_H - r.getDrawY());
					r.setDirSelected(false);
					r.setCardsSelected(false);
					r.setHealth(8);
					r.setVirtual(true);
				}
			}
		}
		//remove robots with no lives from game
		ArrayList<Robots> roboDeadList = new ArrayList<>(8);
		boolean dead = false;
		for (Robots r : roboList) {
			if (r.getLives() <=0) {
				r.setType(OFF);
				dead = true;
			}
		}
		if (dead) {
			for (Robots r : roboList) {
				if (r.getType() != OFF) {
					roboDeadList.add(r);
				}
			}
			roboList.clear();
			roboList = roboDeadList;
		}
		//reset board elements
		for(Boards bd : boardList) {
			if(bd.isBlueBelts() != NONE ) {
				bd.setBlueBeltsDone(false);
			}
			if(bd.isRedBelts()!=NONE) {
				bd.setRedBeltsDone(false);
			}
			if(bd.isPushers()!=NONE) {
				bd.setPushersDone(false);
			}
			if(bd.isGears()!=NONE) {
				bd.setGearsDone(false);
			}
			if(bd.isLasers()!=NONE) {
				bd.setLasersDone(false);
			}
			if(bd.isFlamers()!=NONE) {
				bd.setFlamersDone(false);
			}
		}

	}//endTurn
	public void getTrueFalse(boolean name , int x, int y) {
		if (name) {
			font.setColor(Color.GREEN);
			font.draw(batch,"true", x, y);
		} else {
			font.setColor(Color.RED);
			font.draw(batch,"false", x, y);
		}
		font.setColor(Color.ORANGE);
	}//getTrueFalse
	public void getOnOffNone(int var, int x, int y) {
		if(var == ON) {
			font.setColor(Color.GREEN);
			font.draw(batch,"ON", x, y);
		}
		if(var == OFF) {
			font.setColor(Color.RED);
			font.draw(batch,"OFF", x, y);
		}
		if(var == NONE) {
			font.setColor(Color.BLUE);
			font.draw(batch,"NONE", x, y);
		}
		font.setColor(Color.ORANGE);
	}//getOnOffNone
	public void gameDebugMenu() {
		//ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(img_blackBox, 800,0);
		batch.end();
		font.getData().setScale(1.0f,1.0f);
		int x = 840;
		int y = 770;

		font.setColor(Color.YELLOW);
		batch.begin();

		font.draw(batch, "Game State:", x, y);
		y-=20;
		font.draw(batch, "Turn: " + gameState.getTurn(), x, y);
		y-=20;
		font.draw(batch, "title:", x, y);
		getTrueFalse(gameState.isTitle(),x+120,y);
		y-=20;
		font.draw(batch, "selectBoard:", x, y);
		getTrueFalse(gameState.isSelectBoard(),x+120,y);
		y-=20;
		font.draw(batch, "robotSelect:", x, y);
		getTrueFalse(gameState.isSelectRobot(),x+120,y);
		y-=20;
		font.draw(batch, "dealCards:", x, y);
		getTrueFalse(gameState.isDealCards(),x+120,y);
		y-=20;
		font.draw(batch, "newGame:", x, y);
		getTrueFalse(gameState.isNewGame(),x+120,y);
		y-=20;
		font.draw(batch, "selectStartingDir:", x, y);
		getTrueFalse(gameState.isSelectStartingDir(),x+120,y);
		y-=20;
		font.draw(batch, "selectCards: ", x, y);
		//getTrueFalse(gameState.getSelectCards(),x+120, y);
		y-=20;
		font.draw(batch, "drawGUI: ", x, y);
		//getTrueFalse(gameState.isDrawGUI(),x+120, y);
		y-=20;
		font.draw(batch, "isGameOn: ", x, y);
		getTrueFalse(gameState.isGameOn(),x+120, y);
		y-=20;


		int x2 = 1000;
		int y2 = 770;
		for(Boards bd : boardList) {
			font.setColor(Color.ORANGE);

			font.draw(batch, "Reg Phase: ", x2, y2);
			font.draw(batch, Integer.toString(gameState.getRegistryPhase()), x2+120, y2);
			y2-=40;

			//blue belts
			font.draw(batch, "Blue phase: ", x2, y2);
			font.draw(batch,Integer.toString(bd.getBlueBeltPhase()), x2+100, y2);
			y2 -= 20;
			font.draw(batch, "Blue Belt: ", x2, y2);
			getOnOffNone(bd.isBlueBelts(), x2+100, y2);
			y2 -= 20;
			font.draw(batch, "Blue Done: ", x2, y2);
			getTrueFalse(bd.isBlueBeltsDone(), x2 + 100, y2);
			y2 -= 40;

			//red belts
			font.draw(batch, "Red Belt: ", x2, y2);
			getOnOffNone(bd.isRedBelts(), x2+100, y2);
			y2 -= 20;
			font.draw(batch, "Red Done: ", x2, y2);
			getTrueFalse(bd.isRedBeltsDone(), x2 + 100, y2);
			y2 -= 40;

			//pushers
			font.draw(batch, "Pushers: ", x2, y2);
			getOnOffNone(bd.isPushers(), x2+100, y2);
			y2 -= 20;
			font.draw(batch, "Pushers Done: ", x2, y2);
			getTrueFalse(bd.isPushersDone(), x2 + 100, y2);
			y2 -= 40;

			//gears
			font.draw(batch, "Gears: ", x2, y2);
			getOnOffNone(bd.isGears(), x2+100, y2);
			y2 -= 20;
			font.draw(batch, "Gears Done: ", x2, y2);
			getTrueFalse(bd.isGearsDone(), x2 + 100, y2);
			y2 -= 40;

			//board lasers
			font.draw(batch, "Lasers: ", x2, y2);
			getOnOffNone(bd.isLasers(), x2+100, y2);
			y2 -= 20;
			font.draw(batch, "Lasers Done: ", x2, y2);
			getTrueFalse(bd.isLasersDone(), x2 + 100, y2);
			y2 -= 40;

			//flamers
			font.draw(batch, "Flamers: ", x2, y2);
			getOnOffNone(bd.isFlamers(), x2+100, y2);
			y2 -= 20;
			font.draw(batch, "Flamers Done: ", x2, y2);
			getTrueFalse(bd.isFlamersDone(), x2 + 100, y2);
			y2 -= 40;

			font.draw(batch, "Delay: ", x2, y2);
			if(bd.getDelay()==ON) {
				font.setColor(Color.GREEN);
				font.draw(batch, "ON", x2+100, y2);
			}
			if(bd.getDelay()==OFF) {
				font.setColor(Color.RED);
				font.draw(batch, "OFF", x2+100, y2);
			}
			if(bd.getDelay()==DONE) {
					font.setColor(Color.BLUE);
					font.draw(batch, "DONE", x2+100, y2);
				}

		}//for(Boards bd : boardList)
		batch.end();

		/*
		for (Cards c : cardList) {
			font.draw(batch, "currentSlot 0: " , x, 350);
			if (cardSlot[0]) {
				font.setColor(Color.GREEN);
				font.draw(batch, "True", x+100, 350);
			} else {
				//font.setColor(Color.RED);
				//font.draw(batch, "False", x+100, 350);
			}

			//y-=20;
			font.draw(batch, "currentSlot 1: " , x, 330);
			if (cardSlot[1]) {
				font.setColor(Color.GREEN);
				font.draw(batch, "True", x+100, 330);
			} else {
				//font.setColor(Color.RED);
				//font.draw(batch, "False", x+100, 330);
			}

			//y-=20;
			font.draw(batch, "currentSlot 2: " , x, 310);
			if (cardSlot[2]) {
				font.setColor(Color.GREEN);
				font.draw(batch, "True", x+100, 310);
			} else {
				//font.setColor(Color.RED);
				//font.draw(batch, "False", x+100, 310);
			}
			//y-=20;
			font.draw(batch, "currentSlot 3: " , x, 290);
			if (cardSlot[3]) {
				font.setColor(Color.GREEN);
				font.draw(batch, "True", x+100, 290);
			} else {
				//font.setColor(Color.RED);
				//font.draw(batch, "False", x+100, 290);
			}
			//y-=20;
			font.draw(batch, "currentSlot 4: " , x, 270);
			if (cardSlot[4]) {
				font.setColor(Color.GREEN);
				font.draw(batch, "True", x+100, 270);
			} else {
				//font.setColor(Color.RED);
				//font.draw(batch, "False", x+100, 270);
			}
		}
		*/

		/*
		for (Cards c : cardList) {
			for (int i = 0; i < 5; i++) {
				font.draw(batch, "pickedSlots " + i + ": ", x, y);
				font.draw(batch, Integer.toString(c.getPickedOrder()), x+100, y);
				y -= 20;
			}
		}
		*/
	}//gameDebugMenu
	public void cardDebugMenu() {
		ScreenUtils.clear(0, 0, 0, 1);
		int x = 0;
		int y = 780;
		font.getData().setScale(1f, 1f);
		batch.begin();
		font.setColor(Color.GREEN);
		font.draw(batch, "name:" ,x, y+20);
		font.draw(batch, "owner:" ,x+150, y+20);
		font.draw(batch, "order:" ,x+250, y+20);
		font.draw(batch, "X" ,x+300, y+20);
		font.draw(batch, "y:" ,x+350, y+20);
		font.draw(batch, "P:" ,x+390, y+20);
		for(Cards c : cardList) {
			//if(!c.getOwner().equalsIgnoreCase("NONE")) {
				font.setColor(Color.GREEN);
				font.draw(batch, c.getName(), x, y);
				font.draw(batch, Integer.toString(c.getPriority()), x + 100, y);

				if (c.getOwner().equalsIgnoreCase("NONE")) {
					font.setColor(Color.BLACK);
				}
				for (Robots r : roboList) {//set color of robot name to robot color
					if (c.getOwner().equalsIgnoreCase(r.getName())) {
						font.setColor(r.getColor());
					}
				}
				font.draw(batch, c.getOwner(), x + 150, y);
				font.draw(batch, Integer.toString(c.getOrder()), x + 250, y);
				font.draw(batch, Integer.toString(c.getAIX()), x + 300, y);
				font.draw(batch, Integer.toString(c.getAIY()), x + 350, y);


				if (c.getPickedOrder() != -1) {
					font.setColor(Color.GREEN);
					font.draw(batch, Integer.toString(c.getPickedOrder()), x + 390, y);
				} else {
					font.setColor(Color.RED);
					font.draw(batch, Integer.toString(c.getPickedOrder()), x + 390, y);
				}


				y -= 20;
				if (y < 40) {
					y = 790;
					x += 420;
				}
			//}
		}
		batch.end();
	}//SelectCards
	public void flagDebugMenu() {
		int x = 840;
		int y = 760;
		font.getData().setScale(1.0f, 1.0f);
		font.setColor(Color.ORANGE);
		batch.begin();
		batch.draw(img_blackBox, 800, 0);
		for(Flags f : flagList) {
			font.draw(batch, "No: " + f.getNo(),x,y);
			y-=20;
			font.draw(batch, "XY: " + f.getX() + "   " + f.getY(),x,y);
			y-=40;
		}
		batch.end();
	}//flagDebugMenu
	public void robotDebugMenu() {
		//ScreenUtils.clear(0, 0, 0, 1);
		int x = 0;
		int y = 790;
		//font.setColor(Color.WHITE);
		batch.begin();
		font.getData().setScale(1.0f, 1.0f);
		for (Robots r : roboList) {
			font.setColor(r.getColor());
			font.draw(batch, r.getName(), x, y);
			y-=40;

			font.draw(batch, "playerType:", x, y);
			switch(r.getType()) {
				case OFF:
					font.setColor(Color.RED);
					font.draw(batch, "OFF", x +100, y);
					break;
				case ON:
					font.setColor(Color.GREEN);
					font.draw(batch, "ON", x +100, y);
					break;
				case CPU:
					font.setColor(Color.BLUE);
					font.draw(batch, "CPU", x +100, y);
					break;
			}
			y-=20;

			font.setColor(r.getColor());
			font.draw(batch, "active:", x, y);
			getTrueFalse(r.getActive(), x + 100, y);
			y-=20;

			font.setColor(r.getColor());
			//font.draw(batch, "X: " + Integer.toString(r.getX()), x, y);
			y-=20;

			font.setColor(r.getColor());
			//font.draw(batch, "Y: " + Integer.toString(r.getY()), x, y);
			y-=20;

			font.setColor(r.getColor());
			//font.draw(batch, "drawX: " + Integer.toString(r.getDrawX()), x, y);
			y-=20;

			font.setColor(r.getColor());
			//font.draw(batch, "drawY: " + Integer.toString(r.getDrawY()), x, y);
			y-=40;

			font.draw(batch, "tile: " + r.getTileType(), x, y);
			font.setColor(r.getColor());
			y-=20;

			font.draw(batch, "Move Type:" + Integer.toString(r.getMoveType()), x, y);
			y-=20;

			font.setColor(r.getColor());
			font.draw(batch, "lives: " + r.getLives(), x, y);
			y-=20;

			font.draw(batch, "health: " + r.getHealth(), x, y);
			y-=20;

			font.draw(batch, "virtual: " + r.isVirtual(), x, y);
			y-=20;

			font.draw(batch, "dir_sel: ", x, y);
			getTrueFalse(r.isDirSelected(), x +100, y);
			y-=20;

			font.setColor(r.getColor());
			font.draw(batch, "cards_sel: ", x, y);
			getTrueFalse(r.getCardsSelected(), x +100, y);

			y-=20;
			font.setColor(r.getColor());
			font.draw(batch, "frame: ", x, y);
			switch(r.getFacingDir()) {
				case UP1:
					font.draw(batch, "UP", x+100, y);
					break;
				case DOWN1:
					font.draw(batch, "DOWN", x+100, y);
					break;
				case LEFT1:
					font.draw(batch, "LEFT", x+100, y);
					break;
				case RIGHT1:
					font.draw(batch, "RIGHT", x+100, y);
					break;
			}
			y-=20;

			font.draw(batch, "scale: " + r.getScale(), x, y);
			y-=20;

			font.draw(batch, "dead: ", x, y);
			getTrueFalse(r.isDead(), x + 100, y);
			y-=20;

			font.setColor(r.getColor());
			font.draw(batch, "cardsHeld: " + r.getCardsHeld(), x, y);
			y-=20;

			font.draw(batch, "Move DST:", x, y);
			//font.draw(batch, Integer.toString(r.getMoveDistance()), x+100, y);
			y-=20;

			font.draw(batch, "Turn AMNT:.", x, y);
			font.draw(batch, Integer.toString(r.getTurnAmount()), x+100, y);
			y-=20;

			font.draw(batch, "bounce dir:", x, y);
			if(r.getMoveType() == BOUNCE_FORWARD) {
				font.draw(batch, "FORWARD", x+100, y);
			} else if (r.getMoveType() == BOUNCE_BACK) {
				font.draw(batch, "BACK", x+100, y);
			} else {
				font.draw(batch, "NONE", x+100, y);
			}
			y-=20;

			font.draw(batch, "bounce dist:", x, y);
			font.draw(batch, Integer.toString(r.getBounceDistance()), x+100, y);
			y-=20;

			x += 160;
			y = 790;
		}//for (Robots r : roboList)
		batch.end();
	}//robotDebugMenu
	public void singleRobotDebug(String name) {
		//ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(img_blackBox, 800,0);
		batch.end();
		font.getData().setScale(1f, 1f);
		int x = 840;
		int y = 770;
		for(Robots r : roboList) {
			font.setColor(r.getColor());
			if (name.equalsIgnoreCase(r.getName())) {
				batch.begin();
				font.draw(batch, r.getName(),x, y);
				y-=40;
				font.draw(batch, "Type: " + Integer.toString(r.getType()), x, y);
				y-=20;
				font.draw(batch, "Active: " + Boolean.toString(r.getActive()), x, y);
				y-=20;
				font.draw(batch, "Virtual: " + Boolean.toString(r.isVirtual()), x, y);
				y-=20;
				font.draw(batch, "X : " + r.getX() + " Y: " + r.getY() + "    " + r.getDrawX() + "    " + r.getDrawY(), x, y);
				y-=20;
				font.draw(batch, "Facing: " + Integer.toString(r.getFacingDir()), x, y);
				y-=20;
				//font.draw(batch, "Frame: " + Integer.toString(r.getFrame()), x, y);
				//y-=20;
				font.draw(batch, "Lives: " + r.getLives(), x, y);
				y-=20;
				font.draw(batch, "Health: " + r.getHealth(), x, y);
				y-=20;
				font.draw(batch, "Dead: " + r.isDead(), x, y);
				y-=40;

				font.draw(batch, "Dir Sel.: " + Boolean.toString(r.isDirSelected()), x, y);
				y-=20;
				font.draw(batch, "Cards Sel.: " + Boolean.toString(r.getCardsSelected()), x, y);
				y-=40;
				font.draw(batch, "movedir: " + r.getMoveDirection() , x, y);
				y-=20;
				font.draw(batch, "moveType: " + r.getMoveType(),x,y);// + "    T:" + r.getTurnAmount() , x, y);
				y-=20;
				font.draw(batch, "moveDist: " + r.getMoveDistance(),x,y);// + "    T:" + r.getTurnAmount() , x, y);
				y-=20;
				font.draw(batch, "turnAmount: " + r.getTurnAmount(),x,y);// + "    T:" + r.getTurnAmount() , x, y);
				y-=20;

				/*
				font.draw(batch, "bounceDir: " ,x,y);// + "    T:" + r.getTurnAmount() , x, y);
				if(r.getMoveType()==BOUNCE_FORWARD){
					font.draw(batch, "FORWARD" ,x+100,y);// + "    T:" + r.getTurnAmount() , x, y);
				} else if (r.getMoveType()==BOUNCE_BACK) {
					font.draw(batch, "BACK" ,x+100,y);// + "    T:" + r.getTurnAmount() , x, y);
				} else {
					font.draw(batch, r.get ,x+100,y);// + "    T:" + r.getTurnAmount() , x, y);
				}
				y-=20;
				*/

				font.draw(batch, "bounceDist: " + r.getBounceDistance(),x,y);// + "    T:" + r.getTurnAmount() , x, y);
				y-=40;
				font.draw(batch, "tileType: " + r.getTileType() , x, y);
				y-=40;
				font.draw(batch, "BLK_UP: " + Boolean.toString(r.isBlockedUp()) , x, y);
				y-=20;
				font.draw(batch, "BLK_DOWN: " + Boolean.toString(r.isBlockedDown()) , x, y);
				y-=20;
				font.draw(batch, "BLK_LEFT: " + Boolean.toString(r.isBlockedLeft()) , x, y);
				y-=20;
				font.draw(batch, "BLK_RIGHT: " + Boolean.toString(r.isBlockedRight()) , x, y);
				x=1030;
				y=770;

				//col2
				font.draw(batch, "Target: " + Integer.toString(r.getCurrentTarget()) , x, y);
				y-=20;
				font.draw(batch, "Target XY: " + Integer.toString(r.getTargetX()) + "   " + Integer.toString(r.getTargetY()), x,y);
				y-=40;

				font.draw(batch, "falling: " + Boolean.toString(r.isFalling()) , x, y);
				y-=20;
				font.draw(batch, "scale: " + r.getScale() , x, y);
				y-=20;
				font.draw(batch, "spawnX: " + r.getRespawnX() , x, y);
				y-=20;
				font.draw(batch, "spawnY: " + r.getRespawnY() , x, y);
				y=780;

				int cardX = 840;
				int cardY = 240;//160
				font.draw(batch, "Cards", cardX, cardY);
				cardY -=20;
				font.draw(batch, "#" + "       Name", cardX, cardY);
				font.draw(batch, "Active", cardX +130, cardY);
				font.draw(batch, "Locked", cardX +180, cardY);
				font.draw(batch, "#", cardX +240, cardY);
				font.draw(batch, "Played", cardX +270, cardY);
				font.draw(batch, "Score", cardX +340, cardY);
				cardY -=20;
				for (Cards c : cardList) {
					if (c.getOwner().equalsIgnoreCase(r.getName())) {
						font.draw(batch, Integer.toString(c.getPriority()) + "   " + c.getName(), cardX, cardY);
						font.draw(batch, Boolean.toString(c.getActive()), cardX + 130, cardY);
						font.draw(batch, Boolean.toString(c.getLocked()), cardX + 180, cardY);
						font.draw(batch, Integer.toString(c.getPickedOrder()), cardX + 240, cardY);
						font.draw(batch, Boolean.toString(c.getPlayed()), cardX + 270, cardY);
						font.draw(batch, Integer.toString(c.getScore()), cardX + 340, cardY);
						cardY -= 20;

						/*
						if (c.getPickedOrder() == 0) {
							font.draw(batch, Integer.toString(c.getPriority()) + "   " + c.getName(), cardX, cardY);
							font.draw(batch, Boolean.toString(c.getActive()), cardX + 130, cardY);
							font.draw(batch, Boolean.toString(c.getLocked()), cardX + 180, cardY);
							font.draw(batch, Integer.toString(c.getPickedOrder()), cardX + 240, cardY);
							font.draw(batch, Boolean.toString(c.getPlayed()), cardX + 270, cardY);
							font.draw(batch, Integer.toString(c.getScore()), cardX + 340, cardY);
							cardY -= 20;
						}
						if (c.getPickedOrder() == 1) {
							font.draw(batch, Integer.toString(c.getPriority()) + "   " + c.getName(), cardX, cardY);
							font.draw(batch, Boolean.toString(c.getActive()), cardX + 130, cardY);
							font.draw(batch, Boolean.toString(c.getLocked()), cardX + 180, cardY);
							font.draw(batch, Integer.toString(c.getPickedOrder()), cardX + 240, cardY);
							font.draw(batch, Boolean.toString(c.getPlayed()), cardX + 270, cardY);
							font.draw(batch, Integer.toString(c.getScore()), cardX + 340, cardY);
							cardY -= 20;
						}
						if (c.getPickedOrder() == 2) {
							font.draw(batch, Integer.toString(c.getPriority()) + "   " + c.getName(), cardX, cardY);
							font.draw(batch, Boolean.toString(c.getActive()), cardX + 130, cardY);
							font.draw(batch, Boolean.toString(c.getLocked()), cardX + 180, cardY);
							font.draw(batch, Integer.toString(c.getPickedOrder()), cardX + 240, cardY);
							font.draw(batch, Boolean.toString(c.getPlayed()), cardX + 270, cardY);
							font.draw(batch, Integer.toString(c.getScore()), cardX + 340, cardY);
							cardY -= 20;
						}
						if (c.getPickedOrder() == 3) {
							font.draw(batch, Integer.toString(c.getPriority()) + "   " + c.getName(), cardX, cardY);
							font.draw(batch, Boolean.toString(c.getActive()), cardX + 130, cardY);
							font.draw(batch, Boolean.toString(c.getLocked()), cardX + 180, cardY);
							font.draw(batch, Integer.toString(c.getPickedOrder()), cardX + 240, cardY);
							font.draw(batch, Boolean.toString(c.getPlayed()), cardX + 270, cardY);
							font.draw(batch, Integer.toString(c.getScore()), cardX + 340, cardY);
							cardY -= 20;
						}
						if (c.getPickedOrder() == 4) {
							font.draw(batch, Integer.toString(c.getPriority()) + "   " + c.getName(), cardX, cardY);
							font.draw(batch, Boolean.toString(c.getActive()), cardX + 130, cardY);
							font.draw(batch, Boolean.toString(c.getLocked()), cardX + 180, cardY);
							font.draw(batch, Integer.toString(c.getPickedOrder()), cardX + 240, cardY);
							font.draw(batch, Boolean.toString(c.getPlayed()), cardX + 270, cardY);
							font.draw(batch, Integer.toString(c.getScore()), cardX + 340, cardY);
							cardY -= 20;
						}
						*/
					}
				}
				batch.end();
			}
		}
	}//singleRobotDebug
	public void singleRobotCardDebug(String name) {
		batch.begin();
		batch.draw(img_blackBox, 800,0);
		batch.end();
		font.getData().setScale(1f, 1f);
		int x = 840;
		int y = 730;

		batch.begin();
		font.draw(batch, "PRI:", x,770);
		font.draw(batch, "NAME:", x+40,770);
		font.draw(batch, "P/O:", x+120,770);
		font.draw(batch, "AIX:", x+170,770);
		font.draw(batch, "AIY:", x+220,770);
		font.draw(batch, "SCORE:", x+270,770);
		batch.end();

		for(Robots r : roboList) {
			for(Cards c : cardList) {
				if((r.getName()==name) && (c.getOwner().equalsIgnoreCase(name))) {
					font.setColor(r.getColor());
					batch.begin();
					font.draw(batch, Integer.toString(c.getPriority()), x,y);
					font.draw(batch, c.getName(), x+40,y);
					font.draw(batch, Integer.toString(c.getPickedOrder()), x+120,y);
					font.draw(batch, Integer.toString(c.getAIX()), x+170,y);
					font.draw(batch, Integer.toString(c.getAIY()), x+220,y);
					font.draw(batch, Integer.toString(c.getScore()), x+270,y);

					//font.draw(batch, Integer.toString(c.getScore()), 100,100);
					batch.end();
					y-=20;
				}
			}
		}
	}
	public boolean anyRobotsActive() {
		boolean anyRobotActive = false;
		for (Robots r : roboList) {
			if (r.getActive()) {
				anyRobotActive = true;
			}
		}
		return anyRobotActive;
	}//anyRobotsActive
	/*
	public boolean anyCardsActive() {
		boolean anyCardsActive = false;
		for (Cards c : cardList) {
			if (c.getActive()) {
				anyCardsActive = true;
			}
		}
		return anyCardsActive;
	}//anyCardsActive
	*/
	public boolean allRobotsDoneProgramming() {
		boolean allRobotsReady = true;
		for (Robots r : roboList) {
			if ((!r.isDirSelected()) || (!r.getCardsSelected())) {
				allRobotsReady = false;
			}
		}
		return allRobotsReady;
	}//allRobotProgrammingDone
	public boolean allCardsPlayed() {
		boolean allCardsPlayed = true;
		for (int loop = 0; loop < 5; loop++) {
			if (gameState.getRegistryPhase() == loop) {
				for (Cards c : cardList) {
					if (c.getPickedOrder() == loop) {
						if (!c.getPlayed()) {
							allCardsPlayed = false;
						}
					}
				}
			}
		}
		return allCardsPlayed;
	}//allCardsPlayed
	public boolean areBoardElementsMoving() {
		boolean areBoardElementsMoving = false;
		for(Boards bd : boardList) {
			if (bd.isBlueBelts() == ON) {
				areBoardElementsMoving = true;
			}
			if (bd.isRedBelts() == ON) {
				areBoardElementsMoving = true;
			}
			if (bd.isPushers() == ON) {
				areBoardElementsMoving = true;
			}
			if (bd.isGears() == ON) {
				areBoardElementsMoving = true;
			}
			if (bd.isFlamers() == ON) {
				areBoardElementsMoving = true;
			}
			if (bd.isLasers() == ON) {
				areBoardElementsMoving = true;
			}
		}
		return areBoardElementsMoving;
	}//areBoardElementsMoving
	public boolean allBoardElementsDone() {
		boolean allBoardElementsDone = true;
		for(Boards bd : boardList) {
			if((bd.isBlueBelts() != NONE) && (!bd.isBlueBeltsDone())) {
				allBoardElementsDone = false;
			}
			if((bd.isRedBelts() != NONE) && (!bd.isRedBeltsDone())) {
				allBoardElementsDone = false;
			}
			if((bd.isPushers()!=NONE) && (!bd.isPushersDone())) {
				allBoardElementsDone = false;
			}
			if((bd.isGears()!=NONE) && (!bd.isGearsDone())) {
				allBoardElementsDone = false;
			}
			if((bd.isFlamers()!=NONE) && (!bd.isFlamersDone())) {
				allBoardElementsDone = false;
			}
			if((bd.isLasers()!=NONE) && (!bd.isLasersDone())) {
				allBoardElementsDone = false;
			}
		}
		return allBoardElementsDone;
	}//allBoardElementsDone
	public boolean animationPlaying() {
		boolean animationPlaying = false;
		for(Robots r : roboList) {
			if(r.isFalling()) {
				animationPlaying=true;
			}
		}
		return animationPlaying;
	}
	public void AIdebug() {
		font.getData().setScale(1.5f,1.5f);
		font.setColor(Color.CHARTREUSE);
		for (Tiles t : tileList) {//show tile coordinates
			batch.begin();
			font.draw(batch, Integer.toString(t.getAIscore()), t.getX() * 60, SCREEN_H - t.getY() * 60);
			//font.draw(batch, "AI:: " + t.getAIscore(), t.getX() * 60-10, SCREEN_H - t.getY() * 60-20);
			batch.end();
		}
	}
	public void showGrid() {
		font.getData().setScale(1.0f,1.0f);
		font.setColor(Color.ORANGE);
		batch.begin();//show grid number
		for (int x = 1; x < 13; x++) {
			font.draw(batch, Integer.toString(x), x*60 + 15, 780);
			font.draw(batch, Integer.toString(x), x*60 + 15, 30);
		}
		for (int y = 1; y < 13; y++) {
			font.draw(batch, Integer.toString(y), 30, SCREEN_H - y*60);
			font.draw(batch, Integer.toString(y), 780, SCREEN_H - y*60);
		}
		batch.end();

		//font.setColor(Color.RED);
		for (Tiles t : tileList) {//show tile coordinates
			batch.begin();
			font.draw(batch, "X: " + t.getX(), t.getX() * 60-10, SCREEN_H - t.getY() * 60+20);
			font.draw(batch, "Y: " + t.getY(), t.getX() * 60-10, SCREEN_H - t.getY() * 60);
			//font.draw(batch, "AI:: " + t.getAIscore(), t.getX() * 60-10, SCREEN_H - t.getY() * 60-20);
			batch.end();
		}
	}//showGrid
	public void showMouseXY() {
		//show mouse co-ords
		mouseX = Gdx.input.getX();
		mouseY = Gdx.input.getY();
		String mx = Integer.toString(mouseX);
		String my = Integer.toString(mouseY);
		batch.begin();
		font.getData().setScale(1.0f,1.0f);
		font.setColor(com.badlogic.gdx.graphics.Color.YELLOW);
		font.draw(batch,"X: " + mx,1230,40);
		font.draw(batch,"Y: " + my,1230,20);
		batch.end();
		//show individual tile info on mouseover
		int tmpX = 840;
		int tmpY = 760;
		font.setColor(com.badlogic.gdx.graphics.Color.ORANGE);
		for(Tiles t : tileList) {
			if ((mouseX > t.getX() * 60 - 10) && (mouseX < t.getX() * 60 + 50)) {
				if ((mouseY > t.getY() * 60 - 20) && (mouseY < t.getY() * 60 + 40)) {
					//while (Gdx.input.isButtonJustPressed(0)) {
						batch.begin();
						batch.draw(img_blackBox, 800, 0);

						font.draw(batch, "Name: ", tmpX, tmpY);
						font.draw(batch, t.getName(), tmpX + 100, tmpY);
						tmpY -= 40;

						font.draw(batch, "X: " + t.getX() + " Y: " + t.getY(), tmpX, tmpY);
						//font.draw(batch,t.getName(),tmpX+100,tmpY);
						tmpY -= 20;

						if (t.isUpBlocked()) {
							font.draw(batch, "BLK. Up: ", tmpX, tmpY);
							getTrueFalse(t.isUpBlocked(), tmpX + 100, tmpY);
							tmpY -= 20;
						}
						if (t.isDownBlocked()) {
							font.draw(batch, "BLK. Down: ", tmpX, tmpY);
							getTrueFalse(t.isDownBlocked(), tmpX + 100, tmpY);
							tmpY -= 20;
						}
						if (t.isLeftBlocked()) {
							font.draw(batch, "BLK. Left: ", tmpX, tmpY);
							getTrueFalse(t.isLeftBlocked(), tmpX + 100, tmpY);
							tmpY -= 20;
						}
						if (t.isRightBlocked()) {
							font.draw(batch, "BLK. Right: ", tmpX, tmpY);
							getTrueFalse(t.isRightBlocked(), tmpX + 100, tmpY);
							tmpY -= 40;
						}

						if (t.isUpRed()) {
							font.draw(batch, "Red Up: ", tmpX, tmpY);
							getTrueFalse(t.isUpRed(), tmpX + 100, tmpY);
							tmpY -= 20;
						}
						if (t.isDownRed()) {
							font.draw(batch, "Red Down: ", tmpX, tmpY);
							getTrueFalse(t.isDownRed(), tmpX + 100, tmpY);
							tmpY -= 20;
						}
						if (t.isLeftRed()) {
							font.draw(batch, "Red Left: ", tmpX, tmpY);
							getTrueFalse(t.isLeftRed(), tmpX + 100, tmpY);
							tmpY -= 20;
						}
						if (t.isRightRed()) {
							font.draw(batch, "Red Right: ", tmpX, tmpY);
							getTrueFalse(t.isRightRed(), tmpX + 100, tmpY);
							tmpY -= 40;
						}

						if (t.isUpBlue()) {
							font.draw(batch, "Blue Up: ", tmpX, tmpY);
							getTrueFalse(t.isUpBlue(), tmpX + 100, tmpY);
							tmpY -= 20;
						}
						if (t.isDownBlue()) {
							font.draw(batch, "Blue Down: ", tmpX, tmpY);
							getTrueFalse(t.isDownBlue(), tmpX + 100, tmpY);
							tmpY -= 20;
						}
						if (t.isLeftBlue()) {
							font.draw(batch, "Blue Left: ", tmpX, tmpY);
							getTrueFalse(t.isLeftBlue(), tmpX + 100, tmpY);
							tmpY -= 20;
						}
						if (t.isRightBlue()) {
							font.draw(batch, "Blue Right: ", tmpX, tmpY);
							getTrueFalse(t.isRightBlue(), tmpX + 100, tmpY);
							tmpY -= 40;
						}

						font.draw(batch, "Rotation: ", tmpX, tmpY);
						font.draw(batch, Float.toString(t.getRotation()), tmpX + 100, tmpY);
						tmpY -= 40;

						if (t.getCanPlaceFlag()) {
							font.draw(batch, "Can place flag: ", tmpX, tmpY);
							getTrueFalse(t.getCanPlaceFlag(), tmpX + 100, tmpY);
							tmpY -= 40;
						}
						font.draw(batch, "AI SCORE: " + t.getAIscore(), tmpX, tmpY);
						batch.end();
					//}
				}
			}
		}//tile loop

		//pushers
		for(Pushers p : pusherList) {
			if ((mouseX > p.getX() * 60 - 10) && (mouseX < p.getX() * 60 + 50)) {
				if ((mouseY > p.getY() * 60 - 20) && (mouseY < p.getY() * 60 + 40)) {
					batch.begin();
					font.draw(batch, "PUSHER: ", tmpX, tmpY);
					tmpY-=20;
					font.draw(batch, "Direction: ", tmpX, tmpY);
					switch(p.getDirection()) {
						case 0:
							font.draw(batch, "Up",tmpX+100, tmpY);
							break;
						case -180:
							font.draw(batch, "Down",tmpX+100, tmpY);
							break;
						case -90:
							font.draw(batch, "Right",tmpX+100, tmpY);
							break;
						case -270:
							font.draw(batch, "Left",tmpX+100, tmpY);
							break;
					}
					tmpY-=20;
					if(p.isActive()) {
						font.draw(batch, "Active: ", tmpX, tmpY);
						getTrueFalse(p.isActive(), tmpX + 100, tmpY);
						tmpY-=20;
					}
					batch.end();
				}
			}
		}//pusher loop

		for(Flamers f : flamerList) {
			if ((mouseX > f.getX() * 60 - 10) && (mouseX < f.getX() * 60 + 50)) {
				if ((mouseY > f.getY() * 60 - 20) && (mouseY < f.getY() * 60 + 40)) {
					batch.begin();
					font.draw(batch, "FLAMER: ", tmpX, tmpY);
					tmpY-=20;
					font.draw(batch, "Direction: ", tmpX, tmpY);
					switch(f.getDirection()) {
						case 0:
							font.draw(batch, "Up",tmpX+100, tmpY);
							break;
						case -180:
							font.draw(batch, "Down",tmpX+100, tmpY);
							break;
						case -90:
							font.draw(batch, "Right",tmpX+100, tmpY);
							break;
						case -270:
							font.draw(batch, "Left",tmpX+100, tmpY);
							break;
					}
					tmpY-=40;
					batch.end();
				}
			}
		}
	}//showMouseXY
	@Override
	public void dispose () {
		batch.dispose();
	}
}
