package cl.anpetrus.prueba2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView nameTv = (TextView) findViewById(R.id.detailNameTv);
        TextView descriptionTv = (TextView) findViewById(R.id.detailDescriptionTv);
        TextView startTv = (TextView) findViewById(R.id.detailStartTv);
        TextView endTv = (TextView) findViewById(R.id.detailEndTv);

        long idEvent = getIntent().getLongExtra(MainActivityFragment.EVENT_ID,0L);
        Event event = new EventQuery().get(idEvent);

        getSupportActionBar().setTitle(event.getName());
        descriptionTv.setText(event.getDescription());
        startTv.setText(event.getStart().toString());
        endTv.setText(event.getEnd().toString());
    }

}
