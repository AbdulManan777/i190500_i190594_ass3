<?php
include "conn.php";

if(isset($_POST['image'],$_POST['Username'])){


	
	$img=$_POST['image'];
	$usern=$_POST['Username'];

	$filename="IMG".rand().time().".jpg";
    file_put_contents("images/".$filename,base64_decode($img));

$qry="INSERT INTO `pictures` (`image`,`Username`)
   VALUES ('$filename','$usern')";

$res=mysqli_query($con,$qry);

if($res==true)
echo "Dp Uploaded Successfully";
else
echo "Could not upload File";
}

else{

	$res["msg"]="incomplete Request";
    $res["resultcode"]=-1;
    $res["id"]="N/A";

}
      
?>
