package transfer.demo.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseMessage {

    USER_NOT_FOUND ("101","Пользователь не найден"),
    CONNECTION_REFUSED ("102","Ошибка соединения с базаой данных"),
    CODE_NOT_FOUND ("104","Уникальный код не найден"),
    TRANSACTION_ISSUED ("250","Платеж успешно получен"),
    CREATE_CASH_BOX ("120","Касса успешно создана"),
    CREATE_BALANCE ("110","Баланс успешно создана"),
    INTERNAL_SERVER_ERROR ("200","Ошибка на сервере"),
    TRANSACTION_CREATED ("249","Платеж успешно принят"),
    BALANCE_IS_NULL ("150","Недостаточно средств для списания");
    private String code;
    private String message;
}
