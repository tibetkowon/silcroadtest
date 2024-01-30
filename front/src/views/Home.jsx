import React, {useState} from 'react';
import {AppBar, Toolbar, Typography} from "@mui/material";
import Sidebar from "./Sidebar.jsx";

const Home = () => {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };

    return (
        <>
            <AppBar position="fixed">
                <Toolbar>
                    <Sidebar isOpen={isSidebarOpen} toggleDrawer={toggleSidebar}/>
                    <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                        테스트 화면
                    </Typography>
                </Toolbar>
            </AppBar>
            <div>
                <h1>Home 페이지</h1>
            </div>
        </>
    );
}

export default Home;