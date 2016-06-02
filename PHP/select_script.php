<?php
	$host="localhost";
	$uname="frank73_w16groc";
	$pwd="Groc16";
	$db="frank73_w16groc";

	// Create connection
 	$dbh = mysqli_connect($host, $uname, $pwd);

	// Check connection
	 if (!$dbh) {
	     die("Connection failed: " . mysqli_connect_error());
	}

	//$dbh = mysql_connect($host, $uname, $pwd) or die("Cannot connect to the database because");

	mysqli_select_db($dbh, $db) or die("db selection failed");

	$UniqueID = $_REQUEST['UniqueID'];
	$UserID = $_REQUEST['UserID'];
	$ItemID = $_REQUEST['ItemID'];

	

	if ($result = mysqli_query($dbh, "SELECT frank73_w16groc.items.ItemID, frank73_w16groc.items.ItemName, frank73_w16groc.items.ItemCategory FROM frank73_w16groc.items INNER JOIN frank73_w16groc.useritems ON frank73_w16groc.items.ItemID = frank73_w16groc.useritems.ItemID  WHERE frank73_w16groc.useritems.UserID = '$UserID' ")) {
		while($row = mysqli_fetch_assoc($result))
		{
			$output[] = $row;
		}
	}
	print(json_encode($output));

	mysqli_close($dbh);

?>