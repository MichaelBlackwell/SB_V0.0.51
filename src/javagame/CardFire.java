/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame;


import org.newdawn.slick.Image;

/**
 *
 * @author mike
 */
public class CardFire extends Card{
    
    private int ff;
    private int fp;
    
    public CardFire(String n, Image i, int x, int y, int p, String t, String d, String s, int fp, int ff){
        super(n,i,x,y,p,t,d,s);
        this.fp = fp;
        this.ff = ff;
        
    }
    
    @Override
    public int getFP(){
        return fp;
    }
    
    @Override
    public int getFF(){
        return ff;
    }
    
}
