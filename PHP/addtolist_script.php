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

$UserID = $_REQUEST['UserID'];
$ItemID = $_REQUEST['ItemID'];

$sql = "INSERT INTO useritems (UserID, ItemID) VALUES ($UserID, $ItemID);";

if ($conn->query($sql) === TRUE)
{
	//success
	echo "New record created successfully";
}	else {
	echo "Error: " . $sql . "<br>" . $conn->error;
}


$conn->close();

?>