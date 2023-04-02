<?php

namespace App\Http\Controllers;

use Illuminate\Database\Eloquent\Builder;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\View\View;
use App\Models\Account;
use App\Models\Campaign;

class AdminController extends Controller
{
    //
    public function showAllAccount() {
        $accounts=DB::table('account')
        ->join('deposit_records', 'account.ID', '=', 'deposit_records.Account_ID')
        ->join('report', 'account.ID', '=', 'report.Account_ID')
        ->select(array('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', DB::raw('SUM(deposit_records.Deposit_Amount) as Amount'), 'report.Report_Reason'))
        ->groupBy('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', 'report.Report_Reason')
        ->where('account.Account_Status', '=', 1)
        ->get();
        $type=1;
        return view('admin.accountAll', compact('accounts','type'));
    }

    public function showAccount(Request $request) {
        $account=DB::table('account')
        ->join('deposit_records', 'account.ID', '=', 'deposit_records.Account_ID')
        ->join('report', 'account.ID', '=', 'report.Account_ID')
        ->select(array('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', DB::raw('SUM(deposit_records.Deposit_Amount) as Amount'), 'report.Report_Reason'))
        ->groupBy('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', 'report.Report_Reason')
        ->where('account.ID', $request->input('id'))
        ->get();
        return view('admin.account', compact('account'));
    }

    public function searchAccount(Request $request) {
        $accounts=DB::table('account')
        ->join('deposit_records', 'account.ID', '=', 'deposit_records.Account_ID')
        ->join('report', 'account.ID', '=', 'report.Account_ID')
        ->select(array('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', DB::raw('SUM(deposit_records.Deposit_Amount) as Amount'), 'report.Report_Reason'))
        ->groupBy('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', 'report.Report_Reason')
        ->where('account.Account_Status', '=', 1)
        ->where('account.Account_Name', 'like', '%'.$request->input('query').'%')
        ->get();
        $type=1;
        return view('admin.accountAll', compact('accounts','type'));
    }

    public function showBanAccount()
    {
        $accounts=DB::table('account')
        ->join('deposit_records', 'account.ID', '=', 'deposit_records.Account_ID')
        ->join('report', 'account.ID', '=', 'report.Account_ID')
        ->select(array('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', DB::raw('SUM(deposit_records.Deposit_Amount) as Amount'), 'report.Report_Reason'))
        ->groupBy('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', 'report.Report_Reason')
        ->where('account.Account_Status', '=', 2)
        ->get();
        $type=2;
        //print($accounts);
        return view('admin.accountAll', compact('accounts','type'));
    }

    public function showBan(Request $request) {
        $account=DB::table('account')
        ->join('deposit_records', 'account.ID', '=', 'deposit_records.Account_ID')
        ->join('report', 'account.ID', '=', 'report.Account_ID')
        ->select(array('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', DB::raw('SUM(deposit_records.Deposit_Amount) as Amount'), 'report.Report_Reason'))
        ->groupBy('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', 'report.Report_Reason')
        ->where('account.ID', $request->input('id'))
        ->get();
        return view('admin.account', compact('account'));
    }

    public function searchBanAccount(Request $request) {
        $accounts=DB::table('account')
        ->join('deposit_records', 'account.ID', '=', 'deposit_records.Account_ID')
        ->join('report', 'account.ID', '=', 'report.Account_ID')
        ->select(array('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', DB::raw('SUM(deposit_records.Deposit_Amount) as Amount'), 'report.Report_Reason'))
        ->groupBy('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', 'report.Report_Reason')
        ->where('account.Account_Status', '=', 2)
        ->where('account.Account_Name', 'like', '%'.$request->input('query').'%')
        ->get();
        $type=2;
        return view('admin.accountAll', compact('accounts','type'));
    }

    public function updateAccount(Request $request) {
        Account::where('ID', $request->input('id'))->update(['Account_Status' => 'Ban']);
        $accounts=DB::table('account')
        ->join('deposit_records', 'account.ID', '=', 'deposit_records.Account_ID')
        ->join('report', 'account.ID', '=', 'report.Account_ID')
        ->select(array('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', DB::raw('SUM(deposit_records.Deposit_Amount) as Amount'), 'report.Report_Reason'))
        ->groupBy('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', 'report.Report_Reason')
        ->where('account.Account_Status', '=', 1)
        ->get();
        $type=1;
        return view('admin.accountAll', compact('accounts','type'));
    }

    public function updateBanAccount(Request $request)
    {
        Account::where('ID', $request->input('id'))->update(['Account_Status' => 'UnBan']);
        $accounts=DB::table('account')
        ->join('deposit_records', 'account.ID', '=', 'deposit_records.Account_ID')
        ->join('report', 'account.ID', '=', 'report.Account_ID')
        ->select(array('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', DB::raw('SUM(deposit_records.Deposit_Amount) as Amount'), 'report.Report_Reason'))
        ->groupBy('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Username', 'account.Account_Password', 'account.Account_Status', 'report.Report_Reason')
        ->where('account.Account_Status', '=', 2)
        ->get();
        $type=2;
        //print($accounts);
        return view('admin.accountAll', compact('accounts','type'));
    }

