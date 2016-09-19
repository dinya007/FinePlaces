package ru.fineplaces.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import ru.fineplaces.App;
import ru.fineplaces.R;
import ru.fineplaces.domain.PlaceDto;
import ru.fineplaces.service.PlaceService;
import ru.fineplaces.utils.ViewUtils;

public class PlaceListActivity extends AppCompatActivity {

    String[] mobileArray = {"Android", "IPhone", "WindowsMobile", "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X"};

    @Inject
    PlaceService placeService;
    ArrayAdapter<String> adapter;
    List<String> listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        App.getComponent().inject(this);

        ViewUtils.makeToastText(this, getString(R.string.toast_welcome));

        FloatingActionButton addPlace = (FloatingActionButton) findViewById(R.id.add_place);
        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        listItems = new ArrayList<>();
        listItems.add("");
        listItems.add("");
        adapter = new ArrayAdapter<>(this, R.layout.activity_place_list_item, listItems);
        ListView listView = (ListView) findViewById(R.id.place_list);
        listView.setAdapter(adapter);
        PlaceListLoader placeListLoader = new PlaceListLoader(placeService, this);
        placeListLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);
    }

    private class PlaceListLoader extends AsyncTask<Void, Void, List<PlaceDto>> {

        private final PlaceService placeService;
        private Activity activity;

        private PlaceListLoader(PlaceService placeService, Activity activity) {
            this.placeService = placeService;
            this.activity = activity;
        }

        @Override
        protected List<PlaceDto> doInBackground(Void... params) {
            return placeService.getAll();
        }

        @Override
        protected void onPostExecute(List<PlaceDto> placeDtoList) {
            if (placeDtoList != null && !placeDtoList.isEmpty()) {
                String[] placeNames = new String[placeDtoList.size()];
                ArrayList<String> placeNamesList = new ArrayList<>();
                for (int i = 0; i < placeDtoList.size(); i++) {
                    placeNames[i] = placeDtoList.get(i).getLocationName();
                    placeNamesList.add(placeDtoList.get(i).getLocationName());
                }
                adapter.addAll(placeNames);
            }
            ViewUtils.makeToastText(activity, getString(R.string.toast_now_yet_owner));
        }
    }


}
