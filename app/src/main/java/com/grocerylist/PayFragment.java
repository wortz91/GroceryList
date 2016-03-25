package com.grocerylist;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//This is going to become the activity to launch the AndroidPay/ SamsungPay
public class PayFragment extends Fragment {
    public PayFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.list_fragment_view_2, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final Button b = (Button) view.findViewById(R.id.payButton);


        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_MAIN);

                String title = getResources().getString(R.string.choose_title);

                PackageManager pm = getActivity().getPackageManager();
                Intent payIntent = pm.getLaunchIntentForPackage("com.google.android.apps.walletnfcrel");

                // Whitelist for apps that can be used
                List<String> payTerms = new ArrayList<String>();
                payTerms.add("com.samsung.android.spay");
                payTerms.add("com.google.android.apps.walletnfcrel");

                Intent chooser = CustomChooserIntent.create(pm, payIntent, "Pay Methods", payTerms);
                startActivity(chooser);
            }
        });

        // This auto runs the button click, but it runs it when the other fragment is the one in
        // view. This is not quite what I want. I only want this to run when this fragment view is
        // clicked.
//        b.post(new Runnable() {
//            @Override
//            public void run() {
//                b.performClick();
//            }
//        });
    }
}
