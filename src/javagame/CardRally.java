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
public class CardRally extends Card{
    
    private int rally;
    
    public CardRally(String n, Image i, int x, int y, int p, String t, String d, String s, int r){
        super(n,i,x,y,p,t,d,s);
        rally = r;
    }
}
