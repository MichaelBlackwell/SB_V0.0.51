/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javagame;


import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Michael
 */
public class Rules {
    private Table table;
    private int playerTurn;
    private int turnNum;
    private enum Phase {SETUP_CHOOSE,
                        SETUP_EXECUTE,
                        DEAL,
                        CHOOSE_CARD,
                        EXECUTE_CARD,
                        CHOOSE_MOVE,
                        CHOOSE_FIRE,
                        CHOOSE_RALLY,
                        END_TURN,
                        DRAW_CARDS,
                        END_GAME};
    private int xpos;
    private int ypos;
    private Input input;
    private Image nextButton;
    private Random rand = new Random();
    private Phase phase;
    private Boolean endTurn;
    private int pOneGuysLeft = 0;
    private int pTwoGuysLeft = 0;
    public String log = " ";
    
    public Rules(Table table){
        this.table = table;
        this.phase = Phase.SETUP_CHOOSE;
        this.playerTurn = 1;
        this.turnNum = 0;
        
        
    }
    
    //deal all personnel cards (and later equipment and vehicle cards) to players
    //and have them place their cards (unseen by the opponent) into teams on the table
    
    public void init(){
        
        //deal all personnel cards
        for(int i = 0; i < 7; i++){
            table.getPOneHand().add(table.getPOneDeck().dealSetupCard());
            table.getPTwoHand().add(table.getPTwoDeck().dealSetupCard());
        }
        try{
            nextButton = new Image("res/SB-Buttons_1.png");
        }catch(SlickException e){
            e.printStackTrace();
        }
        
        
    }
    
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g){
        
        //display log
        g.drawString(log, table.getCardWidth() * 7 + 10, 10);
        
        //choose card phase
        if(phase == Phase.CHOOSE_CARD || phase == Phase.SETUP_CHOOSE){
            //tell player to pick a card
            g.drawString("Player " + playerTurn + ", pick a card", 10 , gc.getHeight()- 120);
            
            //display previously executed card
            g.drawString("", playerTurn, playerTurn);
        }
        
        //Execute card phase
        else if (phase == Phase.EXECUTE_CARD || phase == Phase.SETUP_EXECUTE){
            
            //display card stats
            g.drawString("Card: " + table.getSelectedCard().getName(), table.getCardWidth() * 7 + 10 , gc.getHeight()- 120);
            g.drawString("Type: " + table.getSelectedCard().getType(), table.getCardWidth() * 7 + 10 , gc.getHeight()- 105);
            g.drawString("Description: " + table.getSelectedCard().getDes(), table.getCardWidth() * 7 + 10 , gc.getHeight()- 90);
            //g.drawString("Rules: " + table.getSelectedCard().getType(), table.getCardWidth() * 7 + 10 , gc.getHeight()- 105);
            //tell player to play a card
            g.drawString("Player " + playerTurn + ", play the card", 10 , gc.getHeight()- 120);
        }
        //end turn
        else{
            //next button
            nextButton.draw((gc.getWidth()/2) - (nextButton.getWidth()/2), (gc.getHeight()/2) - (nextButton.getHeight()/2) + 40);
            g.drawString("Player " + playerTurn + ", press the play button", 10 , gc.getHeight()- 120);
            
        }
        
        
    }
    
    //Players alternate: choosing action or terrain cards, picking a team to play it on, 
    //and (if neccisary) choose a an opponets team.
    public void update(GameContainer gc, StateBasedGame sbc, int delta){
        xpos = Mouse.getX();
        ypos = Mouse.getY();
        input = gc.getInput();
        
        
        //Turn sequence
        if(phase == Phase.SETUP_CHOOSE){
            
            
            if(table.getSelectedCard() != null)
            {
                //display card stats
                
                phase = Phase.SETUP_EXECUTE;
            }
            
        }
        
        else if(phase == Phase.SETUP_EXECUTE){
            
            //move P one card
            if(table.getSelectedCard() != null){
                for(Lane checkLane : table.getLanes()){
                    if(Mouse.getX() > checkLane.getLeft() //left side of lane
                            && Mouse.getX() < checkLane.getRight() // right side of lane
                            && Mouse.getY() > checkLane.getTop()// top of lane
                            && Mouse.getY() < checkLane.getBottom() // bottom of lane
                            && gc.getInput().isMousePressed(1)){ // right click
                        checkLane.addCard(table.getSelectedCard());
                        table.getSelectedCard().setLane(checkLane);
                        if(table.getSelectedCard().getPlayer() == 1){
                            table.getPOneHand().remove(table.getSelectedCard());
                        }else{
                            table.getPTwoHand().remove(table.getSelectedCard());
                        }
                        table.setExecutedCard(table.getSelectedCard());
                        table.setSelectedCard(null);
                        endTurn = true;
                        phase = Phase.SETUP_CHOOSE; 
                        if(playerTurn == 1){
                            playerTurn = 2;
                        }else{
                            playerTurn = 1;
                        }
                    }
                }
            }
            
            //place cards into lanes until both players' hands are empty
            if(table.getPTwoHand().isEmpty()){
                phase = Phase.DEAL;
            }
            else if(table.getSelectedCard() == null)
            {
                
            }
        }
        
        //deal starting action and terrain cards
        else if(phase == Phase.DEAL){
            
            //deal play cards
            for(int i = 0; i < 7; i++){
                table.getPOneHand().add(table.getPOneDeck().dealPlayCard());
                table.getPTwoHand().add(table.getPTwoDeck().dealPlayCard());
            }
            
            //reset turn, phase, and prevExecutedCard
            phase = Phase.CHOOSE_CARD;
            this.playerTurn = 1;
        }
        
        //choose card phase
        else if(phase == Phase.CHOOSE_CARD){
            
            
            
            if(table.getSelectedCard() != null)
            {
                //display card stats
                phase = Phase.EXECUTE_CARD;
            }
        }
        
        //Execute card phase
        else if (phase == Phase.EXECUTE_CARD ){
            
            //move and do card's action
            if(table.getSelectedCard() != null){
                for(Lane checkLane : table.getLanes()){
                    if(Mouse.getX() > checkLane.getLeft() //left side of lane
                            && Mouse.getX() < checkLane.getRight() // right side of lane
                            && Mouse.getY() > checkLane.getTop()// top of lane
                            && Mouse.getY() < checkLane.getBottom() // bottom of lane
                            && gc.getInput().isMousePressed(1) // right click
                            && executeCheck(checkLane)){ //rules check
                        checkLane.addCard(table.getSelectedCard());
                        table.getSelectedCard().setLane(checkLane);
                        if(table.getSelectedCard().getPlayer() == 1){
                            table.getPOneHand().remove(table.getSelectedCard());
                        }else{
                            table.getPTwoHand().remove(table.getSelectedCard());
                        }
                        table.setExecutedCard(table.getSelectedCard());
                        table.setSelectedCard(null);
                        executeCard();
                        endTurn = true;
                    }
                }
            }
        }
        //end turn
        else{
            //mouse controller for next turn button
            if((xpos > ((gc.getWidth()/2) - (nextButton.getWidth()/2)) 
                    && xpos < ((gc.getWidth()/2) + (nextButton.getWidth()/2))) 
                    && (ypos > ((gc.getHeight()/2) - (nextButton.getHeight()/2) - 40) // 40 is vertical distance from center
                    && ypos < ((gc.getHeight()/2) + (nextButton.getHeight()/2) - 40))){
                if(input.isMousePressed(0)){
                    
                    if(endTurn){
                
                        if(playerTurn == 1){
                            //deal 1 card
                            table.getPOneHand().add(table.getPOneDeck().dealPlayCard());
                            playerTurn = 2;
                            //shuffle deck
                            Collections.shuffle(table.getPOneDeck().getPlayDeck());
                        }else{
                            //deal 1 card
                            table.getPTwoHand().add(table.getPTwoDeck().dealPlayCard());
                            playerTurn = 1;
                            //shuffle deck
                            Collections.shuffle(table.getPTwoDeck().getPlayDeck());
                        }
                        //shuffle deck

                    }
                    endTurn = false;
                    
                    log = "";
                    
                    //check for winner
                    for(Lane checkLane : table.getLanes()){
                        for(Card checkCard : checkLane.getPlayerOneCards()){
                            if(checkCard.getType() == "Personnel"){
                                pOneGuysLeft++;
                            }
                        }
                    }
                    
                    for(Lane checkLane : table.getLanes()){
                        for(Card checkCard : checkLane.getPlayerTwoCards()){
                            if(checkCard.getType() == "Personnel"){
                                pTwoGuysLeft++;
                            }
                        }
                    }
                    if(pOneGuysLeft == 0 || pTwoGuysLeft == 0){
                        sbc.enterState(2);
                    }
                    else{
                        phase = Phase.CHOOSE_CARD;
                        pOneGuysLeft = 0;
                        pTwoGuysLeft = 0;
                    }
                }
            }
        }
    }
    
    public int getPlayerTurn(){
        return playerTurn;
    }
    
    public int getPhase(){
        switch(phase){
            case SETUP_CHOOSE:
                return 0;
            case SETUP_EXECUTE:
                return 1;
            case DEAL:
                return 2;
            case CHOOSE_CARD:
                return 3;
            case EXECUTE_CARD:
                return 4;
            case CHOOSE_MOVE:
                return 5;
            case CHOOSE_FIRE:
                return 6;
            case CHOOSE_RALLY:
                return 7;
            case END_TURN:
                return 8;
            case END_GAME:
                return 9;
            case DRAW_CARDS:
                return 10;
            default:
                return -99;
        }
    }
    
    public void executeCard(){
        if(table.getExecutedCard().getType() == "ActionFire"){
            exFireCard();
            phase = Phase.END_TURN; 
        }
        if(table.getExecutedCard().getName() == "Move"){
            exMoveCard();
            phase = Phase.END_TURN; 
        }
        if(table.getExecutedCard().getType() == "Terrain"){
            exTerrainCard();
            phase = Phase.END_TURN; 
        }
        if(table.getExecutedCard().getType() == "ActionRally"){
            exRallyCard();
            phase = Phase.END_TURN; 
        }
        if(table.getExecutedCard().getType() == "ActionConcealed"){
            exConcealCard();
            phase = Phase.END_TURN; 
        }
        if(table.getExecutedCard().getName() == "Smoke"){
            exSmokeCard();
            phase = Phase.END_TURN; 
        }
        if(table.getExecutedCard().getName() == "Sniper"){
            exSniperCard();
            phase = Phase.END_TURN; 
        }
        if(table.getExecutedCard().getName() == "Hero"){
            exHeroCard();
            phase = Phase.END_TURN; 
        }
        if(table.getExecutedCard().getName() == "Wire"){
            exWireCard();
            phase = Phase.END_TURN; 
        }
    }
    
    public void exFireCard(){
        
        if(playerTurn == 1){
            Iterator<Card> ite = table.getExecutedCard().getLane().getPlayerTwoCards().iterator();
            while(ite.hasNext()){
               Card checkCard = ite.next();
               if(checkCard.getType() == "Personnel"){
                    
                    //KIA
                    if (battleRoll(table.getExecutedCard()) > checkCard.getKIA()){
                        ite.remove();
                        log += checkCard .getName() + " is killed.\n";
                    }
                    //PANIC
                    else if (battleRoll(table.getExecutedCard()) > checkCard.getPanic()){
                        checkCard.setPanicked(true);
                        log += checkCard .getName() + " is panicked.\n";
                    }
                    //ROUT
                    else if (battleRoll(table.getExecutedCard()) > checkCard.getMorale()){
                        checkCard.setRouted(true);
                        log += checkCard .getName() + " is Routed.\n";
                    }
                    //NO EFFECT
                    else{
                        log += checkCard .getName() + " is unharmed.\n";
                    }
                    
                }
            }
            table.getExecutedCard().getLane().getPlayerOneCards().remove(table.getExecutedCard());
            table.getPOneDeck().addPlayCard(table.getExecutedCard());
        }
        
        if(playerTurn == 2){
            Iterator<Card> ite = table.getExecutedCard().getLane().getPlayerOneCards().iterator();
            while(ite.hasNext()){
               Card checkCard = ite.next();
               if(checkCard.getType() == "Personnel"){
                    //KIA
                    if (battleRoll(table.getExecutedCard()) > checkCard.getKIA()){
                        ite.remove();
                        log += checkCard .getName() + " is killed.\n";
                    }
                    //PANIC
                    else if (battleRoll(table.getExecutedCard()) > checkCard.getPanic()){
                        checkCard.setPanicked(true);
                        log += checkCard .getName() + " is panicked.\n";
                    }
                    //ROUT
                    else if (battleRoll(table.getExecutedCard()) > checkCard.getMorale()){
                        checkCard.setRouted(true);
                        log += checkCard .getName() + " is Routed.\n";
                    }
                    //NO EFFECT
                    else{
                        log += checkCard .getName() + " is unharmed.";
                    }
                }
            }
            table.getExecutedCard().getLane().getPlayerTwoCards().remove(table.getExecutedCard());
            table.getPTwoDeck().addPlayCard(table.getExecutedCard());
        }
    }
    
    public int battleRoll(Card c){
        int result = 0;
        int randomNumOne = rand.nextInt((6 - 1) + 1) + 1;
        int randomNumTwo = rand.nextInt((6 - 1) + 1) + 1;
        
        if(c.getPlayer() == 1){
            result = randomNumOne + randomNumTwo 
                + c.getLane().getTwoTEM()
                + c.getFF();
        }
        
        if(c.getPlayer() == 2){
            result = randomNumOne + randomNumTwo 
                + c.getLane().getOneTEM()
                + c.getFF();
        }
        
        if(c.isPanicked()){
            result += 2;
        }else if(c.isRouted()){
            result += 1;
        }
        
        return result;
    }
    
    public void exMoveCard(){
        if(playerTurn == 1){
            table.getExecutedCard().getLane().forOneRR();
        }
        if(playerTurn == 2){
            table.getExecutedCard().getLane().forTwoRR();
        }
        log += "Player " + playerTurn + " moved closer to the enemy.\n";
    }
    
    public void exTerrainCard(){
        if(playerTurn == 1){
            Iterator<Card> ite = table.getExecutedCard().getLane().getPlayerOneCards().iterator();
            while(ite.hasNext()){
                Card checkCard = ite.next();
                if((checkCard.getType() == "Terrain" || checkCard.getName() == "Move" || checkCard.getName() == "Smoke") && checkCard != table.getExecutedCard()){
                    ite.remove();
                    table.getPOneDeck().addPlayCard(checkCard);
                }
            }
            
        }
        if(playerTurn == 2){
            Iterator<Card> ite = table.getExecutedCard().getLane().getPlayerTwoCards().iterator();
            while(ite.hasNext()){
                Card checkCard = ite.next();
                if((checkCard.getType() == "Terrain" || checkCard.getName() == "Move") && checkCard != table.getExecutedCard()){
                    ite.remove();
                    
                    table.getPTwoDeck().addPlayCard(checkCard);
                }
            }
        }
        
        log += "Player " + playerTurn + "'s troops moved to a " + table.getExecutedCard().getName() + ".\n";
    }
    
    public void exRallyCard(){
        if(playerTurn == 1){
            for(Card checkCard : table.getExecutedCard().getLane().getPlayerOneCards()){
                checkCard.setPanicked(false);
                checkCard.setRouted(false);
            }
            table.getExecutedCard().getLane().getPlayerOneCards().remove(table.getExecutedCard());
            table.getPOneDeck().addPlayCard(table.getExecutedCard());
        }
        if(playerTurn == 2){
            for(Card checkCard : table.getExecutedCard().getLane().getPlayerTwoCards()){
                checkCard.setPanicked(false);
                checkCard.setRouted(false);
            }
            table.getExecutedCard().getLane().getPlayerTwoCards().remove(table.getExecutedCard());
            table.getPTwoDeck().addPlayCard(table.getExecutedCard());
        }
        
        log += "Player " + playerTurn + " rallied his troops.\n";
        
    }
    
    private void exConcealCard(){
        
    }
    
    private void exSmokeCard(){
        
    }
    
    private void exSniperCard(){
        
    }
    
    private void exHeroCard(){
        
    }
    
    private void exWireCard(){
        
    }
    private boolean executeCheck(Lane lane){
        log = "";
        
        //fire card check
        if(table.getSelectedCard().getType() == "ActionFire"){
            log += "Execute Fire Card.\n";
            log +=  "Cost in FP: " + table.getSelectedCard().getFP() + " Lane FP: " + lane.getOneFP() + "\n";
            if(lane.getOneFP() < table.getSelectedCard().getFP() && playerTurn == 1){
                log += "Lane does not meet FP requirement.\n";
                return false;
            }
            else if(lane.getTwoFP() < table.getSelectedCard().getFP() && playerTurn == 2){
                log += "Lane does not meet FP requirement.\n";
                return false;
            }
            else{
                return true;
            }
        }
        /*
        //terrain card check
        if(table.getSelectedCard().getType() == "Terrain"){
            log += "Place Terrain Card.\n";
            if(playerTurn == 1){
                for(Card checkCard : table.getSelectedCard().getLane().getPlayerOneCards()){
                    if(checkCard.getName() == "Move"){
                        log += "Move card exists.\n";
                        return true;
                    }
                }
            }
            if(playerTurn == 2){
                for(Card checkCard : table.getSelectedCard().getLane().getPlayerTwoCards()){
                    if(checkCard.getName() == "Move"){
                        log += "Move card exists.\n";
                        return true;
                    }
                }
            }
            else{
                return false;
            }
        }
        */
        return true;
    }
}
