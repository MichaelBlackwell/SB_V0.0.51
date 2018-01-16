/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javagame;

import org.newdawn.slick.*;
import static org.newdawn.slick.Input.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author Michael
 */
public class Play extends BasicGameState{
    
    private Table table;
    private String mess = "";
    
    public Play(int state){
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbc) throws SlickException{
        
        table = new Table((byte)3);
        table.init(gc, sbc);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g) throws SlickException{
        
        //draw background
        g.setColor(new Color(173,154,130));
        g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
        
        //draw table
        g.setColor(new Color(40,73,7));
        table.render(gc, sbc, g);
        
        //draw message at top
        g.setColor(new Color(Color.black));
        g.drawString(mess, 100, 100);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException{
        
        Input input = gc.getInput();
        //update table
        table.update(gc, sbc, delta);
        
        if(input.isKeyDown(KEY_ESCAPE)){
            gc.exit();
        }
    }
    
    @Override
    public int getID(){
        return 1;
    }
    
    public Table getTable(){
        return table;
    }
}
