package timeout.slang.com.ui.loading;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import timeout.slang.com.R;
import timeout.slang.com.ui.FragmentBase;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSplash extends Fragment implements Animation.AnimationListener {

    /* ------------------------------------------------------------------------------------------
     * Constants
     * ------------------------------------------------------------------------------------------ */

    /**
     * Should be used when pushing this fragment onto the stack
     */
    public static final String TAG = "FragmentSplash";

    /**
     * Minimum showtime of 2seconds
     */
    private long MINIMUM_SHOWTIME = 2000;

    /**
     * 1s long animation
     */
    private long ANIMATION_DURATION = 1000;

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------ */

    /**
     * The top (TimeOut) part of our loading view
     */
    private View mTop;

    /**
     * The bottom (London) part of our loading view
     */
    private View mBottom;

    /**
     * Top animation
     */
    private Animation mTopAnimation;

    /**
     * Bottom animation
     */
    private Animation mBottomAnimation;

    /**
     * Earliest time this view can finish
     */
    private long mMinimumEndTime = -1;

    /**
     * Set if its been requested that this view finish
     */
    private boolean mRequestFinish;

    /* ------------------------------------------------------------------------------------------
     * Construction & From FragmentBase
     * ------------------------------------------------------------------------------------------ */

    public FragmentSplash() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loading, container, false);
        mTop = v.findViewById(R.id.top);
        mBottom = v.findViewById(R.id.bottom);

        // Add on predraw listener so we know when the user first sees the fragment
        mBottom.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mBottom.getViewTreeObserver().removeOnPreDrawListener(this);
                setMinimumEndTime();
                return true;
            }
        });

        // Stop touch events getting to underlying screen
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        return v;
    }

    /* ------------------------------------------------------------------------------------------
     * Public Methods
     * ------------------------------------------------------------------------------------------ */

    public synchronized void requestLoadingFragmentFinish() {
        // Data has returned before we've even shown the fragment
        if(mMinimumEndTime == -1) {
            mRequestFinish = true;
        } else {
            startAnimationAndPop();
        }
    }

    /* ------------------------------------------------------------------------------------------
     * From AnimationListener
     * ------------------------------------------------------------------------------------------ */

    @Override
    public void onAnimationStart(Animation animation) { }
    @Override
    public void onAnimationRepeat(Animation animation) { }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation == mTopAnimation) {
            mTop.setVisibility(View.INVISIBLE);

        } else if(animation == mBottomAnimation) {
            mBottom.setVisibility(View.INVISIBLE);
        }

        // If both animations have finished then remove this fragment
        if(mTop.getVisibility() == View.INVISIBLE && mBottom.getVisibility() == View.INVISIBLE) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(this);
            ft.commit();
        }
    }

    /* ------------------------------------------------------------------------------------------
     * Private Methods
     * ------------------------------------------------------------------------------------------ */

    /**
     * Sets the minimum time at which this fragment can finish. Synchronized in case
     * requestLoadingFragmentFinish is called off Main thread
     */
    private synchronized void setMinimumEndTime() {
        mMinimumEndTime = System.currentTimeMillis() + MINIMUM_SHOWTIME;
        if (mRequestFinish == true) {
            startAnimationAndPop();
        }
    }

    private void startAnimationAndPop() {
        (new AsyncStartAnimation()).execute();
    }

    private class AsyncStartAnimation extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Sleep for required time
            long sleepTime = mMinimumEndTime - System.currentTimeMillis();
            if(sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch(Exception e){}
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Create top sliding animation
            int offscreenTop = -1*(mTop.getMeasuredHeight());
            mTopAnimation = createAndSetYAnimation( mTop, offscreenTop, FragmentSplash.this );

            // Create bottom sliding animation
            int offscreenBottom = mBottom.getMeasuredHeight();
            mBottomAnimation = createAndSetYAnimation(mBottom, offscreenBottom, FragmentSplash.this );

            // Start both the animations
            mTop.startAnimation(mTopAnimation);
            mBottom.startAnimation(mBottomAnimation);
        }

        private Animation createAndSetYAnimation(View v, int newY, Animation.AnimationListener listener) {
            TranslateAnimation anim = new TranslateAnimation(0, 0, 0, newY );
            anim.setDuration(ANIMATION_DURATION);
            v.setAnimation(anim);
            anim.setAnimationListener(listener);
            return anim;
        }
    }
}
