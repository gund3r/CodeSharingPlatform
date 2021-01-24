package platform.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.Code;

import java.util.List;
import java.util.UUID;

@Repository
public interface SnippetsRepository extends CrudRepository<Code, UUID> {

    @Query(value = "SELECT * FROM CODE WHERE time = 0 AND views = 0 ORDER BY local_date_time DESC LIMIT 10", nativeQuery = true)
    List<Code> findAllByTimeAndViewsOrderByDate();
}