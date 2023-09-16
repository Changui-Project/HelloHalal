package cu.dev.halal.controller;


import cu.dev.halal.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/image")
public class ImageController {
    private final static Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final ImageService imageService;

    public ImageController(
            @Autowired ImageService imageService
    ){
        this.imageService = imageService;
    }

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<?> download(@PathVariable("id") Long id){
        try{
            byte[] image = this.imageService.download(id);
            if(image != null){
                logger.info("asd"+image.toString());
                return ResponseEntity.status(200).body(image);
            }
            return ResponseEntity.status(404).body("not found");
        }catch (NullPointerException e){
            return ResponseEntity.status(404).body("not found");
        }
    }

}
