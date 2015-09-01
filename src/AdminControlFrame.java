/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Frame;
    import javax.swing.*;
    import java.awt.event.*;
/**
 *
 * @author Virgilio Ilagan III
 */
public class AdminControlFrame extends JFrame implements ActionListener,FocusListener{
    private static AdminControlFrame adminCP;
    @SuppressWarnings("FieldMayBeFinal")
    private UserGroup root;
    
    //User Group List
    private JPanel uGTextPanel;
    private JTextArea uGTextArea;
    private JScrollPane uGScrollPane;
    
    //user functions
    private JPanel userFunctions;
    private JTextField userField;
    private JButton addUser;
    private JButton openUserFrame;
    
    //group functions
    private JPanel groupFunctions;
    private JTextField groupField;
    private JButton addGroup;
    
    //statistics
    private JPanel statistics;
    private JButton showNumberOfUsers;
    private JButton showNumberOfGroups;
    private JButton showNumberOfMessages;
    private JButton showNumberOfPositives;
    
    
    private AdminControlFrame()
    {
        root = new UserGroup("root");
        
        UserPanel.setRoot(root);
        this.setTitle("Admin Control Panel");
        this.setSize(420, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeSwingComponents();
        buildGUI();
        this.setVisible(true);
    }
    
    private void initializeSwingComponents()
    {
        // User Group List
        uGTextPanel = new JPanel(new java.awt.BorderLayout());
        uGTextArea = new JTextArea();
        uGScrollPane = new JScrollPane(uGTextArea);
        
        //User functions
        userFunctions = new JPanel(new java.awt.BorderLayout());
        userField = new JTextField("Enter User Name");
        
        addUser = new JButton("Add User");
        openUserFrame = new JButton("Open User Interface");
        
        //Group Functions
        groupFunctions = new JPanel(new java.awt.BorderLayout());
        groupField = new JTextField("Enter Group Name");
        addGroup = new JButton("Add Group");
        
        //Statistics
        statistics = new JPanel(new java.awt.GridLayout(0,1));
        showNumberOfUsers = new JButton("# of Users");
        showNumberOfGroups = new JButton("# of Groups");
        showNumberOfMessages = new JButton("# of Messages");
        showNumberOfPositives = new JButton("# of Positive Words");
    }
    
    private void buildGUI()
    {
        this.setLayout(null);
        
        // User Group List

        this.add(uGTextPanel);
        uGTextArea.setText(root.showUserGroups(0));
        uGTextArea.setEditable(false);
        uGTextPanel.add(uGScrollPane);
        uGScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        uGTextPanel.setLocation(10, 10);
        uGTextPanel.setSize(190,340);
        uGTextPanel.setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EtchedBorder(),"User Groups"));
        uGScrollPane.setLocation(5,20);
        uGScrollPane.setSize(180,315);
       

        //User functions

        this.add(userFunctions);
        
        userFunctions.add(userField, java.awt.BorderLayout.NORTH);
        userFunctions.add(addUser, java.awt.BorderLayout.CENTER);
        userFunctions.add(openUserFrame, java.awt.BorderLayout.SOUTH);
        addUser.setToolTipText("Adds user to root \nIf the groupfield is currently filled with the \nID of a valid group, it will add to that group instead");
        
