package ehroute.identityservice.models.pagination;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourcePageRequest {
    
    private String number;
	private String size;
	private String offset;
	private String limit;

    // private ResourcePage page;
    // private ResourceSort sort;
    // private List<ResourceFilter> filters;

}
