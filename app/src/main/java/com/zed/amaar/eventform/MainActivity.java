package com.zed.amaar.eventform;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String eventString;
    EditText txtname;
    EditText txtSurname;
    EditText txtEmail;
    EditText txtGuest;
    Button btnSubmit;
    String firstname;
    String surname;
    String email;
    String guestnumber;
    View view;
    private String myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinnerEvent);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.events, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

       txtname = findViewById(R.id.txtName);
        txtSurname= findViewById(R.id.txtSurname);
        txtEmail= findViewById(R.id.txtEmail);
       txtGuest = findViewById(R.id.txtGuest);
       btnSubmit = findViewById(R.id.btnSubmit);

       txtname.addTextChangedListener(formwatcher);
       txtSurname.addTextChangedListener(formwatcher);
       txtEmail.addTextChangedListener(formwatcher);
        txtGuest.addTextChangedListener(formwatcher);
        firstname = txtname.getText().toString();
        surname = txtSurname.getText().toString();
        email = txtEmail.getText().toString();
        guestnumber = txtGuest.getText().toString();
        view = this.getWindow().getDecorView();


    }

    public void opDg(){
        AlertDialog.Builder mydg = new AlertDialog.Builder(MainActivity.this);
        mydg.setTitle("Enter Your name");

        final EditText nameinput = new EditText(MainActivity.this);
        nameinput.setInputType(InputType.TYPE_CLASS_TEXT);
        mydg.setView(nameinput);

        mydg.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                myText = nameinput.getText().toString();
                Toast.makeText(MainActivity.this, "Logged in as " + myText, Toast.LENGTH_SHORT).show();


            }
        });

        mydg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        mydg.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Users:
                opDg();
                return true;
            case R.id.blue:
                view.setBackgroundColor(Color.parseColor("#737EF3"));
                return true;
            case R.id.white:
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                return  true;
            case R.id.purple:
                view.setBackgroundColor(Color.parseColor("#D7C5F3"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private TextWatcher formwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            firstname = txtname.getText().toString();
            surname = txtSurname.getText().toString();
            email = txtEmail.getText().toString();
            guestnumber = txtGuest.getText().toString();

            btnSubmit.setEnabled(!firstname.isEmpty() && !surname.isEmpty() && !email.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {


            case 0:
                eventString = "Pool party";

                break;
            case 1:
                eventString = "Political Party";

                break;
            case 2:
                eventString = "Gathering";

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void OnClick(View v) {
            int guestint = Integer.parseInt(guestnumber);
        if (guestint > 3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You can only have up to 3 guests")

                    .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog ad = builder.create();
            ad.show();

        }

        else {
            //String event = spinner.getSelectedItem().toString();
            String subject = "Events Subject";


            String message = "Name: " + firstname + " " + surname + "\n Email: " + email + "\n Guests: " + guestnumber + "\n Event: " + eventString;

            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "events@gov.co.uk", null
            ));
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(intent, "Choose an Email Client:"));


        }

    }
}







