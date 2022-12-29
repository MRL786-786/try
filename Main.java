//Authors: Sam Romain & Mahdi Lakhani
//File Name: Main.java
//Project Name: PASS2
//Creation Date: 12 December 2022
//Description: Infinite Runner Game  

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;

import Engine.Core.*;
import Engine.Gfx.*;

import java.util.ArrayList;

public class Main extends AbstractGame {
  // Required Basic Game Functional Data
  private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
  private static int screenWidth = device.getDisplayMode().getWidth();
  private static int screenHeight = device.getDisplayMode().getHeight();
  private static int windowWidth;
  private static int windowHeight;

  // Store how many milliseconds are in one second
  private static final float SECOND = 1000f;
  private static final int TITLE_BAR_HEIGHT = 25;

  // Game States - Add/Remove/Modify as needed
  // These are the most common game states, but modify as needed
  // You will ALSO need to modify the two switch statements in Update and Draw
  private static final int MENU = 0;
  private static final int SETTINGS = 1;
  private static final int INSTRUCTIONS = 2;
  private static final int GAMEPLAY = 3;
  private static final int PAUSE = 4;
  private static final int ENDGAME = 5;

  // Required Basic Game Visual data used in main below
  private static String gameName = "MARIO RUNNER";
  private static int fps = 60;

  // Store and set the initial game state, typically MENU to start
  private int gameState = MENU;

  /////////////////////////////////////////////////////////////////////////////////////
  // Define your Global variables and constants here (They do NOT need to be
  ///////////////////////////////////////////////////////////////////////////////////// static)
  ///////////////////////////////////////////////////////////////////////////////////// //
  /////////////////////////////////////////////////////////////////////////////////////
  // demonstrate moving object
  private static final int XINI = 10;
  private static final int YINI = 6;
  private static int x_pos = XINI;
  private static int y_pos = YINI;
  // font
  Font textFfont = new Font("Arial", Font.BOLD + Font.ITALIC, 50);
  private static String locMsg;

  // loading images
  final int LEFT = 1;
  final int RIGHT = -1;
  final int STOP = 0;

private static String intMsg = "Score";

  
  SpriteSheet marioBck1;
  SpriteSheet marioBck2;
  SpriteSheet zeldaTitleBck;
  SpriteSheet Endgameplay;
  SpriteSheet Ob1;
  SpriteSheet Ob11;
  SpriteSheet Ob2;
  SpriteSheet Ob22;

  Vector2F mario1Pos;
  Vector2F mario2Pos;
  Vector2F GameandWatchImgPos;
Vector2F GameandWatchImgPosJump;
  Vector2F Ob1Pos;
  Vector2F Ob11Pos;
  Vector2F Ob2Pos;
  Vector2F Ob22Pos;
  
  float scrollSpeed = 5f;
  int xDir = STOP;
  SpriteSheet GameandWatchImg;
  Rectangle GameandWatchRec;
  Rectangle Ob1Rec;
  Rectangle Ob2Rec;
  Rectangle Ob11Rec;
  Rectangle Ob22Rec;
  int GameAndWatchWidth;
  int GameAndWatchHeight;

  int  X = (int) windowHeight/2 + 160;
  int   X2 = (int) windowHeight/2 + 190;
  
  int GameAndWatchX = 130;
  int GameAndWatchY = windowHeight/3;
  int Ob1X = 200;
  int Ob1Y = X2;
  int Ob2X = 200;
  int Ob2Y = X;
  int ObWidth = 50;
  int Ob1Height = 50;
  int Ob2Height = 100;

  static final int PLAY = 1;
  static final int EXIT = 2;
  static final float MENU_DELTA_Y = 80f;

  Font textFont = new Font("Arial", Font.BOLD + Font.ITALIC, 25);
  int menuOption = PLAY;
  SpriteSheet indicatorImg;

final float GRAVITY = 9.8f/fps;
  float maxSpeed = 5f;
  Vector2F playerSpeed = new Vector2F(0f,0f);
   float accel = 0.2f;
  float friction = accel * 0.5f;
  float tolerance = friction * 0.9f;
  Vector2F forces = new Vector2F(friction, GRAVITY);
   float jumpSpeed = -7f;
  boolean grounded = false;
  double score =0;

  public static void main(String[] args) {
    /***********************************************************************
     * DO NOT TOUCH THIS SECTION
     ***********************************************************************/
    windowWidth = screenWidth;
    windowHeight = screenHeight - TITLE_BAR_HEIGHT;

    GameContainer gameContainer = new GameContainer(new Main(), gameName, screenWidth, screenHeight, fps);
    gameContainer.Start();
  }

