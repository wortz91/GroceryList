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

$UserName = $_REQUEST['UserName'];
$Password = $_REQUEST['Password'];

$sql = "SELECT * FROM users WHERE UserName = $UserName AND Password = $Password;";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $resultArray = array();
	$row = mysqli_fetch_array($result);
	array_push($resultArray,array(
	"UserID"=>$row['UserID']));
	
	echo json_encode(array('result'=>$resultArray));
} else {
    echo "Login not successful";
}

$conn->close();

?>