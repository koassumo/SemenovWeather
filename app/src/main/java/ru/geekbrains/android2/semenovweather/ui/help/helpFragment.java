package ru.geekbrains.android2.semenovweather.ui.help;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;

import ru.geekbrains.android2.semenovweather.R;
import ru.geekbrains.android2.semenovweather.ui.selectoptions.SelectOptionsFragment;

public class helpFragment extends Fragment {

    private MaterialButton goBackMainActivityBtn;
    private MaterialButton goUrlBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goBackMainActivityBtn = view.findViewById(R.id.goBackHomeFragmentBtn);
        goUrlBtn = view.findViewById(R.id.goUrlButton);
        setOnGoOptionsFragmentBtnClick();
        setOnGoUrlBtnClick();
    }

    private void setOnGoOptionsFragmentBtnClick() {
        goBackMainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("arg", "data");
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_help_to_nav_options, bundle);

                // вариант для перехода без навигитора
//                SelectOptionsFragment selectOptionsFragment = new SelectOptionsFragment();
//                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                transaction.replace(R.id.nav_host_fragment_container, selectOptionsFragment);
//                transaction.commit();
            }
        });
    }

    private void setOnGoUrlBtnClick() {
        goUrlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String urlText = getString(R.string.yandex_link);
                Uri uri = Uri.parse(urlText);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browserIntent);
            }
        });
    }
}
