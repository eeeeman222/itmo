import React, { useState } from 'react';
import axios from 'axios';
import {BrowserRouter as Router, Route, Routes, Navigate, useNavigate} from 'react-router-dom';
import api from "../../PostProcessor";

function LoginPage({ onLogin }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();
    const handleSubmit = async (event) => {
        event.preventDefault();

        const action = event.nativeEvent.submitter.value;

        if (action === "login") {
            try {
                const response = await axios.post("http://localhost:8080/api/auth/login", { username, password });
                const token = response.data.token;
                localStorage.setItem("authToken", token);
                console.log(token)
                onLogin();
                navigate("/home");
            } catch (error) {
                alert("Invalid credentials");
            }
        }

        else {
            const data = {
                username: username,
                password: password
            };
            await api.reg(data);
        }
    };


    const onClick = async (data) => {
        await api.reg(data)
    }

    return (
        <div>
            <h1> Войдите, чтобы начать резню:)</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>имя:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label>код слово:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit" name="action" value="login">Login</button>
                <button type="submit" name="action" value="Sign">Sign Up</button>
            </form>
        </div>
    );
}

export default LoginPage;
