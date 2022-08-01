import "./view.scss"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom"
import { base_url, infrasColumns } from "../../Constants";
import { DataGrid } from "@mui/x-data-grid";


const View = () => {
  var axios = require('axios');
  let { userId } = useParams();
  let [info, setInfo] = useState([]);
  const [infras, setInfras] = useState([]);
  const [infrasLoading, setInfrasLoading] = useState(false)
  const [profileLoading, setProfileLoading] = useState(false)
  // let navigate=useNavigate();

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
          <h1 className="title">Information</h1>
          <div className="item">
            <img
              src={info.picture}
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
          ///
        </div>
        <DataGrid
          className="datagrid"
          rows={infras}
          columns={infrasColumns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection
        />
      </div>}
    </div>
  </div>)


}

export default View