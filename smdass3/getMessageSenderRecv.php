<?php
include "conn.php";
$response=array();



  //   $sender=$_POST['SenderName'];
    // $receiver=$_POST['RecieverName'];
     //$pass=$_POST['Password'];

     if(isset($_POST['SenderName'],$_POST['RecieverName'])){

        $name=$_POST['SenderName'];
        $recvname=$_POST['RecieverName'];
        $query= "SELECT `Message` ,`CurrTime` FROM `messages` WHERE (`SenderName` = '$name' and `RecieverName`  = '$recvname') or  (`SenderName` = '$recvname' and `RecieverName`  = '$name') ";

     $res=$con->query($query);



     if($res->num_rows>0){

        $response['msg']="Message retrived";
        $response['code']=1;     
        $response['messages']=array();
        while($row=mysqli_fetch_array($res))
        {
        $contact=array();
      //  $contact['id']=$row['id'];
      if (!empty($row['Message'])){
         $contact['Message']=$row['Message'];
         $contact['CurrTime']=$row['CurrTime'];
         array_push($response['messages'],$contact);

      }

       
        //$contact['SenderName']=$row['SenderName'];
        //$contact['RecieverName']=$row['RecieverName'];
        //$contact['PhoneNum']=$row['PhoneNum'];
        //$contact['Password']=$row['Password'];
        

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