//React Router
import { Link, useNavigate } from "react-router-dom";
//Svg's
import logo from "../../assets/PEEKABOO.svg"
//Mui Icons
import PersonOutlineIcon from "@mui/icons-material/PersonOutline";
import ExitToAppIcon from "@mui/icons-material/ExitToApp";
import WrongLocationIcon from '@mui/icons-material/WrongLocation';
import MapIcon from '@mui/icons-material/Map';

const Sidebar = () => {
  let navigate = useNavigate();
  return (
    <div className="sidebar">
      <div className="top">
        <Link to="/" style={{ textDecoration: "none" }}>
          <img src={logo} className="logo" />
        </Link>
      </div>
      <hr />
      <div className="center">
        <ul>
          <p className="title">MANAGE</p>
          <Link to="/users" style={{ textDecoration: "none" }}>
            <li>
              <PersonOutlineIcon className="icon" />
              <span>Users</span>
            </li>
          </Link>

          <Link to="/map" style={{ textDecoration: "none" }}>
            <li>
              <MapIcon className="icon" />
              <span>Map</span>
            </li>
          </Link>
          <Link to="/false_alarms" style={{ textDecoration: "none" }}>
            <li>
              <WrongLocationIcon className="icon" />
              <span>False Infras</span>
            </li>
          </Link>

          <li className="logout"
          onClick={() => {
            localStorage.removeItem('token');
            navigate("/login");
          }
          }>
            <ExitToAppIcon className="icon" />
            <span >Logout</span>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default Sidebar;
