<?php
include "conn.php";

if(isset($_POST['ImageMessage'],$_POST['SenderName'],$_POST['RecieverName'],$_POST['CurrTime'])){


	
	$img=$_POST['ImageMessage'];
	$sender=$_POST['SenderName'];
    $recv=$_POST['RecieverName'];
    $Curr_Time=$_POST['CurrTime'];
	$filename="IMG".rand().time().".jpg";
    file_put_contents("ImageMessage/".$filename,base64_decode($img));

$qry="INSERT INTO `messages` (`SenderName`,`RecieverName`,`CurrTime`,`ImageMessage`)
   VALUES ('$sender','$recv','$Curr_Time','$filename')";

$res=mysqli_query($con,$qry);

if($res==true)
echo "Image Message Send Successfully";
else
echo "Could not Send Message";
}

else{

	$res["msg"]="incomplete Request";
    $res["resultcode"]=-1;
    $res["id"]="N/A";

}
      
?>
