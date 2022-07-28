import "./login.scss"
import { useState } from 'react';

import logo from "../../PEEKABOO-white.svg"

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");


    return (<div className="login"><div className="welcome">Welcome to<img src={logo} alt="" />
    </div>
        <div className="please">Please login to continue :</div>
        <div className="form-layout">
            <div className="form">
                <div className="input-layout">

                    <label>Username  </label>
                    <input
                        type="text"
                        placeholder={"Enter Your Username"}
                        value={username}
                    />
                </div>
                <div className="input-layout">
                    <label>Password </label>
                    <input
                        type="password"
                        placeholder={"Enter Your Password"}
                        value={password}
                    />
                </div>
            </div>
        </div>



    </div>)


}

export default Login