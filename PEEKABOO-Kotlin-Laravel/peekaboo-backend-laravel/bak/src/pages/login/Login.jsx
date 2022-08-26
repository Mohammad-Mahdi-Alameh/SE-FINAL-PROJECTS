import "./login.scss"
import { useState } from 'react';
import {useNavigate } from "react-router-dom";

import logo from "../../PEEKABOO-white.svg"
import { base_url } from "../../Constants";

const Login = () => {
    const navigate=useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    var data = JSON.stringify({
        "username": username,
        "password": password
      });
    var config = {
        method: 'post',
        url: base_url+'/login',
        headers: {
          'Content-Type': 'application/json'
        },
        data: data
      };
     
      const onLogin = (e) => {
        e.preventDefault();
        if (!username || !password) {
          alert("Please fill missing fields !");
          return;
        }
        fetch(base_url+'/login', config)
          .then(function (response) {
            console.log(response.json());
            localStorage.setItem("token", response.data.token);
            navigate("/");
          })
          .catch(function (error) {
            alert("Wrong Username or password !")
          });
    
        setUsername("");
        setPassword("");
      }


    return (<div className="login"><div className="welcome">Welcome to<img src={logo} alt="" />
    </div>
        <div className="please">Please login to continue :</div>
        <form className="form-layout" onSubmit={onLogin}>
            <div className="form">
                <div className="input-layout">

                    <label>Username  </label>
                    <input
                        type="text"
                        placeholder={"Enter Your Username"}
                        value={username}
                        onChange={(e) => {
                            setUsername(e.target.value);
                          }}
                    />
                </div>
                <div className="input-layout">
                    <label>Password </label>
                    <input
                        type="password"
                        placeholder={"Enter Your Password"}
                        value={password}
                        onChange={(e) => {
                            setPassword(e.target.value);
                          }}
                    />
                </div>
            </div>
        <input type={"submit"} value="Login" className="btn btn-block" />
</form>
        </div>


    )


}

export default Login