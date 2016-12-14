package io.github.cheergoivan.settings.settingValuePredicate;

import java.util.function.Predicate;

public class PositiveNumberPredicate implements Predicate<Object>{

	@Override
	public boolean test(Object t) {
		return ((String)t).matches("[1-9][0-9]*");
	}

}
