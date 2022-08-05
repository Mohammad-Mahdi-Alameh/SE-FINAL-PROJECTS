//Style File
import "./index.scss";
//React Hooks
import { useEffect} from 'react';
//React Router Dom
import { Routes, Route,useNavigate } from "react-router-dom";
//Components
import Map from "./pages/map/Map";
import FalseAlarms from "./pages/falseInfras/FalseInfras";
import Login from "./pages/login/Login";
import View from "./pages/view/View";
import Table from "./components/table/Table";
import Sidebar from "./components/sidebar/Sidebar";
//Axios
import axios from 'axios';

function App() {
  let navigate = useNavigate();
  let token=localStorage.getItem("token");
  useEffect(() => {
  
  if(!token){
    navigate('/login');
  }
else{
  axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('token')}`;

}},[])
  return (
      <div className="App">
        <Sidebar />
        <div className="container">
          <Routes>
            <Route index element={<Map />} />
            <Route path="login" element={<Login />} />
            <Route path="map" element={<Map />} />
            <Route path="false_alarms" element={<FalseAlarms />} />
            <Route path="users">
              <Route index element={<Table />} />
              <Route path=":userId" element={<View />} />
            </Route>
          </Routes>
        </div>
      </div>
  );
}

export default App;
