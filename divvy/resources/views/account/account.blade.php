<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>หน้า Account</title>
</head>
<body>
  <div class="py-12">
    <div class="container">
        <div class="row">
          <div class="col-sm">
            Account Name : {{$accounts->Account_Name}}
          </div>
          <div class="col-sm">
            Account Firstname : {{$accounts->Account_Firstname}}
          </div>
          <div class="col-sm">
            Account Surname : {{$accounts->Account_Surname}}
          </div>
          <div class="col-sm">
            Account Birthday : {{$accounts->Account_Birthday}}
          </div>
          <div class="col-sm">
            Account Username : {{$accounts->Account_Username}}
          </div>
          <div class="col-sm">
            Account Password : {{$accounts->Account_Password}}
          </div>
        </div>
    </div>           
</div>
</body>
</html>
