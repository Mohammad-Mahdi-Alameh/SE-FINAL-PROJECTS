<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\V1\Auth\JWTAuthController;
use App\Http\Controllers\Api\V1\User\UserController;
use App\Http\Controllers\Api\V1\Common\CommonController;
use App\Http\Controllers\Api\V1\Admin\AdminController;



Route::group(['prefix' => 'user'] , function(){
    
        Route::post('/login', [JWTAuthController::class, 'login']);
    
        Route::post('/signup', [JWTAuthController::class, 'signup']);

        Route::group(['middleware' => 'api'], function($router) {

        //infras :infrastructural problems
        Route::post('/get_near_infras', [UserController::class, 'getNearInfras']);   

        Route::put('/add_coins/{user_id}', [UserController::class, 'addCoins']);

        Route::put('/false_infra/{infra_id}', [UserController::class, 'falseInfra']);
        
        Route::put('/edit_profile', [UserController::class, 'editProfile']);

        Route::post('/report', [CommonController::class, 'report']);

        Route::post('/logout', [JWTAuthController::class, 'logout']);

        Route::get('/get_all_infras/{user_id}', [CommonController::class, 'getAllInfras']);

        Route::get('/false_infras/{user_id}', [CommonController::class, 'getUserFalseInfras']);
    });
});

    Route::group(['prefix' => 'admin'] , function(){
       
        Route::post('/login', [JWTAuthController::class, 'login']);

        Route::group(['middleware' => 'api'], function($router) {

        Route::post('/logout', [JWTAuthController::class, 'logout']);

        Route::put('/fix_infra/{infra_id}', [AdminController::class, 'fixInfra']);

        Route::post('/report', [CommonController::class, 'report']);
        
        Route::put('/reject_false_infra/{infra_id}', [AdminController::class, 'rejectFalseInfra']);
        
        Route::put('/delete_user/{user_id}', [AdminController::class, 'deleteUser']);
      
        Route::get('/users/{user_id?}', [AdminController::class, 'getAllUsers']);

        Route::get('/false_infras/{infra_id?}', [AdminController::class, 'getFalseInfras']);

        Route::get('/get_all_infras/{user_id}', [CommonController::class, 'getAllInfras']);


    });

});

