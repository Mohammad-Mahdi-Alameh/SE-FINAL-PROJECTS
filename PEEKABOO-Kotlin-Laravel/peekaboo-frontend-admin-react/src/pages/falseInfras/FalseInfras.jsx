//React Hooks
import { useState, useEffect } from "react";
//Style File
import "../../index.scss"
//Components
import { DataGrid } from "@mui/x-data-grid";
//Utilities
import { falseInfrasColumns , actionColumn } from "../../Utilities/Utilities";
//Axios
import axios from "axios";

//Using Infras as a shortcut of Infrastructural problems//

const FalseInfras = () => {
  //useState()
  const [falseInfras, setFalseInfras] = useState([]);
  const [falseInfrasLoading, setFalseInfrasLoading] = useState(false)
  ////

  //useEffect()
  useEffect(() => {
    const getData = async () => {
      const falseInfrasFromServer = await getFalseInfras();
      setFalseInfras(falseInfrasFromServer);
      setFalseInfrasLoading(true);
    };
    getData();
  }, []);
  ////

  //functions and handlers
  const getFalseInfras = async () => {
    const res = await axios.get( "/false_infras");
    const data = await res.data;
    return data;
  };
  const handleAccept = async (id) => {
    try {
      const res = await axios.put( "/fix_infra/" + id);
      setFalseInfras(falseInfras.filter((item) => item.id !== id));
    } catch (error) {
      console.log(error)
    };
  }
  const handleReject = async (id) => {
    try {
      const res = await axios.put( "/reject_false_infra/" + id);
      setFalseInfras(falseInfras.filter((item) => item.id !== id));
    } catch (error) {
      console.log(error)
    };

  };
  ////

  return (<>
    {falseInfrasLoading && <div className="table">
      <div className="tableTitle">
        False Infras
         
      </div>
      <DataGrid
        className="datagrid"
        rows={falseInfras}
        columns={falseInfrasColumns.concat(actionColumn(handleReject,handleAccept,"Reject","Accept"))}
        pageSize={5}
        rowsPerPageOptions={[5]}
        checkboxSelection
      />
    </div>}
  </>
  );
}

export default FalseInfras;