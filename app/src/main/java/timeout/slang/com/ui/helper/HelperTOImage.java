package timeout.slang.com.ui.helper;

import timeout.slang.com.model.dataobjects.TOCategoryItem;

/**
 * Created by MrLenovo on 16/02/2016.
 */
public class HelperTOImage {

    /* ------------------------------------------------------------------------------------------
     * Constants
     * ------------------------------------------------------------------------------------------*/

    /**
     * 4:3 image sizes
     * Taken from: http://dist.timeout.com/dist/23fe5c8d752510943954812746c613a2552994c6/js/main.js
     */
    private static final int[][] IMAGE_4_3_SIZE = {
            {1372, 1029},  {1024, 768}, {800, 600}, {750, 562}, {710, 533}, {690, 517}, {670, 572},
            {650, 487}, {640, 480}, {630, 472}, {617, 463}, {600, 450}, {580, 435}, {560, 420},
            {540, 405}, {520, 390}, {500, 375}, {480, 360}, {460, 345}, {440, 330}, {420, 315},
            {400, 300}, {380, 285}, {350, 263}, {330, 247}, {320, 240}, {303, 227}, {275, 206},
            {160, 120}
    };

    /**
     * 16:9 image sizes
     * Taken from: http://dist.timeout.com/dist/23fe5c8d752510943954812746c613a2552994c6/js/main.js
     */
    private static final int[][] IMAGE_16_9_SIZE = {
            {1372, 772}, {1024, 576}, {800, 450}, {750, 422}, {710, 399}, {690, 388}, {670, 377},
            {640, 360}, {650, 366}, {630, 354}, {617, 347}, {600, 338}, {580, 326}, {560, 315},
            {540, 304}, {520, 293}, {500, 281}, {480, 270}, {460, 259}, {440, 248}, {420, 236},
            {400, 225}, {380, 214}, {350, 197}, {330, 186}, {320, 180}, {303, 170}, {275, 155},
            {250, 141}, {225, 127}, {185, 104}, {160, 90}
    };

    /**
     * Used to replace {width} in the url (as it comes from the server)
     */
    private static final String REPLACE_WIDTH = "{width}";

    /**
     * Used to replace {height} in the url (as it comes from the server)
     */
    private static final String REPLACE_HEIGHT = "{height}";

    /* ------------------------------------------------------------------------------------------
     * Construction + Methods
     * ------------------------------------------------------------------------------------------*/

    public HelperTOImage() {
    }

    public String getImageRequestStr(String str, int width, TOCategoryItem.AspectRatio ratio) {
        if(str == null || ratio == TOCategoryItem.AspectRatio.Unknown) return str;

        int[] correctSize = getCorrectSize(width, ratio);
        str = str.replace( REPLACE_WIDTH, ""+correctSize[0] );
        str = str.replace( REPLACE_HEIGHT, ""+correctSize[1] );
        return str;
    }

    /**
     * Returns the best fit image from the width provided. Best fit is a width exactly equal to or
     * slightly larger
     * @param width     Width of our control
     * @param ratio     Aspect ratio of the image
     * @return          The width and height (w=ret[0],h=ret[1]) to use
     */
    private int[] getCorrectSize(int width, TOCategoryItem.AspectRatio ratio) {
        int[][] spec = ratio == TOCategoryItem.AspectRatio.Ratio_4_3 ? IMAGE_4_3_SIZE : IMAGE_16_9_SIZE;
        for(int i = 0; i < spec.length; i++) {
            int[] wh = spec[i];
            if(width == wh[0]) {
                return spec[i];
            } else if(width > wh[0]) {
                return spec[ i == 0 ? 0 : i - 1 ];
            }
        }
        return spec[spec.length-1];
    }
}
