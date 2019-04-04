package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
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
        if (prescriptionsItem.getDescription() != null) {
            final HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
                @Override
                public void notifySuccess(String response) {
                    Log.e("responce", response);
                }

                @Override
                public void notifyError(Exception error) {

                }

            });


            httpRequests.sendPrescriptionsPostRequest(imagePart, descriptionPart, prefUtil.getToken());
            Intent intent = new Intent(AddPrescriptionsActivity.this, ViewPrescriptionsActivity.class);
            startActivity(intent);
        }
    }

    public void openGalleryIntent(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_IMAGE);
    }

    public void openCameraIntent(View view) {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
        );
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent,
                    REQUEST_CAPTURE_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap imageBitmap = null;
            if (requestCode == REQUEST_CAPTURE_IMAGE) {
                if (data != null && data.getExtras() != null) {
                    imageBitmap = (Bitmap) data.getExtras().get("data");
                    uploadedPic = findViewById(R.id.uploaded_pic);
                    uploadedPic.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, 400, 400, false));
                }
            } else if (requestCode == REQUEST_GALLERY_IMAGE) {
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    uploadedPic = findViewById(R.id.uploaded_pic);
                    uploadedPic.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, 400, 400, false));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            prescriptionsItem.setImage(imageBitmap);
            try {
                imagePart = castImage(data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private MultipartBody.Part castImage(Uri image) throws IOException {

        File orginalFile = new File(image.getPath());

        RequestBody reqFile = RequestBody.create(MediaType.parse(getContentResolver().getType(image)), orginalFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", orginalFile.getName(), reqFile);

        return body;


    }
}