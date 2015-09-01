/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author Virgilio Ilagan III
 */
public class MessageCounter extends TwitterVisitor{
    
    
    public MessageCounter()
    {
        counter = 0;
    }

    @Override
    public void atUser(User u) {
        counter+=u.getNumberofMessages();
    }

    
}
