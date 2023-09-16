package cu.dev.halal.service.impl;

import cu.dev.halal.dao.ImageDAO;
import cu.dev.halal.entity.ImageEntity;
import cu.dev.halal.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class ImageServiceImpl implements ImageService {
    private final static Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final ImageDAO imageDAO;

    public ImageServiceImpl(
            @Autowired ImageDAO imageDAO
    ){
        this.imageDAO = imageDAO;
    }

    @Override
    public String upload(byte[] multipartFile) throws IOException {
        // 이미지 업로드
        ImageEntity imageEntity = this.imageDAO.upload(ImageEntity.builder().image(multipartFile).build());
        return "/image/"+imageEntity.getId();
    }

    @Override
    public byte[] download(Long id) {
        // 이미지 다운로드
        ImageEntity imageEntity = this.imageDAO.download(id);
        try{
            if(imageEntity.getImage() != null) {
                return imageEntity.getImage();
            }
            return null;
        }catch (NullPointerException e){
            return null;
        }
    }
}
