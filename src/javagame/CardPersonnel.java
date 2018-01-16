/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author mike
 */ 
public class CardPersonnel extends Card{
    private int[] fpr;
    private int morale;
    private int panic;
    private int KIA;
    
    private boolean dead = false;
    private boolean routed = false;
    private boolean panicked = false;
    private Image routimg;
    private Image panicimg;
    
    public CardPersonnel(String n, Image i, int x, int y, int p, String t, String d, String s, int[] fpr, int m, int pa, int k){
        super(n,i,x,y,p,t,d,s);
        this.fpr = fpr;
        this.morale = m;
        this.panic = pa;
        this.KIA = k;
        try{
            this.routimg = new Image("res/CrdRout.png");
            this.panicimg = new Image("res/CrdPanic.png");
        }catch(SlickException e){
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g, TrueTypeFont ttf) throws SlickException{
        
        if(this.isPressed()){
            g.drawString(this.getName() + " is pressed. xyz = " + this.getX() + " " + this.getY(), 30, 100);
        }
        
        
        if(panicked){
            panicimg.draw(this.getX(), this.getY());
        }else if(routed){
            routimg.draw(this.getX(), this.getY());
        
        }else{
            this.getImage().draw(this.getX(), this.getY());
        }
        
        if (this.isSelected()){
            
        }
        if(this.getPlayer() == 1){
            ttf.drawString(this.getX()+3, this.getY(), this.getName(), Color.black);
            
            ttf.drawString(this.getX()+3, this.getY()+47, this.getDes(), Color.black, 0,5);
            ttf.drawString(this.getX()+3, this.getY()+57, this.getStat(), Color.darkGray, 6,10);
        }
        else{
            ttf.drawString(this.getX()+3, this.getY(), this.getName(), Color.red);
            
            ttf.drawString(this.getX()+3, this.getY()+47, this.getDes(), Color.red, 0,5);
            ttf.drawString(this.getX()+3, this.getY()+57, this.getStat(), Color.red, 6,10);
        }
    }
    
    @Override
    public int[] getFPR(){
        return fpr;
    }
    
    @Override
    public int getMorale(){
       return morale;
    }
    
    @Override
    public int getPanic(){
       return panic;
    }
    
    @Override
    public int getKIA(){
       return KIA;
    }
    
    @Override
    public boolean isDead(){
        return dead;
    }
    
    @Override
    public void setDead(boolean d){
        dead = d;
    }
    
    @Override
    public boolean isRouted(){
        return routed;
    }
    
    @Override
    public void setRouted(boolean r){
        routed = r;
    }
    
    @Override
    public boolean isPanicked(){
        return panicked;
    }
    
    @Override
    public void setPanicked(boolean p){
        panicked = p;
    }
}
