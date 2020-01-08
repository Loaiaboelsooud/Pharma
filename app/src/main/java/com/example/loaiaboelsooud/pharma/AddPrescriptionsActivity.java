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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddPrescriptionsActivity extends NavMenuInt implements HTTPRequests.GetPrescriptionPostResult {
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int REQUEST_GALLERY_IMAGE = 1234;
    private ImageView uploadedPic, userAvatar;
    private EditText description;
    private PrescriptionsItem prescriptionsItem;
    private MultipartBody.Part imagePart;
    private ProgressBar progressBar;
    private TextView userName;
    private Button postButton, galleryButton, cameraButton;
    private File photoFile;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescriptions);
        PrefUtil prefUtil = new PrefUtil(this);
        userAvatar = findViewById(R.id.prescription_user_profile_picture);
        userName = findViewById(R.id.prescription_user_name);
        progressBar = findViewById(R.id.presecription_post_progress);
        galleryButton = findViewById(R.id.presecription_gallery_button);
        cameraButton = findViewById(R.id.presecription_camera_button);
        postButton = findViewById(R.id.presecription_post_button);
        prescriptionsItem = new PrescriptionsItem();
        User loggedInUser = prefUtil.getFacebookUserInfo();
        String imageUrl = loggedInUser.getAvatar() + "picture?width=250&height=250";
        userName.setText(loggedInUser.getName());
        Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_loading).dontAnimate().into(userAvatar);
    }

    public void addPrescriptions(View view) {
        postButton.setEnabled(false);
        galleryButton.setEnabled(false);
        cameraButton.setEnabled(false);
        PrefUtil prefUtil = new PrefUtil(this);
        description = findViewById(R.id.presecription_description);
        prescriptionsItem.setDescription(description.getText().toString());
        RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, description.getText().toString());
        if (prescriptionsItem.getDescription() != null && !prescriptionsItem.getDescription().isEmpty() && imagePart != null) {
            progressBar.setVisibility(View.VISIBLE);
            final HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
            });
            httpRequests.sendPrescriptionsPostRequest(imagePart, descriptionPart, this, prefUtil.getToken());

        } else {
            Toast.makeText(this, getString(R.string.post_prescriptions_fail),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void openGalleryIntent(View view) throws IOException {
        Intent pictureIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pictureIntent.setType("image/*");
        pictureIntent.setAction(Intent.ACTION_GET_CONTENT);
        // pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, createFile());
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(pictureIntent, "Select Picture"), REQUEST_GALLERY_IMAGE);
        }
    }

    public void openCameraIntent(View view) throws IOException {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, createFile());
            startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap imageBitmap = null;
            if (requestCode == REQUEST_CAPTURE_IMAGE) {
                //imageBitmap = (Bitmap) data.getExtras().get("data");
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                uploadedPic = findViewById(R.id.uploaded_pic);
                uploadedPic.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, 400, 400, false));

            } else if (requestCode == REQUEST_GALLERY_IMAGE) {
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    createFile();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                    byte[] bitMapData = bos.toByteArray();
                    try {
                        FileOutputStream fos = new FileOutputStream(photoFile);
                        fos.write(bitMapData);
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
        /*File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = File.createTempFile("file", ".jpeg", storageDir);
        Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 *//*ignored for PNG*//*, bos);
        byte[] bitMapData = bos.toByteArray();
        try {
            FileOutputStream fos = new FileOutputStream(photoFile);
            fos.write(bitMapData);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        ImageCompression imageCompression = new ImageCompression(this);
        File file = new File(imageCompression.compressImage(photoFile.getAbsolutePath()));
        RequestBody reqFile = RequestBody.create(MediaType.parse(getContentResolver().getType(photoURI)), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
        return body;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void success() {
        postButton.setEnabled(true);
        galleryButton.setEnabled(true);
        cameraButton.setEnabled(true);
        finish();
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.post_prescriptions_success), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AddPrescriptionsActivity.this, ViewPrescriptionsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void failed() {
        postButton.setEnabled(true);
        galleryButton.setEnabled(true);
        cameraButton.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }
}