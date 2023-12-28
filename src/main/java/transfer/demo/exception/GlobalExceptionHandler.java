package transfer.demo.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import transfer.demo.models.enums.ResponseMessage;
import transfer.demo.response.BaseResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<BaseResponse> balanceException(BalanceException ex) {
        return ResponseEntity.ok(BaseResponse.builder()
                .code(ResponseMessage.BALANCE_IS_NULL.getCode())
                .message(ResponseMessage.BALANCE_IS_NULL.getMessage())
                .build());
    }
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<BaseResponse> balanceException(ServiceException ex) {
        return ResponseEntity.ok(BaseResponse.builder()
                .code(ResponseMessage.INTERNAL_SERVER_ERROR.getCode())
                .message(ResponseMessage.INTERNAL_SERVER_ERROR.getMessage())
                .build());
    }
    @ExceptionHandler(CodeException.class)
    public ResponseEntity<BaseResponse> codeException(CodeException ex) {
        return ResponseEntity.ok(BaseResponse.builder()
                .code(ResponseMessage.CODE_NOT_FOUND.getCode())
                .message(ResponseMessage.CODE_NOT_FOUND.getMessage())
                .build());
    }
    @ExceptionHandler(ConnectionException.class)
    public ResponseEntity<BaseResponse> connectionException(ConnectionException ex) {
        return ResponseEntity.ok(BaseResponse.builder()
                .code(ResponseMessage.CONNECTION_REFUSED.getCode())
                .message(ResponseMessage.CONNECTION_REFUSED.getMessage())
                .build());
    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<BaseResponse> userException(UserException ex) {
        return ResponseEntity.ok(BaseResponse.builder()
                .code(ResponseMessage.USER_NOT_FOUND.getCode())
                .message(ResponseMessage.USER_NOT_FOUND.getMessage())
                .build());
    }

}
