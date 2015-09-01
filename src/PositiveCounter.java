/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Virgilio Ilagan III
 */
public class PositiveCounter extends TwitterVisitor{

    public PositiveCounter()
    {
        counter = 0;
    }
    
    @Override
    public void atUser(User u) 
    {
        for(int i = 0; i < u.getNumberofMessages(); i++)
        {
            String str = u.getUserMessage(i);
            int count = 0;
            count += findPositiveOccurance(str, "good");
            count += findPositiveOccurance(str, "great");
            count += findPositiveOccurance(str, "awesome");
            count += findPositiveOccurance(str, "cool");
            if(count > 0)
                counter++;
        }
    }
    
    private static int findPositiveOccurance(String s, String target)
    {
        int count = 0;
        String newS = s.toUpperCase();
        String newT = target.toUpperCase();
        for (int i = 0; i < s.length();i++)
        {
           if(newS.substring(i).contains(newT))
           {
               //System.out.println(s.indexOf(target));
               i+=newT.length() + newS.substring(i).indexOf(newT);
               
               count++;
           }
        }
        return count;
    }
    
    public static void main(String args[])
    {
        String positives = "COOL";
        System.out.println(findPositiveOccurance(positives,"cool"));
        System.out.println(findPositiveOccurance(positives,"good"));
        System.out.println(findPositiveOccurance(positives,"great"));
    }
    
}
