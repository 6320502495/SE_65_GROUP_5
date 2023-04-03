/*ไม่ได้ใช้*/
<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class AnonymousController extends Controller
{
    function home(){
        return view('anonymous.home');
    }
}
