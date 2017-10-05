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
				$start_arp = $flight[2];
				$start_apc = $flight[3];
				$dest_arp = $flight[4];
				$dest_apc = $flight[5];
			}
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
          <h1>Fluginformationen zum Flug <?php echo strtoupper($flugnummer);?></h1>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4>Flugnummer: <?php echo strtoupper($airline); echo strtoupper($flugnummer); ?></h4>
            </div>
			<?php echo strtoupper($start_arp); echo strtoupper($start_apc); ?>
			
			<?php echo strtoupper($dest_arp); echo strtoupper($dest_apc); ?>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
</html>