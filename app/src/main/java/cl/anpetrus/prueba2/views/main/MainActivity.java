package cl.anpetrus.prueba2.views.main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cl.anpetrus.prueba2.R;
import cl.anpetrus.prueba2.models.Event;
import cl.anpetrus.prueba2.utils.ImageUtil;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_CODE = 100;
    private MainActivityFragment mainActivityFragment;
    private EditText dateStartEt, timeStartEt, nameTv, descriptionTv;
    private ImageView imageIv;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Eventos próximos");

        mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().hide();
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_new_event);

                TextView titleDialogTv = dialog.findViewById(R.id.titleDialogTv);
                titleDialogTv.setText("NUEVO EVENTO");

                LinearLayout addEventLl = dialog.findViewById(R.id.addEventLl);

                nameTv = dialog.findViewById(R.id.nameNewEt);
                descriptionTv = dialog.findViewById(R.id.descriptionNewEt);
                imageIv = dialog.findViewById(R.id.imageIv);

                nameTv.setHint("NOMBRE");
                descriptionTv.setHint("DESCRIPCIÓN");

                dateStartEt = dialog.findViewById(R.id.dateStartEt);
                timeStartEt = dialog.findViewById(R.id.timeStartEt);
                Date dateNow = new Date();
                String dateString = new SimpleDateFormat("dd-MM-yyyy").format(dateNow);
                String timeString = new SimpleDateFormat("HH:mm:ss").format(dateNow);

                dateStartEt.setText(dateString);
                timeStartEt.setText(timeString);

                addEventLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideKeyBoard(view);
                    }
                });

                imageIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGallery();
                    }
                });

                dateStartEt.setOnClickListener(new View.OnClickListener() {
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
                                        dateStartEt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                    }
                                }, year, month, day);
                        datePickerDialog.show();
                    }
                });

                timeStartEt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        hideKeyBoard(view);

                        int hour, minute;
                        final Calendar c = Calendar.getInstance();
                        hour = c.get(Calendar.HOUR_OF_DAY);
                        minute = c.get(Calendar.MINUTE);
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

                        String name = nameTv.getText().toString();
                        String description = descriptionTv.getText().toString();
                        String dateString = dateStartEt.getText().toString() + " " + timeStartEt.getText().toString();
                        Date startDateTime;

                        try {
                            startDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dateString);
                            if (isValidData(name, description, startDateTime)) {
                                Event event = new Event();
                                event.setName(name);
                                event.setDescription(description);
                                event.setStart(startDateTime);
                                imageIv.setDrawingCacheEnabled(true);
                                imageIv.buildDrawingCache();
                                try {
                                    event.setImage(ImageUtil.GetByteFromBitmap(imageIv.getDrawingCache()));
                                    mainActivityFragment.addToAdatperList(event);
                                    getSupportActionBar().show();
                                    imageUri = null;
                                    dialog.dismiss();
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "Problemas con la imagen, favor selecciona otra", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (ParseException e) {
                            if (dateString.trim().length() <= 0) {
                                Toast.makeText(MainActivity.this, "Favor ingresar fecha y hora", Toast.LENGTH_LONG).show();
                            } else {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Error indesperado", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
    }

    private void hideKeyBoard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    private boolean isValidData(String name, String description, Date date) {
        if (name.trim().length() > 0) {
            if (description.trim().length() > 0) {
                if (date.after(new Date())) {
                    if (imageUri != null) {
                        return true;
                    } else {
                        Toast.makeText(this, "Favor agrega una imagen", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Favor ingresar fecha y hora posterior a la actual", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Favor ingresar descripción", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Favor ingresar nombre", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void openGallery() {
        try {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, PICK_IMAGE_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Problemas con la imagen, favor selecciona otra", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
                imageUri = data.getData();
                imageIv.setImageURI(imageUri);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Problemas con la imagen, favor selecciona otra", Toast.LENGTH_LONG).show();
        }
    }
}