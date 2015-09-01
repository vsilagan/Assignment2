/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Virgilio Ilagan III
 */
public abstract class TwitterVisitor {
    protected int counter;
    
    public abstract void atUser(User u);
    
    public void atUserGroup(UserGroup u) {
        for (int i=0; i< u.getNumberofComponents();i++)
        {
            if(u.getMember(i) instanceof User)
                atUser((User) u.getMember(i));
            if(u.getMember(i) instanceof UserGroup)
                atUserGroup((UserGroup) u.getMember(i));
        }
    }
    
    public int getCounter() {
        return counter;
    }
}
