package com.marknkamau;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marknkamau.Exceptions.EmptyFields;
import com.marknkamau.Exceptions.IncorrectInputFormat;
import com.marknkamau.Exceptions.InputFileMissingExtension;
import com.marknkamau.Exceptions.BaseException;
import com.marknkamau.models.JSONOutput;
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

        try {
            presenter = new Presenter(date, sourceFile);
        } catch (Exception e) {
            showErrorAlert(e);
            return;
        }

        textArea.clear();
        if (checkOutputText.isSelected()) {
            outputTxtFile();
        }

        if (checkOutputCSV.isSelected()) {
            outputCsvFile();
        }

        if (checkOutputJSON.isSelected()) {
            outputJsonFile();
        }

    }

    private void outputTxtFile(){
        List<String> textOutput;
        try {
            textOutput = presenter.getTextOutput();
        } catch (IncorrectInputFormat e) {
            showErrorAlert(e);
            return;
        }

        Path target = new File(outputFolder + "\\" + txtOutputFile.getText().trim() + ".txt").toPath();
        try {
            presenter.writeToFile(target, textOutput, () -> {
                Date completed = Calendar.getInstance().getTime();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                textArea.appendText("Saved to " + target.toAbsolutePath() + " at " + timeFormat.format(completed) + "\n");
            });
        } catch (Exception e) {
            showErrorAlert(e);
        }
    }

    private void outputCsvFile() {
        try {
            Path target = new File(outputFolder + "\\" + txtOutputFile.getText().trim() + ".csv").toPath();
            presenter.writeToFile(target, presenter.getCSVOutput(), () -> {
                Date completed = Calendar.getInstance().getTime();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                textArea.appendText("Saved to " + target.toAbsolutePath() + " at " + timeFormat.format(completed) + "\n");
            });
        } catch (Exception e) {
            showErrorAlert(e);
        }
    }

    private void outputJsonFile() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String outputJSON = gson.toJson(new JSONOutput(date, presenter.getJSONOutput()));
            List<String> list = new ArrayList<>();
            list.add(outputJSON);

            Path target = new File(outputFolder + "\\" + txtOutputFile.getText().trim() + ".json").toPath();
            presenter.writeToFile(target, list, () -> {
                Date completed = Calendar.getInstance().getTime();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                textArea.appendText("Saved to " + target.toAbsolutePath() + " at " + timeFormat.format(completed) + "\n");
            });
        } catch (Exception e) {
            showErrorAlert(e);
        }
    }

    private boolean fieldsAreEmpty() {
        return txtInputFile.getText().trim().isEmpty() || txtOutputFile.getText().trim().isEmpty();
    }

    private void showErrorAlert(Exception e) {
        e.printStackTrace();
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

    @FXML
    private void clearFields() {
        txtInputFile.clear();
        txtOutputFolder.clear();
        txtOutputFile.clear();
        textArea.clear();
    }
}
