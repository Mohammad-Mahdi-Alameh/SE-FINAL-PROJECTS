import "./table.scss";
import { useState, useEffect } from "react";
import { DataGrid } from "@mui/x-data-grid";


const Table = () => {
  const [users, setUsers] = useState([]);
  const base_url = "http://192.168.0.100:8000/api/v1/admin";

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
    Users
  </div>
  );
};

export default Table;
