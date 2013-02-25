/*
   Copyright [2013] [Entrib Technologies]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package  com.entrib.mongoslate.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils class for regular expressions.
 * 
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class RegexUtils {

    private static final char SPECIAL_CHARACTERS[] =
            {'[', ']', '{', '}', '^', '\\', '$', '(', ')', '.', '*', '+', '?', '|', '<', '>', '-', '&' };

    private static String PATTERN_STRING;

    static {
        PATTERN_STRING = ".*[";
        for (char ch: SPECIAL_CHARACTERS) {
            PATTERN_STRING += "\\" + ch;
        }
        PATTERN_STRING += "].*";
    }

    private static Pattern pattern = Pattern.compile(PATTERN_STRING);

    /**
     * For the given string escape all the regular expression special characters.
     * 
     * @param text
     * @return
     */
    public static String escapeRegex(String text) {
        Matcher matcher = pattern.matcher(text);
        boolean flag = matcher.find();
        if(flag) {
            StringBuilder builder = new StringBuilder();
            char[] textArr = text.toCharArray();
            for (char c: textArr) {
                switch(c) {
                    case '[': case ']': case '{': case '}': case '^': case '\\': case '$': case '(' :case ')' :
                    case '.': case '*' :case '+' :case '?' :case '|' :case '<' :case '>' :case '-' :case '&' :
                        builder.append("\\").append(c);
                                break;
                    default:
                        builder.append(c);
                }
            }
            return builder.toString();
        }
        return text;
    }
}
