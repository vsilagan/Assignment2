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
public class UserGroup implements UGComponent{

    @SuppressWarnings("FieldMayBeFinal")
    private String name;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<UGComponent> components;
    
    public UserGroup(String name)
    {
        components = new ArrayList<>();
        this.name = name;
    }
    
    public UGComponent getMember(int i)
    {
        return components.get(i);
    }
    
    public User getUser(String s)
    {
        User foundUser;
        for (int i = 0; i < components.size(); i++)
        {
            if (components.get(i) instanceof User)
            {
                if (s.equalsIgnoreCase(components.get(i).getName()))
                {
                    return (User)components.get(i);
                }
            }
            if (components.get(i) instanceof UserGroup)
            {
                UserGroup temp = (UserGroup)components.get(i);
                foundUser = temp.getUser(s);
                if (foundUser != null) return foundUser;
            }
        }
        return null; 
    }
    
    public void addComponent(UGComponent newComponent)
    {
        components.add(newComponent);
    }
    
    public void addComponentToGroup(UGComponent newComponent, String groupName)
    {
        if (groupName.equals(this.getName())) components.add(newComponent);
        else
        for (int i = 0; i < components.size(); i++)
        {
            if (components.get(i) instanceof UserGroup)
            {
                UserGroup temp = (UserGroup)components.get(i);
                if (temp.getName().equalsIgnoreCase(groupName))
                    temp.addComponent(newComponent);
            }
        }
    }
    
    public String showUserGroups(int level)
    {
        String twitterList = "";
        for (int i = 0; i < level; i++)
            twitterList += "   ";
        
        twitterList += "+ " + this.getName() + "\n";
        
        for (int i = 0; i < components.size();i++)
        {
            if(components.get(i) instanceof User)
            {
                for (int j = 0; j < level+1;j++)
                    twitterList += "   ";
                twitterList += "- " + components.get(i).getName()+ "\n";
            }
            
            if(components.get(i) instanceof UserGroup)
            {
                UserGroup temp = (UserGroup)components.get(i);
                twitterList += temp.showUserGroups(level + 1);
            }
        }
        
        return twitterList;
    }
    
    public boolean isUserInGroup(String id)
    {
        boolean found = false;
        if (components.isEmpty()) return false;
        for (int i = 0; i < components.size(); i++)
        {
            if (components.get(i) instanceof User)
            {
                if (id.equalsIgnoreCase(components.get(i).getName()))
                {
                    return true;
                }
            }
            if (components.get(i) instanceof UserGroup)
            {
                UserGroup temp = (UserGroup)components.get(i);
                found = temp.isUserInGroup(id);
                if (found) return found;
            }
        }
        return found; 
    }

    public boolean isGroupInGroup(String id)
    {
        boolean found = false;
        if (this.getName().equals(id)) return true;
        if (components.isEmpty()) return false;
        for (int i = 0; i < components.size(); i++)
        {
            if (components.get(i) instanceof UserGroup)
            {
                UserGroup temp = (UserGroup)components.get(i);
                if (temp.getName().equalsIgnoreCase(id)) return true;
                found = temp.isGroupInGroup(id);
                if (found) return found;
            }
        }
        return found; 
    }
    
    public int getNumberofComponents()
    {
        return components.size();
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void acceptTwitterVisitor(TwitterVisitor vis) 
    {
        vis.atUserGroup(this);
    }
    
    public static void main(String args[])
    {
        UserGroup g = new UserGroup("root");
        g.addComponent(new User("Clive"));
        g.addComponent(new User("John"));
        UserGroup b = new UserGroup("Group 1");
        g.addComponent(new User ("Joe"));
        b.addComponent (new User("Renly"));
        b.addComponent(new UserGroup("Group 2"));
        g.addComponent(b);
        if (!g.isUserInGroup("Joe")) b.addComponent(new User("Joe"));
        else System.out.println("this user already exists!");
        if (!g.isGroupInGroup("Group 2")) b.addComponent(new UserGroup("Group 2"));
        else System.out.println("this group already exists!");
        g.addComponent(new UserGroup("Group 3"));
        
        System.out.println(g.showUserGroups(0));
        System.out.println("\n------------------------------\n");
        System.out.println(b.showUserGroups(0));
        
        
    }


}
