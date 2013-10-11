package de.andrena.kickercam.goal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GoalId {
	private final long id;
	private final Date date;
	private final String filename;

	public GoalId(Date date, long id) {
		this.id = id;
		this.date = date;
		this.filename = new SimpleDateFormat("yyyyMMMdd_HHmmss-SSS", Locale.US).format(date) + ".mp4";
	}

	public Date getDate() {
		return date;
	}

	public String getFilename() {
		return filename;
	}

	public String getTitle() {
		return "#" + id + " - " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
	}
}
