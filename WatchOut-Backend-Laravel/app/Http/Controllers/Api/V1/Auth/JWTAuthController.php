<?php

namespace App\Http\Controllers\Api\V1\Auth;

use Illuminate\Http\Request;

use App\Models\User;
use App\Models\InfrastructuralProblem;

use App\Http\Controllers\Controller;
use JWTAuth;
use Validator;
use Auth;
use Tymon\JWTAuth\Exceptions\JWTException;
use Symfony\Component\HttpFoundation\Response;


class JWTAuthController extends Controller
{
    public $token = true;

    public function __construct()
    {
        $this->middleware('auth:api', ['except' => ['login', 'signup']]);
    }


    /**
     * Register user.
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function signup(Request $request)
    {


        $validator = Validator::make($request->all(), [
            'firstname' => 'required|string|min:2|max:100',
            'lastname' => 'required|string|min:2|max:100',
            'phonenumber' => 'required|integer',
            'username' => 'required|string|max:100|unique:users',
            'password' => 'required|string|min:6',
            'c_password' => 'required|same:password',
        ]);

        if($validator->fails()) {
            return response()->json(["message" => "Validator Failed ! Check your submiited values again!"]);
        }


        $user = new User;

        $user->firstname = $request->firstname;
        $user->lastname = $request->lastname;
        $user->phonenumber = $request->phonenumber;
        $user->picture = "";
        $user->username = $request->username;
        $user->password = bcrypt( $request->password);
        $user->balance = 0;
        $user->total_reports = 0;
        $user->is_admin = 0;

        $user->save();

        $input = $request->only('username', 'password');
        $jwt_token = JWTAuth::attempt($input);


        return response()->json([
            'message' => 'User successfully registered',
            'token' => $jwt_token,
            'user_id' => $user->id
        ], Response::HTTP_OK);
    }

    public function login(Request $request)
    {
        $input = $request->only('username', 'password');
        $jwt_token = null;

        if (!$jwt_token = JWTAuth::attempt($input)) {
            return response()->json([
                'success' => false,
                'message' => 'Invalid Usernname or Password',
            ]);
        }
        $user = Auth::user();

        $totalFalseInfras = InfrastructuralProblem::where("false_infra",1)->count();

        return response()->json([
            'success' => true,
            'token' => $jwt_token,
            'user_id' => $user->id,
            'firstname' => $user->firstname,
            'lastname' => $user->lastname,
            'phonenumber' => $user->phonenumber,
            'picture' => $user->picture,
            'balance' => $user->balance,
            'total_reports' => $user->total_reports,
            'is_admin' => $user->is_admin,
            'total_false_infras' => $totalFalseInfras
        ]);
    }

    public function logout() {
        Auth::guard('api')->logout();

        return response()->json([
            'status' => 'success',
            'message' => 'logout'
        ], 200);
    }



    public function refresh(){
        return $this->respondWithToken(auth()->refresh());
    }

    protected function respondWithToken($token){
        return response()->json([
            'access_token' => $token,
            'token_type' => 'bearer',
            'expires_in' => auth()->factory()->getTTL() * 60
        ]);
    }




}