  public void LoadContent(GameContainer gc) {
    // TODO: This subprogram automatically happens only once, just before the actual
    // game loop starts.
    // It should never be manually called, the Engine will call it for you.
    // Load images, sounds and set up any data

    // Test your Window size by uncommenting and running this command
    // System.out.println("Window Size: " + windowWidth + "x" + windowHeight);

    indicatorImg = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/Arrow.png"));
    indicatorImg.destRec = new Rectangle(windowWidth/3, windowHeight / 2, 100, 100);

    marioBck1 = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/MarioBck.png"));
    marioBck2 = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/MarioBck.png"));
    GameandWatchImg = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/GameWatch.png"), 1, 3, 0, 3, 20);

     Ob1 = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/ob1.png"));
     Ob11 = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/ob1.png"));
 Ob2 = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/ob2.png"));
     Ob22 = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/ob2.png"));
    
    zeldaTitleBck = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/ZeldaBck.png"));

 Endgameplay = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/Endgameplay.png"));


    
    zeldaTitleBck.destRec = new Rectangle(0, 0, windowWidth, windowHeight);
   
    GameandWatchImg.destRec = new Rectangle(GameAndWatchX, GameAndWatchY, 65,65);
    GameandWatchImg.StartAnimation();

GameandWatchRec = GameandWatchImg.destRec;
GameAndWatchWidth = (int) (GameandWatchImg.GetFrameWidth() * 8);
GameAndWatchHeight = (int) (GameandWatchImg.GetFrameHeight() * 8);
    
    marioBck1.destRec = new Rectangle(0, 0, windowWidth, windowHeight);
    marioBck2.destRec = new Rectangle(windowWidth, 0, windowWidth, windowHeight);

      Endgameplay.destRec = new Rectangle(0, 0, windowWidth, windowHeight);
    
    mario1Pos = new Vector2F(marioBck1.destRec.x, marioBck1.destRec.y);
    mario2Pos = new Vector2F(marioBck2.destRec.x, marioBck2.destRec.y);
    GameandWatchImgPos = new Vector2F(GameandWatchImg.destRec.x, GameandWatchImg.destRec.y);

    
    


     Ob1.destRec = new Rectangle(220,X2, ObWidth, Ob1Height);
     Ob11.destRec = new Rectangle(220,X2, ObWidth, Ob1Height);
     Ob2.destRec = new Rectangle(220,X, ObWidth, Ob2Height);
     Ob22.destRec = new Rectangle(220,X, ObWidth, Ob2Height);

Ob1Rec = Ob1.destRec;
    Ob2Rec = Ob2.destRec;
    Ob11Rec = Ob11.destRec;
    Ob22Rec = Ob22.destRec;
    
    Ob1Pos = new Vector2F(Ob1.destRec.x, Ob1.destRec.y);
      Ob11Pos = new Vector2F(Ob11.destRec.x, Ob11.destRec.y);
     Ob2Pos = new Vector2F(Ob2.destRec.x, Ob2.destRec.y);
      Ob22Pos = new Vector2F(Ob22.destRec.x, Ob22.destRec.y);


  }

  public void Update(GameContainer gc, float deltaTime) {
    // TODO: Add your update logic here, including user input, movement, physics,
    // collision, ai, sound, etc.

    // By using a switch statement for game states here, the program
    // can and will only do the UPDATING for the current state of the game
    // NOTE: Some games may have more or less game states, so you will
    // need to add or remove cases (and state constants) as necessary
    switch (gameState) {
      case MENU:
        UpdateMenu(gc);
        break;
      case SETTINGS:
        UpdateSettings();
        break;
      case INSTRUCTIONS:
        UpdateInstructions();
        break;
      case GAMEPLAY:
        UpdateGameplay(deltaTime);
        break;
      case PAUSE:
        UpdatePause();
        break;
      case ENDGAME:
        UpdateEndgame();
        break;
    }
    ScrollScreen(marioBck1, marioBck2, mario1Pos, mario2Pos, 50);

     ScrollScreen(Ob2, Ob22, Ob2Pos, Ob22Pos, 50);
    ScrollScreen(Ob1, Ob11, Ob1Pos, Ob11Pos, 0);
     

  }

