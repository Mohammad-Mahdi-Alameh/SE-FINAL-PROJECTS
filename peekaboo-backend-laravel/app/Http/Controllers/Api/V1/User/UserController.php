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
            // 'speed' => 'required|between:0,99.99',
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
          if($distance < 2){
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

    public function falseInfra($infra_id){
        $infra = InfrastructuralProblem::findOrFail($infra_id);
        $infra->false_infra = 1;
        // $infra->is_fixed=0;
        $infra->pending = 1;
        $infra->save();
        return response()->json([
            'message' => 'reported successfully',
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

    public function getUserFalseInfras($user_id){
      $infras = InfrastructuralProblem::where([["user_id","=",$user_id],["false_infra","=",1]])->get();
      return $infras;
  }
}
