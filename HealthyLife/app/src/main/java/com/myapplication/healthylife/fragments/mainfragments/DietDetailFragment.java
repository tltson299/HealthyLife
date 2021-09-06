package com.myapplication.healthylife.fragments.mainfragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapplication.healthylife.R;
import com.myapplication.healthylife.databinding.FragmentDietDetailBinding;
import com.myapplication.healthylife.databinding.FragmentLaunchBinding;

import com.myapplication.healthylife.local.AppPrefs;
import com.myapplication.healthylife.local.DatabaseHelper;
import com.myapplication.healthylife.model.Diet;
import com.myapplication.healthylife.recycleviewadapters.DietRecViewAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class DietDetailFragment extends Fragment{
    private FragmentDietDetailBinding binding;
    private NavController navController;
    private Diet diet;
    private DatabaseHelper db;
    private int number = 0;
    private ArrayList<Diet> diets;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diet = (Diet) getArguments().getSerializable("DietData");
        db = new DatabaseHelper(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDietDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.DietDetailImage.setImageResource(diet.getImage());
        binding.DietDetailName.setText(diet.getName());
        binding.DietDetailDescription.setText(diet.getDescription());
        binding.DietDetailNote.setText(diet.getNote());
    }

    @Override
    public void onStart() {
        super.onStart();
        navController = Navigation.findNavController(getActivity(), R.id.fragmentContainer);

        binding.LoveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diets = db.getDietList();
                boolean alreadyExists = false;
                for(Diet i : diets){
                    if(i.isAssigned()){
                        alreadyExists = true;
                        break;
                    }
                    number++;
                }
                if(!alreadyExists) {
                    diet.setAssigned(true);
                    db.editAssignedDiet(diet);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PickDietData", (Serializable) diet);
                    navController.navigate(R.id.action_DietDetail_to_mainFragment, bundle);
                }
                else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Your current dietary recommendation will disappear. Do you want to continue?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    diet.setAssigned(true);
                                    diets.get(number).setAssigned(false);
                                    db.editAssignedDiet(diet);
                                    db.editAssignedDiet(diets.get(number));
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("PickDietData", (Serializable) diet);
                                    navController.navigate(R.id.action_DietDetail_to_mainFragment, bundle);
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
