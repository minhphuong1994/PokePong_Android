package com.example.pokepong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;


// SurfaceView is a type of "view" that you can draw on
// Runnable tells Android that this class should be run on a Thread
// - Thread = multithreaded programs
public class GameEngine extends SurfaceView implements Runnable {

    // Android debug variables
    final static String TAG="PONG-GAME";
    // screen size
    int screenHeight;
    int screenWidth;
    // game state
    boolean gameIsRunning;
    // threading
    Thread gameThread;
    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;

    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------

    // @TODO: Setup your sprites here
    // Each sprite must have a (x,y) position
    // optional: image, direction, movement speed
    // ----------------------------
    // ## GAME STATS
    // ----------------------------
    // Example: current score, lives remaining, total points, etc
    int score = 0;
    int lives = 5;

    public GameEngine(Context context, int w, int h) {
        super(context);

        // default code that sets up the view so we can draw on it
        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;


        this.printScreenInfo();

        // @TODO: Add your sprites
        this.createBalls();
        // @TODO: Any other game setup

    }


    // Helper function that outputs information about the user's screen size
    private void printScreenInfo() {
        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }

    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }



    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------
    ArrayList<Sprite> balls = new ArrayList<>();

    public void createBalls(){
        Random r = new Random();

        for(int i=0;i<10;i++){
            //random int number from 15 to max width (max-min+1)+min
            balls.add(new Sprite(r.nextInt(this.screenWidth-14)+15,10,r.nextInt(15)+10));
        }
        Log.d(TAG,"Balls: "+balls.toString());
    }


    int xPos = 50;
    int yPos = 0;
    int speed = 10;
    public void updatePositions() {
        // @TODO: Update position of your sprites
        // @TODO: Do collision detection

        this.yPos += speed;

        for(Sprite b:balls){
            if(b.getY()>=paddle.y1 && b.getY()<=paddle.y2 && b.getX()>=paddle.x1 && b.getX()<=paddle.x2){
                b.setSpeed(b.getSpeed()*-1);
                score +=1;
            }
            else if(b.getY()<=0){
                b.setSpeed(b.getSpeed()*-1);
            }
            b.setY(b.getY()+b.getSpeed());
        }
        if(yPos>=paddle.y1 && yPos<=paddle.y2 && xPos>=paddle.x1 && xPos<=paddle.x2){
            Log.d(TAG, "you are at the bottom of the screen");
            Log.d(TAG, "Current pos: (" + xPos + ", " + yPos + ")");
            speed *= -1;
            score +=1;
        }
        if(yPos <=0){
            Log.d(TAG, "you are at the top of the screen");
            Log.d(TAG, "Current pos: (" + xPos + ", " + yPos + ")");
            speed *= -1;
        }

    }
    //initial paddle obj
    Paddle paddle = new Paddle(100, 800, 250, 20);

    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------
            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            

            paintbrush.setColor(Color.BLUE);
            this.canvas.drawCircle(xPos,yPos,20,paintbrush);

            paintbrush.setColor(Color.YELLOW);
            for(Sprite b:balls){
                this.canvas.drawCircle(b.getX(),b.getY(),b.getSize(),paintbrush);
            }

            paintbrush.setColor(Color.BLACK);
            // draw a paddle
            this.canvas.drawRect(paddle.x1, paddle.y1, paddle.x2, paddle.y2, paintbrush);
            paintbrush.setTextSize(100);
            this.canvas.drawText("Score: "+score,50,400,paintbrush);

            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    // You can ignore this function
    // It sets the speed at which the screen refreshes
    public void setFPS() {
        try {
            gameThread.sleep(17);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Detect the user's action
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        // MotionEvent.ACTION_DOWN = person touched screen
        // MotionEVent.ACTION_UP = person lifted their finger off the screen
//        if (userAction == MotionEvent.ACTION_DOWN) {
//            // this means the person pressed the screen
//            Log.d(TAG, "Person tapped the screen at: " + event.getX() + ", " + event.getY());
//            Random r = new Random();
//            int randomSpeed = r.nextInt(15)+10; //random from 10-14
//            Sprite b = new Sprite((int)event.getX(), (int)event.getY(), randomSpeed);
//            this.balls.add(b);
//        }

        //Moving Paddle when clicking on each side of the screen
        if(event.getX() < screenWidth/2){
            if(paddle.x1 >0+paddle.speed){
                paddle.x1 -= paddle.speed;
                paddle.x2 -= paddle.speed;
            }
        }
        else{
            if(paddle.x2 <screenWidth-paddle.speed){
                paddle.x1 += paddle.speed;
                paddle.x2 += paddle.speed;
            }
        }


        return true;
    }
}
