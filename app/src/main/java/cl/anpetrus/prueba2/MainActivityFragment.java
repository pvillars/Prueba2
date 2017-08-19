package cl.anpetrus.prueba2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.anpetrus.prueba2.adapters.ClickListener;
import cl.anpetrus.prueba2.adapters.EventAdapter;
import cl.anpetrus.prueba2.data.EventQuery;
import cl.anpetrus.prueba2.models.Event;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ClickListener{

    public static final String EVENT_ID = "cl.anpetrus.prueba2.KEY.EVENT_ID";
    private EventAdapter eventAdapter;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.eventsRv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        eventAdapter = new EventAdapter(new EventQuery().getNext(),this);
        recyclerView.setAdapter(eventAdapter);
    }

    @Override
    public void clicked() {
    }

    @Override
    public void startDetailEventById(Long id) {
        Intent intent= new Intent(getContext(),EventActivity.class);
        intent.putExtra(EVENT_ID,id);
        startActivity(intent);
    }

    public void addToAdatperList(Event event){
        eventAdapter.addEvent(event);
    }
}
