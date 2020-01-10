package com.example.plant.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.plant.R;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private List<HomeItem> list;
    private HomeAdapter homeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button knowledge1Button = getActivity().findViewById(R.id.button_knowledge1);
        knowledge1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Knowledge1Activity.class);
                intent.putExtra("class", 1);
                startActivity(intent);
            }
        });
        Button knowledge2Button = getActivity().findViewById(R.id.button_knowledge2);
        knowledge2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Knowledge1Activity.class);
                intent.putExtra("class", 2);
                startActivity(intent);
            }
        });
        Button knowledge3Button = getActivity().findViewById(R.id.button_knowledge3);
        knowledge3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Knowledge1Activity.class);
                intent.putExtra("class", 3);
                startActivity(intent);
            }
        });
        Button knowledge4Button = getActivity().findViewById(R.id.button_knowledge4);
        knowledge4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Knowledge1Activity.class);
                intent.putExtra("class", 4);
                startActivity(intent);
            }
        });
    }

}