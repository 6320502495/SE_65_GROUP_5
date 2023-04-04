<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\View\View;
use App\Models\Campaign;
use App\Models\DonationRecord;
use Illuminate\Support\Facades\Auth;


class AnonymousController extends Controller
{
    function home(){
        $campaign=DB::table('campaign')->get();

       $donation_rec=DB::table('donation_record')->get();
        
        return view('welcome', compact('campaign','donation_rec'));
    }

    public function show(int $Campaign_ID) : View
    {
        $campaign=Campaign::find($Campaign_ID);
        
        $total_donation=$total_donations = DB::table('donation_record')
        ->where('Campaign_ID', $Campaign_ID)
        ->sum('Amount');
        
        return view('anonymousCampaign', compact('campaign','total_donation'));
    }
   
    
    
    
}


?>
