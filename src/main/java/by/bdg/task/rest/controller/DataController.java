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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllData() {
        List<Data> dataList = (List<Data>) dataRepository.findAll();
        return new ResponseEntity<>(dataList.stream().map(d -> converter.convertToDto(d, DataDto.class)), HttpStatus.OK);
    }


//    @GetMapping("/getByName")
//    public ResponseEntity<?> getAnnouncementByName(@RequestParam String name) {
//        List<Announcement> getByName = announcementService.getAll()
//                .stream().filter(x -> x.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
//
//        return new ResponseEntity<>(getByName.stream().map(d -> converter.convertToDto(d, AnnouncementDto.class))
//                .collect(Collectors.toList()),
//                HttpStatus.OK);
//    }


//    @GetMapping("/getByNameAndPrice")
//    public ResponseEntity<?> getAnnouncementByNameAndPrice(@RequestParam String name, Integer price) {
//        List<Announcement> getByNameAndPrice = announcementService.getAll()
//                .stream().filter(x -> x.getName().equalsIgnoreCase(name))
//                .filter(x -> x.getPrice() < price)
//                .collect(Collectors.toList());
//
//        return new ResponseEntity<>(getByNameAndPrice.stream().map(d -> converter.convertToDto(d, AnnouncementDto.class))
//                .collect(Collectors.toList()),
//                HttpStatus.OK);
//    }
//
//
//    @GetMapping("/getByPriceLowerThan")
//    public ResponseEntity<?> getAnnouncementByPrice(@RequestParam Integer price) {
//        List<Announcement> getByPrice = announcementService.getAll()
//                .stream().filter(x -> x.getPrice() < price).collect(Collectors.toList());
//
//        return new ResponseEntity<>(getByPrice.stream().map(d -> converter.convertToDto(d, AnnouncementDto.class))
//                .collect(Collectors.toList()),
//                HttpStatus.OK);
//    }
//
//
//    @GetMapping("/getSalesHistory")
//    public ResponseEntity<?> getAllSalesHistory() {
//        List<Announcement> getSalesHistoryByUser = announcementService.getClosedAnnouncements();
//
//        return new ResponseEntity<>(getSalesHistoryByUser.stream()
//                .map(d -> converter.convertToDto(d, AnnouncementDto.class))
//                .collect(Collectors.toList()), HttpStatus.OK);
//    }
//
//
//    @GetMapping("/getSalesHistory/{id}")
//    public ResponseEntity<?> getSalesHistory(@PathVariable("id") int id) {
//        User user = userService.getById(id);
//        List<Announcement> getSalesHistoryByUser = announcementService.getClosedAnnouncements()
//                .stream()
//                .filter(x -> x.getUser().getLogin().equals(user.getLogin()))
//                .collect(Collectors.toList());
//
//        return new ResponseEntity<>(getSalesHistoryByUser.stream()
//                .map(d -> converter.convertToDto(d, AnnouncementDto.class))
//                .collect(Collectors.toList()), HttpStatus.OK);
//    }
//
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PatchMapping("/vip/{id}")
//    public ResponseEntity<?> setVip(@PathVariable("id") int id,
//                                    @RequestParam(value = "vip") Boolean vip) {
//        logger.info("UPDATE: try to update ID Announcement: " + id);
//        try {
//            Announcement announcement = announcementService.getById(id);
//            announcement.setVip(vip);
//            announcementService.update(announcement);
//            logger.info("UPDATE: update ID Announcement: " + id);
//            return new ResponseEntity<>("UPDATE: update ID Announcement: " + id, HttpStatus.OK);
//        } catch (NoResultException e) {
//            logger.error("UPDATE: Can't update ad ID. It doesn't exist: " + id, e);
//            return new ResponseEntity<>("UPDATE: Can't update ad ID. It doesn't exist: " + id, HttpStatus.OK);
//        }
//    }
//
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @PatchMapping("/{id}")
//    public ResponseEntity<?> updateAnnouncement(@PathVariable("id") int id,
//                                                @RequestParam(value = "name", required = false) String name,
//                                                @RequestParam(value = "price", required = false) Integer price,
//                                                @RequestParam(value = "description", required = false) String description,
//                                                @RequestParam(value = "sold", required = false) Boolean sold) {
//
//        logger.info("UPDATE: try to update ID Announcement: " + id);
//        try {
//            User userRequest = userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
//            Announcement announcement = announcementService.getById(id);
//
//            if (announcement.getUser().getLogin().equalsIgnoreCase(userRequest.getLogin())) {
//                if (name != null) {
//                    announcement.setName(name);
//                }
//                if (price != null) {
//                    announcement.setPrice(price);
//                }
//                if (description != null) {
//                    announcement.setDescription(description);
//                }
//                if (sold != null && sold == true) {
//                    announcement.setSold(sold);
//                    announcement.setEndDate(new Date());
//                } else if (sold != null && sold == false) {
//                    announcement.setSold(sold);
//                    announcement.setEndDate(null);
//                }
//                announcementService.update(announcement);
//                logger.info("UPDATE: update succsessfully: " + id);
//                return new ResponseEntity<>(converter.convertToDto(announcement, AnnouncementDto.class), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("UPDATE: This user can`t change this announcement", HttpStatus.OK);
//            }
//        } catch (NoResultException e) {
//            logger.error("UPDATE: This Announcement doesn`t exist, ID Announcement: " + id);
//            return new ResponseEntity<>("UPDATE: This Announcement doesn`t exist, ID Announcement: " + id, HttpStatus.OK);
//        }
//    }
//
//
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @PostMapping
//    public ResponseEntity<?> createAnnouncement(@RequestParam(value = "name") String name,
//                                                @RequestParam(value = "price") Integer price,
//                                                @RequestParam(value = "description", required = false) String description) {
//
//        Announcement announcement = new Announcement();
//        User userRequest = userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
//        announcement.setName(name);
//        announcement.setPrice(price);
//        announcement.setStartDate(new Date());
//        announcement.setDescription(description);
//        announcement.setUser(userRequest);
//        announcement.setVip(false);
//        announcement.setRating(5.0);
//
//        announcementService.create(announcement);
//
//        return new ResponseEntity<>(converter.convertToDto(announcement, AnnouncementDto.class), HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable("id") int id) {
//
//        logger.info("DELETE: try to delete ID Announcement: " + id);
//        try {
//            announcementService.delete(id);
//            logger.info("Deleted successfully, ID Announcement: " + id);
//        } catch (NoResultException e) {
//            logger.error("This Announcement doesn`t exist, ID Announcement: " + id, e);
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
