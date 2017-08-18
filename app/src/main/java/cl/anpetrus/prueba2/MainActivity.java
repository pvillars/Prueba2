package cl.anpetrus.prueba2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cl.anpetrus.prueba2.models.Event;

public class MainActivity extends AppCompatActivity {

    private MainActivityFragment mainActivityFragment;

    EditText dateStartEt,timeStartEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_new_event);

                Button dateStartBtn = dialog.findViewById(R.id.dateStartBtn);
                Button timeStartBtn = dialog.findViewById(R.id.timeStartBtn);
                dateStartEt = dialog.findViewById(R.id.dateStartEt);
                timeStartEt = dialog.findViewById(R.id.timeStartEt);

                dateStartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        hideKeyBoard(view);

                        int year, month, day;
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        year = c.get(Calendar.YEAR);
                        month = c.get(Calendar.MONTH);
                        day = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        dateStartEt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                    }
                                }, year, month, day);
                        datePickerDialog.show();
                    }
                });

                timeStartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideKeyBoard(view);
                        int hour,minute;
                        // Get Current Time
                        final Calendar c = Calendar.getInstance();
                        hour = c.get(Calendar.HOUR_OF_DAY);
                        minute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        timeStartEt.setText(hourOfDay + ":" + minute + ":00");
                                    }
                                }, hour, minute, false);
                        timePickerDialog.show();
                    }
                });

                Button saveBtn = dialog.findViewById(R.id.saveEventBtn);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        EditText nameTv = dialog.findViewById(R.id.nameNewEt);
                        String name = nameTv.getText().toString().trim();

                        if (name.length() > 0) {
                            Event event = new Event();
                            event.setName(name);
                            event.setDescription("Description " + name);

                            try {
                                String dateString = dateStartEt.getText().toString()+" "+timeStartEt.getText().toString();
                                Date startDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateString);
                                event.setStart(startDateTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                dialog.dismiss();
                            }

                            mainActivityFragment.addToAdatperList(event);
                        }
                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
    }

    private void hideKeyBoard(View view){
        try {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }

}
