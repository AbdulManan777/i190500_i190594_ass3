<?php
include "conn.php";

if(isset($_POST['Message'],$_POST['Message2'])){

    $mess=$_POST['Message'];
    $mess2=$_POST['Message2'];

    $query= "UPDATE messages SET  Message = '$mess2' WHERE Message = '$mess' ";

    $result = mysqli_query($con,$query);
    
     
    if($result){
        echo "Data Updated";
       
    }
    else{
        echo "Failed";
    }







}

else{
    $result["msg"]="incomplete Request";
    $result["resultcode"]=-1;
    $result["id"]="N/A";


}

?>

