package com.dti.cornell.events.utils;

import com.dti.cornell.events.models.Event;
import com.dti.cornell.events.models.Organization;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Data
{
	public static Map<Integer, Event> eventForID = new HashMap<>();
	public static Map<Integer, String> tagForID = new HashMap<>();
	public static Map<Integer, Organization> organizationForID = new HashMap<>();
	public static DateTime selectedDate;

	public static final int NUM_DAYS_IN_FEED = 30;
	public static final ImmutableList<DateTime> DATES;

	static
	{
		DateTime now = DateTime.now();
		List<DateTime> dates = new ArrayList<>(NUM_DAYS_IN_FEED);
		for (int i = 0; i < NUM_DAYS_IN_FEED; i++)
			dates.add(now.plusDays(i));
		DATES = ImmutableList.copyOf(dates);

		selectedDate = now;
	}

	public static void createDummyData()
	{
		//TODO remove when API is available
		tagForID.put(1, "#Tag1");
		tagForID.put(2, "#davidLovesHashtags!");
		tagForID.put(3, "#halp");
		tagForID.put(4, "#kornell");

		Organization organization = new Organization(1, "Cornell Design & Tech Initiative and more stuff blah blah blah", "Hi there howdy do yall. This is an extremely long, grandiloquent, pontificate description designed for the sole singular purpose of testing limits of descriptions", 1, ImmutableList.of(1, 2, 3, 4, 5, 6, 7), ImmutableList.of("User id"), ImmutableList.of(1,2,3,4), "www.google.com", "dc788@cornell.edu");
		organizationForID.put(organization.id, organization);

		for (int i = 1; i <= DATES.size(); i++)
		{
			Event event = new Event(i, DATES.get(i-1), DATES.get(i-1).plusDays(i).plusMinutes(i*30), "I’m just trying to see what it would look like if there was like an extra long title blah", "The simple yet courageous #metoo hashtag campaign has emerged as a rallying cry for people everywhere who have survived sexual assault and sexual harassment", "Goldwin Smith Hall Room 202", "ChIJndqRYRqC0IkR9J8bgk3mDvU", ImmutableList.of("User id"), 1, 1, ImmutableList.of(1, 2, 3, 4));
			eventForID.put(i, event);
		}
	}
	public static List<Event> events()
	{
		List<Event> events = Lists.newArrayList(eventForID.values());
		Collections.sort(events);
		return events;
	}

	public static boolean equalsSelectedDate(DateTime dateTime)
	{
		return DateTimeComparator.getDateOnlyInstance().compare(dateTime, selectedDate) == 0;
	}
}
