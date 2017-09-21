import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PhotosModel {

    @JsonProperty("photos")
    private List<Photo> photos = null;

    @JsonProperty("photos")
    public List<Photo> getPhotos() {
        return photos;
    }

    @JsonProperty("photos")
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

}

