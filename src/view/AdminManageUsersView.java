package view;

import java.util.ArrayList;
import java.util.List;

import controller.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;

public class AdminManageUsersView extends BorderPane{

	private AdminNav nav;
	private AdminController ac;
	private List<User> users;
	private VBox container;
	private TableView<User> userTable;
	private Label tableLbl;
	
	
	private void init() {
		// inisialisasi komponen yang akan digunakan
		ac = new AdminController();
		nav = new AdminNav();
		container = new VBox();
		userTable = new TableView<User>();
		users = new ArrayList<User>();
		tableLbl = new Label("User List");
	}

	private void setLayout() {
		// memasukkan komponen ke container yang ada, agar dapat ditampilkan
		this.container.setSpacing(15);
		this.container.getChildren().addAll(tableLbl, userTable);
		this.setTop(nav);
		this.setCenter(container);
	}
	
	private void setTable() {
		// inisialisasi kolom-kolom pada tabel
		TableColumn<User, String> usernameColumn = new TableColumn<User, String>("Username");
		usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		
		TableColumn<User, String> emailColumn = new TableColumn<User, String>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		
		TableColumn<User, String> roleColumn = new TableColumn<User, String>("Role");
		roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("roleName"));
		
		TableColumn<User, Void> actionCol = new TableColumn<User, Void>("Action");
		
		actionCol.setCellFactory(param -> new TableCell<User, Void>(){
			private final Button deleteBtn = new Button("Delete");
			{
				// handle click dari Delete Button
				deleteBtn.setOnAction(event -> {
					// mengeluarkan confirmation alert
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confrim???");
					alert.setHeaderText(null);
					alert.setContentText("Confirm to delete");
					
					alert.showAndWait().ifPresent(response -> {
			            if (response == ButtonType.OK) {
							User currUser = getTableView().getItems().get(getIndex());
			            	handleDelete(currUser.getId());
			            }
					});
				});
				
			}
			
			// set button ke container
			protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox();
                	hbox.getChildren().addAll(deleteBtn);
                    hbox.setSpacing(10);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
		});
		this.userTable.getColumns().addAll(usernameColumn, emailColumn, roleColumn, actionCol);
	}
	
	private void loadData() {
		// mengambil data dan di isi ke table 
		users.clear();
		this.users = ac.getAllUsers();
		ObservableList<User> userList = FXCollections.observableArrayList(this.users);
		this.userTable.setItems(userList);
	}
	
	private void setStyle() {
		this.tableLbl.setStyle("-fx-font-size: 24px;");
	}
	
	// handle delete
	private void handleDelete(int userId) {
		if(ac.deleteUser(userId)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
	        alert.setHeaderText(null);
	        alert.setContentText("User Has Deleted");
			loadData();
			alert.showAndWait();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Failed");
	        alert.setHeaderText(null);
	        alert.setContentText("delete is fail");
			alert.showAndWait();
		}
	}
	
	public AdminManageUsersView() {
		init();
		setLayout();
		setTable();
		loadData();
		setStyle();
	}

}
