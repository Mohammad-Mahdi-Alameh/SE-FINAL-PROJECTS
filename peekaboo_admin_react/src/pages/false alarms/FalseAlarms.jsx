import "./false-alarms.scss"
import { DataGrid } from "@mui/x-data-grid";
import { falseInfrasColumns , base_url } from "../../Constants";
import { useState, useEffect } from "react";
import axios from "axios";

const FalseAlarms = () => {
    const [falseInfras, setFalseInfras] = useState([]);
  
   
  
  
    const handleAccept = async (id) => {
        const res = await axios.put(base_url + "/delete_infra/" + id);
        const result = await res.data;
        if (result.message == "Deleted successfully") {
          setFalseInfras(falseInfras.filter((item) => item.id !== id));
        }
    
      };
    const handleReject = async (id) => {
        const res = await axios.put(base_url + "/reset_infra_status/" + id);
        const result = await res.data;
        if (result.message == "Reseted successfully") {
          setFalseInfras(falseInfras.filter((item) => item.id !== id));
        }
    
      };

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
          columns={falseInfrasColumns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection
        />
      </div>}
      </>
    );
}

export default FalseAlarms;