package com.home.app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtils {
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
