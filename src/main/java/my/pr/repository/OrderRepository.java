package my.pr.repository;

import my.pr.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query(value = "select * from orders where email = :email", nativeQuery = true)
    List<Order> findByEmail(@Param("email") String email);

    @Query(value = "select * from orders where order_status in ('1','2') and ((now() - modification_time)> '24 hours' )", nativeQuery = true)
    List<Order> findForArchived();
}
