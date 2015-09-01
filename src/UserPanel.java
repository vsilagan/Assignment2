/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.event.*;
        
import java.util.ArrayList;

/**
 *
 * @author Virgilio Ilagan III
 */
public class UserPanel extends JFrame implements ActionListener, FocusListener, MouseListener{
    private static UserGroup root;
    private static ArrayList<UserPanel> currentUsersOpen = new ArrayList<>();
    
    @SuppressWarnings("FieldMayBeFinal")
    private User user;
    private Timer updateChecker;
    
    //Add User Panel
    private JPanel addUser;
    private JTextField addUserField;
    private JButton addUserButton;
    
    //Followers List
    private JPanel followeesList;
    private JScrollPane followeesListScroll;
    private JTextArea followeesListArea;
    
    //Tweet
    private JPanel tweet;
    private JTextField tweetField;
    private JButton tweetButton;
    
    //News Feed
    private JPanel newsFeed;
    private JScrollPane newsFeedScroll;
    private JTextArea newsFeedArea;    
    
    private UserPanel(User user)
    {
        this.user = user;
        this.setTitle(user.getName() + " - User Panel");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(300,400);
        initializeSwingComponents();
        buildGUI();
        updateChecker = new Timer (10,this);
        updateChecker.start();
        this.setVisible(true);
    }
    
    private void initializeSwingComponents()
    {
        //add user panel
        addUser = new JPanel(new java.awt.BorderLayout());
        addUserField = new JTextField("Enter User Name");
        addUserButton = new JButton("Follow User");
        
        //followers list panel
        followeesList = new JPanel(new java.awt.BorderLayout());
        followeesListArea = new JTextArea();
        followeesListScroll = new JScrollPane(followeesListArea);
        
        //tweet panel
        tweet = new JPanel(new java.awt.BorderLayout());
        tweetField = new JTextField("Enter Tweet");
        tweetButton = new JButton("Submit Tweet");
        
        //Message panel
        newsFeed = new JPanel(new java.awt.BorderLayout());
        newsFeedArea = new JTextArea();
        newsFeedScroll = new JScrollPane(newsFeedArea);
    }
    
    private void buildTextAndButtonPanel(JPanel panel, JTextField field, JButton button, int sizeX, int sizeY, int x, int y, String title)
    {
        this.add(panel);
        panel.add(field, java.awt.BorderLayout.NORTH);
        panel.add(button, java.awt.BorderLayout.CENTER);
        field.addFocusListener(this);
        button.addActionListener(this);
        
        panel.setSize(sizeX, sizeY);
        panel.setLocation(x,y);
        panel.setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EtchedBorder(),title));
        panel.setVisible(true);
    }
    
    private void buildList(JPanel panel, JTextArea area,String contents, JScrollPane scroll, int sizeX, int sizeY, int x, int y, String title)
    {
        this.add(panel);
        area.setText(contents);
        area.setEditable(false);
        panel.add(scroll);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setLocation(x,y);
        panel.setSize(sizeX,sizeY);
        panel.setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EtchedBorder(),title));
        area.addMouseListener(this);
        scroll.addMouseListener(this);
        panel.addMouseListener(this);
        scroll.setLocation(5,20);
        scroll.setSize(sizeX-10,sizeY-25);
    }
    
    private void buildGUI()
    {
        this.setLayout(null);
        
        //addUser Panel
        buildTextAndButtonPanel(addUser, addUserField, addUserButton,275,60,5,5, "Add User");
        
        //Follower List Panel
        buildList(followeesList,followeesListArea, user.getFollowingList(),followeesListScroll, 275,100,5,70,"Followers");

        //Tweet Panel
        buildTextAndButtonPanel(tweet, tweetField, tweetButton,275,60,5,170, "Tweet Message");
        
        //Tweet Panel
        buildList(newsFeed, newsFeedArea, user.getTweets(), newsFeedScroll, 275, 115, 5, 240, "Messages");
    }
    
    public static UserPanel newUserFrame (User u)
    {
        
        if(currentUsersOpen.isEmpty())
        {
            UserPanel temp = new UserPanel(u);
            currentUsersOpen.add(temp);
            return temp;
        }
             
        for (int i = 0; i < currentUsersOpen.size(); i++)
        {
            if (currentUsersOpen.get(i).user.getName().equals(u.getName()))
            {
                return currentUsersOpen.get(i);
            }
            UserPanel temp = new UserPanel(u);
            currentUsersOpen.add(temp);
            return temp;
        }
    
        // It will probably never reach this.
        return null;
    }
    
    public static void setRoot(UserGroup u)
    {
        root = u;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == updateChecker)
        {
            followeesListArea.setText(user.getFollowingList());
            newsFeedArea.setText(user.getTweets());
            followeesListArea.setCaretPosition(0);
            newsFeedArea.setCaretPosition(0);
            
        }
        
        if(e.getSource() == addUserButton)
        {
            if(addUserField.getText().equals("") || addUserField.getText().equals("Enter User Name"))
                JOptionPane.showMessageDialog(new java.awt.Frame(), (Object) "No user name entered!");
            else if(root.isUserInGroup(addUserField.getText()))
                user.follow(root.getUser(addUserField.getText()));
            else
                JOptionPane.showMessageDialog(new java.awt.Frame(), (Object) "User does not exist!");
        }
        
        if(e.getSource() == tweetButton)
        {
            if(tweetField.getText().equals("") || tweetField.getText().equals("Enter Tweet"))
                JOptionPane.showMessageDialog(new java.awt.Frame(), (Object) "No tweet entered!");
            else
                user.tweet(tweetField.getText());
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == addUserField)
            addUserField.setText("");
        if (e.getSource() == tweetField)
            tweetField.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        updateChecker.stop();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        updateChecker.start();
    }
}