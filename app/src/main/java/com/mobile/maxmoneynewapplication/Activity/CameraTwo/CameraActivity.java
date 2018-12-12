/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mobile.maxmoneynewapplication.Activity.CameraTwo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.mobile.maxmoneynewapplication.R;

public class CameraActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent getIntent = getIntent();

        String bitmap1 = "123";
        String idNo = "934576098978";
        if (null == savedInstanceState) {

            if(Build.VERSION.SDK_INT < 23){
                Log.d("Build.VERSION.SDK_INT()",String.valueOf(Build.VERSION.SDK_INT));
                Log.d("getDeviceName()",getDeviceName());
            }else{
                Log.d("Build.VERSION.SDK_INT()",String.valueOf(Build.VERSION.SDK_INT));
                Log.d("getDeviceName()",getDeviceName());
                if(getDeviceName().contains("HUAWEI")){
                    Camera2VideoFragment fragment = Camera2VideoFragment.newInstance();
                    Bundle arguments = new Bundle();
                    arguments.putString("bitmap1", bitmap1);
                    arguments.putString("idNo", idNo);
                    fragment.setArguments(arguments);
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }else{
                    Intent next = new Intent(getApplicationContext(), VideoActivity.class);
                    startActivity(next);
                }
            }



        }
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

}
