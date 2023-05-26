<?php
$con=mysqli_connect("localhost","root","") or 
    die("cannot connect to the DB").mysqli_error();
mysqli_select_db($con,"chat_application");



?>