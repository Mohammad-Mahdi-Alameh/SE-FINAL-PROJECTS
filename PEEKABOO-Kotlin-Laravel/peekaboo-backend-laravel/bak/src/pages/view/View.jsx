import "./view.scss"
import { useState, useEffect } from "react";
import { base_url } from "../../Constants";
import { DataGrid } from "@mui/x-data-grid";


const View = () => {
    let [info, setInfo] = useState([]);
    const [infras, setInfras] = useState([]);
    let urlString = window.location.href;
    // let navigate=useNavigate();
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

   
  
    // const actionColumn = [
    //   {
    //     field: "action",
    //     headerName: "Show On Map",
    //     width: 200,
    //     renderCell: (params) => {
    //       return (
    //         <div className="cellAction">
    //           <div className="viewButton"
    //             onClick={() =>navigate('/maps/'+params.row.id)}>View</div>
    //           <div
    //             className="deleteButton"
    //             onClick={() => handleDelete(params.row.id)}
    //           >
    //             Delete
    //           </div>
    //         </div>
    //       );
    //     },
    //   },
    // ];
  
  //getting infras reported by this user
    const getInfras = async () => {
      const res = await fetch(base_url + "/get_all_infras/" + user_id);
      const data = await res.json();
      return data;
    };
  
  
  
  
    useEffect(() => {
      const getData = async () => {
        const infrasFromServer = await getInfras();
        setInfras(infras);
      };
      getData();
    }, []);
    // return (<div>
    //   {infras.length>0 && <div className="table">
    //     <div className="tableTitle">
    //       ///
    //     </div>
    //     <DataGrid
    //       className="datagrid"
    //       rows={infras}
    //       columns={infrasColumns}
    //       // columns={userColumns.concat(actionColumn)}
    //       pageSize={5}
    //       rowsPerPageOptions={[5]}
    //       checkboxSelection
    //     />
    //   </div>}
    //   </div>
    // );



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
    <div>
      {infras.length>0 && <div className="table">
        <div className="tableTitle">
          ///
        </div>
        <DataGrid
          className="datagrid"
          rows={infras}
          columns={infrasColumns}
          // columns={userColumns.concat(actionColumn)}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection
        />
      </div>}
      </div>
  </div>)


}

export default View