    public function showAllCampaign() {
        //$campaign = Campaign::all();
        $campaign=DB::table('campaign')
        ->join('account', 'account.ID', '=', 'campaign.Account_ID')
        ->join('donation_record', 'donation_record.Campaign_ID', '=', 'campaign.ID')
        ->select(array('campaign.ID','campaign.Campaign_Name', 'account.Account_Name', DB::raw('SUM(donation_record.Amount) as Amount'), 'campaign.Campaign_Donation_Goals', 'campaign.Campaign_Starting_Date', 'campaign.Campaign_Status'))
        ->groupBy('campaign.ID','campaign.Campaign_Name', 'account.Account_Name', 'campaign.Campaign_Donation_Goals', 'campaign.Campaign_Starting_Date', 'campaign.Campaign_Status')
        ->get();
        return view('admin.campaignAll', compact('campaign'));
    }

    public function showCampaign(Request $request) {
        $campaign=DB::table('campaign')
        ->join('account', 'account.ID', '=', 'campaign.Account_ID')
        ->join('donation_record', 'donation_record.Campaign_ID', '=', 'campaign.ID')
        ->where('campaign.ID', $request->input('id'))
        ->select(array('campaign.ID', 'campaign.Campaign_Name', 'campaign.Campaign_Details', 'account.Account_Name', DB::raw('SUM(donation_record.Amount) as Amount'), 'campaign.Campaign_Donation_Goals', 'campaign.Campaign_Starting_Date', 'campaign.Campaign_Status'))
        ->groupBy('campaign.ID', 'campaign.Campaign_Name', 'campaign.Campaign_Details', 'account.Account_Name', 'campaign.Campaign_Donation_Goals', 'campaign.Campaign_Starting_Date', 'campaign.Campaign_Status')
        ->get();
        return view('admin.campaign', compact('campaign'));
    }

    public function searchCampaign(Request $request) {
        $campaign=DB::table('campaign')
        ->join('account', 'account.ID', '=', 'campaign.Account_ID')
        ->join('donation_record', 'donation_record.Campaign_ID', '=', 'campaign.ID')
        ->where('campaign.Campaign_Name', 'like', '%'.$request->input('query').'%')
        ->orwhere('campaign.Campaign_Details', 'like', '%'.$request->input('query').'%')
        ->orwhere('account.Account_Name', 'like', '%'.$request->input('query').'%')
        ->orwhere('campaign.Campaign_Donation_Goals', 'like', '%'.$request->input('query').'%')
        ->orwhere('campaign.Campaign_Starting_Date', 'like', '%'.$request->input('query').'%')
        ->orwhere('campaign.Campaign_Status', 'like', '%'.$request->input('query').'%')
        ->select(array('campaign.ID', 'campaign.Campaign_Name', 'campaign.Campaign_Details', 'account.Account_Name', DB::raw('SUM(donation_record.Amount) as Amount'), 'campaign.Campaign_Donation_Goals', 'campaign.Campaign_Starting_Date', 'campaign.Campaign_Status'))
        ->groupBy('campaign.ID', 'campaign.Campaign_Name', 'campaign.Campaign_Details', 'account.Account_Name', 'campaign.Campaign_Donation_Goals', 'campaign.Campaign_Starting_Date', 'campaign.Campaign_Status')
        ->get();
        return view('admin.campaignAll', compact('campaign'));
    }

    public function cancelCampaign(Request $request) {
        // print(DB::table('campaign')->where('ID', $request->input('id'))->get());
        DB::table('campaign')->where('ID', $request->input('id'))->update(['Campaign_Status' => 'Cancel']);
        $campaign=DB::table('campaign')
        ->join('account', 'account.ID', '=', 'campaign.Account_ID')
        ->join('donation_record', 'donation_record.Campaign_ID', '=', 'campaign.ID')
        ->select(array('campaign.ID','campaign.Campaign_Name', 'account.Account_Name', DB::raw('SUM(donation_record.Amount) as Amount'), 'campaign.Campaign_Donation_Goals', 'campaign.Campaign_Starting_Date', 'campaign.Campaign_Status'))
        ->groupBy('campaign.ID','campaign.Campaign_Name', 'account.Account_Name', 'campaign.Campaign_Donation_Goals', 'campaign.Campaign_Starting_Date', 'campaign.Campaign_Status')
        ->get();
        return view('admin.campaignAll', compact('campaign'));
    }

    public function closeCampaign(Request $request) {
        // print(DB::table('campaign')->where('ID', $request->input('id'))->get());
        DB::table('campaign')->where('ID', $request->input('id'))->update(['Campaign_Status' => 'Close']);
        $campaign=DB::table('campaign')
        ->join('account', 'account.ID', '=', 'campaign.Account_ID')
        ->join('donation_record', 'donation_record.Campaign_ID', '=', 'campaign.ID')
        ->select(array('campaign.ID','campaign.Campaign_Name', 'account.Account_Name', DB::raw('SUM(donation_record.Amount) as Amount'), 'campaign.Campaign_Donation_Goals', 'campaign.Campaign_Starting_Date', 'campaign.Campaign_Status'))
        ->groupBy('campaign.ID','campaign.Campaign_Name', 'account.Account_Name', 'campaign.Campaign_Donation_Goals', 'campaign.Campaign_Starting_Date', 'campaign.Campaign_Status')
        ->get();
        return view('admin.campaignAll', compact('campaign'));
    }
    
    public static function showReportedUsers()
    {
        $reportUsers=DB::table('account')
        ->join('report', 'account.ID', '=', 'report.Account_ID')
        ->select(array('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Status', 'report.Report_Reason', 'report.sending'))
        ->groupBy('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Status', 'report.Report_Reason', 'report.sending')
        ->where('account.Account_Status', '=', 'unban')
        ->where('report.Sending', '=', 'Admin')
        ->get();
        return $reportUsers;
    }
}
