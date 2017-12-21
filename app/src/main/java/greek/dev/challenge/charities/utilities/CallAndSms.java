package greek.dev.challenge.charities.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Helper class created to be able to handle calls and messaging.
 * Should be used after call button is pressed or sms button is pressed.
 * Methods return an intent which you can then use ie through startActivity()
 * No permissions are needed since actions are sent directly to respective services.
 *
 * Ex.
 * public void myMethod() {
     Intent callIntent = CallAndSms.call(this, "6981234567");
     Intent smsIntent = CallAndSms.sms(this, "6981234567", "Best of luck ");
     startActivity(callIntent);
     startActivity(smsIntent);
 }
 */
public final class CallAndSms {


    /**
     * Method for making a phone call to charity
     * @param context The context of the app that's asking for the method.
     *                Usually this or MainActivity.class
     * @param telephone the number which will be called
     *                  ex. Charity.getTelephone()
     * @return Intent that will take the user to the phone app.
     *         ex. startActivity(CallAndSms.call(this, Charity.getTelephone() );
     */
    public static Intent call(Context context, String telephone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + telephone));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            return intent;
        }
        return null;
    }

    /**
     * Method for sending an sms message to charity.
     * @return Intent that will take the user to the sms app.
     * @param context The context of the app that's asking for the method.
     *                Usually this or MainActivity.class
     * @param smsNumber the number to which the sms will be sent
     *                  ex. Charity.getSms()
     * @param message the message which will be sent.
     *
     */
    public static Intent sms(Context context, String smsNumber , String message){

        String smsMessage = " ";
        if(!message.isEmpty()){
            smsMessage = message;
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + smsNumber));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", smsMessage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            return intent;
        }

        return null;

    }

}
