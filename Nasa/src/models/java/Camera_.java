import com.fasterxml.jackson.annotation.JsonProperty;

public class Camera_ {

    @JsonProperty("name")
    private String name;
    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