  public void Draw(GameContainer gc, Graphics2D gfx) {
    // TODO: Add your draw logic here
    // The only other logic here should be selection logic (everything else should
    // be in Update)
    // By using a switch statement for game states here, the program
    // can and will only do the DRAWING for the current state of the game
    // NOTE: Some games may have more or less game states, so you will
    // need to add or remove cases (and state constants) as necessary
    switch (gameState) {
      case MENU:
        DrawMenu(gfx);
        break;
      case SETTINGS:
        DrawSettings(gfx);
        break;
      case INSTRUCTIONS:
        DrawInstructions(gfx);
        break;
      case GAMEPLAY:
        DrawGameplay(gfx);
        break;
      case PAUSE:
        DrawPause(gfx);
        break;
      case ENDGAME:
        DrawEndgame(gfx);
        break;
    }
  }

  private void UpdateMenu(GameContainer gc) {
    if (Input.IsKeyReleased(KeyEvent.VK_UP) && menuOption == EXIT) {
      menuOption = PLAY;
      indicatorImg.destRec.y -= MENU_DELTA_Y;
    } else if (Input.IsKeyReleased(KeyEvent.VK_DOWN) && menuOption == PLAY) {
      menuOption = EXIT;
      indicatorImg.destRec.y += MENU_DELTA_Y;
    }

    // Trigger the chosen menu option on either the space bar or enter key
    if (Input.IsKeyReleased(KeyEvent.VK_SPACE) || Input.IsKeyReleased(KeyEvent.VK_ENTER)) {
      switch (menuOption) {
        case PLAY:
          // Setup the first round
          gameState = GAMEPLAY;
          break;
        case EXIT:
          // Setup the first round
          gameState = ENDGAME;
          break;
      }
    }

  }

  private void UpdateSettings() {
    // Get and handle input on the settings screen as necessary
  }

  private void UpdateInstructions() {
    // Get and handle input on the instructions screen as necessary
  }

  private void UpdateGameplay(float deltaTime) {
    // This is where the core of your game logic will be:
    // - Get user input
    // - apply any new user instructions, e.g. start jumping, pause game, etc.
    // - Update active game objects, e.g. moving enemies, bullets, etc.
    // - Update any environment objects, e.g. moving platforms, etc.
    // - Handle collisions and resulting consequences of collisions
    // - Update any user interface data (HUD)
    // - Anything else needed for your specific game
    mario1Pos.x = mario1Pos.x + -3;
    mario2Pos.x = mario2Pos.x + -3;
     Ob1Pos.x = Ob1Pos.x + -7;
    Ob2Pos.x = Ob2Pos.x + -3;
    

 if (Input.IsKeyPressed(KeyEvent.VK_SPACE) && grounded == true)
    {
      playerSpeed.y = jumpSpeed ;
   
    }

    //Add gravity to the player's y Speed (even if not jumping, to allow for falling)
    playerSpeed.y += forces.y;

    //Move the player using its current speed and position
    MoveGameObject(GameandWatchImg, GameandWatchImgPos, playerSpeed);
     PlayerWallCollision();
    Ob1Collision();

    // for (int i =0; i>10; i++){
    score = score+0.25;
    // }
    intMsg = " Score : " + score;
  }

  private void UpdatePause() {
    // Handle user input to unpause the menu
  }

  private void UpdateEndgame() {
    // Get and handle input on the end game screen as necessary
if (Input.IsKeyReleased(KeyEvent.VK_R)){
   gameState = GAMEPLAY;
  GameandWatchImgPos.y = Ob2Pos.x-80;
  score = 0;
}
    
  }

  private void DrawMenu(Graphics2D gfx) {
    // Draw the game menu screen, including pointers to menu options
    Draw.Sprite(gfx, zeldaTitleBck);
    Draw.Sprite(gfx, indicatorImg);

  }

  private void DrawSettings(Graphics2D gfx) {
    // Draw the settings screen
  }

  private void DrawInstructions(Graphics2D gfx) {
    // Draw the instructions screen
  }

