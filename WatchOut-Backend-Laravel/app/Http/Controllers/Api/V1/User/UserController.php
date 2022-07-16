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
            'latitude' => 'required|string|min:2|max:100',
            'longitude' => 'required|string|min:2|max:100',
            // 'risk_level' => 'required',
            'type' => 'required|string', 
        ]);

        if($validator->fails()) {
            return response()->json(["message" => "Validator Failed ! Check your submiited values again!"]);
        }

     
        $infrastructural_problem = new InfrastructuralProblem;

        $record = Type::where("name","=",$request->type)->get();
        
        if(count($record) == 0){
            return response()->json([
                'message' => 'There is no such type!',
                
            ]);
        }
        $type_id = json_decode($record,true)[0]["id"];

        $infrastructural_problem->latitude = $request->latitude;
        $infrastructural_problem->longitude = $request->longitude;
        $infrastructural_problem->is_fixed = 0;
        $infrastructural_problem->risk_level = 0;
        $infrastructural_problem->type_id = $type_id;

        $infrastructural_problem->save();

        return response()->json([
            'message' => 'Infrastructural problem reported successfully',
        ], Response::HTTP_OK);
    }

    public function get(Request $request){
        function getDistance($lat1, $lon1, $lat2, $lon2) {
            if (($lat1 == $lat2) && ($lon1 == $lon2)) {
              return 0;
            }
            else {
              $theta = $lon1 - $lon2;
              $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
              $dist = acos($dist);
              $dist = rad2deg($dist);
              $miles = $dist * 60 * 1.1515;
              $unit = strtoupper($unit);
          
              return $miles * 1.609344;
             
            }
          }

        $infrastructural_problems=InfrastructuralProblem::all();
        return $infrastructural_problems;
    }
   

      
      
}
