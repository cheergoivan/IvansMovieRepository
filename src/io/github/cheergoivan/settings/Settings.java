package io.github.cheergoivan.settings;

import java.util.function.Consumer;
import java.util.function.Predicate;

import io.github.cheergoivan.settings.settingAction.DefaultSettingAction;
import io.github.cheergoivan.settings.settingAction.SetLocalRepository;
import io.github.cheergoivan.settings.settingAction.SetWebPageTheme;
import io.github.cheergoivan.settings.settingValuePredicate.TruePredicate;
import io.github.cheergoivan.settings.settingValuePredicate.WebPageThemePredicate;
import io.github.cheergoivan.settings.settingValuePredicate.LocalRepositoryPredicate;

public enum Settings {

	remoteRepository(""),
	
	username("",false),
	
	password("",false),
	
	localRepository("",true,LocalRepositoryPredicate.class,SetLocalRepository.class),
	
	webPageTheme(GlobalSettings.defaultTheme,true,WebPageThemePredicate.class,SetWebPageTheme.class),
	
	webPageTitle("");
	
	private Object value;
	/**
	 * whether the setting is displayed to user
	 */
	private boolean visible;
	private Class<? extends Predicate<Object>> valuePredicate;
	private Class<? extends Consumer<Object>> settingAction;
	
	
	Settings(Object value){
		this.value=value;
		this.visible=true;
		this.valuePredicate=TruePredicate.class;
		this.settingAction=DefaultSettingAction.class;
	}
	
	Settings(Object value,boolean visible){
		this.value=value;
		this.visible=visible;
		this.valuePredicate=TruePredicate.class;
		this.settingAction=DefaultSettingAction.class;
	}
	
	Settings(Object value,boolean visible,Class<? extends Predicate<Object>> valuePredicate,Class<? extends Consumer<Object>> settingAction) {
		this.value = value;
		this.visible=visible;
		this.valuePredicate=valuePredicate;
		this.settingAction=settingAction;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public Predicate<Object> getValuePredicate() {
		try {
			return valuePredicate.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return new TruePredicate();
	}
	
	public Consumer<Object> getSettingAction() {
		try {
			return settingAction.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return new DefaultSettingAction();
	}
	
	public String getValueAsString(){
		return String.valueOf(value);
	}
	
	public int getValueAsInteger(){
		return Integer.parseInt(getValueAsString());
	}

	/**
	 * transfer string type to Setting type
	 * @param s
	 * @return
	 */
	public static Settings getSetting(String s){
		for(Settings setting:Settings.values()){
			if(setting.name().equalsIgnoreCase(s)){
				return setting;
			}
		}
		return null;
	}
	
	public String toString(){
		return name()+":"+value;
	}
	
	public boolean isSetted(){
		return value!=null&&!getValueAsString().equals("");
	}
}
