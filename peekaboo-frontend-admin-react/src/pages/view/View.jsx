//React Hooks
import { useState, useEffect } from "react";
//React Router Dom
import { useParams } from "react-router-dom"
//Components
import { DataGrid } from "@mui/x-data-grid";
//Utilities
import {  infrasColumns } from "../../Utilities/Utilities";
//Constants
import { base_url } from "../../Utilities/Constants";
//axios
import axios from "axios";
//SVG's
import profile_icon from "../../profile_icon.svg"


const View = () => {
  let { userId } = useParams();
  let [info, setInfo] = useState([]);
  const [infras, setInfras] = useState([]);
  const [infrasLoading, setInfrasLoading] = useState(false)
  const [profileLoading, setProfileLoading] = useState(false)

  const fetchInfo = async () => {

    try {
      const res = await axios.get(base_url + "/users/" + userId);
      const data = await res.data;
      return data;
    }
    catch (error) {
      console.log(error)
    }

  };

  const getInfras = async () => {
    try {
      const res = await axios.get(base_url + "/get_all_infras/" + userId);
      const data = await res.data;
      return data;
    }
    catch (error) {
      console.log(error)
    }
  };

  useEffect(() => {

    const getInfo = async () => {
      const serverInfo = await fetchInfo();
      console.log(serverInfo)
      setInfo(serverInfo);
      setProfileLoading(true)
    };
    const getData = async () => {
      const infrasFromServer = await getInfras();
      setInfras(infrasFromServer);
      setInfrasLoading(true)
    };
    getInfo();
    getData();
  }, []);



  return (<div className="view">
    {profileLoading && <div className="singleContainer">
      <div className="top">
        <div className="left">
            <div className="item">
            <img
              src={info.picture ? info.picture : profile_icon}
              alt=""
              className="itemImg"
            />
            <div className="details">
              <h1 className="itemTitle">{info.firstname}{" "}{info.lastname}</h1>
              <div className="detailItem">
                <span className="itemKey">Username :</span>
                <span className="itemValue">{" " + info.username}</span>
              </div>
              <div className="detailItem">
                <span className="itemKey">Phone :</span>
                <span className="itemValue">{" " + info.phonenumber}</span>
              </div>
              <div className="detailItem">
                <span className="itemKey">Toral Reports :</span>
                <span className="itemValue">{" " + info.total_reports}</span>
              </div>
              <div className="detailItem">
                <span className="itemKey">Balance : </span>
                <span className="itemValue">
                  {info.balance}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>}
    <div>
      {infrasLoading && <div className="table">
        <div className="tableTitle">
          Report History
        </div>
        <DataGrid
          className="datagrid"
          rows={infras}
          columns={infrasColumns}
          pageSize={3}
          rowsPerPageOptions={[3]}
          checkboxSelection
        />
      </div>}
    </div>
  </div>)


}

export default View