module live.denisdev.elaborazionecsv {
    requires javafx.fxml;
    requires com.sothawo.mapjfx;
    requires javafx.controls;


    opens live.denisdev.elaborazionecsv to javafx.fxml;
    exports live.denisdev.elaborazionecsv;
}