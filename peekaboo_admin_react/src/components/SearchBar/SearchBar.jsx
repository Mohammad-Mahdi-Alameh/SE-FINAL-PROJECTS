//React
import React from "react"
//Mui Icons
import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
//Use-Places-Autocomplete imports
import usePlacesAutocomplete, { getGeocode, getLatLng } from "use-places-autocomplete";
//Combobox
import { Combobox, ComboboxInput, ComboboxPopover, ComboboxList, ComboboxOption } from "@reach/combobox";
//Combobox Style
import "@reach/combobox/styles.css";

const SearchBar = ({panTo}) => {
    const { ready, value, suggestions: { status, data }, setValue, clearSuggestions } = usePlacesAutocomplete({
        requestOptions: {
            location: {
                lat: () => 43.653225,
                lng: () => -79.383186,
            },
            radius: 200 * 1000,
        }
    })
    return (<div className="wrapper">
        <div className="search"><Combobox
            onSelect={async (address) => {
                setValue(address, false);
                clearSuggestions();
                try {
                    const results = await getGeocode({ address });
                    const { lat, lng } = getLatLng(results[0]);
                    panTo({ lat, lng });
                } catch (error) {
                    console.log("error")
                }
            }} >
            <ComboboxInput value={value} onChange={(e) => {
                setValue(e.target.value);
            }}
                disable={!ready}
                placeholder="Enter an address"
            />
            <ComboboxPopover>
                <ComboboxList>
                    {status === "OK" && data.map(({ id, description }) => (<ComboboxOption key={id} value={description} />))}
                </ComboboxList>
            </ComboboxPopover>

        </Combobox>
            <SearchOutlinedIcon />
        </div>
    </div>)


}
export default SearchBar;
