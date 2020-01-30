package com.example.modsquad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.modsquad.MainActivity.sendMessageToServer;

public class home extends Fragment {

    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home, null);

        imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(R.mipmap.title);

        Button add,search;
        add = view.findViewById(R.id.addBtn);
        search = view.findViewById(R.id.searchBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr = new add();
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm2.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_main, fr);
                fragmentTransaction.commit();
                Toast.makeText(getContext(), "Add", Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToServer("current values");
                Toast.makeText(getContext(), "Search", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
