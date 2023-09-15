package cu.dev.halal.dao;

import cu.dev.halal.entity.ImageEntity;

public interface ImageDAO {

    public ImageEntity upload(ImageEntity imageEntity);
    public ImageEntity download(Long id);

}
