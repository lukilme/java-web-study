<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Register</title>
    <script src="https://cdn.tailwindcss.com"></script>
  </head>
  <body class="bg-gray-50 min-h-screen flex items-center justify-center">
    <div class="max-w-md w-full bg-white p-8 rounded-lg shadow">
      <h2 class="text-2xl font-semibold mb-4">Crie sua conta</h2>
      <form id="registerForm" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700" for="name">Nome</label>
          <input id="name" name="name" type="text" required class="mt-1 block w-full border border-gray-300 rounded-md px-3 py-2" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700" for="email">Email</label>
          <input id="email" name="email" type="email" required class="mt-1 block w-full border border-gray-300 rounded-md px-3 py-2" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700" for="password">Senha</label>
          <input id="password" name="password" type="password" required class="mt-1 block w-full border border-gray-300 rounded-md px-3 py-2" />
        </div>
        <input type="hidden" name="role" value="USER" />
        <div>
          <button id="regBtn" type="button" class="w-full bg-indigo-600 text-white py-2 rounded-md hover:bg-indigo-700">Criar conta</button>
        </div>
      </form>
      <script>
        document.getElementById('regBtn').addEventListener('click', async () => {
          const name = document.getElementById('name').value;
          const email = document.getElementById('email').value;
          const password = document.getElementById('password').value;
          const form = new URLSearchParams();
          form.append('name', name);
          form.append('email', email);
          form.append('password', password);
          form.append('role', 'USER');
          const res = await fetch('http://localhost:8085/api/users', { method: 'POST', body: form });
          if (res.status === 201) {
            window.location.href = '/login.jsp';
          } else {
            alert('Erro ao criar conta');
          }
        });
      </script>
      </form>
      <p class="text-sm text-gray-600 mt-4">Já tem conta? <a href="/login.jsp" class="text-indigo-600 hover:underline">Entrar</a></p>
    </div>
  </body>
</html>
