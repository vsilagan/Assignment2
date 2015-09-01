/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Virgilio Ilagan III
 */
public class UserGroupCounter extends TwitterVisitor{
    
    public UserGroupCounter()
    {
        counter = 0;
    }
    
    @Override
    public void atUser(User u) {
        //User group counter does not count 
    }

    @Override
    public void atUserGroup(UserGroup u) {
        counter++;
        for (int i = 0; i < u.getNumberofComponents(); i++)
        {
            if(u.getMember(i) instanceof UserGroup)
            {
                counter++;
                atUserGroup((UserGroup) u.getMember(i));       
            }
        }
    }    
}
