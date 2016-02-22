package timeout.slang.com.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.ui.helper.HelperTOImage;

/**
 * Extends ImageView, sizes image based on width available then setting the height at the correct
 * aspect ratio so the downloaded image looks right
 */
public class ViewTOImage extends ImageView {

    /* ------------------------------------------------------------------------------------------
     * Static
     * ------------------------------------------------------------------------------------------*/

    public static final String LOCAL_RSC = "local://";

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------*/

    /**
     * Used to grab correct sized image
     * ps I don't like that you have to make this non-private for Dagger, bleh
     */
    @Inject HelperTOImage mTOImageHelper;

    /**
     * Url to be used to download image after measure
     */
    private String mPendingImg;

    private OnClickListener mStoredOnClickListener;

    /**
     * Aspect ratio of this TimeOut image
     */
    private TOCategoryItem.AspectRatio mAspectRatio = TOCategoryItem.AspectRatio.Ratio_4_3;

    /* ------------------------------------------------------------------------------------------
     * Construction
     * ------------------------------------------------------------------------------------------*/

    public ViewTOImage(Context context) {
        super(context);
    }

    public ViewTOImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewTOImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* ------------------------------------------------------------------------------------------
     * Public Methods
     * ------------------------------------------------------------------------------------------*/

    /**
     * Set the aspect ratio of this image
     * @param aspectRatio   4:3 or 16:9
     */
    public void setAspectRatio(TOCategoryItem.AspectRatio aspectRatio) {
        mAspectRatio = aspectRatio;
    }

    /**
     * Set the image url (synchronized just in case this isn't called from main thread and it's run
     * in tandem with onMeasure)
     * @param str       Contains {width} & {height} instead of values
     */
    public synchronized void setImageUrl(String str) {
        // If we've already been measure then make the image request
        if(getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
            requestImage(str);

        // Otherwise save the url for use after onMeasure
        } else {
            mPendingImg = str;
        }
    }

    /* ------------------------------------------------------------------------------------------
     * Protected Methods
     * ------------------------------------------------------------------------------------------*/

    /**
     * Setting onClick in the ViewHolder and the onClick method is never getting called. Workaround...
     * @param l     OnClickListener to call when the image is clicked
     */
    @Override
    public void setOnClickListener(OnClickListener l) {
        mStoredOnClickListener = l;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Get the available width
        int maxSize = MeasureSpec.getSize(widthMeasureSpec);
        int w = MeasureSpec.makeMeasureSpec(maxSize, MeasureSpec.EXACTLY);

        // Check the aspect ratio and set the height accordingly
        int h;
        if(mAspectRatio == TOCategoryItem.AspectRatio.Ratio_4_3) {
            h = MeasureSpec.makeMeasureSpec((int) Math.round((maxSize / 4) * 3), MeasureSpec.EXACTLY);
        } else {
            h = MeasureSpec.makeMeasureSpec((int) Math.round((maxSize / 16) * 9), MeasureSpec.EXACTLY);
        }
        super.onMeasure(w, h);

        // If we have a pending image then load it
        if(mPendingImg != null) {
            requestImage(mPendingImg);
            mPendingImg = null;
        }
    }

    /**
     * Uses Picasso to grab the image and wipes away the mPendingImg
     */
    protected void requestImage(String url) {
        if(url != null) {
            if(url.startsWith(LOCAL_RSC)) {
                int id = Integer.parseInt(url.substring(LOCAL_RSC.length()));
                Picasso.with(getContext()).load(id).into(this);

            } else {
                String imgUrl = mTOImageHelper.getImageRequestStr(url, getMeasuredWidth(), mAspectRatio);
                Picasso.with(getContext()).load(imgUrl).into(this);

            }

            // Setting onClick in the ViewHolder and the onClick method isn't getting called. Setting it
            // here works however, so passing the click on
            super.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (mStoredOnClickListener != null) {
                        mStoredOnClickListener.onClick(v);
                    }
                }
            });
        }
    }
}
