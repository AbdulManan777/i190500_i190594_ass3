<?php
include "conn.php";
$response=array();

//$response['data']=array();

if(isset($_POST['SenderName'],$_POST['RecieverName'])){

    $sender=$_POST['SenderName'];
    $recv=$_POST['RecieverName'];
    

    $query= "SELECT * FROM `messages`   WHERE (`ImageMessage` IS NOT NULL and `SenderName` = '$sender' and `RecieverName`  = '$recv') or  (`ImageMessage` IS NOT NULL and `SenderName` = '$recv' and `RecieverName`  = '$sender') ";

$res=mysqli_query($con,$query);


if($res)
{
    
    $response['msg']="Img retrived";
    $response['code']=1;     
    $response['messages']=array();
    while($row=mysqli_fetch_array($res))
    {
        $contact=array();
        
      //  $contact['id']=$row['id'];
      if (!empty($row['ImageMessage']))
    {
        $contact['image']=$row['ImageMessage'];
        $contact['CurrTime']=$row['CurrTime'];
        array_push($response['messages'],$contact);

        
    }
        
       // $contact['Username']=$row['Username'];
        //$contact['Password']=$row['Password'];
       

    }







 
    }
    
    else{

        $response['msg']="Cannot retrive data";
        $response['code']=-1;
    }
}

else{
    $result["msg"]="incomplete Request";
    $result["resultcode"]=-1;
    $result["id"]="N/A";


}
    
    echo json_encode($response);

?>