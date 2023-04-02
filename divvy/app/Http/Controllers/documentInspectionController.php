<?php

namespace App\Http\Controllers;

use Illuminate\Database\Eloquent\Builder;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\View\View;

class DocumentInspectionController
{
    public static function showAll()
    {
        $reportUsers=DB::table('account')
        ->join('report', 'account.ID', '=', 'report.Account_ID')
        ->select(array('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Status', 'report.Report_Reason', 'report.sending'))
        ->groupBy('account.ID', 'account.Account_Firstname', 'account.Account_Surname', 'account.Account_Name', 'account.Account_Birthday', 'account.Account_Status', 'report.Report_Reason', 'report.sending')
        ->where('account.Account_Status', '=', 'unban')
        ->where('report.Sending', '=', 'Inspector')
        ->get();
        return $reportUsers;
    }

    public static function proceed($ID)
    {
        DB::table('report')->update(['sending' => 'Admin'])->where('Account_ID', '=', $ID);
    }
}