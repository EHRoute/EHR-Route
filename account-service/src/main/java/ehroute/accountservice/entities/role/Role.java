package ehroute.accountservice.entities.role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id private Long Id;
    @Column(value = "name") private String name;

}
