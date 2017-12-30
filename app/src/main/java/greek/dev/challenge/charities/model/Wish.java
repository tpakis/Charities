package greek.dev.challenge.charities.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nalex on 26/12/2017.
 */

public class Wish {

    private String wish;
    private String author;
    private int charityId;
    private long timestamp;

    public Wish(){
        //default constructor needed...
    }

    public Wish(String wish, String author, int charityId, long timestamp) {
        this.author = author;
        this.wish = wish;
        this.charityId = charityId;
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

    public int getCharityId() {return charityId;}

    //helper methods, to be DELETED!!!

    public static ArrayList<Wish> createWishList (int number) {
        ArrayList wishList = new ArrayList(number);
        for (int i = 0; i < number; i ++) {
            Random r = new Random();
            String fakeWish = wishesArray[r.nextInt(wishesArray.length)];
            String fakeAuthor = nameArray[r.nextInt(nameArray.length)];

            Wish newWish = new Wish(fakeWish, fakeAuthor, 0 ,0);
            wishList.add(i, newWish );
        }
        return wishList;



    }

    private static final String [] wishesArray = {
            "Χρόνια πολλά",
            "Ευτυχισμένο το 2018",
            "Εύχομαι ό,τι καλύτερο για το ίδρυμα σας!",
            "Μια μικρή χρηματική συμβολή με πολλή αγάπη.",
            "Σας στέλνουμε τη δωρεά μας και τις καλύτερες ευχές μας για το νέο έτος!",
            "Καλά μας δουλεύετε; Αυτή η εφαρμογή σκίζει," +
                    "γιατί δε τη σκέφτηκε κανείς νωρίτερα;;; ΧΡΟΝΙΑ ΠΟΛΛΑ!"
    };

    private static final String [] nameArray = {
            "Αριστείδης Μαρίνος",
            "Βάσω Σ.",
            "Παναγιώτης Παπαπαναγιώτου",
            "Μ.Ν.",
            "Γιάννης Χατζηχριστοδούλου"
    };

}
