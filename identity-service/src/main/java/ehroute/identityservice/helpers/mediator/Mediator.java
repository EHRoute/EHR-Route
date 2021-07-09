package ehroute.identityservice.helpers.mediator;

public interface Mediator {
   /**
    * 
    * @param <T>     Type of the response that will be returned
    * @param request Request that will be handled
    * @return        Result of the executed handler
    */
   <T> T dispatch(final Request<T> request);
}
