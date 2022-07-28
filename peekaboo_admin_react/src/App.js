import { Routes, Route } from "react-router-dom";
import "./App.scss";
import Home from "./pages/home/Home";
import Map from "./pages/map/Map";
import Login from "./pages/login/Login";
import View from "./pages/view/View";
import Add from "./pages/add/Add";
import List from "./pages/list/List";
import Navbar from "./components/navbar/Navbar";
import Sidebar from "./components/sidebar/Sidebar";

function App() {
  return (
      <div className="App">
        <Sidebar />
        <div className="container">
          <Navbar />
          <Routes>
            <Route index element={<Home />} />
            <Route path="login" element={<Login />} />
            <Route path="map" element={<Map />} />
            <Route path="users">
              <Route index element={<List />} />
              <Route path=":userId" element={<View />} />
              <Route
                path="add"
                element={<Add />}
              />
            </Route>
          </Routes>
        </div>
      </div>
  );
}

export default App;
