import {mapStyles} from "./mapStyles"
export const libraries = ["places"]
export const mapContainerStyle = {
    width: "100%",
    height: "100vh"
}
export const options = {
    styles: mapStyles,
    disableDefaultUI: true,
    zoomControl: true,
}