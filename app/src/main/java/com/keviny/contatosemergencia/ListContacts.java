package com.keviny.contatosemergencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListContacts extends AppCompatActivity {

    ListView lista;
    ArrayList<String> arrayList;
    ArrayList<Contact> phoneContacts;
    ArrayAdapter<String> arrayAdapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);
        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        editor = preferences.edit();

        lista = findViewById(R.id.contactsList);
        //tv_phonebook = findViewById(R.id.tv_phonebook);
        //to initialize the memory to arraylist
        arrayList = new ArrayList<>();
        phoneContacts = new ArrayList<>();
        getcontact();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
                                      @Override
                                      public void onClick(View v) {
                                          getShared();
                                      }
                                  }

        );





    }

    void getShared(){


    }


    private void getcontact() {
        //to pass all function contacts to cursor

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        //to fetch all the contact from cursor
        while (cursor.moveToNext()) {
            //pass the data into string from cursor
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //now add the data to an array list
            arrayList.add(name + "\n" + mobile);
            //Contact contact = new Contact(name,mobile);
            //phoneContacts.add(contact);
            //to attach the arraylist into textview
            //System.out.println(phoneContacts.get(1).name);
            //tv_phonebook.setText(arrayList.toString());
        }

        for(int i = 0; i < phoneContacts.size(); i++){
            System.out.println("Nome("+i+"): "+phoneContacts.get(i).name);
            System.out.println("Número("+i+"): "+phoneContacts.get(i).number);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        lista.setAdapter(arrayAdapter);
    }

}