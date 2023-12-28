package transfer.demo.service;

import transfer.demo.request.GlobalRequest;
import transfer.demo.response.BaseResponse;

public interface SendService {
    BaseResponse sendPayment(GlobalRequest globalRequest);
}
