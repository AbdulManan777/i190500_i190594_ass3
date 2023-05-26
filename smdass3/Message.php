<?php


include "conn.php";
$res=array();
if(isset($_POST['Message'],$_POST['SenderName'],$_POST['RecieverName'],$_POST['CurrTime']))
{
    $message=$_POST['Message'];
    $senderName=$_POST['SenderName'];
    $reciverName=$_POST['RecieverName'];
    $time1=$_POST['CurrTime'];
    $query="INSERT INTO `messages`
    (`Message`,`SenderName`,`RecieverName`,`CurrTime`) 
    VALUES ('$message','$senderName','$reciverName','$time1')";
    $r=mysqli_query($con,$query);

    if($r)
    {
        $res["msg"]="Message Inserted";
        $res["code"]=1;
        $res["id"]=mysqli_insert_id($con);
        
    }
    else{
        $res["msg"]="Failed to Insert Message";
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