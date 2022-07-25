import "./sidebar.scss";
const Sidebar = () => {
    return (
        <div className="sidebar">
            <div >
                <span >PEEKABOO</span>
            </div>
            <hr />
            <div className="center">
                <ul>
                    <p>MAIN</p>
                    <p>LISTS</p>
                    <li>
                        <span>Users</span>
                    </li>

                    <p>MAP</p>
                    <li>
                        <span>Map</span>
                    </li>


                    <p>USER</p>

                    <li>
                        <span>Logout</span>
                    </li>
                </ul>
            </div>
        </div>
    );
};

export default Sidebar;
