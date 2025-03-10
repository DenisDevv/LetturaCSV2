package live.denisdev.elaborazionecsv;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
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
    }
    public static void main(String[] args) {
        launch();
    }
}