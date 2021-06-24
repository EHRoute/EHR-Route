package ehroute.accountservice.helpers.requestdispatcher;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


@Service
public class ApiRequestDispatcher extends RequestDispatcher {

    private final Map<String, RequestHandler> rawMap;

    public ApiRequestDispatcher(Map<String, RequestHandler> rawMap) {
        this.rawMap = rawMap;
    }

    @PostConstruct
    private void setUp() {
        if (rawMap != null && !rawMap.isEmpty()) {
            for (RequestHandler requestHandler : rawMap.values()) {
                Type request = ((ParameterizedType) requestHandler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
                preparedMap.put((Class) request, requestHandler);
            }
        }
    }

}
