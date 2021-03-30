package pl.solix.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import pl.solix.model.ToScreenConnection;

import java.net.Socket;

public class MainViewController {

    private ToScreenConnection toScreenConnection;

    @FXML
    private Button miButton;

    @FXML
    private Button tabletButton;

    @FXML
    private Button computerButton;

    @FXML
    private Button usgButton;

    public void initialize() {
        toScreenConnection = new ToScreenConnection();


    }


    @FXML
    private void changeSourceToMagicInfo(){

        ToScreenConnection.sendUdpCommand(ToScreenConnection.MI);

    }

    @FXML
    private void changeSourceToTablet(){
        ToScreenConnection.sendUdpCommand(ToScreenConnection.HDMI2);
        ToScreenConnection.coundDown();


    }

    @FXML
    private void changeSourcetoComputer(){
        ToScreenConnection.sendUdpCommand(ToScreenConnection.DVI);
        ToScreenConnection.coundDown();
    }

    @FXML
    private void changeSourceToUsg(){
        ToScreenConnection.sendUdpCommand(ToScreenConnection.HDMI1);
        ToScreenConnection.coundDown();
    }







}
