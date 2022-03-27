package views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.Employee;

//EmployeeDTOとEmployeeViewsの相互変換を行うためのクラス

public class EmployeeConverter {
    /* ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param ev EmployeeViewのインスタンス
     * @return Employeeのインスタンス*/

    //EmployeeViewのデータ型を持つev変数に引数を渡して戻り値を返すメソッド
    public static Employee toModel(EmployeeView ev) {

        //戻り値としてEmployeeクラスのインスタンスオブジェクトを返す
        return new Employee(
                //getterを使って、EmployeeViewオブジェクトの各フィールドの値をEmployeeオブジェクトに代入
                ev.getId(),
                ev.getCode(),
                ev.getName(),
                ev.getPassword(),
                ev.getAdminFlag() == null
                        ? null
                        : ev.getAdminFlag() == AttributeConst.ROLE_ADMIN.getIntegerValue()
                                ? JpaConst.ROLE_ADMIN
                                : JpaConst.ROLE_GENERAL,
                ev.getCreatedAt(),
                ev.getUpdatedAt(),
                ev.getDeleteFlag() == null
                        ? null
                        : ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                                ? JpaConst.EMP_DEL_TRUE
                                : JpaConst.EMP_DEL_FALSE);
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param e Employeeのインスタンス
     * @return EmployeeViewのインスタンス
     */

    public static EmployeeView toView(Employee e) {

        if(e == null) {
            return null;
        }
        //EmployeeViesのインスタンスオブジェクトを戻り値して返す
        return new EmployeeView(
                //EmployeeViewオブジェクトの各フィールドに、Employeeオブジェクトの各値を代入
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
    //ジェネリクスをEmployeeView型に指定して、配列listを生成
    public static List<EmployeeView> toViewList(List<Employee> list) {
        //配列evsを生成
        List<EmployeeView> evs = new ArrayList<>();
        //拡張for文を利用して、list配列内の値を順次取り出し、その値をEmployee型変数eとして、配列evsに加える
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

        //Employeeオブジェクトに、EmployeeViewオブジェクトの各フィールドの値をコピーする
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
