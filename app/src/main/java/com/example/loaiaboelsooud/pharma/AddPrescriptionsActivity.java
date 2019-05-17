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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddPrescriptionsActivity extends NavMenuInt {
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int REQUEST_GALLERY_IMAGE = 1234;
    private ImageView uploadedPic;
    private EditText description;
    private HTTPRequests httpRequests;
    private PrescriptionsItem prescriptionsItem;
    private MultipartBody.Part imagePart;
    private File photoFile;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescriptions);
        intNavToolBar();
        prescriptionsItem = new PrescriptionsItem();
    }

    public void addPrescriptions(View view) {
        PrefUtil prefUtil = new PrefUtil(this);
        description = findViewById(R.id.presecription_description);
        prescriptionsItem.setDescription(description.getText().toString());
        RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, description.getText().toString());
        if (prescriptionsItem.getDescription() != null && photoFile != null) {
            final HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
            });
            finish();
            httpRequests.sendPrescriptionsPostRequest(imagePart, descriptionPart, prefUtil.getToken());
            Intent intent = new Intent(AddPrescriptionsActivity.this, ViewPrescriptionsActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(AddPrescriptionsActivity.this, "Please choose an image and write a description", Toast.LENGTH_LONG);
        }
    }

    public void openGalleryIntent(View view) throws IOException {
        Intent pictureIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pictureIntent.setType("image/*");
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

    }

}