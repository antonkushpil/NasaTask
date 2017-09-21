import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "landing_date",
        "launch_date",
        "status",
        "max_sol",
        "max_date",
        "total_photos",
        "photos"
})
public class PhotoManifest {

    @JsonProperty("name")
    public String name;
    @JsonProperty("landing_date")
    public String landingDate;
    @JsonProperty("launch_date")
    public String launchDate;
    @JsonProperty("status")
    public String status;
    @JsonProperty("max_sol")
    public Integer maxSol;
    @JsonProperty("max_date")
    public String maxDate;
    @JsonProperty("total_photos")
    public Integer totalPhotos;
    @JsonProperty("photos")
    public List<PhotoFromManifest> photos = null;

}