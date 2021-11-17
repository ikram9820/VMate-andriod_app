package com.example.vmate;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.vmate.Models.FbVideoDownloader;

public class PopUpUrlReciever extends Activity {
    EditText url;
    private int Permission_Storage_Id=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_url_reciever);
        getStoragePermission();
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        url= (EditText)findViewById(R.id.input_uri) ;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width =dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.4));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
            else
                Toast.makeText(this,"no intent of this type",Toast.LENGTH_SHORT);
        }

    }//end onCreate()

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            url.setText(sharedText);

        }
    }//end handleSendText()

    public void download(View view) {
        final String URL = url.getText().toString();

        FbVideoDownloader downloader = new FbVideoDownloader(PopUpUrlReciever.this,URL);
        downloader.DownloadVideo();
    }
    protected void getStoragePermission() {
        if (ContextCompat.checkSelfPermission(PopUpUrlReciever.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
           // Toast.makeText(PopUpUrlReciever.this, "Permission is Already Granted!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(PopUpUrlReciever.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("Storage Permission Needed")
                        .setMessage("Storage Permission is Needed to Access External Files")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PopUpUrlReciever.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Permission_Storage_Id);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Permission_Storage_Id);
            }

        }
        if (ContextCompat.checkSelfPermission(PopUpUrlReciever.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(PopUpUrlReciever.this, "Permission is Already Granted!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(PopUpUrlReciever.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("Storage Permission Needed")
                        .setMessage("Storage Permission is Needed to Access External Files")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PopUpUrlReciever.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Permission_Storage_Id);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Permission_Storage_Id);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == Permission_Storage_Id)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED)
            {
                if(!ActivityCompat.shouldShowRequestPermissionRationale(PopUpUrlReciever.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    new AlertDialog.Builder(this)
                            .setTitle("Storage Permission Needed")
                            .setMessage("Storage Permission is Compulsory for this Feature \r\nGrant Now?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri=Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                    Toast.makeText(PopUpUrlReciever.this,"Grant Permission from Permission Tab",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();

                }
            }
        }

    }
}