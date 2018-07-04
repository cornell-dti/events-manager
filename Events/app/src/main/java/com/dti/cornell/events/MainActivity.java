package com.dti.cornell.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.danlew.android.joda.JodaTimeAndroid;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		JodaTimeAndroid.init(this);

		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		BottomNavigationView tabBar = findViewById(R.id.tabBar);
		tabBar.setOnNavigationItemSelectedListener(this);
		tabBar.setSelectedItemId(R.id.tab_discover);    //select discover page first
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item)
	{
		Fragment fragment;
		switch (item.getItemId())
		{
			case R.id.tab_discover:
				toolbar.setTitle(R.string.tab_discover);
				fragment = new DiscoverFragment();
				break;
			case R.id.tab_for_you:
				toolbar.setTitle(R.string.tab_for_you);
				fragment = new ForYouFragment();
				break;
			case R.id.tab_my_events:
				toolbar.setTitle(R.string.tab_my_events);
				fragment = new MyEventsFragment();
				break;
			case R.id.tab_profile:
				toolbar.setTitle(R.string.tab_profile);
				fragment = new ProfileFragment();
				break;
			default:
				return false;
		}

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragmentContainer, fragment);
		transaction.commit();
		return true;
	}
}