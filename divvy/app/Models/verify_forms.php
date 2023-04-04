<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class verify_forms extends Model
{
    use HasFactory;
    protected $fillable = [
        'status_form',
        'id_user',
        'nameTitle',
        'name',
        'lasName',
        'birthDate',
        'phone_Number',
        'bank',
        'bank_num',
        'address',
        'image',
    ];
}
