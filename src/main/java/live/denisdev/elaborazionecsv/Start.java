package live.denisdev.elaborazionecsv;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class Start extends Application {
    private static final Coordinate INITIAL_COORDINATE = new Coordinate(41.9028, 12.4964);
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("hello-view.fxml"));
        BorderPane root = fxmlLoader.load();
        MapView mapView = new MapView();
        mapView.initialize();
        mapView.setCenter(INITIAL_COORDINATE);
        mapView.setZoom(10);
        root.setCenter(mapView);
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("ReNDiS");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(_ -> mapView.close());
        mapView.initializedProperty().addListener((obs, wasInitialized, isNowInitialized) -> {
            if (isNowInitialized) {
                try (BufferedReader reader = new BufferedReader(
                        new FileReader(Start.class.getResource("karaje.csv").getFile()))) {
                    String line;
                    int i = 0;
                    while ((line = reader.readLine()) != null) {
                        if (i == 0) {
                            i++;
                            continue;
                        }
                        String[] parts = line.split(";");
                        double lat = Double.parseDouble(parts[11]);
                        double lon = Double.parseDouble(parts[12]);
                        URL iconUrl = Start.class.getResource("marker.png");
                        Marker marker = new Marker(iconUrl, 200, 200).setVisible(true);
                        marker.setPosition(new Coordinate(lat, lon));
                        mapView.addMarker(marker);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void main(String[] args) {
        launch();
    }
}