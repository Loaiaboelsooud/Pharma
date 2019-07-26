package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    private static final String ISFILTERED = "isFiltered";
    private ImageView uploadedPic;
    private EditText name, city, region, address, area, price, description, notes, mobileNumbers, landLineNumbers;
    private Spinner listedForSpinner, typeSpinner;
    private HTTPRequests httpRequests;
    private PropertiesItem propertiesItem;
    private MultipartBody.Part imagePart;
    private File photoFile;
    private Uri photoURI;
    private ProgressBar progressBar;
    private List<String> mobileNumbersList, landLineNumbersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_properties);
        intNavToolBar();
        progressBar = findViewById(R.id.presecription_post_progress);
        propertiesItem = new PropertiesItem();
        mobileNumbersList = new ArrayList<String>();
        landLineNumbersList = new ArrayList<String>();
    }

    public void addProperties(View view) {
        PrefUtil prefUtil = new PrefUtil(this);
        name = findViewById(R.id.properties_name);
        city = findViewById(R.id.properties_city);
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
        if (city.getText().toString() != null && !city.getText().toString().isEmpty() &&
                region.getText().toString() != null && !region.getText().toString().isEmpty() &&
                name.getText().toString() != null && !name.getText().toString().isEmpty() &&
                address.getText().toString() != null && !address.getText().toString().isEmpty() &&
                mobileNumbers.getText().toString() != null && !mobileNumbers.getText().toString().isEmpty()) {
            mobileNumbersList.add(mobileNumbers.getText().toString());
            progressBar.setVisibility(View.VISIBLE);
            final HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
            });
            httpRequests.sendPropertiesPostRequest(prefUtil.getToken(), name.getText().toString(), city.getText().toString(), region.getText().toString()
                    , address.getText().toString(), area.getText().toString(), listedForSpinner.getSelectedItem().toString(), typeSpinner.getSelectedItem().toString()
                    , Integer.parseInt(price.getText().toString()), description.getText().toString(), notes.getText().toString(), mobileNumbersList,
                    landLineNumbersList, this);

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

    public void openCameraIntent(View view) throws IOException {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, createFile());
            startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap imageBitmap = null;
            if (requestCode == REQUEST_CAPTURE_IMAGE) {
                imageBitmap = (Bitmap) data.getExtras().get("data");
                //imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), URI);
                uploadedPic = findViewById(R.id.uploaded_pic);
                uploadedPic.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, 400, 400, false));

            } else if (requestCode == REQUEST_GALLERY_IMAGE) {
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                uploadedPic = findViewById(R.id.uploaded_pic);
                uploadedPic.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, 400, 400, false));
            }
            try {
                imagePart = imageToFile(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Uri createFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        photoFile = File.createTempFile("file", ".jpg", storageDir);
        photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
        return photoURI;
    }

    private MultipartBody.Part imageToFile(Bitmap bitmap) throws IOException {
        createFile();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
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
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", photoFile.getName(), reqFile);
        return body;
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(AddPropertiesActivity.this, ViewPropertiesActivity.class);
        intent.putExtra(ISFILTERED, false);
        startActivity(intent);
    }

    @Override
    public void success() {
        finish();
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.post_properties_success), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AddPropertiesActivity.this, ViewPropertiesActivity.class);
        intent.putExtra(ISFILTERED, false);
        startActivity(intent);
    }

    @Override
    public void failed() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

}
