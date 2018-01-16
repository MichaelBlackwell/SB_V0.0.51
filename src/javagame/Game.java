/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{
    
    public static final String gameName = "Super Breed ver: 0.0.9";
    public static final int menu = 0;
    public static final int play = 1;
    public static final int end = 2;
    public static final int WIDTH = 1300;
    public static final int HEIGHT = 700;
    public static final boolean FULLSCREEN = false;
    
    public Game(String gameName){
        super(gameName);
        this.addState(new Menu(menu));
        this.addState(new Play(play));
        //this.addState(new End(end));
        
    }
    
    public void initStatesList(GameContainer gc) throws SlickException{
        this.getState(menu).init(gc, this);
        this.getState(play).init(gc, this);
        //this.getState(end).init(gc, this);
        
        this.enterState(menu);
    }
    
    public static void main(String[] args) {
        AppGameContainer appgc;
        try{
            appgc = new AppGameContainer(new Game(gameName));
            appgc.setDisplayMode(WIDTH, HEIGHT, FULLSCREEN);
            appgc.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
}
