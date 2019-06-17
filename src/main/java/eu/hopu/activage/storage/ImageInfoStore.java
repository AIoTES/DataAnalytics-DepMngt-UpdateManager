package eu.hopu.activage.storage;

import eu.hopu.activage.services.dto.ImageInfo;

public interface ImageInfoStore {

    ImageInfo getImageInfo(String imageId);

    ImageInfo addImageInfo(String imageId, String imageInfo);

    ImageInfo updateImageInfo(String imageId, String imageInfo);

    boolean deleteImageInfo(String imageId);

}
