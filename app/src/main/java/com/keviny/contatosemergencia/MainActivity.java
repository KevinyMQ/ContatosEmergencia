package com.keviny.contatosemergencia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    TextView tv_phonebook;
    ListView lista;
    //to store the phonebook
    ArrayList<String> arrayList;
    ArrayList<Contact> phoneContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.contactList);
        //tv_phonebook = findViewById(R.id.tv_phonebook);
        //to initialize the memory to arraylist
        arrayList = new ArrayList<>();
        phoneContacts = new ArrayList<>();
        //give runtime permission for read contact
        //this problem comes in marshmallow or greater version
        //this problem only for dangerous permission like phonebook, sms, camera
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    1);



        }else{
            //
            //
            //getcontact();
        }

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                    changeScreen();
              }
          }

        );


          lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  Toast.makeText(MainActivity.this, ""+phoneContacts.get(position).name, Toast.LENGTH_SHORT).show();

                  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && checkSelfPermission(Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                            2);
                }else{
                      Intent chamada = new Intent(Intent.ACTION_CALL);
                      //pega a posição da pessoa

                      //chamada.setData(Uri.parse("tel:" + phoneContacts.get(position).number));
                      chamada.setData(Uri.parse("tel:"+"85991730861"));
                      startActivity(chamada);
                    //
                    //
                    //getcontact();
                }


              }
          });


    }




    public void changeScreen(){
        Intent intent = new Intent(this, ListContacts.class);
        startActivity(intent);
    }





    private void getcontact() {
        //to pass all function contacts to cursor

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        //to fetch all the contact from cursor
        while (cursor.moveToNext()){
            //pass the data into string from cursor
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //now add the data to an array list
            arrayList.add(name + "\n"+ mobile);
            Contact contact = new Contact(name,mobile);
            phoneContacts.add(contact);
            //to attach the arraylist into textview
            //System.out.println(phoneContacts.get(1).name);
            //tv_phonebook.setText(arrayList.toString());
        }

//        for(int i = 0; i < phoneContacts.size(); i++){
//            System.out.println("Nome("+i+"): "+phoneContacts.get(i).name);
//            System.out.println("Número("+i+"): "+phoneContacts.get(i).number);
//        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        lista.setAdapter(arrayAdapter);
        }

    //to get the output of runtime permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //now permission is granted call the function again
                //getcontact();
            }else{
                this.finishAffinity();
            }
        }
        if (requestCode == 2) {
            // Request for camera permission.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Toast.makeText(MainActivity.this, "dentro do if", Toast.LENGTH_SHORT).show();
            } else {
                // Permission request was denied.
                Toast.makeText(MainActivity.this, "fora do if", Toast.LENGTH_SHORT).show();
            }
        }


    }
}