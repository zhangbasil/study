import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDTO {

    Long id;

    String name;

    char sex;

    Boolean deleted;

    Date gmtCreated;

    LocalDate gmtModified;

    LocalDateTime bornTime;

    CustomerDTO other;

}
