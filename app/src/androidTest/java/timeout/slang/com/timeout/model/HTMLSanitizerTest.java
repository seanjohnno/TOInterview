package timeout.slang.com.timeout.model;

import org.junit.Assert;
import org.junit.Test;

import timeout.slang.com.model.helper.HelperHTMLSanitizer;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public class HTMLSanitizerTest {

    @Test
    public void testHandlesNull() {
        HelperHTMLSanitizer sanitizer = new HelperHTMLSanitizer();
        String test = sanitizer.sanitizeHTML(null);
        Assert.assertNull(test);
    }
}
