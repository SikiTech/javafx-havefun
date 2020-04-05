package sample.util;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * @author ZhouRuikang
 * @email zhouruikang015@163.com
 * @github https://github.com/ZhouRuikang015
 * @since 2020-04-05 1:10 下午
 */
public class FileChooserUtil {
    public static File chooseOpenFile(FileChooser.ExtensionFilter... extensionFilter) {
        File file = null;

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            if (extensionFilter != null) {
                fileChooser.getExtensionFilters().addAll(extensionFilter);
            }

            file = fileChooser.showOpenDialog(new Stage());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return file;
    }



    public static File chooseSaveFile(String defaultFileName, FileChooser.ExtensionFilter... extensionFilter) {
        File file = null;

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            if (defaultFileName != null) {
                fileChooser.setInitialFileName(defaultFileName);
            }

            if (extensionFilter != null) {
                fileChooser.getExtensionFilters().addAll(extensionFilter);
            }

            file = fileChooser.showSaveDialog(new Stage());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return file;
    }
}
