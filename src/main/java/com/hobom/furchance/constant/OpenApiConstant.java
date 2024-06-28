package com.hobom.furchance.constant;

import com.hobom.furchance.util.Util;

public class OpenApiConstant {

    public static final String OPENAPI_BASE_URL = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc";

    // 유기동물 오픈 api
    public static final String PARAM_ABANDONED_ANIMAL = "/abandonmentPublic";

    public static final String SEARCH_BEGIN_DATE = "20240620";

    public static final String SEARCH_END_DATE = Util.getTodayDate();

    public static final String SEOUL_CODE = "6110000";

    public static final String TYPE_JSON = "json";

    public static final String COUNT = "100";

    // FeignClientRetryer
    public static final int PERIOD = 100;

    public static final Long DURATION = 3L;

    public static final int MAX_ATTEMPT = 5;


}
