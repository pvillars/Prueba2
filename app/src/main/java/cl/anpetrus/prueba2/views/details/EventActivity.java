package cl.anpetrus.prueba2.views.details;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import cl.anpetrus.prueba2.R;
import cl.anpetrus.prueba2.data.EventQuery;
import cl.anpetrus.prueba2.models.Event;
import cl.anpetrus.prueba2.utils.ImageUtil;
import cl.anpetrus.prueba2.views.main.MainActivityFragment;

public class EventActivity extends AppCompatActivity {

    ImageView imageIv;
    boolean imageZoom;
    FloatingActionButton fabZoomFake;

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
        imageZoom = true;
        TextView nameTv = (TextView) findViewById(R.id.detailNameTv);
        TextView descriptionTv = (TextView) findViewById(R.id.detailDescriptionTv);
        TextView dateStartTv = (TextView) findViewById(R.id.detailDateStartTv);
        TextView timeStartTv = (TextView) findViewById(R.id.detailTimeStartTv);
        imageIv = (ImageView) findViewById(R.id.detailImageIv);
        fabZoomFake = (FloatingActionButton) findViewById(R.id.fabZoomFake);
        fabZoomFake.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorBlack)));

        long idEvent = getIntent().getLongExtra(MainActivityFragment.EVENT_ID,0L);
        Event event = new EventQuery().get(idEvent);

        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageZoom) {
                    ViewGroup.LayoutParams params = imageIv.getLayoutParams();
                    params.height = LinearLayout.LayoutParams.MATCH_PARENT;
                    params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    imageIv.setLayoutParams(params);
                    fabZoomFake.setImageResource(R.mipmap.ic_zoom_out_white_24dp);
                    imageZoom=false;
                }else{
                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int pixels = (int) (200 * metrics.density + 0.5f);
                    ViewGroup.LayoutParams params = imageIv.getLayoutParams();
                    params.height = pixels;
                    params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    imageIv.setLayoutParams(params);
                    imageIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    fabZoomFake.setImageResource(R.mipmap.ic_zoom_in_white_24dp);
                    imageZoom = true;
                }
                }
        });

        getSupportActionBar().setTitle("Evento");

        try {
            imageIv.setImageBitmap(ImageUtil.convertByteArrayToBitmap(event.getImage()));
        } catch (Exception e) {
            Toast.makeText(this, "Problemas al cargar poster", Toast.LENGTH_LONG).show();
        }

        nameTv.setText(event.getName());
        descriptionTv.setText(event.getDescription());

        String dateString = new SimpleDateFormat("dd-MM-yyyy").format(event.getStart());
        String timeString = new SimpleDateFormat("HH:mm").format(event.getStart()) + " Hrs.";

        dateStartTv.setText(dateString);
        timeStartTv.setText(timeString);
    }

}
