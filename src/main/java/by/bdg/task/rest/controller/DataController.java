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
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping(value = "/data")
public class DataController {


    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    private static String PIC_FOLDER = "images//";
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
    public ResponseEntity<?> createData(@RequestParam(value = "date", required = false) String date,
                                                @RequestParam(value = "text", required = false) String text,
                                                @RequestParam(value = "image", required = false) MultipartFile image,
                                                @RequestParam(value = "number", required = false) Integer number) {
        Data newData = new Data();
        try {
            // Get the file and save it somewhere
            byte[] bytes = image.getBytes();

            String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

            Path path = Paths.get(PIC_FOLDER + currentDate + "_" + image.getOriginalFilename());
            Files.write(path, bytes);
            newData.setImage(path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (date != null) {
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                newData.setDate(formatter.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (text != null) {
            newData.setText(text);
        }
        if (number != null) {
            newData.setNumber(number);
        }
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
