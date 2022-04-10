package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Report;
import models.validators.ReportValidator;

//reportテーブルの操作を行うDAOクラス
/**
 * 日報テーブルの操作に関わる処理を行うクラス
 */
public class ReportService extends ServiceBase {

    /**
     * 指定した従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReportView> getMinePerPage(EmployeeView employee, int page) {
        /*（）内の引数１がJPQL文であり、この中の変数部分に上記メソッドの引数が渡されることで、CRUD操作を行う。
         * 上記操作で取得した値をReportクラスのオブジェクトに格納する。
         */
        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_MINE, Report.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                /*データを何件目から表示するかを指定する。１ページあたりの表示件数が１５件で、
                 * 表示するページ数はpage変数から１を引いた値となる。これは、Resultの要素は配列と同じく、要素番号は０から始まるためである。
                 * よって、仮に最初のページを表示するなら、１−１＝０となり、配列要素番号０の値を表示する必要があるため、−１としている。
                 */
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                //１ページあたりの最大表示件数を１５と指定。
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                //上記で取得した情報をlist型で取得する。
                .getResultList();
        //上記のlistデータが格納されたreportsをtovViewListの引数に渡して、同じ値を持つ表示用のlistを作成
        return ReportConverter.toViewList(reports);
    }

    /**
     * 指定した従業員が作成した日報データの件数を取得し、返却する
     * @param employee
     * @return 日報データの件数
     */
    public long countAllMine(EmployeeView employee) {
        //引数１にメソッドのJPQL文を実行し、引数２に定義してlongクラスオブジェクトに格納する。
        long count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_ALL_MINE, Long.class)
                //上記のJSQL文の末尾の定数部分にsetParameterの引数２の値を代入してSQL文を完成させて実行
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

    /**
     * 指定されたページ数の一覧画面に表示する日報データを取得し、ReportViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReportView> getAllPerPage(int page) {

        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL, Report.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(reports);
    }

    /**
     * 日報テーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long reports_count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT, Long.class)
                .getSingleResult();
        return reports_count;
    }

    /**
     * idを条件に取得したデータをReportViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public ReportView findOne(int id) {
        return ReportConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力された日報の登録内容を元にデータを1件作成し、日報テーブルに登録する
     * @param rv 日報の登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(ReportView rv) {
        List<String> errors = ReportValidator.validate(rv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            rv.setCreatedAt(ldt);
            rv.setUpdatedAt(ldt);
            createInternal(rv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された日報の登録内容を元に、日報データを更新する
     * @param rv 日報の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(ReportView rv) {

        //バリデーションを行う
        List<String> errors = ReportValidator.validate(rv);

        if (errors.size() == 0) {

            //更新日時を現在時刻に設定
            LocalDateTime ldt = LocalDateTime.now();
            rv.setUpdatedAt(ldt);

            updateInternal(rv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Report findOneInternal(int id) {
        return em.find(Report.class, id);
    }

    /**
     * 日報データを1件登録する
     * @param rv 日報データ
     */
    private void createInternal(ReportView rv) {

        em.getTransaction().begin();
        em.persist(ReportConverter.toModel(rv));
        em.getTransaction().commit();

    }

    /**
     * 日報データを更新する
     * @param rv 日報データ
     */
    private void updateInternal(ReportView rv) {

        em.getTransaction().begin();
        Report r = findOneInternal(rv.getId());
        ReportConverter.copyViewToModel(r, rv);
        em.getTransaction().commit();

    }

}