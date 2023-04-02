<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\models\Signup;

class SignupController extends Controller{
    public function index(){
        return view('signup.signup');
    }
    public function home(){
        return view('welcome');
    }
    //ตรวจสอบข้อมูล
    public function store(Request $request){
        //บันทึกข้อมูล
        $signup = array();
         $signup["Account_name"]=$request->Account_Username;
        $signup["Account_Firstname"]=$request->Account_Firstname;
        $signup["Account_Surname"]=$request->Account_Surname;
        $signup["Account_Birthday"]=$request->Account_Birthday;
        $signup["Account_Username"]=$request->Account_Username;
        $signup["Account_Password"]=$request->Account_Password;
        DB::table('account')->insert($signup);
        return redirect()->back()->with('susccess',"บันทึกข้อมูลเรียนร้อย");
    }
}