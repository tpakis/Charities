package greek.dev.challenge.charities.model;

public class Charity {
    private int id;
    private String name;
    private String description;
    private int sms;
    private String smscost;
    private String smstext;
    private int telephone;
    private String telephonecost;
    private String iconlink;
    private String imageurls;


    public Charity(){

    }
    public Charity(int id, String name, String description, int sms, String smscost, String smstext, int telephone, String telephonecost, String iconlink, String imageurls) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sms = sms;
        this.smscost = smscost;
        this.smstext = smstext;
        this.telephone = telephone;
        this.telephonecost = telephonecost;
        this.iconlink = iconlink;
        this.imageurls = imageurls;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getSmscost() {
        return smscost;
    }

    public void setSmscost(String smscost) {
        this.smscost = smscost;
    }

    public String getSmstext() {
        return smstext;
    }

    public void setSmstext(String smstext) {
        this.smstext = smstext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getTelephonecost() {
        return telephonecost;
    }

    public void setTelephonecost(String telephonecost) {
        this.telephonecost = telephonecost;
    }

    public String getIconlink() {
        return iconlink;
    }

    public void setIconlink(String iconlink) {
        this.iconlink = iconlink;
    }

    public String getImageurls() {
        return imageurls;
    }

    public void setImageurls(String imageurls) {
        this.imageurls = imageurls;
    }

    @Override
    public String toString() {
        return "Charity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sms='" + sms + '\'' +
                ", smscost='" + smscost + '\'' +
                ", smstext='" + smstext + '\'' +
                ", telephone='" + telephone + '\'' +
                ", telephonecost='" + telephonecost + '\'' +
                ", iconlink='" + iconlink + '\'' +
                ", imageurls='" + imageurls + '\'' +
                '}';
    }
}
