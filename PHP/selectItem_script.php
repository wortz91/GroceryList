<?php 

	//variables
	$host="localhost";
	$uname="frank73_w16groc";
	$pwd="Groc16";
	$db="frank73_w16groc";
	
	//create a connection
 	$conn = new mysqli($host, $uname, $pwd, $db);
	
	//check connection
	 if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}
	
	//select database
	mysqli_select_db($conn, $db) or die("db selection failed");
	
	//These have been passed in
	$ItemID=$_REQUEST['ItemID'];
	
	//Sql code
	$sql = "
	SELECT *
	FROM frank73_w16groc.items
	WHERE ItemID='$ItemID';";
	
	//Execute
	if ($result = $conn->query($sql)) {
		while($row = mysqli_fetch_assoc($result))
		{
			$output[] = $row;
		}
	}
	if ($output != null) {
		print(json_encode($output));

	} else {
		print("Error: Could not find item with id = " . $ItemID);
	}
	$conn->close();
?>