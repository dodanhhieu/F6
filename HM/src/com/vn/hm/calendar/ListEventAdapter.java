package com.vn.hm.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vn.hm.R;

public class ListEventAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CalendarEvent> events;

    private class ViewHolder {
	private TextView txtTime, txtTitle, txtDesc;
    }

    public ListEventAdapter(Context context, ArrayList<CalendarEvent> data) {
	this.context = context;
	this.events = data;
    }

    @Override
    public int getCount() {
	return events.size();
    }

    @Override
    public CalendarEvent getItem(int position) {
	return events.get(position);
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	View v = convertView;
	ViewHolder holder = null;
	if (v == null) {
	    holder = new ViewHolder();
	    LayoutInflater inflater = LayoutInflater.from(context);
	    v = inflater.inflate(R.layout.item_list_event, null);
	    v.setTag(holder);
	} else {
	    holder = (ViewHolder) v.getTag();
	}
	CalendarEvent event = events.get(position);
	holder.txtTime = (TextView) v.findViewById(R.id.event_txtTime);
	holder.txtTitle = (TextView) v.findViewById(R.id.event_txtTitle);
	holder.txtDesc = (TextView) v.findViewById(R.id.event_txtDesc);
	holder.txtTime.setText(getDate(event.getTimeStart()));
	holder.txtTitle.setText(event.getTitle());
	holder.txtDesc.setText(event.getDesc());
	return v;
    }

    @SuppressLint("SimpleDateFormat")
    private String getDate(long milliSeconds) {
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(milliSeconds);
	return formatter.format(calendar.getTime());
    }

}
