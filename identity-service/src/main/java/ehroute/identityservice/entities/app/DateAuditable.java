package ehroute.identityservice.entities.app;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import lombok.Data;


@Data
public class DateAuditable {
    
    @CreatedDate
    @Column(value = "created_at")
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(value = "updated_at")
    private OffsetDateTime updatedAt;

}
