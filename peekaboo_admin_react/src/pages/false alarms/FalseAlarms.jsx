import "./false-alarms.scss"
import { DataGrid } from "@mui/x-data-grid";
import { falseInfrasColumns , base_url } from "../../Constants";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const FalseAlarms = () => {
    const [falseInfras, setFalseInfras] = useState([]);
  

    const getFalseInfras = async () => {
      const res = await fetch(base_url + "/false_infras");
      const data = await res.json();
      return data;
    };
  
  
  
  
    useEffect(() => {
      const getData = async () => {
        const falseInfrasFromServer = await getFalseInfras();
        setFalseInfras(falseInfrasFromServer);
      };
      getData();
    }, []);
    return (<>
      </>
    );
}

export default FalseAlarms