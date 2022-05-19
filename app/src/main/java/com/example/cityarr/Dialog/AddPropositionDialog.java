package com.example.cityarr.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cityarr.DAO.DaoProposition;
import com.example.cityarr.R;
import com.example.cityarr.entity.Proposition;
import com.google.android.material.textfield.TextInputLayout;

public class AddPropositionDialog extends Dialog {

   public static AddPropositionDialog instance ;
   public static Uri image;

    public Context context;
    private EditText titleET ;
    private TextInputLayout titleTIL;
    private ImageView propositionPic;
    private Button addBTN;

    private AddPropositionCallBack propositionCallBack;
    private AddPropositionCallBack imageCallBack ;

    public AddPropositionDialog(Context context, AddPropositionCallBack propositionCallBack,AddPropositionCallBack imageCallBack) {
        super(context);
        this.context = context;
        this.propositionCallBack = propositionCallBack;
        this.imageCallBack = imageCallBack ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.proposition_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        setCanceledOnTouchOutside(true);
        getWindow().getAttributes().height =
                (int) (getDeviceMetrics(context).heightPixels * 0.8);


        initView();
        initEvent();

    }

    private void initView() {

        titleTIL = findViewById(R.id.titleTIL);
        titleET = findViewById(R.id.titleET);

        propositionPic = findViewById(R.id.propositionImage);

        addBTN = findViewById(R.id.addBTN);

    }

    private void initEvent() {

        titleET.addTextChangedListener(new MyTextWatcher(titleET));

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        propositionPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseImage();
            }
        });
    }

    private void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);

        imageCallBack.onImageAdded(intent,propositionPic);
    }

    public static DisplayMetrics getDeviceMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.titleET:
                    validateInput(titleET, titleTIL, context.getString(R.string.error_message_first_name));
                    break;
            }
        }
    }

    private boolean validateInput(EditText editText, TextInputLayout textInputLayout, String errorMessage) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError(context.getString(R.string.error_message_first_name));
            requestFocus(editText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateInput(titleET, titleTIL, context.getString(R.string.error_message_first_name))
        ) {
            return;
        }

        if (image != null) {

            DaoProposition dao = new DaoProposition();
            DaoProposition.currentTitle = (titleET.getText().toString().trim());

            dao.upload();

               if(DaoProposition.finalPorp!=null){
                    propositionCallBack.onPropositionAdded(DaoProposition.finalPorp);
                }


        }else{
            Toast.makeText(getContext(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }


    }




    public interface AddPropositionCallBack
    {
        void onImageAdded(Intent intent , ImageView propositionPic);

        void onPropositionAdded(Proposition proposition);
    }


}
