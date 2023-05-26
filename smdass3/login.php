<?php

if(isset($_POST['Username'],$_POST['Password'])){
    require_once "conn.php";

    $name=$_POST['Username'];
    $pass=$_POST['Password'];

    $query= "SELECT * FROM `users` WHERE `Username` = '$name' and `Password`  = '$pass' ";
    $res=$con->query($query);

    
    if($res->num_rows>0){

        echo "Success";


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
    
    
    //echo json_encode($response);



    


?>