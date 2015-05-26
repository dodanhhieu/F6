package com.vn.hm.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class CalendarUtility {

    public static ArrayList<CalendarEvent> readCalendarEvent(Context context) {
	ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();
	Uri uri = Uri.parse(getCalendarUriBase(context) + "events");
	String[] projection = new String[] { "calendar_id", "title",
		"description", "dtstart", "dtend", "eventLocation" };
	Cursor cursor = context.getContentResolver().query(uri, projection,
		null, null, null);
	cursor.moveToFirst();

	// fetching calendars name
	String CNames[] = new String[cursor.getCount()];
	// fetching calendars id
	for (int i = 0; i < CNames.length; i++) {
	    CalendarEvent event = new CalendarEvent();
	    event.setTitle(cursor.getString(1));
	    event.setDesc(cursor.getString(2));
	    event.setTimeStart(cursor.getLong(3));
	    event.setTimeEnd(cursor.getLong(4));
	    event.setDateStart(getDate(cursor.getLong(3)));
	    CNames[i] = cursor.getString(1);
	    cursor.moveToNext();
	    events.add(event);
	}
	return events;
    }

    public static void addEvent(Context context, String tile, String desc,
	    long timeStart, long timeEnd) {
	// get calendar
	Uri EVENTS_URI = Uri.parse(getCalendarUriBase(context) + "events");
	ContentResolver cr = context.getContentResolver();

	// event insert
	ContentValues values = new ContentValues();
	values.put("calendar_id", 2);
	values.put("title", tile);
	values.put("description", desc);
	values.put("allDay", 0);
	values.put("dtstart", timeStart);
	values.put("dtend", timeEnd);
	values.put("eventTimezone", TimeZone.getDefault().getDisplayName());
	// values.put("visibility", 0);
	values.put("hasAlarm", 1);
	cr.insert(EVENTS_URI, values);

	// Set alarm
	AlarmManager am = (AlarmManager) context
		.getSystemService(Context.ALARM_SERVICE);
	Intent i = new Intent(context, AlarmRecerver.class);
	i.putExtra("title", tile);
	i.putExtra("desc", desc);
	PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
	am.set(AlarmManager.RTC_WAKEUP, timeStart, pi);
    }

    public static void deleteEvent(Context context, String title, String desc,
	    long timeStart) {
	int i = context.getContentResolver().delete(
		Uri.parse(getCalendarUriBase(context) + "events"),
		"calendar_id=? and title =? and description=? and dtstart=? ",
		new String[] { "2", title, desc, String.valueOf(timeStart) });


	Log.d("AAA", "delete: " + i);
	
    }

    @SuppressLint("SimpleDateFormat")
    private static String getDate(long milliSeconds) {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(milliSeconds);
	return formatter.format(calendar.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    private static String getTime(long milliSeconds) {
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(milliSeconds);
	return formatter.format(calendar.getTime());
    }

    private static String getCalendarUriBase(Context context) {
	String calendarUriBase = null;
	Uri calendars = Uri.parse("content://calendar/calendars");
	Cursor managedCursor = null;
	try {
	    managedCursor = context.getContentResolver().query(calendars, null,
		    null, null, null);
	} catch (Exception e) {
	    // eat
	}

	if (managedCursor != null) {
	    calendarUriBase = "content://calendar/";
	} else {
	    calendars = Uri.parse("content://com.android.calendar/calendars");
	    try {
		managedCursor = context.getContentResolver().query(calendars,
			null, null, null, null);
	    } catch (Exception e) {
		// eat
	    }

	    if (managedCursor != null) {
		calendarUriBase = "content://com.android.calendar/";
	    }
	}
	return calendarUriBase;
    }
}
