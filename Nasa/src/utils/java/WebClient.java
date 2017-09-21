import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class WebClient{

    private static String nasaApiMarsDate = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=RmVNLgTbVNtG5swaXpoCSTJyvz2POXunTSas15Mq";
    private static String nasaApiEarthDate = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=%s&api_key=RmVNLgTbVNtG5swaXpoCSTJyvz2POXunTSas15Mq";
    private static String nasaApiByCamera = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&camera=%s&api_key=RmVNLgTbVNtG5swaXpoCSTJyvz2POXunTSas15Mq";
    private static String nasaApiCuriosityManifest = "https://api.nasa.gov/mars-photos/api/v1/manifests/Curiosity?api_key=RmVNLgTbVNtG5swaXpoCSTJyvz2POXunTSas15Mq";

    private CloseableHttpClient client;

    public WebClient() {
        client = HttpClients.createDefault();
    }

    public List<Photo> getSolDatePhotos() throws IOException {
        HttpGet request = new HttpGet(nasaApiMarsDate);
        HttpResponse response = client.execute(request);
        InputStream responseStream = response.getEntity().getContent();

        ObjectMapper mapper = new ObjectMapper();
        PhotosModel photos = mapper.readValue(readStream(responseStream), PhotosModel.class);

        return photos.getPhotos();
    }

    public List<Photo> getEarthDatePhotos(String earthDate) throws IOException {
        HttpGet request = new HttpGet(String.format(nasaApiEarthDate, earthDate));
        HttpResponse response = client.execute(request);
        InputStream responseStream = response.getEntity().getContent();

        ObjectMapper mapper = new ObjectMapper();
        PhotosModel photos = mapper.readValue(readStream(responseStream), PhotosModel.class);

        return photos.getPhotos();
    }

    public List<Photo> getPhotosByCameraName(String cameraName) throws IOException {
        HttpGet request = new HttpGet(String.format(nasaApiByCamera, cameraName));
        HttpResponse response = client.execute(request);
        InputStream responseStream = response.getEntity().getContent();

        ObjectMapper mapper = new ObjectMapper();
        PhotosModel photos = mapper.readValue(readStream(responseStream), PhotosModel.class);

        return photos.getPhotos();
    }

    public List<String> getCameraNames() throws IOException {
        HttpGet request = new HttpGet(nasaApiCuriosityManifest);
        HttpResponse response = client.execute(request);
        InputStream responseStream = response.getEntity().getContent();

        ObjectMapper mapper = new ObjectMapper();
        CuriosityManifestModel photos = mapper.readValue(readStream(responseStream), CuriosityManifestModel.class);
        PhotoFromManifest thousendSolPhotos = getPhotosBySol(photos.photoManifest.photos, 1000);

        return thousendSolPhotos.cameras;
    }

    private String readStream(InputStream stream){
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder result = new StringBuilder();
        String line;
        try {
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private PhotoFromManifest getPhotosBySol(List<PhotoFromManifest> photos, int solDate){
        for(PhotoFromManifest photo : photos){
            if(photo.sol.equals(solDate)) return photo;
        }
        return null;
    }

}
