package com.example.contentprovider.Activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.contentprovider.Adapters.ContactsAdapter;
import com.example.contentprovider.ModelClasses.ContactsModel;
import com.example.contentprovider.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ContactsModel> list = new ArrayList<ContactsModel>();
    RecyclerView contacts;
    ContactsAdapter adapter;
    private static final String TAG =MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = (RecyclerView) findViewById(R.id.contacts);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();


            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            contacts.setLayoutManager(layoutManager);


//        ContactsModel model=new ContactsModel("Divij","6283609992");
//        list.add(model);
//        for(int i=0;i<20;i++){
//            model=new ContactsModel("Divij","6283609992");
//            list.add(model);
//
//        }
            getAllContacts();

            adapter = new ContactsAdapter(list, this);
            contacts.setAdapter(adapter);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean contacts = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && contacts) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                } else if (Build.VERSION.SDK_INT > 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Permission Required");
                    builder.setMessage("Permissions are required to run the app");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    })
                            .create()
                            .show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 10);
                }

        }
    }


    private void getAllContacts(){

            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                    if (hasPhoneNumber > 0) {
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        ContactsModel model = new ContactsModel();
                        model.setName(name);
                        Log.d(TAG, "Contact Name: " + name);


                        Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                        if (phoneCursor.moveToNext()) {

                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            model.setNumber(phoneNumber);
                            if(ContactsContract.CommonDataKinds.Phone.PHOTO_URI !=null){
                                String pic=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                                Log.d("pic",""+pic);
                                model.setPhoto_uri(pic);
                            }
                            Log.d(TAG,"Contact Phone NUmber: "+phoneNumber);
                        }
                        phoneCursor.close();

//                    Cursor emailCursor = contentResolver.query(
//                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Email.CONTACT_ID+"=?",
//                            new String[]{id}, null);
//                    while (emailCursor.moveToNext()) {
//                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//                    }
//                    model.setNumber("6283609992");
                        list.add(model);
                    }
                }

                cursor.close();

            }






    }
}
