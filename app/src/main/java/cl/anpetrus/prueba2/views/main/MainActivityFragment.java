package cl.anpetrus.prueba2.views.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.anpetrus.prueba2.R;
import cl.anpetrus.prueba2.adapters.ClickListener;
import cl.anpetrus.prueba2.adapters.EventAdapter;
import cl.anpetrus.prueba2.data.EventQuery;
import cl.anpetrus.prueba2.models.Event;
import cl.anpetrus.prueba2.views.details.EventActivity;

public class MainActivityFragment extends Fragment implements ClickListener {

    public static final String EVENT_ID = "cl.anpetrus.prueba2.KEY.EVENT_ID";
    private EventAdapter eventAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EventQuery eventQuery;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        eventQuery = new EventQuery();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.eventsRv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        eventAdapter = new EventAdapter(new EventQuery().getNext(), this);
        recyclerView.setAdapter(eventAdapter);

        swipeRefreshLayout = view.findViewById(R.id.reloadSr);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventAdapter.reloadEvents(eventQuery.getNext());
                swipeRefreshLayout.setRefreshing(false);
               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },800);*/
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        eventAdapter.reloadEvents(eventQuery.getNext());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void startDetailEventById(Long id) {
        Intent intent = new Intent(getContext(), EventActivity.class);
        intent.putExtra(EVENT_ID, id);
        startActivity(intent);
    }

    public void addToAdatperList(Event event) {
        eventAdapter.addEvent(event);
    }
}