<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		
		<title>
            FlugManager - Data Output
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
		$first = TRUE;
		
		try{			
		$conn = new PDO("$db_data:$db_port=$db_servername;dbname=$db_name", $db_username, $db_password);
		$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
		$stmt = $conn -> prepare("SELECT * FROM flights WHERE flightnr LIKE :flugnummer");
		
		$stmt -> bindParam(":flugnummer", $flugnummer);
		if($first = TRUE){
			$flugnummer = $_GET["flugnr"];	
		}else{
			$flugnummer = $_COOKIE['Flugnummer'];
			$first = FALSE;
		}
		$stmt -> execute();
    
			if($stmt -> rowCount() > 0){
				$flight = $stmt -> fetch();
				$airline = $flight[0];
				$start_dep_time = $flight[2];
				$start_dep_air = $flight[3];
				$end_des_time = $flight[4];
				$end_des_air = $flight[5];
				$planetyp = $flight[6];
			}
			
		$start_dep_time_date = substr($start_dep_time, 0, 10);
		$start_dep_time_clock = substr($start_dep_time, 11, 8);
		
		$end_des_time_date = substr($end_des_time, 0, 10);
		$end_des_time_clock = substr($end_des_time, 11, 8);
		
		
		}
		catch(PDOException $e) {
		echo "Connection failed: " . $e->getMessage();
		}
		
		//Zusatzdaten werden in Cookie gespeichert
		setcookie("Flugnummer", $flugnummer);
		setcookie("Fluglinie", $airline);
		setcookie("Flugzeugtyp", $planetyp);
		setcookie("Abflugsdatum", $start_dep_time_date);
		setcookie("Abflugszeit", $start_dep_time_clock);
		setcookie("Anflugsdatum", $end_des_time_date);
		setcookie("Anflugszeit", $end_des_time_clock);
		setcookie("Abflughafen", $start_dep_air);
		setcookie("Anflughafen", $end_des_air);
		
		//print_r($_COOKIE);
		?>		
		<div class="container">
			<div class="row">
				<div class="text-center">
					<h1>Fluginformationen zum Flug 
					<?php 
					//result wird vorbereitet und gefetch --> holt (in dem Fall) nur eine Row und der erste Index wird auf die Variable $airlinename gespeichert.
					$result = $conn -> query("SELECT name FROM airlines WHERE id LIKE '$airline'");
					$row = $result -> fetch();
					$airlinename = $row[0];
					echo $airlinename; echo " ("; echo strtoupper($airline); echo ") "; echo $flugnummer; ?></h1>
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
				<div class="col-xs-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							Passagiere des Flugs <?php echo $airlinename; echo " ("; echo strtoupper($airline); echo ") "; echo $flugnummer; ?>
						</div>
						<div class="panel-body">
							<table class="table table-bordered">
								<tr>
									<th>
										Vorname
									</th>
									<th>
										Nachname
									</th>
									<th colspan="2">
										Sitznummer
									</th>
								</tr>
								<?php
								//Die gefundenen Passagiere werden in die Tabelle eingetragen und gezählt
								$result = $conn -> query("SELECT firstname, lastname, rownr, seatposition, id FROM passengers WHERE airline LIKE '$airline' AND flightnr = $flugnummer ORDER BY 3 ASC, 4 ASC");
								$counter = 0;
								while($row = $result -> fetch()){
									echo '<form method="POST" action="Web_Passenger.php">';
									echo '<tr><td>'.$row[0].'</td>';
									echo '<td>'.$row[1].'</td>';
									echo '<td>'.$row[2].$row[3].'</td>';
									echo '<td><button type="submit" class="btn btn-primary btn-danger">Entfernen des Passagieres</button></td>';
									//Line wird gebraucht um ID zum Entfernen zu schicken
									echo '<td style="visibility:hidden;"><input type="text" name="pass_id" value="'.$row[4].'"></td>';
									echo '</tr>';
									echo '</form>';
									$counter = $counter + 1;
								}
								?>
								<tr>
									<td colspan="2">
									<strong>Anzahl aller Sitze im Flugzeug</strong>
									</td>
									<td colspan="2">
									<?php
									//Maximale Sitzanzahl wird geholt
									$result = $conn -> query("SELECT maxseats FROM planes WHERE id LIKE $planetyp");
									$row = $result -> fetch();
									$maxseat = $row[0];
									echo $maxseat;
									?>
									</td>
								</tr>
								<tr>
									<td colspan="2">
									<strong>Anzahl aller Passagiere</strong>
									</td>
									<td colspan="2">
									<?php
									//Ruft den Counter der Passagiere auf
									echo $counter 
									?>
									</td>
								</tr>
								<tr>
									<td colspan="2">
									<strong>Anzahl aller freien Plätze</strong>
									</td>
									<td colspan="2">
									<?php
									//Ausrechnung der freien Plätze
									$frei = $maxseat - $counter;
									echo $frei 
									?>
									</td>
								</tr>
							</table>
						</div>
					</div>
                </div>        
            </div>
        </div>
	</body>
</html>