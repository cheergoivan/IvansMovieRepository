package io.github.cheergoivan.settings.settingValuePredicate;

import java.util.function.Predicate;

public class TruePredicate implements Predicate<Object>{

	@Override
	public boolean test(Object t) {
		return true;
	}
}
