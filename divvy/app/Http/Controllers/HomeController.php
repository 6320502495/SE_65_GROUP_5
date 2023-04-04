<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class HomeController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Contracts\Support\Renderable
     */
    public function index()
    {
        return view('home');
    }
    public function financeHome()
    {
        return view('financehome');
    }
    public function documentHome()
    {
        return view('documenthome');
    }
    public function adminHome()
    {
        return view('adminhome');
    }
}
