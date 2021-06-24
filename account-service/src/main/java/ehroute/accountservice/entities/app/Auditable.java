package ehroute.accountservice.entities.app;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import java.time.OffsetDateTime;


@Data
public class Auditable {

    @CreatedDate
    @Column(value = "created_at")
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(value = "updated_at")
    private OffsetDateTime updatedAt;

}
