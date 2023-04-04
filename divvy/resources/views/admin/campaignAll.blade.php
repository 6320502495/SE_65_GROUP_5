<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
        type="text/css">
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
        .row1 {
            width: 1200px;
            display: flex;
            justify-content: center;
        }
    </style>
</head>

<body>
    <nav class="navbar navbar-expand-md navbar-dark bg-primary">
        <div class="container"> <button class="navbar-toggler navbar-toggler-right border-0" type="button"
                data-toggle="collapse" data-target="#navbar18">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbar18"> <a class="navbar-brand d-none d-md-block"
                    href="{{ route('home') }}">
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
                            <div class="input-group-append"><button type="submit" class="btn btn-primary"><i
                                        class="fa fa-search"></i></button></div>
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

            <div class="col">
                <div class="row ">
                    <!-- Ban-->
                    <!--  foreach-->
                    @foreach ($campaigns as $campaign)
                        <div class="border rounded m-1 py-1 shadow border-light col-md-3"><img
                                class="img-fluid d-block w-auto h-auto mx-auto"
                                src="{{asset($campaign->campaign_Image)}}" style="">
                            <h5 class="text-center mt-2 w-100">{{ $campaign->campaign_name }}</h5>
                            <p class="">ชื่อผู้เปิดแคมเปญ : {{ $campaign->name }}</p>
                            <p class="">ยอดบริจาค : {{ $campaign->current_money ?? 0 }}</p>
                            <p class="">เป้าหมายเงินโดเนท : {{ $campaign->goals }}</p>
                            <p class="">ประเภทแคมเปญ : {{ $campaign->campaign_type }}</p>
                            <h5 class="text-right">สถานะ : {{ $campaign->status }}</h5>
                            <div class="row">
                                <div class="col-md-4"><a class="btn btn-primary text-white"
                                        onclick="window.location='{{ route('adminCampaign') }}?id={{ $campaign->id }}'"
                                        style="">detail&gt;</a></div>
                            </div>
                        </div>
                    @endforeach
                    <!--  endforeach-->
                    <!-- Ban-->
                </div>
            </div>
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
