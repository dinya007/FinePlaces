package ru.fineplaces.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import javax.inject.Inject;

import ru.fineplaces.App;
import ru.fineplaces.R;
import ru.fineplaces.domain.Coordinates;
import ru.fineplaces.domain.PlaceDto;
import ru.fineplaces.enums.IntentExtras;
import ru.fineplaces.service.PlaceService;
import ru.fineplaces.state.PlaceMap;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DetailProfileActivity extends AppCompatActivity {

    @Inject
    PlaceService placeService;
    @Inject
    PlaceMap placeMap;

    private PlaceDto place = null;
    private EditText locationName;
    private EditText description;
    private EditText sale;
    private FloatingActionButton saveButton;
    private FloatingActionButton editButton;
    private FloatingActionButton cancelButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        App.getComponent().inject(this);

        init();
    }

    private void init() {
        place = placeMap.getByCoordinates(new Gson().fromJson(getIntent().getStringExtra(IntentExtras.PLACE_COORDINATES.name()), Coordinates.class));

        this.setTitle(place.getLocationName());

        locationName = (EditText) findViewById(R.id.detail_profile_location_name);
        description = (EditText) findViewById(R.id.detail_profile_description);
        sale = (EditText) findViewById(R.id.detail_profile_sale);

        locationName.setEnabled(false);
        description.setEnabled(false);
        sale.setEnabled(false);

        locationName.setText(place.getLocationName());
        description.setText(place.getDescription());
        sale.setText(String.valueOf(place.getSale()));

        editButton = (FloatingActionButton) findViewById(R.id.detailProfileEditButton);
        saveButton = (FloatingActionButton) findViewById(R.id.detailProfileSaveButton);
        cancelButton = (FloatingActionButton) findViewById(R.id.detailProfileCancelButton);


        saveButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationName != null) locationName.setEnabled(true);
                if (description != null) description.setEnabled(true);
                if (sale != null) sale.setEnabled(true);
                saveButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.INVISIBLE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationName != null) locationName.setEnabled(false);
                if (description != null) description.setEnabled(false);
                if (sale != null) sale.setEnabled(false);
                saveButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
                editButton.setVisibility(View.VISIBLE);

                place.setLocationName(locationName.getText().toString());
                place.setDescription(description.getText().toString());
                place.setSale(Integer.parseInt(sale.getText().toString()));

                Observable.create(new Observable.OnSubscribe<Void>() {
                    @Override

                    public void call(Subscriber<? super Void> subscriber) {
                        placeService.save(place);
                        placeMap.put(place.getCoordinates(), place);

                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(new Subscriber<Void>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(Void aVoid) {

                            }
                        });
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationName != null) locationName.setEnabled(false);
                if (description != null) description.setEnabled(false);
                if (sale != null) sale.setEnabled(false);

                saveButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
                editButton.setVisibility(View.VISIBLE);

                locationName.setText(place.getLocationName());
                description.setText(place.getDescription());
                sale.setText(String.valueOf(place.getSale()));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
            return super.onKeyDown(keyCode, event);

        }
        return super.onKeyDown(keyCode, event);

    }
}
