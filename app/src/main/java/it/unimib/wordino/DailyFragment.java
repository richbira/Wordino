package it.unimib.wordino;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
/**
 * A simple {@link Fragment} subclass.
 * ù
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = DailyFragment.class.getSimpleName();
    public View activeBox;

    public DailyFragment() {
        // Required empty public constructor
    }

    public static DailyFragment newInstance(String param1, String param2) {
        DailyFragment fragment = new DailyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activeBox = view.findViewById(R.id.word_01);

        Button qButton = view.findViewById(R.id.key_q); qButton.setOnClickListener(this);
        Button wButton = view.findViewById(R.id.key_w); wButton.setOnClickListener(this);
        Button eButton = view.findViewById(R.id.key_e); eButton.setOnClickListener(this);
        Button rButton = view.findViewById(R.id.key_r); rButton.setOnClickListener(this);
        Button tButton = view.findViewById(R.id.key_t); tButton.setOnClickListener(this);
        Button yButton = view.findViewById(R.id.key_y); yButton.setOnClickListener(this);
        Button uButton = view.findViewById(R.id.key_u); uButton.setOnClickListener(this);
        Button iButton = view.findViewById(R.id.key_i); iButton.setOnClickListener(this);
        Button oButton = view.findViewById(R.id.key_o); oButton.setOnClickListener(this);
        Button pButton = view.findViewById(R.id.key_p); pButton.setOnClickListener(this);
        Button aButton = view.findViewById(R.id.key_a); aButton.setOnClickListener(this);
        Button sButton = view.findViewById(R.id.key_s); sButton.setOnClickListener(this);
        Button dButton = view.findViewById(R.id.key_d); dButton.setOnClickListener(this);
        Button fButton = view.findViewById(R.id.key_f); fButton.setOnClickListener(this);
        Button gButton = view.findViewById(R.id.key_g); gButton.setOnClickListener(this);
        Button hButton = view.findViewById(R.id.key_h); hButton.setOnClickListener(this);
        Button jButton = view.findViewById(R.id.key_j); jButton.setOnClickListener(this);
        Button kButton = view.findViewById(R.id.key_k); kButton.setOnClickListener(this);
        Button lButton = view.findViewById(R.id.key_l); lButton.setOnClickListener(this);
        Button zButton = view.findViewById(R.id.key_z); zButton.setOnClickListener(this);
        Button xButton = view.findViewById(R.id.key_x); xButton.setOnClickListener(this);
        Button cButton = view.findViewById(R.id.key_c); cButton.setOnClickListener(this);
        Button vButton = view.findViewById(R.id.key_v); vButton.setOnClickListener(this);
        Button bButton = view.findViewById(R.id.key_b); bButton.setOnClickListener(this);
        Button nButton = view.findViewById(R.id.key_n); nButton.setOnClickListener(this);
        Button mButton = view.findViewById(R.id.key_m); mButton.setOnClickListener(this);
        Button cancButton = view.findViewById(R.id.key_cancel); cancButton.setOnClickListener(this);
        Button enterButton = view.findViewById(R.id.key_enter); enterButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.key_q) updateActiveBox("Q");
        else if (id == R.id.key_w) updateActiveBox("W");
        else if (id == R.id.key_e) updateActiveBox("E");
        else if (id == R.id.key_r) updateActiveBox("R");
        else if (id == R.id.key_t) updateActiveBox("T");
        else if (id == R.id.key_y) updateActiveBox("Y");
        else if (id == R.id.key_u) updateActiveBox("U");
        else if (id == R.id.key_i) updateActiveBox("I");
        else if (id == R.id.key_o) updateActiveBox("O");
        else if (id == R.id.key_p) updateActiveBox("P");
        else if (id == R.id.key_a) updateActiveBox("A");
        else if (id == R.id.key_s) updateActiveBox("S");
        else if (id == R.id.key_d) updateActiveBox("D");
        else if (id == R.id.key_f) updateActiveBox("F");
        else if (id == R.id.key_g) updateActiveBox("G");
        else if (id == R.id.key_h) updateActiveBox("H");
        else if (id == R.id.key_j) updateActiveBox("J");
        else if (id == R.id.key_k) updateActiveBox("K");
        else if (id == R.id.key_l) updateActiveBox("L");
        else if (id == R.id.key_z) updateActiveBox("Z");
        else if (id == R.id.key_x) updateActiveBox("X");
        else if (id == R.id.key_c) updateActiveBox("C");
        else if (id == R.id.key_v) updateActiveBox("V");
        else if (id == R.id.key_b) updateActiveBox("B");
        else if (id == R.id.key_n) updateActiveBox("N");
        else if (id == R.id.key_m) updateActiveBox("M");
        else if (id == R.id.key_cancel) ; //TODO cancel key
        else if (id == R.id.key_enter) ; //TODO enter key
    }

    private void updateActiveBox(String text) {
        if (activeBox instanceof TextView) {
            ((TextView) activeBox).setText(text);
            nextWordBox();
        }
    }

    public void nextWordBox() {
        String fullName = getResources().getResourceName(activeBox.getId());
        String activeBoxName = fullName.substring(fullName.lastIndexOf("/") + 1);
        int nextLetterNum = Integer.parseInt(activeBoxName.substring(activeBoxName.length() - 1)) + 1;
        String nextActiveBoxName = activeBoxName.substring(0, activeBoxName.length() - 1) + nextLetterNum;
        int nextActiveBoxId = getResources().getIdentifier(nextActiveBoxName, "id", "it.unimib.wordino"); //TODO defpackage non fisso ma fare funzionare il getpackage o buildconfig
        if (nextActiveBoxId != 0) { // Check if the next box exist
            activeBox = getView().findViewById(nextActiveBoxId);
        } else {
            // Handle the case when there's no next box (maybe reset to the first box or do nothing)
            Log.d(TAG, "No next word box available.");
        }
    }


}


