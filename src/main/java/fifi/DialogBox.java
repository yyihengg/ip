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
     * Flips the dialog box so the avatar appears on the left.
     */
    private void flip() {
        setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> nodes = FXCollections.observableArrayList(getChildren());
        FXCollections.reverse(nodes);
        getChildren().setAll(nodes);
    }
}
