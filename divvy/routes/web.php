<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AnonymousController;
use App\Http\Controllers\LoginController;
use App\Http\Controllers\SignupController;
use App\Http\Controllers\AccountController;
use App\Http\Controllers\CampaignController;
use App\Http\Controllers\AdminController;
use App\Http\Controllers\HomeController;
use App\Http\Controllers\DocumentInspectionController;

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

/*register*/
Route::get('/signup',[SignupController::class,'index'])->name('signup');
Route::post('/signup/add',[SignupController::class,'store'])->name('register');

/*admin registration*/
Route::get('admin/home',[HomeController::class,'adminHome'])->name('admin.home')->middleware('is_admin');

/*login */
Route::get('/login', [LoginController::class,'index'] )->name('login');

/*anonymous/**/
Route::get('/anonymous/home', [AnonymousController::class,'home'])->name('home');

/*account */
Route::get('/account/all', [AccountController::class, 'showAll'])->name('accountAll');
Route::get('/account/{id}', [AccountController::class, 'show'])->name('account');

/*campaign */
Route::get('/campaign/create', [CampaignController::class, 'create'])->name('campaignCreate');
Route::get('/campaign/createIndividual', [CampaignController::class, 'createIndividual'])->name('campaignCreateIndividual');
Route::get('/campaign/createOrganization', [CampaignController::class, 'createOrganization'])->name('campaignCreateOrganization');
Route::post('/campaign/saveIndividual', [CampaignController::class, 'saveIndividual'])->name('campaignSaveIndividual');
Route::post('/campaign/saveOrganization', [CampaignController::class, 'saveOrganization'])->name('campaignSaveOrganization');
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
Route::get('/documentInspection', function () { 
    try {
        $password = file_get_contents("D:/Passwords/DocumentInspectorPassword.txt");
        if (password_verify($password, '$2y$10$MHIsDHSbSAjnJQ.CbnBTRurAkGC6dGpFIZ/uInce7.OvDN18CBt5S')) {
            return view('documentInspection/reportAcquisition');
        }
        $password = file_get_contents("D:/Passwords/AdminPassword.txt");
        if (password_verify($password, '$2y$10$hKkp36BE38F6hMb0C9nMp.0SKNo1QHm29TPo8BNg1tk7GbJIo6w7q')) {
            return view('documentInspection/reportObligation');
        }
    } catch (Exception $e) {
        echo $e->getMessage();
    } 
});

Route::get('/documentInspection/proceed/{id}', function ($id) {
    try {
        $password = file_get_contents("D:/Passwords/DocumentInspectorPassword.txt");
        if (password_verify($password, '$2y$10$MHIsDHSbSAjnJQ.CbnBTRurAkGC6dGpFIZ/uInce7.OvDN18CBt5S')) {
            DocumentInspectionController::proceed($id);
            echo "Go back and refresh the page!";
        }
    } catch (Exception $e) {
        echo $e->getMessage();
    } 
});

Route::get('/documentInspection/reject/{id}', function ($id) {
    try {
        $password = file_get_contents("D:/Passwords/DocumentInspectorPassword.txt");
        if (password_verify($password, '$2y$10$MHIsDHSbSAjnJQ.CbnBTRurAkGC6dGpFIZ/uInce7.OvDN18CBt5S')) {
            DocumentInspectionController::reject($id);
            echo "Go back and refresh the page!";
        }
    } catch (Exception $e) {
        echo $e->getMessage();
    } 
});

Route::get('/admin/ban/{id}', function ($id) {
    try {
        $password = file_get_contents("D:/Passwords/AdminPassword.txt");
        if (password_verify($password, '$2y$10$hKkp36BE38F6hMb0C9nMp.0SKNo1QHm29TPo8BNg1tk7GbJIo6w7q')) {
            DocumentInspectionController::ban($id);
            echo "Go back and refresh the page!";
        }
    } catch (Exception $e) {
        echo $e->getMessage();
    } 
});

Route::get('/admin/reject/{id}', function ($id) {
    try {
        $password = file_get_contents("D:/Passwords/AdminPassword.txt");
        if (password_verify($password, '$2y$10$hKkp36BE38F6hMb0C9nMp.0SKNo1QHm29TPo8BNg1tk7GbJIo6w7q')) {
            DocumentInspectionController::reject($id);
            echo "Go back and refresh the page!";
        }
    } catch (Exception $e) {
        echo $e->getMessage();
    } 
});
Auth::routes();

Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');

/*user*/

Route::middleware([
    'auth:sanctum',
    config('jetstream.auth_session'),
    'verified'
])->group(function () {
    Route::get('/dashboard', [UserController::class, 'home'], function () {
        return view('dashboard');
    })->name('dashboard');
});

    
Route::get('/campaign/{id}', [UserController::class, 'show'])->name('campaign');
Route::post('/campaign/donate',[UserController::class, 'userDonate'])->name('userDonate');


