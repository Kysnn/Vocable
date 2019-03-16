package MineDict;

import java.io.IOException;

/**
 *
 * @author Sinan
 */
public class Run {

    public static void main(String[] args) throws IOException {
        Gui Menu = new Gui();
        Menu.getFile().OpenFileToLoad();
        Menu.getFile().ReadRecords();
        Menu.getFile().CloseFileToLoad();
//        Menu.getFile().Dict.AddNewVocable("Dog","Takip Etmek","photographers seemed to dog her every step");
        Menu.CreateMenu();

    }
}
