package greek.dev.challenge.charities.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Class created to be able to handle calls and messaging.
 * Needs to be instantiated with a context before being used.
 * Should be called after call button is pressed or sms button is pressed.
 * No permissions are needed since actions are sent directly to respective services.
 *
 * Ex.
 * public void myMethod(View view) {
        CallAndSms callAndSms = new CallAndSms(this);
        callAndSms.call("2101234567");
        callAndSms.sms("6941234567", "I love donating");
    }
 */
public class CallAndSms {

    private Context context;

    /**
     * Constructor for the class, needed to instantiate context.
     * @param context the context of the app calling the class.
     *                ex. MainActivity.class or this
     */
    public CallAndSms(Context context) {
        this.context = context;
    }

    /**
     * Method for making a phone call to charity.
     * @param telephone the number which will be called
     *                  ex. Charity.getTelephone()
     */
    public void call(String telephone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + telephone));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Method for sending an sms message to charity.
     * @param smsNumber the number to which the sms will be sent
     * @param message the message which will be sent.
     */
    public void sms(String smsNumber , String message){

        String smsMessage = " ";
        if(!message.isEmpty()){
            smsMessage = message;
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + smsNumber));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", smsMessage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }

    }

}
