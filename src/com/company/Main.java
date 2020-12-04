package com.company;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Main {
    static String filePath = "qwerty.zip";
    static String directionPath = "qwerty";
    static String passwords = "258";

    public static void main(String[] args) {
        final boolean[] isRun = {true};
        SymbolPassword password = new SymbolPassword(() -> isRun[0] = false);
        password.setPosCharPass(new String(SymbolPassword.NUMBER));
        password.setLength(3);
        new File(filePath).delete();
        new File(directionPath).delete();
        createZipArchive(passwords);
        while (isRun[0]) {
            System.out.println(password.toString());
            try {
                readZipArchive(password.toString());
                break;
            } catch (ZipException ignored) {
                System.out.println("error");
            }
            password.next();
        }

    }

    private static void readZipArchive(String password) throws ZipException{
        ZipFile zipFile = new ZipFile(filePath, password.toCharArray());
        zipFile.extractAll(directionPath);
    }

    private static void createZipArchive(String password) {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
        File file = new File("test.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<File> filesToAdd = Collections.singletonList(file);
        ZipFile zipFile = new ZipFile(filePath, password.toCharArray());
        try {
            zipFile.createSplitZipFile(filesToAdd,zipParameters,true, 10485760);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

}
