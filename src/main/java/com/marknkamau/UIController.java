package com.marknkamau;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marknkamau.models.JSONOutput;
import com.marknkamau.models.PingStats;
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
import java.text.DateFormat;
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

    private LocalDate dateChosen;
    private List<String> outputCSV;
    private List<String> outputTXT;
    private List<PingStats> JSONFormat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateChosen = LocalDate.now();
        datePicker.setValue(dateChosen);
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
        if (fieldsEmpty()) {
            showErrorAlert(new EmptyFieldsException());
            return;
        }

        String outputFolder = txtOutputFolder.getText().trim();
        Path sourceFile = new File(txtInputFile.getText().trim()).toPath();

        if (!validInputFile(sourceFile)) {
            return;
        }

        List<String> fileContents;
        try {
            fileContents = FileInteraction.readFileTokens(sourceFile, "Ping statistics for ");
        } catch (Exception e) {
            showErrorAlert(e);
            return;
        }

        if (fileContents == null || fileContents.isEmpty()) {
            showErrorAlert(new EmptyInputFileException());
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
        dateChosen = datePicker.getValue();
        String date = dateChosen.format(formatter);

        outputTXT = new ArrayList<>();
        outputTXT.add("Date: " + date);

        outputCSV = new ArrayList<>();
        outputCSV.add(date);
        outputCSV.add("IP ADDRESS, MIN, MAX, AVG, SENT, RECEIVED, LOST, % LOSS");

        JSONFormat = new ArrayList<>();

        for (String s : fileContents) {
            try {
                Map<String, String> dataMap = NetStatsParser.parse(s);
                if (dataMap == null) {
                    continue;
                }
                formatForText(dataMap);
            } catch (Exception e) {
                if (e instanceof ArrayIndexOutOfBoundsException) {
                    e = new InputFormatException();
                }
                showErrorAlert(e);
            }
        }

        textArea.clear();
        if (checkOutputText.isSelected()) {
            Path target = new File(outputFolder + "\\" + txtOutputFile.getText().trim() + ".txt").toPath();
            writeToFile(target, outputTXT);
        }

        if (checkOutputCSV.isSelected()) {
            for (String s : fileContents) {
                try {
                    Map<String, String> dataMap = NetStatsParser.parse(s);
                    if (dataMap == null) {
                        continue;
                    }
                    formatForCSV(dataMap);
                } catch (Exception e) {
                    if (e instanceof ArrayIndexOutOfBoundsException) {
                        e = new InputFormatException();
                    }
                    showErrorAlert(e);
                }
            }

            Path target = new File(outputFolder + "\\" + txtOutputFile.getText().trim() + ".csv").toPath();
            writeToFile(target, outputCSV);
        }

        if (checkOutputJSON.isSelected()) {
            for (String s : fileContents) {
                try {
                    Map<String, String> dataMap = NetStatsParser.parse(s);
                    if (dataMap == null) {
                        continue;
                    }
                    formatForJSON(dataMap);
                } catch (Exception e) {
                    if (e instanceof ArrayIndexOutOfBoundsException) {
                        e = new InputFormatException();
                    }
                    showErrorAlert(e);
                }
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String outputJSON = gson.toJson(new JSONOutput(date, JSONFormat));
            List<String> list = new ArrayList<>();
            list.add(outputJSON);

            Path target = new File(outputFolder + "\\" + txtOutputFile.getText().trim() + ".json").toPath();
            writeToFile(target, list);
        }

        textArea.appendText("\n");
        for (String s : outputTXT) {
            textArea.appendText(s + "\n");
        }
    }

    private boolean fieldsEmpty() {
        return txtInputFile.getText().trim().isEmpty() || txtOutputFile.getText().trim().isEmpty();
    }

    private boolean validInputFile(Path source) {
        String inputExtension;
        try {
            inputExtension = source.getFileName().toString().split("\\.")[1];

            if (!inputExtension.equals("txt")) {
                showErrorAlert(new InputExtensionException());
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            if (e instanceof ArrayIndexOutOfBoundsException) {
                e = new InputMissingExtensionException();
            }
            showErrorAlert(e);
            return false;
        }

    }

    private void formatForText(Map<String, String> map) throws Exception {
        try {
            outputTXT.add("");
            outputTXT.add("Ping statistics for: " + map.get(NetStatsParser.IP_ADDRESS));
            outputTXT.add("Minimum latency: " + map.get(NetStatsParser.MINIMUM_LATENCY));
            outputTXT.add("Maximum latency: " + map.get(NetStatsParser.MAXIMUM_LATENCY));
            outputTXT.add("Average latency: " + map.get(NetStatsParser.AVERAGE_LATENCY));
            outputTXT.add("Packets sent: " + map.get(NetStatsParser.PACKETS_SENT));
            outputTXT.add("Packets received: " + map.get(NetStatsParser.PACKETS_RECEIVED));
            outputTXT.add("Packets lost: " + map.get(NetStatsParser.PACKETS_LOST));
            outputTXT.add("Percentage packets lost: " + map.get(NetStatsParser.PACKETS_LOST_PERCENT));
        } catch (Exception e) {
            if (e instanceof ArrayIndexOutOfBoundsException) {
                e = new InputFormatException();
            }
            throw e;
        }
    }

    private void formatForCSV(Map<String, String> map) throws Exception {
        try {
            outputCSV.add(map.get(NetStatsParser.IP_ADDRESS) + ", "
                    + map.get(NetStatsParser.MINIMUM_LATENCY) + ", "
                    + map.get(NetStatsParser.MAXIMUM_LATENCY) + ", "
                    + map.get(NetStatsParser.AVERAGE_LATENCY) + ", "
                    + map.get(NetStatsParser.PACKETS_SENT) + ", "
                    + map.get(NetStatsParser.PACKETS_RECEIVED) + ", "
                    + map.get(NetStatsParser.PACKETS_LOST) + ", "
                    + map.get(NetStatsParser.PACKETS_LOST_PERCENT)
            );
        } catch (Exception e) {
            if (e instanceof ArrayIndexOutOfBoundsException) {
                e = new InputFormatException();
            }
            throw e;
        }
    }

    private void formatForJSON(Map<String, String> map) throws Exception {
        try {
            PingStats pingStats = new PingStats(
                    map.get(NetStatsParser.IP_ADDRESS),
                    map.get(NetStatsParser.PACKETS_SENT),
                    map.get(NetStatsParser.PACKETS_RECEIVED),
                    map.get(NetStatsParser.PACKETS_LOST),
                    map.get(NetStatsParser.PACKETS_LOST_PERCENT),
                    map.get(NetStatsParser.MINIMUM_LATENCY),
                    map.get(NetStatsParser.MAXIMUM_LATENCY),
                    map.get(NetStatsParser.AVERAGE_LATENCY)
            );
            JSONFormat.add(pingStats);
        } catch (Exception e) {
            if (e instanceof ArrayIndexOutOfBoundsException) {
                e = new InputFormatException();
            }
            throw e;
        }
    }

    private void writeToFile(Path targetFile, List<String> content) {
        try {
            FileInteraction.writeFileByLine(targetFile, content, false, () -> {
                Date completed = Calendar.getInstance().getTime();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                textArea.appendText("Saved to " + targetFile.toAbsolutePath() + " at " + timeFormat.format(completed) + "\n");
            });
        } catch (Exception e) {
            if (e instanceof ArrayIndexOutOfBoundsException) {
                e = new InputMissingExtensionException();
            }
            showErrorAlert(e);
        }
    }

    private void showErrorAlert(Exception e) {
        if (e instanceof NoSuchFileException) {
            e = new FileDoesNotExistException();
        }
        String title;
        if (e instanceof EmptyFieldsException) {
            title = "Empty fields";
        } else if (e instanceof EmptyInputFileException) {
            title = "Empty input file";
        } else if (e instanceof FileDoesNotExistException) {
            title = "File does not exist";
        } else if (e instanceof InputExtensionException) {
            title = "Incompatible input file";
        } else if (e instanceof InputFormatException) {
            title = "Input format error";
        } else if (e instanceof InputMissingExtensionException) {
            title = "File missing extension";
        } else {
            title = "Error";
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
