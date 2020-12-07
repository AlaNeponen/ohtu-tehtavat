/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.matcher;

import statistics.Player;

/**
 *
 * @author nate
 */
public class HasFewerThan implements Matcher{
    private int value;
    private String fieldName;
    private HasAtLeast atLeast;
    
    public HasFewerThan(int value, String field) {
        this.value = value;
        this.fieldName = field;
        this.atLeast = new HasAtLeast(this.value, this.fieldName);
    }
    @Override
    public boolean matches(Player p) {
        return !(atLeast.matches(p));
    }
    
}
