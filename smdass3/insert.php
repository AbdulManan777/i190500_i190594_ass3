<?php
#   http://localhost/smdass3/insert.php
include "conn.php";
$res=array();
if(isset($_POST['Username'],$_POST['Password'],$_POST['PhoneNum']))
{
    $name=$_POST['Username'];
    $phno=$_POST['PhoneNum'];
    $password=$_POST['Password'];
    $query="INSERT INTO `users`
    (`Username`, `PhoneNum`, `Password`) 
    VALUES ('$name','$phno','$password')";
    $r=mysqli_query($con,$query);

    if($r)
    {
        $res["msg"]="User Inserted";
        $res["code"]=1;
        $res["id"]=mysqli_insert_id($con);
    }
    else{
        $res["msg"]="Failed to Insert";
        $res["resultcode"]=-1;
        $res["id"]="N/A";

    }
}
else{
    $res["msg"]="incomplete Request";
    $res["resultcode"]=-1;
    $res["id"]="N/A";


}

$x=json_encode($res);
echo $x;
?>