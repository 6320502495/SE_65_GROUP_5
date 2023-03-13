<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class CampaignController extends Controller
{
    public function index() {
        $campaign=DB::table('campaign')->first();
        return view('campaign.campaign', compact('campaign'));
    }
}
