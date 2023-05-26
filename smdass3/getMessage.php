<?php
include "conn.php";
$response=array();



  //   $sender=$_POST['SenderName'];
    // $receiver=$_POST['RecieverName'];
     //$pass=$_POST['Password'];

     $query= "SELECT `Message` ,`CurrTime` FROM `messages` ";

     $res=$con->query($query);



     if($res->num_rows>0){

        $response['msg']="Message retrived";
        $response['code']=1;     
        $response['messages']=array();
        while($row=mysqli_fetch_array($res))
        {
        $contact=array();
      //  $contact['id']=$row['id'];
        $contact['Message']=$row['Message'];
        //$contact['SenderName']=$row['SenderName'];
        //$contact['RecieverName']=$row['RecieverName'];
        //$contact['PhoneNum']=$row['PhoneNum'];
        //$contact['Password']=$row['Password'];
        array_push($response['messages'],$contact);

    }


    }
    
    else{
    
       echo "Failure";
    }
    

    
    
    echo json_encode($response);

?>