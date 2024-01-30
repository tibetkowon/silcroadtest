import { createTheme } from '@mui/material/styles';

const theme = createTheme({
    palette: {
        primary: {
            main: '#1976D2',
        },
        secondary: {
            main: '#FF4081',
        },
        background: {
            default: '#f0f0f0',
            paper: '#ffffff'
        }
    },
    typography: {
        fontFamily: 'Roboto, sans-serif',
    },
});

export default theme;