package ehroute.accountservice.models.app;

public class ApiRoutes {

    public interface Account {
        final String Main = "/accounts";
        final String ByID = "/accounts/{id}";
    }

}
