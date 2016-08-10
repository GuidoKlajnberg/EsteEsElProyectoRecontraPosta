<?php
$con=mysqli_connect("localhost","u760341785_guido","gk3003","u760341785_mydb");
if(mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " .mysqli_connect_error();
}
else
{
	$us=$_GET["NombreUsuario"];
	$query = "SELECT idUsuario FROM Usuario WHERE NombreUsuario='$us'";
$query_exc=mysqli_query($con, $query);
$usuarios = Array();
		while($row=mysqli_fetch_assoc($query_exc)){
			$usuario = Array(
			"idUsuario" => $row["idUsuario"]
			);
			array_push($usuarios, $usuario);
		}
	
	
	header("Content-Type: application/json");
	$json = json_encode($usuarios, JSON_PRETTY_PRINT);
	echo($json);
}
mysqli_close($con);

?>