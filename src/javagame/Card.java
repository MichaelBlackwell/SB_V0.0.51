/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javagame;


import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import org.lwjgl.input.Mouse;

/**
 *
 * @author Michael
 */
public abstract class Card {
    private final String name;
    private final Image image;
    
    private final String type;
    private final String des;
    private final String stat;
    private int x;
    private int y;
    private boolean pressed = false;
    private boolean selected = false;
    private int player;
    private Lane lane;
    public int cardOrder;
    
    public Card(String n, Image i, int x, int y, int p, String t, String d, String s){
        this.name = n;
        this.image = i;
        
        this.type = t;
        this.x = x;
        this.y = y;
        this.player = p;
        this.cardOrder = 0;
        this.lane = null;
        this.des = d;
        this.stat = s;
    }
    
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g, TrueTypeFont ttf) throws SlickException{
        
        if(pressed){
            g.drawString(name + " is pressed. xyz = " + x + " " + y, 30, 100);
        }
            
        image.draw(x, y);
        
        
        if (this.isSelected()){
            
        }
        if(player == 1){
            ttf.drawString(x+3, y, name, Color.black);
            
            ttf.drawString(x+3, y+47, des, Color.black, 0,5);
            ttf.drawString(x+3, y+57, stat, Color.darkGray, 6,10);
        }
        else{
            ttf.drawString(x+3, y, name, Color.red);
            
            ttf.drawString(x+3, y+47, des, Color.red, 0,5);
            ttf.drawString(x+3, y+57, stat, Color.red, 6,10);
        }
    }
    
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException{
        
        
        
        //mouse pressed inside bounds for card
        if(Mouse.getX() > x
                && Mouse.getX() < x + image.getWidth()
                && Mouse.getY() > gc.getHeight() - y - image.getHeight()
                && Mouse.getY() < gc.getHeight() - y
                && gc.getInput().isMouseButtonDown(0)){
            
            pressed = true;
        }else{
            pressed = false;
        }
        
    }
    
    public String getName(){
        return name;
    }
    
    public Image getImage(){
        return image;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }

    public boolean isPressed(){
        return pressed;
    }
    
    public void isPressed(boolean p){
        pressed = p;
    }
    
    public boolean isSelected(){
        return selected;
    }
    
    public void setSelected(boolean s){
        selected = s;
    }
    
    public String getType(){
        return type;
    }
    
    public void setPlayer(int p){
        player = p;
    }
    
    public int getPlayer(){
        return player;
    }
    
    public void setLane(Lane l){
        lane = l;
    }
    
    public Lane getLane(){
        return lane;
    }
    
    public String getDes(){
        return des;
    }
    
    public String getStat(){
        return stat;
    }
    
    
    //*********INHERITEDED METHODS************
    
    
    public int getFP(){
        return x;
    }
    
    public int getFF(){
        return x;
    }
    
    public int getTEM(){
        return x;
    }
    
    public int[] getFPR(){
        int[] fpr = {};
        return fpr;
    }
    
    public int getMorale(){
       return x;
    }
    
    public int getPanic(){
       return x;
    }
    
    public int getKIA(){
       return x;
    }
    
    public boolean isDead(){
        boolean dead = false;
        return dead;
    }
    
    public void setDead(boolean d){
    }
    
    public boolean isRouted(){
        boolean routed = false;
        return routed;
    }
    
    public void setRouted(boolean r){
    }
    
    public boolean isPanicked(){
        boolean panicked = false;
        return panicked;
    }
    
    public void setPanicked(boolean p){
    }
}
