/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javagame;

/**
 *
 * @author Michael
 */
public class Counter {
    private final int id;
    private final int type;
    
    Counter(int theId, int theType)
    {
        id = theId;
        type = theType;
    }
    
    int GetType()
    {
        return type;
    }
    
    int GetId()
    {
        return id;
    }
}
