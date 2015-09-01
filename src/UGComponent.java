/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Virgilio Ilagan III
 */
public interface UGComponent {
    public String getName();
    public void acceptTwitterVisitor(TwitterVisitor vis);
}
