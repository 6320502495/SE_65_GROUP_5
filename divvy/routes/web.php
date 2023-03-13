<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AnonymousController;
use App\Http\Controllers\LoginController;
use App\Http\Controllers\SignupController;

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


