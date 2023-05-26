<?php
include "conn.php";
$response=array();



  //   $sender=$_POST['SenderName'];
    // $receiver=$_POST['RecieverName'];
     //$pass=$_POST['Password'];

     if(isset($_POST['Username'],$_POST['PhoneNum'])){

        $name=$_POST['Username'];
        $phone=$_POST['PhoneNum'];
        $query= "SELECT `Password`  FROM `users` WHERE `Username` = '$name' and `PhoneNum`  = '$phone'";

     $res=$con->query($query);



     if($res->num_rows>0){

        $response['msg']="Password retrived";
        $response['code']=1;     
        $response['password']=array();
        while($row=mysqli_fetch_array($res))
        {
        $contact=array();
      //  $contact['id']=$row['id'];
        $contact['Password']=$row['Password'];
       // $contact['CurrTime']=$row['CurrTime'];
        //$contact['SenderName']=$row['SenderName'];
        //$contact['RecieverName']=$row['RecieverName'];
        //$contact['PhoneNum']=$row['PhoneNum'];
        //$contact['Password']=$row['Password'];
        array_push($response['password'],$contact);

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