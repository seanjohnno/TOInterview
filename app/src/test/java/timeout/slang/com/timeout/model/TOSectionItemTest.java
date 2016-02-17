package timeout.slang.com.timeout.model;

import junit.framework.Assert;

import org.junit.Test;

import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.model.helper.HelperHTMLSanitizer;

public class TOSectionItemTest {

    @Test
    public void testHandlesNull() {
        TOSection section = getSectionItem(null);
        Assert.assertNull(section.getTitle());
    }

    @Test
    public void testHandlesEmpty() {
        TOSection section = getSectionItem("");
        Assert.assertEquals("", section.getTitle());
    }

    @Test
    public void testLeaveItBe() {
        String leaveMeBe = "This string should be unedited";
        TOSection section = getSectionItem(leaveMeBe);
        Assert.assertEquals(section.getTitle(), leaveMeBe);
    }

    @Test
    public void testSanitize() {
        String sanitize = "This string &#039;should&#039; be edited";
        TOSection section = getSectionItem(sanitize);
        Assert.assertEquals(section.getTitle(), "This string 'should' be edited");
    }

    @Test
    public void testAddItem() {
        TOSection section = getSectionItem("");
        Assert.assertEquals(section.getCategoryItems().size(), 0);
        section.addItem(getCategoryItem());
        Assert.assertEquals(section.getCategoryItems().size(), 1);
        section.addItem(getCategoryItem());
        Assert.assertEquals(section.getCategoryItems().size(), 2);
    }

    @Test
    public void testAddNull() {
        TOSection section = getSectionItem("");
        try {
            section.addItem(null);
        } catch(Exception e) {
            Assert.fail("Exception");
        }
    }

    @Test
    public void testNullDoesntGrow() {
        TOSection section = getSectionItem("");
        section.addItem(null);
        Assert.assertEquals(section.getCategoryItems().size(), 0);
    }

    protected TOCategoryItem getCategoryItem() {
        return new TOCategoryItem(getSanitizer());
    }

    protected TOSection getSectionItem(String title) {
        return new TOSection(title, getSanitizer());
    }

    protected HelperHTMLSanitizer getSanitizer() {
        return new HelperHTMLSanitizer();
    }
}
