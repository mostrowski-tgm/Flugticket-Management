<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		
		<title>
            FlugnummerSearcher
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
								Flugnummer Searcher
							</h1>							
						</div>
						<div class="panel-body">
							<form action="Web_FlugData.php" method="post">
								<div class="row">
									<div class="col-md-6 offset-md-3">
										<input type="text" placeholder=" Insert Flightnummer" name="flugnr">
									</div>
								</div>
								<br>
								<div class="row">
									<div class="col-md-6 offset-md-3">
										<button type="submit">
											Flugnummer suchen
										</button>
									</div>
								</div> 
							</form>
						</div>
					</div>
				</div>	
			</div>
		</div>
		<div class="col-md-4">
		</div>
		
    </body>
</html>