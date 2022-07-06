package by.bdg.task.rest.controller;

import by.bdg.task.dataPlatform.model.Data;
import by.bdg.task.dataPlatform.repository.DataRepository;
import by.bdg.task.rest.converter.BasicConverter;
import by.bdg.task.rest.dto.DataDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import java.io.Console;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping(value = "/data")
public class DataController {

    @Autowired
    ServletContext servletContext;

    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    private static String PIC_FOLDER = "WEB-INF//images//";

    private final DataRepository dataRepository;
    private final BasicConverter<Data, DataDto> converter;

    public DataController(DataRepository dataRepository, BasicConverter<Data, DataDto> converter) {
        this.dataRepository = dataRepository;
        this.converter = converter;
    }

    @GetMapping("/getAllData")
    public ResponseEntity<?> getAllData() {
        List<Data> dataList = (List<Data>) dataRepository.findAll();
        return new ResponseEntity<>(dataList.stream().map(d -> converter.convertToDto(d, DataDto.class)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Data data = dataRepository.findById(id).orElse(null);
        if (data != null) {
            return new ResponseEntity<>(converter.convertToDto(data, DataDto.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = {"", "/{id}"})
    public ResponseEntity<?> createData(@PathVariable(value = "id", required = false) Long id,
                                        @RequestParam(value = "image", required = false) MultipartFile image,
                                        @RequestParam (value = "info", required = false) String info ){

        Data data = new Data();
        if(id != null){
            data = dataRepository.findById(id).orElse(null);
            if(data == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }


        if (info != null) {
            info = decode(info);
            ObjectMapper om = new ObjectMapper();
            try {
                Data newData = om.readValue(info, Data.class);
                if(newData.getDate() != null) data.setDate(newData.getDate());
                if(newData.getNumber() != null) data.setNumber(newData.getNumber());
                if(newData.getText() != null) data.setText(newData.getText());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        if (image != null) {
            try {
                // Get the file and save it to PIC_FOLDER
                byte[] bytes = image.getBytes();
                String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                Path path = Paths.get(servletContext.getRealPath(PIC_FOLDER) + currentDate + "_" + image.getOriginalFilename());
                String fileName = "images/" + currentDate + "_" + image.getOriginalFilename();

                Files.write(path, bytes);
                data.setImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Save to DB
        dataRepository.save(data);
        return new ResponseEntity<>(converter.convertToDto(data, DataDto.class), HttpStatus.OK);
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
