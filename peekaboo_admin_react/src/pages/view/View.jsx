import "./view.scss"
import { useState, useEffect } from "react";

const View = () => {
    const base_url = "http://192.168.0.100:8000/api/v1/admin";
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


    
 return (<div className="view">View</div>)

}

export default View