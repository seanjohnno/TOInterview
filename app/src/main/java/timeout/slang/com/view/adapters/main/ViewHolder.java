package timeout.slang.com.view.adapters.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import timeout.slang.com.R;
import timeout.slang.com.TOApp;
import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.view.views.ViewTOImage;

/**
 * Standard Android ViewHolder pattern/classes. One for each type of View in the RecyclerView
 */
public class ViewHolder {

    /* ------------------------------------------------------------------------------------------
     * ViewHolder for section title
     * ------------------------------------------------------------------------------------------ */

    public static class TitleViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleText;

        public TitleViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.list_title_item, parent, false));
            mTitleText = (TextView)itemView.findViewById(R.id.title_text);
        }

        public TextView getTextView() {
            return mTitleText;
        }
    }

    /* ------------------------------------------------------------------------------------------
     * ViewHolder for image spanning single row
     * ------------------------------------------------------------------------------------------ */

    public static class SingleImgViewHolder extends RecyclerView.ViewHolder {

        private ViewTOImage mImgView;
        private TextView mTitle;

        public SingleImgViewHolder(Context context, TOCategoryItem.AspectRatio aspectRatio) {
            super(View.inflate(context, R.layout.list_single_item, null));
            mImgView = (ViewTOImage)itemView.findViewById(R.id.single_img);
            mImgView.setAspectRatio(aspectRatio);

            // I don't like this because it's tied to TOApp...
            TOApp.getInstance().inject(mImgView);

            mTitle = (TextView)itemView.findViewById(R.id.img_title);
        }

        public ViewTOImage getImage() {
            return mImgView;
        }

        public TextView getTextView() {
            return mTitle;
        }
    }

    /* ------------------------------------------------------------------------------------------
     * ViewHolder for row with 2 images
     * ------------------------------------------------------------------------------------------ */

    public static class DoubleImgViewHolder extends RecyclerView.ViewHolder {

        private ViewTOImage mLhsImgView;
        private ViewTOImage mRhsImgView;
        private TextView mLhsTitle;
        private TextView mRhsTitle;

        public DoubleImgViewHolder(Context context, TOCategoryItem.AspectRatio aspectRatio) {
            super(View.inflate(context, R.layout.list_double_item, null));

            mLhsImgView = (ViewTOImage)itemView.findViewById(R.id.img_lhs);
            mLhsImgView.setAspectRatio(aspectRatio);
            TOApp.getInstance().inject(mLhsImgView); // I don't like this because it's tied to TOApp...

            mRhsImgView = (ViewTOImage)itemView.findViewById(R.id.img_rhs);
            mRhsImgView.setAspectRatio(aspectRatio);
            TOApp.getInstance().inject(mRhsImgView); // I don't like this because it's tied to TOApp...

            mLhsTitle = (TextView)itemView.findViewById(R.id.img_lhs_title);
            mRhsTitle = (TextView)itemView.findViewById(R.id.img_rhs_title);
        }

        public ViewTOImage getLhsImgView() {
            return mLhsImgView;
        }

        public ViewTOImage getmRhsImgView() {
            return mRhsImgView;
        }

        public TextView getLhsTextView() {
            return mLhsTitle;
        }

        public TextView getRhsTextView() {
            return mRhsTitle;
        }
    }
}
