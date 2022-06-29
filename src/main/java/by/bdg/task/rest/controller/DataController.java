package by.bdg.task.rest.controller;

import by.bdg.task.dataPlatform.model.Data;
import by.bdg.task.dataPlatform.repository.DataRepository;
import by.bdg.task.rest.converter.BasicConverter;
import by.bdg.task.rest.dto.DataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/data")
public class DataController {


    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
    private final DataRepository dataRepository;
    private final BasicConverter<Data, DataDto> converter;

    final HttpHeaders httpHeaders= new HttpHeaders();

    public DataController(DataRepository dataRepository, BasicConverter<Data, DataDto> converter) {
        this.dataRepository = dataRepository;
        this.converter = converter;
    }

    @GetMapping("/getAllData")
    public ResponseEntity<?> getAllData() {
        List<Data> dataList = (List<Data>) dataRepository.findAll();
        return new ResponseEntity<>(dataList.stream().map(d -> converter.convertToDto(d, DataDto.class)), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> createData(@RequestParam(value = "date", required = false) Date date,
                                                @RequestParam(value = "text", required = false) String text,
                                                @RequestParam(value = "image", required = false) String image,
                                                @RequestParam(value = "number", required = false) Integer number) {

        Data newData = new Data();
        newData.setDate(date);
        newData.setText(text);
        newData.setImage(image);
        newData.setNumber(number);

        dataRepository.save(newData);

        return new ResponseEntity<>(converter.convertToDto(newData, DataDto.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        logger.info("DELETE: try to delete Data by ID : " + id);
        try {
            dataRepository.deleteById(id);
            logger.info("Deleted successfully, ID Data: " + id);
        } catch (NoResultException e) {
            logger.error("This Data doesn`t exist, ID Data: " + id, e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
