package com.dti.cornell.events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dti.cornell.events.models.CardList;

import java.util.Collections;
import java.util.List;

public class CardSectionAdapter extends RecyclerView.Adapter<AbstractCardListViewHolder>
{
	private static final String TAG = CardSectionAdapter.class.getSimpleName();
	private final LayoutInflater inflater;
	private boolean hasSeeMore = false;
	private List<CardList> data = Collections.emptyList();

	public CardSectionAdapter(Context context)
	{
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public AbstractCardListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		switch (viewType)
		{
			case 0:
				return new CardListViewHolder(inflater.inflate(R.layout.item_card_list, parent, false));
			case 1:
				return new SeeMoreViewHolder(inflater.inflate(R.layout.item_see_more, parent, false));
			default:
				Log.e(TAG, "onCreateViewHolder: Invalid view type found: " + viewType);
				return null;
		}
	}

	@Override
	public void onBindViewHolder(AbstractCardListViewHolder holder, int position)
	{
		if (getItemViewType(position) == 0)
			((CardListViewHolder) holder).configure(data.get(position));
	}

	@Override
	public int getItemCount()
	{
		return data.size() + (hasSeeMore ? 1 : 0);
	}

	@Override
	public int getItemViewType(int position)
	{
		return position == data.size() ? 1 : 0;
	}

	public void setData(List<CardList> data, boolean hasSeeMore)
	{
		this.data = data;
		this.hasSeeMore = hasSeeMore;
		notifyDataSetChanged();
	}
}
