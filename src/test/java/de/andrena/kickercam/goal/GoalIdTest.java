package de.andrena.kickercam.goal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class GoalIdTest {
	private static final int ID = 17;
	private static final int DAY = 14;
	private static final int MONTH = 10;
	private static final int YEAR = 2013;
	private GoalId goalId;

	@Before
	public void setUp() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(YEAR, MONTH - 1, DAY);
		Date date = calendar.getTime();
		goalId = new GoalId(date, ID);
	}

	@Test
	public void getFilename_HasCorrectDate() throws Exception {
		assertThat(goalId.getFilename(), is("2013Oct14_000000-000.mp4"));
	}

	@Test
	public void getSubtitleFilename_HasCorrectDate() throws Exception {
		assertThat(goalId.getSubtitleFilename(), is("2013Oct14_000000-000.srt"));
	}

	@Test
	public void getTitle_HasCorrectDate() throws Exception {
		assertThat(goalId.getTitle(), is("#17 - 14.10.2013 00:00:00"));
	}

}
