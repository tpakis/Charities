package greek.dev.challenge.charities.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nalex on 26/12/2017.
 */

public class Wish {

    private String wish;
    private String author;
    private String charityName;
    private long timestamp;

    public Wish(){
        //default constructor needed...
    }

    public Wish(String wish, String author, String charityName, long timestamp) {
        this.author = author;
        this.wish = wish;
        this.charityName = charityName;
        this.timestamp = timestamp;
    }

    //getters also needed
    public String getWish() {
        return wish;
    }

    public String getAuthor() {
        return author;
    }

    public long getTimestamp() {return timestamp;}

    public String getCharityName() {return charityName;}

}
