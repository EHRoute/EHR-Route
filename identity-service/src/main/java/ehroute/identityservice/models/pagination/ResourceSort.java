package ehroute.identityservice.models.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceSort {

    private String sortBy;
    private SortOrder order;

}
