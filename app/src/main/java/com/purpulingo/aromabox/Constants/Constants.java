package com.purpulingo.aromabox.Constants;

public interface Constants {
    interface LOGIN_USER{
        String KEY_USER_ID = "user_id";
        String KEY_ORG_ID = "org_id";
        String KEY_PASSWORD = "user_password";
    }
    interface GET_LOGIN_USER{
        String KEY_JSON_STATUS = "json_status";
        String KEY_FIRST_NAME = "user_f_name";
        String KEY_LAST_NAME = "user_l_name";
        String KEY_ORG_NAME = "name";
        String KEY_ROLE = "role_name";
        String KEY_ORG_ID = "id";
        String KEY_USER_ID = "user_id";
        String KEY_EMAIL = "Email";
        String KEY_USER_TYPE = "user_type";
    }

    interface  NOTIFICATION_LIST{
        String KEY_USER_ID = "user_id";
        String KEY_INDENT_HEADER_ID = "indent_header_id";
        String KEY_ORG_ID = "org_id";
        String KEY_SEASON_ID = "season_id";
        String KEY_POP_ID = "pop_id";
        String KEY_INDENT_DATE_ID = "indent_date";
        String KEY_CREATED_BY_ID = "created_by";
        String KEY_INDENT_AREA_ID = "indent_area2";
        String KEY_INDENT_AREA_ACTUAL_ID = "indent_area_actual";
        String KEY_FARMER_COUNT_ID = "farmer_count";
        String KEY_FARMER_COUNT_ACTUAL_ID = "farmer_count_actual";
        String KEY_INDENT_STATUS_ID = "indent_status";
        String KEY_UPDATE_DATE_ID = "update_date";
        String KEY_UOM_ID = "uom_id";
    }
    interface NOTIFICATION_LIST_DETAILS{
        String KEY_FARMER_ID = "farmer_id";
        String KEY_USER_ID = "user_id";
        String KEY_INDENT_ID = "indent_id";
        String KEY_ORG_ID = "org_id";
        String KEY_OPS_TYPE = "ops_type";
        String KEY_INDENT_DETAILS_ID = "indent_details_id";
        String KEY_INDENT_HEADER_ID = "indent_header_id";
        String KEY_POP_ID = "pop_id";
        String KEY_FARMER_NAME = "farmer_name";
        String KEY_INDENT_AREA = "indent_area";
        String KEY_CUTTING_DATE = "cutting_date";
        String KEY_VILLAGE_NAME = "village_name";
        String KEY_FARMER_IMG = "f_img";
        String KEY_UOM_ID = "uom_id";
    }

    interface QR_SCANNER_PROFILE{
        String KEY_QRVAL = "qrval";
        String KEY_FIRST_NAME = "first_name";
        String KEY_LAST_NAME = "last_name";
        String KEY_AGE = "age";
        String KEY_MOBILE = "mobile";
        String KEY_GENDER = "gender";
        String KEY_PHOTO = "photo";
        String KEY_PG_NAME = "pg_name";
        String KEY_MOBILE_NO_2 = "mobile_no_2";
        String KEY_POP_ID = "pop_id";
        String KEY_FARMER_ID = "farmer_id";
        String KEY_BATCH_ID = "batch_id";
        String KEY_STATE = "state";
        String KEY_DISTRICT = "district";
        String KEY_BLOCK = "block";
        String KEY_VILLAGE = "village";
        String KEY_PIN_CODE = "pincode";
        String KEY_LAND_AREA = "land_area";
        String KEY_START_DATE = "srart_date";
        String KEY_LAST_CUTTING_DATE = "last_cutting_date";
        String KEY_PRODUCTION = "production";

    }

    interface USER_SESSION_MANAGEMENT{
        String KEY_PREFER_NAME = "AromaBox";

        String KEY_USER_ID = "user_id";
        String KEY_FIRST_NAME = "first_name";
        String KEY_LAST_NAME = "last_name";
        String KEY_EMAIL = "email";
        String KEY_ORG_NAME = "org_name";
        String KEY_ROLE = "role";
        String KEY_ORG_ID = "org_id";
        String KEY_USER_TYPE = "user_type";

    }

    interface START_DISTILLATION{
        String KEY_USER_ID = "user_id";
        String KEY_ORG_ID = "org_id";
        String KEY_POS_ID = "pos_id";
        String KEY_START_DATE = "start_date";
        String KEY_START_TIME = "start_time";
        String KEY_QUANTITY = "quantity";
        String KEY_QUALITY = "grade";
        String KEY_UOM = "uom";

        String KEY_RETCODE = "retcode";
    }
    interface EXITING_DISTILLATION_BATCH{
        String KEY_USER_ID = "user_id";
        String KEY_ORG_ID = "org_id";

        String KEY_POS_ID = "pos_id";
        String KEY_START_DATE = "start_date";
        String KEY_START_TIME = "start_time";
        String KEY_QUANTITY = "input_qty";
        String KEY_BATCH_ID = "batch_id";
        String KEY_GRADE = "grade";

    }
    interface END_DISTILLATION{
        String KEY_USER_ID = "user_id";
        String KEY_ORG_ID = "org_id";
        String KEY_POS_ID = "pos_id";
        String KEY_BATCH_ID = "batch_id";

