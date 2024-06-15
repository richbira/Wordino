package it.unimib.wordino.main.ui;

import static it.unimib.wordino.main.util.Constants.PACKAGE_NAME;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unimib.wordino.R;
import it.unimib.wordino.databinding.FragmentSettingsBinding;
import it.unimib.wordino.databinding.FragmentSocialBinding;
import it.unimib.wordino.main.model.Highscore;
import it.unimib.wordino.main.model.UserStat;
import it.unimib.wordino.main.repository.IWordRepositoryLD;
import it.unimib.wordino.main.repository.user.IUserRepository;
import it.unimib.wordino.main.ui.welcome.UserViewModel;
import it.unimib.wordino.main.ui.welcome.UserViewModelFactory;
import it.unimib.wordino.main.util.ServiceLocator;

public class SocialFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = SocialFragment.class.getSimpleName();
    private FragmentSocialBinding binding;
    private ScoresViewModel highscoresModel;
    private Observer<List<Highscore>> highscoresObserver;
    private UserViewModel userViewModel;
    private String tokenId;
    private TextView gamePlayedText;
    private TextView currentStreakText;
    private TextView maxStreakText;
    private TextView winrateText;
    private HorizontalBarChart horizontalBarChart;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;



    public SocialFragment() {
        // Required empty public constructor
    }

    public static SocialFragment newInstance(String param1, String param2) {
        SocialFragment fragment = new SocialFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_social, container, false);

        tabLayout = view.findViewById(R.id.socialTabLayout);
        viewPager = view.findViewById(R.id.socialPager);

        viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);


        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("User statistics");
                        break;
                    case 1:
                        tab.setText("Local leaderboard");
                        break;
                }
            }
        }).attach();




        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateFragmentInViewPager();
    }

    private void updateFragmentInViewPager() {
        Log.d(TAG, "updateFragmentInViewPager");  //todo questo è il codice che dovrebbe far refreshare il sottofragment
        Fragment currentFragment = viewPagerAdapter.createFragment(viewPager.getCurrentItem());

        if (currentFragment instanceof SocialStatsTabFragment) {
            Log.d(TAG, "If passato");
            FragmentTransaction fragTransaction = getChildFragmentManager().beginTransaction();
            fragTransaction.detach(currentFragment);
            fragTransaction.attach(currentFragment);
            fragTransaction.commitNow();
        }
    }


}