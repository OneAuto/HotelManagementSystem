package mvcpro.controlled;

import javafx.application.Platform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mvcpro.model.dao.*;
import mvcpro.model.entity.*;
import mvcpro.model.utils.Uitls;
import mvcpro.view.AlertDefined;
import mvcpro.view.FileChooserDefined;
import mvcpro.view.UiInfoRoom;
import mvcpro.view.server.*;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UiMainController implements Initializable {

    private UserDao userDao;
    private ClientDao clientDao;
    private InfoRoomDao infoRoomDao;
    private BookRoomDao bookRoomDao;
    private StandardRoomDao standardRoomDao;
    private User user;
    private final String verifyUser="用户";

    @FXML
    private TabPane tabPane_master;

    @FXML
    private Tab tab_4;

    @FXML
    private Tab tab_2;
    @FXML
    private Tab tab_3;

    @FXML
    private Menu menu_system;

    private Stage mainStage;

    @FXML
    private ImageView imageView_one;

    @FXML
    private ImageView imageView_two;

    @FXML
    private ImageView iv_picture_user;

    @FXML
    private Button add;

    @FXML
    private Button update;

    @FXML
    private Button delete;

    @FXML
    private Button mainMinimize;

    @FXML
    private Button browse;

    @FXML
    private Button mainExit;

    @FXML
    private Button btn_add_info;

    @FXML
    private Button btn_delete_info;

    @FXML
    private Button btn_delete_standard;

    @FXML
    private Pane pane_info;

    @FXML
    private TextField txf_search_user;

    @FXML
    private TableView<UserData> mTableUser;

    @FXML
    private TableView<ClientData> mTableClient;

    @FXML
    private TableView<BookRoomData> mTableBookRoom;

    @FXML
    private TableView<InfoRoomData> mTableInfoRoom;

    @FXML
    private TableView<StandardRoomData> mTableStandardRoom;

    //用户表
    @FXML
    private TableColumn<UserData, String> tableColumnPassword_user;

    @FXML
    private TableColumn<UserData, String> tableColumnType_user;

    @FXML
    private TableColumn<UserData, String> tableColumnId_user;

    @FXML
    private TableColumn<UserData, Integer> tableColumnUUID_user;

    @FXML
    private TableColumn<UserData, String> tableColumnQs_three_user;

    @FXML
    private TableColumn<UserData, String> tableColumnQs_one_user;

    @FXML
    private TableColumn<UserData, String> tableColumnQs_two_user;

    @FXML
    private TableColumn<UserData, String> tableColumnPicture_user;


    @FXML
    private TableColumn<ClientData, String> tableColumnNative_client;

    @FXML
    private TableColumn<ClientData, String> tableColumnIdNumber_client;

    @FXML
    private TableColumn<ClientData, String> tableColumnName_client;

    @FXML
    private TableColumn<ClientData, String> tableColumnSex_client;

    @FXML
    private TableColumn<ClientData, String> tableColumnCard_client;

    @FXML
    private TableColumn<ClientData, String> tableColumnPhone_client;

    //订房信息列
    @FXML
    private TableColumn<BookRoomData, Integer> tableColumnId_number_booking;

    @FXML
    private TableColumn<BookRoomData, String> tableColumnType_booking;

    @FXML
    private TableColumn<BookRoomData, Integer> tableColumnPrice_booking;

    @FXML
    private TableColumn<BookRoomData, String> tableColumnPeople_id_booking;

    @FXML
    private TableColumn<BookRoomData, String> tableColumnPeople_name_booking;
    @FXML
    private TableColumn<BookRoomData, String> tableColumnIn_date_booking;

    @FXML
    private TableColumn<BookRoomData, String> tableColumnDiscount_booking;

    @FXML
    private TableColumn<BookRoomData, String> tableColumnOut_date_booking;

    @FXML
    private TableColumn<BookRoomData, Integer> tableColumnAmout_booking;

    @FXML
    private TableColumn<BookRoomData, String> tableColumnRemark_booking;

    //房间信息列
    @FXML
    private TableColumn<InfoRoomData,Integer> tableColumnIdNumber_Info;

    @FXML
    private TableColumn<InfoRoomData,ComboBox> tableColumnType_Info;

    @FXML
    private TableColumn<InfoRoomData, Integer> tableColumnArea_Info;

    @FXML
    private TableColumn<InfoRoomData, Integer> tableColumnMax_bed_Info;

    @FXML
    private TableColumn<InfoRoomData, Integer> tableColumnMax_people_Info;

    @FXML
    private TableColumn<InfoRoomData, String> tableColumnAir_conditioning_Info;
    @FXML
    private TableColumn<InfoRoomData, String> tableColumnTv_Info;

    @FXML
    private TableColumn<InfoRoomData, String> tableColumnRest_Info;

    @FXML
    private TableColumn<InfoRoomData, String> tableColumnPhone_Info;

    @FXML
    private TableColumn<InfoRoomData, String> tableColumnPs_Info;


    //房间标准列
    @FXML
    private TableColumn<StandardRoomData, Integer> tableColumnIdNumber_standard;

    @FXML
    private TableColumn<StandardRoomData, String> tableColumnType_standard;

    @FXML
    private TableColumn<StandardRoomData, String> tableColumnFloor_standard;

    @FXML
    private TableColumn<StandardRoomData, Integer> tableColumnPrice_standard;

    @FXML
    private TableColumn<StandardRoomData, String> tableColumnRemark_standard;


    //
    // 用户表数据列表，此列表绑定这控件
    //
    private final ObservableList<UserData> userData_list = FXCollections.observableArrayList();

    //客户数据列表
    private final ObservableList<ClientData> clientData_list = FXCollections.observableArrayList();

    //订房数据列表
    private final ObservableList<BookRoomData> bookRoomData_list = FXCollections.observableArrayList();

    //房间信息列表
    private final ObservableList<InfoRoomData> infoRoomData_list = FXCollections.observableArrayList();

    //房间标准信息
    private final ObservableList<StandardRoomData> standardRoomData_list = FXCollections.observableArrayList();


    /*
    标准房字段
     */

    @FXML
    private  TextField txf_id_number_standard;
    @FXML
    private  ComboBox<String> cbx_type_standard;
    @FXML
    private  ComboBox<String> cbx_floor_standard;
    @FXML
    private TextField txf_price_standard;
    @FXML
    private TextArea txa_remark_standard;
    @FXML
    private TextField txf_search_standard;


    /*
    房间信息字段
     */

    @FXML
    private  TextField txf_search_info;

    @FXML
    void CheckAdd(ActionEvent event) throws Exception {
        System.out.println("add");
    }

    @FXML
    void CheckUadate(ActionEvent event) {
        System.out.println("update!");
    }

    @FXML
    void CheckDelete(ActionEvent event) {
        System.out.println("delete!");
    }

    @FXML
    void CheckBrower(ActionEvent event) {
        System.out.println("brower!");
    }

    @FXML
    void CheckMainExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void CheckMainMinimize(ActionEvent event) {
        mainStage.setIconified(true);
    }

    public void setMainStage(Stage mainStage,User user) {
        this.user=user;
        this.mainStage = mainStage;
        if (user.getUserType().equals(verifyUser)){
            tabPane_master.getTabs().remove(tab_4);
            tabPane_master.getTabs().remove(tab_2);
            tabPane_master.getTabs().remove(tab_3);
            btn_add_info.setVisible(false);
            btn_delete_info.setVisible(false);
            btn_delete_standard.setVisible(false);
            pane_info.setVisible(false);
            menu_system.setVisible(false);
        }
    }

    @FXML
    void systemClose(ActionEvent event) {
        Platform.exit();
    }

    private void initTableColumnEvent() {
        //用户列表添加列双击事件
        tableColumnId_user.setCellFactory(TextFieldTableCell.<UserData>forTableColumn());
        tableColumnId_user.setOnEditCommit(
                (TableColumn.CellEditEvent<UserData, String> event) -> {
                    try {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setId(event.getNewValue());
                        userDao.update(event.getTableView().getItems().get(event.getTablePosition().getRow()).userToEntity());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

        tableColumnPassword_user.setCellFactory(TextFieldTableCell.<UserData>forTableColumn());
        tableColumnPassword_user.setOnEditCommit((TableColumn.CellEditEvent<UserData, String> event) -> {
            try {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
                userDao.update(event.getTableView().getItems().get(event.getTablePosition().getRow()).userToEntity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tableColumnType_user.setCellFactory(TextFieldTableCell.<UserData>forTableColumn());
        tableColumnType_user.setOnEditCommit((TableColumn.CellEditEvent<UserData, String> event) -> {
            try {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setUserType(event.getNewValue());
                userDao.update(event.getTableView().getItems().get(event.getTablePosition().getRow()).userToEntity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void initTableEvent(){
        mTableUser.getSelectionModel().selectedItemProperty().addListener(// 选中某一行
                new ChangeListener<UserData>() {
                    @Override
                    public void changed(ObservableValue<? extends UserData> observable, UserData oldValue, UserData newValue) {
                        if (newValue.getPicture() == null)
                            iv_picture_user.setImage(new Image("/png/timg.jpeg"));
                        iv_picture_user.setImage(new Image(newValue.getPicture()));
                    }
                });

        mTableStandardRoom.getSelectionModel().selectedItemProperty().addListener(// 选中某一行
                new ChangeListener<StandardRoomData>() {
                    @Override
                    public void changed(ObservableValue<? extends StandardRoomData> observable, StandardRoomData oldValue, StandardRoomData newValue) {
                        StandardRoom standardRoom= newValue.standardRoomToEntity();
                        txa_remark_standard.setText(standardRoom.getRoom_remark());
                        txf_id_number_standard.setText(standardRoom.getRoom_id_number().toString());
                        txf_price_standard.setText(standardRoom.getRoom_price().toString());
                        cbx_type_standard.setValue(standardRoom.getRoom_type());
                        cbx_floor_standard.setValue(standardRoom.getRoom_floor());
                    }
                });
    }

    private void initDataDao() {
        bookRoomDao = new BookRoomDao();
        clientDao = new ClientDao();
        infoRoomDao = new InfoRoomDao();
        standardRoomDao = new StandardRoomDao();
        userDao = new UserDao();
    }

    private void initProprety() {

        Rectangle clip_one = new Rectangle(imageView_one.getImage().getWidth(), imageView_one.getImage().getHeight());
        clip_one.setArcWidth(15);
        clip_one.setArcHeight(15);
        imageView_one.setClip(clip_one);

        Rectangle clip_two = new Rectangle(361, 30);
        clip_two.setArcWidth(15);
        clip_two.setArcHeight(15);
        imageView_two.setClip(clip_two);


        update.setFont(new Font("宋体", 13));
        delete.setFont(new Font("宋体", 13));
        add.setFont(new Font("宋体", 13));
        browse.setFont(new Font("宋体", 13));
        mainExit.setFont(new Font("宋体", 13));
        mainMinimize.setFont(new Font("宋体", 13));
        iv_picture_user.setImage(new Image("/png/timg.jpeg"));
        cbx_type_standard.getItems().setAll("单人间","标准间","豪华间/高级间","商务间","双套间","组合套间","复式套间");
        cbx_floor_standard.getItems().setAll("一楼","二楼","一楼","二楼","一楼","二楼");
        txf_search_standard.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")){
                try {
                    standardRoomData_list.removeAll(standardRoomData_list);
                    for (StandardRoom standardRoom:standardRoomDao.list())
                        standardRoomData_list.add(new StandardRoomData(standardRoom));
                    ac_refresh_standard(new ActionEvent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        txf_search_info.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                try {
                    infoRoomData_list.removeAll(infoRoomData_list);
                    for (InfoRoom infoRoom : infoRoomDao.list())
                        infoRoomData_list.add(new InfoRoomData(infoRoom));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        txf_search_user.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")){
                try {
                    userData_list.removeAll(userData_list);
                    for (User user : userDao.list())
                        userData_list.add(new UserData(user));
                    ac_refresh_user(new ActionEvent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void initUserTable() throws Exception {

        //字段名
        tableColumnId_user.setCellValueFactory(new PropertyValueFactory<UserData, String>("id"));
        tableColumnPassword_user.setCellValueFactory(new PropertyValueFactory<UserData, String>("password"));
        tableColumnType_user.setCellValueFactory(new PropertyValueFactory<UserData, String>("userType"));
        tableColumnUUID_user.setCellValueFactory(new PropertyValueFactory<UserData, Integer>("UUID"));
        tableColumnQs_one_user.setCellValueFactory(new PropertyValueFactory<UserData, String>("question_one"));
        tableColumnQs_two_user.setCellValueFactory(new PropertyValueFactory<UserData, String>("question_two"));
        tableColumnQs_three_user.setCellValueFactory(new PropertyValueFactory<UserData, String>("question_three"));
        tableColumnPicture_user.setCellValueFactory(new PropertyValueFactory<UserData, String>("picture"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("用户信息列表初始化线程已启动...");
                try {
                    for (User user : userDao.list())
                        userData_list.add(new UserData(user));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mTableUser.setItems(userData_list);
            }
        }).start();
    }

    private void initClientTable() throws Exception {


        tableColumnName_client.setCellValueFactory(new PropertyValueFactory<ClientData, String>("client_name"));
        tableColumnSex_client.setCellValueFactory(new PropertyValueFactory<ClientData, String>("client_sex"));
        tableColumnCard_client.setCellValueFactory(new PropertyValueFactory<ClientData, String>("client_id_card"));
        tableColumnPhone_client.setCellValueFactory(new PropertyValueFactory<ClientData, String>("client_phone"));
        tableColumnNative_client.setCellValueFactory(new PropertyValueFactory<ClientData, String>("client_native"));
        tableColumnIdNumber_client.setCellValueFactory(new PropertyValueFactory<ClientData, String>("client_id_number"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("客户信息列表初始化线程已启动...");
                try {
                    for (Client client : clientDao.list())
                        clientData_list.add(new ClientData(client));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mTableClient.setItems(clientData_list);
            }
        }).start();

    }

    private void initBookRoomTable() throws Exception {
        tableColumnRemark_booking.setCellValueFactory(new PropertyValueFactory<BookRoomData, String>("room_remark"));
        tableColumnAmout_booking.setCellValueFactory(new PropertyValueFactory<BookRoomData, Integer>("room_amout"));
        tableColumnPeople_name_booking.setCellValueFactory(new PropertyValueFactory<BookRoomData, String>("room_peple_name"));
        tableColumnDiscount_booking.setCellValueFactory(new PropertyValueFactory<BookRoomData, String>("room_discount"));
        tableColumnId_number_booking.setCellValueFactory(new PropertyValueFactory<BookRoomData, Integer>("room_id_number"));
        tableColumnIn_date_booking.setCellValueFactory(new PropertyValueFactory<BookRoomData, String>("room_in_date"));
        tableColumnOut_date_booking.setCellValueFactory(new PropertyValueFactory<BookRoomData, String>("room_out_date"));
        tableColumnPeople_id_booking.setCellValueFactory(new PropertyValueFactory<BookRoomData, String>("room_peple_id"));
        tableColumnType_booking.setCellValueFactory(new PropertyValueFactory<BookRoomData, String>("room_type"));
        tableColumnPrice_booking.setCellValueFactory(new PropertyValueFactory<BookRoomData, Integer>("room_price"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("房间预定信息列表初始化线程已启动...");
                try {
                    for (BookRoom bookRoom : bookRoomDao.list())
                        bookRoomData_list.add(new BookRoomData(bookRoom));
                    mTableBookRoom.setItems(bookRoomData_list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void initInfoRoomTable() throws Exception {
        tableColumnMax_bed_Info.setCellValueFactory(new PropertyValueFactory<InfoRoomData, Integer>("max_bed"));
        tableColumnAir_conditioning_Info.setCellValueFactory(new PropertyValueFactory<InfoRoomData, String>("air_conditioning"));
        tableColumnPhone_Info.setCellValueFactory(new PropertyValueFactory<InfoRoomData, String>("iphone"));
        tableColumnType_Info.setCellValueFactory(new PropertyValueFactory<InfoRoomData, ComboBox>("type"));
        tableColumnArea_Info.setCellValueFactory(new PropertyValueFactory<InfoRoomData, Integer>("area"));
        tableColumnPs_Info.setCellValueFactory(new PropertyValueFactory<InfoRoomData, String>("ps"));
        tableColumnTv_Info.setCellValueFactory(new PropertyValueFactory<InfoRoomData, String>("tv"));
        tableColumnMax_people_Info.setCellValueFactory(new PropertyValueFactory<InfoRoomData, Integer>("max_people"));
        tableColumnRest_Info.setCellValueFactory(new PropertyValueFactory<InfoRoomData, String>("rest"));
        tableColumnIdNumber_Info.setCellValueFactory(new PropertyValueFactory<InfoRoomData,Integer>("id_number"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("房间信息列表初始化线程已启动...");
                try {
                    for (InfoRoom infoRoom : infoRoomDao.list())
                        infoRoomData_list.add(new InfoRoomData(infoRoom));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mTableInfoRoom.setItems(infoRoomData_list);
            }
        }).start();

    }

    private void initStandardRoom() throws Exception {
        tableColumnIdNumber_standard.setCellValueFactory(new PropertyValueFactory<StandardRoomData, Integer>("room_id_number"));
        tableColumnFloor_standard.setCellValueFactory(new PropertyValueFactory<StandardRoomData, String>("room_floor"));
        tableColumnPrice_standard.setCellValueFactory(new PropertyValueFactory<StandardRoomData, Integer>("room_price"));
        tableColumnType_standard.setCellValueFactory(new PropertyValueFactory<StandardRoomData, String>("room_type"));
        tableColumnRemark_standard.setCellValueFactory(new PropertyValueFactory<StandardRoomData, String>("room_remark"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("标准房间信息列表初始化线程已启动...");
                try {
                    for (StandardRoom standardRoom : standardRoomDao.list())
                        standardRoomData_list.add(new StandardRoomData(standardRoom));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mTableStandardRoom.setItems(standardRoomData_list);
            }
        }).start();
    }

    private void clear_standard(){
        txa_remark_standard.clear();
        txf_id_number_standard.clear();
        txf_price_standard.clear();
        cbx_type_standard.setValue(null);
        cbx_floor_standard.setValue(null);
    }

    @FXML
    void ac_refresh_user(ActionEvent event) {
      Platform.runLater(new Runnable() {
          @Override
          public void run() {
              try {
                  System.out.println("用户刷新线程已启动...");
                  iv_picture_user.setImage(new Image("/png/timg.jpeg"));
                  userData_list.removeAll(userData_list);
                  for (User user : userDao.list())
                      userData_list.add(new UserData(user));
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      });
    }

    @FXML
    void ac_AlterPicture_user(ActionEvent event) throws SQLException {
        if (mTableUser.getSelectionModel().getSelectedIndex() == -1) {
            new AlertDefined(Alert.AlertType.INFORMATION, "提示", "当前未选中用户").show();
            return;
        }

        File file = new FileChooser().showOpenDialog(new Stage());
        UserData userSelect = mTableUser.getSelectionModel().getSelectedItem();
        if (file != null) {
            String pictureUrl = new String("/png/" + file.getName());
            iv_picture_user.setImage(new Image(pictureUrl));
            userSelect.setPicture(pictureUrl);
            userDao.update(userSelect.userToEntity());
        }
    }

    @FXML
    void ac_delete_user(ActionEvent event)  {
        try {
            int index = mTableUser.getSelectionModel().getSelectedIndex();
            if (index == -1) {
                new AlertDefined(Alert.AlertType.INFORMATION, "提示", "当前未选中用户").show();
                return;
            }
            UserData selectUser = mTableUser.getSelectionModel().getSelectedItem();
            AlertDefined dialog = new AlertDefined(Alert.AlertType.INFORMATION, "提示", "你确定要删除用户[ " + selectUser.getId() + " ]吗?");
            Optional result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (!userDao.delete(selectUser.userToEntity())) {
                    System.out.println("点击确认");
                    new AlertDefined(Alert.AlertType.ERROR, "提示", "删除用户失败").show();
                    return;
                } else {
                    new AlertDefined(Alert.AlertType.INFORMATION, "提示", "该用户已删除").show();
                    ac_refresh_user(event);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ac_search_user(ActionEvent event) {
        if(txf_search_user.getText().equals("")){
            new AlertDefined(Alert.AlertType.INFORMATION, "提示", "请输入搜索信息").show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("搜索线程已启动...");
                if (!txf_search_user.getText().equals("")) {



                    String search_text = txf_search_user.getText();



                    ArrayList<UserData> search_result_list = new ArrayList<>();



                    try {
                        for (User test:userDao.list()) {
                            if (search_text.equals(String.valueOf(test.getUUID())) ||
                                    search_text.equals(test.getUserType()) ||
                                    search_text.equals(test.getId()) ||
                                    search_text.equals(String.valueOf(test.getPassword()))){


                                search_result_list.add(new UserData(test));
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    userData_list.removeAll(userData_list);
                    for (int j = 0; j < search_result_list.size(); j++) {
                        userData_list.add(j, search_result_list.get(j));
                    }


                    search_result_list.clear();
                }
            }
        }).start();
    }

    @FXML
    private void ac_refresh_standard(ActionEvent event){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("标准刷新线程已启动...");
                    standardRoomData_list.removeAll(standardRoomData_list);
                    clear_standard();
                    for (StandardRoom standardRoom: standardRoomDao.list())
                        standardRoomData_list.add(new StandardRoomData(standardRoom));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void ac_delete_standard(ActionEvent event){
        try {
            int index = mTableStandardRoom.getSelectionModel().getSelectedIndex();
            if (index == -1) {
                new AlertDefined(Alert.AlertType.INFORMATION, "提示", "当前未选中房间").show();
                return;
            }
            StandardRoomData selectStandardRoom = mTableStandardRoom.getSelectionModel().getSelectedItem();
            System.out.println(selectStandardRoom.standardRoomToEntity());
            AlertDefined dialog = new AlertDefined(Alert.AlertType.INFORMATION, "提示", "你确定要删除房间[ " + selectStandardRoom.getRoom_id_number() + " ]吗?");
            Optional result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (!standardRoomDao.delete(selectStandardRoom.standardRoomToEntity())) {
                    new AlertDefined(Alert.AlertType.ERROR, "提示", "删除房间失败").show();
                    return;
                } else {
                    standardRoomData_list.remove(selectStandardRoom);
                    standardRoomDao.delete(selectStandardRoom.standardRoomToEntity());
                    for (InfoRoom infoRoom:infoRoomDao.list()){
                        if(infoRoom.getId_number()==selectStandardRoom.getRoom_id_number()){
                            infoRoomData_list.removeAll(infoRoomData_list);
                            infoRoomDao.delete(infoRoom);
                            for (InfoRoom next:infoRoomDao.list())
                                infoRoomData_list.add(new InfoRoomData(next));

                        }
                    }
                    ac_refresh_standard(event);
                    new AlertDefined(Alert.AlertType.INFORMATION, "提示", "该房间已删除").show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ac_search_standard(ActionEvent event){
        if(txf_search_standard.getText().equals("")){
            new AlertDefined(Alert.AlertType.INFORMATION, "提示", "请输入搜索信息").show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("搜索线程已启动...");
                if (!txf_search_standard.getText().equals("")) {

                    String search_text = txf_search_standard.getText();
                    System.out.println(search_text);


                    ArrayList<StandardRoomData> search_result_list = new ArrayList<>();


                    try {
                        for (StandardRoom test:standardRoomDao.list()) {
                            if (search_text.equals(String.valueOf(test.getRoom_id_number())) ||
                                    search_text.equals(test.getRoom_floor()) ||
                                    search_text.equals(test.getRoom_type()) ||
                                    search_text.equals(String.valueOf(test.getRoom_price()))) {

                                search_result_list.add(new StandardRoomData(test));
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    standardRoomData_list.removeAll(standardRoomData_list);
                    for (int j = 0; j < search_result_list.size(); j++) {
                        standardRoomData_list.add(j, search_result_list.get(j));
                    }


                    search_result_list.clear();
                }
            }
        }).start();
    }

    @FXML
    void  ac_alter_standard(ActionEvent event) throws Exception {
        int index = mTableStandardRoom.getSelectionModel().getSelectedIndex();
        StandardRoomData selectStandardRoom = mTableStandardRoom.getSelectionModel().getSelectedItem();
        if (index == -1) {
            new AlertDefined(Alert.AlertType.INFORMATION, "提示", "当前未选中房间").show();
            return;
        }

        if (cbx_type_standard.getValue()==null||
                txf_id_number_standard.getText().equals(null)||
                txf_price_standard.getText().equals(null)||
                txa_remark_standard.getText().equals(null)||
                cbx_floor_standard.getValue()==null) {
            new AlertDefined(Alert.AlertType.INFORMATION, "提示", "请完善信息").show();
            return;
        }
            if(!standardRoomDao.list().isEmpty())
                for (StandardRoom standardRoom:standardRoomDao.list()){
                    if(!txf_id_number_standard.getText().equals((selectStandardRoom.standardRoomToEntity().getRoom_id_number().toString())))
                        if (txf_id_number_standard.getText().equals((standardRoom.getRoom_id_number().toString()))){
                            new AlertDefined(Alert.AlertType.INFORMATION, "提示", "该编号已存在").show();
                            return;
                        }
                }

        if (!Uitls.isNumber(txf_id_number_standard.getText())||!Uitls.isNumber(txf_price_standard.getText())){
            new AlertDefined(Alert.AlertType.INFORMATION, "提示", "编号/单价请输入数字").show();
            return;
        }

        AlertDefined dialog = new AlertDefined(Alert.AlertType.INFORMATION, "提示", "你确定要修改房间[ " + selectStandardRoom.getRoom_id_number() + " ]的吗?");
        Optional result = dialog.showAndWait();
        if (result.get() == ButtonType.OK) {
            StandardRoom standardRoom=selectStandardRoom.standardRoomToEntity();
            standardRoom.setRoom_id_number(Integer.parseInt(txf_id_number_standard.getText()));
            standardRoom.setRoom_remark(txa_remark_standard.getText());
            standardRoom.setRoom_type(cbx_type_standard.getValue());
            standardRoom.setRoom_floor(cbx_floor_standard.getValue());
            standardRoom.setRoom_price(Integer.parseInt(txf_price_standard.getText()));

            InfoRoom newNext=null;
            for (InfoRoom infoRoom:infoRoomDao.list()){
                if (infoRoom.getId_number() == selectStandardRoom.getRoom_id_number()) {
                    newNext=infoRoom;
                    break;
                }
            }

            newNext.setPs(txa_remark_standard.getText());
                newNext.setType(cbx_type_standard.getValue());
                    newNext.setId_number(Integer.parseInt(txf_id_number_standard.getText()));

            if (!standardRoomDao.update(standardRoom)||!infoRoomDao.update(newNext)) {
                System.out.println("点击确认");
                new AlertDefined(Alert.AlertType.ERROR, "提示", "修改失败").show();
                return;
            } else {
                standardRoomData_list.remove(selectStandardRoom);
                standardRoomData_list.add(new StandardRoomData(standardRoom));
                ac_refresh_standard(event);
                ac_refresh_info(event);
                new AlertDefined(Alert.AlertType.INFORMATION, "提示", "已修改").show();
            }
        }

    }

    @FXML
    void ac_add_standard(ActionEvent event) throws Exception {
        if (cbx_type_standard.getValue()==null||
                txf_id_number_standard.getText().equals(null)||
                txf_price_standard.getText().equals(null)||
                txa_remark_standard.getText().equals(null)||
                 cbx_floor_standard.getValue()==null) {
            new AlertDefined(Alert.AlertType.INFORMATION, "提示", "请完善信息").show();
            return;
        }
    if(!standardRoomDao.list().isEmpty())
        for (StandardRoom standardRoom:standardRoomDao.list()){
                if (txf_id_number_standard.getText().equals(standardRoom.getRoom_id_number().toString())){
                    new AlertDefined(Alert.AlertType.INFORMATION, "提示", "该编号已存在").show();
                    return;
                }
            }

        if (!Uitls.isNumber(txf_id_number_standard.getText())||!Uitls.isNumber(txf_price_standard.getText())){
            new AlertDefined(Alert.AlertType.INFORMATION, "提示", "编号/单价请输入数字").show();
            return;
        }

        StandardRoom standardRoom=new StandardRoom();
        standardRoom.setRoom_id_number(Integer.parseInt(txf_id_number_standard.getText()));
        standardRoom.setRoom_remark(txa_remark_standard.getText());
        standardRoom.setRoom_type(cbx_type_standard.getValue());
        standardRoom.setRoom_floor(cbx_floor_standard.getValue());
        standardRoom.setRoom_price(Integer.parseInt(txf_price_standard.getText()));
        System.out.println(standardRoom);
        standardRoomDao.save(standardRoom);
        standardRoomData_list.add(new StandardRoomData(standardRoom));
        clear_standard();
    }

    @FXML
    void ac_add_info(ActionEvent event) throws Exception {
       UiInfoRoom uiInfoRoom = new UiInfoRoom();
       uiInfoRoom.start(new Stage());
       uiInfoRoom.setInfoRoomData(infoRoomData_list);
    }

    @FXML
    void ac_refresh_info(ActionEvent event){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("标准刷新线程已启动...");
                    infoRoomData_list.removeAll(infoRoomData_list);
                    clear_standard();
                    for (InfoRoom infoRoom: infoRoomDao.list())
                        infoRoomData_list.add(new InfoRoomData(infoRoom));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void ac_delete_info(ActionEvent event){
        try {
            int index = mTableInfoRoom.getSelectionModel().getSelectedIndex();
            if (index == -1) {
                new AlertDefined(Alert.AlertType.INFORMATION, "提示", "当前未选中房间").show();
                return;
            }
            InfoRoomData selectInfoRoom = mTableInfoRoom.getSelectionModel().getSelectedItem();
            AlertDefined dialog = new AlertDefined(Alert.AlertType.INFORMATION, "提示", "你确定要房间[ " + selectInfoRoom.getId_number()+ " ]详情吗?");
            Optional result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (!infoRoomDao.delete(selectInfoRoom.infoRoomToEntity())) {
                    new AlertDefined(Alert.AlertType.ERROR, "提示", "删除失败").show();
                    return;
                } else {
                    new AlertDefined(Alert.AlertType.INFORMATION, "提示", "已删除").show();
                    infoRoomData_list.remove(selectInfoRoom);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ac_search_info(ActionEvent event){
        if(txf_search_info.getText().equals("")){
            new AlertDefined(Alert.AlertType.INFORMATION, "提示", "请输入搜索信息").show();
            return;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("搜索线程已启动...");
                if (!txf_search_info.getText().equals("")) {



                    String search_text = txf_search_info.getText();
                    System.out.println(search_text);


                    ArrayList<InfoRoomData> search_result_list = new ArrayList<>();



                    try {
                        for (InfoRoom test:infoRoomDao.list()) {
                            if (search_text.equals(String.valueOf(test.getId_number()))||
                                    search_text.equals(test.getAir_conditioning())||
                                    search_text.equals(String.valueOf(test.getIphone()))||
                                    search_text.equals(test.getRest())||
                                    search_text.equals(test.getType())||
                                    search_text.equals(test.getTv())||
                                    search_text.equals(String.valueOf(test.getArea()))||
                                    search_text.equals(String.valueOf(test.getMax_bed()))||
                                    search_text.equals(String.valueOf(test.getMax_people()))){


                                search_result_list.add(new InfoRoomData(test));
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    infoRoomData_list.removeAll(infoRoomData_list);
                    for (int j = 0; j < search_result_list.size(); j++) {
                        infoRoomData_list.add(j, search_result_list.get(j));
                    }

                    //
                    // 清空
                    //
                    search_result_list.clear();
                }
            }
        }).start();
    }

    @FXML
    void ac_backup_restore_item(ActionEvent event){
        new FileChooserDefined().start(new Stage());
    }

    @FXML
    void ac_technology_item(ActionEvent event){
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                URI uri = new URI("mailto:1596863112@qq.com");
                desktop.mail(uri);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @FXML
    void ac_web_item(ActionEvent event){
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                URL url = new URL("http://www.hotelhis.com/yx/?bd_vid=12200328551249509319");
                desktop.browse(url.toURI());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        try{
            initDataDao();
            initProprety();
            initUserTable();
            initClientTable();
            initBookRoomTable();
            initInfoRoomTable();
            initStandardRoom();
            initTableColumnEvent();
            initTableEvent();
            //tableColumnType_Info.setCellValueFactory(cellData ->cellData.getValue().cb.getComboBox());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

