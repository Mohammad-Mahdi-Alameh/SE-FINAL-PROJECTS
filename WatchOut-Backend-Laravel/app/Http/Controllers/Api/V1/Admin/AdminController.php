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
        $infra->delete();
        
        return response()->json([
            'message' => 'Deleted successfully ',
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
        $users=User::all();
        return $users;
    }
}
