<?php 
	//DIFFERENT APPROACH!!!!
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
	//$ItemID= (int) ;

	//Sql code
	$sql = "
	SELECT *
	FROM items
	WHERE ItemID=$ItemID;";

	//Execute
	$r = mysqli_query($conn, $sql);
	
	//pushing result to an array
	$item = array();
	$row = mysqli_fetch_array($r);
	array_push($item, array(
	"ItemName" => $row['ItemName'],
	"ItemUnitType" => $row['ItemUnitType'],
	"ItemDescription" => $row['ItemDescription'],
	"ItemPrice" => $row['ItemPrice'],
	"ItemCount" => $row['ItemCount'],
	"ItemCategory" => $row['ItemCategory']
	));
//		$item[ItemName] = $ItemName;
//		$item[ItemUnitType] = $ItemUnitType;
//		$item[ItemDescription] = $ItemDescription;
//		$item[ItemPrice] = $ItemPrice;
//		$item[ItemCount] = $ItemCount;
//		$item[ItemCategory] = $ItemCategory;
	
	
	echo json_encode(array('item' => $item));
	
	
	$conn->close();
?>