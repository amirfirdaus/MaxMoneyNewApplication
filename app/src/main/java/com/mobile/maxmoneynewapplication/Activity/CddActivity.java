package com.mobile.maxmoneynewapplication.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.chrisbanes.photoview.PhotoView;
import com.mobile.maxmoneynewapplication.Activity.CameraTwo.CameraActivity;
import com.mobile.maxmoneynewapplication.Activity.CameraTwo.VideoRecording.MainActivity;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.StandardProgressDialog;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.FaceResult;
import com.mobile.maxmoneynewapplication.Utils.ImageUtils;
import com.mobile.maxmoneynewapplication.Utils.NukeSSLCerts;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class CddActivity extends AppCompatActivity {

    ImageView imageView_documentFront, imageView_documentBack, imageView_back, imageView_cancel;
    String filePATH1, filePATH2;
    TextView textView_v1, textView_v2, textView_v3, textView_name, textView_passport, textView_cdd,textView_spinner,textView_business;
    Button button_submit;
    Intent intent_next, intent_back, intent_cancel;
    private int GALLERY_DOCUMENT_FRONT = 11;
    private int CAMERA_DOCUMENT_FRONT = 12;
    private int GALLERY_DOCUMENT_BACK = 21;
    private int CAMERA_DOCUMENT_BACK = 22;
    private Uri outPutfileUri;
    private Bitmap imageFirst, imageSecond;
    Boolean statusGALERY = false, statusCAMERA = true;
    private String cameraBITMAPSTRING = "", galeryBITMAPSTRING = "";

    String idNo = "",email="",idType="",cusomterName="",session="",mobile="";
    StandardProgressDialog pDialog;

    Spinner spinner_sourceOfIncome,spinner_natureOfBusiness;
    String whichONE = "";
    Bitmap MICROCHIPBACK,MICROCHIPFRONT;

    String ImageDistanceFront = "";
    String ImageDistanceBack = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdd);

        Intent getIntent = getIntent();
        idNo = getIntent.getStringExtra("idNo");
        email = getIntent.getStringExtra("email");
        idType = getIntent.getStringExtra("idType");
        cusomterName = getIntent.getStringExtra("name");
        mobile = getIntent.getStringExtra("mobile");

        spinner_sourceOfIncome = findViewById(R.id.spinner_sourceOfIncome);
        spinner_natureOfBusiness = findViewById(R.id.spinner_natureOfBusiness);

        pDialog = new StandardProgressDialog(this.getWindow().getContext());
        ActivityCompat.requestPermissions(CddActivity.this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO},1);

        //declare
        textView_v1 = findViewById(R.id.textView_v1);
        textView_v2 = findViewById(R.id.textView_v2);
        textView_v3 = findViewById(R.id.textView_v3);
        textView_cdd = findViewById(R.id.textView_cdd);
        textView_name = findViewById(R.id.textView_name);
        textView_business = findViewById(R.id.textVie_business);
        textView_spinner = findViewById(R.id.textView_source);
        textView_passport = findViewById(R.id.textView_passport);

        textView_name.setText(cusomterName);
        textView_passport.setText(idNo);

        imageView_back = findViewById(R.id.imageView_back);
        imageView_documentFront = findViewById(R.id.imageView_documentFront);
        imageView_documentBack = findViewById(R.id.imageView_documentBack);

        button_submit = findViewById(R.id.button_submit);

        intent_next = new Intent(getApplicationContext(), SmsActivity.class);

        //font type
        ChangeFontType();

        //on click image
        imageView_documentFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog("F");
            }
        });
        imageView_documentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog("B");
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.show();
                if(statusCAMERA == false && statusGALERY == true){
                    intent_next.putExtra("image_1",cameraBITMAPSTRING);
                }
                if(statusCAMERA == true && statusGALERY == false){
                    intent_next.putExtra("image_1",galeryBITMAPSTRING);
                    Log.d("BITMAP 1",galeryBITMAPSTRING);
                }
                intent_next.putExtra("ImageDistanceFront",ImageDistanceFront);
                intent_next.putExtra("ImageDistanceBack",ImageDistanceBack);
                registerCDD();
            }
        });

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(CddActivity.this,RegisterActivity.class);
                startActivity(next);
            }
        });
    }

    private void ChangeFontType() {
        textView_v1.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        textView_v2.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        textView_v3.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        textView_cdd.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        textView_name.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        textView_passport.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        button_submit.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        textView_business.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        textView_spinner.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
    }

    private void showPictureDialog(final String s) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
              /*  "Select photo from gallery",*/
                "Capture photo from camera",
                "View Image"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                           /* case 0:
                                String ss = s + "G";
                                choosePhotoFromGallery(ss);
                                break;*/
                            case 0:
                                String sss = s + "C";
                                takePhotoFromCamera(sss);
                                break;
                            case 1:
                                if (imageFirst != null || imageSecond != null) {
                                    if (s.equals("F")) {
                                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CddActivity.this);
                                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                                        PhotoView photoView = mView.findViewById(R.id.imageView);
                                        photoView.setImageBitmap(imageFirst);
                                        mBuilder.setView(mView);
                                        AlertDialog mDialog = mBuilder.create();
                                        mDialog.show();
                                    } else if (s.equals("B")) {
                                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CddActivity.this);
                                        View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                                        PhotoView photoView = mView.findViewById(R.id.imageView);
                                        photoView.setImageBitmap(imageSecond);
                                        mBuilder.setView(mView);
                                        AlertDialog mDialog = mBuilder.create();
                                        mDialog.show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "choose image first", Toast.LENGTH_LONG).show();
                                }
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery(String s) {
        int SEND = 0;
        if (s.equals("FG")) {
            SEND = GALLERY_DOCUMENT_FRONT;
        } else if (s.equals("BG")) {
            SEND = GALLERY_DOCUMENT_BACK;
        }
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, SEND);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void takePhotoFromCamera(String s) {
        Intent intent = null;
        int SEND = 0;
        if (s.equals("FC")) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "documentFront.jpg");
            outPutfileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            SEND = CAMERA_DOCUMENT_FRONT;
        } else if (s.equals("BC")) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "documentBack.jpg");
            outPutfileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            SEND = CAMERA_DOCUMENT_BACK;
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
        startActivityForResult(intent, SEND);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_DOCUMENT_FRONT) {
            if (data != null) {
                Uri contentURI = data.getData();
                Bitmap bitmapCROP = ImageUtils.getBitmap(ImageUtils.getRealPathFromURI(this, contentURI), 2048, 1232);
                if(bitmapCROP != null)
                    detectFaceGALERY(bitmapCROP);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageFirst = bitmap;
                    imageView_documentFront.setImageBitmap(bitmap);
                    filePATH1 = getStringImage(imageFirst);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == CAMERA_DOCUMENT_FRONT) {
            whichONE = "first";
            startCropImageActivity(outPutfileUri);
        } else if (requestCode == GALLERY_DOCUMENT_BACK) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageSecond = bitmap;
                    imageView_documentBack.setImageBitmap(bitmap);
                    filePATH2 = getStringImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == CAMERA_DOCUMENT_BACK) {
            whichONE = "second";
            startCropImageActivity(outPutfileUri);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if(whichONE.equals("first")){
                    Bitmap bitmapCROP = ImageUtils.getBitmap(ImageUtils.getRealPathFromURI(this, outPutfileUri), 2048, 1232);
                    detectFaceCAMERA(bitmapCROP);

                    try {
                        Bitmap ICIMAGE = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                        Bitmap SCALEDIC = Bitmap.createScaledBitmap(ICIMAGE, 1080, 1000, true);
                        imageFirst = SCALEDIC;
                        filePATH1 = getStringImage(imageFirst);

                        MICROCHIPFRONT = Bitmap.createBitmap(SCALEDIC, 300, 430, 200, 200);
                        getImageDistanceFront(MICROCHIPFRONT);
                        Drawable d = new BitmapDrawable(getResources(), SCALEDIC);
                        imageView_documentFront.setImageDrawable(d);

                        File file = new File(getFilePath(outPutfileUri));
                        file.delete();
                        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(getFilePath(outPutfileUri)))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(whichONE.equals("second")){
                    try {
                        Bitmap ICIMAGE = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                        Bitmap SCALEDIC = Bitmap.createScaledBitmap(ICIMAGE, 1080, 1000, true);
                        imageSecond = SCALEDIC;
                        filePATH2 = getStringImage(imageSecond);
                        MICROCHIPBACK = Bitmap.createBitmap(SCALEDIC, 20, 30, 290, 950);
                        getImageDistanceback(MICROCHIPBACK);
                        Drawable d = new BitmapDrawable(getResources(), imageSecond);
                        imageView_documentBack.setImageDrawable(d);

                        File file = new File(getFilePath(outPutfileUri));
                        file.delete();
                        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(getFilePath(outPutfileUri)))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private String getFilePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String picturePath = cursor.getString(columnIndex); // returns null
            cursor.close();
            return picturePath;
        }
        return null;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageString;
    }

    private void detectFaceCAMERA(Bitmap bitmap) {
        statusCAMERA = false;
        statusGALERY = true;
        android.media.FaceDetector fdet_ = new android.media.FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 1);

        android.media.FaceDetector.Face[] fullResults = new android.media.FaceDetector.Face[1];
        fdet_.findFaces(bitmap, fullResults);

        ArrayList<FaceResult> faces_ = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            if (fullResults[i] != null) {
                PointF mid = new PointF();
                fullResults[i].getMidPoint(mid);

                float eyesDis = fullResults[i].eyesDistance();
                float confidence = fullResults[i].confidence();
                float pose = fullResults[i].pose(android.media.FaceDetector.Face.EULER_Y);

                Rect rect = new Rect(
                        (int) (mid.x - eyesDis * 1.20f),
                        (int) (mid.y - eyesDis * 0.55f),
                        (int) (mid.x + eyesDis * 1.20f),
                        (int) (mid.y + eyesDis * 1.85f));

                /**
                 * Only detect face size > 100x100
                 */
                if (rect.height() * rect.width() > 100 * 100) {
                    FaceResult faceResult = new FaceResult();
                    faceResult.setFace(0, mid, eyesDis, confidence, pose, System.currentTimeMillis());
                    faces_.add(faceResult);

                    //
                    // Crop Face to display in RecylerView
                    //
                    Bitmap cropedFace = ImageUtils.cropFace(faceResult, bitmap, 0);
                    if (cropedFace != null) {
                        cameraBITMAPSTRING = getStringImage(cropedFace);
                        openDialogFace(cropedFace);
                    }else{
                        Toast.makeText(getApplicationContext(),"Please Try Again. We cannot find Face",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please Try Again. We cannot find Face",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Please Try Again. We cannot find Face",Toast.LENGTH_LONG).show();
            }

        }

    }

    private void detectFaceGALERY(Bitmap bitmap) {
        statusCAMERA = true;
        statusGALERY = false;
        android.media.FaceDetector fdet_ = new android.media.FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 1);

        android.media.FaceDetector.Face[] fullResults = new android.media.FaceDetector.Face[1];
        fdet_.findFaces(bitmap, fullResults);

        ArrayList<FaceResult> faces_ = new ArrayList<>();


        for (int i = 0; i < 1; i++) {
            if (fullResults[i] != null) {
                PointF mid = new PointF();
                fullResults[i].getMidPoint(mid);

                float eyesDis = fullResults[i].eyesDistance();
                float confidence = fullResults[i].confidence();
                float pose = fullResults[i].pose(android.media.FaceDetector.Face.EULER_Y);

                Rect rect = new Rect(
                        (int) (mid.x - eyesDis * 1.20f),
                        (int) (mid.y - eyesDis * 0.55f),
                        (int) (mid.x + eyesDis * 1.20f),
                        (int) (mid.y + eyesDis * 1.85f));

                /**
                 * Only detect face size > 100x100
                 */
                if (rect.height() * rect.width() > 100 * 100) {
                    FaceResult faceResult = new FaceResult();
                    faceResult.setFace(0, mid, eyesDis, confidence, pose, System.currentTimeMillis());
                    faces_.add(faceResult);

                    //
                    // Crop Face to display in RecylerView
                    //
                    Bitmap cropedFace = ImageUtils.cropFace(faceResult, bitmap, 0);
                    if (cropedFace != null) {
                        galeryBITMAPSTRING = getStringImage(cropedFace);
                        openDialogFace(cropedFace);
                    }else{
                        Toast.makeText(getApplicationContext(),"Please Try Again. We cannot find Face",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please Try Again. We cannot find Face",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Please Try Again. We cannot find Face",Toast.LENGTH_LONG).show();
            }
        }

    }

    private void openDialogFace(final Bitmap bitmap){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CddActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_face_recognition_preview_cdd, null);
        mBuilder.setView(mView);
        final AlertDialog mDialog = mBuilder.create();
        mDialog.show();

        ImageView imageView = mView.findViewById(R.id.imageView_face);
        imageView.setImageBitmap(bitmap);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
            }
        }, 4000);
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    public void registerCDD(){
        loginAGENT();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                insertCDD();
            }
        }, 1500);

    }

    private void insertCDD() {
        NukeSSLCerts.nuke();
        if(filePATH1.toString().equals("") || filePATH2.toString().equals("")){
            Toast.makeText(getApplicationContext(),"Please Upload Picture",Toast.LENGTH_SHORT).show();
        }else{
            String URLs = "https://www.maxmoney.com/cdd-app/process/register-save-cdd-testing.php";
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.getString("status").equals("false")) {
                                    if(obj.getString("message").equals("Authentication failed")){
                                        pDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), obj.getString("message") + "Please Login Again", Toast.LENGTH_LONG).show();
                                    }else{
                                        runUpdateProfile();
                                        pDialog.dismiss();
                                    }

                                } else {
                                    pDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
                                    intent_next.putExtra("idNo",idNo);
                                    intent_next.putExtra("email",email.toString().trim());
                                    intent_next.putExtra("mobile",mobile);
                                    startActivity(intent_next);
                                }
                            } catch (JSONException e) {
                                pDialog.dismiss();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams ()  throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("token",session+"");
                    params.put("idNo", idNo.toString().trim()+"");
                    params.put("natureOfBusiness",spinner_natureOfBusiness.getSelectedItem().toString());
                    params.put("incomeSource",spinner_sourceOfIncome.getSelectedItem().toString());
                    params.put("email", email.toString().trim()+"");
                    params.put("idType", idType.toString().trim()+"");
                    params.put("front", filePATH1.toString()+"");
                    params.put("back", filePATH2.toString()+"");

                    return params;
                }

            };
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    }

    private void runUpdateProfile() {
        StringRequest stringRequest = new StringRequest(PUT, BasedURL.ROOT_URL_API +"v1/users/"+email+"/profile",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
                        intent_next.putExtra("idNo",idNo);
                        intent_next.putExtra("email",email.toString().trim());
                        intent_next.putExtra("mobile",mobile);
                        startActivity(intent_next);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errpr0",error.toString());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("api-key",session);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("natureOfBusiness",spinner_natureOfBusiness.getSelectedItem().toString());
                params.put("incomeSource",spinner_sourceOfIncome.getSelectedItem().toString());
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void loginAGENT(){
        NukeSSLCerts.nuke();
        StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/sessions/current",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectResponse = new JSONObject(response);
                            if(objectResponse.has("code")){
                            }
                            if(objectResponse.has("session")){
                                session = objectResponse.getString("session");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username","sa@maxmoney.com");
                params.put("password","MaxMoney@2016");
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getImageDistanceFront(final Bitmap microchip) {
        StringRequest stringRequest = new StringRequest(POST, "https://www.maxmoney.com/cdd-app/process/e-kyc.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject next = new JSONObject(response);
                            if(next.has("output")){
                                JSONObject yaw = new JSONObject(next.getString("output"));
                                ImageDistanceFront = yaw.getString("distance");

                            }else{
                                Toast.makeText(getApplicationContext(),"IMAGE ERROR",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("front",getStringImage(microchip));
                return params;
            };
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getImageDistanceback(final Bitmap microchip) {
        StringRequest stringRequest = new StringRequest(POST, "https://www.maxmoney.com/cdd-app/process/e-kyc-back.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject next = new JSONObject(response);
                            if(next.has("output")){
                                JSONObject yaw = new JSONObject(next.getString("output"));
                                ImageDistanceBack = yaw.getString("distance");
                            }else{
                                Toast.makeText(getApplicationContext(),"IMAGE ERROR",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("front",getStringImage(microchip));
                return params;
            };
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
