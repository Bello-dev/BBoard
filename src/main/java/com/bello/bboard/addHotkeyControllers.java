package com.bello.bboard;

import com.bello.bboard.Utils.GlobalControls;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class addHotkeyControllers {

    private List<String> wl = Arrays.asList(".mp3", ".wav");

    @FXML
    public Label filePath;

    @FXML
    public TextField hotkeyField;

    @FXML
    public Label MSG;

    @FXML
    public void clickSaveButton() throws IOException {

        if (hotkeyField.getText().isEmpty()) {
            info("PLEASE SET A HOTKEY");
            return;
        }
        if (filePath.getText().isEmpty()) {
            info("PLEASE SET A FILE");
            return;
        }

        if (!wl.contains(filePath.getText().substring(filePath.getText().length() -4))) {
            info("PLEASE USE A VALID SOUND FILE: mp3, wav");
            return;
        }

        Controller controllers = GlobalControls.getControllers();
        int selectedIndex = controllers.hotkeyContainer.getSelectionModel().getSelectedIndex();
        String newHotkey = hotkeyField.getText() + ";" + filePath.getText();
        List<String> hotkeys = Bboard.config.getConfig().getHotkeys();
        if (selectedIndex != -1) {
            hotkeys.set(selectedIndex, newHotkey);
            Bboard.updateList();
            Bboard.config.saveConfig();
            info("EDIT SAVED!");
            return;
        }
        Bboard.config.getConfig().addHotkey(newHotkey);
        Bboard.updateList();
        Bboard.config.saveConfig();
        info("SAVED!");
    }


    public void info(String msg) {
        MSG.setText(msg);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), ev -> {
            MSG.setText("");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
