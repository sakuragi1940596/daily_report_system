package views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//従業員情報の入力・出力を行うためのviewモデル
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeView {
    //id
    private Integer id;

    //number
    private String code;

    //name
    private String name;

    //pass
    private String password;

    //ManagerCheck
    private Integer adminFlag;

    //createDateTime
    private LocalDateTime createdAt;

    //updateDateTime
    private LocalDateTime updatedAt;

    //deleteCheck
    private Integer deleteFlag;
}
