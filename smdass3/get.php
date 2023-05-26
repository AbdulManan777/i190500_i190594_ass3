<?php
include "conn.php";
$response=array();

if(isset($_POST['Username'],$_POST['Password'])){

     $name=$_POST['Username'];
     $pass=$_POST['Password'];

     $query= "SELECT Username FROM `users` Where `Password`!=".$pass;
     $res=mysqli_query($con,$query);



if($res)
{
    
    $response['msg']="Data retrived";
    $response['code']=1;     
    $response['users']=array();
    while($row=mysqli_fetch_array($res))
    {
        $contact=array();
      //  $contact['id']=$row['id'];
        $contact['Username']=$row['Username'];
        //$contact['PhoneNum']=$row['PhoneNum'];
        //$contact['Password']=$row['Password'];
        array_push($response['users'],$contact);

    }

}

else{

    $response['msg']="Cannot retrive data";
    $response['code']=-1;
}

}

else{
    $res["msg"]="incomplete Request";
    $res["resultcode"]=-1;
    $res["id"]="N/A";


}


echo json_encode($response);

?>