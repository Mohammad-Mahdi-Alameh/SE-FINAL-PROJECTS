<?php

namespace App\Http\Controllers\Api\V1\Admin;
use Symfony\Component\HttpFoundation\Response;
use App\Http\Controllers\Controller;
use App\Models\InfrastructuralProblem;
use App\Models\User;
use Illuminate\Http\Request;

class AdminController extends Controller
{

    public function __construct()
  {
      $this->middleware('auth:api', ['except' => []]);
  }
 
    public function fixInfra($infra_id){
        $infra = InfrastructuralProblem::findOrFail($infra_id);
        $infra->is_fixed=1;
        $infra->accepted=1;
        $infra->false_infra=0;
        $infra->pending=0;

        $user = User::findOrFail($infra->user_id);
        $user->total_reports= $user->total_reports - 1;
        // $user->total_reports= $user->total_requests - 1;

        $infra->save();
        $user->save();
        
        return response()->json([
            'message' => 'Fixed successfully ',
        ], Response::HTTP_OK);
    }
    public function rejectFalseInfra($infra_id){
        $infra = InfrastructuralProblem::findOrFail($infra_id);
        $infra->pending=0;
        $infra->rejected=1;
        $infra->false_infra=0;
        
        $infra->save();
        
        return response()->json([
            'message' => 'Rejected successfully ',
        ], Response::HTTP_OK);
    }
    public function deleteUser($user_id){
        $user = User::findOrFail($user_id);
        $user->delete();
        
        return response()->json([
            'message' => 'Deleted successfully ',
        ], Response::HTTP_OK);
    }

    public function getAllUsers($user_id = null){
        if($user_id){
            $user = User::findOrFail($user_id);
            return $user;
        }
        $users = User::where("is_admin","=",0)->get();
        return $users;
    }
    public function getFalseInfras($infra_id = null){
        if($infra_id){
            $infra = InfrastructuralProblem::findOrFail($infra_id);
            return $infra;
        }
        $infras = InfrastructuralProblem::where("false_infra","=",1)->get();
        return $infras;
    }

}
