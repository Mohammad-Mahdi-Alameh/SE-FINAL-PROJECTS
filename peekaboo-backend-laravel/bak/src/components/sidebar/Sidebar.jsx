import "./sidebar.scss";
import logo from "../../PEEKABOO.svg"
import PersonOutlineIcon from "@mui/icons-material/PersonOutline";
import ExitToAppIcon from "@mui/icons-material/ExitToApp";
import AccountCircleOutlinedIcon from "@mui/icons-material/AccountCircleOutlined";
import MyLocationIcon from '@mui/icons-material/MyLocation';
import WrongLocationIcon from '@mui/icons-material/WrongLocation';
import MapIcon from '@mui/icons-material/Map';
import { Link ,useNavigate} from "react-router-dom";
const Sidebar = () => {
  // navigate = useNavigate();
  return (
    <div className="sidebar">
      <div className="top">
      <Link to="/" style={{ textDecoration: "none" }}>
        <img src={logo} className="logo"/>
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
            <MapIcon  className="icon" />
            <span>Map</span>
          </li>
          </Link>
          <Link to="/false_alarms" style={{ textDecoration: "none" }}>
          <li>
            <WrongLocationIcon className="icon" />
            <span>False Alarms</span>
          </li>
          </Link>

          <p className="title">USER</p>
          <li>
            <AccountCircleOutlinedIcon className="icon" />
            <span>Profile</span>
          </li>
          <li>
            <ExitToAppIcon className="icon" />
            <span onClick={()=>{

            localStorage.removeItem('token');
            // navigate("/");
            }
            
            
            
            }>Logout</span>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default Sidebar;
