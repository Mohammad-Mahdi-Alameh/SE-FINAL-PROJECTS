<?php

namespace App\Http\Controllers\Api\V1\Admin;
use Symfony\Component\HttpFoundation\Response;
use App\Http\Controllers\Controller;
use App\Models\InfrastructuralProblem;
use Illuminate\Http\Request;

class AdminController extends Controller
{
    public function fixInfra($infra_id){
        $infra = InfrastructuralProblem::findOrFail($infra_id);
        $infra->delete();
        
        return response()->json([
            'message' => 'Deleted successfully ',
        ], Response::HTTP_OK);
    }
}
