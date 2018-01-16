/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javagame;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Michael
 */
public class DiscardPile {
    private final int cardWidth;
    private final int cardHeight;
    private int top;
    private int bottom;
    private int left;
    private int right;
    
    
    DiscardPile(int w, int h, int t, int b, int l, int r){
        cardWidth = w;
        cardHeight = h;
        top = t;
        bottom = b;
        left = l;
        right = r;
    }
    
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g) throws SlickException{
        
    }
    
    public void update(){
        
    }
    
    public int getLeft(){
        return left;
    }
    
    public int getRight(){
        return right;
    }
    
    public int getTop(){
        return top;
    }
    
    public int getBottom(){
        return bottom;
    }
}