        userFunctions.setLocation(210,10);
        userFunctions.setSize(190,90);
        userFunctions.setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EtchedBorder(),"User Functions"));
        
        addUser.addActionListener(this);
        userField.addFocusListener(this);
        openUserFrame.addActionListener(this);
        
        //Group Functions
        
        this.add(groupFunctions);
        groupFunctions.add(groupField, java.awt.BorderLayout.NORTH);
        groupFunctions.add(addGroup, java.awt.BorderLayout.CENTER);
        groupFunctions.setLocation(210,120);
        groupFunctions.setSize(190,90);
        groupFunctions.setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EtchedBorder(),"Group Functions"));       
        addGroup.addActionListener(this);
        groupField.addFocusListener(this);
        
        
         //Statistics
        
        this.add(statistics);
        statistics.add(showNumberOfUsers);
        statistics.add(showNumberOfGroups);
        statistics.add(showNumberOfMessages);
        statistics.add(showNumberOfPositives);
        showNumberOfUsers.addActionListener(this);
        showNumberOfGroups.addActionListener(this);
        showNumberOfMessages.addActionListener(this);
        showNumberOfPositives.addActionListener(this);
        statistics.setLocation(210,240);
        statistics.setSize(190,90);
        
    }
    
    public static AdminControlFrame getInstance()
    {
        if (adminCP == null)
            adminCP = new AdminControlFrame();
        
        return adminCP;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addUserButtonFunction(e);
        addGroupButtonFunction(e);
        if (e.getSource() == addUser || e.getSource() == addGroup)
            uGTextArea.setText(root.showUserGroups(0));
        openUserFrame(e);
        showNumberOfUsersButtonFunction(e);
        showNumberOfGroupsButtonFunction(e);
        showNumberOfMessagesButtonFunction(e);
        showNumberOfPositivesButtonFunction(e);
        
        
    }
    
    private void addUserButtonFunction(ActionEvent e)
    {
        if(e.getSource() == addUser)
        {
            if(userField.getText().equals("") || userField.getText().equals("Enter User Name"))
            {
                JOptionPane.showMessageDialog(new Frame(), (Object) "No User Entered!");
            }
            else
            {
                if(root.isUserInGroup(userField.getText()))
                    JOptionPane.showMessageDialog(new Frame(), (Object) "User is already in the System!");
                else
                {
                    // if the current string in group field is a valid group name, and is the name of an actual group
                    // add this user to that group
                    if((!groupField.getText().equals("") || !groupField.getText().equals("Enter Group Name")) 
                            && root.isGroupInGroup(groupField.getText()))
                    {
                        root.addComponentToGroup(new User(userField.getText()),groupField.getText());
                    }
                    else
                    {
                        root.addComponent(new User(userField.getText()));
                    }
                }    
            }
        }
    }
    
    private void addGroupButtonFunction(ActionEvent e)        
    {
        if (e.getSource() == addGroup)
        {
            if((groupField.getText().equals("") || groupField.getText().equals("Enter Group Name"))) 
            {
                JOptionPane.showMessageDialog(new Frame(), (Object) "No Group Name Entered!");
            }
            else
            {
                if(root.isGroupInGroup(groupField.getText()))
                {
                    JOptionPane.showMessageDialog(new Frame(), (Object) "This Group already Exists!");
                }
                else
                {
                    root.addComponent(new UserGroup(groupField.getText()));
                }
            }
        }
    }
    
    private void showNumberOfUsersButtonFunction(ActionEvent e)
    {
        if(e.getSource() == showNumberOfUsers)
        {
            TwitterVisitor vis = new UserCounter();
            root.acceptTwitterVisitor(vis);
            JOptionPane.showMessageDialog(new Frame(), (Object) ("Number of Users: " + vis.getCounter()));
        }
    }
    
    private void showNumberOfGroupsButtonFunction(ActionEvent e)
    {
        if(e.getSource() == showNumberOfGroups)
        {
            TwitterVisitor vis = new UserGroupCounter();
            root.acceptTwitterVisitor(vis);
            JOptionPane.showMessageDialog(new Frame(), (Object) ("Number of Groups: " + vis.getCounter()));
        }
    }
    
    private void showNumberOfMessagesButtonFunction(ActionEvent e)
    {
        if(e.getSource() == showNumberOfMessages)
        {
            TwitterVisitor vis = new MessageCounter();
            root.acceptTwitterVisitor(vis);
            JOptionPane.showMessageDialog(new Frame(), (Object) ("Number of Messages: " + vis.getCounter()));
        }
    }
    
    private void showNumberOfPositivesButtonFunction(ActionEvent e)
    {
        if(e.getSource() == showNumberOfPositives)
        {
            TwitterVisitor vis = new PositiveCounter();
            TwitterVisitor vis2 = new MessageCounter();
            root.acceptTwitterVisitor(vis);
            root.acceptTwitterVisitor(vis2);
            JOptionPane.showMessageDialog(new Frame(), (Object) ("\nPositive Words were found in " + (vis2.getCounter()!=0 ? (((float) vis.getCounter())/vis2.getCounter() * 100): 0) +"% of all messages"));
        }
    }
    private void openUserFrame(ActionEvent e)
    {
        if(e.getSource() == openUserFrame)
        {
            if(userField.getText().equals("") || userField.getText().equals("Enter User Name"))
            {
                JOptionPane.showMessageDialog(new Frame(), (Object) "No User Entered!");
            }
            else
            {
                if(root.isUserInGroup(userField.getText()))
                {
                    UserPanel p = UserPanel.newUserFrame(root.getUser(userField.getText()));
                    if (!p.isVisible()) p.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(new Frame(), (Object) "User does not exist!");
                }
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource() == userField)
            userField.setText("");
        if(e.getSource() == groupField)
            groupField.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
            
    }
}
