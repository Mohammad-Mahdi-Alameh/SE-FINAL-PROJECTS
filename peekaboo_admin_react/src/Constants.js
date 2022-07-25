import profile_icon from "./profile_icon.svg"
export const userColumns = [
  { field: "id", headerName: "ID", width: 70 },
  {
    field: "user",
    headerName: "User",
    width: 230,
    renderCell: (params) => {
      return (
        <div className="cellWithImg">
          <img className="cellImg" src={params.row.picture ? params.row.picture : profile_icon} alt=""/>
          {params.row.firstname}{" "}{params.row.lastname}
        </div>
      );
    },
  },
  {
    field: "username",
    headerName: "Username",
    width: 230,
  },

  {
    field: "total_reports",
    headerName: "Total Reports",
    width: 230,
  },
];



export const base_url = "http://192.168.0.100:8000/api/v1/admin";
