<?php

namespace App\Http\Controllers\Api\V1\User;
use Symfony\Component\HttpFoundation\Response;
use App\Models\InfrastructuralProblem;
use App\Models\Type;
use App\Models\User;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Validator;
class UserController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth:api', ['except' => []]);
    }

    public function report(Request $request)
    {


        $validator = Validator::make($request->all(), [
            'latitude' => 'required|string|min:2|max:100',
            'longitude' => 'required|string|min:2|max:100',
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
        $infrastructural_problem->type_id = $type_id;

        $infrastructural_problem->save();

        return response()->json([
            'message' => 'Infrastructural problem reported successfully',
        ], Response::HTTP_OK);
    }
//infras :infrastructural problems
    public function getNearInfras(Request $request){
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


              return $miles * 1.609344;

          }
        }

          $validator = Validator::make($request->all(), [
            'base_latitude' => 'required|string|min:2|max:100',
            'base_longitude' => 'required|string|min:2|max:100',
        ]);

        if($validator->fails()) {
            return response()->json(["message" => "Validator Failed ! Check your submiited values again!"]);
        }

        $infrastructural_problems=InfrastructuralProblem::all();
        $decoded = json_decode($infrastructural_problems, false);
        $near_infra_problems = [];
        $i = 0;
        foreach($decoded as $d) {

          $i++;
          $distance = getDistance(floatval($request->base_latitude),floatval($request->base_longitude),floatval($d->latitude),floatval($d->longitude),"K");
          if($distance < 7){
                  array_push($near_infra_problems,$d);

                }

        }
        return $near_infra_problems;
    }

    public function getAllInfras($user_id = null){
        if($user_id){
            $infras = InfrastructuralProblem :: where("user_id","=",$user_id) ->get();
            return $infras;
        }

        $infrastructural_problems=InfrastructuralProblem::all();
        return $infrastructural_problems;

    }

    public function addCoins($user_id){
        $user = User::findOrFail($user_id);
        $user->balance +=5;
        $user->save();
        return response()->json([
            'message' => 'added successfully',
        ], Response::HTTP_OK);

}

    public function falseAlarm($infra_id){
        $infra = InfrastructuralProblem::findOrFail($infra_id);
        $infra->false_alarms +=1;
        $infra->save();
        return response()->json([
            'message' => 'added successfully',
        ], Response::HTTP_OK);

}
    public function editProfile(Request $request){

        $user = User::findOrFail($request->user_id);
        $user->firstname = $request ->firstname;
        $user->lastname = $request ->lastname;
        $user->phonenumber = $request ->phonenumber;
        $user->picture = $request ->picture;
        $user->username = $request ->username;
        $user->password = bcrypt( $request->password);

        $user->save();
        return response()->json([
            'message' => 'updated successfully',
        ], Response::HTTP_OK);

    }
}
