const api = {
        sendPoint: async (data) => {
            try {
                const token = localStorage.getItem("authToken");
                console.log(JSON.stringify({x: data.x, y: data.y, r: data.radius}));
                const response = await fetch('http://localhost:8080/api/points', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`  // Добавляем токен в заголовок
                    },
                    body: JSON.stringify({x: data.x, y: data.y, r: data.radius}),
                });

                if (!response.ok) {
                    throw new Error(`Ошибка записи`);
                }
                console.log(response)
                return await response.json();
            } catch (error) {
                console.error('Ошибка при отправке точки:', error);
                throw error;
            }
        },

        fetchPoints: async () => {
            try {
                const token = localStorage.getItem("authToken");
                const res = await fetch('http://localhost:8080/api/points', {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                if (!res.ok) {
                    throw new Error('Ошибка получения');
                }
                console.log(res)
                return await res.json();
            } catch (error) {
                console.error('Ошибка при получении точек:', error);
                throw error;
            }
        },

    deletePoints: async () => {
        try {
            const token = localStorage.getItem("authToken");
            const res = await fetch('http://localhost:8080/api/points', {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (!res.ok) {
                throw new Error('Ошибка удаления');
            }
        } catch (error) {
            console.error('Ошибка при получении точек:', error);
            throw error;
        }
    },

    reg: async (data) => {
        try {
            const res = await fetch('http://localhost:8080/api/auth/reg', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({username: data.username, password: data.password}),
            });
            if (!res.ok) {
                throw new Error('Ошибка регистрации');
            }
        } catch (error) {
            console.error('Ошибка при реге', error);
            throw error;
        }
    }
};

export default api
