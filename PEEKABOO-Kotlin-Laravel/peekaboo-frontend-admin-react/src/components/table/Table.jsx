//React Hooks
import { useState, useEffect } from "react";
//React Router Dom
import { useNavigate , Link } from "react-router-dom";
//Components
import { DataGrid } from "@mui/x-data-grid";
//Utilities
import { userColumns ,actionColumn } from "../../Utilities/Utilities";
//axios
import axios from "axios";


const Table = () => {
  //useState()
  const [users, setUsers] = useState([]);
  const [usersLoading, setUsersLoading] = useState(false)
  ////
  
  //useNavigate()
  let navigate=useNavigate();
  ////

  //useEffect()
  useEffect(() => {
    const getData = async () => {
      const usersFromServer = await getUsers();
      setUsers(usersFromServer);
      setUsersLoading(true)
    };
    getData();
  }, []);
  ////


  //Functions and handlers
  //function to delete user from the database and remove it from the datagrid
  const handleDelete = async (id) => {
  try{  const res = await axios.put("/delete_user/" + id);
    const result = await res.data;
      setUsers(users.filter((item) => item.id !== id));
    }
    catch(err){
      //
    }

  };

  //function to navigate to the view user page
  const handleView = (id) => {
    navigate('/users/'+id);
  }

//function to get all users from database
  const getUsers = async () => {
    const res = await axios.get("/users");
    const data = await res.data;
    return data;
  };

  return (<>
    {usersLoading && <div className="table">
      <div className="tableTitle">
        Users
      </div>
      <DataGrid
        className="datagrid"
        rows={users}
        columns={userColumns.concat(actionColumn(handleDelete,handleView,"Delete","View"))}
        pageSize={3}
        rowsPerPageOptions={[3]}
        checkboxSelection
      />
    </div>}
    </>
  );
};

export default Table;
