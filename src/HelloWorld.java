
public class HelloWorld {

	private static DatabaseConnection db;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			System.out.println("Hello World");
			db = new DatabaseConnection();
			db.accessDB();
			//db.createEntry();
			//db.readDB();
			//db.updateDB();
			db.deleteRow();
			db.readDB();
	}

}
