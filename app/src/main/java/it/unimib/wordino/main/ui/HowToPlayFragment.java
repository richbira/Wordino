package it.unimib.wordino.main.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import it.unimib.wordino.R;
import it.unimib.wordino.databinding.FragmentHowToPlayBinding;
import it.unimib.wordino.databinding.FragmentRegistrationBinding;
import it.unimib.wordino.main.model.Result;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HowToPlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HowToPlayFragment extends Fragment {

    private FragmentHowToPlayBinding binding;



    public HowToPlayFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHowToPlayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.closeButton.setOnClickListener(v -> {

                        Navigation.findNavController(view).navigate(R.id.action_howToPlayFragment_to_gameActivity);

                });
    }
}