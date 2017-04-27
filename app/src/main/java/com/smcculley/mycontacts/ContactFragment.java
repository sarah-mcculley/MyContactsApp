package com.smcculley.mycontacts;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * Created by smcculley on 2/9/2017.
 */

public class ContactFragment extends Fragment {
    private static final String ARG_CONTACT_ID = "contact_id";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Contact mContact;
    private EditText mNameField;
    private EditText mEmailField;
    private FavoriteView mFavoriteView;
    private EditText mAddressField;
    private MapView mMapView;
    private ImageView mImageView;
    private Callbacks mCallbacks;

    public interface Callbacks {
        void onContactUpdated();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public static ContactFragment newInstance(UUID contactID) {
        ContactFragment contactFragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT_ID, contactID);
        contactFragment.setArguments(args);
        return contactFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID contactID = (UUID) getArguments().getSerializable(ARG_CONTACT_ID);

        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contact, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menu_item_send_email:
                if (mContact.getEmail() == null){
                    return true;
                }
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                String[] addresses = {mContact.getEmail()};
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);

                //check for email app
                ComponentName emailApp = intent.resolveActivity(getContext().getPackageManager());
                ComponentName unsupportedAction = ComponentName.unflattenFromString("com.android.fallback/.Fallback");
                if (emailApp != null && !emailApp.equals(unsupportedAction)){
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(), R. string.email_app_error, Toast.LENGTH_SHORT).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        mFavoriteView = (FavoriteView) view.findViewById(R.id.contact_favorite);
        mFavoriteView.setSelected(mContact.isFavorite());
        mFavoriteView.setOnSelectedChangedListener(new FavoriteView.OnSelectedChangedListener() {
            @Override
            public void onSelectedChanged(boolean selected) {
                mContact.setFavorite(selected);
            }
        });

        mNameField = (EditText) view.findViewById(R.id.contact_name);
        mNameField.setText(mContact.getName());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContact.setName(s.toString());
                AddressBook.get(getContext()).updateContact(mContact);
                mCallbacks.onContactUpdated();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmailField = (EditText) view.findViewById(R.id.contact_email);
        mEmailField.setText(mContact.getEmail());
        mEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContact.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFavoriteView = (FavoriteView) view.findViewById(R.id.contact_favorite);
        mFavoriteView.setSelected(mContact.isFavorite());
        mFavoriteView.setOnSelectedChangedListener(new FavoriteView.OnSelectedChangedListener() {
            @Override
            public void onSelectedChanged(boolean selected) {
                mContact.setFavorite(selected);
            }
        });

        mAddressField = (EditText) view.findViewById(R.id.contact_address);
        mAddressField.setText(mContact.getAddress());
        mAddressField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContact.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mAddressField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    updateMap();
                }
            }
        });

        mImageView = (ImageView)view.findViewById(R.id.contact_image);
        if (mContact.getImage() != null) {
            mImageView.setImageBitmap(mContact.getImage());
        }
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });






        mMapView = (MapView) view.findViewById(R.id.contact_map);
        mMapView.onCreate(savedInstanceState);
        updateMap();

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mContact.setImage(imageBitmap);
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    private void updateMap() {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Geocoder geo = new Geocoder(getContext());
                try {
                    List<Address> addresses =
                            geo.getFromLocationName(mAddressField.getText().toString(), 1);
                    if (addresses.size() > 0) {
                        LatLng latLng = new LatLng(addresses.get(0).getLatitude(),
                                addresses.get(0).getLongitude());
                        MarkerOptions marker = new MarkerOptions().position(latLng);
                        googleMap.addMarker(marker);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    }
                } catch (IOException e) {

                }
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        AddressBook.get(getContext()).updateContact(mContact);
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

}
