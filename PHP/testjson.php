<?php

$ItemName = $_REQUEST['ItemName'];
$ItemUnitType = $_REQUEST['ItemUnitType'];
$ItemDescription = $_POST['ItemDescription'];
$ItemPrice = $_POST['ItemPrice'];
$ItemCount = $_POST['ItemCount'];
$ItemCategory = $_POST['ItemCategory'];

echo var_dump($_REQUEST);
echo var_dump($_POST);

echo "json decoded input:";
$data = json_decode(file_get_contents('php://input'), true);
print_r($data);


echo "get contents input:";
echo file_get_contents('php://input');

echo "var_dump raw post:";
echo var_dump($HTTP_RAW_POST_DATA);

?>