package transfer.demo.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import transfer.demo.exception.ConnectionException;
import transfer.demo.models.entity.Transaction;
import transfer.demo.models.enums.ResponseMessage;
import transfer.demo.request.*;
import transfer.demo.response.BaseResponse;
import transfer.demo.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "api/v1", method = {RequestMethod.GET, RequestMethod.POST})
public class MyController {

    private final SendService sendService;
    private final ReceiveService receiveService;
    private final CashBoxService cashBoxService;
    private final BalanceService balanceService;
    private final FilteringService filteringService;
    private final Counter counter;
    private final MeterRegistry meterRegistry;

    public MyController(SendService sendService, ReceiveService receiveService, CashBoxService cashBoxService,
                        BalanceService balanceService, FilteringService filteringService, MeterRegistry registry, MeterRegistry meterRegistry) {
        this.sendService = sendService;
        this.receiveService = receiveService;
        this.cashBoxService = cashBoxService;
        this.balanceService = balanceService;
        this.filteringService = filteringService;
        counter = Counter.builder("custom_counter").description("A custom counter").register(registry);
        this.meterRegistry = meterRegistry;
    }
    @GetMapping("/my-endpoint")
    @Timed(value = "greeting.time", description = "Time taken to return greeting")
    public String myEndpoint() {
//            var counter = Counter.builder("my_counter").tag("hello",request.getContextPath()).register(meterRegistry);
//            counter.increment();
        return "templates/hello";
    }

    @PostMapping(value = "/firstPage")
    public String openFirstPage()  {
        counter.increment();
        return "templates/firstPage";
    }

    @PostMapping(value = "/sendPayment")
    public String send(Model model)  {
        model.addAttribute("globalRequest", new GlobalRequest());
        return "templates/sendPayment";
    }

    @PostMapping(value = "/check")
    public String sendPayment(@ModelAttribute("globalRequest") GlobalRequest globalRequest, Model model)  {
        System.out.println(globalRequest);
        BaseResponse baseResponse = sendService.sendPayment(globalRequest);
        model.addAttribute("baseResponse", baseResponse);
        return "templates/sendPaymentResult";
    }

    @PostMapping(value = "/getPayment")
    public String get(Model model)  {
        model.addAttribute("paymentRequest", new PaymentRequest());
        return "templates/getPayment";
    }

    @PostMapping(value = "/get")
    public String getPayment(@ModelAttribute("paymentRequest") PaymentRequest request, Model model)  {
        BaseResponse baseResponse = receiveService.getPayment(request);
        model.addAttribute("baseResponse", baseResponse);
        return "templates/getPaymentResult";
    }

    @PostMapping(value = "/create/cashbox")
    public ResponseEntity<?> createCashbox(@RequestBody CashBoxRequest request)  {
        return ResponseEntity.accepted().body(cashBoxService.save(request));
    }

    @PostMapping(value = "/create/balance")
    public ResponseEntity<?> createBalance(@RequestBody BalanceRequest request)  {
        return ResponseEntity.accepted().body(balanceService.createBalance(request));
    }

    @PostMapping(value = "/filtering")
    public String findAll(@ModelAttribute("filterRequest") FilterRequest request, Model model)  {
        List<Transaction> transactionList = filteringService.getFilteredData(request);
        model.addAttribute("transactionList",transactionList);
        return "templates/findAll";
    }
    @GetMapping(value = "/findTransactions")
    public String findTransactions( Model model, FilterRequest filterRequest )  {
        model.addAttribute("filterRequest", filterRequest);
        return "templates/find";
    }
    @GetMapping(value = "/findAll")
    public String find(@RequestParam (required = false, defaultValue = "0") int page,
                                  @RequestParam(required = false, defaultValue = "10") int size, Model model, FilterRequest filterRequest )  {
        List<Transaction> transactionList = filteringService.findAll(PageRequest.of(page, size));
        model.addAttribute("transactionList",transactionList);
        model.addAttribute("filterRequest", filterRequest);
        return "templates/findAll";
    }
}
