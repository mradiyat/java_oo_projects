import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** An instance is the game breakout. Start it by executing
    Breakout.main(null);
    */
public class Breakout extends GraphicsProgram {
    /** Width of the game display (all coordinates are in pixels) */
    private static final int GAME_WIDTH= 480;
    /** Height of the game display */
    private static final int GAME_HEIGHT= 620;
    
    /** Width of the paddle */
    private static final int PADDLE_WIDTH= 58;
    /** Height of the paddle */
    private static final int PADDLE_HEIGHT= 11;
    /**Distance of the (bottom of the) paddle up from the bottom */
    private static final int PADDLE_OFFSET= 30;
    
    /** Horizontal separation between bricks */
    public static final int BRICK_SEP_H= 5;
    /** Vertical separation between bricks */
    private static final int BRICK_SEP_V= 4;
    /** Height of a brick */
    private static final int BRICK_HEIGHT= 8;
    /** Offset of the top brick row from the top */
    private static final int BRICK_Y_OFFSET= 70;
    
    /** Number of bricks per row */
    public static  int BRICKS_IN_ROW= 10;
    /** Number of rows of bricks, in range 1..10. */
    public static  int BRICK_ROWS= 10;
    /** Width of a brick */
    public static int BRICK_WIDTH= GAME_WIDTH / BRICKS_IN_ROW - BRICK_SEP_H;
    
    /** Diameter of the ball in pixels */
    private static final int BALL_DIAMETER= 18;
    
    /** Number of turns */
    private static final int NTURNS= 3;
    
     /** rowColors[i] is the color of row i of bricks */
    private static final Color[] rowColors= {Color.red, Color.red, Color.orange, Color.orange,
        Color.yellow, Color.yellow, Color.green, Color.green,
        Color.cyan, Color.cyan};
    
    /** random number generator */
    private RandomGenerator rgen= new RandomGenerator();
    
    
    /** Sound to play when the ball hits a brick or the paddle. */
    private static AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
    
    /** paddle*/
    private GRect blackPaddle;
    
    /** ball*/
    private GOval ball;
    
    /** ball's horizontal speed*/
    private double vx;
    
    /** ball's vertical speed*/
    private double vy;
    
    private int numberOfBricks;
    
    /** Run the program as an application. If args contains 2 elements that are positive
        integers, then use the first for the number of bricks per row and the second for
        the number of rows of bricks.
        A hint on how main works. The main program creates an instance of
        the class, giving the constructor the width and height of the graphics
        panel. The system then calls method run() to start the computation.
      */
    public static void main(String[] args) {
        fixBricks(args);
        String[] sizeArgs= {"width=" + GAME_WIDTH, "height=" + GAME_HEIGHT};
        new Breakout().start(sizeArgs);
        
    }
    
    /** If b is null, doesn't have exactly two elements, or the elements are not
        positive integers, DON'T DO ANYTHING.
        If b is non-null, has exactly two elements, and they are positive
        integers with no blanks surrounding them, then:
        Store the first int in BRICKS_IN_ROW, store the second int in BRICK_ROWS,
        and recompute BRICK_WIDTH using the formula given in its declaration.
         */
    public static void fixBricks(String[] b) {
        /** Hint. You have to make sure that the two Strings are positive integers.
            The simplest way to do that is to use the calls Integer.valueOf(b[0]) and
            Integer.valueOf(b[1]) within a try-statement in which the catch block is
            empty. Don't store any values in the static fields UNTIL YOU KNOW THAT
            both array elements are positive integers. */
        if (b == null || b.length != 2) {return;}
    	try {
        	if (Integer.valueOf(b[0]) < 1 || Integer.valueOf(b[1]) < 1) {
        		throw new Exception("Not positive integers");
        	}
        	else {
        		BRICKS_IN_ROW = Integer.valueOf(b[0]);
        		BRICK_ROWS = Integer.valueOf(b[1]);
        		BRICK_WIDTH= GAME_WIDTH / BRICKS_IN_ROW - BRICK_SEP_H;
        	}
        }
        catch (Exception e) {
        }
        
    }
    
    /** Run the Breakout program. */
    public void run() {
        setUpObjects();
        
        vy = 3.0;
        vx= rgen.nextDouble(1.0, 3.0);
        if (!rgen.nextBoolean(0.5)) vx= -vx;
        
        while (numberOfBricks != 0) {
        	ball.setLocation(ball.getX() + vx, ball.getY() + vy);
        	resolveWallCollisions();
            resolveObjectCollisions();
        	pause(10);
        }
        
        if (numberOfBricks == 0) {
        	endGame();
        }
    }

