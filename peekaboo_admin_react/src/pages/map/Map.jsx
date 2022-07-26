import { GoogleMap,Marker,MarkerClusterer,useLoadScript } from "@react-google-maps/api"
import "./map.scss"
import mapStyles from "../../mapStyles"
import React ,{ useState , useCallback ,useRef } from "react"
const libraries = ["places"]
const mapContainerStyle = {
    width : "80vw",
    height : "80vh"
}
const center ={
    lat:43.653225,
    lng:-79.383186,
}
const options ={
    styles : mapStyles
}

const Map = () => {
    const [markers , setMarkers]=useState([])
    const mapRef = React.useRef();
    const onMapLoad = React.useCallback((map)=>{
        mapRef.current = map ;
    })

    const onMapClick = React.useCallback((event)=>
    setMarkers((current) => [
        ...current,
        {
            lat : event.latLng.lat(),
            lng : event.latLng.lng(),
            time : new Date()
        }
    ]))

    const {isLoaded,loadError } = useLoadScript({
        googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
        libraries
    });

    if(loadError) return "Error loading maps";
    if(!isLoaded) return "Loading maps";


    return (<div className="map">
        <GoogleMap 
        mapContainerStyle={mapContainerStyle}
        zoom={8}
        center={center}
        options={options}
        onClick={onMapClick}
        onLoad={onMapLoad}
        >
            {markers.map((marker) => (
                <Marker
                key={marker.time.toISOString()}
                position={{lat :marker.lat, lng :marker.lng}}/>

            ))}</GoogleMap></div>)


}

export default Map