package com.getmyschool.common.validator;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

public class CustomValidator {
	private static Logger LOGGER = LoggerFactory.getLogger(CustomValidator.class);

	private static List<String> orderList = Arrays.asList("ASC", "DESC");

	public static boolean isEmpty(@Nullable Object str) {
		return (str == null || "".equals(str.toString().trim()));
	}

	public static boolean hasValidValue(@Nullable Object longObj) {
		return (null == longObj || ((Long) longObj).longValue() <= 0);
	}

	public static boolean hasValidInteger(int value) {
		return value <= 0;
	}

	public static boolean isValidPattern(Pattern pattern, String stringValue) {
		return stringValue != null ? pattern.matcher(stringValue).matches() : false;
	}

	public static boolean isValidPattern(Pattern pattern, BigDecimal stringValue) {
		return stringValue != null ? pattern.matcher(stringValue.toPlainString()).matches() : false;
	}

	public static <T extends Comparable<T>> boolean isBetween(T value, T start, T end) {
		return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
	}

	public static boolean isValueNegative(int value) {
		return value < 0;
	}

	/**
	 * String name = "cxmt ASC,outletName DESC";
	 * 
	 * @param <T>
	 * @param s
	 * @param columeName
	 * @return
	 */
	public static <T> boolean validateSortByColumn(Class<T> s, String columeName) {

		boolean returnFLag = true;
		if (null != columeName) {
			returnFLag = false;
			try {

				String[] elements = columeName.split(",");
				for (String columns : elements) {
					String[] inner = columns.split(" ");
					Field field1 = s.getDeclaredField(inner[0]);
					returnFLag = orderList.contains(inner[1]);
					if (!returnFLag)
						break;
				}

			} catch (NoSuchFieldException | SecurityException e) {
				returnFLag = false;
				LOGGER.info("not able to pase the sort column:" + columeName);
			} catch (NullPointerException e) {
				returnFLag = false;
				LOGGER.info("not able to pase the sort column:" + columeName);
			} catch (Exception e) {
				returnFLag = false;
				LOGGER.info("not able to pase the sort column:" + columeName);
			}
		}
		return returnFLag;
	}
}
