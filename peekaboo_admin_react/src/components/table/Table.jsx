import "./table.scss";
import { useState, useEffect } from "react";
import{Link} from  "react-router-dom";
import { DataGrid } from "@mui/x-data-grid";
import { userColumns } from "../../datatablesource";


const Table = () => {
  const [users, setUsers] = useState([]);
  const base_url = "http://192.168.0.100:8000/api/v1/admin";

  const actionColumn = [
    {
      field: "action",
      headerName: "Action",
      width: 200,
      renderCell: (params) => {
        return (
          <div className="cellAction">
              <div className="viewButton">View</div>
            <div
              className="deleteButton"
            >
              Delete
            </div>
          </div>
        );
      },
    },
  ];
 

  const getUsers = async () => {
    const res = await fetch(base_url + "/users");
    const data = await res.json();
    return data;
  };

  useEffect(() => {
    const getData = async () => {
      const usersFromServer = await getUsers();
      setUsers(usersFromServer);
    };
    getData();
  }, []);
  return (
    <div className="table">
    <div className="tableTitle">
      Add New User
      <Link to="/users/add" className="link">
        Add New
      </Link>
    </div>
    <DataGrid
      className="datagrid"
      rows={users}
      columns={userColumns.concat(actionColumn)}
      pageSize={9}
      rowsPerPageOptions={[9]}
      checkboxSelection
    />
  </div>
  );
};

export default Table;
