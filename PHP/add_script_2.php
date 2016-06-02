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
$request = file_get_contents('php://input')
$input = json_decode($request, true);

$ItemName = $input['ItemName'];
$ItemUnitType = $input['ItemUnitType'];
$ItemDescription = $input['ItemDescription'];
$ItemPrice = $input['ItemPrice'];
$ItemCount = $input['ItemCount'];
$ItemCategory = $input['ItemCategory'];

$sql = "INSERT INTO items (ItemName, ItemUnitType, ItemDescription, ItemPrice, ItemCount, ItemCategory)
VALUES ('$ItemName', '$ItemUnitType', '$ItemDescription', '$ItemPrice', '$ItemCount', '$ItemCategory)';";

if ($conn->query($sql) === TRUE)
{
	//success
	echo "New record created successfully";
}	else {
	echo "Error: " . $sql . "<br>" . $conn->error;
}


$conn->close();

?>