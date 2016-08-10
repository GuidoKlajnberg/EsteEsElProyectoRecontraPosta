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
$query = "INSERT INTO NecesitadoHabilidades (Habilidades_idHabilidad,Necesitado_Nombre,Fecha) values (?,?,?)";
$stmt=$con->prepare($query);
$stmt->bind_param(
		'sss',
		$usuario["Habilidades_idHabilidad"],
		$usuario["Necesitado_Nombre"],
		$usuario["Fecha"]
		);
		$stmt->execute();
		$res=$stmt->get_result();

}
mysqli_close($con);

?>