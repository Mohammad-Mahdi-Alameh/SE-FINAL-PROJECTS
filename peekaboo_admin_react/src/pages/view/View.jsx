import "./view.scss"
import { useState, useEffect } from "react";

const View = () => {
    let [info, setInfo] = useState([]);
    let urlString = window.location.href;
    let paramString = urlString.split('?')[1];
    let queryString = new URLSearchParams(paramString);
    for (var pair of queryString.entries()) {
        var user_id = pair[1]
    }



    
 return (<div className="view">View</div>)

}

export default View