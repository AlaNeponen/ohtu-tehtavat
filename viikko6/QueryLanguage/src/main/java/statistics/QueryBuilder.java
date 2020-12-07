/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;
import java.util.ArrayList;
import statistics.matcher.*;
/**
 *
 * @author nate
 */
public class QueryBuilder {
    private Matcher matcher;
    
    public QueryBuilder() {
        this.matcher = new All();
    }
    
    public Matcher build() {
        return this.matcher;
    }

    public QueryBuilder playsIn(String joukkue) {
        this.matcher = new And(this.matcher, new PlaysIn(joukkue));
        return this;
    }

    public QueryBuilder hasAtLeast(int value, String fieldName) {
        this.matcher = new And(this.matcher, new HasAtLeast(value, fieldName));
        return this;
    }

    public QueryBuilder hasFewerThan(int value, String field) {
        this.matcher = new And(this.matcher, new HasFewerThan(value, field));
        return this;
    }
    public QueryBuilder not(Matcher matcher) {
        this.matcher = new And(this.matcher, new Not(matcher));
        return this;
    }
}
