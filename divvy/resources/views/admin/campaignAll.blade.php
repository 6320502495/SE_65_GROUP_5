<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="https://static.pingendo.com/bootstrap/bootstrap-4.3.1.css">
    <title>Campaign</title>
    <style>
        .container {
            max-width: none;
            width: 1200px;
        }
       /* .container-wrapper {
            display: flex;
            justify-content: center;
         }*/
       .row1{
            width: 1200px;
            display: flex;
            justify-content: center;
       }
    </style>
</head>
<body>
  <nav class="navbar navbar-expand-md navbar-dark bg-primary">
  <div class="container"> <button class="navbar-toggler navbar-toggler-right border-0" type="button" data-toggle="collapse" data-target="#navbar18">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbar18"> <a class="navbar-brand d-none d-md-block" href="{{route('home')}}">
        <i class="fa d-inline fa-lg fa-circle"></i>
        <b> Divvy</b>
      </a>
      <ul class="navbar-nav mx-auto"></ul>
      <ul class="navbar-nav">
        <!-- login -->
        <li class="nav-item"> <a class="nav-link" href="#">Log in</a> </li>
        <!-- Register -->
        <li class="nav-item"> <a class="nav-link text-white" href="#">Register</a> </li>
      </ul>
    </div>
  </div>
</nav>
<!-- ตัวช่วยการค้นหา-->
<div class="border-top-0 m-0 py-2 pl-5 w-100" style="">
  <div class="container">
    <div class="row">
      <div class="col-md-1 text-right" style="">
        <h4 class="">Filter</h4>
      </div>
      <div class="col-md-3" style="">
        <form class="form-inline" action="{{ route('adminSearchCampaign') }}" method="GET">
          <div class="input-group">
            <input type="text" name="query" placeholder="Search...">
            <div class="input-group-append"><button type="submit" class="btn btn-primary"><i class="fa fa-search"></i></button></div>
          </div>
        </form>
      </div>
      <div class="col-md-3" style="">
        <div class="btn-group">
          <!-- ตัวช่วยการค้นหา หมวดหมู่-->
          <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown"> Catagory</button>
          <div class="dropdown-menu"> <a class="dropdown-item" href="#">Action</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">Separated link</a>
          </div>
        </div>
      </div>
      <div class="col-md-3" style=""></div>
    </div>
  </div>
</div>
<div class="mx-1">
  <div class="container" style="">
  <!-- row-->
  
    <div class="row1 w-100">
      <!-- Ban-->
     <!--  foreach-->
     @foreach ($campaign as $campaigns)
      <div class="border rounded m-1 py-1 shadow border-light col-md-3"><img class="img-fluid d-block w-75 mx-auto" src="https://static.pingendo.com/img-placeholder-1.svg" style="">
        <h5 class="text-center mt-2 w-100">{{$campaigns->Campaign_Name}}</h5>
        <p class="">Account Name : {{$campaigns->Account_Name}}</p>
        <p class="">Campaign Amount : {{$campaigns->Amount}}</p>
        <p class="">Campaign Donate Goal : {{$campaigns->Campaign_Donation_Goals}}</p>
        <p class="">Campaign Start Date : {{$campaigns->Campaign_Starting_Date}}</p>
        <h5 class="text-right">Status : {{$campaigns->Campaign_Status}}</h5>
        <div class="row">
          <div class="col-md-4"><a class="btn btn-primary text-white" onclick="window.location='{{route('adminCampaign')}}?id={{$campaigns->ID}}'" style="">detail&gt;</a></div>
        </div>
      </div>
      @endforeach 
      <!--  endforeach-->
      <!-- Ban-->
    </div>
  </div>
</div>
</body>
{{-- <body>
    <div class="py-12">
      <form action="" method="get">
        <label for="search">Search:</label>
        <input type="text" id="search" name="id">
        <button type="submit">Search</button>
      </form>      
      @foreach ($campaign as $campaigns)
        <div class="container">
            <div class="row">
              <div class="col-sm">
                Campaign Name : {{$campaigns->Campaign_Name}}
              </div>
              <div class="col-sm">
                Account Name : {{$campaigns->Account_Name}}
              </div>
              <div class="col-sm">
                Campaign Amount : {{$campaigns->Amount}}
              </div>
              <div class="col-sm">
                Campaign Donate Goal : {{$campaigns->Campaign_Donation_Goals}}
              </div>
              <div class="col-sm">
                Campaign Start Date : {{$campaigns->Campaign_Starting_Date}}
              </div>
            </div>
        </div>
        <button style="background-color: red; color: white;" onclick="window.location='{{route('adminCampaignCancel')}}?name={{$campaigns->Campaign_Name}}'">Cancel</button>           
      @endforeach
    </div>
</body> --}}
</html>