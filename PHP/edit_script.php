<?php 

	if ($_SERVER['REQUEST_METHOD']=='GET') {

		//variables
		define('HOST','localhost');
		define('USER','frank73_w16groc');
		define('PASS','Groc16');
		define('DB','frank73_w16groc');
		
		//create a connection
		$conn = mysqli_connect(HOST, USER, PASS, DB) or die('Unable to connect');
		
		//select database
		mysqli_select_db($conn, DB) or die("db selection failed");
		
		//These have been passed in
		$ItemID=$_GET['ItemID'];
		$ItemName=$_GET['ItemName'];
		$ItemUnitType=$_GET['ItemUnitType'];
		$ItemDescription=$_GET['ItemDescription'];
		$ItemPrice=$_GET['ItemPrice'];
		$ItemCount=$_GET['ItemCount'];
		$ItemCategory=$_GET['ItemCategory'];
	
		$sql = "
		UPDATE items 
		SET 
			ItemName = '$ItemName', ItemUnitType = '$ItemUnitType',	ItemDescription = '$ItemDescription',
			ItemPrice = $ItemPrice,	ItemCount = $ItemCount,	ItemCategory = '$ItemCategory'
		WHERE
			ItemID = $ItemID;";
			
		$result = mysqli_query($conn, $sql);
		
		if ($result) {
			//successfully inserted into database
			$response["success"] = 1;
			$response["message"] = "Item successfully updated.";
			
			$encoded_rows = array_map('utf8_encode', $response);
			echo json_encode($encoded_rows);
		}
		else {
			//failed to insert row
			$response["success"] = 0;
			$response["message"] = "Oops! An error occurred.";
			
			$encoded_rows = array_map('utf8_encode', $response);
			echo json_encode($encoded_rows);
		}
		
			
		mysqli_close($conn);
	}
?>