<?php

namespace App\Http\Controllers\Api\V1\Common;
use Symfony\Component\HttpFoundation\Response;
use App\Models\InfrastructuralProblem;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Validator;
class CommonController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth:api', ['except' => []]);
    }
    
    public function getAllInfras($user_id = null){
        if($user_id == 0 ){
            $infras = InfrastructuralProblem :: where("is_fixed","=",0) ->get();
            return $infras;
        }
        if($user_id){
            $infras = InfrastructuralProblem :: where("user_id","=",$user_id) ->get();
            return $infras;
        }

        $infrastructural_problems=InfrastructuralProblem::all();
        return $infrastructural_problems;

    }
    public function report(Request $request)
    {


        $validator = Validator::make($request->all(), [
            'latitude' => 'required|between:0,99.99|min:2|max:100',
            'longitude' => 'required|between:0,99.99|min:2|max:100',
            'type' => 'required|string',
            'user_id' => 'required|integer'
        ]);

        if($validator->fails()) {
            return response()->json(["message" => "Validator Failed ! Check your submiited values again!"]);
        }


        $infrastructural_problem = new InfrastructuralProblem;

        $infrastructural_problem->latitude = $request->latitude;
        $infrastructural_problem->longitude = $request->longitude;
        $infrastructural_problem->is_fixed = 0;
        $infrastructural_problem->false_infra = 0;
        $infrastructural_problem->rejected = 0;
        $infrastructural_problem->pending = 0;
        $infrastructural_problem->accepted = 0;
        $infrastructural_problem->user_id = $request->user_id;
        $infrastructural_problem->type = $request ->type;

        $infrastructural_problem->save();

        return response()->json([
            'message' => 'Infrastructural problem reported successfully',
            'infra' => $infrastructural_problem
        ], Response::HTTP_OK);
    }
}