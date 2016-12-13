package io.github.cheergoivan.settings;

import java.util.function.Consumer;
import java.util.function.Predicate;

import io.github.cheergoivan.settings.settingAction.DefaultSettingAction;
import io.github.cheergoivan.settings.settingAction.SetLocalRepository;
import io.github.cheergoivan.settings.settingValuePredicate.AssertPositiveInteger;
import io.github.cheergoivan.settings.settingValuePredicate.AssertString;

public enum Settings {

	remoteRepository(""),username("",false),password("",false),localRepository("",new SetLocalRepository()),
	webPageTemplate(""),webPageTitle(""),moviesPerPage(7,true,new AssertPositiveInteger(),new DefaultSettingAction());


	private Object value;
	/**
	 * whether the setting is displayed to user
	 */
	private boolean visible;
	private Predicate<String> assertValueType;
	private Consumer<Object> settingAction;
	
	Settings(Object value){
		this.value=value;
		this.visible=true;
		this.assertValueType=new AssertString();
		this.settingAction=new DefaultSettingAction();
	}
	
	Settings(Object value,Consumer<Object> settingAction){
		this.value=value;
		this.visible=true;
		this.assertValueType=new AssertString();
		this.settingAction=settingAction;
	}
	
	Settings(Object value,boolean visible){
		this.value=value;
		this.visible=visible;
		this.assertValueType=new AssertString();
		this.settingAction=new DefaultSettingAction();
	}
	
	Settings(Object value,boolean visible,Predicate<String> assertValueType,Consumer<Object> settingAction) {
		this.value = value;
		this.visible=visible;
		this.assertValueType=assertValueType;
		this.settingAction=settingAction;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public Predicate<String> getAssertValueType() {
		return assertValueType;
	}
	
	public Consumer<Object> getSettingAction() {
		return settingAction;
	}
	
	public String getValueAsString(){
		return (String)value;
	}
	
	public int getValueAsInteger(){
		return Integer.parseInt((String) value);
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
	
	public boolean isEmpty(){
		return value==null||value.equals("");
	}
}
