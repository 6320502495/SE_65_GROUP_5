<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Campaign extends Model
{
    use HasFactory;
    protected $table = 'campaign';

    protected $fillable = [
        'Account_ID',
        'Campaign_Name',
        'Campaign_Details',
        'Campaign_Tel',
        'Campaign_Bank_ID',
        'Campaign_Bank_Type',
        'Campaign_Image',
        'Campaign_Starting_Date',
        'Campaign_Duration',
        'Campaign_Category',
        'Campaign_Donation_Goals',
        'Campaign_Type',
        'Campaign_Institute_Name',
        'Campaign_Institute_Paper',
        'Campaign_Institute_Tel',
        'Approve_Campaign_ID ',
    ];
}
