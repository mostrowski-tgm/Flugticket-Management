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
		
		try{			
		$conn = new PDO("$db_data:host=$db_servername;dbname=$db_name", $db_username, $db_password);
		$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
		$stmt = $conn -> prepare("SELECT * FROM flights WHERE flightnr LIKE :flugnummer");
		
		$stmt -> bindParam(":flugnummer", $flugnummer);
		$flugnummer = $_POST["flugnr"];
		$stmt -> execute();
    
			if($stmt -> rowCount() > 0){
				$flight = $stmt -> fetch();
				$airline = $flight[0];
				$start_dep_time = $flight[2];
				$start_dep_air = $flight[3];
				$end_des_time = $flight[4];
				$end_des_air = $flight[5];
			}
			
		$start_dep_time_date = substr($start_dep_time, 0, 10);
		$start_dep_time_clock = substr($start_dep_time, 11, 8);
		
		$end_des_time_date = substr($end_des_time, 0, 10);
		$end_des_time_clock = substr($end_des_time, 11, 8);
		
		}
		catch(PDOException $e) {
		echo "Connection failed: " . $e->getMessage();
		}
		/* 
		Zum Darstellen von den Cookies
		print_r($_COOKIE);
		*/
		?>		
		<div class="container">
			<div class="row">
				<div class="text-center">
					<h1>Fluginformationen zum Flug <?php echo strtoupper($flugnummer); echo strtoupper($airline); ?></h1>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4>Abfluginfos</h4>
						</div>
						<div class="panel-body">
							<div class="input-group col-xs-12">
								<?php
								//Holt sich das Landkürzel von der Datenbank durch den Flughafenkürzel
									echo "<h4>Land: </h4><h5>";
									$result = $conn -> query("SELECT country FROM airports WHERE airportcode LIKE '$start_dep_air'");
									$row = $result -> fetch();
									$landkürzel = $row[0];
								//Holt sich das Land von der Datenbank durch den Landkürzel
									$result = $conn -> query("SELECT name FROM countries WHERE code LIKE '$landkürzel'");
									$row = $result -> fetch();
									echo $row[0];
									echo "<br>";
									echo "</h5>";
								//Holt sich das Land von der Datenbank durch den Flughafenkürzel
									echo "<h4>Stadt: </h4><h5>";
									$result = $conn -> query("SELECT city FROM airports WHERE airportcode LIKE '$start_dep_air'");
									$row = $result -> fetch();
									echo $row[0];
									echo "<br>";
									echo "</h5>";
								//Holt sich den Flughafenname von der Datenbank durch den Flughafenkürzel
									echo "<h4>Fluhafen: </h4><h5>";
									$result = $conn -> query("SELECT name FROM airports WHERE airportcode LIKE '$start_dep_air'");
									$row = $result -> fetch();
									echo $row[0];
									echo "<br>";
									  echo "</h3>";
								//Gibt das Abflugsdatum aus
									echo "<h4>Datum: </h4><h5>";
									echo $start_dep_time_date;
									echo "<br>";
									echo "</h5>";
								//Gibt die Abflugs Zeit aus
									echo "<h4>Uhrzeit: </h4><h5>";
									echo $start_dep_time_clock;
									echo "</h5>";
								?>
							</div>
						</div>	
					</div>			
				</div>
				<div class="col-xs-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4>Anfluginfos</h4>
						</div>
						<div class="panel-body">
							<div class="input-group col-xs-12">
								<?php
								//Holt sich das Landkürzel von der Datenbank durch den Flughafenkürzel
									echo "<h4>Land: </h4><h5>";
									$result = $conn -> query("SELECT country FROM airports WHERE airportcode LIKE '$end_des_air'");
									$row = $result -> fetch();
									$landkürzel = $row[0];
								//Holt sich das Land von der Datenbank durch den Landkürzel
									$result = $conn -> query("SELECT name FROM countries WHERE code LIKE '$landkürzel'");
									$row = $result -> fetch();
									echo $row[0];
									echo "<br>";
									echo "</h5>";
								//Holt sich das Land von der Datenbank durch den Flughafenkürzel
									echo "<h4>Stadt: </h4><h5>";
									$result = $conn -> query("SELECT city FROM airports WHERE airportcode LIKE '$end_des_air'");
									$row = $result -> fetch();
									echo $row[0];
									echo "<br>";
									echo "</h5>";
								//Holt sich den Flughafenname von der Datenbank durch den Flughafenkürzel
									echo "<h4>Fluhafen: </h4><h5>";
									$result = $conn -> query("SELECT name FROM airports WHERE airportcode LIKE '$end_des_air'");
									$row = $result -> fetch();
									echo $row[0];
									echo "<br>";
									  echo "</h5>";
								//Gibt das Abflugsdatum aus
									echo "<h4>Datum: </h4><h5>";
									echo $end_des_time_date;
									echo "<br>";
									echo "</h5>";
								//Gibt die Abflugs Zeit aus
									echo "<h4>Uhrzeit: </h4><h5>";
									echo $end_des_time_clock;
									echo "</h5>";
								?>
							</div>
						</div>	
					</div>			
				</div>
			</div>
			<div class="row">
			
			</div>
		</div>
	</body>
</html>
</html>