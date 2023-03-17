<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AnonymousController;
use App\Http\Controllers\LoginController;
use App\Http\Controllers\SignupController;
use App\Http\Controllers\AccountController;
use App\Http\Controllers\CampaignController;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

/*login */
Route::get('/login', [LoginController::class,'index'] );

/*register*/
Route::get('/signup',[SignupController::class,'index']);

/*anonymous/**/
Route::get('/anonymous/home', [AnonymousController::class,'home'])->name('home');

/*account */
Route::get('/account/all', [AccountController::class, 'showAll'])->name('accountAll');
Route::get('/account/{id}', [AccountController::class, 'show'])->name('account');

/*campaign */
Route::get('/campaign/create', [CampaignController::class, 'create'])->name('campaignCreate');
Route::post('/campaign/save', [CampaignController::class, 'save'])->name('campaignSave');
Route::get('/campaign/edit/{id}', [CampaignController::class, 'edit'])->name('campaignEdit');
Route::post('/campaign/update/{id}', [CampaignController::class, 'update'])->name('campaignUpdate');
Route::get('/campaign/all', [CampaignController::class, 'showAll'])->name('campaignAll');
Route::get('/campaign/{id}', [CampaignController::class, 'show'])->name('campaign');