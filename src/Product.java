import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Product {

    private final SimpleIntegerProperty productID;
    private final SimpleStringProperty productName;
    private final SimpleIntegerProperty productQuantity;
 
    Product(Integer pID, String pName, Integer pQuantity) {
        this.productID = new  SimpleIntegerProperty (pID);
        this.productName = new SimpleStringProperty (pName);
        this.productQuantity = new  SimpleIntegerProperty (pQuantity);
    }
 
    public int getProductID() {
        return productID.get();
    }
    public void setProductID(int pID) {
        productID.set(pID);
    }
        
    public String getProductName() {
        return productName.get();
    }
    public void setProductName(String pName) {
        productName.set(pName);
    }
    
    public int getProductQuantity() {
        return productQuantity.get();
    }
    public void setProductQuantity(int pQuantity) {
        productQuantity.set(pQuantity);
    }
    
}
	
