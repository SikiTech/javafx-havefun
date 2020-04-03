package sample.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HomeController {

    @FXML
    private AnchorPane body;

    @FXML
    private Label title;

    @FXML
    private FontAwesomeIconView titleIcon;

    @FXML
    private AnchorPane content;

    @FXML
    private JFXHamburger hamburger;

    private HamburgerBackArrowBasicTransition ht;

    private JFXDrawersStack drawersStack;

    private JFXDrawer leftDrawer;

    @FXML
    public void initialize() {
        drawersStack = new JFXDrawersStack();
//        drawersStack.setLayoutX(100); drawer的起始位置定点，默认0
        body.getChildren().add(drawersStack);

        leftDrawer = new JFXDrawer();
        VBox vBox = null;
        try {
            vBox = FXMLLoader.load(getClass().getResource("/fxml/drawer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Node node: vBox.getChildren()){
            if (node.getAccessibleText() != null){
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    refreshPage(node.getAccessibleText());
                    toggerDrawer();
                });
            }
        }
        leftDrawer.setSidePane(vBox);
//        leftDrawer.setDirection(JFXDrawer.DrawerDirection.LEFT); 默认LEFT
        leftDrawer.setDefaultDrawerSize(160);
        leftDrawer.setResizeContent(false);
        leftDrawer.setOverLayVisible(false);
        leftDrawer.setResizableOnDrag(true);

        //hamburger点击动态切换
        ht = new HamburgerBackArrowBasicTransition(hamburger);
        ht.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            toggerDrawer();
        });

        //抽屉打开状态下，点击content抽屉以关闭
        content.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (ht.getRate()!= -1){
                toggerDrawer();
            }
        });

        refreshPage("Happy");
    }

    private void refreshPage(String page){
        try {
            content.getChildren().clear();
            AnchorPane contentPage = FXMLLoader.load(getClass().getResource("/fxml/" + page + ".fxml"));
            content.getChildren().add(contentPage);

            switch (page){
                case "Happy" : {
                    title.setText("Happy");
                    titleIcon.setGlyphName("CAMERA");
                    return;
                }
                case "Balance": {
                    title.setText("Balance");
                    titleIcon.setGlyphName("BALANCE_SCALE");
                    return;
                }
                case "Health" : {
                    title.setText("Health");
                    titleIcon.setGlyphName("MEDKIT");
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toggerDrawer() {
        ht.setRate(ht.getRate() * -1);
        ht.play();
        drawersStack.toggle(leftDrawer);
    }

}

