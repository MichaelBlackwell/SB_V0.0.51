/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javagame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author Michael
 */
public class Lane {
    
    private ArrayList<Card> playerOneCards;
    private ArrayList<Card> playerTwoCards;
    
    private int x;
    private int y;
    private int left;
    private int right;
    private int bottom;
    private int top;
    private int laneSize;
    private int laneNum;
    private int oneRR = 0;
    private int oneFP = 0;
    private int oneTEM = 0;
    private int twoRR = 0;
    private int twoFP = 0;
    private int twoTEM = 0;
    private Image image;
    
    
    
    public Lane(GameContainer gc, int laneNumber, int Size){
        this.laneSize = Size;
        this.laneNum = laneNumber;
        this.x = (laneSize/2) + (laneNum * laneSize);
        this.y = gc.getHeight() / 2;
        playerOneCards = new ArrayList<Card>();//1, x, y
        playerTwoCards = new ArrayList<Card>();//2, x - 100, 150
        left = (5 * (laneNum + 1)) + ((laneSize - 5) * laneNum);
        right = left + laneSize - 10;
        top = 145;
        bottom = gc.getHeight() - 265;
        try{
        image = new Image ("res/Lane.png");
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
    
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g, TrueTypeFont ttf) throws SlickException{
        
        //draw lane box
        g.setColor(Color.darkGray);
        g.fillRect((5 * (laneNum + 1)) + ((laneSize - 5) * laneNum), 
                top, 
                laneSize - 10, 
                bottom);
        g.drawImage(image, (5 * (laneNum + 1)) + ((laneSize - 5) * laneNum), 
                top,
                (5 * (laneNum + 1)) + ((laneSize - 5) * laneNum) + laneSize - 10, 
                gc.getHeight() - 120,
                400, 
                600, 
                laneSize + 500, 
                500 + 600);
        //draw lane stats
        g.setColor(Color.white);
        g.drawString("P1 RR:" + 
                oneRR +
                "\nP1 FP:" + 
                oneFP +
                "\nP1 TEM:" + 
                oneTEM, 
                x-50, gc.getHeight() - 220);
        
        g.drawString("P2 RR:" + 
                twoRR +
                "\nP2 FP:" + 
                twoFP +
                "\nP2 TEM:" + 
                twoTEM, 
                x-50, 
                200);
        
        
        for(Card checkCard : playerOneCards){
            checkCard.render(gc, sbc, g, ttf);
        }
        for(Card checkCard : playerTwoCards){
            checkCard.render(gc, sbc, g, ttf);
        }
    }
    
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException{

        //update player one
        //update card order
        Collections.sort(playerOneCards, new Comparator<Card>() {
            @Override 
            public int compare(Card c1, Card c2) {
                return c1.getType().compareTo(c2.getType());
            }
        });
        
        //update card positions
        int i = 0;
        int numCards = 0;
        for(Card checkCard : playerOneCards){
            checkCard.cardOrder = i;
            checkCard.setX( x - 80 + (i * 25));
            i++;
            numCards++;
        }
        
        //update player two
        //update card order
        Collections.sort(playerTwoCards, new Comparator<Card>() {
            @Override 
            public int compare(Card c1, Card c2) {
                return c1.getType().compareTo(c2.getType());
            }
        });
        
        //update card positions
        i = 0;
        numCards = 0;
        for(Card checkCard : playerTwoCards){
            checkCard.cardOrder = i;
            checkCard.setX( x - 80 + (i * 25));
            i++;
            numCards++;
        }
        
        //update lane statistics
        oneTEM = 0;
        twoTEM = 0;
        oneFP = 0;
        twoFP = 0;
        CardTerrain terCrd;
        for(Card checkCard : playerOneCards){
            if(checkCard.getType() == "Terrain"){
                oneTEM +=  checkCard.getTEM();
            }
            if(checkCard.getType() == "Personnel"){
                if(!(checkCard.isPanicked() || checkCard.isRouted())){
                    oneFP +=  checkCard.getFPR()[oneRR + twoRR];
                }
            }
        }
        for(Card checkCard : playerTwoCards){
            if(checkCard.getType() == "Terrain"){
                twoTEM +=  checkCard.getTEM();
            }
            if(checkCard.getType() == "Personnel"){
                if(!(checkCard.isPanicked() || checkCard.isRouted())){
                    twoFP +=  checkCard.getFPR()[oneRR + twoRR];
                }
            }
        }
    }
    
    public void addCard(Card c){
        
        
        
        if (c == null)
            throw new NullPointerException("Can't add null card to a hand.");
        if (c.getPlayer() == 1){
            playerOneCards.add(c);
            c.setY(y+30);
        }
        else{
            playerTwoCards.add(c);
            c.setY(y-90);
        }
        
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
    
    public ArrayList<Card> getPlayerOneCards(){
        return playerOneCards;
    }
    
    public ArrayList<Card> getPlayerTwoCards(){
        return playerTwoCards;
    }
    
    public int getOneRR(){
        return oneRR;
    }
    
    public int getTwoRR(){
        return twoRR;
    }
    
    public void forOneRR(){
        oneRR++;
    }
    
    public void retOneRR(){
        oneRR--;
    }
    
    public void forTwoRR(){
        twoRR++;
    }
    
    public void retTwoRR(){
        twoRR--;
    }
    
    public int getOneTEM(){
        return oneTEM;
    }
    
    public int getTwoTEM(){
        return twoTEM;
    }
    
    public int getOneFP(){
        return oneFP;
    }
    
    public int getTwoFP(){
        return twoFP;
    }
}
