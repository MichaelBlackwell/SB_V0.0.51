package javagame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Collections;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
/**
 *
 * @author Michael
 */
public class Deck {
    
    private final ArrayList<Card> playDeck;
    private final ArrayList<Card> setupDeck;
    private final int[][] fireCount = {{1,1,2,2,3,3,4,4,5},
                                {1,2,3,4,5,6,7,8,0},
                                {3,4,5,6,7,8,9,0,0},
                                {7,8,9,10,11,12,0,0,0},
                                {10,11,12,13,14,15,0,0,0},
                                {13,14,15,16,17,18,0,0,0},
                                {15,16,17,18,0,0,0,0,0},
                                {17,18,0,0,0,0,0,0,0}};
    private final int[] assaultRifleRange = {0,1,1,2,3,5};
    private final int[] LMGRange = {2,3,3,4,4,5};
    
    private final String name;
    private int cardsUsed;
    private int x;
    private int y;
    private final int player;
    private int cardCount;

    
    public Deck(String name, int player)
    {
        
        playDeck = new ArrayList<Card>();
        setupDeck = new ArrayList<Card>();
        cardCount = 100;
        cardsUsed = 0; 
        
        this.name = name;
        this.player = player;
            
        //initalize cards and add cards to player one's deck

        
    }
    
    public void init(GameContainer gc, StateBasedGame sbc) throws SlickException{
        
     
        
        
        try{
            
            //create all play cards
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 9; j++){
                    if(fireCount[i][j] != 0){
                        playDeck.add(new CardFire(fireCount[i][j] + " Fire " + (i+1), new Image ("res/GenericCard.png"), x, y, player, "ActionFire",
                            "Attack the enemy.","FP: " + fireCount[i][j] + " FF: " + (i + 1),fireCount[i][j],i+1));
                    }
                }
            }
            for(int j = 0; j < 27; j++){
                playDeck.add(new CardMove("Move" , new Image ("res/GenericCard.png"), x, y, player, "Action",
                    "Move out.","RR +/-1 TEM: +1",1));
            }
            for(int j = 0; j < 2; j++){
                playDeck.add(new CardTerrain("Smoke" , new Image ("res/GenericCard.png"), x, y, player, "Action",
                    "Temporary terrain.", "TEM: -1",-1));
            }
            for(int j = 0; j < 3; j++){
                playDeck.add(new CardTerrain("Wire" , new Image ("res/GenericCard.png"), x, y, player, "Action",
                    "Very sharp.","TEM: -1",-1));
            }
            for(int j = 0; j < 8; j++){
                playDeck.add(new CardTerrain("Concealed 1" , new Image ("res/GenericCard.png"), x, y, player, "ActionConcealed",
                    "Hide and go seek.","C",-1));
            }
            for(int j = 0; j < 4; j++){
                playDeck.add(new CardTerrain("Concealed 2" , new Image ("res/GenericCard.png"), x, y, player, "ActionConcealed",
                    "Hide and go seek.","C",-2));
            }
            for(int j = 0; j < 2; j++){
                playDeck.add(new CardTerrain("Concealed 3" , new Image ("res/GenericCard.png"), x, y, player, "ActionConcealed",
                    "Hide and go seek.","C",-3));
            }
            for(int j = 0; j < 7; j++){
                playDeck.add(new CardRally("Rally 1" , new Image ("res/GenericCard.png"), x, y, player, "ActionRally",
                    "Encouraging words.","Can flip 1 routed",1));
            }
            for(int j = 0; j < 5; j++){
                playDeck.add(new CardRally("Rally 2" , new Image ("res/GenericCard.png"), x, y, player, "ActionRally",
                    "Encouraging words.","Can flip 2 routed",2));
            }
            for(int j = 0; j < 4; j++){
                playDeck.add(new CardRally("Rally 3" , new Image ("res/GenericCard.png"), x, y, player, "ActionRally",
                    "Encouraging words.","Can flip 3 routed",3));
            }
            for(int j = 0; j < 3; j++){
                playDeck.add(new CardRally("Rally 4" , new Image ("res/GenericCard.png"), x, y, player, "ActionRally",
                    "Encouraging words.","Can flip 4 routed",4));
            }
            for(int j = 0; j < 2; j++){
                playDeck.add(new CardRally("Rally 5" , new Image ("res/GenericCard.png"), x, y, player, "ActionRally",
                    "Encouraging words.","Can flip 5 routed",5));
            }
            for(int j = 0; j < 1; j++){
                playDeck.add(new CardRally("Rally 6" , new Image ("res/GenericCard.png"), x, y, player, "ActionRally",
                    "Encouraging words.","Can flip 6 routed",6));
            }
            for(int j = 0; j < 1; j++){
                playDeck.add(new CardRally("Rally All" , new Image ("res/GenericCard.png"), x, y, player, "ActionRally",
                    "Encouraging words.","Can flip all routed",7));
            }
            for(int j = 0; j < 2; j++){
                playDeck.add(new CardHero("Hero" , new Image ("res/GenericCard.png"), x, y, player, "Action",
                    "Bravery can be seen.","Can flip 2 routed personnel"));
            }
            for(int j = 0; j < 5; j++){
                playDeck.add(new CardTerrain("Woods" , new Image ("res/CrdGenTerrain.png"), x, y, player, "Terrain",
                    "Effective cover.","TEM: -1",-1));
            }
            for(int j = 0; j < 3; j++){
                playDeck.add(new CardTerrain("Hill" , new Image ("res/CrdGenTerrain.png"), x, y, player, "Terrain",
                    "A small hill.","TEM: -1",-1));
            }
            for(int j = 0; j < 4; j++){
                playDeck.add(new CardTerrain("Buildings 2" , new Image ("res/CrdGenTerrain.png"), x, y, player, "Terrain",
                    "A few scattered buildings.","TEM: -2",-2));
            }
            for(int j = 0; j < 4; j++){
                playDeck.add(new CardTerrain("Buildings 3" , new Image ("res/CrdGenTerrain.png"), x, y, player, "Terrain",
                    "A few scattered buildings.","TEM: -3",-3));
            }
            for(int j = 0; j < 1; j++){
                playDeck.add(new CardTerrain("Bunker" , new Image ("res/CrdGenTerrain.png"), x, y, player, "Terrain",
                    "A concrete Bunker.","TEM: -4",-4));
            }
            for(int j = 0; j < 3; j++){
                playDeck.add(new CardTerrain("Gully" , new Image ("res/CrdGenTerrain.png"), x, y, player, "Terrain",
                    "A former river.","TEM: -2",-2));
            }
            for(int j = 0; j < 2; j++){
                playDeck.add(new CardTerrain("Stream" , new Image ("res/CrdGenTerrain.png"), x, y, player, "Terrain",
                    "Lots of water.","TEM: -2",-2));
            }
            for(int j = 0; j < 2; j++){
                playDeck.add(new CardTerrain("Marsh" , new Image ("res/CrdGenTerrain.png"), x, y, player, "Terrain",
                    "A swampy area.","TEM: -1",-1));
            }
            for(int j = 0; j < 6; j++){
                playDeck.add(new CardTerrain("Brush" , new Image ("res/CrdGenTerrain.png"), x, y, player, "Terrain",
                    "Overgrown grass.", "TEM: -1",-1));
            }
            for(int j = 0; j < 2; j++){
                playDeck.add(new CardTerrain("Minefield" , new Image ("res/CrdGenTerrain.png"), x, y, player, "Action",
                    "IEDs.", "TEM: +1",+1));
            }
       
