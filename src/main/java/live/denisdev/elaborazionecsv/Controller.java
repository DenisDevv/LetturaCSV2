package live.denisdev.elaborazionecsv;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
        aggiungiCampoMiovalore();
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

    private void aggiungiCampoMiovalore() {
        for (String[] record : records) {
            String[] newRecord = Arrays.copyOf(record, record.length + 2);
            newRecord[record.length] = String.valueOf(ThreadLocalRandom.current().nextInt(10, 21));
            newRecord[record.length + 1] = "false"; // Logical deletion marker
            records.set(records.indexOf(record), newRecord);
        }
    }

    public int contaCampi(String[] record) {
        return record.length;
    }

    public int lunghezzaMassimaRecord() {
        int maxLength = 0;
        for (String[] record : records) {
            int length = Arrays.stream(record).mapToInt(String::length).sum();
            if (length > maxLength) {
                maxLength = length;
            }
        }
        return maxLength;
    }

    public void rendiDimensioneFissa() {
        int maxLength = lunghezzaMassimaRecord();
        for (String[] record : records) {
            int currentLength = Arrays.stream(record).mapToInt(String::length).sum();
            int spacesToAdd = maxLength - currentLength;
            record[record.length - 1] += " ".repeat(spacesToAdd);
        }
    }

    @FXML
    public void aggiungiRecord() {
        String[] newRecord = new String[]{"NuovoComune", "NuovaProvincia", "NuovaRegione", "0", "0", "Unità", "Fonte", "Data", "Anno", "Mese", "Ora", "Latitudine", "Longitudine", String.valueOf(ThreadLocalRandom.current().nextInt(10, 21)), "false"};
        records.add(newRecord);
        aggiornaTabella();
    }

    @FXML
    public void modificaRecord() {
        String chiave = campoChiave.getText();
        for (String[] record : records) {
            if (record[0].equals(chiave)) {
                record[1] = "ProvinciaModificata"; // Example modification
                aggiornaTabella();
                return;
            }
        }
        mostraMessaggio("Record non trovato");
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
        TableColumn<String[], String> colMiovalore = new TableColumn<>("Miovalore");
        colMiovalore.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[13]));
        TableColumn<String[], String> colCancellato = new TableColumn<>("Cancellato");
        colCancellato.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[14]));
        tabellaRecords.getColumns().addAll(colMiovalore, colCancellato);
    }

    @FXML
    public void cercaRecord() {
        String chiave = campoChiave.getText();
        for (String[] record : records) {
            if (record[0].equals(chiave) && !record[record.length - 1].equals("true")) {
                tabellaRecords.getItems().clear();
                tabellaRecords.getItems().add(record);
                return;
            }
        }
        mostraMessaggio("Record non trovato");
    }

    public void aggiornaTabella() {
        tabellaRecords.getItems().clear();
        for (String[] record : records) {
            tabellaRecords.getItems().add(record);
        }
    }

    private void mostraMessaggio(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
