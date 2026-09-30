<%@ page contentType="text/html;charset=UTF-8" %>
<%
        String error = request.getParameter("error");
%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Login</title>
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body class="bg-gray-50 min-h-screen flex items-center justify-center">
        <div class="max-w-md w-full bg-white p-8 rounded-lg shadow">
            <h2 class="text-2xl font-semibold mb-4">Entrar</h2>
            <% if (error != null) { %>
                <div class="text-sm text-red-600 mb-3"><%= error %></div>
            <% } %>
            <form id="loginForm" class="space-y-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700" for="email">Email</label>
                    <input id="email" name="email" type="text" required class="mt-1 block w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500" />
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700" for="password">Senha</label>
                    <input id="password" name="password" type="password" required class="mt-1 block w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500" />
                </div>
                <div>
                    <button id="submitBtn" type="button" class="w-full bg-indigo-600 text-white py-2 rounded-md hover:bg-indigo-700">Entrar</button>
                </div>
            </form>
            <script>
                document.getElementById('submitBtn').addEventListener('click', async () => {
                    const email = document.getElementById('email').value;
                    const password = document.getElementById('password').value;
                    const form = new URLSearchParams();
                    form.append('email', email);
                    form.append('password', password);
                    const res = await fetch('http://localhost:8085/api/auth/login', { method: 'POST', body: form });
                    if (res.ok) {
                        const json = await res.json();
                        localStorage.setItem('user', JSON.stringify(json));
                        window.location.href = '/';
                    } else {
                        const el = document.createElement('div'); el.className='text-sm text-red-600 mb-3'; el.textContent='Credenciais inválidas';
                        document.querySelector('.max-w-md').insertBefore(el, document.querySelector('.max-w-md').children[2]);
                    }
                });
            </script>
            <p class="text-sm text-gray-600 mt-4">Não tem conta? <a href="/o/portlet-app/register.jsp" class="text-indigo-600 hover:underline">Registre-se</a></p>
        </div>
    </body>
</html>
