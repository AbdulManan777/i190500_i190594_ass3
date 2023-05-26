<?php


include "conn.php";
$response=array();

if(isset($_POST['Message'])){

     $message=$_POST['Message'];
    // $receiver=$_POST['RecieverName'];
     //$pass=$_POST['Password'];

     $query= "SELECT MID FROM `messages` WHERE `Message` = '$message' ";

     $res=$con->query($query);



     if($res->num_rows>0){

        $response['msg']="Message ID retrived";
        $response['code']=1;     
        $response['mid']=array();
        while($row=mysqli_fetch_array($res))
        {
        $contact=array();
      //  $contact['id']=$row['id'];
        $contact['MID']=$row['MID'];
        //$contact['PhoneNum']=$row['PhoneNum'];
        //$contact['Password']=$row['Password'];
        array_push($response['mid'],$contact);

    }


    }
    
    else{
    
       echo "Failure";
    }
    
}
    
    else{
        $res["msg"]="incomplete Request";
        $res["resultcode"]=-1;
        $res["id"]="N/A";
    
    
    }
    
    
    echo json_encode($response);


?>