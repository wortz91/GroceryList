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

$UserName = $_REQUEST['UserID'];
$Password = $_REQUEST['Password'];

$sql1 = "SELECT * FROM users WHERE UserName = '$UserName' AND Password = '$Password';";
$sql2 = "INSERT INTO users (UserName, Password)
VALUES ('$UserName', '$Password');";

$result = $conn->query($sql1);

if ($result->num_rows > 0) {
    echo "Registration failed";
} else if ($conn->query($sql2) === TRUE) {
	//success
	echo "New record created successfully";
} else {
	echo "Error: " . $sql2 . "<br>" . $conn->error;
}


$conn->close();

?>