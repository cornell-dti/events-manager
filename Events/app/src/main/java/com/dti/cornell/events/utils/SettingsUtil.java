package com.dti.cornell.events.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dti.cornell.events.models.Event;
import com.dti.cornell.events.models.Organization;
import com.dti.cornell.events.models.Tag;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SettingsUtil
{
	public static SettingsUtil SINGLETON;
	private final SharedPreferences settings;
	private final Context context;

	//keys
	private static final String TIMESTAMP = "timestamp";
	private static final String EVENTS = "events";
	private static final String ORGANIZATIONS = "organizations";
	private static final String FIRST_RUN = "firstRun";
	private static final String NAME = "name";
	private static final String EMAIL = "email";
	private static final String TOKEN = "token";
	private static final String IMAGE_URL = "imageURL";

	private static String token = "";

	private SettingsUtil(Context context)
	{
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		this.context = context;
	}

	public static void createSingleton(Context context)
	{
		if (SINGLETON == null)
			SINGLETON = new SettingsUtil(context);
	}

	public String getTimestamp()
	{
		//default = oldest possible time
		return settings.getString(TIMESTAMP, Internet.addTToTimestamp(new DateTime(0).toString(Internet.TIME_FORMAT_NO_T)));
	}
	public void setTimestamp(String timestamp)
	{
		settings.edit()
				.putString(TIMESTAMP, timestamp)
				.apply();
	}

	public Map<Integer, Event> getEvents()
	{
		Set<String> eventStrings = settings.getStringSet(EVENTS, Collections.<String>emptySet());
		Map<Integer, Event> events = new HashMap<>(eventStrings.size());
		for (String eventString : eventStrings)
		{
			Event event = Event.fromString(eventString);
			events.put(event.id, event);
		}
		return events;
	}

	public void setEvents(Map<Integer, Event> events)
	{
		setStringSet(new HashSet<>(events.values()), EVENTS);
	}

	public Map<Integer, Organization> getOrganizations()
	{
		Set<String> orgStrings = settings.getStringSet(ORGANIZATIONS, Collections.<String>emptySet());
		Map<Integer, Organization> organizations = new HashMap<>(orgStrings.size());
		for (String orgString : orgStrings)
		{
			Organization org = Organization.fromString(orgString);
			organizations.put(org.id, org);
		}
		return organizations;
	}

	public void setOrganizations(Map<Integer, Organization> organizations)
	{
		setStringSet(new HashSet<>(organizations.values()), ORGANIZATIONS);
	}

	public boolean getFirstRun()
	{
		return settings.getBoolean(FIRST_RUN, true);
	}

	public void setFirstRun() {
		settings.edit()
			.putBoolean(FIRST_RUN, false)
			.apply();
	}

	public void resetFirstRun() {
		settings.edit()
				.putBoolean(FIRST_RUN, true)
				.apply();
	}

	public String getName()
	{
		return settings.getString(NAME, "");
	}

	public void setName(String name)
	{
		settings.edit()
				.putString(NAME, name)
				.apply();
	}

	public String getEmail()
	{
		return settings.getString(EMAIL, "");
	}

	public void setEmail(String email)
	{
		settings.edit()
				.putString(EMAIL, email)
				.apply();
	}

	public String getToken()
	{
		return token;
		//return settings.getString(TOKEN, "");
	}
	public void setToken(String token)
	{
		SettingsUtil.token = token;
		settings.edit()
				.putString(TOKEN, token)
				.apply();
	}

	public String getImageUrl()
	{
		return settings.getString(IMAGE_URL, "");
	}

	public void setImageUrl(String imageUrl)
	{
		settings.edit()
				.putString(IMAGE_URL, imageUrl)
				.apply();
	}

	private void setStringSet(Set<?> set, String key)
	{
		Set<String> strings = new HashSet<>(set.size());
		for (Object o : set)
			strings.add(o.toString());

		settings.edit()
				.putStringSet(key, strings)
				.apply();
	}







	public void loadTags(){
		//String toBeDecoded = PreferenceManager.getDefaultSharedPreferences(context).getString("TAG_STRING", "");
		SharedPreferences sp = context.getSharedPreferences("TAGS", Context.MODE_PRIVATE);
		String toBeDecoded = sp.getString("TAG_STRING", "DEFAULT");
		Log.e("TAG STRING ENCODED", toBeDecoded);
		if(toBeDecoded.equalsIgnoreCase("DEFAULT")){
			Log.e("SettingsUtil Tags", "Tags are null!");
			toBeDecoded = "";
		}
		TagUtil.tagIDsAndImportance = TagUtil.decodeTagIDs(toBeDecoded);
		TagUtil.tagsInterested = new ArrayList<>();
		for(Integer eventID : TagUtil.tagIDsAndImportance.keySet()){
			TagUtil.tagsInterested.add(eventID);
		}

		Set<String> allTags = sp.getStringSet("TAGS_ALL", new HashSet<String>());
		for(String tag : allTags){
			TagUtil.allTags.add(Tag.fromString(tag));
		}
		if(!allTags.isEmpty()){
			System.out.println(TagUtil.allTags.get(0));
		}

		TagUtil.tagsLoaded = true;
	}

	public void loadOrganizations(){
		SharedPreferences sp = context.getSharedPreferences("ORGS", Context.MODE_PRIVATE);
		String toBeDecoded = sp.getString("ORGANIZATION_STRING", "DEFAULT");
		Log.e("ORG STRING ENCODED", toBeDecoded);
		if(toBeDecoded.equalsIgnoreCase("DEFAULT")){
			Log.e("loadOrgs", "TOBEDECODED IS NULL");
			toBeDecoded = "";
		}
		OrganizationUtil.followedOrganizations = OrganizationUtil.decodeTagIDs(toBeDecoded);
		OrganizationUtil.organizationsLoaded = true;
	}

	public void loadAttendance(){
		//String toBeDecoded = PreferenceManager.getDefaultSharedPreferences(context).getString("ATTENDANCE_STRING", "");
		SharedPreferences sp = context.getSharedPreferences("ATTENDANCE", Context.MODE_PRIVATE);
		String toBeDecoded = sp.getString("ATTENDANCE_STRING", "DEFAULT");
		Log.e("ATT STRING ENCODED", toBeDecoded);
		if(toBeDecoded.equalsIgnoreCase("DEFAULT")){
			Log.e("loadAttendance", "TOBEDECODED IS NULL");
			toBeDecoded = "";
		}
//		EventUtil.interestedEvents = EventUtil.decodeEventIDs(toBeDecoded, ATTENDANCE.INTERESTED);
//		EventUtil.goingEvents = EventUtil.decodeEventIDs(toBeDecoded, ATTENDANCE.GOING);
		EventUtil.allAttendanceEvents = EventUtil.decodeEventIDs(toBeDecoded);
		EventUtil.removeDuplicates();
		EventUtil.attendanceLoaded = true;
	}

	public void saveTags(){
		SharedPreferences.Editor e = context.getSharedPreferences("TAGS", Context.MODE_PRIVATE).edit();
		e.putString("TAG_LIKES", TagUtil.encodeTagIDs());
		Set<String> tags = new HashSet<>();
		for(Tag tag : TagUtil.allTags){
			tags.add(tag.toString());
		}
		e.putStringSet("TAGS_ALL", tags);
		e.commit();
		//PreferenceManager.getDefaultSharedPreferences(context).edit().putString("TAG_STRING", TagUtil.encodeTagIDs()).commit();
	}

	public void saveFollowedOrganizations(){
//		SharedPreferences settings;
//		settings = context.getSharedPreferences("ORGANIZATION_STRING", Context.MODE_PRIVATE);
//		settings.edit().putString(OrganizationUtil.encodeOrganizationIDs(), null).apply();
		Log.e("OrgEncodeCheck", OrganizationUtil.encodeOrganizationIDs());
		SharedPreferences.Editor e = context.getSharedPreferences("ORGS", Context.MODE_PRIVATE).edit();
		e.putString("ORGANIZATION_STRING", OrganizationUtil.encodeOrganizationIDs());
		e.commit();
	}

	public void saveAttendance(){
		Log.e("EventEncodeCheck", EventUtil.encodeEventIDs());
		SharedPreferences.Editor e = context.getSharedPreferences("ATTENDANCE", Context.MODE_PRIVATE).edit();
		e.putString("ATTENDANCE_STRING", EventUtil.encodeEventIDs());
		e.commit();
		//PreferenceManager.getDefaultSharedPreferences(context).edit().putString("ATTENDANCE_STRING", EventUtil.encodeEventIDs()).commit();
	}

}
