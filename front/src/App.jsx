import React, {useState} from 'react';
import {ThemeProvider, createTheme} from '@mui/material/styles';
import {CssBaseline} from '@mui/material';
import {BrowserRouter, Routes, Route, Link} from 'react-router-dom';
import SignIn from './views/Signin';
import SignUp from './views/signup';
import Home from './views/Home';

const theme = createTheme({
    palette: {
        primary: {
            main: '#2196F3',
        },
        secondary: {
            main: '#FF4081',
        },
        info: {
            main: '#69ff40',
        },
    },
});

const App = () => {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline/>
            <BrowserRouter>
                <Routes>
                    <Route path="/sign-up" element={<SignUp/>}/>
                    <Route path="/" element={<SignIn/>}/>
                    <Route path="/home" element={<Home/>}/>
                </Routes>
            </BrowserRouter>
        </ThemeProvider>
    );
}

export default App;