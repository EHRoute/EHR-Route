package ehroute.identityservice.models.resource;

import java.util.List;

import org.jooq.Condition;
import org.jooq.SortField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceQuery {

    private ResourcePage page;
    private List<SortField<?>> sortFields;
    private List<Condition> filters;

}
