package shared.presentation.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for downloading files.
 */
public class Downloader {
    /**
     * Download the given delivery note.
     *
     * @param file The delivery note file.
     * @param dest The file to set the downloaded content.
     * @return Whether the delivery note has been downloaded or not.
     */
    public static boolean downloadDeliveryNote(File file, File dest) {
        if (file == null) {
            return false;
        }

        try {
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
