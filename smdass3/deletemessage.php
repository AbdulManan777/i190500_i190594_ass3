<?php
include "conn.php";
$response=array();
if(isset($_POST["Message"]))
{
    $messa=$_POST["Message"];
    $query="DELETE FROM `messages` WHERE Message = '$messa' ";
    $res=mysqli_query($con,$query);
    if($res)
    {
        $response['msg']="Message Deleted";
       // $response['id']=$id;
        $response['code']=1;
    }
    else{

        $response['msg']="Cannot Delete";
        $response['id']="N/A";
        $response['code']=-1;
    }


}
else{
    $response['msg']="incomplete request";
    $response['id']="N/A";
    $response['code']=-1;
}

echo json_encode($response);
?>