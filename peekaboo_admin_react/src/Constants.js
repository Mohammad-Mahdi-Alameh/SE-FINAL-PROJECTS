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

  {
    field: "created_at",
    headerName: "Reported at",
    width: 100,
  },
];
export const falseInfrasColumns = [

  {
    field: "latitude",
    headerName: "Latitude",
    width: 150,
    
  },
  {
    field: "longitude",
    headerName: "Longitude",
    width: 150,
  },

  {
    field: "type",
    headerName: "Type",
    width: 100,
  },
];

export function infraExtractor(infra){
var infraJSON = {

  id: infra.id,
  lat: infra.latitude,
  lng: infra.longitude,
  type: infra.type,
  date: infra.created_at,
  by: infra.user_id
  
}
  return infraJSON;

}




export const base_url = "http://192.168.0.100:8000/api/v1/admin";
