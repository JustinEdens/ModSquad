package com.example.modsquad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class add extends Fragment {

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_add, null);

        Button choose = view.findViewById(R.id.choosePic);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent,"Pick an image"),1);

            }
        });

        final EditText year,make,model,name,insta;
        year = view.findViewById(R.id.year);
        make = view.findViewById(R.id.make);
        model = view.findViewById(R.id.model);
        name = view.findViewById(R.id.dName);
        insta = view.findViewById(R.id.insta);

        final Spinner throttle = view.findViewById(R.id.throttle);
        final Spinner injectors = view.findViewById(R.id.injectors);
        final Spinner pulley = view.findViewById(R.id.pulley);
        final Spinner diameter = view.findViewById(R.id.diameter);

        final CheckBox headers,frontpipe,cat;
        headers = view.findViewById(R.id.checkBoxHeaders);
        frontpipe = view.findViewById(R.id.checkBoxFrontPipe);
        cat = view.findViewById(R.id.checkBoxCat);

        Button submit = view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String header = "Stock";
                String pipe = "Stock";
                String catBack = "Stock";

                if(headers.isChecked())
                    header = "Aftermarket";
                if(frontpipe.isChecked())
                    pipe = "Aftermarket";
                if(cat.isChecked())
                    catBack = "Aftermarket";

                String profileString = "<";
                profileString = profileString +
                              year.getText().toString() +
                        "!" + make.getText().toString() +
                        "?" + model.getText().toString() +
                        "#" + name.getText().toString() +
                        "$" + insta.getText().toString() +
                        "%" + throttle.getSelectedItem().toString() +
                        "^" + injectors.getSelectedItem().toString() +
                        "&" + pulley.getSelectedItem().toString() +
                        "*" + header +
                        "(" + pipe +
                        ")" + catBack +
                        "+" + diameter.getSelectedItem().toString() +
                        ">";

                MainActivity.sendMessageToServer(profileString);

            }
        });

        return view;
    }
    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){

        if (resultCode == RESULT_OK && requestCode == 1){

            ImageView imageView = view.findViewById(R.id.imageView2);

            try{
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                imageView.setImageBitmap(bitmap);
                imageView.setRotation(90);

            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

}
