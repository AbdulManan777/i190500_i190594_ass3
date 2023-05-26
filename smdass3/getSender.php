<?php


include "conn.php";
$response=array();

if(isset($_POST['MID'])){

     $mid=$_POST['MID'];
    // $receiver=$_POST['RecieverName'];
     //$pass=$_POST['Password'];

     $query= "SELECT SenderName FROM `messages` WHERE `MID` = '$mid' ";

     $res=$con->query($query);



     if($res->num_rows>0){

        $response['msg']="Message ID retrived";
        $response['code']=1;     
        $response['sendername']=array();
        while($row=mysqli_fetch_array($res))
        {
        $contact=array();
      //  $contact['id']=$row['id'];
        $contact['SenderName']=$row['SenderName'];
        //$contact['PhoneNum']=$row['PhoneNum'];
        //$contact['Password']=$row['Password'];
        array_push($response['sendername'],$contact);

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