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
public class CardMove extends Card{
    
    private int tem;
    
    public CardMove(String n, Image i, int x, int y, int p, String t, String d, String s, int tem){
        super(n,i,x,y,p,t,d,s);
        this.tem = tem;
    }
    
    @Override
    public int getTEM(){
        return tem;
    }
    
    
    
}
