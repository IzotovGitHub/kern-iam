// login.js - отдельный файл с логикой страницы входа

(function() {
    // Конфигурация
    const API_BASE_URL = '/kern/v1/auth'; // базовый URL для API

    // DOM элементы
    const loginForm = document.getElementById('loginForm');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const rememberCheckbox = document.getElementById('rememberCheckbox');
    const messageBox = document.getElementById('messageBox');
    const togglePasswordBtn = document.getElementById('togglePasswordBtn');
    const toggleIcon = document.getElementById('toggleIcon');
    const forgotLink = document.getElementById('forgotLink');
    const registerLink = document.getElementById('registerLink');
    const loginButton = document.getElementById('loginButton');

    // Состояние загрузки
    let isLoading = false;

    // Функция показа сообщений
    function showMessage(text, type, duration = 5000) {
        messageBox.innerHTML = '';
        const msgDiv = document.createElement('div');
        msgDiv.textContent = text;
        msgDiv.style.padding = '0.75rem';
        msgDiv.style.borderRadius = '0.75rem';
        msgDiv.style.fontWeight = '500';
        msgDiv.style.animation = 'fadeIn 0.3s ease';

        if (type === 'error') {
            msgDiv.style.backgroundColor = '#fee2e2';
            msgDiv.style.color = '#b91c1c';
            msgDiv.style.borderLeft = '4px solid #ef4444';
        } else if (type === 'success') {
            msgDiv.style.backgroundColor = '#e0f2fe';
            msgDiv.style.color = '#075985';
            msgDiv.style.borderLeft = '4px solid #0ea5e9';
        } else if (type === 'warning') {
            msgDiv.style.backgroundColor = '#fef3c7';
            msgDiv.style.color = '#92400e';
            msgDiv.style.borderLeft = '4px solid #f59e0b';
        } else {
            msgDiv.style.backgroundColor = '#f1f5f9';
            msgDiv.style.color = '#1e293b';
        }

        messageBox.appendChild(msgDiv);

        // авто-очистка через указанное время
        setTimeout(() => {
            if (messageBox.firstChild === msgDiv) {
                msgDiv.style.transition = 'opacity 0.3s';
                msgDiv.style.opacity = '0';
                setTimeout(() => {
                    if (messageBox.firstChild === msgDiv) messageBox.innerHTML = '';
                }, 300);
            }
        }, duration);
    }

    // Очистка сообщения
    function clearMessage() {
        messageBox.innerHTML = '';
    }

    // Установка состояния загрузки
    function setLoading(loading) {
        isLoading = loading;
        if (loginButton) {
            if (loading) {
                loginButton.disabled = true;
                loginButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Вход...';
            } else {
                loginButton.disabled = false;
                loginButton.innerHTML = '<i class="fas fa-arrow-right-to-bracket"></i> Войти';
            }
        }
    }

    // Асинхронная отправка данных на сервер
    async function sendLoginRequest(username, password) {
            const requestBody = {
                username: username,
                password: password
            };

            // Логируем для отладки
            console.log('Sending login request to:', `${API_BASE_URL}/login`);
            console.log('Request body:', requestBody);

        const response = await fetch(`${API_BASE_URL}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(requestBody)
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || `HTTP ${response.status}: ${response.statusText}`);
        }

        return await response.json();
    }

    // Обработка успешного входа
    function handleLoginSuccess(data, username) {
        // Сохраняем токен, если он есть
        if (data.token) {
            localStorage.setItem('authToken', data.token);
            sessionStorage.setItem('authToken', data.token);
        }

        // Сохраняем данные пользователя
        const userData = {
            username: data.username || username,
            roles: data.roles || [],
            loginTime: new Date().toISOString()
        };
        localStorage.setItem('userData', JSON.stringify(userData));

        // Запоминание пользователя
        if (rememberCheckbox.checked) {
            localStorage.setItem('rememberedUser', username);
            localStorage.setItem('rememberFlag', 'true');
        } else {
            localStorage.removeItem('rememberedUser');
            localStorage.removeItem('rememberFlag');
        }

        // Показываем сообщение об успехе
        showMessage(`✅ Добро пожаловать, ${userData.username}! Вы успешно вошли в систему.`, 'success', 3000);

        // Перенаправление
        const redirectUrl = data.redirectUrl || '/dashboard';

        setTimeout(() => {
            window.location.href = redirectUrl;
        }, 1500);
    }

    // Обработка ошибок аутентификации
    function handleLoginError(error, username, password) {
        console.error('Login error:', error);

        let errorMessage = '❌ Ошибка аутентификации. ';

        if (error.message.includes('401') || error.message.includes('Unauthorized')) {
            errorMessage += 'Неверное имя пользователя или пароль.';
        } else if (error.message.includes('403')) {
            errorMessage += 'Доступ запрещен. Обратитесь к администратору.';
        } else if (error.message.includes('500')) {
            errorMessage += 'Внутренняя ошибка сервера. Попробуйте позже.';
        } else if (error.message.includes('NetworkError') || error.message.includes('Failed to fetch')) {
            errorMessage += 'Ошибка сети. Проверьте подключение к серверу.';
        } else {
            errorMessage += error.message || 'Попробуйте еще раз.';
        }

        showMessage(errorMessage, 'error');

        // Очищаем поле пароля при ошибке
        passwordInput.value = '';
        passwordInput.focus();
    }

    // Основная функция обработки логина
    async function handleLogin(event) {
        event.preventDefault();

        // Очищаем предыдущие сообщения
        clearMessage();

        // Получаем данные из формы
        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();

        // Валидация
        if (!username) {
            showMessage('❌ Пожалуйста, введите имя пользователя.', 'error');
            usernameInput.focus();
            return;
        }

        if (!password) {
            showMessage('❌ Пожалуйста, введите пароль.', 'error');
            passwordInput.focus();
            return;
        }

        if (password.length < 3) {
            showMessage('⚠️ Пароль должен содержать минимум 3 символа.', 'warning');
            passwordInput.focus();
            return;
        }

        // Блокируем кнопку и показываем загрузку
        setLoading(true);

        try {
            // Отправляем запрос на сервер
            const responseData = await sendLoginRequest(username, password);
            console.log("authenticated: " + responseData.authenticated)

            // Обрабатываем успешный вход
            handleLoginSuccess(responseData, username);

        } catch (error) {
            // Обрабатываем ошибку
            handleLoginError(error, username, password);
        } finally {
            // Разблокируем кнопку (если не было редиректа)
            setTimeout(() => {
                if (!isLoading) return;
                setLoading(false);
            }, 500);
        }
    }

    // Переключение видимости пароля
    function togglePasswordVisibility() {
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);
        if (type === 'text') {
            toggleIcon.classList.remove('fa-eye-slash');
            toggleIcon.classList.add('fa-eye');
        } else {
            toggleIcon.classList.remove('fa-eye');
            toggleIcon.classList.add('fa-eye-slash');
        }
    }

    // Загрузка сохранённого логина
    function loadRememberedUser() {
        const rememberFlag = localStorage.getItem('rememberFlag');
        const savedUser = localStorage.getItem('rememberedUser');
        if (rememberFlag === 'true' && savedUser) {
            usernameInput.value = savedUser;
            rememberCheckbox.checked = true;
            // Добавляем подсказку
            const hintMsg = document.createElement('small');
            hintMsg.style.display = 'block';
            hintMsg.style.marginTop = '4px';
            hintMsg.style.fontSize = '0.7rem';
            hintMsg.style.color = '#4f46e5';
            hintMsg.textContent = '🔐 Логин восстановлен (запомнить меня)';
            usernameInput.parentNode.appendChild(hintMsg);
            setTimeout(() => hintMsg.remove(), 3000);
        }
    }

    // Проверка наличия токена при загрузке
    function checkExistingToken() {
        const token = localStorage.getItem('authToken') || sessionStorage.getItem('authToken');
        if (token) {
            // Можно проверить валидность токена
            console.log('Existing token found');
            // Опционально: перенаправить на дашборд
            // window.location.href = '/dashboard';
        }
    }

    // Обработка "Забыли пароль?"
    function handleForgotPassword(e) {
        e.preventDefault();
        showMessage('📧 Для восстановления пароля обратитесь к администратору.', 'success');

        // Можно открыть модальное окно или перенаправить на страницу восстановления
        // window.location.href = '/forgot-password';
    }

    // Обработка "Создать аккаунт"
    function handleRegister(e) {
        e.preventDefault();
        showMessage('📝 Перенаправление на страницу регистрации...', 'success');

        setTimeout(() => {
            // window.location.href = '/register';
            console.log('Redirect to registration page');
        }, 1000);
    }

    // Сброс сообщения при вводе текста
    function resetMessageOnInput() {
        if (messageBox.innerHTML !== '') {
            clearMessage();
        }
    }

    // Добавляем CSS анимацию
    function addAnimationStyles() {
        if (!document.querySelector('#login-animation-styles')) {
            const style = document.createElement('style');
            style.id = 'login-animation-styles';
            style.textContent = `
                @keyframes fadeIn {
                    from {
                        opacity: 0;
                        transform: translateY(-10px);
                    }
                    to {
                        opacity: 1;
                        transform: translateY(0);
                    }
                }

                .spinner {
                    animation: spin 1s linear infinite;
                }

                @keyframes spin {
                    from {
                        transform: rotate(0deg);
                    }
                    to {
                        transform: rotate(360deg);
                    }
                }
            `;
            document.head.appendChild(style);
        }
    }

    // Инициализация событий
    function init() {
        addAnimationStyles();

        loginForm.addEventListener('submit', handleLogin);
        togglePasswordBtn.addEventListener('click', togglePasswordVisibility);
        forgotLink.addEventListener('click', handleForgotPassword);
        registerLink.addEventListener('click', handleRegister);

        // Загружаем "запомнить меня" при старте
        loadRememberedUser();

        // Проверяем существующий токен
        checkExistingToken();

        // Устанавливаем фокус
        if (usernameInput.value === '') {
            usernameInput.focus();
        } else {
            passwordInput.focus();
        }

        // Сброс сообщения при вводе
        usernameInput.addEventListener('input', resetMessageOnInput);
        passwordInput.addEventListener('input', resetMessageOnInput);

        // Добавляем обработку Enter в полях
        usernameInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter' && !isLoading) {
                e.preventDefault();
                passwordInput.focus();
            }
        });

        passwordInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter' && !isLoading) {
                e.preventDefault();
                loginForm.dispatchEvent(new Event('submit'));
            }
        });
    }

    // Запускаем инициализацию после полной загрузки DOM
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();