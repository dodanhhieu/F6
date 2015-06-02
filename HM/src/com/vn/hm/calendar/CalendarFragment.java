package com.vn.hm.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vn.hm.R;
import com.vn.hm.calendar.ListEventAdapter.ItemClickListener;

public class CalendarFragment extends Fragment implements OnClickListener,
	OnItemClickListener {
    private TextView title;
    private RelativeLayout previous, next;
    private ListView lvEvent;
    private GridView gridview;

    private GregorianCalendar month, itemmonth;// calendar instances.
    private CalendarAdapter adapter;// adapter instance
    private Handler handler;// for grabbing some event values for showing the
			    // dot
			    // marker.
    private ArrayList<String> items; // container to store calendar items which
				     // needs showing the event marker
    private ArrayList<CalendarEvent> events, dayEvent;
    private ListEventAdapter eventAdapter;

    private ItemClickListener ItemClickListener = new ItemClickListener() {

	@Override
	public void onDelete(int position) {
	    // delete in db
	    CalendarEvent event = dayEvent.get(position);
	    CalendarUtility.deleteEvent(getActivity(), event.getId());

	    // update calendar view
	    events.remove(event);
	    adapter.notifyDataSetChanged();
	    handler.post(calendarUpdater);

	    // update list event
	    dayEvent.remove(position);
	    eventAdapter.notifyDataSetChanged();
	    
	}
    };

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_calendar, null);
	Locale.setDefault(Locale.US);

	month = (GregorianCalendar) GregorianCalendar.getInstance();
	itemmonth = (GregorianCalendar) month.clone();

	items = new ArrayList<String>();
	adapter = new CalendarAdapter(getActivity(), month);

	gridview = (GridView) v.findViewById(R.id.gridview);
	gridview.setAdapter(adapter);

	handler = new Handler();
	handler.post(calendarUpdater);

	events = CalendarUtility.readCalendarEvent(getActivity());
	dayEvent = new ArrayList<CalendarEvent>();
	eventAdapter = new ListEventAdapter(getActivity(), dayEvent,
		ItemClickListener);
	lvEvent = (ListView) v.findViewById(R.id.listEvent);
	lvEvent.setAdapter(eventAdapter);
	for (int i = 0; i < events.size(); i++) {
	    if (events.get(i).getDateStart().equals(adapter.curentDateString)) {
		dayEvent.add(events.get(i));
	    }
	}

	title = (TextView) v.findViewById(R.id.title);
	title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

	previous = (RelativeLayout) v.findViewById(R.id.previous);
	previous.setOnClickListener(this);

	next = (RelativeLayout) v.findViewById(R.id.next);
	next.setOnClickListener(this);

	gridview.setOnItemClickListener(this);
	return v;
    }

    protected void setNextMonth() {
	if (month.get(GregorianCalendar.MONTH) == month
		.getActualMaximum(GregorianCalendar.MONTH)) {
	    month.set((month.get(GregorianCalendar.YEAR) + 1),
		    month.getActualMinimum(GregorianCalendar.MONTH), 1);
	} else {
	    month.set(GregorianCalendar.MONTH,
		    month.get(GregorianCalendar.MONTH) + 1);
	}

    }

    protected void setPreviousMonth() {
	if (month.get(GregorianCalendar.MONTH) == month
		.getActualMinimum(GregorianCalendar.MONTH)) {
	    month.set((month.get(GregorianCalendar.YEAR) - 1),
		    month.getActualMaximum(GregorianCalendar.MONTH), 1);
	} else {
	    month.set(GregorianCalendar.MONTH,
		    month.get(GregorianCalendar.MONTH) - 1);
	}

    }

    protected void showToast(String string) {
	Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();

    }

    public void refreshCalendar() {
	adapter.refreshDays();
	adapter.notifyDataSetChanged();
	handler.post(calendarUpdater); // generate some calendar items
	title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }

    public Runnable calendarUpdater = new Runnable() {

	@Override
	public void run() {
	    items.clear();

	    // Print dates of the current week
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	    String itemvalue;

	    for (int i = 0; i < events.size(); i++) {
		itemvalue = df.format(itemmonth.getTime());
		itemmonth.add(GregorianCalendar.DATE, 1);
		items.add(events.get(i).getDateStart().toString());
	    }
	    adapter.setItems(items);
	    adapter.notifyDataSetChanged();
	}
    };

    @Override
    public void onClick(View v) {
	if (v == previous) {
	    setPreviousMonth();
	    refreshCalendar();
	} else if (v == next) {
	    setNextMonth();
	    refreshCalendar();
	}
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id) {
	((CalendarAdapter) parent.getAdapter()).setSelected(view);
	String selectedGridDate = CalendarAdapter.dayString.get(position);
	String[] separatedTime = selectedGridDate.split("-");
	// taking last part of date. ie; 2 from 2012-12-02.
	String gridvalueString = separatedTime[2].replaceFirst("^0*", "");
	int gridvalue = Integer.parseInt(gridvalueString);
	// navigate to next or previous month on clicking offdays.
	if ((gridvalue > 10) && (position < 8)) {
	    setPreviousMonth();
	    refreshCalendar();
	} else if ((gridvalue < 7) && (position > 28)) {
	    setNextMonth();
	    refreshCalendar();
	}
	((CalendarAdapter) parent.getAdapter()).setSelected(view);

	dayEvent.clear();
	for (int i = 0; i < events.size(); i++) {
	    if (events.get(i).getDateStart().equals(selectedGridDate)) {
		dayEvent.add(events.get(i));
	    }
	}
	eventAdapter.notifyDataSetChanged();
    }
}
