import "./false-alarms.scss"
import { DataGrid } from "@mui/x-data-grid";
import {  base_url } from "../../Constants";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const FalseAlarms = () => {
    const [falseInfras, setFalseInfras] = useState([]);
  
 const getFalseInfras = async () => {
      const res = await axios.get(base_url + "/false_infras");
      const data = await res.data;
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
      {falseInfras.length>0 && <div className="table">
        <div className="tableTitle">
         ////
        </div>
        <DataGrid
          className="datagrid"
          rows={falseInfras}
          columns={}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection
        />
      </div>}
      </>
    );
}

export default FalseAlarms;