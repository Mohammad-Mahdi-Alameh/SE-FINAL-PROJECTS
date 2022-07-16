<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\V1\Auth\JWTAuthController;
use App\Http\Controllers\Api\V1\User\UserController;



Route::group(['prefix' => 'user'] , function(){
    
    Route::post('/signup', [JWTAuthController::class, 'signup']);
    
    Route::post('/login', [JWTAuthController::class, 'login']);
    
    Route::group(['middleware' => 'api'], function($router) {

        Route::get('/getNearInfras', [UserController::class, 'getNearInfras']);   //infras :infrastructural problems
        
        Route::post('/report', [UserController::class, 'report']);
        
        Route::post('/logout', [JWTAuthController::class, 'logout']);
    });
});