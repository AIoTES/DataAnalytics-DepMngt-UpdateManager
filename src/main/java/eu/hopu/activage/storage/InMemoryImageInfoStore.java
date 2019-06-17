package eu.hopu.activage.storage;

import eu.hopu.activage.services.dto.ImageInfo;

import java.util.HashMap;

public class InMemoryImageInfoStore implements ImageInfoStore {

    private HashMap<String, ImageInfo> infoByImageId;

    public InMemoryImageInfoStore() {
        infoByImageId = new HashMap<>();
    }

    @Override
    public ImageInfo getImageInfo(String imageId) {
        return infoByImageId.get(imageId);
    }

    @Override
    public ImageInfo addImageInfo(String imageId, String imageInfo) {
        ImageInfo info = new ImageInfo(imageId, imageInfo);
        infoByImageId.put(imageId, info);
        return info;
    }

    @Override
    public ImageInfo updateImageInfo(String imageId, String imageInfo) {
        return addImageInfo(imageId, imageInfo);
    }

    @Override
    public boolean deleteImageInfo(String imageId) {
        ImageInfo response = infoByImageId.remove(imageId);
        return response != null;
    }

}
