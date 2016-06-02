<?php

$servername = "localhost";
$username = "frank73_w16groc";
$password = "Groc16";
$db="frank73_w16groc";


// Create connection
 $conn = new mysqli($servername, $username, $password, $db);
// Check connection
 if ($conn->connect_error) {
     die("Connection failed: " . $conn->connect_error);
}
$data = json_decode(file_get_contents('php://input'), true);

$ItemName = $data['ItemName'];
$ItemUnitType = $data['ItemUnitType'];
$ItemDescription = $data['ItemDescription'];
$ItemPrice = $data['ItemPrice'];
$ItemCount = $data['ItemCount'];
$ItemCategory = $data['ItemCategory'];

$sql = "INSERT INTO items (ItemName, ItemUnitType, ItemDescription, ItemPrice, ItemCount, ItemCategory)
VALUES ('$ItemName', '$ItemUnitType', '$ItemDescription', '$ItemPrice', '$ItemCount', '$ItemCategory');";

if ($conn->query($sql) === TRUE)
{
	//success
	echo "New record created successfully";
	
	
}	else {
	echo "Error: " . $sql . "<br>" . $conn->error;
}


$conn->close();

?>
