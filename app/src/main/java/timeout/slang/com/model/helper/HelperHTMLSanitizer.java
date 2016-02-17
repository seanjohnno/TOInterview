package timeout.slang.com.model.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public class HelperHTMLSanitizer {

    /* ------------------------------------------------------------------------------------------
     * Constants
     * ------------------------------------------------------------------------------------------ */

    /**
     * Regex pattern to convert &#039; style ascii codes. Html class didn't seem to do the trick, unsure
     * if there's an API for this
     */
    private static Pattern ASCII_PATTERN = Pattern.compile("&#(\\d*);");

    /**
     * Convert &#039; style ascii codes
     * @param html      Input HTML
     * @return          Output as sanitized string
     */
    public HelperHTMLSanitizer() {

    }

    public String sanitizeHTML(String html) {
        if(html != null) {
            Matcher matcher = ASCII_PATTERN.matcher(html);
            StringBuilder sb = new StringBuilder();

            // Loop through while regex matches
            while(matcher.find()) {

                // Convert the group to an int and replace, reset matched with updated str
                sb.append((char) Integer.parseInt(matcher.group(1)));
                html = matcher.replaceFirst(sb.toString());
                matcher.reset(html);

                // Cleanup for next loop
                sb.setLength(0);
            }
        }
        return html;
    }
}
