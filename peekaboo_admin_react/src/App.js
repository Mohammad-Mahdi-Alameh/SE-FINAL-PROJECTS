import { BrowserRouter } from "react-router-dom";
import Home from "./pages/home/Home";
import Map from "./pages/map/Map";
import Login from "./pages/login/Login";
import View from "./pages/view/View";
import Add from "./pages/add/Add";
import List from "./pages/list/List";

function App() {
  return (
    <div className="App">
     <BrowserRouter>
     <Routes>
      <Route index element={<Home/>}/>
      <Route path="login" element={<Login/>}/>
      <Route path="map" element={<Map/>}/>
      <Route path="users" >
          <Route path="list" element={<List/>}/>
          <Route path="view" element={<View/>}/>
          <Route path="add" element={<Add/>}/>
      </Route>
     </Routes>
     </BrowserRouter>
    </div>
  );
}

export default App;
