<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		
		<title>
            FlugManager
        </title>
		
		<link href="css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
	<br>
		<div class="container">
			<div class="row">
				<div class="col-md-4">
				</div>
				<div class="col-md-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h1>
								Datenbank Connector
							</h1>							
						</div>
						<div class="panel-body">
							<form action="Web_FlugnummerSearcher.php" method="post">
								<div class="row">
									<div class="col-md-6 offset-md-3">
										<input type="text" placeholder=" Insert Username" name="username">
									</div>
								</div>
								<br>
								<div class="row">
									<div class="col-md-6 offset-md-3">
										<input type="password" placeholder=" Insert Password" name="password">
									</div>
								</div>
								<br>
								<div class="row">
									<div class="col-md-6 offset-md-3">
										<input type="text" placeholder=" Insert IP-Adresse (127.0.0.1)" name="adresse">
									</div>
								</div>
								<br>
								<div class="row">
									<div class="col-md-6 offset-md-3">
										<input type="text" placeholder=" Insert Databanktyp (mysqli)" name="data">
									</div>
								</div>
								<br>
								<div class="row">
									<div class="col-md-6 offset-md-3">
										<input type="text" placeholder=" Insert Port (3306)" name="port">
									</div>
								</div>
								<br>
								<button type="submit" id="trigger">
									Zu Datenbank verbinden
								</button>
							</form>
						</div>
					</div>
				</div>	
			</div>
		</div>		
    </body>
</html>