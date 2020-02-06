package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddPropertiesActivity extends NavMenuInt implements HTTPRequests.GetPropertiesPostResult, HTTPRequests.DeleteProperty {

    private static final int REQUEST_GALLERY_IMAGE = 1234;
    private List<MultipartBody.Part> images;
    private ProgressBar progressBar;
    private PharmaConstants pharmaConstants;
    private Button postButton;
    private Spinner citySpinner, regionSpinner, typeSpinner, statusSpinner, listedForSpinner;
    private EditText propertyName;
    private TextView imagesCount, priceUnit, rentingHint;
    private LinearLayout galleryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_properties);
        progressBar = findViewById(R.id.properties_post_progress);
        pharmaConstants = new PharmaConstants(this);
        postButton = findViewById(R.id.properties_post_button);
        galleryLayout = findViewById(R.id.properties_gallery_layout);
        imagesCount = findViewById(R.id.properties_images_count);
        citySpinner = findViewById(R.id.properties_city);
        regionSpinner = findViewById(R.id.properties_region);
        typeSpinner = findViewById(R.id.properties_type);
        statusSpinner = findViewById(R.id.properties_status);
        listedForSpinner = findViewById(R.id.properties_listed_for);
        propertyName = findViewById(R.id.properties_name);
        priceUnit = findViewById(R.id.properties_price_unit);
        rentingHint = findViewById(R.id.properties_renting_text_hint);
        initCitySpinner();
        initListedForSpinner();
        initRegionSpinner();
        initStatusSpinner();
        initTypeSpinner();
        galleryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    openPropertiesGalleryIntent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initCitySpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                citySpinner,
                new HintAdapter<>(this, R.string.properties_city, Arrays.asList(getResources().getStringArray(R.array.cities_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        if (PharmaConstants.citesToRegionStringsMap.get(PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem()).toString()) != null) {
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddPropertiesActivity.this,
                                    PharmaConstants.citesToRegionStringsMap.get(PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem()).toString()), android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            regionSpinner.setAdapter(adapter);
                            regionSpinner.setEnabled(true);
                        } else {
                            regionSpinner.setAdapter(null);
                            regionSpinner.setEnabled(false);
                        }
                    }
                });
        hintSpinner.init();
    }

    private void initRegionSpinner() {
        regionSpinner.setEnabled(false);
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                regionSpinner,
                new HintAdapter<>(this, R.string.properties_region, Arrays.asList(getResources().getStringArray(R.array.regions_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                    }
                });
        hintSpinner.init();
    }

    private void initTypeSpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                typeSpinner,
                new HintAdapter<>(this, R.string.properties_type, Arrays.asList(getResources().getStringArray(R.array.properties_type_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        if (PharmaConstants.typeMapAdd.get(typeSpinner.getSelectedItem().toString()) != null) {
                            switch (PharmaConstants.typeMapAdd.get(typeSpinner.getSelectedItem().toString())) {
                                case PharmaConstants.PHARMACY:
                                    propertyName.setHint(getString(R.string.properties_pharmacy_name));
                                    break;
                                case PharmaConstants.HOSPITAL:
                                    propertyName.setHint(getString(R.string.properties_hospital_name));
                                    break;
                                case PharmaConstants.WAREHOUSE:
                                    propertyName.setHint(getString(R.string.properties_warehouse_name));
                                    break;
                                case PharmaConstants.FACTORY:
                                    propertyName.setHint(getString(R.string.properties_factory_name));
                                    break;
                            }

                        }
                    }
                });
        hintSpinner.init();
    }

    private void initStatusSpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                statusSpinner,
                new HintAdapter<>(this, R.string.properties_status, Arrays.asList(getResources().getStringArray(R.array.properties_status_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                    }
                });
        hintSpinner.init();
    }

    private void initListedForSpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                listedForSpinner,
                new HintAdapter<>(this, R.string.properties_listed_for, Arrays.asList(getResources().getStringArray(R.array.properties_listed_for_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {

                        switch (PharmaConstants.listedForMapAdd.get(listedForSpinner.getSelectedItem().toString())) {
                            case PharmaConstants.SELLING:
                                rentingHint.setVisibility(View.GONE);
                                priceUnit.setText(getResources().getString(R.string.LE));
                                break;
                            case PharmaConstants.RENTING:
                                rentingHint.setVisibility(View.VISIBLE);
                                priceUnit.setText(getResources().getString(R.string.LE_Month));
                                break;
                        }

                    }
                });
        hintSpinner.init();
    }

    public void addProperties(View view) {
        EditText name, address, area, price, description, mobileNumber, averageDailyIncome, mobileNumber2;
        PrefUtil prefUtil = new PrefUtil(this);
        List<String> mobileNumbersList;
        int parsedPrice = 1, parsedAverageDailyIncome = 1;
        mobileNumbersList = new ArrayList<String>();
        name = findViewById(R.id.properties_name);
        address = findViewById(R.id.properties_address);
        area = findViewById(R.id.properties_area);
        listedForSpinner = findViewById(R.id.properties_listed_for);
        statusSpinner = findViewById(R.id.properties_status);
        price = findViewById(R.id.properties_price);
        description = findViewById(R.id.properties_description);
        averageDailyIncome = findViewById(R.id.properties_average_daily_income);
        mobileNumber = findViewById(R.id.properties_mobile);
        mobileNumber2 = findViewById(R.id.properties_mobile2);

        final HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        if (name.getText().toString() != null && !name.getText().toString().isEmpty() &&
                address.getText().toString() != null && !address.getText().toString().isEmpty() &&
                mobileNumber.getText().toString() != null && !mobileNumber.getText().toString().isEmpty()
                && description.getText().toString() != null && !description.getText().toString().isEmpty()
                && regionSpinner.getSelectedItemId() < regionSpinner.getCount() && citySpinner.getSelectedItemId() < citySpinner.getCount()
                && statusSpinner.getSelectedItemId() < statusSpinner.getCount() && listedForSpinner.getSelectedItemId() < listedForSpinner.getCount()
                && typeSpinner.getSelectedItemId() < typeSpinner.getCount()) {
            mobileNumbersList.clear();
            progressBar.setVisibility(View.VISIBLE);
            mobileNumbersList.add(mobileNumber.getText().toString());
            if (mobileNumber2.getText().toString() != null && !mobileNumber2.getText().toString().isEmpty()) {
                mobileNumbersList.add(mobileNumber2.getText().toString());
            }
            if (!price.getText().toString().equals("")) {
                parsedPrice = Integer.parseInt(price.getText().toString());
            }
            if (!averageDailyIncome.getText().toString().equals("")) {
                parsedAverageDailyIncome = Integer.parseInt(averageDailyIncome.getText().toString());
            }
            if (area.getText().toString() == null || area.getText().toString().isEmpty() || area.getText().toString() == "") {
                area.setText("0");
            }
            if (address.getText().toString().length() < 11) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, getString(R.string.address_properties_fail),
                        Toast.LENGTH_LONG).show();
            } else if (mobileNumber.getText().toString().length() < 11) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, getString(R.string.mobile_properties_fail),
                        Toast.LENGTH_LONG).show();
            } else if (name.getText().toString().length() < 4) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, getString(R.string.name_properties_fail),
                        Toast.LENGTH_LONG).show();
            } else {
                postButton.setEnabled(false);
                galleryLayout.setEnabled(false);
                httpRequests.sendPropertiesPostRequest(prefUtil.getToken(), name.getText().toString(), pharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem().toString()),
                        PharmaConstants.regionsMapAdd.get(regionSpinner.getSelectedItem().toString()), address.getText().toString(), area.getText().toString(),
                        pharmaConstants.listedForMapAdd.get(listedForSpinner.getSelectedItem().toString()),
                        pharmaConstants.typeMapAdd.get(typeSpinner.getSelectedItem().toString()), parsedPrice, description.getText().toString(),
                        parsedAverageDailyIncome, mobileNumbersList, pharmaConstants.statusMapAdd.get(statusSpinner.getSelectedItem().toString()), images, this, this);
            }
        } else {
            Toast.makeText(this, getString(R.string.post_properties_fail),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void openPropertiesGalleryIntent() throws IOException {
        Intent pictureIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pictureIntent.setType("image/*");
        pictureIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        pictureIntent.setAction(Intent.ACTION_GET_CONTENT);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, createFile());
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(pictureIntent, "Select Picture"), REQUEST_GALLERY_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_GALLERY_IMAGE) {
            List<Bitmap> imageBitmap = new ArrayList<>();

            try {
                if (data.getClipData() != null) {
                    for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                        imageBitmap.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getClipData().getItemAt(i).getUri()));
                    }
                } else {
                    imageBitmap.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
                }
                images = imageToFile(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private List<MultipartBody.Part> imageToFile(List<Bitmap> bitmapList) throws IOException {
        List<MultipartBody.Part> images = new ArrayList<>();
        int size = bitmapList.size() >= 8 ? 8 : bitmapList.size();
        for (int i = 0; i < size; i++) {
            File photoFile;
            Uri photoURI;
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            photoFile = File.createTempFile("file", ".jpg", storageDir);
            photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmapList.get(i).compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);

            byte[] bitmapdata = bos.toByteArray();
            try {
                FileOutputStream fos = new FileOutputStream(photoFile);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageCompression imageCompression = new ImageCompression(this);
            File compressedImageFile = new File(imageCompression.compressImage(photoFile.getAbsolutePath()));
            RequestBody reqFile = RequestBody.create(MediaType.parse(getContentResolver().getType(photoURI)), compressedImageFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("images[]", compressedImageFile.getName(), reqFile);
            images.add(body);
        }

        imagesCount.setText(getResources().getQuantityString(R.plurals.images_count, size, size));
        return images;
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(AddPropertiesActivity.this, ViewPropertiesActivity.class);
        intent.putExtra(PharmaConstants.ISFILTERED, false);
        startActivity(intent);
    }

    @Override
    public void success() {
        progressBar.setVisibility(View.GONE);
        finish();
        Intent intent = new Intent(AddPropertiesActivity.this, ViewPropertiesActivity.class);
        intent.putExtra(PharmaConstants.ISFILTERED, false);
        startActivity(intent);
        Toast.makeText(this, getString(R.string.post_properties_success), Toast.LENGTH_LONG).show();
    }

    @Override
    public void failed() {
        progressBar.setVisibility(View.GONE);
        postButton.setEnabled(true);
        galleryLayout.setEnabled(true);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

}
