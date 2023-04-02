<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

Class LoginController extends Controller {
    function index() {
        return view('login.login');
    }
    public function home(){
        return view('welcome');
    }
}