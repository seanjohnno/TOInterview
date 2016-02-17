package timeout.slang.com.timeout.model;

import junit.framework.Assert;

import org.junit.Test;

import timeout.slang.com.model.helper.HelperHTMLSanitizer;


public class TestHtmlSanitizer {

    @Test
    public void testHandlesNull() {
        HelperHTMLSanitizer sanitizer = new HelperHTMLSanitizer();
        String test = sanitizer.sanitizeHTML(null);
        Assert.assertNull(test);
    }

    @Test
    public void testHandlesEmpty() {
        HelperHTMLSanitizer sanitizer = new HelperHTMLSanitizer();
        String test = sanitizer.sanitizeHTML("");
        Assert.assertEquals("", test);
    }

    @Test
    public void testLeaveItBe() {
        String leaveMeBe = "This string should be unedited";
        HelperHTMLSanitizer sanitizer = new HelperHTMLSanitizer();
        String test = sanitizer.sanitizeHTML(leaveMeBe);
        Assert.assertEquals(leaveMeBe, test);
    }

    @Test
    public void testAlmost() {
        String leaveMeBe = "This &#string should &#be unedited";
        HelperHTMLSanitizer sanitizer = new HelperHTMLSanitizer();
        String test = sanitizer.sanitizeHTML(leaveMeBe);
        Assert.assertEquals(leaveMeBe, test);
    }

    @Test
    public void testConvert() {
        String editMe = "This &#039; should be edited";
        HelperHTMLSanitizer sanitizer = new HelperHTMLSanitizer();
        String test = sanitizer.sanitizeHTML(editMe);
        Assert.assertEquals("This ' should be edited", test);
    }

    @Test
    public void testNotAnInt() {
        String leaveMeBe = "This &#string; should be unedited";
        HelperHTMLSanitizer sanitizer = new HelperHTMLSanitizer();
        String test = sanitizer.sanitizeHTML(leaveMeBe);
        Assert.assertEquals(leaveMeBe, test);
    }

    @Test
    public void testSomeOfAnInt() {
        String leaveMeBe = "This &#67string67; should be unedited";
        HelperHTMLSanitizer sanitizer = new HelperHTMLSanitizer();
        String test = sanitizer.sanitizeHTML(leaveMeBe);
        Assert.assertEquals(leaveMeBe, test);
    }

    @Test
    public void testConvertMultiple() {
        String editMe = "This &#039;should be edited&#039;";
        HelperHTMLSanitizer sanitizer = new HelperHTMLSanitizer();
        String test = sanitizer.sanitizeHTML(editMe);
        Assert.assertEquals("This 'should be edited'", test);
    }
}
