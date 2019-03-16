package MineDict;

import MineDict.LineController.Vocable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Sinan
 */
public class FileController {

    public static FileReader FileRead;
    public static FileWriter FileWrite;
    public static BufferedReader BufferRead;
    public static BufferedWriter BufferWrite;
    public LineController Dict = new LineController();
    public static ArrayList<String> Eng = new ArrayList<String>();
    public static ArrayList<String> Tr = new ArrayList<String>();
    public static ArrayList<String> Examples = new ArrayList<String>();
    ArrayList<String> Transfer = new ArrayList<String>();

    private ObjectOutputStream OutputoFile;
    private ObjectInputStream InputoFile;
    private Scanner input = new Scanner(System.in);

    public void OpenFileToSave() {
        try {
            OutputoFile = new ObjectOutputStream(new FileOutputStream(
                    "Vocables.ser",
                    false));
        } catch (IOException ioException) {
            System.err.println("Error opening to file.");
        }
    }

    public void AddRecords() {

        for (LineController.Vocable Item : Dict.getVocables()) {
            try {
                OutputoFile.writeObject(Item);

            } catch (IOException ioException) {
                System.err.println("Error writing to file.");

            }

        }

    }

    public void CloseFileToSave() {
        try {
            if (OutputoFile != null) {
                OutputoFile.close();
            }
        } catch (IOException ioException) {
            System.err.println("Error closing to file.");
            System.exit(1);
        }
    }

    public void OpenFileToLoad() {
        try {

            InputoFile = new ObjectInputStream(
                    new FileInputStream("Vocables.ser"));
        } catch (IOException ioException) {
            System.err.println("Error opening file.");
        }
    }

    public void ReadRecords() {
        Vocable Record;

        try {
            while (true) {

                Record = (Vocable) InputoFile.readObject();

                if (Record != null) {
                    Dict.getVocables().add(Record);
                }

            }
        } catch (EOFException endOfFileException) {
            return;
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Unable to create object.");
        } catch (IOException ioException) {
            System.err.println("Error during reading from file.");
        }
    }

    public void CloseFileToLoad() {
        try {
            if (InputoFile != null) {
                InputoFile.close();
            }
        } catch (IOException ioException) {
            System.err.println("Error closing file.");
            System.exit(1);
        }
    }

}
