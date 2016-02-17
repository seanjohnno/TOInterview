package timeout.slang.com.timeout.model;

import junit.framework.Assert;

import org.junit.Test;

import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.model.helper.HelperHTMLSanitizer;

public class TestTOCategoryItem {

    @Test
    public void testHandlesNull() {
        TOCategoryItem item = getCategoryItem();
        item.setCategoryName(null);
        Assert.assertNull(item.getCategoryName());
    }

    @Test
    public void testHandlesEmpty() {
        TOCategoryItem item = getCategoryItem();
        item.setCategoryName("");
        Assert.assertEquals("", item.getCategoryName());
    }

    @Test
    public void testLeaveItBe() {
        String leaveMeBe = "This string should be unedited";
        TOCategoryItem item = getCategoryItem();
        item.setCategoryName(leaveMeBe);
        Assert.assertEquals(leaveMeBe, item.getCategoryName());
    }

    @Test
    public void testSanitize() {
        String leaveMeBe = "This &#039;string&#039; should be unedited";
        TOCategoryItem item = getCategoryItem();
        item.setCategoryName(leaveMeBe);
        Assert.assertEquals("This 'string' should be unedited", item.getCategoryName());
    }

    @Test
    public void testNullLink() {
        TOCategoryItem item = getCategoryItem();
        item.setLink(null);
        Assert.assertNull(item.getLink());
    }

    @Test
    public void testBlankLink() {
        TOCategoryItem item = getCategoryItem();
        item.setLink("");
        Assert.assertEquals("", item.getLink());
    }

    @Test
    public void testProperLink() {
        String url = "http://www.google.co.uk";
        TOCategoryItem item = getCategoryItem();
        item.setLink(url);
        Assert.assertEquals(url, item.getLink());
    }

    @Test
    public void testNullImage() {
        TOCategoryItem item = getCategoryItem();
        item.setCategoryImage(null);
        Assert.assertNull(item.getCategoryImage());
    }

    @Test
    public void testBlankImage() {
        TOCategoryItem item = getCategoryItem();
        item.setCategoryImage("");
        Assert.assertEquals("", item.getCategoryImage());
    }

    @Test
    public void testProperImage() {
        String url = "http://www.google.co.uk/someimageurl{width}x{height}";
        TOCategoryItem item = getCategoryItem();
        item.setCategoryImage(url);
        Assert.assertEquals(url, item.getCategoryImage());
    }

    @Test
    public void testAspectRatio() {
        TOCategoryItem item = getCategoryItem();
        Assert.assertEquals(TOCategoryItem.AspectRatio.Unknown, item.getAspectRatio());
        item.setAspectRatio(TOCategoryItem.AspectRatio.Ratio_4_3);
        Assert.assertEquals(TOCategoryItem.AspectRatio.Ratio_4_3, item.getAspectRatio());
        item.setAspectRatio(TOCategoryItem.AspectRatio.Ratio_16_9);
        Assert.assertEquals(TOCategoryItem.AspectRatio.Ratio_16_9, item.getAspectRatio());
    }

    @Test
    public void testStringAspectRatio() {
        TOCategoryItem item = getCategoryItem();
        Assert.assertEquals(TOCategoryItem.AspectRatio.Unknown, item.getAspectRatio());

        item.setAspectRatio((String) null);
        Assert.assertEquals(TOCategoryItem.AspectRatio.Unknown, item.getAspectRatio());

        item.setAspectRatio(TOCategoryItem.RATIO_16_9);
        Assert.assertEquals(TOCategoryItem.AspectRatio.Ratio_16_9, item.getAspectRatio());

        item.setAspectRatio(TOCategoryItem.RATIO_4_3);
        Assert.assertEquals(TOCategoryItem.AspectRatio.Ratio_4_3, item.getAspectRatio());

        item.setAspectRatio("hdshsj");
        Assert.assertEquals(TOCategoryItem.AspectRatio.Unknown, item.getAspectRatio());
    }


    protected TOCategoryItem getCategoryItem() {
        return new TOCategoryItem(getSanitizer());
    }

    protected HelperHTMLSanitizer getSanitizer() {
        return new HelperHTMLSanitizer();
    }
}
