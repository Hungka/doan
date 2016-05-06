package fragment;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.tranmanhhung.myplacearound.R;

/**
 * Created by TranManhHung on 31-Mar-16.
 */
public class ShowDialog {
    Dialog dialog;
    public ShowDialog(Context context)
    {
        dialog = new Dialog (context);
        dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_details);
        dialog.getWindow ().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void ShowDialog(boolean b)
    {
        if(b)
        dialog.show();
        else dialog.dismiss();
    }

}
