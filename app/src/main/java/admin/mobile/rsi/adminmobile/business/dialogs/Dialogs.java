package admin.mobile.rsi.adminmobile.business.dialogs;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Dialogs {

    public static void showErrorDialog(Context context, String title, String message, DialogInterface.OnCancelListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert);
        if (listener != null) {
            dialogBuilder.setOnCancelListener(listener);
        }
        dialogBuilder.show();
    }

    public static void showInfoDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public static AlertDialog showWaitDialog(Context context){
        return new AlertDialog.Builder(context)
                .setTitle("Proszę czekać")
                .setMessage("Proszę czekać na wykonanie operacji")
                .setIcon(android.R.drawable.ic_dialog_info)
                .create();
    }

}
