//React
import React from 'react';
//Style File
import './index.scss';
//React Router Dom
import {BrowserRouter as Router} from 'react-router-dom';
//React Dom
import ReactDOM from 'react-dom/client';
//App
import App from './App';
//Report Web Vitals
import reportWebVitals from './reportWebVitals';
//Axios
import axios from 'axios';
axios.defaults.baseURL = 'http://192.168.0.101:8000/api/v1/admin'
axios.defaults.headers.common['Content-Type'] = 'application/json'
axios.defaults.headers.common['Accept'] = 'application/json'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Router>
    <App />
    </Router>

);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

