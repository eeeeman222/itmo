import React, {useEffect, useState} from "react";
import HomePages from "./pages/HomePage/HomePages";
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import axios from "axios";
import LoginPage from "./pages/LoginPage/LoginPage";

const App = () => {
    const [isAuthenticated, setIsAuthenticated] = React.useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const checkAuth = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/auth/status");
                console.log(response.data.auth);
                setIsAuthenticated(response.data.auth);
            } catch (error) {
                console.error("Ошибка аунтификации", error);
                setIsAuthenticated(false);
            } finally {
                setLoading(false);
            }
        };
        checkAuth();
    }, []);

    const login = () => setIsAuthenticated(true);
    const logout = () => setIsAuthenticated(false);


    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage onLogin={login} />} />
                <Route path="/home" element={isAuthenticated ? <HomePages onLogout={logout} /> : <Navigate to="/login" />} />
                <Route path="*" element={<Navigate to="/login" />} />
            </Routes>
        </Router>
    );
}

export default App;
