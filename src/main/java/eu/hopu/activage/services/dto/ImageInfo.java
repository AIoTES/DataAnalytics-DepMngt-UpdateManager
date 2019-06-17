package eu.hopu.activage.services.dto;

public class ImageInfo {

    private String imageId;
    private String imageInfo;

    public ImageInfo() {
    }

    public ImageInfo(String imageId, String imageInfo) {
        this.imageId = imageId;
        this.imageInfo = imageInfo;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(String imageInfo) {
        this.imageInfo = imageInfo;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "imageId='" + imageId + '\'' +
                ", imageInfo='" + imageInfo + '\'' +
                '}';
    }
}
