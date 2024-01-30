import React, { useState } from 'react';
import { Button, TextField, Typography, Container } from '@mui/material';
import {useNavigate} from 'react-router-dom';

const SignUp = () => {
    const navigate = useNavigate();
    const [userId, setuserId] = useState('');
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [desc, setDesc] = useState('');

    const handleSignUp = () => {
        if (password !== confirmPassword) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        fetch('http://localhost:8080/sign-up', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ userId, name, password, desc }),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log('서버 응답:', data);

                // 서버 응답에 따른 알림 띄우기
                if (data.code === '0000') {
                    navigate('/');
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
                회원가입
            </Typography>
            <form>
                <TextField
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    id="email"
                    label="아이디"
                    name="userId"
                    autoComplete="userId"
                    autoFocus
                    value={userId}
                    onChange={(e) => setuserId(e.target.value)}
                />
                <TextField
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    id="name"
                    label="이름"
                    name="name"
                    autoComplete="name"
                    autoFocus
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
                <TextField
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    name="password"
                    label="비밀번호"
                    type="password"
                    id="password"
                    autoComplete="new-password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <TextField
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    name="confirmPassword"
                    label="비밀번호 확인"
                    type="password"
                    id="confirmPassword"
                    autoComplete="new-password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />
                <TextField
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    name="desc"
                    label="기타 설명"
                    id="desc"
                    autoComplete="desc"
                    value={desc}
                    onChange={(e) => setDesc(e.target.value)}
                />
                <Button type="button" fullWidth variant="contained" color="primary" onClick={handleSignUp}>
                    회원가입
                </Button>
            </form>
        </Container>
    );
};

export default SignUp;