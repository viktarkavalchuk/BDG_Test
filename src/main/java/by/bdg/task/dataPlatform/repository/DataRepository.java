package by.bdg.task.dataPlatform.repository;

import by.bdg.task.dataPlatform.model.Data;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends PagingAndSortingRepository<Data, Long> {
}
