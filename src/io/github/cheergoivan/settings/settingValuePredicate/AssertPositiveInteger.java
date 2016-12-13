package io.github.cheergoivan.settings.settingValuePredicate;

import java.util.function.Predicate;

public class AssertPositiveInteger implements Predicate<String>{

	@Override
	public boolean test(String t) {
		return t.matches("[1-9][0-9]*");
	}

}
