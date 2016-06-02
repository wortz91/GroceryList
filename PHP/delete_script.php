<?PHP

	//variables
	define('HOST','localhost');
	define('USER','frank73_w16groc');
	define('PASS','Groc16');
	define('DB','frank73_w16groc');
	
	//create a connection
 	$conn = mysqli_connect(HOST, USER, PASS, DB) or die('Unable to connect');
	
	//select database
	mysqli_select_db($conn, DB) or die("db selection failed");
	
	//These have been passed in.
	$ItemID=$_GET['ItemID'];
	$UserID=$_GET['UserID'];
	
	//Sql code
	$sql = "
		DELETE FROM frank73_w16groc.useritems
		WHERE ItemID=$ItemID
		AND UserID=$UserID;";
		
	//Execute
	if (mysqli_query($conn,$sql)) {
		$message="Item data deleted successfully.";
	} else {
		$message="Error deleting record: " . $conn->error;
	}
	
	//Print
	print($message);
	$conn->close();
?>