package ehroute.identityservice.models.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourcePage {

    private int number;
	private int offset;
	private Long seekId; // The ID which is used as a seek predicate for the Seek pagination method
	private int limit;
	private int size;

	private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

	public ResourcePage() {

		// Set default page number and size if not set
		this.number = ((number != 0) ? number : DEFAULT_PAGE_NUMBER);
		this.size = ((size != 0) ? size : DEFAULT_PAGE_SIZE);

		// Calculate page limit and offset
		offset = Math.max(0, (this.number - 1) * limit);
		limit = Math.max(1, this.size);

	}

}
