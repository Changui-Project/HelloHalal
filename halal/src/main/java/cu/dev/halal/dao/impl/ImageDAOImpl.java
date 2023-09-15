package cu.dev.halal.dao.impl;

import cu.dev.halal.dao.ImageDAO;
import cu.dev.halal.entity.ImageEntity;
import cu.dev.halal.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ImageDAOImpl implements ImageDAO {
    private final static Logger logger = LoggerFactory.getLogger(ImageDAO.class);
    private final ImageRepository imageRepository;

    public ImageDAOImpl(
            @Autowired ImageRepository imageRepository
    ){
        this.imageRepository = imageRepository;
    }

    @Override
    public ImageEntity upload(ImageEntity imageEntity) {
        return this.imageRepository.save(imageEntity);
    }

    @Override
    public ImageEntity download(Long id) {

        if(this.imageRepository.existsById(id)){
            return this.imageRepository.getById(id);
        }else{
            return ImageEntity.builder().image(null).build();
        }
    }
}
