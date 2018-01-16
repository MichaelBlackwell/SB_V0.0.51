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
public class CardTerrain extends Card{
    
    private int TEM;
    
    public CardTerrain(String n, Image i, int x, int y, int p, String t, String d, String s, int tem){
        super(n,i,x,y,p,t,d,s);
        this.TEM = tem;
    }
    
    @Override
    public int getTEM(){
        return TEM;
    }
    
}
