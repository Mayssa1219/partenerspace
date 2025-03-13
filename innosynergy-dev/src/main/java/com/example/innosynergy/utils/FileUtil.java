package com.example.innosynergy.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUtil {
    private static final String UPLOAD_DIR = "src/main/resources/Avatars";

    public static String saveFile(File sourceFile) throws IOException {
        // Ensure the upload directory exists
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate a unique filename
        String fileExtension = sourceFile.getName().substring(sourceFile.getName().lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // Copy the file to the upload directory
        File destinationFile = new File(UPLOAD_DIR, uniqueFileName);
        Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName; // Return the saved file name
    }
}