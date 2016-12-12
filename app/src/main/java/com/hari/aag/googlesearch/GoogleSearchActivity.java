package com.hari.aag.googlesearch;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoogleSearchActivity extends AppCompatActivity
    implements View.OnClickListener{

    private static final String LOG_TAG = GoogleSearchActivity.class.getSimpleName();
    private static final String PREFS_NAME = GoogleSearchActivity.class.getSimpleName();

    private String searchStr;

    private static final String SEARCH_STRING = "searchString";

    @BindView(R.id.id_search_string) EditText searchStringET;
    @BindView(R.id.id_search_btn) Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_search);
        ButterKnife.bind(this);

        searchBtn.setOnClickListener(this);

        Log.d(LOG_TAG, "Inside - onCreate");
        readValuesFromPrefs();
        updateValueToUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Inside - onPause");
        saveValuesToPrefs();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_search_btn:
                String searchStr1 = searchStringET.getText().toString();
                if (searchStr1.isEmpty()){
                    Toast.makeText(this, "Search String is Empty!", Toast.LENGTH_SHORT).show();
                    Log.d(LOG_TAG, "Search String is Empty!");
                    break;
                }

                searchStr = searchStr1;
                saveValuesToPrefs();
                triggerSearch();
                break;
        }
    }

    private void updateValueToUI(){
        searchStringET.setText(searchStr);
    }

    private void readValuesFromPrefs(){
        SharedPreferences mySharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        searchStr = mySharedPrefs.getString(SEARCH_STRING, "");

        Log.d(LOG_TAG, "Values Read from Prefs.");
        dumpPrefValues();
    }

    private void saveValuesToPrefs(){
        SharedPreferences.Editor prefsEditor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

        prefsEditor.putString(SEARCH_STRING, searchStr);
        prefsEditor.commit();

        Log.d(LOG_TAG, "Values Saved to Prefs.");
        dumpPrefValues();
    }

    private void dumpPrefValues(){
        Log.d(LOG_TAG, SEARCH_STRING + " - " + searchStr);
    }

    private void triggerSearch(){
        Intent queryIntent = new Intent(Intent.ACTION_WEB_SEARCH);
        queryIntent.putExtra(SearchManager.QUERY, searchStr);
        startActivity(queryIntent);
    }
}
