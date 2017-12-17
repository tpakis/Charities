package greek.dev.challenge.charities;

/**
 * Created by Nasia LiAD on 12/18/2017.
 */


// This class is just a mock one. Used at recycler view's adapter.

public class SampleData {
    private String name;
    private String info;
    private int logo;
    private int stars;

    public SampleData(String name, String info, int logo, int stars){
        this.name = name;
        this.info = info;
        this.logo = logo;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
