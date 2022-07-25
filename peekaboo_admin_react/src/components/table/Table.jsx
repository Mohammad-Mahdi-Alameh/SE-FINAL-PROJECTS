import "./table.scss";
import { useState, useEffect } from "react";
import { useNavigate , Link } from "react-router-dom";
import { DataGrid } from "@mui/x-data-grid";
import { base_url , userColumns } from "../../Constants";


const Table = () => {
  let navigate=useNavigate();
  const [users, setUsers] = useState([]);



  const handleDelete = async (id) => {
    const res = await fetch(base_url + "/delete_user/" + id, {
      method: "PUT",
    });
    const result = await res.json();
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
        pageSize={5}
        rowsPerPageOptions={[5]}
        checkboxSelection
      />
    </div>
  );
};

export default Table;
