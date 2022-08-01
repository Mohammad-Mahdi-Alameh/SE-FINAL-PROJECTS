import "./table.scss";
import { useState, useEffect } from "react";
import { useNavigate , Link } from "react-router-dom";
import { DataGrid } from "@mui/x-data-grid";
import { base_url , userColumns } from "../../Constants";
import axios from "axios";


const Table = () => {
  let navigate=useNavigate();
  const [users, setUsers] = useState([]);



  const handleDelete = async (id) => {
    const res = await axios.put(base_url + "/admin/delete_user/" + id);
    const result = await res.data;
    if (result.message == "Deleted successfully") {
      setUsers(users.filter((item) => item.id !== id));
    }

  };

  const actionColumn = [
    {
      field: "action",
      headerName: "Action",
      width: 200,
      renderCell: (params) => {
        return (
          <div className="cellAction">
            <div className="viewButton"
              onClick={() =>navigate('/users/'+params.row.id)}>View</div>
            <div
              className="deleteButton"
              onClick={() => handleDelete(params.row.id)}
            >
              Delete
            </div>
          </div>
        );
      },
    },
  ];


  const getUsers = async () => {
    const res = await axios.get(base_url + "/admin/users");
    const data = await res.data;
    return data;
  };




  useEffect(() => {
    const getData = async () => {
      const usersFromServer = await getUsers();
      setUsers(usersFromServer);
    };
    getData();
  }, []);
  return (<>
    {users.length>0 && <div className="table">
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
        pageSize={5}
        rowsPerPageOptions={[5]}
        checkboxSelection
      />
    </div>}
    </>
  );
};

export default Table;
