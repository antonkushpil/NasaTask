import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "photo_manifest"
})
public class CuriosityManifestModel {

    @JsonProperty("photo_manifest")
    public PhotoManifest photoManifest;

}
