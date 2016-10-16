package com.mobilecomputing.game.bluetooth;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mobilecomputing.game.R;

/**
 * Created by Xun Hu on 17/08/2016.
 */
public class ChatFragment extends Fragment {
    EditText etMessage;
    EditText etBox;
    Button button;
    String message = "";
    Communication communication;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container,false);
        etMessage = (EditText) view.findViewById(R.id.etSending);
        etBox = (EditText)view.findViewById(R.id.etMessageBox);
        button =(Button)view.findViewById(R.id.btnSending);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communication.sendData(etMessage.getText().toString());
                message+="Me: "+etMessage.getText().toString()+"\n";
                etBox.setText(message);
                etMessage.setText("");
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communication = (Communication) activity;

    }
    public void addText(String text){
        message+="other: "+text+"\n";
        etBox.setText(message);
    }
    public interface Communication{
        public void sendData(String data);
    }
}
