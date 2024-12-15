package controller;

import java.util.List;

import database.VendorDataAccess;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Event;
import model.VendorProduct;

public class VendorController {

	private VendorDataAccess vd;
	
	public VendorController() {
		vd = new VendorDataAccess();
	}
	
	public List<VendorProduct> getAllProducts(int vendorId){
		return vd.getAllProducts(vendorId);
	}
	
	public boolean manageVendor(String productName, String productDescription) {
		return vd.manageVendor(productName, productDescription);
	}
	
	public boolean checkManageVendorInput(String productName, String productDescription) {
		if(productName.isEmpty() || productDescription.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Failed");
	        alert.setHeaderText(null);
	        alert.setContentText("Fill all The Fields");
	        alert.showAndWait();
	        return false;
		}else if(productDescription.length() > 200){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Failed");
	        alert.setHeaderText(null);
	        alert.setContentText("Product Description must less than 200 characters or equal");
	        alert.showAndWait();
	        return false;
		}
		
		return true;
	}
	
	public List<Event> getAcceptedEvent(String email){
		return vd.getAcceptedEvent(email);
	}
	
	public List<Event> getInvitations(String email){
		return vd.getInvitations(email);
	}
	
	public boolean acceptInvitation(int eventId) {
		return vd.acceptInvitation(eventId);
	}

}
