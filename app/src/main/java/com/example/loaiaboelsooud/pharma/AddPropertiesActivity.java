package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddPropertiesActivity extends NavMenuInt implements HTTPRequests.GetPropertiesPostResult {

    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int REQUEST_GALLERY_IMAGE = 1234;
    private List<MultipartBody.Part> images;
    private ProgressBar progressBar;
    private PharmaConstants pharmaConstants;
    private Button postButton, galleryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_properties);
        intNavToolBar();
        progressBar = findViewById(R.id.properties_post_progress);
        pharmaConstants = new PharmaConstants(this);
        postButton = findViewById(R.id.properties_post_button);
        galleryButton = findViewById(R.id.properties_gallery_button);
    }

    public void addProperties(View view) {
        EditText name, region, address, area, price, description, notes, mobileNumbers, landLineNumbers;
        Spinner listedForSpinner, typeSpinner, citiesSpinner;
        PrefUtil prefUtil = new PrefUtil(this);
        List<String> mobileNumbersList, landLineNumbersList;
        mobileNumbersList = new ArrayList<String>();
        landLineNumbersList = new ArrayList<String>();
        name = findViewById(R.id.properties_name);
        citiesSpinner = findViewById(R.id.properties_city);
        region = findViewById(R.id.properties_region);
        address = findViewById(R.id.properties_address);
        area = findViewById(R.id.properties_area);
        listedForSpinner = findViewById(R.id.properties_listed_for);
        typeSpinner = findViewById(R.id.properties_type);
        price = findViewById(R.id.properties_price);
        description = findViewById(R.id.properties_description);
        notes = findViewById(R.id.properties_notes);
        mobileNumbers = findViewById(R.id.properties_mobile);
        landLineNumbers = findViewById(R.id.properties_land_number);
        final HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        if (region.getText().toString() != null && !region.getText().toString().isEmpty() &&
                name.getText().toString() != null && !name.getText().toString().isEmpty() &&
                address.getText().toString() != null && !address.getText().toString().isEmpty() &&
                mobileNumbers.getText().toString() != null && !mobileNumbers.getText().toString().isEmpty()) {
            mobileNumbersList.clear();
            mobileNumbersList.add(mobileNumbers.getText().toString());
            if (landLineNumbers.getText().toString() != null && !landLineNumbers.getText().toString().equals("")) {
                landLineNumbersList.clear();
                landLineNumbersList.add(landLineNumbers.getText().toString());
            }
            progressBar.setVisibility(View.VISIBLE);
            postButton.setEnabled(false);
            galleryButton.setEnabled(false);
            int paresedPrice = 1;
            if (!price.getText().toString().equals("")) {
                paresedPrice = Integer.parseInt(price.getText().toString());
            }

            httpRequests.sendPropertiesPostRequest(prefUtil.getToken(), name.getText().toString(), pharmaConstants.citiesMapAdd.get(citiesSpinner.getSelectedItem().toString()),
                    region.getText().toString(), address.getText().toString(), area.getText().toString(),
                    pharmaConstants.listedForMapAdd.get(listedForSpinner.getSelectedItem().toString()),
                    pharmaConstants.typeMapAdd.get(typeSpinner.getSelectedItem().toString()), paresedPrice, description.getText().toString(),
                    notes.getText().toString(), mobileNumbersList, landLineNumbersList, images, this);

        } else {
            Toast.makeText(this, getString(R.string.post_properties_fail),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void openPropertiesGalleryIntent(View view) throws IOException {
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
        for (int i = 0; i < bitmapList.size() || i < 8; i++) {
            File photoFile;
            Uri photoURI;
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            photoFile = File.createTempFile("file", ".jpg", storageDir);
            photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmapList.get(i).compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            try {
                FileOutputStream fos = new FileOutputStream(photoFile);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            RequestBody reqFile = RequestBody.create(MediaType.parse(getContentResolver().getType(photoURI)), photoFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("images[]", photoFile.getName(), reqFile);
            images.add(body);
        }
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
        finish();
        progressBar.setVisibility(View.GONE);
        postButton.setEnabled(true);
        galleryButton.setEnabled(true);
        Toast.makeText(this, getString(R.string.post_properties_success), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AddPropertiesActivity.this, ViewPropertiesActivity.class);
        intent.putExtra(PharmaConstants.ISFILTERED, false);
        startActivity(intent);
    }

    @Override
    public void failed() {
        progressBar.setVisibility(View.GONE);
        postButton.setEnabled(true);
        galleryButton.setEnabled(true);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

}
