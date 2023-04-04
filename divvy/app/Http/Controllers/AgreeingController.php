<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Http\Requests;
use App\Http\Controllers\Controller;

class AgreeingController
{
    /*
    public static function createAgreeing($campaignID)
    {
        $donators = DB::table('donation_record')
            ->select('account_ID')
            ->distinct()
            ->where('Campaign_ID', '=', $campaignID)
            ->get();  
        foreach ($donators as $donator) {
            $donatedAmount = DB::table('donation_record')
                ->where('Campaign_ID', '=', $campaignID)
                ->where('Account_ID', '=', $donator->account_ID)
                ->sum('Amount');
            DB::insert('insert into agreeing (campaign_ID, account_ID, total_donation) values(?, ?, ?)', [$campaignID, $donatedUser->account_ID, $donatedAmount]);
        }
    }
    */

    public static function getAgreeingNeededCampaigns($accountID) {
        $campaigns=DB::table('agreeing')
            ->join('campaign', 'campaign.ID', '=', 'agreeing.Campaign_ID')
            ->select(array('campaign.ID', 'campaign.Campaign_Name', 'agreeing.total_donation'))
            ->groupBy('campaign.Campaign_Name', 'agreeing.total_donation', 'campaign.ID')
            ->where('agreeing.account_ID', '=', $accountID)
            ->where('status', '=', 'None')
            ->get();
        return $campaigns;
    }

    public static function agreeCampaign($accountID, $campaignID) {
        DB::table('agreeing')
        ->where('account_ID', '=', $accountID)
        ->where('campaign_ID', '=', $campaignID)
        ->update(array('status' => 'Agree'));
        AgreeingController::checkCampaign($campaignID);
    }

    public static function rejectCampaign($accountID, $campaignID) {
        DB::table('agreeing')
        ->where('account_ID', '=', $accountID)
        ->where('campaign_ID', '=', $campaignID)
        ->update(array('status' => 'Reject'));
        AgreeingController::checkCampaign($campaignID);
    }

    public static function checkCampaign($campaignID) {
        $total_donators = DB::table('agreeing')
            ->count();
        $agreeing_donators = DB::table('agreeing')
            ->where('status', '=', 'Agree')
            ->count();
        $rejecting_donators = DB::table('agreeing')
            ->where('status', '=', 'Reject')
            ->count();
        if ($agreeing_donators / $total_donators >= 0.2) {
            DB::table('campaign')
                ->where('ID', '=', $campaignID)
                ->update(array('Campaign_Status' => 'Agree'));
        } else if ($rejecting_donators / $total_donators > 0.8) {
            DB::table('campaign')
                ->where('ID', '=', $campaignID)
                ->update(array('Campaign_Status' => 'Reject'));
        }
    }
}

?>
