import React, {useState} from 'react';
import {Button, TextField, Typography, Container} from '@mui/material';
import {useNavigate} from 'react-router-dom';

const SignIn = () => {
    const navigate = useNavigate();
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');

    const goSignIn = () => {
        navigate("/sign-up");
    }

    const handleLogin = () => {
        if (userId === '' || password === '') {
            alert("아이디/비밀번호를 입력하세요");
            return;
        }
        fetch('http://localhost:8080/sign-in', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ userId, password }),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log('서버 응답:', data);

                // 서버 응답에 따른 알림 띄우기
                if (data.code === '0000') {
                    navigate("/home");
                } else {
                    alert(data.message);
                }
            })
            .catch((error) => {
                console.error('에러 발생:', error);
            });
    };

    return (
        <Container component="main" maxWidth="xs">
            <Typography variant="h5" component="div">
                로그인
            </Typography>
            <form>
                <TextField
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    id="userId"
                    label="아이디"
                    name="userId"
                    autoComplete="userId"
                    autoFocus
                    value={userId}
                    onChange={(e) => setUserId(e.target.value)}
                />
                <TextField
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    name="password"
                    label="비밀번호"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <Button type="button" variant="contained" color="info" onClick={goSignIn}>
                    회원가입
                </Button>
                <Button type="button" variant="contained" color="primary" onClick={handleLogin}>
                    로그인
                </Button>
            </form>
        </Container>
    );
};

export default SignIn;