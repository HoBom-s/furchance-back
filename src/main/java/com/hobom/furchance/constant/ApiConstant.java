package com.hobom.furchance.constant;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ApiConstant {

    public static final String SEARCH_BEGIN_DATE = "20240620";

    public static final String SEARCH_END_DATE = getTodayDate();

    public static final String SEOUL_CODE = "6110000";

    public static final String TYPE_JSON = "json";

    public static String getTodayDate() {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return formatter.format(now);
    }
}
