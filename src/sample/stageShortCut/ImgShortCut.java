package sample.stageShortCut;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ImgShortCut {

    private Stage stage = new Stage();
    private BorderPane root = new BorderPane();
    Scene scene = new Scene(root,1280 ,720);

    public void show() {

        FXMLLoader loader;
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/stageShortCut/imgShortCut.fxml"));
        try {
            root.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case C:
                    stage.close();
                    break;
                case ESCAPE:
                    stage.close();
                    break;
            }
        });
    }
}
