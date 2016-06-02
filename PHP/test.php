<?php
$servername = "localhost";
$username = "frank73_w16groc";
$password = "Groc16";

// Create connection
 $conn = mysqli_connect($servername, $username, $password);

// Check connection
 if (!$conn) {
     die("Connection failed: " . mysqli_connect_error());
}
echo "Connected successfully";
 ?> 