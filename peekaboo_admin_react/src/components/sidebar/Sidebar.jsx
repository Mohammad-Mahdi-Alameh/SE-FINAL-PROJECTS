import "./sidebar.scss";
const Sidebar = () => {
    return (
        <div className="sidebar">
            <div className="top">
                <span className="logo">PEEKABOO</span>
            </div>
            <hr />
            <div className="center">
                <ul>

                    <p className="title">MANAGE</p>
                    <li>
                        <span>Users</span>
                    </li>

                    <li>
                        <span>MAP</span>
                    </li>
                    
                    <p className="title">USER</p>
                    <li>
                        <span>Profile</span>
                    </li>
                    <li>
                        <span>Logout</span>
                    </li>
                </ul>
            </div>
        </div>
    );
};

export default Sidebar;
