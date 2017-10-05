<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		
		<title>
            Flug Data
        </title>
		
		<link href="css/bootstrap.min.css" rel="stylesheet">
		
	
    </head>
    <body>
	<?php
		
		$db_servername = $_COOKIE['adresse'];
		$db_name = "flightdata";
		$db_username = $_COOKIE['username'];
		$db_password = $_COOKIE['password'];
		$db_data = $_COOKIE['typ'];
		$db_port = $_COOKIE['port'];
		$id = $_POST['pass_id'];
		$flugnr = $_COOKIE['Flugnummer'];
		
		try{			
		$conn = new PDO("$db_data:$db_port=$db_servername;dbname=$db_name", $db_username, $db_password);
		$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
				
		$stmt = $conn -> query("SELECT flightnr FROM passengers WHERE id LIKE $id");

		//Löscht den Passagier mit der ID, welche vom Buttonklick mitgeschickt wurde.
		$conn -> query("DELETE FROM passengers WHERE id=$id");
		header("Location: Web_FlugData.php?flugnr=$flugnr");
		}
		catch(PDOException $e) {
		echo "Connection failed: " . $e->getMessage();
		}
		/* 
		Zum Darstellen von den Cookies
		print_r($_COOKIE);
		*/
		?>
	</body>
</html>