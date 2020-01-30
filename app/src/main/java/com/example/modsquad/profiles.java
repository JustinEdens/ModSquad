package com.example.modsquad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

public class profiles extends Fragment {

    String year;
    String make;
    String model;

    car_profile profile;
    FragmentManager fm2;
    FragmentTransaction fragmentTransaction;

    public profiles(){

    }

    public profiles(String yr, String ma, String mo){
        year = yr;
        make = ma;
        model = mo;
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_profiles, null);

        TextView queryLabel = view.findViewById(R.id.queryLabel);
        queryLabel.setText(year+" "+make+" "+model);

        //adding profiles
        LinearLayout profileLayout = view.findViewById(R.id.profileLayout);

        profileLayout.removeAllViews();

        profile = new car_profile("2019","Toyota","86","Justin Edens","@justinedens","Bored","600cc","Light Weight","Aftermarket","Aftermarket","Aftermarket","3.0 Inch");
        car_profile p = new car_profile("2019","Toyota","86","Nicole Richardson","@glassofpayne","Stock","Stock","Stock","Stock","Stock","Stock","Stock");

        //add to pro list
        fm2 = getFragmentManager();
        fragmentTransaction = fm2.beginTransaction();
        //fragmentTransaction.add(profileLayout.getId(),profile);

        if(MainActivity.profiles != null) {
            queryLabel.setText("Results: "+MainActivity.profiles.size());
            for (car_profile pro : MainActivity.profiles) {
                fragmentTransaction.add(profileLayout.getId(),pro);
            }
        }
        else {
            fragmentTransaction.add(profileLayout.getId(),p);
        }

        fragmentTransaction.commit();

        /*profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fm2 = getFragmentManager();
                fragmentTransaction = fm2.beginTransaction();
                //fragmentTransaction.hide(fm2.getFragments().get(0));
                fragmentTransaction.replace(R.id.fragment_main, profile);
                fragmentTransaction.commit();


            }
        });*/

        return view;
    }
    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}