            if(player == 1){
                
                //create player 1 setup cards
                for(int i = 0; i < 4; i++){
                    setupDeck.add(new CardPersonnel("Boot" , new Image ("res/CrdGenPerson.png"), x, y, player, "Personnel",
                            "Does what he's told.", "M:3 P:5 \nKIA: 8 FP:",assaultRifleRange,9,10,14));
                }
                for(int i = 0; i < 2; i++){
                    setupDeck.add(new CardPersonnel("MG" , new Image ("res/CrdGenPerson.png"), x, y, player, "Personnel",
                            "Leads the way.", "M:5 P:7 \nKIA:8 FP:",LMGRange,9,10,14));
                }
                setupDeck.add(new CardPersonnel("NCO" , new Image ("res/CrdGenPerson.png"), x, y, player, "Personnel",
                        "Smartest guy around.", "M:4 P:6 \nKIA: 8 FP:",assaultRifleRange,9,10,14));
            }else{
                
                //create player 2 setup cards
                for(int i = 0; i < 3; i++){
                    setupDeck.add(new CardPersonnel("Jihadist" , new Image ("res/CrdGenPerson.png"), x, y, player, "Personnel",
                            "Ala Ak Baud!", "M:3 P:5 \nKIA: 8 FP:",assaultRifleRange,9,10,14));
                    setupDeck.add(new CardPersonnel("Local" , new Image ("res/CrdGenPerson.png"), x, y, player, "Personnel",
                            "Pissed off Hadji.", "M:3 P:5 \nKIA: 8 FP:",assaultRifleRange,9,10,14));
                }
                setupDeck.add(new CardPersonnel("Milita" , new Image ("res/CrdGenPerson.png"), x, y, player, "Personnel",
                        "From far away.", "M:3 P:5 \nKIA: 8 FP:",assaultRifleRange,9,10,14));
                
            }
            
            Collections.shuffle(setupDeck);
            Collections.shuffle(playDeck);
        }catch(SlickException e){
            e.printStackTrace();
        }
    }

            
    public int cardsLeft()
    {
        return playDeck.size() - cardsUsed;
    }
    
    public Card dealSetupCard()
    {
        if (cardsUsed == setupDeck.size())
            throw new IllegalStateException("No cards are left in the deck.");
        cardsUsed++;
        return setupDeck.get(cardsUsed - 1);
    }
    
    public Card dealPlayCard()
    {
        if (cardsUsed == playDeck.size())
            throw new IllegalStateException("No cards are left in the deck.");
        cardsUsed++;
        return playDeck.get(cardsUsed - 1);
    }
    
    public void addPlayCard(Card c){
        cardsUsed--;
        playDeck.add(c);
    }
    
    public ArrayList<Card> getPlayDeck(){
        return playDeck;
    }
    
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g) throws SlickException{
        
    }
    
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException{
        
    }
}
