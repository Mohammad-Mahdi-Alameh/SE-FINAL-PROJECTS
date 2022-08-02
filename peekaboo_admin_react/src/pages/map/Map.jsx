import { GoogleMap, InfoWindow, Marker, MarkerClusterer, useLoadScript } from "@react-google-maps/api"
import "../../index.scss"
import SearchBar from "../../components/SearchBar";
import compass from "../../compass.svg"
import mapStyles from "../../mapStyles"
import React, { useState, useCallback, useRef, useEffect } from "react"
import { formatRelative } from "date-fns";
import { base_url } from "../../Constants";
import "@reach/combobox/styles.css";
import axios from "axios";

////
const libraries = ["places"]
const mapContainerStyle = {
    width: "100%",
    height: "100vh"
}
const options = {
    styles: mapStyles,
    disableDefaultUI: true,
    zoomControl: true,
}

const Map = () => {
    const [markers, setMarkers] = useState([])
    const [infras, setInfras] = useState([])
    const [selected, setSelected] = useState(null)
    const [clicked, setClicked] = useState(null)

    const mapRef = React.useRef();
    const onMapLoad = React.useCallback((map) => {
        mapRef.current = map;
        getLiveLocation();
    });

    //Infras : Infrastructural problems
    const getAllInfras = async () => {
        const res = await axios.get(base_url + "/get_all_infras/0");
        const data = await res.data;
        return data;
    };
   
    useEffect(() => {
        const getData = async () => {
            const infrasFromServer = await getAllInfras();
            setInfras(infrasFromServer);
            ///////
        };
        getData();
    }, []);

    useEffect(() => {

        // console.log(infras)
        infras.map(infra => {
            setMarkers((current) => [
                ...current,
                {
                    id: infra.id,
                    lat: infra.latitude,
                    lng: infra.longitude,
                    type: infra.type,
                    date: infra.created_at,
                    by: infra.user_id

                    /////
                    // time: new Date()
                }
            ])
        })


    }, [infras]);

    const handleDelete = async (id) => {
        try{  const res = await axios.put(base_url + "/fix_infra/" + id);
            setInfras(infras.filter((item) => item.id !== id));
            setMarkers(markers.filter((item) => item.id !== id));
            setSelected(null);
          }
          catch(err){
            console.log(err)
          }
      
        };


    function getLiveLocation() {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                panTo({
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                });
            },
            () => null
        );
    }

    function handleDrag(marker){
            // console.log(marker.lat)
    
    ondragend = (event) =>{
        console.log(event)
    }
    }
    const panTo = React.useCallback(({ lat, lng }) => {
        mapRef.current.panTo({ lat, lng });
        mapRef.current.setZoom(14);
    }, []);

    const onMapClick = React.useCallback((event) =>
        // setMarkers((current) => [
        //     ...current,
        //     {
        //         lat: event.latLng.lat(),
        //         lng: event.latLng.lng(),
        //         time: new Date()
        //     }
        // ])
        setClicked(event)
        );

    const { isLoaded, loadError } = useLoadScript({
        googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
        libraries
    });

    if (loadError) return "Error loading maps";
    if (!isLoaded) return "Loading maps";

    function Locate({ panTo }) {
        return (<button className="locate" onClick={() => {
            getLiveLocation();
        }}
        >
            <img src={compass} alt="" /></button>
        );
    }


    return (<div className="map">

        <GoogleMap
            mapContainerStyle={mapContainerStyle}
            zoom={8}
            options={options}
            onClick={onMapClick}
            onLoad={onMapLoad}
        >

            {markers.map((marker,index) => (
                <Marker
                    key={index}
                    position={{ lat: marker.lat, lng: marker.lng }}
                    onClick={() => {
                        setSelected(marker);
                        setClicked(null)
                    }} 
                  />

            ))}
            {
                selected ? (<InfoWindow position={{ lat: selected.lat, lng: selected.lng }} onCloseClick={() => { setSelected(null) }}>
                    <div>
                        <p> {selected.type}</p>

                        <p> {selected.by}</p>
                        <button onClick={()=>handleDelete(selected.id)}>Delete</button>
                    </div>

                </InfoWindow>

                ) : null}
            {
                clicked ? (<InfoWindow position={{ lat: clicked.latLng.lat(), lng: clicked.latLng.lng() }} onCloseClick={() => { setClicked(null) }}>
                 <div>
                            <input type={"radio"} name={"infra_type"} value={"Hole"}/>
                            <label for={"Hole"}>Hole</label><br/>
                            <input type={"radio"} name={"infra_type"} value={"Bump"}/>
                            <label for={"Bump"}>Bump</label><br/>
                            <input type={"radio"} name={"infra_type"} value={"Turn"}/>
                            <label for={"Turn"}>Turn</label><br/>
                            <input type={"radio"} name={"infra_type"} value={"Blockage"}/>
                            <label for={"Blockage"}>Blockage</label><br/>
                            <input type={"submit"} value="Add"  />
                    </div>

                </InfoWindow>

                ) : null}





        </GoogleMap>
        <SearchBar panTo={panTo} />
        <Locate panTo={panTo} />
    </div>)


}

export default Map;