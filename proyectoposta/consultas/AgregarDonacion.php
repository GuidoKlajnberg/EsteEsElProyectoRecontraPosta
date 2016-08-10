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
$query = "INSERT INTO DonacionCosaNecesitada (Usuario_idUsuario,Necesitado_idNecesitado,CosasNecesitadas_idCosaNecesitada) values (?,?,?)";
$stmt=$con->prepare($query);
$stmt->bind_param(
		'sss',
		$usuario["Usuario_idUsuario"],
		$usuario["Necesitado_idNecesitado"],
		$usuario["CosasNecesitadas_idCosaNecesitada"]
		);
		$stmt->execute();
		$res=$stmt->get_result();

}
mysqli_close($con);

?>




