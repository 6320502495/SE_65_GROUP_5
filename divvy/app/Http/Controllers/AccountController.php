<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class AccountController extends Controller
{
    public function index() {
        $accounts=DB::table('account')->first();
        return view('account.account', compact('accounts'));
    }
}
