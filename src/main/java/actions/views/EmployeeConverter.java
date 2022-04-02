package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.Employee;

/**
 * 従業員データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class EmployeeConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param ev EmployeeViewのインスタンス
     * @return Employeeのインスタンス
     */
    //呼び出し先で渡される値が、引数のEmployeeView evに渡され、EmplloyeeViewクラスのインスタンス変数evが生成される
    public static Employee toModel(EmployeeView ev) {
        //戻り値としてEmployeeクラスのインスタンスオブジェクトを返す
        //Employeeクラスにコンストラクタは記述していないが、lombokでいつも通りの各フィールドを引数とするコンストラクタが自動で生成される。
        /*つまり、以下の{}内は引数ありコンストラクタの各引数であるフィールドに、このメソッドの引数で生成されるEmployeeViewオブジェクト
        の各フィールドの値をgetterで取得し、渡しているということである。*/
        return new Employee(
                ev.getId(),
                ev.getCode(),
                ev.getName(),
                ev.getPassword(),
                //ここで三項演算子を使って場合分け処理。このev.get~== Attribute~が条件式でROLE_GENERALまでが変数１、変数２
                //つまりev.getAdminFlagは右辺であり、左辺はEmployeeのフィールドであるInteger adminFlagとなる。
                ev.getAdminFlag() == null? null: ev.getAdminFlag() == AttributeConst.ROLE_ADMIN.getIntegerValue()
                        ? JpaConst.ROLE_ADMIN
                        : JpaConst.ROLE_GENERAL,
                ev.getCreatedAt(),
                ev.getUpdatedAt(),
                ev.getDeleteFlag() == null? null: ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                        ? JpaConst.EMP_DEL_TRUE
                        : JpaConst.EMP_DEL_FALSE);
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param e Employeeのインスタンス
     * @return EmployeeViewのインスタンス
     */
    //引数に渡される値によりインスタンス変数eのEmployeeインスタンスオブジェクトが生成される。
    public static EmployeeView toView(Employee e) {
        //eがnullの場合は以下の処理を行わないように、戻り値としてnullを返す
        if(e == null) {
            return null;
        }
        /*引数で生成されるEmployeeクラスのインスタンスオブジェクトから、getterでEmployeeViewの各フィールドの
        の値を取得して、EmployeeViewのインスタンスオブジェクトコンストラクタの各引数に渡す*/
        return new EmployeeView(
                e.getId(),
                e.getCode(),
                e.getName(),
                e.getPassword(),
                e.getAdminFlag() == null
                        ? null
                        : e.getAdminFlag() == JpaConst.ROLE_ADMIN
                                ? AttributeConst.ROLE_ADMIN.getIntegerValue()
                                : AttributeConst.ROLE_GENERAL.getIntegerValue(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getDeleteFlag() == null
                        ? null
                        : e.getDeleteFlag() == JpaConst.EMP_DEL_TRUE
                                ? AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                                : AttributeConst.DEL_FLAG_FALSE.getIntegerValue());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    //ジェネリクスにEmployeeViewを指定することで、同クラス型のデータのみ追加されるようにする。
    //引数にEmployeeジェネリクスのList型変数listを定義し、渡された値がListになる
    public static List<EmployeeView> toViewList(List<Employee> list) {
        //ArrayListのインスタンスオブジェクト（配列の入れ物）を生成
        List<EmployeeView> evs = new ArrayList<>();
        //上記の入れ物に、引数でListにしたEmployeeオブジェクトの各フィールドの値を追加していく
        for (Employee e : list) {
            evs.add(toView(e));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param ev Viewモデル(コピー元)
     */
    public static void copyViewToModel(Employee e, EmployeeView ev) {
        e.setId(ev.getId());
        e.setCode(ev.getCode());
        e.setName(ev.getName());
        e.setPassword(ev.getPassword());
        e.setAdminFlag(ev.getAdminFlag());
        e.setCreatedAt(ev.getCreatedAt());
        e.setUpdatedAt(ev.getUpdatedAt());
        e.setDeleteFlag(ev.getDeleteFlag());

    }

}