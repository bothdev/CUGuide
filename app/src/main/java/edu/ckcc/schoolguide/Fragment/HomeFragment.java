package edu.ckcc.schoolguide.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import edu.ckcc.schoolguide.R;

public class HomeFragment extends android.app.Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment, container, false);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}