package cl.anpetrus.prueba2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import cl.anpetrus.prueba2.data.EventQuery;
import cl.anpetrus.prueba2.models.Event;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Futura acción para compartir", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView nameTv = (TextView) findViewById(R.id.detailNameTv);
        TextView descriptionTv = (TextView) findViewById(R.id.detailDescriptionTv);
        TextView dateStartTv = (TextView) findViewById(R.id.detailDateStartTv);
        TextView timeStartTv = (TextView) findViewById(R.id.detailTimeStartTv);
        ImageView imageIv = (ImageView) findViewById(R.id.detailImageIv);

        long idEvent = getIntent().getLongExtra(MainActivityFragment.EVENT_ID,0L);
        Event event = new EventQuery().get(idEvent);

        getSupportActionBar().setTitle("Evento");

        imageIv.setImageBitmap(ImageUtils.convertByteArrayToBitmap(event.getImage()));

        nameTv.setText(event.getName());
        descriptionTv.setText(event.getDescription());

        String dateString = new SimpleDateFormat("dd-MM-yyyy").format(event.getStart());
        String timeString = new SimpleDateFormat("HH:mm").format(event.getStart()) + " Hrs.";

        dateStartTv.setText(dateString);
        timeStartTv.setText(timeString);
    }

}
