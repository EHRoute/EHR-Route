package ehroute.providerservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public Mono<HashMap> getTestData() {
        return Mono.just(new HashMap() {{ put("foo", 1); put("bar", 2); put("foo2", 3); }});
    }

}
