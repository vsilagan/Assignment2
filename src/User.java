/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
/**
 *
 * @author Virgilio Ilagan III
 */
public class User extends Observable implements Observer, UGComponent{
    @SuppressWarnings("FieldMayBeFinal")
    private String userID;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<User> following;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<String> tweets;
    private int tweetCounter;
    
    public User(String iD)
    {
        this.userID = iD;
        following = new ArrayList<>();
        tweets = new ArrayList<>();
        
    }
    
    public void follow(User followedUser)
    {
        if(!following.contains(followedUser))
        {
            if(followedUser.getName().equals(this.getName()))
            {
                javax.swing.JOptionPane.showMessageDialog(new java.awt.Frame(), (Object) "You cannot follow yourself!!");
            }
            else
            {
                followedUser.addObserver(this);
                following.add(followedUser);
            }
        }
        else
        {
            javax.swing.JOptionPane.showMessageDialog(new java.awt.Frame(), (Object) "You are already following this User!");
        }
    }
    
    public void tweet(String message)
    {
        tweets.add(0,getName()+ " : " + message);
        setChanged();
        notifyObservers(message);
        tweetCounter++;
    }
    
    public int getNumberofMessages()
    {
        return tweetCounter;
    }
    
    public String getUserMessage(int i)
    {
        return tweets.get(i);
    }
    
    public String getTweets()
    {
        String messages = "Your Messages:\n";
        for (int i = 0; i < tweets.size(); i++)
            messages += " - " + tweets.get(i) + "\n";
        return messages;
    }
    
    public String getFollowingList()
    {
        String followingList = "Currently Following:\n";
        for (int i=0; i< following.size(); i++)
            followingList += " - " + following.get(i).getName() + "\n";
        return followingList;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        User temp = (User) o;
        if (arg instanceof String)
            tweets.add(0,temp.getName()+ " : " + arg);
    }
    
    @Override
    public String getName() {
        return userID;
    }
    
    @Override
    public void acceptTwitterVisitor(TwitterVisitor vis) {
        vis.atUser(this); 
   }

    public static void main(String args[])
    {
        User user1 = new User("Joe");
        User user2 = new User("Frank");
        user2.follow(user1);
        user2.follow(new User("Anne"));
        user2.follow(new User("Steve"));
        user2.tweet("Just followed Joe, Anne, and Steve. What cool people!");
        user1.tweet("Thanks Frank! You're a cool guy as well");
        user1.tweet("Man, turnips are great");
        
        System.out.println(user1.getTweets());
        System.out.println("\n----------------------\n");
        System.out.println(user2.getTweets());
        System.out.println("\n----------------------\n");
        System.out.println("Frank is " + user2.getFollowingList());
       
        
    }
}
