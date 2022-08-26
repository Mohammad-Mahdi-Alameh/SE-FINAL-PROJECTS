import "./false-alarms.scss"
import { DataGrid } from "@mui/x-data-grid";
import { falseAlarmsColumns } from "../../Constants";
const FalseAlarms = () => {
    let navigate=useNavigate();
    const [falseAlarms, setFalseAlarms] = useState([]);
  
    const actionColumn = [
      {
        field: "action",
        headerName: "Show On Map",
        width: 200,
        renderCell: (params) => {
          return (
            <div className="cellAction">
              <div className="rejectButton"
                onClick={() =>handleReject(params.row.id)}>
                    Reject</div>
              <div
                className="acceptButton"
                onClick={() => handleAccept(params.row.id)}>
                Accept
              </div>
            </div>
          );
        },
      },
    ];
  
  
    const handleAccept = async (id) => {
        const res = await fetch(base_url + "/delete_infra/" + id, {
          method: "PUT",
        });
        const result = await res.json();
        if (result.message == "Deleted successfully") {
          setUsers(users.filter((item) => item.id !== id));
        }
    
      };
    const handleReject = async (id) => {
        const res = await fetch(base_url + "/reset_infra_status/" + id, {
          method: "PUT",
        });
        const result = await res.json();
        if (result.message == "Reseted successfully") {
          setUsers(users.filter((item) => item.id !== id));
        }
    
      };

    const getFalseAlarms = async () => {
      const res = await fetch(base_url + "/false_alarms");
      const data = await res.json();
      return data;
    };
  
  
  
  
    useEffect(() => {
      const getData = async () => {
        const falseAlarmsFromServer = await getFalseAlarms();
        setFalseAlarms(falseAlarmsFromServer);
      };
      getData();
    }, []);
    return (<>
      {falseAlarms.length>0 && <div className="table">
        <div className="tableTitle">
         ////
        </div>
        <DataGrid
          className="datagrid"
          rows={users}
          columns={falseAlarmsColumns.concat(actionColumn)}
        //   columns={userColumns.concat(actionColumn)}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection
        />
      </div>}
      </>
    );
}

export default FalseAlarms