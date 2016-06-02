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

$UserID = $_GET['UserID'];
$ItemID = $_GET['ItemID'];



$sql = "INSERT INTO frank73_w16groc.useritems (UserID, ItemID)
VALUES ('$UserID', '$ItemID');";

if ($conn->query($sql) === TRUE)
{
    //success
    echo "New record created successfully";
}   else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}


$conn->close();

?>
