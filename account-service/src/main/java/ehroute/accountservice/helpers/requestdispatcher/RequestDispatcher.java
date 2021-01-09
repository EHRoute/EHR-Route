package ehroute.accountservice.helpers.requestdispatcher;
import reactor.core.CorePublisher;
import java.util.HashMap;
import java.util.Map;


public abstract class RequestDispatcher {

    protected Map<Class, RequestHandler> preparedMap = new HashMap<>();

    public <I extends ApiRequest, O extends CorePublisher> O dispatch(I request) {
        return (O) preparedMap.get(request.getClass()).handle(request);
    }

}
