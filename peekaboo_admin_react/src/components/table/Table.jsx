//React Hooks
import { useState, useEffect } from "react";
//React router Dom
import { useNavigate , Link } from "react-router-dom";
//Components
import { DataGrid } from "@mui/x-data-grid";
//Utilities
import { userColumns ,actionColumn } from "../../Utilities";
//Constants
import { base_url  } from "../../Utilities/Constants";
//axios
import axios from "axios";


const Table = () => {
  let navigate=useNavigate();
  const [users, setUsers] = useState([]);


  //function to delete user from the database and remove it from the datagrid
  const handleDelete = async (id) => {
  try{  const res = await axios.put(base_url + "/delete_user/" + id);
    const result = await res.data;
    console.log(result.message);
      setUsers(users.filter((item) => item.id !== id));
      console.log(users);

      // /
    }
    catch(err){
      console.log(err)
    }

  };

  //function to navigate to the view user page
  const handleView = (id) => {
    navigate('/users/'+id);
  }

//function to get all users from database
  const getUsers = async () => {
    const res = await axios.get(base_url + "/users");
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
        Users
        {/* <Link to="/users/add" className="link">
          Add New
        </Link> */}
      </div>
      <DataGrid
        className="datagrid"
        rows={users}
        columns={userColumns.concat(actionColumn(handleDelete,handleView,"Delete","View"))}
        pageSize={5}
        rowsPerPageOptions={[5]}
        checkboxSelection
      />
    </div>}
    </>
  );
};

export default Table;
