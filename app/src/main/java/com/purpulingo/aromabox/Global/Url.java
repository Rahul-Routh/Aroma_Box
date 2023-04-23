package com.purpulingo.aromabox.Global;

public class Url {

    private static final String BASE_URL = "https://api2.boxfarming.in/wsapi/v1/index.php/";

    public static final String LOGIN_URL = BASE_URL + "Aroma/jSON_User_verification2";

    public static final String NOTIFICATION_LIST_URL = BASE_URL + "Aroma/aroma_indent_list";
    public static final String NOTIFICATION_LIST_DETAILS_URL = BASE_URL + "Aroma/aroma_indent_list_details";

    public static final String FARMER_SEARCH_QR = BASE_URL + "Aroma/farmer_search_qr";

    public static  final String DISTILLATION_START = BASE_URL +"Aroma/aroma_distillation_start";
    public static  final String DISTILLATION_BATCH_LIST = BASE_URL +"Aroma/aroma_distillation_batch";
    public static  final String DISTILLATION_END = BASE_URL +"Aroma/aroma_distillation_batch_end";

    public static  final String QR_PROFILE = BASE_URL +"Aroma/bx_ph_stock_in";

    public static  final String SELECTED_FARMER_LIST = BASE_URL +"Aroma/aroma_indent_submit";
    public static  final String SELECTED_FARMER_LIST_CAL = BASE_URL +"Aroma/aroma_cutting_scheduling";

    public static  final String PROCUREMENT_SEARCH = BASE_URL +"Aroma/bx_ph_procurement_search";
    public static  final String PROCUREMENT_LIST = BASE_URL +"Aroma/bx_ph_procurement_list";

    public static  final String PAYMENT_DUE_LIST = BASE_URL +"Aroma/bx_ph_farmer_list_due";
    public static  final String PAYMENT_PAID_LIST = BASE_URL +"Aroma/bx_ph_payment_list";
    public static  final String PAYMENT_SUBMIT = BASE_URL +"Aroma/bx_ph_farmer_payment";

    public static  final String CULTIVATION_URL = BASE_URL +"Aroma/bx_aroma_mhome";

    public static  final String FARMER_LIST = BASE_URL +"Aroma/bx_pg_list";
    public static  final String FARMER_PG_LIST = BASE_URL +"Aroma/bx_farmer_list_by_pg";
    public static  final String FARMER_LIST_POPUP = BASE_URL +"Aroma/aroma_cutting_sch_view";

    public static  final String OIL_LIST = BASE_URL +"Aroma/aroma_distillation_list";
    public static  final String CUTTING_LIST_CAL = BASE_URL +"Aroma/aroma_cuttling_list_cal";

    public static  final String FARMER_SUBMIT_PROFILE = BASE_URL +"Aroma/aroma_pop_trace";

}
