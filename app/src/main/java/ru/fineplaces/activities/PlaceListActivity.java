package ru.fineplaces.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.fineplaces.App;
import ru.fineplaces.R;
import ru.fineplaces.domain.Coordinates;
import ru.fineplaces.domain.PlaceDto;
import ru.fineplaces.domain.PlaceKey;
import ru.fineplaces.enums.IntentExtras;
import ru.fineplaces.service.AuthenticationService;
import ru.fineplaces.service.PlaceService;
import ru.fineplaces.state.PlaceMap;
import ru.fineplaces.utils.ViewUtils;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.observers.Subscribers;
import rx.schedulers.Schedulers;

public class PlaceListActivity extends AppCompatActivity {

    @Inject
    PlaceMap placeMap;

    @Inject
    PlaceService placeService;
    @Inject
    AuthenticationService authenticationService;

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
                ViewUtils.makeSnackbarText(view, "Replace with your own action");
            }
        });


        listItems = new ArrayList<>();
        listItems.add("");
        listItems.add("");
        placeMap.put(new PlaceKey(new Coordinates(Double.MIN_VALUE, Double.MIN_VALUE), 0), null);
        placeMap.put(new PlaceKey(new Coordinates(Double.MIN_VALUE + 1, Double.MIN_VALUE + 1), 1), null);

        adapter = new ArrayAdapter<>(this, R.layout.activity_place_list_item, listItems);
        ListView listView = (ListView) findViewById(R.id.place_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new PlaceClickListener(this));

        PlaceListLoader placeListLoader = new PlaceListLoader(placeService, this);
        placeListLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
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
                ArrayList<String> placeNamesList = new ArrayList<>();
                for (int i = 0; i < placeDtoList.size(); i++) {
                    PlaceDto placeDto = placeDtoList.get(i);
                    placeNamesList.add(placeDto.getLocationName());
                    placeMap.put(new PlaceKey(placeDto.getCoordinates(), i + 2), placeDto);
                }
                adapter.addAll(placeNamesList);

            }
            ViewUtils.makeToastText(activity, getString(R.string.toast_now_yet_owner));
        }
    }


    private class PlaceClickListener implements AdapterView.OnItemClickListener {

        private final PlaceListActivity activity;

        private PlaceClickListener(PlaceListActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(activity, DetailProfileActivity.class);
            intent.putExtra(IntentExtras.PLACE_COORDINATES.name(), new Gson().toJson(placeMap.getByPosition(position).getCoordinates()));
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Observable.create(new Observable.OnSubscribe<Void>() {
                @Override
                public void call(Subscriber<? super Void> subscriber) {
                    authenticationService.logout();
                }
            }).subscribeOn(Schedulers.io()).subscribe();
            return super.onKeyDown(keyCode, event);

        }
        return super.onKeyDown(keyCode, event);

    }

}
