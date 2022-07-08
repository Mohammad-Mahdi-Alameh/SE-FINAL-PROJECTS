<?php

namespace App\Http\Controllers\Api\V1\User;
use Symfony\Component\HttpFoundation\Response;
use App\Models\InfrastructuralProblem;
use App\Models\Type;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Validator;


class UserController extends Controller
{
    public function report(Request $request)
    {

        
        $validator = Validator::make($request->all(), [
            'latitude' => 'required|string|min:2|max:100|unique:infrastructural_problems',
            'longitude' => 'required|string|min:2|max:100|unique:infrastructural_problems',
            'risk_level' => 'required',
            'type_name' => 'required|string', 
        ]);

        if($validator->fails()) {
            return response()->json(["message" => "Validator Failed ! Check your submiited values again!"]);
        }

     
        $infrastructural_problem = new InfrastructuralProblem;

        $record = Type::where("name","=",$request->type_name)->get();
        
        if(count($record) == 0){
            return response()->json([
                'message' => 'There is no such type!',
                
            ]);
        }
        $type_id = json_decode($record,true)[0]["id"];

        $infrastructural_problem->latitude = $request->latitude;
        $infrastructural_problem->longitude = $request->longitude;
        $infrastructural_problem->is_fixed = 0;
        $infrastructural_problem->risk_level = $request->risk_level;
        $infrastructural_problem->type_id = $type_id;

        $infrastructural_problem->save();

        return response()->json([
            'message' => 'Infrastructural problem reported successfuly',
        ], Response::HTTP_OK);
    }
}
