<?php
$con=mysqli_connect("localhost","u760341785_guido","gk3003","u760341785_mydb");
if(mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " .mysqli_connect_error();
}
else
{		
$string = file_get_contents('php://input');
$usuario=json_decode($string,true);
$query = "INSERT INTO Necesitado (Nombre,Sexo,Descripcion) values (?,?,?)";
$stmt=$con->prepare($query);
$stmt->bind_param(
		'sss',
		$usuario["Nombre"],
		$usuario["Sexo"],
		$usuario["Descripcion"]
		);
		$stmt->execute();
		$res=$stmt->get_result();

}
mysqli_close($con);

?>