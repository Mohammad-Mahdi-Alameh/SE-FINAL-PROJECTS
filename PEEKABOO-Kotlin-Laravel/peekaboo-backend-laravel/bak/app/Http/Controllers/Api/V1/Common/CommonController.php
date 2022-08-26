<?php

namespace App\Http\Controllers\Api\V1\Common;
use Symfony\Component\HttpFoundation\Response;
use App\Models\InfrastructuralProblem;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Validator;
class CommonController extends Controller
{
  
    
    public function getAllInfras($user_id = null){
        if($user_id == 0 ){
            $infras = InfrastructuralProblem :: all();
            return $infras;
        }
        if($user_id){
            $infras = InfrastructuralProblem :: where("user_id","=",$user_id) ->get();
            return $infras;
        }

        $infrastructural_problems=InfrastructuralProblem::all();
        return $infrastructural_problems;

    }
}