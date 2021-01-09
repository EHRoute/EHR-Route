package ehroute.providerservice;
// import ehroute.providerservice.configuration.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.HashMap;


// @RefreshScope
@RestController
@RequestMapping("test")
public class TestController {

    /*private ServiceConfig serviceConfig;
    
    @Autowired
    public TestController(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }*/

    /*@GetMapping
    public Mono<String> getTestData() {
        *//*return Mono.just(new HashMap() {{ put("foo", 1); put("bar", 2); put("foo2", 3); }});*//*
        return Mono.just(serviceConfig.getTestMessage());
    }*/

    @GetMapping()
    public Mono<String> ping() {
        return Mono.just("Hello World");
    }

}
