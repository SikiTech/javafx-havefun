package sample.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import sample.entity.DoubleAttribute;
import sample.util.FileChooserUtil;


import java.io.File;
import java.util.List;

public class ExcelController {

    @FXML
    private JFXButton importButton;

    @FXML
    private JFXButton exportButton;

    @FXML
    private JFXTreeTableView<DoubleAttribute> inTable;

    @FXML
    private JFXTreeTableView<DoubleAttribute> outTable;

    @FXML
    private JFXTreeTableView<DoubleAttribute> smTable;

    @FXML
    private JFXTextField inRowsCountText;

    @FXML
    private JFXTextField outRowsCountText;

    @FXML
    private JFXTextField smRowsCountText;

    @FXML
    private JFXButton calButton;

    @FXML
    private JFXButton areaRValue;

    private double inSum;

    private double outSum;

    private double smSum;

    private int inRowsCount;

    private int outRowsCount;

    private int smRowsCount;

    @FXML
    public void initialize() {
        JFXTreeTableColumn<DoubleAttribute, String> qi = new JFXTreeTableColumn<>("Qi(L/s)");
        qi.setPrefWidth(83);
        qi.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DoubleAttribute, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<DoubleAttribute, String> param) {
                return param.getValue().getValue().firstProperty();
            }
        });

        JFXTreeTableColumn<DoubleAttribute, String> ci = new JFXTreeTableColumn<>("Ci(mg/L)");
        ci.setPrefWidth(83);
        ci.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DoubleAttribute, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<DoubleAttribute, String> param) {
                return param.getValue().getValue().secondProperty();
            }
        });

        inTable.getColumns().setAll(qi, ci);


        JFXTreeTableColumn<DoubleAttribute, String> qj = new JFXTreeTableColumn<>("Qj(L/s)");
        qj.setPrefWidth(83);
        qj.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DoubleAttribute, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<DoubleAttribute, String> param) {
                return param.getValue().getValue().firstProperty();
            }
        });

        JFXTreeTableColumn<DoubleAttribute, String> cj = new JFXTreeTableColumn<>("Cj(mg/L)");
        cj.setPrefWidth(83);
        cj.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DoubleAttribute, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<DoubleAttribute, String> param) {
                return param.getValue().getValue().secondProperty();
            }
        });

        outTable.getColumns().setAll(qj, cj);

        JFXTreeTableColumn<DoubleAttribute, String> mk = new JFXTreeTableColumn<>("Mk(mg/m2·s)");
        mk.setPrefWidth(93);
        mk.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DoubleAttribute, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<DoubleAttribute, String> param) {
                return param.getValue().getValue().firstProperty();
            }
        });

        JFXTreeTableColumn<DoubleAttribute, String> Sk = new JFXTreeTableColumn<>("Sk(m2)");
        Sk.setPrefWidth(73);
        Sk.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DoubleAttribute, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<DoubleAttribute, String> param) {
                return param.getValue().getValue().secondProperty();
            }
        });

        smTable.getColumns().setAll(mk, Sk);
    }

    @FXML
    void importAreaResourceExcel(ActionEvent event) {
        File file = FileChooserUtil.chooseOpenFile(
                new FileChooser.ExtensionFilter("All Excel", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx"));
        if (file == null) return;

        ExcelReader reader = ExcelUtil.getReader(file);
        List<List<Object>> readAll = reader.read();

        inSum = outSum = smSum = 0;
        inRowsCount = outRowsCount = smRowsCount = 0;

        ObservableList<DoubleAttribute> inCols = FXCollections.observableArrayList();
        ObservableList<DoubleAttribute> outCols = FXCollections.observableArrayList();
        ObservableList<DoubleAttribute> smCols = FXCollections.observableArrayList();
        for (int i = 2; i < readAll.size() ; i++) {
            List<Object> readLine = readAll.get(i);
            if (readLine.size() > 1 && readLine.get(0) != null && readLine.get(1) != null){
                String a = String.valueOf(readLine.get(0));
                String b = String.valueOf(readLine.get(1));
                inCols.add(new DoubleAttribute(a,b));
                inSum += Double.valueOf(a) * Double.valueOf(b);
                inRowsCount++;
            }
            if (readLine.size() > 3 && readLine.get(2) != null && readLine.get(3) != null){
                String a = String.valueOf(readLine.get(2));
                String b = String.valueOf(readLine.get(3));
                outCols.add(new DoubleAttribute(a,b));
                outSum += Double.valueOf(a) * Double.valueOf(b);
                outRowsCount++;
            }
            if (readLine.size() > 5 && readLine.get(4) != null && readLine.get(5) != null){
                String a = String.valueOf(readLine.get(4));
                String b = String.valueOf(readLine.get(5));
                smCols.add(new DoubleAttribute(a,b));
                smSum += Double.valueOf(a) * Double.valueOf(b);
                smRowsCount++;
            }

        }
        final TreeItem<DoubleAttribute> rootInCols = new RecursiveTreeItem<DoubleAttribute>(inCols, RecursiveTreeObject::getChildren);
        inTable.setRoot(rootInCols);
        inTable.setShowRoot(false);

        final TreeItem<DoubleAttribute> rootOutCols = new RecursiveTreeItem<DoubleAttribute>(outCols, RecursiveTreeObject::getChildren);
        outTable.setRoot(rootOutCols);
        outTable.setShowRoot(false);

        final TreeItem<DoubleAttribute> rootSmCols = new RecursiveTreeItem<DoubleAttribute>(smCols, RecursiveTreeObject::getChildren);
        smTable.setRoot(rootSmCols);
        smTable.setShowRoot(false);

        inRowsCountText.setText("共" + inRowsCount + "行");
        inRowsCountText.setVisible(true);
        outRowsCountText.setText("共" + outRowsCount + "行");
        outRowsCountText.setVisible(true);
        smRowsCountText.setText("共" + smRowsCount + "行");
        smRowsCountText.setVisible(true);
    }

    @FXML
    void calAreaSourceRate(ActionEvent event) {
        double res = (1 - outSum/(inSum + smSum))*100;
        areaRValue.setText(String.format("%.2f", res));
    }

    @FXML
    void exportAreaResourceTemplate(ActionEvent event) {
        String fileName = "AreaResourceTemplate.xlsx";
        File file = FileChooserUtil.chooseSaveFile(fileName,
                new FileChooser.ExtensionFilter("All Excel", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx"));
        if (file != null) {
            ExcelWriter writer = ExcelUtil.getWriter(file.getPath());

            List<String> row1 = CollUtil.newArrayList("入流流量Qi (L/s)", "入流污染物浓度Ci (mg/L)", "出流流量Qj (L/s)", "出流污染物浓度Cj (mg/L)", "垫面系数Mk (mg/m2 s)", "垫面面积Sk (m2)");
            List<String> row2 = CollUtil.newArrayList("22", "20", "6", "5", "6", "5");
            List<String> row3 = CollUtil.newArrayList("7", "8", "6.5", "8", "16", "5");
            List<String> row4 = CollUtil.newArrayList("22", "20", "4", "11", "7", "6");
            List<String> row5 = CollUtil.newArrayList("5", "8");
            List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

            writer.merge(5, "删除下方数据行(第三行开始)重新填数据即可");
            writer.setColumnWidth(0,25);
            writer.setColumnWidth(1,25);
            writer.setColumnWidth(2,25);
            writer.setColumnWidth(3,25);
            writer.setColumnWidth(4,25);
            writer.setColumnWidth(5,25);
            writer.write(rows, true);
            writer.close();
        }
    }

}

