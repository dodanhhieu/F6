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
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;

public class CalendarUtility {

    public static ArrayList<CalendarEvent> readCalendarEvent(Context context) {
	long myCalId = getCalendarId(context);
	ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();
	Uri uri = Uri.parse(getCalendarUriBase(context) + "events");

	String[] projection = new String[] { "calendar_id", "title",
		"description", "dtstart", "dtend", "eventLocation", "_id" };
	Cursor cursor = context.getContentResolver().query(uri, projection,
		"calendar_id = " + myCalId, null, null);
	cursor.moveToFirst();

	// fetching calendars name
	String CNames[] = new String[cursor.getCount()];
	// fetching calendars id
	for (int i = 0; i < CNames.length; i++) {
	    CalendarEvent event = new CalendarEvent();
	    event.setId(cursor.getInt(0));
	    event.setTitle(cursor.getString(1));
	    event.setDesc(cursor.getString(2));
	    event.setTimeStart(cursor.getLong(3));
	    event.setTimeEnd(cursor.getLong(4));
	    event.setDateStart(getDate(cursor.getLong(3)));
	    event.setId(cursor.getInt(6));
	    CNames[i] = cursor.getString(1);
	    cursor.moveToNext();
	    events.add(event);
	}
	return events;
    }

    public static void addEvent(Context context, String title, String desc,
	    long timeStart, long timeEnd) {
	// get calendar
	Uri EVENTS_URI = Uri.parse(getCalendarUriBase(context) + "events");
	ContentResolver cr = context.getContentResolver();

	long myCalId = getCalendarId(context);
	// event insert
	ContentValues values = new ContentValues();

	values.put("calendar_id", 2);

	values.put("title", title);
	values.put("description", desc);
	values.put("allDay", 0);
	values.put("dtstart", timeStart);
	values.put("dtend", timeEnd);
	values.put("eventTimezone", TimeZone.getDefault().getDisplayName());

	values.put("hasAlarm", 1);
	Uri insertUri = cr.insert(EVENTS_URI, values);

	// Set alarm
	AlarmManager am = (AlarmManager) context
		.getSystemService(Context.ALARM_SERVICE);
	Intent i = new Intent(context, AlarmRecerver.class);
	i.putExtra("title", title);
	i.putExtra("desc", desc);
	PendingIntent pi = PendingIntent.getBroadcast(context,
		Integer.valueOf(insertUri.getLastPathSegment()), i, 0);
	am.set(AlarmManager.RTC_WAKEUP, timeStart, pi);
    }

    public static void deleteEvent(Context context, int eventId) {
	context.getContentResolver().delete(
		Uri.parse(getCalendarUriBase(context) + "events"),
		"_id = " + eventId, null);

	// Cancel alarm
	AlarmManager am = (AlarmManager) context
		.getSystemService(Context.ALARM_SERVICE);
	Intent i = new Intent(context, AlarmRecerver.class);
	PendingIntent pi = PendingIntent.getBroadcast(context, eventId, i, 0);
	am.cancel(pi);
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

    @SuppressLint("NewApi")
    private static long getCalendarId(Context context) {
	long calId = 0;
	ContentResolver cr = context.getContentResolver();
	String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
		+ Calendars.ACCOUNT_TYPE + " = ?) AND ("
		+ Calendars.OWNER_ACCOUNT + " = ?))";
	String[] selectionArgs = new String[] { "hieudodanh@gmail.com",
		"com.google", "hieudodanh@gmail.com" };
	final String[] EVENT_PROJECTION = new String[] { Calendars._ID, // 0
		Calendars.ACCOUNT_NAME, // 1
		Calendars.CALENDAR_DISPLAY_NAME, // 2
		Calendars.OWNER_ACCOUNT // 3
	};
	Cursor cur = cr.query(Calendars.CONTENT_URI, EVENT_PROJECTION,
		selection, selectionArgs, null);
	while (cur.moveToNext()) {
	    calId = cur.getLong(0);
	}
	if (calId == 0) {
	    ContentValues values = new ContentValues();
	    values.put(Calendars.NAME, "MyGym");
	    values.put(Calendars.CALENDAR_DISPLAY_NAME, "MyGym");
	    values.put(Calendars.VISIBLE, 1);
	    values.put(Calendars.ACCOUNT_NAME, "hieudodanh@gmail.com");
	    values.put(Calendars.ACCOUNT_TYPE,
		    CalendarContract.ACCOUNT_TYPE_LOCAL);
	    values.put(Calendars.OWNER_ACCOUNT, "hieudodanh@gmail.com");
	    Uri calUri = Calendars.CONTENT_URI;
	    calUri = calUri
		    .buildUpon()
		    .appendQueryParameter(
			    CalendarContract.CALLER_IS_SYNCADAPTER, "true")
		    .appendQueryParameter(
			    CalendarContract.Calendars.ACCOUNT_NAME,
			    "hieudodanh@gmail.com")
		    .appendQueryParameter(
			    CalendarContract.Calendars.ACCOUNT_TYPE,
			    CalendarContract.ACCOUNT_TYPE_LOCAL).build();
	    Uri insertUri = cr.insert(calUri, values);
	    calId = Long.valueOf(insertUri.getLastPathSegment());
	}
	return calId;
    }
}
