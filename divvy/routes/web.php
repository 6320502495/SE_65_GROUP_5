<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AnonymousController;
use App\Http\Controllers\LoginController;
use App\Http\Controllers\SignupController;
use App\Http\Controllers\AccountController;
use App\Http\Controllers\CampaignController;
use App\Http\Controllers\AdminController;

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

/*admin*/
    /*Account*/
Route::get('/admin/account/all', [AdminController::class, 'showAllAccount'])->name('adminAccountAll');
Route::get('/admin/account', [AdminController::class, 'showAccount'])->name('adminAccount');
Route::get('/admin/account/search', [AdminController::class, 'searchAccount'])->name('adminSearchAccount');
Route::get('/admin/account/update', [AdminController::class, 'updateAccount'])->name('adminUpdateAccount');
    /*Ban Account*/
Route::get('/admin/account/ban/all', [AdminController::class, 'showBanAccount'])->name('adminAccountBan');
Route::get('/admin/account/ban', [AdminController::class, 'showBan'])->name('adminBan');
Route::get('/admin/account/ban/search', [AdminController::class, 'searchBanAccount'])->name('adminSearchBanAccount');
Route::get('/admin/account/ban/update', [AdminController::class, 'updateBanAccount'])->name('adminUpdateBan');
    /*Campaign*/
Route::get('/admin/campaign/all', [AdminController::class, 'showAllCampaign'])->name('adminCampaignAll');
Route::get('/admin/campaign', [AdminController::class, 'showCampaign'])->name('adminCampaign');
Route::get('/admin/campaign/search', [AdminController::class, 'searchCampaign'])->name('adminSearchCampaign');
Route::get('/admin/campaign/close', [AdminController::class, 'closeCampaign'])->name('adminCampaignClose');
Route::get('/admin/campaign/cancel', [AdminController::class, 'cancelCampaign'])->name('adminCampaignCancel');

/* Document Inspection */
Route::get('/documentInspection/{password}', function ($password) {
    if (password_verify($password, '$2y$10$MHIsDHSbSAjnJQ.CbnBTRurAkGC6dGpFIZ/uInce7.OvDN18CBt5S')) {
        // password: documentinspectorpassword
        return view('documentInspection/reportAcquisition');
    } else if (password_verify($password, '$2y$10$hKkp36BE38F6hMb0C9nMp.0SKNo1QHm29TPo8BNg1tk7GbJIo6w7q')) {
        // password: adminpassword
        return view('documentInspection/reportObligation');
    }
    // เมื่อ Divvy ทำเสร็จสมบูรณ์ จะต้องลบรหัสผ่านที่ไม่ได้ถูก hash ออกไปจากโค้ดเพื่อความปลอดภัย
});

/*  $hashed_password = password_hash($password, PASSWORD_DEFAULT);
    โค้ดสำหรับการ hash $password ให้กลายเป็น $hashed_password
    password_verify($user_password, $stored_hashed_password)
    โค้ดตรวจสอบว่า $user_password ตรงกับ $stored_hashed_password หรือไม่ */