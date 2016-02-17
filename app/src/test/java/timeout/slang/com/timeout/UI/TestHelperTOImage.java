package timeout.slang.com.timeout.UI;

import org.junit.Assert;
import org.junit.Test;

import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.view.helper.HelperTOImage;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public class TestHelperTOImage {

    /**
     * 4:3 image sizes

     private static final int[][] IMAGE_4_3_SIZE = {
     {1372, 1029},  {1024, 768}, {800, 600}, {750, 562}, {710, 533}, {690, 517}, {670, 572},
     {650, 487}, {640, 480}, {630, 472}, {617, 463}, {600, 450}, {580, 435}, {560, 420},
     {540, 405}, {520, 390}, {500, 375}, {480, 360}, {460, 345}, {440, 330}, {420, 315},
     {400, 300}, {380, 285}, {350, 263}, {330, 247}, {320, 240}, {303, 227}, {275, 206},
     {160, 120}
     };


     * 16:9 image sizes

     private static final int[][] IMAGE_16_9_SIZE = {
     {1372, 772}, {1024, 576}, {800, 450}, {750, 422}, {710, 399}, {690, 388}, {670, 377},
     {640, 360}, {650, 366}, {630, 354}, {617, 347}, {600, 338}, {580, 326}, {560, 315},
     {540, 304}, {520, 293}, {500, 281}, {480, 270}, {460, 259}, {440, 248}, {420, 236},
     {400, 225}, {380, 214}, {350, 197}, {330, 186}, {320, 180}, {303, 170}, {275, 155},
     {250, 141}, {225, 127}, {185, 104}, {160, 90}
     };
     */

    public static final String URL = "https://media.timeout.com/images/103122427/{width}/{height}/image.jpg";

    private static int[] Test43 = { 580, 435 };

    private static int[] Test169 = { 630, 354 };

    @Test
    public void testNull() {
        HelperTOImage toImage = new HelperTOImage();
        toImage.getImageRequestStr(null, 0, TOCategoryItem.AspectRatio.Ratio_4_3);
    }

    @Test
    public void testEmpty() {
        HelperTOImage toImage = new HelperTOImage();
        String ret = toImage.getImageRequestStr("", 0, TOCategoryItem.AspectRatio.Ratio_4_3);
        Assert.assertEquals("", ret);
    }

    @Test
    public void testUknownAspectRatio() {
        HelperTOImage toImage = new HelperTOImage();
        String ret = toImage.getImageRequestStr(URL, 0, TOCategoryItem.AspectRatio.Unknown);
        Assert.assertEquals(URL, ret);
    }

    @Test
    public void testNear169() {
        HelperTOImage toImage = new HelperTOImage();
        String ret = toImage.getImageRequestStr(URL, 625, TOCategoryItem.AspectRatio.Ratio_16_9);
        Assert.assertEquals(create(Test169), ret);
    }

    @Test
    public void testNear43() {
        HelperTOImage toImage = new HelperTOImage();
        String ret = toImage.getImageRequestStr(URL, 571, TOCategoryItem.AspectRatio.Ratio_4_3);
        Assert.assertEquals(create(Test43), ret);
    }

    @Test
    public void testExact169() {
        HelperTOImage toImage = new HelperTOImage();
        String ret = toImage.getImageRequestStr(URL, 630, TOCategoryItem.AspectRatio.Ratio_16_9);
        Assert.assertEquals(create(Test169), ret);
    }

    @Test
    public void testEact43() {
        HelperTOImage toImage = new HelperTOImage();
        String ret = toImage.getImageRequestStr(URL, 580, TOCategoryItem.AspectRatio.Ratio_4_3);
        Assert.assertEquals(create(Test43), ret);
    }



    private String create(int[] wh) {
        return URL.replace("{height}", ""+wh[1]).replace("{width}", ""+wh[0]);
    }
}
