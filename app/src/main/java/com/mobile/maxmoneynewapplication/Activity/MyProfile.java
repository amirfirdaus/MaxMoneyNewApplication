package com.mobile.maxmoneynewapplication.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.StandardProgressDialog;
import com.mobile.maxmoneynewapplication.Common.TypeFaceClass;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.NukeSSLCerts;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class MyProfile extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{

    private PreferenceManagerLogin session;

    TextView textView_personal,textView_addressInfo,textView_idNo,textView_nationality,textView_phoneNo,textView_email,textView_source,textView_natureofbisnes,
            textView_address,textView_city,textView_postol,textView_state,textView_country,textView_username,textView_usernameb,textView_fullname,
            textView_fullnameb;

    EditText editText_idNo,editText_nationality,editText_phoneNo,editText_email,editText_address,editText_city,editText_postol;

    Button button_update;

    private SwipeRefreshLayout swipeRefreshLayout;

    MediaType countriesJSON;

    ArrayAdapter<String> spinnerCountriesAdapter;

    ImageView iv;

    String filePATH1;

    SearchableSpinner spinner_country,spinner_state,spinner_source,spinner_natureofbisnes;

    private Uri outPutfileUri;

    private int GALLERY_PROFILE = 1,CAMERA_PROFILE = 2;

    private Bitmap profileImage;

    String URL_IMAGE = "https://www.maxmoney.com/my/app/android/getProfilePicture.php?";

    String URL_UPLOAD_IMAGE = "https://www.maxmoney.com/my/app/android/uploadProfilePicture.php";

    ImageView imageView_icon_menu;

    TextView textView_title;
    StandardProgressDialog standardProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        countriesJSON = MediaType.parse("application/json; charset=utf-8");
        session = new PreferenceManagerLogin(getApplicationContext());
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        iv =  findViewById(R.id.profile_image);

        imageView_icon_menu = findViewById(R.id.imageView_icon_menu);
        textView_title = findViewById(R.id.textView_title);
        TypeFaceClass.setTypeFaceTextView(textView_title,getApplicationContext());
        textView_username = findViewById(R.id.textView_username);
        textView_usernameb = findViewById(R.id.textView_usernameb);
        textView_fullname = findViewById(R.id.textView_fullname);
        textView_fullnameb = findViewById(R.id.textView_fullnameb);
        textView_personal = findViewById(R.id.textView_personal);
        textView_addressInfo = findViewById(R.id.textView_AddressInfo);
        textView_idNo = findViewById(R.id.textView_idNo);
        textView_nationality = findViewById(R.id.textView_nationality);
        textView_phoneNo = findViewById(R.id.textView_phoneno);
        textView_email = findViewById(R.id.textView_email);
        textView_source = findViewById(R.id.textView_sourceIncome);
        textView_natureofbisnes = findViewById(R.id.textView_natureBisnes);
        textView_address = findViewById(R.id.textView_address);
        textView_city = findViewById(R.id.textView_city);
        textView_postol = findViewById(R.id.textView_poskod);
        textView_state = findViewById(R.id.textView_state);
        textView_country = findViewById(R.id.textView_country);

        editText_idNo = findViewById(R.id.editText_idNo);
        editText_nationality = findViewById(R.id.editText_nationality);
        editText_phoneNo = findViewById(R.id.editText_phoneNo);
        editText_email = findViewById(R.id.editText_email);
        editText_address = findViewById(R.id.editText_address);
        editText_city = findViewById(R.id.editText_city);
        editText_postol = findViewById(R.id.editText_poskod);

        spinner_country = findViewById(R.id.spinner_country);
        spinner_state = findViewById(R.id.spinner_state);
        spinner_source =  findViewById(R.id.spinner_income);
        spinner_natureofbisnes = findViewById(R.id.spinner_natureBisnes);

        button_update = findViewById(R.id.button_update);
        ChangeFontType();
        unableEditText();
        spinnerItem();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
        @Override
        public void run() { swipeRefreshLayout.setRefreshing(true);populateData(); getImage();}
    });
        button_update.setOnClickListener(this);
        iv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPictureDialog("P");
        }
    });


        imageView_icon_menu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i =  new Intent(getApplicationContext(), MenuActivity.class);
            i.putExtra("current","profile");
            startActivity(i);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }
    });
}

    public void onBackPressed() {

    }

    private void showPictureDialog(final String s) {
        android.support.v7.app.AlertDialog.Builder pictureDialog = new android.support.v7.app.AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new android.content.DialogInterface.OnClickListener() {
                    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                String ss = s + "G";
                                choosePhotoFromGallery(ss);
                                break;
                            case 1:
                                String sss = s + "C";
                                takePhotoFromCamera(sss);
                                break;

                        }
                    }
                });
        pictureDialog.show();
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    private void takePhotoFromCamera(String s) {
        Intent intent = null;
        int SEND = 0;
        if (s.equals("PC")) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            android.content.ContentValues values = new android.content.ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "profile.jpg");
            outPutfileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            SEND = CAMERA_PROFILE;
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
        startActivityForResult(intent, SEND);
    }

    public void choosePhotoFromGallery(String s) {
        int SEND = 0;
        if (s.equals("PG")) {
            SEND = GALLERY_PROFILE;
        }
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, SEND);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PROFILE) {
            if (data != null) {
                Uri contentURI = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Bitmap rotate = rotateImageIfRequired(bitmap,this,contentURI);
                    profileImage = rotate;
                    filePATH1 = getStringImage(profileImage);
                    iv.setImageBitmap(profileImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == CAMERA_PROFILE) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outPutfileUri);
                Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 920, 576, true);

                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotate = Bitmap.createBitmap(scaled , 0, 0, scaled.getWidth(), scaled.getHeight(), matrix, true);
                profileImage = rotate;
                iv.setImageBitmap(rotate);
                filePATH1 = getStringImage(rotate);

                File file = new File(getFilePath(outPutfileUri));
                file.delete();
                this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(getFilePath(outPutfileUri)))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void spinnerItem(){
        spinner_state.setTitle("Choose State");
        spinner_source.setTitle("Choose Source of income");
        spinner_natureofbisnes.setTitle("Choose Nature of business");
        try {
            JSONObject obj = new JSONObject(readJSONFromAsset());
            JSONArray array = obj.getJSONArray("country");
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject yeah = array.getJSONObject(i);
                list.add(yeah.getString("name"));
            }
            spinnerCountriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
            spinner_country.setTitle("Choose Country");
            spinner_country.setAdapter(spinnerCountriesAdapter);
            spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        }catch (JSONException e) {
            e.printStackTrace();
        }

        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
                ((TextView) parent.getChildAt(0)).setTextSize(14);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
                ((TextView) parent.getChildAt(0)).setTextSize(13);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_natureofbisnes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
                ((TextView) parent.getChildAt(0)).setTextSize(13);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void unableEditText(){
        editText_idNo.setFocusable(false);
        editText_nationality.setFocusable(false);
    }

    private void ChangeFontType() {
        textView_username.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_usernameb.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_fullname.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_fullnameb.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_personal.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_addressInfo.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_idNo.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_nationality.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_phoneNo.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_email.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_source.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_natureofbisnes.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_address.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_city.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_postol.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_state.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_country.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        button_update.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));

    }

    @Override
    public void onRefresh() {
        populateData();
    }

    public void populateData(){
        NukeSSLCerts.nuke();
        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/users/current",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            swipeRefreshLayout.setRefreshing(false);
                            JSONObject userOBJ = new JSONObject(response);

                            //getdata
                            textView_fullnameb.setText(userOBJ.getString("fullName"));
                            textView_usernameb.setText(userOBJ.getString("username"));
                            editText_idNo.setText(userOBJ.getString("idNo"));
                            editText_email.setText(userOBJ.getString("email"));
                            editText_phoneNo.setText(userOBJ.getString("mobile"));

                            JSONObject riskProfiledOBJ = new JSONObject(userOBJ.getString("riskProfile"));
                            JSONObject cddOBJ = new JSONObject(riskProfiledOBJ.getString("cdd"));

                            editText_nationality.setText(cddOBJ.getString("nationality"));

                            spinner_state.setSelection(getIndex(spinner_state, cddOBJ.getString("state")));
                            editText_address.setText(cddOBJ.getString("address"));
                            editText_city.setText(cddOBJ.getString("city"));
                            editText_postol.setText(cddOBJ.getString("postalCode"));

                            JSONObject eddOBJ = new JSONObject(riskProfiledOBJ.getString("edd"));


                            spinner_source.setSelection(getIndex(spinner_source, eddOBJ.getString("incomeSource")));
                            spinner_natureofbisnes.setSelection(getIndex(spinner_natureofbisnes,eddOBJ.getString("natureOfBusiness")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> user = session.getUserDetails();
                String token = user.get(PreferenceManagerLogin.KEY_TOKEN);
                HashMap<String, String> headers = new HashMap();
                headers.put("api-key",token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    @Override
    public void onClick(View v) {
        standardProgressDialog.show();
        uploadImage();

        NukeSSLCerts.nuke();
        //guna link ni PUT https://api.maxmoney2u.com:443/v1/users/sa%40maxmoney.com/profile
        HashMap<String, String> users = session.getUserDetails();
        String username = users.get(PreferenceManagerLogin.KEY_EMAIL);
        final String mobile = editText_phoneNo.getText().toString().trim();
        final String email = editText_email.getText().toString().trim();

        final String address = editText_address.getText().toString();
        final String city = editText_city.getText().toString().trim();
        final String postalCode = editText_postol.getText().toString().trim();

        final String incomeSource = spinner_source.getSelectedItem().toString();
        final String natureOfBusiness = spinner_natureofbisnes.getSelectedItem().toString();

        final String state = spinner_state.getSelectedItem().toString();
        final String country = spinner_country.getSelectedItem().toString();

        StringRequest stringRequest = new StringRequest(PUT, BasedURL.ROOT_URL_API +"v1/users/"+username+"/profile",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        standardProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Successfully updated",Toast.LENGTH_SHORT).show();
                        onRefresh();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        standardProgressDialog.dismiss();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> user = session.getUserDetails();
                String token = user.get(PreferenceManagerLogin.KEY_TOKEN);
                HashMap<String, String> headers = new HashMap();
                headers.put("api-key",token);
                return headers;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile",mobile);
                params.put("email",email);
                params.put("address",address);
                params.put("city",city);
                params.put("postalCode",postalCode);
                params.put("incomeSource",incomeSource);
                params.put("natureOfBusiness",natureOfBusiness);
                params.put("state",state);
                params.put("country",country);
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getImage(){
        HashMap<String, String> user = session.getUserDetails();
        String token = user.get(PreferenceManagerLogin.KEY_TOKEN);
        StringRequest stringRequest = new StringRequest(GET, URL_IMAGE+"user_id="+token,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject OBJOBJ = new JSONObject(response);
                            JSONArray result = new JSONArray(OBJOBJ.getString("result"));
                            for(int i = 0; i<result.length();i++){
                                JSONObject beneficiaryOBJ = result.getJSONObject(i);

                                if(Build.VERSION.SDK_INT <= 22){
                                    String imageUrl = "http://www.maxmoney.com/my/images/profile-picture/"+beneficiaryOBJ.getString("url");
                                    Picasso.get().load(imageUrl).fit().centerCrop().into(iv);
                                }else{
                                    String imageUrl = "https://www.maxmoney.com/my/images/profile-picture/"+beneficiaryOBJ.getString("url");
                                    Picasso.get().load(imageUrl).into(iv);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void uploadImage(){
        NukeSSLCerts.nuke();
        if(profileImage == null){

        }else {
            HashMap<String, String> user = session.getUserDetails();
            final String user_id = user.get(PreferenceManagerLogin.KEY_TOKEN);

            final String imageBITMAP = getStringImagePNG(profileImage);
            StringRequest stringRequest = new StringRequest(POST, URL_UPLOAD_IMAGE,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            standardProgressDialog.dismiss();
                            try {
                                JSONObject resultOBJ = new JSONObject(response);
                                JSONArray resultArray = new JSONArray(resultOBJ.getString("result"));

                                for(int i =0; i < resultArray.length(); i++){
                                    JSONObject statuss = resultArray.getJSONObject(i);
                                    if(statuss.getString("status").equals("true")){
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Failed updated profile photo",Toast.LENGTH_SHORT).show();
                                    }
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            standardProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id",user_id);
                    params.put("image",imageBITMAP.toString()+"");
                    return params;
                };
            };

            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

    }

    public String getStringImagePNG(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageString;
    }

    public static Bitmap rotateImageIfRequired(Bitmap img, Context context, Uri selectedImage) throws IOException {

        if (selectedImage.getScheme().equals("content")) {
            String[] projection = { MediaStore.Images.ImageColumns.ORIENTATION };
            Cursor c = context.getContentResolver().query(selectedImage, projection, null, null, null);
            if (c.moveToFirst()) {
                final int rotation = c.getInt(0);
                c.close();
                return rotateImage(img, rotation);
            }
            return img;
        } else {
            ExifInterface ei = new ExifInterface(selectedImage.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(img, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(img, 270);
                default:
                    return img;
            }
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        return rotatedImg;
    }


}
