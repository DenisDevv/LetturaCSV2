package live.denisdev.elaborazionecsv;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.*;

public class Controller {

    private static final String PERCORSO_FILE = "/live/denisdev/elaborazionecsv/karaje.csv";
    private List<String[]> records;

    @FXML
    private TextField campoChiave;
    @FXML
    private TableView<String[]> tabellaRecords;
    @FXML
    private TableColumn<String[], String> colComune;
    @FXML
    private TableColumn<String[], String> colProvincia;
    @FXML
    private TableColumn<String[], String> colRegione;
    @FXML
    private TableColumn<String[], String> colProfondita;
    @FXML
    private TableColumn<String[], String> colMagnitudo;
    @FXML
    private TableColumn<String[], String> colUnitaMisuraMagnitudo;
    @FXML
    private TableColumn<String[], String> colFonte;
    @FXML
    private TableColumn<String[], String> colData;
    @FXML
    private TableColumn<String[], String> colAnno;
    @FXML
    private TableColumn<String[], String> colMese;
    @FXML
    private TableColumn<String[], String> colOra;
    @FXML
    private TableColumn<String[], String> colLatitudine;
    @FXML
    private TableColumn<String[], String> colLongitudine;

    @FXML
    public void initialize() throws IOException {
        records = leggiCSV(PERCORSO_FILE);
        setupTableColumns();
        aggiornaTabella();
    }

    public List<String[]> leggiCSV(String percorsoFile) throws IOException {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Controller.class.getResourceAsStream(percorsoFile))))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                records.add(linea.split(";"));
            }
        }
        return records;
    }

    private void setupTableColumns() {
        colComune.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        colProvincia.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        colRegione.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
        colProfondita.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));
        colMagnitudo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4]));
        colUnitaMisuraMagnitudo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[5]));
        colFonte.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[6]));
        colData.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[7]));
        colAnno.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[8]));
        colMese.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[9]));
        colOra.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[10]));
        colLatitudine.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[11]));
        colLongitudine.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[12]));
    }

    public void aggiornaTabella() {
        tabellaRecords.getItems().clear();
        for (String[] record : records) {
            tabellaRecords.getItems().add(record);
        }
    }

    @FXML
    public void cercaRecord() {
        String chiave = campoChiave.getText();
        for (String[] record : records) {
            if (record[0].equals(chiave)) {
                tabellaRecords.getItems().clear();
                tabellaRecords.getItems().add(record);
                return;
            }
        }
        mostraMessaggio("Record non trovato");
    }

    @FXML
    public void aggiungiRecord() {
        // Implementa la logica per aggiungere un record
        mostraMessaggio("Funzionalità non implementata");
    }

    @FXML
    public void modificaRecord() {
        // Implementa la logica per modificare un record
        mostraMessaggio("Funzionalità non implementata");
    }

    @FXML
    public void cancellaRecord() {
        String chiave = campoChiave.getText();
        for (String[] record : records) {
            if (record[0].equals(chiave)) {
                record[record.length - 1] = "true";
                aggiornaTabella();
                return;
            }
        }
        mostraMessaggio("Record non trovato");
    }

    private void mostraMessaggio(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}