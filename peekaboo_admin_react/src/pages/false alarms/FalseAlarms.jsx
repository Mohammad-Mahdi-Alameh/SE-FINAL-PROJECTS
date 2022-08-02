//React Hooks
import { useState, useEffect } from "react";
//Style File
import "../../index.scss"
//Components
import { DataGrid } from "@mui/x-data-grid";
//Constants
import { falseInfrasColumns, base_url } from "../../Constants";
//Axios
import axios from "axios";

//Using Infras as a shortcut of Infrastructural problems//

const FalseAlarms = () => {
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

  //Constants
  const actionColumn = [
    {
      field: "action",
      headerName: "Action",
      width: 200,
      renderCell: (params) => {
        return (
          <div className="cellAction">
            <div className="negativeButton"
              onClick={() => handleReject(params.row.id)}>
              Reject</div>
            <div
              className="positiveButton"
              onClick={() => handleAccept(params.row.id)}>
              Accept
            </div>
          </div>
        );
      },
    },
  ];
  ////

  //functions and handlers
  const getFalseInfras = async () => {
    const res = await axios.get(base_url + "/false_infras");
    const data = await res.data;
    return data;
  };
  const handleAccept = async (id) => {
    try {
      const res = await axios.put(base_url + "/fix_infra/" + id);
      setFalseInfras(falseInfras.filter((item) => item.id !== id));
    } catch (error) {
      console.log(error)
    };
  }
  const handleReject = async (id) => {
    try {
      const res = await axios.put(base_url + "/reject_false_infra/" + id);
      setFalseInfras(falseInfras.filter((item) => item.id !== id));
    } catch (error) {
      console.log(error)
    };

  };
  ////

  return (<>
    {falseInfrasLoading && <div className="table">
      <div className="tableTitle">
         ////
      </div>
      <DataGrid
        className="datagrid"
        rows={falseInfras}
        columns={falseInfrasColumns.concat(actionColumn)}
        pageSize={5}
        rowsPerPageOptions={[5]}
        checkboxSelection
      />
    </div>}
  </>
  );
}

export default FalseAlarms;