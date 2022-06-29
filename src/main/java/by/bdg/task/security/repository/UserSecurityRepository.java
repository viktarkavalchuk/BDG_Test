package by.bdg.task.security.repository;

import by.bdg.task.security.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityRepository extends PagingAndSortingRepository<User, Long> {
    User findByLogin(String userName);
}