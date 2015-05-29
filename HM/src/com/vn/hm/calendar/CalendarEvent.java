package com.vn.hm.calendar;

public class CalendarEvent {
    private int id;
    private long timeStart, timeEnd;
    private String title, desc, dateStart;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public long getTimeStart() {
	return timeStart;
    }

    public void setTimeStart(long timeStart) {
	this.timeStart = timeStart;
    }

    public long getTimeEnd() {
	return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
	this.timeEnd = timeEnd;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

    public String getDateStart() {
	return dateStart;
    }

    public void setDateStart(String dateStart) {
	this.dateStart = dateStart;
    }

}
