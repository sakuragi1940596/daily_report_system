package constants;


//画面の項目値等を定数で定義するenumクラス
public enum AttributeConst {
    //定数として列挙する値は、すべて大文字で定義し、それぞれに値を持たせる場合は（）内に記述

    //flush message
    FLUSH("flush"),

    //index
    MAX_ROW("maxRow"),
    PAGE("page"),

    //inputform
    TOKEN("_token"),
    ERR("errors"),

    //loginEmployee
    LOGIN_EMP("login_employee"),

    //login
    LOGIN_ERR("loginError"),

    //employeeManagement
    EMPLOYEE("employee"),
    EMPLOYEES("employees"),
    EMP_COUNT("employees_count"),
    EMP_ID("id"),
    EMP_CODE("code"),
    EMP_PASS("password"),
    EMP_NAME("name"),
    EMP_ADMIN_FLG("admin_flag"),

    //managerFlag
    ROLE_ADMIN(1),
    ROLE_GENERAL(0),

    //deleate
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

    //daily_report_manager
    REPORT("report"),
    REPORTS("reports"),
    REP_COUNT("reports_count"),
    REP_ID("id"),
    REP_DATE("report_date"),
    REP_TITLE("title"),
    REP_CONTENT("content");

    //上記がenumの定数の定義
    //ここからフィールドとコンストラクタの定義。定数に値を設定したため必要

    //フィールドの宣言
    private final String text;
    private final Integer i;

    //コンストラクタの記述
    private AttributeConst(final String text) {
        this.text=text;
        this.i=null;
    }

    private AttributeConst(final Integer i) {
        this.text=null;
        this.i=i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }






}
