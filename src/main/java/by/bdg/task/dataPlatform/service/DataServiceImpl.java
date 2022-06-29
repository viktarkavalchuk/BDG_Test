package by.bdg.task.dataPlatform.service;

import by.bdg.task.dataPlatform.model.Data;
import by.bdg.task.dataPlatform.repository.DataRepository;
import by.bdg.task.dataPlatform.service.interfaces.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("jpaDataService")
@Repository
@Transactional
public class DataServiceImpl implements DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);
    private final DataRepository dataRepository;

    public DataServiceImpl(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public void create(Data data) {
        logger.info("Try to create data: " + data.getId());
        try {
            dataRepository.save(data);
        } catch (Exception e) {
            logger.error("Error creating a record! " + e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Try to delete data: " + id);
        try {
            dataRepository.delete(getById(id));
        } catch (Exception e) {
            logger.error("Error deleting a record! " + e);
        }
    }

    @Override
    public void update(Data data) {
        logger.info("Try to update data: " + data.getId());
        try {
            dataRepository.save(data);
        } catch (Exception e) {
            logger.error("Error updating a record! " + e);
        }
    }

    @Override
    public Data getById(Long id) {
        Data newData = dataRepository.findById(id).orElse(null);
        return newData;
    }

    @Override
    public List<Data> getAll() {
        List<Data> dataList = new ArrayList<>();
        dataList.add((Data) dataRepository.findAll());
        return dataList;
    }
}
