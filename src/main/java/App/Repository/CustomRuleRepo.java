package App.Repository;

import App.Entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomRuleRepo extends CrudRepository<Order , Long> {
}
