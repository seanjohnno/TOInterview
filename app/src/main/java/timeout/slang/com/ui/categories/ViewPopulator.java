package timeout.slang.com.ui.categories;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import timeout.slang.com.IFragmentController;
import timeout.slang.com.model.dataobjects.TOCategoryItem;

/**
 * Strategy pattern. Map 1-1 with ViewHolders so each can populate the View/ViewHolder data
 * Hold data required to populate i.e. a section title populator will hold the title String
 */
public class ViewPopulator {

    /* ------------------------------------------------------------------------------------------
     * Constants
     * ------------------------------------------------------------------------------------------ */

    // int IDs to identify each type of view
    public static final int VIEW_TYPE_TITLE             = 1;
    public static final int VIEW_TYPE_SINGLE_IMG_4_3    = 2;
    public static final int VIEW_TYPE_SINGLE_IMG_16_9   = 3;
    public static final int VIEW_TYPE_DOUBLE_IMG_4_3    = 4;
    public static final int VIEW_TYPE_DOUBLE_IMG_16_9   = 5;

    /* ------------------------------------------------------------------------------------------
     * IViewPopulator
     * ------------------------------------------------------------------------------------------ */

    // Abstract class that each ViewPopulator implements
    public interface IViewPopulator {

        /**
         * @return  one of the VIEW_TYPE_* constants
         */
        public int getType();

        /**
         * Called by AdapterCategories
         * @param viewHolder    ViewHolder and View items to populate
         */
        public void populateView(RecyclerView.ViewHolder viewHolder);
    }

    /* ------------------------------------------------------------------------------------------
     * Populator for section title
     * ------------------------------------------------------------------------------------------ */

    public static class TitlePopulator implements IViewPopulator {

        private String mTitle;

        private ICategorySelectListener mListener;

        public TitlePopulator(String title, ICategorySelectListener listener) {
            mTitle = title;
            mListener = listener;
        }

        public void populateView(RecyclerView.ViewHolder viewHolder){
            ((ViewHolder.TitleViewHolder)viewHolder).getTextView().setText(mTitle);
        }

        public int getType() {
            return VIEW_TYPE_TITLE;
        }
    }

    /* ------------------------------------------------------------------------------------------
     * Populator for single row image
     * ------------------------------------------------------------------------------------------ */

    public static class SingleImagePopulator implements IViewPopulator, View.OnClickListener {

        private TOCategoryItem mItem;

        private ICategorySelectListener mListener;

        public SingleImagePopulator(TOCategoryItem item, ICategorySelectListener listener) {
            mItem = item;
            mListener = listener;
        }

        public void populateView(RecyclerView.ViewHolder viewHolder){
            ViewHolder.SingleImgViewHolder vh = ((ViewHolder.SingleImgViewHolder)viewHolder);
            vh.getTextView().setText(mItem.getCategoryName());
            vh.getImage().setImageUrl(mItem.getCategoryImage());

            vh.getImage().setOnClickListener(this);
        }

        @Override
        public int getType() {
            return mItem.getAspectRatio() == TOCategoryItem.AspectRatio.Ratio_4_3 ?  VIEW_TYPE_SINGLE_IMG_4_3 : VIEW_TYPE_SINGLE_IMG_16_9;
        }

        @Override
        public void onClick(View v) {
            mListener.handleCategorySelect(mItem.getLink());
        }
    }

    /* ------------------------------------------------------------------------------------------
     * Populator for 2 image row
     * ------------------------------------------------------------------------------------------ */

    public static class DoubleImagePopulator implements IViewPopulator {

        private TOCategoryItem mLhs;

        private TOCategoryItem mRhs;

        private ICategorySelectListener mListener;

        public DoubleImagePopulator(TOCategoryItem lhs, TOCategoryItem rhs, ICategorySelectListener listener) {
            mLhs = lhs;
            mRhs = rhs;
            mListener = listener;
        }

        public void populateView(RecyclerView.ViewHolder viewHolder){
            ViewHolder.DoubleImgViewHolder vh = ((ViewHolder.DoubleImgViewHolder)viewHolder);
            vh.getLhsTextView().setText(mLhs.getCategoryName());
            vh.getRhsTextView().setText(mRhs.getCategoryName());

            vh.getLhsImgView().setImageUrl(mLhs.getCategoryImage());
            vh.getmRhsImgView().setImageUrl(mRhs.getCategoryImage());

            // Not a fan of the casts but alternatives are setting a singleton or having a reference
            // in every list item
            vh.getLhsImgView().setOnClickListener(new View.OnClickListener() { public void onClick(View v) {
                mListener.handleCategorySelect(mLhs.getLink());
            } } );
            vh.getmRhsImgView().setOnClickListener(new View.OnClickListener() { public void onClick(View v) {
                mListener.handleCategorySelect(mLhs.getLink());
            } } );
        }

        @Override
        public int getType() {
            return mLhs.getAspectRatio() == TOCategoryItem.AspectRatio.Ratio_4_3 ?  VIEW_TYPE_DOUBLE_IMG_4_3 : VIEW_TYPE_DOUBLE_IMG_16_9;
        }
    }
}
