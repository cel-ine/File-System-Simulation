package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import src.filesystem.FileSystem;
import src.filesystem.ContiguousAllocation;

public class Main extends Application {

// Using JavaFX WebView to show basic html
    
    private FileSystem fileSystem;

    @Override
    public void start(Stage stage) {
        fileSystem = new FileSystem(new ContiguousAllocation(), 50); // Example setup

        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();

        // Load your UI HTML file
        engine.load(getClass().getResource("/ui/index.html").toExternalForm());


        // Connect Java to JavaScript
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("javaApp", this); // Expose this Java class to JS
            }
        });

        stage.setScene(new Scene(webView, 800, 600));
        stage.setTitle("File System Simulation");
        stage.show();
    }

    // 👇 Methods JS can call directly
    public void createFile(String name, int size) {
        fileSystem.createFile(name, size);
        System.out.println("Created file: " + name);
    }

    public void deleteFile(String name) {
        fileSystem.deleteFile(name);
        System.out.println("Deleted file: " + name);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
