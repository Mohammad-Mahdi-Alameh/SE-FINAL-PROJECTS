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
        $infrastructural_problem->false_alarms = 0;
        $infrastructural_problem->user_id = $request->user_id;
        $infrastructural_problem->type = $request ->type;

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
            'base_latitude' => 'required|between:0,99.99|min:2|max:100',
            'base_longitude' => 'required|between:0,99.99|min:2|max:100',
            'speed' => 'required|between:0,99.99',
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
          $distance = getDistance($request->base_latitude,$request->base_longitude,$d->latitude,$d->longitude,"K");
          $distance = $distance * 1000;
          $time_to_stop = ($request ->speed *7)/27.77; 
          if($distance < $request -> speed * $time_to_stop){
                  array_push($near_infra_problems,$d);

                }

        }
        return $near_infra_problems;
    }

    public function addCoins($user_id){
        $user = User::findOrFail($user_id);
        $user->balance +=5;
        $user->total_reports +=1;
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
        $user->username = $request ->username;//will write later a code to check if username exists
        $user->password = bcrypt( $request->password);

        $user->save();
        return response()->json([
            'message' => 'updated successfully',
        ], Response::HTTP_OK);

    }
}
