package com.theinngu32.common.utils.constants;

public class RegexUtils {
    public static final String NRC_REGEX = "^(1[0-4]|[1-9])/(?:[က-အ]{3,}|[A-Za-z]{6,})\\((C|AC|NC|M|T|V|N|နိုင်|ဧည့်|ပြု|သ|ယာယီ|စ|သီ)\\)\\d{6}$";
    public static final String NRC_REGEX_GROUP = "^(1[0-4]|[1-9])/([က-အ]{3,}|[A-Za-z]{6,})\\((C|AC|NC|M|T|V|N|နိုင်|ဧည့်|ပြု|သ|ယာယီ|စ|သီ)\\)(\\d{6})$";

    public static final String NRC_PREFIX_CODE = "^(1[0-4]|[1-9])$";
    public static final String NRC_DIVISION = "^(?:[က-အ]{3,}|[A-Za-z]{6,})$";
    public static final String NRC_TYPE = "^(C|AC|NC|M|T|V|N|နိုင်|ဧည့်|ပြု|သ|ယာယီ|စ|သီ)$";
    public static final String NRC_POST_FIX_DIGIT = "^\\d{6}$";
    public static final String AGE_REGEX = "^(5|[6-9]|[1-9][0-9]|1[0-2][0-9]|130)$";
    public static final String PHONE_REGEX = "^\\d{7,15}$";
    public static final String START_DATE_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    public static final String END_DATE_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    public static final String TITLE_REGEX = "^.{0,200}$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String NAME_REGEX = "^[a-zA-Z\\u1000-\\u1021\\u1023-\\u1027\\u1029-\\u102a\\u102b-\\u102d\\u102f-\\u1036 ]+$";
    public static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,12}$\n";
}
