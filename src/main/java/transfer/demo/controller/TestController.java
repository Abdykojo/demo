//package transfer.demo.controller;
//
//import io.micrometer.core.annotation.Timed;
//import io.micrometer.core.instrument.Counter;
//import io.micrometer.core.instrument.MeterRegistry;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "api/v1", method = {RequestMethod.GET, RequestMethod.POST})
//public class TestController {
//    private final Counter counter;
//
//    public TestController(MeterRegistry registry) {
//        counter = Counter.builder("custom_counter").description("A custom counter").register(registry);
//    }
//    @GetMapping("/my-endpoint")
//    @Timed(value = "greeting.time", description = "Time taken to return greeting")
//    public String myEndpoint() {
//        // Increment the counter each time this endpoint is called
//        counter.increment();
//        return "hello";
//    }
//}
