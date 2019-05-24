package cn.hsy.echo.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ParamValidUtil {
    public static final String REGEX_TELEPHONE = "^[1][3,4,5,7,8][0-9]{9}$";

    public static final String REGEX_STUDENT_ID = "\\d{9}$";

    public static boolean isTelephone(String telephone) {
        return Pattern.matches(REGEX_TELEPHONE, telephone);
    }

    public static boolean isSutdentId(String studentId) {
        return Pattern.matches(REGEX_STUDENT_ID, studentId);
    }
}
