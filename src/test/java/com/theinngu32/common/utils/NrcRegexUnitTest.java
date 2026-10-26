package com.theinngu32.common.utils;

import com.theinngu32.common.utils.constants.RegexUtils;
import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

public class NrcRegexUnitTest {

    // Test NRC_REGEX (full pattern without groups)
    @Test
    public void testNrcRegex_ValidInputs() {
        String[] validInputs = {
                "1/ကမန(N)123456",
                "10/PaMaNa(C)654321",
                "14/Region(နိုင်)987654",
                "7/ကမနတ(T)000000"
        };

        for (String input : validInputs) {
            assertTrue(Pattern.matches(RegexUtils.NRC_REGEX, input),
                    "Expected '" + input + "' to match NRC_REGEX");
        }
    }

    @Test
    public void testNrcRegex_InvalidInputs() {
        String[] invalidInputs = {
                "0/ကမန(N)123456",       // Prefix < 1
                "15/PaMaNa(C)654321",    // Prefix > 14
                "10/ကမ(N)123456",        // Division too short (Myanmar)
                "10/Reg(C)654321",       // Division too short (English)
                "10/PaMaNa(X)987654",    // Invalid NRC type
                "10/PaMaNa(N)12345",     // Postfix too short
                "10/PaMaNa123456",       // Missing NRC type
                "10/PaMaNa(N)1234567"    // Postfix too long
        };

        for (String input : invalidInputs) {
            assertFalse(Pattern.matches(RegexUtils.NRC_REGEX, input),
                    "Expected '" + input + "' to NOT match NRC_REGEX");
        }
    }

    // Test NRC_REGEX_GROUP (full pattern with groups)
    @Test
    public void testNrcRegexGroup_ValidInputsWithGroups() {
        String input = "10/PaMaNa(N)123456";
        Pattern pattern = Pattern.compile(RegexUtils.NRC_REGEX_GROUP);
        java.util.regex.Matcher matcher = pattern.matcher(input);

        assertTrue(matcher.matches(), "Expected '" + input + "' to match NRC_REGEX_GROUP");
        assertEquals("10", matcher.group(1), "Group 1 (prefix) should be '10'");
        assertEquals("PaMaNa", matcher.group(2), "Group 2 (division) should be 'PaMaNa'");
        assertEquals("N", matcher.group(3), "Group 3 (type) should be '(N)'");
        assertEquals("123456", matcher.group(4), "Group 4 (postfix) should be '123456'");
    }

    @Test
    public void testNrcRegexGroup_InvalidInputs() {
        String[] invalidInputs = {
                "15/PaMaNa(N)123456",    // Prefix > 14
                "10/ကမ(N)123456",        // Division too short
                "10/PaMaNa(X)987654",    // Invalid NRC type
                "10/PaMaNa(N)123"        // Postfix too short
        };

        Pattern pattern = Pattern.compile(RegexUtils.NRC_REGEX_GROUP);
        for (String input : invalidInputs) {
            assertFalse(pattern.matcher(input).matches(),
                    "Expected '" + input + "' to NOT match NRC_REGEX_GROUP");
        }
    }

    // Test NRC_PREFIX_CODE
    @Test
    public void testNrcPrefixCode_ValidInputs() {
        String[] validInputs = {"1", "2", "9", "10", "11", "14"};
        for (String input : validInputs) {
            assertTrue(Pattern.matches(RegexUtils.NRC_PREFIX_CODE, input),
                    "Expected '" + input + "' to match NRC_PREFIX_CODE");
        }
    }

    @Test
    public void testNrcPrefixCode_InvalidInputs() {
        String[] invalidInputs = {"0", "15", "20", "01", "abc"};
        for (String input : invalidInputs) {
            assertFalse(Pattern.matches(RegexUtils.NRC_PREFIX_CODE, input),
                    "Expected '" + input + "' to NOT match NRC_PREFIX_CODE");
        }
    }

    // Test NRC_DIVISION
    @Test
    public void testNrcDivision_ValidInputs() {
        String[] validInputs = {"ကမန", "ကမနတ", "PaMaNa", "RegionalOffice"};
        for (String input : validInputs) {
            assertTrue(Pattern.matches(RegexUtils.NRC_DIVISION, input),
                    "Expected '" + input + "' to match NRC_DIVISION");
        }
    }

    @Test
    public void testNrcDivision_InvalidInputs() {
        String[] invalidInputs = {"ကမ", "က", "Reg", "PaMa", "123"};
        for (String input : invalidInputs) {
            assertFalse(Pattern.matches(RegexUtils.NRC_DIVISION, input),
                    "Expected '" + input + "' to NOT match NRC_DIVISION");
        }
    }

    // Test NRC_TYPE
    @Test
    public void testNrcType_ValidInputs() {
        String[] validInputs = {"C", "AC", "N", "T", "နိုင်", "ဧည့်", "ယာယီ"};
        for (String input : validInputs) {
            assertTrue(Pattern.matches(RegexUtils.NRC_TYPE, input),
                    "Expected '" + input + "' to match NRC_TYPE");
        }
    }

    @Test
    public void testNrcType_InvalidInputs() {
        String[] invalidInputs = {"X", "ABC", "က", "123", ""};
        for (String input : invalidInputs) {
            assertFalse(Pattern.matches(RegexUtils.NRC_TYPE, input),
                    "Expected '" + input + "' to NOT match NRC_TYPE");
        }
    }

    // Test NRC_POST_FIX_DIGIT
    @Test
    public void testNrcPostFixDigit_ValidInputs() {
        String[] validInputs = {"123456", "000000", "987654"};
        for (String input : validInputs) {
            assertTrue(Pattern.matches(RegexUtils.NRC_POST_FIX_DIGIT, input),
                    "Expected '" + input + "' to match NRC_POST_FIX_DIGIT");
        }
    }

    @Test
    public void testNrcPostFixDigit_InvalidInputs() {
        String[] invalidInputs = {"12345", "1234567", "abc123", "12-345"};
        for (String input : invalidInputs) {
            assertFalse(Pattern.matches(RegexUtils.NRC_POST_FIX_DIGIT, input),
                    "Expected '" + input + "' to NOT match NRC_POST_FIX_DIGIT");
        }
    }
}
