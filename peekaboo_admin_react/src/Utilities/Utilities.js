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
          <img className="cellImg" src={params.row.picture ? params.row.picture : profile_icon} alt="" />
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
    width: 80,
  },

  {
    field: "created_at",
    headerName: "Reported at",
    width: 140,
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

export function infraDataExtractor(infra) {
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

export function getMarker(type) {
  switch (type) {
    case "Hole":
      return "/hole_marker.svg"
    case "Bump":
      return "/bump_marker.svg"
    case "Blockage":
      return "/blockage_marker.svg"
    case "Turn":
      return "/turn_marker.svg"
    default:
      return ""
  }
}

export function actionColumn(negativeFunction,positiveFunction,negativeButtonText,positiveButtonText) {
  return [
    {
      field: "action",
      headerName: "Action",
      width: 200,
      renderCell: (params) => {
        return (
          <div className="cellAction">
            <div className="negativeButton"
              onClick={() => {negativeFunction(params.row.id)}}>
              {negativeButtonText}</div>
            <div
              className="positiveButton"
              onClick={() => {positiveFunction(params.row.id)}}>
              {positiveButtonText}
            </div>
          </div>
        );
      },
    }
  ]
}