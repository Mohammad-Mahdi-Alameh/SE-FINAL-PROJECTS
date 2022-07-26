import "./view.scss"
import { useState, useEffect } from "react";
import { base_url } from "../../Constants";

const View = () => {
    let [info, setInfo] = useState([]);
    let urlString = window.location.href;
    let paramString = urlString.split('?')[1];
    let queryString = new URLSearchParams(paramString);
    for (var pair of queryString.entries()) {
        var user_id = pair[1]
    }
    const fetchInfo = async () => {

            const res = await fetch(base_url +"/users?id=" + user_id);
            const data = await res.json();
            return data;

    };

    useEffect(() => {
        const getInfo = async () => {
            const serverInfo = await fetchInfo();
            setInfo(serverInfo[0]);
        };
        getInfo();

    }, []);


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
              {/* <div className="detailItem">
                <span className="itemKey">Toral Reports :</span>
                <span className="itemValue">{" "+info.total_reports}</span>
              </div> */}
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

export default View