<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\View\View;

class CampaignController extends Controller
{
    public function showAll() {
        $campaign=DB::table('campaign')->get();
        return view('campaign.campaignAll', compact('campaign'));
    }
    public function show(string $id) : View
    {
        $campaign=DB::table('campaign')->where('campaign.Campaign_ID',$id)->get();
        //print($accounts);
        return view('campaign.campaign', compact('campaign'));
    }
}
