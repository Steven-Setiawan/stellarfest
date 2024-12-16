package view;

import java.util.ArrayList;
import java.util.List;

import controller.VendorController;
import database.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.VendorProduct;

public class VendorManageProductView extends BorderPane{

	private VendorNav nav;
	private VBox wrapper, formWrapper;
	private TableView<VendorProduct> productTable;
	private List<VendorProduct> products;
	private GridPane formContainer;
	private Label productNameLbl, productDescriptionLbl, title;
	private TextArea productNameField, productDescriptionField;
	private Button addBtn;
	private VendorController controller;
	
	private void init() {
		// inisialisasi komponen yang akan digunakan
		nav = new VendorNav();
		wrapper = new VBox(20);
		formContainer = new GridPane();
		productTable = new TableView<VendorProduct>();
		products = new ArrayList<VendorProduct>();
		addBtn = new Button("Add");
		formWrapper = new VBox(10);
		controller = new VendorController();
		
		title = new Label("Add Product");
		productNameLbl = new Label("Product Name");
		productDescriptionLbl = new Label("Product Description");
		
		productNameField = new TextArea();
		productDescriptionField = new TextArea();
		
		productNameField.setPrefWidth(350);
		productDescriptionField.setPrefWidth(350);
		
		productNameField.setPrefHeight(20);
		productDescriptionField.setPrefHeight(60);
		productDescriptionField.setWrapText(true);
		
		this.formContainer.setVgap(10);
		this.formContainer.setHgap(10);
		
		this.addBtn.setOnAction(event -> {
			handleAdd();
		});
		
		this.title.setStyle("-fx-font-size: 24px;");
	}
	
	private void setLayout() {
		// memasukkan komponen ke container yang ada, agar dapat ditampilkan
		this.formContainer.add(productNameLbl, 0, 1);
		this.formContainer.add(productNameField, 1, 1);
		this.formContainer.add(productDescriptionLbl, 0, 2);
		this.formContainer.add(productDescriptionField, 1, 2);
		
		this.formWrapper.getChildren().addAll(title, formContainer, addBtn);
		
		this.wrapper.getChildren().addAll(nav, formWrapper, productTable);
		this.setTop(wrapper);
	}
	
	// setting table dengan membuat kolom
	private void setTable() {
		TableColumn<VendorProduct, String> productNameColumn = new TableColumn<VendorProduct, String>("Product Name");
		productNameColumn.setCellValueFactory(new PropertyValueFactory<VendorProduct, String>("productName"));
		productNameColumn.setPrefWidth(100);
		
		TableColumn<VendorProduct, String> productDescriptionColumn = new TableColumn<VendorProduct, String>("Description");
		productDescriptionColumn.setCellValueFactory(new PropertyValueFactory<VendorProduct, String>("productDescription"));
		productDescriptionColumn.setPrefWidth(300);
		
		this.productTable.getColumns().addAll(productNameColumn, productDescriptionColumn);
	}
	
	// mengambil data dan di isi ke table 
	private void loadData() {
		this.products.clear();
		this.products = controller.getAllProducts(Session.getInstance().getLoggedInUser().getId());
		ObservableList<VendorProduct> productList = FXCollections.observableArrayList(this.products);
		this.productTable.setItems(productList);
	}
	
	// function untuk menghandle button Add product
	private void handleAdd() {
		String productName = this.productNameField.getText().toString();
		String productDescription = this.productDescriptionField.getText().toString();
		if(controller.checkManageVendorInput(productName, productDescription)) {
			if(controller.manageVendor(productName, productDescription)) {
				// show success message
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
		        alert.setHeaderText(null);
		        alert.setContentText("Product Has Added");
				loadData();
				alert.showAndWait();
			}else {
				// show error message
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Failed");
		        alert.setHeaderText(null);
		        alert.setContentText("Product Add operation is Failed");
				alert.showAndWait();
			}
		}
	}
	
	public VendorManageProductView() {
		init();
		setLayout();
		setTable();
		loadData();
	}

}
