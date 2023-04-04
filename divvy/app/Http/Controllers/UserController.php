<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\View\View;
use App\Models\Campaign;
use App\Models\DonationRecord;
use Illuminate\Support\Facades\Auth;


class UserController extends Controller
{
    function home(){
        $campaign=DB::table('campaign')->get();
        
        return view('dashboard', compact('campaign'));
    }
   
    public function show(int $Campaign_ID) : View
    {
        $campaign=Campaign::find($Campaign_ID);
        
        
        return view('user.campaign', compact('campaign'));
    }
    public function userDonate(Request $request ) 
    {
       
        $request->validate([
            'donation_amount'=>'required|nullable|numeric|min:0.01'
           ],
            [
                'donation_amount.required'=>'กรุณาป้อนจำนวนเงิน'
            ]);
       
        $data=array();
        $data["Amount"]=$request->donation_amount;
        $data["User_ID"]=Auth::user()->id;
        $data["Campaign_ID"]=$request->Campaign_ID;
        $data["Date"]=now();
    
        $total_donations = DB::table('donation_record')
        ->where('Campaign_ID', $data["Campaign_ID"])
        ->sum('Amount');
        
        
        $target_amount = DB::table('campaign')
        ->where('Campaign_ID', $data["Campaign_ID"])
        ->value('Campaign_Donation_Goals');

       

        if($total_donations+$request->donation_amount > $target_amount){
            return redirect()->back()->with('unsuccess','กรุณาเช็คจำนวน');
            dd($total_donations+$request->donation_amount,$target_amount);
        }
        
        else{
            

            if(Auth::user()->balance < $request->donation_amount){
                return redirect()->back()->with('unsuccess','กรุณาเช็คยอดเงินคงเหลือ');
            }
            else{
                
                
                DB::table('donation_record')->insert($data);

                $current_balance=Auth::user()->balance - $request->donation_amount;
                
                DB::table('users')
                ->where('id', $data["User_ID"])
                ->update(['balance' => $current_balance]);
                
                
            }

            $total_donations = DB::table('donation_record')
            ->where('Campaign_ID', $data["Campaign_ID"])
            ->sum('Amount');

            if($total_donations==$target_amount){
                DB::table('campaign')
                ->where('Campaign_ID', $data["Campaign_ID"])
                ->update(['status' => 0]);

                
        
                return $this->home();
                

            }      

        }
        

    return redirect()->back()->with('success','บริจาคสำเร็จ');
       
    }

    
    
}


?>