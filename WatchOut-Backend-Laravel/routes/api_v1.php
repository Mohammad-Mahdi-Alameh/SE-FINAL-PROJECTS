<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\V1\Auth\JWTAuthController;
use App\Http\Controllers\Api\V1\User\UserController;



Route::group(['prefix' => 'user'] , function(){

    Route::post('/signup', [JWTAuthController::class, 'signup']);

    Route::post('/login', [JWTAuthController::class, 'login']);

    Route::group(['middleware' => 'api'], function($router) {

        Route::post('/get_near_infras', [UserController::class, 'getNearInfras']);   //infras :infrastructural problems

        Route::get('/get_all_infras/{user_id?}', [UserController::class, 'getAllInfras']);

        Route::put('/add_coins/{user_id}', [UserController::class, 'addCoins']);

        Route::put('/false_alarm/{infra_id}', [UserController::class, 'falseAlarm']);
        
        Route::put('/edit_profile', [UserController::class, 'editProfile']);

        Route::post('/report', [UserController::class, 'report']);

        Route::post('/logout', [JWTAuthController::class, 'logout']);
    });
});
