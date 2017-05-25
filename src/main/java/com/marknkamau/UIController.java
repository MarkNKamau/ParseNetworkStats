package com.marknkamau;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marknkamau.Exceptions.*;
import com.marknkamau.models.JSONOutput;
import com.marknkamau.utils.FileInteraction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class UIController implements Initializable {

    @FXML
    private TextField txtInputFile;
    @FXML
    private Button btnInputFile;
    @FXML
    private TextField txtOutputFolder;
    @FXML
    private Button btnOutputFolder;
    @FXML
    private CheckBox checkOutputText;
    @FXML
    private CheckBox checkOutputCSV;
    @FXML
    private CheckBox checkOutputJSON;
    @FXML
    private TextField txtOutputFile;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea textArea;

    private String outputFolder;
    private Presenter presenter;
    private String date;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    public void selectInput() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select input file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));

        File input = fileChooser.showOpenDialog(btnInputFile.getScene().getWindow());
        if (input != null) {
            txtInputFile.setText(input.getAbsolutePath());
        }
    }

    @FXML
    public void selectOutput() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Output directory");

        File output = directoryChooser.showDialog(btnOutputFolder.getScene().getWindow());
        if (output != null) {
            txtOutputFolder.setText(output.getPath());
        }
    }

    @FXML
    public void parse() {
        if (fieldsAreEmpty()) {
            showErrorAlert(new EmptyFields());
            return;
        }

        outputFolder = txtOutputFolder.getText().trim();
        Path sourceFile = new File(txtInputFile.getText().trim()).toPath();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
        LocalDate newDate = datePicker.getValue();
        date = newDate.format(formatter);

        String outputFile = txtOutputFile.getText().trim();
        try {
            presenter = new Presenter(date, sourceFile, outputFolder, outputFile);
        } catch (Exception e) {
            showErrorAlert(e);
            return;
        }

        if (!checkOutputText.isSelected() && !checkOutputCSV.isSelected() && !checkOutputJSON.isSelected()) {
            showErrorAlert(new NoOutputSelected());
            return;
        }
        textArea.clear();

        if (checkOutputText.isSelected()) {
            presenter.createTXTOutput(file -> textArea.appendText("Saved to " + file + " at " + getFormattedCurrentTime() + "\n"));
        }

        if (checkOutputCSV.isSelected()) {
            presenter.createCSVOutput(file -> textArea.appendText("Saved to " + file + " at " + getFormattedCurrentTime() + "\n"));
        }

        if (checkOutputJSON.isSelected()) {
            presenter.createJsonOutput(file -> textArea.appendText("Saved to " + file + " at " + getFormattedCurrentTime() + "\n"));
        }

    }

    private boolean fieldsAreEmpty() {
        return txtInputFile.getText().trim().isEmpty() || txtOutputFile.getText().trim().isEmpty();
    }

    private void showErrorAlert(Exception e) {
        if (e instanceof ArrayIndexOutOfBoundsException) {
            e = new InputFileMissingExtension();
        }
        String title;
        if (e instanceof BaseException) {
            title = ((BaseException) e).getTitle();
        } else {
            title = e.getMessage();
        }

        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.show();
    }

    private String getFormattedCurrentTime() {
        return DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
    }

    @FXML
    private void clearFields() {
        txtInputFile.clear();
        txtOutputFolder.clear();
        txtOutputFile.clear();
        textArea.clear();
    }
}
