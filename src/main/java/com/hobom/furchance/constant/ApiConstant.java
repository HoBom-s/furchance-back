package com.hobom.furchance.constant;

import com.hobom.furchance.util.Util;

public class ApiConstant {

    public static final String OPENAPI_BASE_URL = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc";

    public static final String PARAM_ABANDONED_ANIMAL = "/abandonmentPublic";

    public static final String SEARCH_BEGIN_DATE = "20240615";

    public static final String SEARCH_END_DATE = Util.getTodayDate();

    public static final String SEOUL_CODE = "6110000";

    public static final String TYPE_JSON = "json";

    public static final String COUNT = "100";

}
