import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Customer {

    Long id;

    String name;

    char sex;

    boolean deleted;

    Date gmtCreated;

    LocalDate gmtModified;

    LocalDateTime bornTime;


    Customer other;

    List<Customer> customers;


}
