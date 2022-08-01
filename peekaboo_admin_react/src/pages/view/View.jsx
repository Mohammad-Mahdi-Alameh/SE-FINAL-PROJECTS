import "./view.scss"
import { useState, useEffect } from "react";
import { base_url } from "../../Constants";


const View = () => {
  var axios = require('axios');
    let [info, setInfo] = useState([]);
    const [infras, setInfras] = useState([]);
    const [id, setId] = useState(0);
    let urlString = window.location.href;
    let paramString = urlString.split('?')[1];
    let queryString = new URLSearchParams(paramString);
    for (var pair of queryString.entries()) {
        setId(pair[1])
    }
    const fetchInfo = async () => {

            const res = await axios.get(base_url +"/users/" + id);
            const data = await res.data;
            return data;

    };
    useEffect(() => {
        const getInfo = async () => {
            const serverInfo = await fetchInfo();
            setInfo(serverInfo[0]);
        };
        const getData = async () => {
          const infrasFromServer = await getInfras();
          setInfras(infrasFromServer);
        };
        getData();
        getInfo();

    }, []);

   
  
   
  
  //getting infras reported by this user
    const getInfras = async () => {
      const res = await axios.get(base_url + "/get_all_infras/" + user_id);
      const data = await res.data;
      return data;
    };
  
  
  
  



    return (<div className="view">
    <div className="singleContainer">
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
                <span className="itemValue">{" "+info.username}</span>
              </div>
              <div className="detailItem">
                <span className="itemKey">Phone :</span>
                <span className="itemValue">{" "+info.phonenumber}</span>
              </div>
               <div className="detailItem">
                <span className="itemKey">Toral Reports :</span>
                <span className="itemValue">{" "+info.total_reports}</span>
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
    </div>
   
  </div>)


}

export default View;