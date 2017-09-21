import com.sun.javafx.collections.MappingChange;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class Tests {
    private WebClient client = new WebClient();

    @Test
    public void compareImages() throws IOException {
        List<Photo> solDatePhotos = client.getSolDatePhotos();
        List<Photo> firstTenSol = solDatePhotos.subList(0, 10);

        String earthDate = firstTenSol.get(0).getEarthDate();

        List<Photo> earthDatePhotos = client.getEarthDatePhotos(earthDate);
        List<Photo> firstTenEarth = earthDatePhotos.subList(0, 10);

        boolean result = false;
        for (int i = 0; i < 9; i++){
            Photo solPhoto = firstTenSol.get(i);
            Photo earthPhoto = firstTenEarth.get(i);

            boolean tempResult = ImageCompare.AreEqual(solPhoto.getImgSrc(), earthPhoto.getImgSrc());
            result |= tempResult;
        }

        Assert.assertTrue("All images should be the same", result);
    }

    @Test
    public void compareCameras() throws IOException {
        List<String> cameras = client.getCameraNames();

        HashMap<String, Integer> photosByCamera = new HashMap<>(cameras.size());
        for(String camera : cameras){
            client = new WebClient();
            List<Photo> photos = client.getPhotosByCameraName(camera);
            photosByCamera.put(camera, photos.size());
        }

        String maxPhotosCamera = photosByCamera.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
        String minPhotosCamera = photosByCamera.entrySet().stream().min((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();

        double maxPhotos = (double) photosByCamera.get(maxPhotosCamera);
        double minPhotos = (double) photosByCamera.get(minPhotosCamera);

        String messagePattern = "Camera %s has %s photos, what is more than 10 times photos as camera: %s with %s photos";

        if(minPhotos == 0) {
            Assert.assertTrue(String.format(messagePattern, maxPhotosCamera, maxPhotos, minPhotosCamera, minPhotos), maxPhotos < 10);
        }
        else {
            Assert.assertTrue(String.format(messagePattern, maxPhotosCamera, maxPhotos, minPhotosCamera, minPhotos), maxPhotos / minPhotos < 10);
        }
    }

    @BeforeClass
    public static void testSetup(){
    }
}
