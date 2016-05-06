package dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tranmanhhung.myplacearound.R;

import adapter.AutoCompleteAdapter;

/**
 * Created by TranManhHung on 14-Apr-16.
 */
public class DialogAutoText extends DialogFragment{

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteAdapter autoCompleteAdapter;
    ImageView imgClear;
    Button btnClose;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_autotext, container, false);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        btnClose = (Button) view.findViewById(R.id.btnCancel);
        imgClear =(ImageView)view.findViewById(R.id.imgClear);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        autoCompleteAdapter = new AutoCompleteAdapter(getContext(), R.layout.layout_item_auto_complete);
        autoCompleteTextView.setAdapter(autoCompleteAdapter);

        getDialog().setTitle("Search Place in 10 km");

        //set onClick when touch item in list
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemTouch = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), itemTouch, Toast.LENGTH_SHORT).show();
                hide_keyboard(getActivity());
                dismiss();

            }
        });


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.setText("");
            }
        });


        return view;
    }

    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void ShowKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.showSoftInput(autoCompleteTextView, 0);
    }
}
