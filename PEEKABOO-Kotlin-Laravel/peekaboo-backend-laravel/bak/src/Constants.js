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
    width: 100,
  },
];
export const infrasColumns = [
  {
    field: "latitude",
    headerName: "Latitude",
    width: 120,
    
  },
  {
    field: "longitude",
    headerName: "Longitude",
    width: 120,
  },

  {
    field: "type",
    headerName: "Type",
    width: 100,
  },
];
export const falseAlarmsColumns = [
  // {
  //   field: "user",
  //   headerName: "User",
  //   width: 230,
  //   renderCell: (params) => {
  //     return (
  //       <div className="cellWithImg">
  //         <img className="cellImg" src={params.row.picture ? params.row.picture : profile_icon} alt=""/>
  //         {params.row.firstname}{" "}{params.row.lastname}
  //       </div>
  //     );
  //   },
  // },
  {
    field: "latitude",
    headerName: "Latitude",
    width: 120,
    
  },
  {
    field: "longitude",
    headerName: "Longitude",
    width: 120,
  },

  {
    field: "type",
    headerName: "Type",
    width: 100,
  },
];




export const base_url = "http://192.168.0.101:8000/api/v1/admin";
