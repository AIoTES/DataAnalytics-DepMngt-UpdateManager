package eu.hopu.activage.services;

import eu.hopu.activage.services.dto.ImageInfo;

import java.util.List;

public interface RegistryService {

    List<String> getImages();

    List<String> getImagTags(String imageId);

    ImageInfo getImageInfo(String imageId);

    ImageInfo addImageInfo(String imageId, String imageInfo);

    ImageInfo updateImageInfo(String imageId, String imageInfo);

    boolean deleteImageInfo(String imageId);

}
