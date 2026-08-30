package fifi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests the chatbot's public run method with controlled input and output.
 */
public class FifiTest {
    private final PrintStream originalOut = System.out;

    @TempDir
    private Path temporaryDirectory;

    @AfterEach
    public void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    public void run_byeCommand_welcomeAndExitMessagesPrinted() {
        System.setIn(new ByteArrayInputStream("bye\n".getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
        Fifi fifi = new Fifi(temporaryDirectory.resolve("duke.txt").toString());

        fifi.run();

        String printedText = output.toString(StandardCharsets.UTF_8);
        assertTrue(printedText.contains("Hello! My name is Fifi ^^"));
        assertTrue(printedText.contains("BaiBai! Hope to see you soon ^^"));
    }
}
