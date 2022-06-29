package by.bdg.task.dataPlatform.service.interfaces;

import by.bdg.task.dataPlatform.model.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataService {
    void create(Data data);

    void delete(Long id);

    void update(Data data);

    Data getById(Long id);

    List<Data> getAll();
}
