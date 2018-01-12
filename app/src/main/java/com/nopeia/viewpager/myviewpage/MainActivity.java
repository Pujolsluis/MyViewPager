package com.nopeia.viewpager.myviewpage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nopeia.viewpager.myviewpage.dummy.DummyContent;

import org.w3c.dom.Text;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName() ;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ArrayList<Integer> mLayouts;

    private ArrayList<TextView> mDots;

    private LinearLayout mDotsLayout;

    private Button mNextButton;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mLayouts = new ArrayList<>();
        mLayouts.add(R.layout.activity_screen_1);
        mLayouts.add(R.layout.activity_screen_2);
        mLayouts.add(R.layout.activity_screen_3);
        mLayouts.add(R.layout.activity_screen_4);
        mLayouts.add(R.layout.activity_screen_5);


        final IntroManager introManager = new IntroManager(MainActivity.this);

//        if(!introManager.Check()) {
//
//            introManager.setFirst(false);
//            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//            startActivity(intent);
//            finish();
//
//        }

        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_main);

        mNextButton = findViewById(R.id.view_pager_next_button);
        mDotsLayout = findViewById(R.id.view_page_dots_linear_layout);
        addBottomDots(0);
        changeStatusBarColor();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(viewListener);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = getItem(1);

                if(currentItem < mLayouts.size()) {
                    mViewPager.setCurrentItem(currentItem);
                } else {
                    introManager.setFirst(false);
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ArrayList<Integer> mLayouts;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * The fragment argument representing the layout array for this
         * fragment
         */
        private static final String ARG_LAYOUTS_ARRAY ="layouts_array";

        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(ArrayList<Integer> layouts, int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putIntegerArrayList(ARG_LAYOUTS_ARRAY, layouts);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int screenPos = getArguments().getInt(ARG_SECTION_NUMBER);
            mLayouts = getArguments().getIntegerArrayList(ARG_LAYOUTS_ARRAY);

            View rootView = inflater.inflate(mLayouts.get(screenPos), container, false);

            return rootView;
        }
    }

    private void addBottomDots(int position) {

        mDots = new ArrayList<>(mLayouts.size());
        Log.d(TAG, "AMOUNT OF TEXT VIEWS: " + mDotsLayout.getChildCount());
        int[] colorActive = getResources().getIntArray(R.array.dot_active);
        int[] colorInactive = getResources().getIntArray(R.array.dot_inactive);

        mDotsLayout.removeAllViews();
        for(int i=0; i < mLayouts.size(); i++) {
            mDots.add(i, new TextView(MainActivity.this));
//            mDots.get(i).setText(Html.fromHtml("&#8226;"));
            mDots.get(i).setText(Html.fromHtml("&#8226;"));
            mDots.get(i).setTextSize(35);
            mDots.get(i).setTextColor(colorInactive[position]);
            mDots.get(i).setElevation(2);
            mDotsLayout.addView(mDots.get(i));
        }

        Log.d(TAG, "AMOUNT OF TEXT VIEWS: " + mDotsLayout.getChildCount());
        if(mDots.size() > 0) {
            mDots.get(position).setTextColor(colorActive[position]);
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if(position == mLayouts.size() - 1) {
                mNextButton.setText("PROCEED");
            } else {
                mNextButton.setText("NEXT");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) { super(fm); }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(mLayouts, position);
        }

        @Override
        public int getCount() {
            return mLayouts.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            PlaceholderFragment fragment = (PlaceholderFragment) obj;
            return view == fragment.getView();
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (position < getCount()) {
                FragmentManager manager = ((Fragment) object).getFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove((Fragment) object);
                trans.commit();
            }
        }
    }
}

