package com.example.rockyjain.stackintegration;

public class AppConstants {
    static String REQUEST_USERNAME = "com.example.rockyjain.stackintegration.REQUEST_USERNAME";
    static String REQUEST_PASSWORD = "com.example.rockyjain.stackintegration.REQUEST_PASSWORD";
    static String REQUEST_DISPLAYNAME = "com.example.rockyjain.stackintegration.REQUEST_DISPLAY_NAME";
    static String REQUEST_WEB_VIEW = "com.example.rockyjain.stackintegration.REQUEST_WEB_VIEW";

    static String REQUEST_POPULAR_TAG = "https://api.stackexchange.com/2.2/tags?order=desc&sort=popular&site=stackoverflow";
    static String REQUEST_API = "https://api.stackexchange.com/2.2/questions?order=desc&sort=";
    static String REQUEST_TAGS = "&tagged=";
    static String REQUEST_SITE = "&site=stackoverflow";
    static String REQUEST_ACTIVITY = "activity";
    static String REQUEST_HOT = "hot";
    static String REQUEST_WEEK = "week";

    static int REQUEST_CODE = 1008;
    static int READ_TIMEOUT = 3000;
    static int CONNECTION_TIMEOUT = 3000;
    static String REQUEST_METHOD = "GET";
}
