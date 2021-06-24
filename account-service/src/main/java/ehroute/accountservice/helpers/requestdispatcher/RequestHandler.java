package ehroute.accountservice.helpers.requestdispatcher;
import reactor.core.CorePublisher;


public interface RequestHandler<I extends ApiRequest, O extends CorePublisher> {
    O handle(I request);
}
