package fifi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Displays one chat message together with an avatar image.
 */
public class DialogBox extends HBox {
    private final Label text;
    private final ImageView displayPicture;

    /**
     * Creates a dialog box containing a message and avatar image.
     *
     * @param message text to show in the dialog box
     * @param image avatar image to show beside the message
     */
    private DialogBox(String message, Image image) {
        text = new Label(message);
        displayPicture = new ImageView(image);

        text.setWrapText(true);
        text.setMaxWidth(250.0);
        text.setMinHeight(Region.USE_PREF_SIZE);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        displayPicture.setPreserveRatio(true);
        setSpacing(10.0);
        setAlignment(Pos.TOP_RIGHT);

        getChildren().addAll(text, displayPicture);
    }

    /**
     * Creates a right-aligned user dialog.
     *
     * @param message user's message
     * @param image user's avatar image
     * @return a dialog box for the user's message
     */
    public static DialogBox getUserDialog(String message, Image image) {
        return new DialogBox(message, image);
    }

    /**
     * Creates a left-aligned Fifi dialog.
     *
     * @param message Fifi's message
     * @param image Fifi's avatar image
     * @return a dialog box for Fifi's message
     */
    public static DialogBox getFifiDialog(String message, Image image) {
        DialogBox dialogBox = new DialogBox(message, image);
        dialogBox.flip();
        return dialogBox;
    }

    /**
     * Creates a Fifi dialog with a distinct appearance when the command failed.
     *
     * @param response message and error status supplied by Fifi
     * @param image Fifi's avatar image
     * @return a dialog box styled according to the response status
     */
    public static DialogBox getFifiDialog(ChatResponse response, Image image) {
        DialogBox dialogBox = getFifiDialog(response.getMessage(), image);
        if (response.isError()) {
            Label heading = new Label("Error");
            heading.getStyleClass().add("error-heading");
            dialogBox.text.getStyleClass().add("error-message");
            // The container's width includes its padding, so long messages still fit the chat area.
            dialogBox.text.setMaxWidth(Double.MAX_VALUE);
            dialogBox.getChildren().remove(dialogBox.text);
            VBox errorBox = new VBox(6.0, heading, dialogBox.text);
            errorBox.getStyleClass().add("error-box");
            errorBox.setMinWidth(0.0);
            errorBox.setMaxWidth(250.0);
            dialogBox.getChildren().add(errorBox);
        }
        return dialogBox;
    }

    /**
     * Flips the dialog box so the avatar appears on the left.
     */
    private void flip() {
        setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> nodes = FXCollections.observableArrayList(getChildren());
        FXCollections.reverse(nodes);
        getChildren().setAll(nodes);
    }
}
