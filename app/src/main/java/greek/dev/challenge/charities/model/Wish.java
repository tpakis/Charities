package greek.dev.challenge.charities.model;

import java.util.ArrayList;

/**
 * Created by nalex on 26/12/2017.
 */

public class Wish {

    private String wish;
    private String author;

    public Wish(String wish, String author) {
        this.author = author;
        this.wish = wish;
    }

    public String getWish() {
        return wish;
    }

    public String getAuthor() {
        return author;
    }

    public static ArrayList<Wish> createWishList (int number) {
        ArrayList wishList = new ArrayList(number);
        for (int i = 0; i < number; i ++) {
            Wish fakeWish = new Wish("WishWishWishWishWish" + i, "Author" + i);
            wishList.add(i, fakeWish );
        }
        return wishList;



    }

}
