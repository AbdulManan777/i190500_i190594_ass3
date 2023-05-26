<?php


include "conn.php";
$response=array();

if(isset($_POST['Username'],$_POST['Password'])){

     $username=$_POST['Username'];
     $password=$_POST['Password'];
    // $receiver=$_POST['RecieverName'];
     //$pass=$_POST['Password'];

     $query= "SELECT PhoneNum FROM `users` WHERE `Username` = '$username' and `Password` = '$password' ";

     $res=$con->query($query);



     if($res->num_rows>0){

        $response['msg']="Phone Num retrived";
        $response['code']=1;     
        $response['phonenumber']=array();
        while($row=mysqli_fetch_array($res))
        {
        $contact=array();
      //  $contact['id']=$row['id'];
        $contact['PhoneNumber']=$row['PhoneNum'];
        //$contact['PhoneNum']=$row['PhoneNum'];
        //$contact['Password']=$row['Password'];
        array_push($response['phonenumber'],$contact);

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