    /** Sets up all the objects on the program initially; also adds listeners
     * 
     */
    public void setUpObjects() {
    	//fill BRICK_ROWS number of rows
        for (int i = 0; i < BRICK_ROWS; i++) {
        	//fill BRICKs_IN_ROW number of bricks in a row
        	for (int j = 0; j < BRICKS_IN_ROW; j++) {
        		//calculate position of each new brick
        		Brick currentBrick = new Brick(BRICK_SEP_H / 2 + (j * (BRICK_WIDTH + BRICK_SEP_H)), 
        				BRICK_Y_OFFSET + (i * (BRICK_HEIGHT + BRICK_SEP_V)), BRICK_WIDTH, BRICK_HEIGHT); 
        		int whichColor = i%10; //if there are more than 10 rows, reset the colors
        		currentBrick.setColor(rowColors[whichColor]);
        		currentBrick.setFillColor(rowColors[whichColor]);
        		currentBrick.setFilled(true);
        		add(currentBrick);
        	}
        }
        numberOfBricks = BRICK_ROWS * BRICKS_IN_ROW;
        
        //create paddle
        blackPaddle = new GRect(GAME_WIDTH/2 - PADDLE_WIDTH/2, GAME_HEIGHT - PADDLE_OFFSET - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
        blackPaddle.setFilled(true);
        add(blackPaddle);
        
        //create the ball
        ball = new GOval(GAME_WIDTH/2 - BALL_DIAMETER/2, GAME_HEIGHT/2 - BALL_DIAMETER/2, BALL_DIAMETER, BALL_DIAMETER);
        ball.setFilled(true);
        add(ball);
        
        //add listeners
        addMouseListeners();
    }
    
    /** Resolve collisions with the 4 walls. If player hits bottom wall, ball goes back to the middle. 
     * 
     */
    public void resolveWallCollisions() {
    	//4 checks for outer bounds of the board
    	if (ball.getY() < 0) {
    		vy = -vy;
    	}
    	if (ball.getY() > GAME_HEIGHT - BALL_DIAMETER) {
    		remove(ball);
    		GLabel newBallLabel = new GLabel("Ball will refresh in 3 seconds");
    		newBallLabel.setLocation(GAME_WIDTH/2 - newBallLabel.getWidth()/2, 
    				GAME_HEIGHT/2 - newBallLabel.getHeight()/2);
    		add(newBallLabel);
    		ball.setLocation(GAME_WIDTH/2 - BALL_DIAMETER / 2, GAME_HEIGHT / 2 - BALL_DIAMETER / 2);
    		pause(3000);
    		remove(newBallLabel);
    		add(ball);
    	}
    	if (ball.getX() < 0) {
    		vx = -vx;
    	}
    	if (ball.getX() > GAME_WIDTH - BALL_DIAMETER) {
    		vx = -vx;
    	}
    }
    
    /** Resolve collisions with bricks and paddle. If ball hits paddle coming down, reflect ball. 
     * If ball hits paddle going up, do nothing. If ball hits brick, invert vertical velocity and remove brick.
     */
    public void resolveObjectCollisions() {
    	//check for collisions with paddle as the ball goes down
    	GObject collidedObject = getCollidingObject();
    	if (collidedObject == blackPaddle) {
    		if (vy > 0) {
    			vy = -vy;
    		}
    	}
    	if (collidedObject instanceof Brick) {
    		remove(collidedObject);
    		vy = -vy;
    		numberOfBricks--;
    	}
    }
    
    /** End the game
     *  Add a label saying the game is over to the middle of the board
     */
    public void endGame() {
    	removeAll();
    	GLabel endGameLabel = new GLabel("Good Job!");
		endGameLabel.setLocation(GAME_WIDTH/2 - endGameLabel.getWidth()/2, 
				GAME_HEIGHT/2 - endGameLabel.getHeight()/2);
		add(endGameLabel);
    }
    
    
    /** Move the horizontal middle of the paddle to the x-coordinate of the mouse
        -- but keep the paddle completely on the board.
        Called by the system when the mouse is used. 
      */
    public void mouseMoved(MouseEvent e) {
        GPoint p= new GPoint(e.getPoint());
        // Set x to the left edge of the paddle so that the middle of the paddle
        // is where the mouse is --except that the mouse must stay completely
        // in the pane if the mouse moves past the left or right edge.
        double x = p.getX();
        //Use Math.min and Math.max to set high and low bounds so paddle doesn't go off screen
        blackPaddle.setLocation(Math.min(Math.max(x - blackPaddle.getWidth()/2, 0), 
        		GAME_WIDTH - blackPaddle.getWidth()), blackPaddle.getY());
    }
    
    public GObject getCollidingObject() {
    	if (getElementAt(ball.getX(), ball.getY()) != null) {
    		return getElementAt(ball.getX(), ball.getY());
    	}
    	if (getElementAt(ball.getX() + BALL_DIAMETER, ball.getY()) != null) {
    		return getElementAt(ball.getX() + BALL_DIAMETER, ball.getY());
    	}
    	if (getElementAt(ball.getX(), ball.getY() + BALL_DIAMETER) != null) {
    		return getElementAt(ball.getX(), ball.getY() + BALL_DIAMETER);
    	}
    	if (getElementAt(ball.getX() + BALL_DIAMETER, ball.getY() + BALL_DIAMETER) != null) {
    		return getElementAt(ball.getX() + BALL_DIAMETER, ball.getY() + BALL_DIAMETER);
    	}
    	return null;
    }
    
    /** = representation of array b: its elements separated by ", " and delimited by [].
        if b == null, return null. */
    public static String toString(String[] b) {
        if (b == null) return null;
        
        String res= "[";
        // inv res contains "[" + elements of b[0..k-1] separated by ", "
        for (int k= 0; k < b.length; k= k+1) {
            if (k > 0)
                res= res + ", ";
            res= res + b[k];
        }
        return res + "]";
    }
    
}

/** An instance is a Brick */
/*  Note: This program will not compile until you write the two
    constructors correctly, because GRect does not have a 
    constructor with no parameters.  (You know that if a constructor
    does not begin with a call off another constructor, Java inserts
    
        super();
        
    */
class Brick extends GRect {
    public double width; 
    public double height;
    /** Constructor: a new brick with width w and height h*/
    public Brick(double w, double h) {   
    	super(w, h);
    }
    
    /** Constructor: a new brick at (x,y) with width w and height h*/
    public Brick(double x, double y, double w, double h) {
        super(x, y, w, h);
    }
}
