import { GoogleMap,useLoadScript } from "@react-google-maps/api"
import "./map.scss"
const libraries = ["places"]
const mapContainerStyle = {
    width : "80vw",
    height : "80vh"
}
const center ={
    lat:43.653225,
    lng:-79.383186,
}
const Map = () => {

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
        ></GoogleMap></div>)


}

export default Map