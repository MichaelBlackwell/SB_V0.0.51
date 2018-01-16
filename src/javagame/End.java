/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author mike
 */
public class End extends BasicGameState{
        
    private Image playButton;
    private Image exitButton;
    private int winner;
    
    public End(int state){
        
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbc) throws SlickException{
        playButton = new Image("res/SB-Buttons_1.png");
        exitButton = new Image("res/SB-Buttons_2.png");
    }
    
    //draw
    @Override
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g) throws SlickException{
        
        g.setColor(new Color(100,100,151));
        
        g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
        playButton.draw((gc.getWidth()/2) - (playButton.getWidth()/2), (gc.getHeight()/2) - (playButton.getHeight()/2) + 40);
        exitButton.draw((gc.getWidth()/2) - (playButton.getWidth()/2), (gc.getHeight()/2) - (playButton.getHeight()/2) + 85);
        
        
        g.setColor(Color.darkGray);
        g.drawString("GAME OVER\nPLAYER " + winner + " WINS", 
                gc.getWidth()/2 - 50, gc.getHeight()/2 - 320);
    }
    
    //change values and images by delta
    @Override
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException{
        
        
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();
        Input input = gc.getInput();
        
        //mouse controller for play button
        if((xpos > ((gc.getWidth()/2) - (playButton.getWidth()/2)) 
                && xpos < ((gc.getWidth()/2) + (playButton.getWidth()/2))) 
                && (ypos > ((gc.getHeight()/2) - (playButton.getHeight()/2) - 40) // 40 is vertical distance from center
                && ypos < ((gc.getHeight()/2) + (playButton.getHeight()/2) - 40))){
            if(input.isMouseButtonDown(0)){
                sbc.enterState(1);
            }
        }
        
        //mouse controller for exit button
        if((xpos > ((gc.getWidth()/2) - (exitButton.getWidth()/2)) 
                && xpos < ((gc.getWidth()/2) + (exitButton.getWidth()/2))) 
                && (ypos > ((gc.getHeight()/2) - (exitButton.getHeight()/2) - 85) // 85 is vertical distance from center 
                && ypos < ((gc.getHeight()/2) + (exitButton.getHeight()/2) - 85))){ 
            if(input.isMouseButtonDown(0)){
                gc.exit();
            }
        }
        
    }
    
    @Override
    public int getID(){
        return 0; // change to correct corresponding state
    }
    
    public void setWinner(int w){
        winner = w;
    }
}
