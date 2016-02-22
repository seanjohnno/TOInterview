package timeout.slang.com.ui.categories;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrLenovo on 22/02/2016.
 */
public class ViewModelCategories implements Parcelable {

    /* ------------------------------------------------------------------------------------------
     * Construction & From FragmentBase
     * ------------------------------------------------------------------------------------------ */

    public static class CategoryLvl implements Parcelable {

        private String mUrl;

        private boolean mRequestMade;

        private boolean mDataReceived;

        public CategoryLvl(String url) {
            mUrl = url;
        }

        public CategoryLvl(Parcel p) {
            mUrl = p.readString();
            mRequestMade = p.readByte() == 1 ? true : false;
        }

        public String getUrl() {
            return mUrl;
        }

        public boolean isRequestMade() {
            return mRequestMade;
        }

        public void setRequestMade() {
            mRequestMade = true;
        }

        public boolean isDataReceived() {
            return mDataReceived;
        }

        public void setDataReceived() {
            mDataReceived = true;
        }

        private void reset() {
            mDataReceived = false;
            mRequestMade = false;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mUrl);
            dest.writeByte((byte) (mRequestMade ? 1 : 0));
            dest.writeByte((byte)(mDataReceived ? 1 : 0));
        }

        public static final Parcelable.Creator<CategoryLvl> CREATOR
                = new Parcelable.Creator<CategoryLvl>() {
            public CategoryLvl createFromParcel(Parcel in) {
                return new CategoryLvl(in);
            }

            public CategoryLvl[] newArray(int size) {
                return new CategoryLvl[size];
            }
        };
    }

    /* ------------------------------------------------------------------------------------------
     * Construction & From FragmentBase
     * ------------------------------------------------------------------------------------------ */

    private List<CategoryLvl> mCategoryLvls;

    private boolean mServedInitialContent;

    public ViewModelCategories() {
        mCategoryLvls = new ArrayList<>();
    }

    public ViewModelCategories(Parcel p) {
        p.readTypedList(mCategoryLvls, CategoryLvl.CREATOR);
    }

    public CategoryLvl push(String url) {
        CategoryLvl lvl = new CategoryLvl(url);
        mCategoryLvls.add(lvl);
        return lvl;
    }

    public CategoryLvl peek() {
        return mCategoryLvls.isEmpty() ? null : mCategoryLvls.get(mCategoryLvls.size() - 1);
    }

    public CategoryLvl pop() {
        CategoryLvl retLvl = mCategoryLvls.isEmpty() ? null : mCategoryLvls.remove(mCategoryLvls.size() - 1);

        CategoryLvl oneBack = peek();
        if(oneBack != null) {
            oneBack.reset();
        }

        return retLvl;
    }

    public boolean isInitialContent() {
        return mCategoryLvls.size() == 1 && !mCategoryLvls.get(mCategoryLvls.size() - 1).mDataReceived;
    }

    public void creatingFragment() {
        CategoryLvl lvl = peek();
        if(lvl != null) {
            lvl.reset();
        }
    }

    /* ------------------------------------------------------------------------------------------
     * From Parcelable
     * ------------------------------------------------------------------------------------------ */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mCategoryLvls);
    }

    public static final Parcelable.Creator<ViewModelCategories> CREATOR
            = new Parcelable.Creator<ViewModelCategories>() {
        public ViewModelCategories createFromParcel(Parcel in) {
            return new ViewModelCategories(in);
        }

        public ViewModelCategories[] newArray(int size) {
            return new ViewModelCategories[size];
        }
    };
}
