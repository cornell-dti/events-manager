package com.dti.cornell.events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dti.cornell.events.utils.EventUtils;

public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
	private final Context context;
	private final TextView startTime;
	private final TextView endTime;
	private final TextView title;
	private final TextView location;
	private final TextView friendsGoing;
	private Event event;

	public EventViewHolder(View itemView)
	{
		super(itemView);
		startTime = itemView.findViewById(R.id.startTime);
		endTime = itemView.findViewById(R.id.endTime);
		title = itemView.findViewById(R.id.eventTitle);
		location = itemView.findViewById(R.id.eventLocation);
		friendsGoing = itemView.findViewById(R.id.friendsGoing);

		itemView.setOnClickListener(this);
		context = itemView.getContext();
	}

	public void configure(Event event)
	{
		this.event = event;
		startTime.setText(EventUtils.getDateTimeStringHHmm(event.startTime));
		endTime.setText(EventUtils.getDateTimeStringHHmm(event.endTime));
		title.setText(event.title);
		location.setText(event.location);
		friendsGoing.setText(event.friendsGoing);
	}

	@Override
	public void onClick(View view)
	{
		DetailsActivity.startWithEvent(event, context);
	}
}
