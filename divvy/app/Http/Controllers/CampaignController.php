<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\View\View;
use App\Models\Campaign;

class CampaignController extends Controller
{
    public function showAll() {
        //$campaign = Campaign::all();
        $campaign=DB::table('campaign')->get();
        return view('campaign.campaignAll', compact('campaign'));
    }

    public function show(string $id) : View
    {
        $campaign=Campaign::find($id);
        //print($accounts);
        return view('campaign.campaign', compact('campaign'));
    }

    public function create() {
        //$campaign = Campaign::all();
        return view('campaign.campaignCreate');
    }

    public function createIndividual() {
        //$campaign = Campaign::all();
        return view('campaign.campaignCreateIndividual');
    }

    public function createOrganization() {
        //$campaign = Campaign::all();
        return view('campaign.campaignCreateOrganization');
    }

    public function saveIndividual(Request $request) {
        $campaign = new Campaign;
        $campaign->Account_ID = 1;
        $campaign->Campaign_Name = $request->Campaign_Name;
        $campaign->Campaign_Details = $request->Campaign_Details;
        $campaign->Campaign_Tel = $request->Campaign_Tel;
        $campaign->Campaign_Bank_ID = $request->Campaign_Bank_ID;
        $campaign->Campaign_Bank_Type = $request->Campaign_Bank_Type;
        $campaign->Campaign_Category = $request->Campaign_Category;
        $campaign->Campaign_Donation_Goals = $request->Campaign_Donation_Goals;
        $campaign->Campaign_Type = 'Individual';
        $campaign->save();
        $campaign=DB::table('campaign')->get();
        return view('campaign.campaignAll', compact('campaign'));
    }

    public function saveOrganization(Request $request) {
        $campaign = new Campaign;
        $campaign->Account_ID = 1;
        $campaign->Campaign_Name = $request->Campaign_Name;
        $campaign->Campaign_Details = $request->Campaign_Details;
        $campaign->Campaign_Tel = $request->Campaign_Tel;
        $campaign->Campaign_Bank_ID = $request->Campaign_Bank_ID;
        $campaign->Campaign_Bank_Type = $request->Campaign_Bank_Type;
        $campaign->Campaign_Category = $request->Campaign_Category;
        $campaign->Campaign_Donation_Goals = $request->Campaign_Donation_Goals;
        $campaign->Campaign_Institute_Name = $request->Campaign_Institute_Name;
        $campaign->Campaign_Institute_Paper = $request->Campaign_Institute_Paper;
        $campaign->Campaign_Institute_Tel = $request->Campaign_Institute_Tel;
        $campaign->Campaign_Type = 'Organization';
        $campaign->save();
        $campaign=DB::table('campaign')->get();
        return view('campaign.campaignAll', compact('campaign'));
    }

    public function edit(string $id){
        $campaign=Campaign::find($id);
        return view('campaign.campaignEdit', compact('campaign'));
    }

    public function update(Request $request, $id){
        $update = Campaign::find($id)->update([
            'Account_ID' => 1,
            'Campaign_Name' => $request->Campaign_Name,
            'Campaign_Details' => $request->Campaign_Details,
            'Campaign_Tel' => $request->Campaign_Tel,
            'Campaign_Bank_ID' => $request->Campaign_Bank_ID,
            'Campaign_Bank_Type' => $request->Campaign_Bank_Type,
            'Campaign_Category' => $request->Campaign_Category,
            'Campaign_Type' => $request->Campaign_Type,
        ]);
        return redirect()->route('campaignAll')->with('success', "Update Complete");
    }
}