  private void ScrollScreen(SpriteSheet img1, SpriteSheet img2, Vector2F pos1, Vector2F pos2, float speed) {
    // Move the true positions of the screens in the set direction
    pos1.x = pos1.x + (xDir * speed);
    pos2.x = pos2.x + (xDir * speed);

    // NOTE: We are using an if else if statement here because it can only ever
    // be one of the scenarios at a time and we don't want to separate left from
    // right
    // because the left may shift it to the right and then the right may
    // unintentially
    // go into its if statement as well (if it were not an if else-if)

    // When a background image goes off screen shift it to the other side
    if (pos1.x < -windowWidth) {
      // First image has moved off the screen left
      pos1.x = pos1.x + (2 * windowWidth);
    } else if (pos2.x < -windowWidth) {
      // Second image has moved off the screen left
      pos2.x = pos2.x + (2 * windowWidth);
    } else if (pos1.x > windowWidth) {
      // First image has moved off the screen right
      pos1.x = pos1.x - (2 * windowWidth);
    } else if (pos2.x > windowWidth) {
      // Second image has moved off the screen right
      pos2.x = pos2.x - (2 * windowWidth);
    }

    // Now that the true position has settled, set the draw position
    img1.destRec.x = (int) pos1.x;
    img2.destRec.x = (int) pos2.x;
  }

  private void DrawGameplay(Graphics2D gfx) {
    // This is where the game content is draw. Think of it as drawing in layers
    // where the first thing drawn is on the bottom and will be covered by things
    // draw later:
    // - Draw the background
    // - Draw any environment items that should be underneath or behind the main
    // action
    // - Draw the main game items, like the player, enemies, bullets, etc.
    // - Draw environment items that should be on top of or in front of the main
    // action,
    // This will give the illusion of depth
    // - Draw the HUD (e.g. health bar, scores, etc.)
    Draw.Sprite(gfx, marioBck1);
    Draw.Sprite(gfx, marioBck2);
    Draw.Sprite(gfx, Ob1);
    Draw.Sprite(gfx, Ob2);
    Draw.Sprite(gfx, GameandWatchImg);
    Draw.Text(gfx, intMsg, 250, 25, textFont, Helper.WHITE, 0.5f);
  }

  private void DrawPause(Graphics2D gfx) {
    // Typically the pause screen consist of the regular game play with text
    // on top to indicate the game is pause. So, in that case, call DrawGameplay
    // and then draw the Pause text and anything else desired
  }

  private void DrawEndgame(Graphics2D gfx)
  {
    //Draw the end game screen
    Draw.Sprite(gfx, Endgameplay);
  }
 private void MoveGameObject(SpriteSheet object, Vector2F truePos, Vector2F speed)
  {
    //Add the speed components to the object's true position
    truePos.x = truePos.x + speed.x;
    truePos.y = truePos.y + speed.y;

    //Set the object's drawn position to rounded down true position
    object.destRec.x = (int)truePos.x;
    object.destRec.y = (int)truePos.y;
  }
 private void PlayerWallCollision()
  {
    //If the player hits the side walls, pull them in bounds and stop their horizontal movement
    if (GameandWatchImg.destRec.x < 0)
    {
      GameandWatchImg.destRec.x = 0;
      GameandWatchImgPos.x = GameandWatchImg.destRec.x;
      playerSpeed.x = 0;
    }
    else if (GameandWatchImg.destRec.x + GameandWatchImg.destRec.width > windowWidth)
    {
      GameandWatchImg.destRec.x = windowWidth - GameandWatchImg.destRec.width;
      GameandWatchImgPos.x = GameandWatchImg.destRec.x;
      playerSpeed.x = 0;
    }

    //If the player hits the top/bottom walls, pull them in bounds and stop their vertical movement
    if (GameandWatchImg.destRec.y < 0)
    {
      GameandWatchImg.destRec.y = 0;
      GameandWatchImgPos.y = GameandWatchImg.destRec.y;
      playerSpeed.y = 0;
    }
    else if (GameandWatchImg.destRec.y + GameandWatchImg.destRec.height >= windowHeight)
    {
      //Readjust the player to be standing directly on the ground
      GameandWatchImg.destRec.y = windowHeight - GameandWatchImg.destRec.height;
      GameandWatchImgPos.y = GameandWatchImg.destRec.y;
      playerSpeed.y = 0f;

      //The player just landed on the ground
      grounded = true;
    }
    else
    {
      //The player is off the ground, either jumping or falling
      grounded = false;
    }
  }


private void Ob1Collision()
{

  if (Helper.Intersects(GameandWatchRec,Ob1Rec)){
    gameState = ENDGAME;
  }
 
  if (Helper.Intersects(GameandWatchRec,Ob2Rec)){
    gameState = ENDGAME;
  }
  
  if (Helper.Intersects(GameandWatchRec,Ob11Rec)){
    gameState = ENDGAME;
  }
 
  if (Helper.Intersects(GameandWatchRec,Ob22Rec)){
    gameState = ENDGAME;
  }
  
}
}