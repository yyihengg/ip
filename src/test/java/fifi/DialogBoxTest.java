package fifi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Tests error presentation and command recovery on the JavaFX application thread.
 */
public class DialogBoxTest {
    @TempDir
    private Path temporaryDirectory;

    /**
     * Starts JavaFX when the environment provides a graphical display.
     */
    @BeforeAll
    public static void startJavaFx() throws Exception {
        boolean isLinux = System.getProperty("os.name").startsWith("Linux");
        assumeTrue(!isLinux || System.getenv("DISPLAY") != null,
                "JavaFX presentation tests require a graphical display.");
        CountDownLatch started = new CountDownLatch(1);
        Platform.startup(() -> {
            Platform.setImplicitExit(false);
            started.countDown();
        });
        assertTrue(started.await(10, TimeUnit.SECONDS), "JavaFX did not start.");
    }

    @Test
    public void getFifiDialog_errorThenSuccess_onlyErrorHasHighlight() throws Exception {
        runOnJavaFxThread(() -> {
            Fifi fifi = new Fifi(temporaryDirectory.resolve("duke.txt").toString());
            WritableImage avatar = new WritableImage(1, 1);
            DialogBox error = DialogBox.getFifiDialog(fifi.getChatResponse("blah"), avatar);
            DialogBox success = DialogBox.getFifiDialog(fifi.getChatResponse("todo Oops! read book"), avatar);
            VBox root = layOutDialogs(error, success);

            assertEquals(Pos.TOP_LEFT, error.getAlignment());
            Label heading = (Label) error.lookup(".error-heading");
            assertNotNull(heading);
            assertEquals("Error", heading.getText());
            Label message = (Label) error.lookup(".error-message");
            assertTrue(message.getText().contains("Valid commands include"));
            assertTrue(message.isWrapText());

            VBox errorBox = (VBox) error.lookup(".error-box");
            assertNotNull(errorBox.getBorder());
            assertEquals(Color.web("#fff1f0"), errorBox.getBackground().getFills().getFirst().getFill());
            assertEquals(Color.web("#8a1c13"), message.getTextFill());
            assertTrue(heading.getFont().getStyle().contains("Bold"));
            assertTrue(errorBox.getWidth() <= 250.0);
            assertTrue(message.getWidth() < errorBox.getWidth());
            assertTrue(message.getLayoutY() + message.getHeight() <= errorBox.getHeight());

            assertNull(success.lookup(".error-heading"));
            assertNull(success.lookup(".error-box"));
            assertTrue(((Label) success.lookup(".label")).getText().contains("Oops! read book"));
            assertEquals(1, root.lookupAll(".error-box").size());
        });
    }

    @Test
    public void getFifiDialog_validThenInvalidDate_messageAndErrorStatusMatch() throws Exception {
        runOnJavaFxThread(() -> {
            Fifi fifi = new Fifi(temporaryDirectory.resolve("duke.txt").toString());
            WritableImage avatar = new WritableImage(1, 1);
            DialogBox success = DialogBox.getFifiDialog(fifi.getChatResponse("list"), avatar);
            DialogBox error = DialogBox.getFifiDialog(fifi.getChatResponse("show 2025-02-30"), avatar);
            layOutDialogs(success, error);

            assertNull(success.lookup(".error-box"));
            Label message = (Label) error.lookup(".error-message");
            assertEquals("Oops! Please use yyyy-MM-dd for dates.",
                    message.getText());
        });
    }

    @Test
    public void getUserDialog_errorWords_userMessageRemainsNormalAndRightAligned() throws Exception {
        runOnJavaFxThread(() -> {
            DialogBox user = DialogBox.getUserDialog("Error: Oops!", new WritableImage(1, 1));
            layOutDialogs(user);

            assertEquals(Pos.TOP_RIGHT, user.getAlignment());
            assertEquals("Error: Oops!", ((Label) user.lookup(".label")).getText());
            assertFalse(user.getChildren().isEmpty());
            assertNull(user.lookup(".error-box"));
        });
    }

    private static VBox layOutDialogs(DialogBox... dialogs) {
        VBox root = new VBox(dialogs);
        Scene scene = new Scene(root, 370.0, 600.0);
        scene.getStylesheets().add(Main.class.getResource("/styles/dialog.css").toExternalForm());
        root.applyCss();
        root.layout();
        return root;
    }

    private static void runOnJavaFxThread(Runnable assertions) throws Exception {
        FutureTask<Void> task = new FutureTask<>(assertions, null);
        Platform.runLater(task);
        task.get(10, TimeUnit.SECONDS);
    }
}
