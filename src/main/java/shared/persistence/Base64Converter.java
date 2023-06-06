package shared.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages all the conversion between a Base64 string and a file.
 */
public class Base64Converter {

    /**
     * Encode the given file to Base 64.
     *
     * @param file The file to encode.
     * @return A map containing the encoded file content and its extension.
     */
    public static Map<String, String> encode(File file) {
        if (file == null) {
            return null;
        }

        Map<String, String> attributes = new HashMap<>();

        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            Logger.getLogger(Base64Converter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        // File content.
        String fileContent = Base64.getEncoder().encodeToString(fileBytes);
        attributes.put("content", fileContent);

        // File extension.
        String fileName = file.getName();
        int lastPointIndex = fileName.lastIndexOf(".");
        String fileExtension = fileName.substring(lastPointIndex);
        attributes.put("extension", fileExtension);

        return attributes;
    }

    /**
     * Decode the given base64 content and save it on a temporary file with the given extension.
     *
     * @param fileAttributes The base64 file attributes.
     * @return The created temporary file containing the specified content.
     */
    public static File decode(Map<String, String> fileAttributes) {
        String content = fileAttributes.get("content");
        String extension = fileAttributes.get("extension");

        try {
            byte[] contentBytes = Base64.getDecoder().decode(content);
            Path temp = Files.createTempFile(null, extension);
            Files.write(temp, contentBytes);
            File tmpFile = temp.toFile();
            tmpFile.deleteOnExit();
            return tmpFile;
        } catch (IOException ex) {
            Logger.getLogger(Base64Converter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
