package com.getmyschool.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getmyschool.common.contant.Constant;

public class DateUtils {

	private Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * Return LocalDateTime in Asia timezone.
	 * 
	 * @return
	 */
	public static LocalDateTime getAsiaLocalDateTime() {
		return LocalDateTime.now(ZoneId.of(Constant.TIMEZONE_ASIA));
	}

	public static String getAsiaLocalDateTimeInCustomFormat() {

		String formatDateTime = getAsiaLocalDateTime().format(DateTimeFormatter.ofPattern(Constant.DATABASE_DB_FORAMT));
		return formatDateTime;
	}

	public static String getDateByString(LocalDateTime dateString) {

		String returnString = dateString
				.format(DateTimeFormatter.ofPattern(com.getmyschool.common.contant.Constant.DATABASE_DB_FORAMT));
		return returnString;

	}

	public static void main(String args[]) {
		LocalDateTime vv = getAsiaLocalDateTime();
		System.out.println("Before " + vv);
		vv = vv.minusMinutes(15);
		System.out.println("After: " + vv);

		System.out.println(DateUtils.getAsiaLocalDateTimeInCustomFormat());

		// System.out.println(TimeUnit.MINUTES.toSeconds(15));
	}

	/**
	 * get dayName by given date.
	 * 
	 * @param date
	 * @return
	 */
	public static String getDayNameByDate(String date) {
		// Parse the given date string into a LocalDate object
		LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);

		// Get the day name for the given date
		String dayName = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

		return dayName;
	}
}
