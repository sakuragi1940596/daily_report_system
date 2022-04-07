package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.EmployeeService;

/**
 * 認証に関する処理を行うActionクラス
 *
 */
public class AuthAction extends ActionBase {
    //EmployeeServiceクラスのオブジェクトを生成し、DBを操作するために、同クラス型の参照変数をクラス全体で宣言
    private EmployeeService service;

    /**
     * メソッドを実行する
     */
    //ActionBaseのprocessメソッドを継承
    @Override
    public void process() throws ServletException, IOException {
        //EmployeeServiceのインスタンスオブジェクトを生成する。分解すると、EmployeeService service = new EmployeeService()となる
        service = new EmployeeService();

        //HTTPリクエストからパラメータを取得し、メソッドを実行
        invoke();
        //DBとの接続を閉じる
        service.close();
    }

    /**
     * ログイン画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void showLogin() throws ServletException, IOException {

        //CSRF対策用トークンを設定。getTokenid()でsessionscopeからsessionidを取得し、_tokenに格納してリクエストしコープへ
        putRequestScope(AttributeConst.TOKEN, getTokenId());

        //セッションにフラッシュメッセージが登録されている場合はリクエストスコープに設定する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH,flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //ログイン画面を表示。loginフォルダ内のlogin.jspを定数で指定している。
        forward(ForwardConst.FW_LOGIN);
    }

    /**
     * ログイン処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void login() throws ServletException, IOException {
        //login.jspから送信され、requestscopeに存在しているcode,pass、アプリケーションスコープのハッシュ化のためのpepperを取得する
        String code = getRequestParam(AttributeConst.EMP_CODE);
        String plainPass = getRequestParam(AttributeConst.EMP_PASS);
        String pepper = getContextScope(PropertyConst.PEPPER);

        //有効な従業員か認証する
        /*isvalidに上記で取得した各種値を渡して、findOneメソッドによりテーブルから該当する従業員のデータを持つ
         * evインスタンスオブジェクトを生成して取得する。
         */
        Boolean isValidEmployee = service.validateLogin(code, plainPass, pepper);
        //上記メソッドは、evインスタンスオブジェクトが生成された場合のみ、boolean型のisvalid変数をtrueとして返してくる
        if (isValidEmployee) {
            //認証成功の場合

            //CSRF対策 tokenのチェック
            if (checkToken()) {
                //ログインした従業員のDBデータを取得
                /*findOneメソッドで、引数に渡した社員番号とパスワードを持つ従業員データをテーブルから取得して生成された
                 * EmployeeConverterのオブジェクトEmployeeViewと同じデータを持つインスタンスオブジェクトevを生成する。
                 */
                EmployeeView ev = service.findOne(code, plainPass, pepper);
                //セッションにログインした従業員を設定
                /*evのインスタンスオブジェクトを、login_empに格納してsessionscopeに保管する。
                 * このsessionscopeに格納されたevオブジェクトによって、ログイン中のクライアントの情報が、ログアウトまで保存される。
                 */
                putSessionScope(AttributeConst.LOGIN_EMP, ev);
                //セッションにログイン完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_LOGINED.getMessage());
                //トップページへリダイレクト
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
            }
        } else {
            //認証失敗の場合

            //CSRF対策用トークンを設定
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            //認証失敗エラーメッセージ表示フラグをたてる
            putRequestScope(AttributeConst.LOGIN_ERR, true);
            //入力された従業員コードを設定
            putRequestScope(AttributeConst.EMP_CODE, code);

            //ログイン画面を表示
            forward(ForwardConst.FW_LOGIN);
        }
    }

    /**
     * ログアウト処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void logout() throws ServletException, IOException {

        //セッションからログイン従業員のパラメータを削除
        //loginメソッドで生成された、ログイン中の従業員情報のインスタンスオブジェクトevを削除する。
        removeSessionScope(AttributeConst.LOGIN_EMP);

        //セッションにログアウト時のフラッシュメッセージを追加
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_LOGOUT.getMessage());

        //ログイン画面にリダイレクト
        redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);

    }
}