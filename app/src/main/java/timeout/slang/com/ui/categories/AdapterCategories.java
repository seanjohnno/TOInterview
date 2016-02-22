package timeout.slang.com.ui.categories;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.model.dataobjects.TOSection;

/**
 * Created by MrLenovo on 12/02/2016.
 */
public class AdapterCategories extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------ */

    /**
     * List of IViewPopulator - These are used to set the text/images on the ViewHolders. Map 1-1
     * with the amount of list items
     */
    private List<ViewPopulator.IViewPopulator> mViewBuilderList;

    /**
     * Listener to call when an item is selected
     */
    private ICategorySelectListener mSelectListener;

    /* ------------------------------------------------------------------------------------------
     * Constructor & Public Methods
     * ------------------------------------------------------------------------------------------ */

    /**
     * Constructor
     * @param sections     List of Time Out sections from main page
     */
    public AdapterCategories(List<TOSection> sections, ICategorySelectListener listener) {
        mSelectListener = listener;
        mViewBuilderList = createViewPopulators(sections);
    }

    /**
     * Set the data in the event of a re-load
     * @param sections      List of Time Out sections from main page
     */
    public void setData(List<TOSection> sections) {
        mViewBuilderList = createViewPopulators(sections);
        notifyDataSetChanged();
    }

    /* ------------------------------------------------------------------------------------------
     * From RecyclerView.Adapter<RecyclerView.ViewHolder>
     * ------------------------------------------------------------------------------------------ */

    /**
     * Called by RecyclerView, returns type of View/ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case ViewPopulator.VIEW_TYPE_TITLE:
                return new ViewHolder.TitleViewHolder(parent.getContext(), parent);

            case ViewPopulator.VIEW_TYPE_SINGLE_IMG_4_3:
                return new ViewHolder.SingleImgViewHolder(parent.getContext(), TOCategoryItem.AspectRatio.Ratio_4_3);

            case ViewPopulator.VIEW_TYPE_SINGLE_IMG_16_9:
                return new ViewHolder.SingleImgViewHolder(parent.getContext(), TOCategoryItem.AspectRatio.Ratio_16_9);

            case ViewPopulator.VIEW_TYPE_DOUBLE_IMG_4_3:
               return new ViewHolder.DoubleImgViewHolder(parent.getContext(), TOCategoryItem.AspectRatio.Ratio_4_3);

            case ViewPopulator.VIEW_TYPE_DOUBLE_IMG_16_9:
                return new ViewHolder.DoubleImgViewHolder(parent.getContext(), TOCategoryItem.AspectRatio.Ratio_16_9);

            default:
                return null;
        }
    }

    /**
     * Uses and IViewPopulator to take the holder and populate it's images/text properly
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mViewBuilderList.get(position).populateView(holder);
    }

    @Override
    public int getItemCount() {
        return mViewBuilderList.size();
    }

    /**
     * Tells the RecyclerView what time of control is at the supplied position
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mViewBuilderList.get(position).getType();
    }


    /**
     * TODO - Probably want to create these off the UI thread
     * Strategy pattern, have different types of ViewPopulators for different types of view. Map
     * 1-1 with list items
     * @param sections      List of TO sections from main page
     * @return              List of IViewPopulators
     */
    private List<ViewPopulator.IViewPopulator> createViewPopulators(List<TOSection> sections) {
        List<ViewPopulator.IViewPopulator> viewBuilderList = new ArrayList<>();
        if(sections != null) {
            for (TOSection section : sections) {
                if (section.getTitle() != null && section.getTitle().length() > 0) {
                    viewBuilderList.add(new ViewPopulator.TitlePopulator(section.getTitle(), mSelectListener));
                }

                int startIndex = 0;
                if (section.getCategoryItems().size() > 0 && section.getCategoryItems().size() % 2 != 0) {
                    viewBuilderList.add(new ViewPopulator.SingleImagePopulator(section.getCategoryItems().get(0), mSelectListener));
                    startIndex++;
                }
                for (; startIndex < section.getCategoryItems().size(); startIndex += 2) {
                    viewBuilderList.add(new ViewPopulator.DoubleImagePopulator(section.getCategoryItems().get(startIndex), section.getCategoryItems().get(startIndex + 1), mSelectListener));
                }
            }
        }
        return viewBuilderList;
    }
}
