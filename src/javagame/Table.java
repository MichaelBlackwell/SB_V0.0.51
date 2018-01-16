/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javagame;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import java.awt.Font;
import static javafx.scene.paint.Color.color;

/**
 *
 * @author Michael
 */
public class Table{
    private ArrayList<Lane> lanes;
    private ArrayList<Card> pOneHand;
    private ArrayList<Card> pTwoHand;
    
    private DiscardPile discard;
    
    private Deck pOneDeck;
    private Deck pTwoDeck;
    
    private Card selectedCard = null;
    private Card prevSelectedCard = null;
    private Card executedCard = null;
    
    private Image cardBack;
    
    private Rules rules;
    private int laneSize;
    private int numLanes;
    private final int cardWidth = 70;
    private final int cardHeight = 98;
    private int i;
    private Font font;
    private Font fontTitle;
    private TrueTypeFont ttf;
    private boolean cardWasExecuted;

    
    public Table(int numLanes)
    {
        this.numLanes = numLanes;
        lanes = new ArrayList<Lane>();
        pOneHand = new ArrayList<Card>();
        pTwoHand = new ArrayList<Card>();
        pOneDeck = new Deck("Player One Deck", 1);
        pTwoDeck = new Deck("Player Two Deck", 2);
        rules = new Rules(this);
        this.font = new Font("Verdana", Font.BOLD, 8);
        this.ttf = new TrueTypeFont(font, true);
        cardWasExecuted = false;
        try{
            cardBack = new Image("res/CrdSB.png");
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
    
    public void init(GameContainer gc, StateBasedGame sbc) throws SlickException{
        
        
        
        //initialize decks
        pOneDeck.init(gc, sbc);
        pTwoDeck.init(gc, sbc);
        
        //rules setup
        rules.init();
        
        
        
        
        
        //create lanes
        laneSize = gc.getWidth() / numLanes;
        for(int i = 0; i < numLanes; i++){
            lanes.add(new Lane(gc, i, laneSize));
        }
        
        discard = new DiscardPile(cardWidth, cardHeight, gc.getHeight() - cardHeight,gc.getHeight(),gc.getWidth() - cardWidth,gc.getWidth());
    }
    
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g) throws SlickException{
        
        //draw title
        g.drawString(sbc.getTitle(), gc.getWidth() - 300, 10);
        
        //draw rules 
        g.setColor(Color.black);
        g.drawString("Player Turn: " + rules.getPlayerTurn(), 100, 100);
        g.drawString("Phase: " + rules.getPhase(), 100, 120);
        
        
        //draw players hand
        for(Card checkCard : getPOneHand()){
            if (rules.getPlayerTurn() == 1){
                checkCard.render(gc, sbc, g, ttf);
            }
            else{
                g.drawImage(cardBack, checkCard.getX(), checkCard.getY());
            }
        }
        for(Card checkCard : getPTwoHand()){
            if (rules.getPlayerTurn() == 2){
                checkCard.render(gc, sbc, g, ttf);
            }
            else{
                g.drawImage(cardBack, checkCard.getX(), checkCard.getY());
            }
        }
        
        
        //draw cards in lane
        for(Lane checkLane : lanes){
            checkLane.render(gc, sbc, g, ttf);
        }
        
        //draw discard pile
        g.setColor(Color.red);
        g.fillRect(gc.getWidth() - cardWidth, gc.getHeight() - cardHeight, cardWidth, cardHeight);
        g.setColor(Color.black);
        g.drawString("discard", gc.getWidth() - cardWidth + 5, gc.getHeight() - cardHeight + cardHeight / 2);
        
        //draw draw pile
        g.setColor(Color.green);
        g.fillRect(gc.getWidth() - cardWidth,0, cardWidth, cardHeight);
        g.setColor(Color.black);
        g.drawString("draw", gc.getWidth() - cardWidth + 5,cardHeight / 2);
        
        //border for selected card for both players
        for(Card checkCard : getPOneHand()){
            if(checkCard == selectedCard && selectedCard != null){
                g.drawImage(new Image("res/CardBorder1.png"), selectedCard.getX() - 10, selectedCard.getY() - 10);
            }
        }
        for(Card checkCard : getPTwoHand()){
            if(checkCard == selectedCard && selectedCard != null){
                g.drawImage(new Image("res/CardBorder1.png"), selectedCard.getX() - 10, selectedCard.getY() - 10);
            }
        }
        for(Lane checkLane : getLanes()){
            for(Card checkCard : checkLane.getPlayerOneCards()){
                if(checkCard == selectedCard && selectedCard != null){
                    g.drawImage(new Image("res/CardBorder1.png"), selectedCard.getX() - 10, selectedCard.getY() - 10);
                }
            }
        }
        for(Lane checkLane : getLanes()){
            for(Card checkCard : checkLane.getPlayerTwoCards()){
                if(checkCard == selectedCard && selectedCard != null){
                    g.drawImage(new Image("res/CardBorder1.png"), selectedCard.getX() - 10, selectedCard.getY() - 10);
                }
            }
        }
        
        //draw map
        g.setColor(new Color(100,200,100));
        g.fillRect(gc.getWidth() - 400, 5, 75, 175);
        g.setColor(Color.black);
        for(i = 0; i<4; i++){
            g.drawLine(gc.getWidth() - 400 + (i * 25), 5, gc.getWidth() - 400 + (i * 25), 180);
        }
        for(i = 0; i<8; i++){
            g.drawLine(gc.getWidth() - 400, (i * 25) + 5, gc.getWidth() - 400 + 75, (i * 25) + 5);
        }
        
        //draw RR counters
        g.setColor(Color.blue);
        i = 0;
        for(Lane checkLane : getLanes()){
            g.fillOval(gc.getWidth() - 400 + (25 * i), 155 - (checkLane.getOneRR() * 25), 25, 25);
            i++;
        }
        g.setColor(Color.red);
        i = 0;
        for(Lane checkLane : getLanes()){
            g.fillOval(gc.getWidth() - 400 + (25 * i), 5 + (checkLane.getTwoRR() * 25), 25, 25);
            i++;
        }
        g.setColor(Color.black);
        
        
        //draw rules
        rules.render(gc, sbc, g);
    }
    
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException{
        
        
        
        //update decks
        pOneDeck.update(gc, sbc, delta);
        pTwoDeck.update(gc, sbc, delta);
        
        //update lanes
        
        for(Lane checkedLane : lanes){
            checkedLane.update(gc, sbc, delta);
        }
        
        //update players' hand
        for(Card updateCard : getPOneHand()){
            updateCard.update(gc, sbc, delta);
        }
        for(Card updateCard : getPTwoHand()){
            updateCard.update(gc, sbc, delta);
        }
        
        //update card order in players' hand
        Collections.sort(pOneHand, new Comparator<Card>() {
            @Override 
            public int compare(Card c1, Card c2) {
                return c1.getType().compareTo(c2.getType());
            }
        });
        Collections.sort(pTwoHand, new Comparator<Card>() {
            @Override 
            public int compare(Card c1, Card c2) {
                return c1.getType().compareTo(c2.getType());
            }
        });
        
        //shift cards in players' hand to the right by their card width and place at bottom of screen
        i = 0;
        for(Card checkCard : getPOneHand()){
            checkCard.setY(gc.getHeight() - cardHeight);
            checkCard.setX(i * cardWidth);
            i++;
        }
        i = 0;
        for(Card checkCard : getPTwoHand()){
            checkCard.setY(0);
            checkCard.setX(i * cardWidth);
            i++;
        }
        
        //select/move depending on whose turn it is
        //select newly clicked card and deselect the old one
        //move card to team on table by right clicking
        Select();
        //move card to discard on table by right clicking
        //discardFromHand(gc);
        
        //play rules
        rules.update(gc, sbc, delta);
    }
    
    //select newly clicked card and deselect the old one
    public void Select(){
        if(!(rules.getPhase() == 8)){
            if(rules.getPlayerTurn() == 1){
                for(Card checkCard : getPOneHand()){
                    if(checkCard.isPressed() == true){
                        checkCard.setSelected(true);
                        prevSelectedCard = selectedCard;
                        selectedCard = checkCard;
                        if(selectedCard != prevSelectedCard && prevSelectedCard != null){
                            prevSelectedCard.setSelected(false);
                        }
                    }
                }
            }else{
                for(Card checkCard : getPTwoHand()){
                    if(checkCard.isPressed() == true){
                        checkCard.setSelected(true);
                        prevSelectedCard = selectedCard;
                        selectedCard = checkCard;
                        if(selectedCard != prevSelectedCard && prevSelectedCard != null){
                            prevSelectedCard.setSelected(false);
                        }
                    }
                }
            }
        }
    }
    
 
 
    
    public void discardFromHand(GameContainer gc){
        //player 1 discard
        if(selectedCard != null && selectedCard.getPlayer() == 1){
            
            if(Mouse.getX() > getDiscard().getLeft() //left side of lane
                    && Mouse.getX() < getDiscard().getRight() // right side of lane
                    && Mouse.getY() > 0 // bottom of lane
                    && Mouse.getY() < getCardHeight() // bottom of lane
                    && gc.getInput().isMousePressed(1)){ // right click
                
                pOneDeck.getPlayDeck().add(selectedCard);
                pOneHand.remove(selectedCard);
                executedCard = null;
                selectedCard = null;
            }
        }
        
        //player 2 discard
        if(selectedCard != null && selectedCard.getPlayer() == 2){
            
            if(Mouse.getX() > getDiscard().getLeft() //left side of lane
                    && Mouse.getX() < getDiscard().getRight() // right side of lane
                    && Mouse.getY() > 0// bottom of lane
                    && Mouse.getY() < getCardHeight() // bottom of lane
                    && gc.getInput().isMousePressed(1)){ // right click
                
                pTwoDeck.getPlayDeck().add(selectedCard);
                pTwoHand.remove(selectedCard);
                executedCard = null;
                selectedCard = null;
            }
            
        }
    }
    
    public DiscardPile getDiscard(){
        return discard;
    }
    
    public int getCardHeight(){
        return cardHeight;
    }
    
    public int getCardWidth(){
        return cardWidth;
    }
        
    public ArrayList<Card> getPOneHand(){
        return pOneHand;
    }
    
    public ArrayList<Card> getPTwoHand(){
        return pTwoHand;
    }
    
    public Deck getPOneDeck(){
        return pOneDeck;
    }
    
    public Deck getPTwoDeck(){
        return pTwoDeck;
    }
    
    public ArrayList<Lane> getLanes(){
        return lanes;
    }
    
    public Card getSelectedCard(){
        return selectedCard;
    }
    
    public void setSelectedCard(Card card){
        selectedCard = card;
    }
    
    public Card getExecutedCard(){
        return executedCard;
    }
    
    public void setExecutedCard(Card card){
        executedCard = card;
    }
    
    public boolean wasCardExecuted(){
        return cardWasExecuted;
    }
}
