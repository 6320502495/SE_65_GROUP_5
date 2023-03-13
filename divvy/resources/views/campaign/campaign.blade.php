<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>หน้า Campaign</title>
</head>
<body>
    <div class="py-12">
        <div class="container">
            <div class="row">
              <div class="col-sm">
                Campaign Name : {{$campaign->Campaign_Name}}
              </div>
              <div class="col-sm">
                Campaign Details : {{$campaign->Campaign_Details}}
              </div>
              <div class="col-sm">
                Campaign Tel : {{$campaign->Campaign_Tel}}
              </div>
              <div class="col-sm">
                Campaign Bank ID : {{$campaign->Campaign_Bank_ID}}
              </div>
              <div class="col-sm">
                Campaign Bank Type : {{$campaign->Campaign_Bank_Type}}
              </div>
              <div class="col-sm">
                Campaign Category : {{$campaign->Campaign_Category}}
              </div>
              <div class="col-sm">
                Campaign Type : {{$campaign->Campaign_Type}}
              </div>
            </div>
        </div>           
    </div>
</body>
</html>