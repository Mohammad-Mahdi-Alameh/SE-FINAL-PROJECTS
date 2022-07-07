<?php

namespace App\Http\Controllers\Api\V1\Auth;

use Illuminate\Http\Request;

use App\Models\User;

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
            'username' => 'required|string|max:100|unique:users',
            'password' => 'required|string|min:6',
            'c_password' => 'required|same:password', 
            'score' => 'required|string|',
            'level' => 'required|string|',
            // 'is_admin' => 'required|string|confirmed|min:6',
        ]);

        if($validator->fails()) {
            return response()->json(["message" => "Validator Failed ! Check your submiited values again!"]);
        }

     
        $user = new User;

        $user->firstname = $request->firstname;
        $user->lastname = $request->lastname;
        $user->username = $request->username;
        $user->password = bcrypt( $request->password);
        $user->score = $request->score;
        $user->level = $request->level;
        $user->is_admin = 0;

        $user->save();

        $input = $request->only('username', 'password');  
        $jwt_token = JWTAuth::attempt($input);


        return response()->json([
            'message' => 'User successfully registered',
            'token' => $jwt_token,
        ], Response::HTTP_OK);
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
