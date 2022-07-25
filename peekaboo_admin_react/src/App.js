import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import Home from "./pages/home/Home";
import Map from "./pages/map/Map";
import Login from "./pages/login/Login";
import View from "./pages/view/View";
import Add from "./pages/add/Add";
import List from "./pages/list/List";

function App() {
  return (
    <BrowserRouter>
      <div className="App">
          <Routes>
            <Route index element={<Home />} />
            <Route path="login" element={<Login />} />
            <Route path="map" element={<Map />} />
            <Route path="users">
              <Route index element={<List />} />
              <Route path=":userId" element={<View />} />
              <Route
                path="new"
                element={<Add />}
              />
            </Route>
          </Routes>
        </div>
    </BrowserRouter>
  );
}

export default App;
