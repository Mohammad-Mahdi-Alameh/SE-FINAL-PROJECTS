<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\V1\Auth\JWTAuthController;
use App\Http\Controllers\Api\V1\MyController;


Route::group(['middleware' => 'api'], function($router) {

    Route::post('/signup', [JWTAuthController::class, 'signup']);
    
    Route::post('/login', [JWTAuthController::class, 'login']);
});