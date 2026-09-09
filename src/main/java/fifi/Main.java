package fifi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Shows the JavaFX interface for the Fifi chatbot.
 */
public class Main extends Application {
    private static final String SAVE_FILE_PATH = "data/duke.txt";
    private static final int AVATAR_SIZE = 100;

    private final Image userImage = createAvatarImage(0xFFB7E4C7);
    private final Image fifiImage = createAvatarImage(0xFFFFD6A5);
    private final Fifi fifi = new Fifi(SAVE_FILE_PATH);

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;

    /**
     * Sets up and displays the primary JavaFX stage.
     *
     * @param stage main window provided by JavaFX
     */
    @Override
    public void start(Stage stage) {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);
        Scene scene = new Scene(mainLayout);

        stage.setTitle("Fifi");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);
        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        sendButton.setOnMouseClicked(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());
        dialogContainer.heightProperty().addListener((observable, oldValue, newValue) -> scrollPane.setVvalue(1.0));
        dialogContainer.getChildren().add(DialogBox.getFifiDialog("Hello! My name is Fifi ^^\nHow may I help?",
                fifiImage));

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Shows the user's input and Fifi's response in the dialog container.
     */
    private void handleUserInput() {
        String userText = userInput.getText();
        if (userText.isBlank()) {
            return;
        }

        String fifiText = fifi.getResponse(userText);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getFifiDialog(fifiText, fifiImage));
        userInput.clear();

        if (fifi.isExit()) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }

    /**
     * Creates a simple square avatar image for a dialog box.
     *
     * @param color the ARGB color to fill the image with
     * @return the generated avatar image
     */
    private static Image createAvatarImage(int color) {
        WritableImage image = new WritableImage(AVATAR_SIZE, AVATAR_SIZE);
        for (int x = 0; x < AVATAR_SIZE; x++) {
            for (int y = 0; y < AVATAR_SIZE; y++) {
                image.getPixelWriter().setArgb(x, y, color);
            }
        }
        return image;
    }
}
