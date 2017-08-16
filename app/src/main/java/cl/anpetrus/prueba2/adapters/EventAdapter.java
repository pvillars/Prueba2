package cl.anpetrus.prueba2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cl.anpetrus.prueba2.R;
import cl.anpetrus.prueba2.models.Event;

/**
 * Created by Petrus on 12-08-2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> events;
    private ClickListener listener;

    public EventAdapter(List<Event> events, ClickListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Event event = events.get(position);
        holder.nameTv.setText(event.getName());
        holder.startDateTv.setText(event.getStart().toString());
        holder.eventRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.startDetailEventById(event.getId());
            }
        });
    }

    public void addEvent(Event event){
        events.add(event);
        event.save();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nameTv;
        private TextView startDateTv;
        private RelativeLayout eventRl;


        public ViewHolder(View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.nameTv);
            startDateTv = itemView.findViewById(R.id.startDateTv);
            eventRl = itemView.findViewById(R.id.eventRl);
        }
    }
}
