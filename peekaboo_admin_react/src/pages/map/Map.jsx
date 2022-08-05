//React Hooks
import React, { useState, useCallback, useRef, useEffect } from "react"
//Style File
import "../../index.scss"
//Google Maps
import { GoogleMap, InfoWindow, Marker, MarkerClusterer, useLoadScript } from "@react-google-maps/api"
//Components
import SearchBar from "../../components/searchBar/SearchBar";
//Utilities
import { infraDataExtractor, getMarker } from "../../Utilities/Utilities";
//Constants
import { base_url } from "../../Utilities/Constants";
import { libraries, mapContainerStyle, options } from "./mapConstants";
//Axios
import axios from "axios";
//Svg's
import compass from "../../compass.svg"

//Using Infras as a shortcut of Infrastructural problems//

const Map = () => {
    //useState()
    const [markers, setMarkers] = useState([])
    const [infras, setInfras] = useState([])
    const [selected, setSelected] = useState(null)
    const [clicked, setClicked] = useState(null)
    const [infraType, setInfraType] = useState()
    const [lat, setLat] = useState()
    const [lng, setLng] = useState()
    ////

    //axios configuration
    var data = JSON.stringify({
        "latitude": lat,
        "longitude": lng,
        "type": infraType,
        "user_id": 0
    });
    var config = {
        method: 'post',
        url: base_url + '/report',
        headers: {
            'Content-Type': 'application/json'
        },
        data: data
    };
    ////

    //useRef()
    const mapRef = React.useRef();
    ////

    //useCallback()
    const onMapLoad = React.useCallback((map) => {
        mapRef.current = map;
        getLiveLocation();
    });

    const panTo = React.useCallback(({ lat, lng }) => {
        mapRef.current.panTo({ lat, lng });
        mapRef.current.setZoom(14);
    }, []);

    const onMapClick = React.useCallback((event) =>
        setClicked(event)
    );
    ////

    //useEffect()
    useEffect(() => {
        const getData = async () => {
            const infrasFromServer = await getAllInfras();
            setInfras(infrasFromServer);
        };
        getData();
    }, []);

    useEffect(() => {
        infras.map(infra => {
            setMarkers((current) => [
                ...current,
                infraDataExtractor(infra)
            ])
        })
    }, [infras]);
    ////

    //functions and handlers
    const getAllInfras = async () => {
        const res = await axios.get(base_url + "/get_all_infras/0");
        const data = await res.data;
        return data;
    };

    const handleDelete = async (id) => {
        try {
            const res = await axios.put(base_url + "/fix_infra/" + id);
            setInfras(infras.filter((item) => item.id !== id));
            setMarkers(markers.filter((item) => item.id !== id));
            setSelected(null);
        }
        catch (err) {
            console.log(err)
        }
    };

    const handleAdd = async () => {
        if (infraType) {
            axios(config)
                .then(function (response) {
                    const addedInfra = response.data["infra"];
                    setMarkers((current) => [
                        ...current,
                        infraDataExtractor(addedInfra)
                    ]);
                    setClicked(null);
                })
                .catch(function (error) {
                    alert("Adding Infra failed !")
                });
        } else {
            setClicked(null);
        }
    }

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

    function Locate({ panTo }) {
        return (<button className="locate" onClick={() => {
            getLiveLocation();
        }}
        >
            <img src={compass} alt="" /></button>
        );
    }
    ////

    //useLoadScript()
    const { isLoaded, loadError } = useLoadScript({
        googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
        libraries
    });

    if (loadError) return "Error loading maps";
    if (!isLoaded) return "Loading maps";
    ////

    return (<div className="map">

        <GoogleMap
            mapContainerStyle={mapContainerStyle}
            zoom={8}
            options={options}
            onClick={onMapClick}
            onLoad={onMapLoad}
        >

            {markers.map((marker, index) => (
                <Marker
                    key={index}
                    position={{ lat: marker.lat, lng: marker.lng }}
                    onClick={() => {
                        setSelected(marker);
                        setClicked(null)
                    }}
                    icon={
                        {
                            url: getMarker(marker.type),
                            scaledSize: new window.google.maps.Size(27, 37),
                        }
                    }
                />

            ))}
            {
                selected ? (<InfoWindow position={{ lat: selected.lat, lng: selected.lng }}
                    onCloseClick={() => { setSelected(null) }}>
                    <div className="delete-infowindow">
                        <p> {selected.type}</p>

                        <input type={"submit"} value="Fix" onClick={() => handleDelete(selected.id)}/>
                    </div>

                </InfoWindow>

                ) : null}
            {
                clicked ? (<InfoWindow position={{ lat: clicked.latLng.lat(), lng: clicked.latLng.lng() }} onCloseClick={() => { setClicked(null) }}>
                    <div className="infowindow">
                        <form onChange={(e) => {
                            setInfraType(e.target.value);
                            setLat(clicked.latLng.lat());
                            setLng(clicked.latLng.lng());
                        }}>
                            <input type={"radio"} name={"infra_type"} value={"Hole"} />
                            <label for={"Hole"}>Hole</label><br />
                            <input type={"radio"} name={"infra_type"} value={"Bump"} />
                            <label for={"Bump"}>Bump</label><br />
                            <input type={"radio"} name={"infra_type"} value={"Turn"} />
                            <label for={"Turn"}>Turn</label><br />
                            <input type={"radio"} name={"infra_type"} value={"Blockage"} />
                            <label for={"Blockage"}>Blockage</label><br />
                        </form>
                        <input type={"submit"} value="Add" onClick={handleAdd} />
                    </div>

                </InfoWindow>

                ) : null}

        </GoogleMap>
        <SearchBar panTo={panTo} />
        <Locate panTo={panTo} />
    </div>)


}

export default Map;