        String KEY_END_DATE = "end_date";
        String KEY_END_TIME = "end_time";
        String KEY_QUANTITY = "qty";
        String KEY_UOM = "uom";

        String KEY_RETCODE = "retcode";
    }

    interface KEY_QR_FARMER_PROFILE{
        String KEY_ORG_ID = "org_id";
        String KEY_PRODUCT_ID = "product_id";
        String KEY_FARMER_ID = "farmer_id";
        String KEY_QUANTITY = "qty";
        String KEY_UOM = "uom";
        String KEY_DATE = "update";

    }

    interface KEY_PROCUREMENT_SEARCH{
        String KEY_ORG_ID = "org_id";
        String KEY_START_DATE = "start_date";
        String KEY_END_DATE = "end_date";

        String KEY_QUANTITY = "qty";
        String KEY_AMOUNT = "amount";

    }
    interface KEY_PROCUREMENT_LIST{
        String KEY_ORG_ID = "org_id";
        String KEY_START_DATE = "start_date";
        String KEY_END_DATE = "end_date";

        String KEY_FARMER_ID = "farmer_id";
        String KEY_FARMER_NAME = "farmer_name";
        String KEY_QTY = "trn_qty";
        String KEY_AMOUNT = "amount";
        String KEY_TEN_DATE = "trn_date";

    }
    interface KEY_DUE_LIST{
        String KEY_ORG_ID = "org_id";

        String KEY_FARMER_ID = "farmer_id";
        String KEY_FARMER_NAME = "farmer_name";
        String KEY_DUE_AMOUNT = "due_amount";
    }
    interface KEY_PAID_LIST{
        String KEY_ORG_ID = "org_id";
        String KEY_START_DATE = "start_date";
        String KEY_END_DATE = "end_date";

        String KEY_FARMER_ID = "farmer_id";
        String KEY_FARMER_NAME = "farmer_name";
        String KEY_PAYMENT_AMOUNT = "payment_amount";
        String KEY_PAYMENT_DATE = "payment_date";

    }
    interface KEY_PAYMENT{
        String KEY_ORG_ID = "org_id";
        String KEY_FARMER_NAME = "farmer_name";
        String KEY_FARMER_ID = "farmer_id";
        String KEY_PAYMENT_DATE = "payment_date";
        String KEY_PAYMENT_AMOUNT = "amount";
        String KEY_REF_NO = "ref_no";
    }

    interface KEY_CULTIVATION{
        String KEY_ORG_ID = "org_id";
        String KEY_FARMER_COUNT = "farmer_count";
        String KEY_AREA = "area";
        String KEY_OIL_STOCK = "stock";
    }

    interface KEY_FARMER_LIST {
        String KEY_ORG_ID = "org_id";
        String KEY_COUNT = "farmer_count";
        String KEY_AREA = "frm_area";
        String KEY_PG_ID = "pg_id";
        String KEY_PG_NAME = "pg_name_desc";
    }
    interface KEY_FARMER_PG_LIST{
        String KEY_ORG_ID = "org_id";
        String KEY_PG_ID = "pg_id";
        String KEY_FARMER_ID = "f_id";
        String KEY_AREA = "frm_area";
        String KEY_ADDRESS = "frm_address";
        String KEY_FARMER_NAME = "farmer_name";
        String KEY_VILLAGE = "village_name";
        String KEY_IMG = "img";
        String KEY_LAST_CUTTING = "sch_date";
        String KEY_NEXT_CUTTING = "next_cutting_date";
        String KEY_LAST_IRR = "last_irr";
        String KEY_LAST_FER = "last_fer";
        String KEY_TRACE = "trace";
        String KEY_DATES = "dates";
        String KEY_OPS_ID = "ops_id";
        String KEY_TRN_DATE = "trn_date";
    }

    interface KEY_OIL_LIST {
        String KEY_ORG_ID = "org_id";
        String KEY_START_DATE = "start_date";
        String KEY_END_DATE = "end_date";
        String KEY_POS_ID = "pos_id";

        String KEY_QUANTITY = "qty";
    }

    interface KEY_CUTTING_LIST_CAL {
        String KEY_USER_ID = "user_id";
        String KEY_ORG_ID = "org_id";
        String KEY_YEAR_ID = "year_id";
        String KEY_MONTH_ID = "month_id";
        String KEY_POP_ID = "pop_id";
        String KEY_DAY = "day";
        String KEY_CUTTING = "cutting";
    }

    interface KEY_FARMER_LIST_POPUP{
        String KEY_ORG_ID = "org_id";
        String KEY_USER_ID = "user_id";
        String KEY_SEARCH_DATE = "sch_date";

        String KEY_FARMER_ID = "farmer_id";
        String KEY_FARMER_NAME = "farmer_name";
        String KEY_VILLAGE = "village_name";
    }

    interface KEY_FARMER_PROFILE_SUBMIT{
        String KEY_ORG_ID = "org_id";
        String KEY_USER_ID = "user_id";
        String KEY_FARMER_ID = "farmer_id";

        String KEY_OPS_ID = "ops_id";
        String KEY_OPS_DATE = "ops_date";
    }

}
