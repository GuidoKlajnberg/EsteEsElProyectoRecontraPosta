<?php
$con=mysqli_connect("localhost","u760341785_guido","gk3003","u760341785_mydb");
if(mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " .mysqli_connect_error();
}
else
{
	$query = "SELECT Nombre FROM Habilidades";
$query_exc=mysqli_query($con, $query);
$habilidades = Array();
		while($row=mysqli_fetch_assoc($query_exc)){
			$habilidad = Array(
			"Nombre" => $row["Nombre"]
			);
			array_push($habilidades, $habilidad);
		}
	
	
	header("Content-Type: application/json");
	$json = json_encode($habilidades, JSON_PRETTY_PRINT);
	echo($json);
}
mysqli_close($con);

?>