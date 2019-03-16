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
        Menu.CreateMenu();

    }
}
