package transfer.demo.service;

import transfer.demo.request.PaymentRequest;
import transfer.demo.response.BaseResponse;

public interface ReceiveService {
    BaseResponse getPayment (PaymentRequest request);
}
