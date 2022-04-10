package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Report;

/**
 * 日報データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class ReportConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ReportViewのインスタンス
     * @return Reportのインスタンス
     */
    //呼び出し先で渡された値を元に、rvインスタンスオブジェクトを引数で生成
    public static Report toModel(ReportView rv) {
        /*引数のrvオブジェクトの値をgetterで取得し、Reportのインスタンスオブジェクトのコンストラクタの引数に渡すことで、
        rvのフィールドと同じ値を持つインスタンスオブジェクトを生成*/
        return new Report(
                rv.getId(),
                EmployeeConverter.toModel(rv.getEmployee()),
                rv.getReportDate(),
                rv.getTitle(),
                rv.getContent(),
                rv.getCreatedAt(),
                rv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Reportのインスタンス
     * @return ReportViewのインスタンス
     */
    //呼び出し先で渡された値を元に、インスタンス変数rのオブジェクトを生成
    public static ReportView toView(Report r) {

        if (r == null) {
            return null;
        }
        //rのオブジェクトの各値をgetterで取得し、ReportViewクラスのインスタンスオブジェクトを生成
        return new ReportView(
                r.getId(),
                /*getEmployeeは、DTOのフィールドEmployee employeeのgetterである。
                 * rオブジェクトの従業員情報と同じフィールドを持つEmployeeViewクラスのオブジェクトを生成
                 */
                EmployeeConverter.toView(r.getEmployee()),
                r.getReportDate(),
                r.getTitle(),
                r.getContent(),
                r.getCreatedAt(),
                r.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    //呼び出し先の値を配列要素とするReportクラス型のlistを引数で生成
    public static List<ReportView> toViewList(List<Report> list) {
        //evsのリストを作成
        List<ReportView> evs = new ArrayList<>();
        /*引数のlistの各値がReportクラス型の参照変数rの右辺となり、rの値と同じ値を持つReportViewオブジェクトを生成し、
         * その値をevsリストに順次追加する。
         */
        for (Report r : list) {
            evs.add(toView(r));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Report r, ReportView rv) {
        r.setId(rv.getId());
        r.setEmployee(EmployeeConverter.toModel(rv.getEmployee()));
        r.setReportDate(rv.getReportDate());
        r.setTitle(rv.getTitle());
        r.setContent(rv.getContent());
        r.setCreatedAt(rv.getCreatedAt());
        r.setUpdatedAt(rv.getUpdatedAt());

    }

}