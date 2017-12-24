package greek.dev.challenge.charities.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Charity implements Parcelable {
    /**
     * unuque id of the charity institute
     */
    private int id;
    /**
     * name of the institute
     */
    private String name;
    /**
     * long text with a description of the institute
     */
    private String description;
    /**
     * sms phone number to which the app will send the messages
     * If sms number is not provided then it should contain 0
     */
    private String sms;
    /**
     * text containing info about the cost of the sms
     */
    private String smscost;
    /**
     * text that the sms should contain
     */
    private String smstext;
    /**
     * phone number to which the app will make calls
     */
    private String telephone;
    /**
     * text containing info about the cost of the phone call.
     * If phone number is not provided then it should contain 0.
     */
    private String telephonecost;
    /**
     * link to the firebase storage resource containing the logo of the institution
     */
    private String iconlink;
    /**
     * comma seperated list of links to the firebase storage containing photos of the institute
     */
    private String imageurls;

    private int DrawableIconPosition;

    public Charity(){

    }

    /**
     *
     * @param id unuque id of the charity isntitute
     * @param name name of the institute
     * @param description long text with a description of the institute
     * @param sms sms phone number to which the app will send the messages. 0 if not provided
     * @param smscost text containing info about the cost of the sms
     * @param smstext text that the sms should containg
     * @param telephone phone number to which the app will make calls. 0 if not provided
     * @param telephonecost text containing info about the cost of the phone call
     * @param iconlink link to the firebase storage resource containing the logo of the institution
     * @param imageurls comma seperated list of links to the firebase storage containing photos of the institute
     */
    public Charity(int id, String name, String description, String sms, String smscost, String smstext, String telephone, String telephonecost, String iconlink, String imageurls) {
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

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
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
    public int getDrawableIconPosition() {return DrawableIconPosition; }

    public void setDrawableIconPosition(Integer drawableIconPosition) {DrawableIconPosition = drawableIconPosition;}

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.sms);
        dest.writeString(this.smscost);
        dest.writeString(this.smstext);
        dest.writeString(this.telephone);
        dest.writeString(this.telephonecost);
        dest.writeString(this.iconlink);
        dest.writeString(this.imageurls);
        dest.writeInt(this.DrawableIconPosition);
    }

    protected Charity(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.sms = in.readString();
        this.smscost = in.readString();
        this.smstext = in.readString();
        this.telephone = in.readString();
        this.telephonecost = in.readString();
        this.iconlink = in.readString();
        this.imageurls = in.readString();
        this.DrawableIconPosition=in.readInt();
    }

    public static final Creator<Charity> CREATOR = new Creator<Charity>() {
        @Override
        public Charity createFromParcel(Parcel source) {
            return new Charity(source);
        }

        @Override
        public Charity[] newArray(int size) {
            return new Charity[size];
        }
    };
}
